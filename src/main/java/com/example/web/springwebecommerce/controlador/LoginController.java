package com.example.web.springwebecommerce.controlador;

import com.example.web.springwebecommerce.entidad.Rol;
import com.example.web.springwebecommerce.entidad.Usuario;
import com.example.web.springwebecommerce.servicios.IUsuario;
import com.example.web.springwebecommerce.utilidad.correo.modelo.Correo;
import com.example.web.springwebecommerce.utilidad.correo.servicio.CorreoServicio;
import com.example.web.springwebecommerce.utilidad.login.LoginServicio;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Value("${spring.localhost.direccion}")
    private String url;

    @Autowired
    private IUsuario iUsuario;

    @Autowired
    private CorreoServicio correoServicio;

    @Autowired
    private LoginServicio loginServicio;


    @GetMapping("/login")
    public String Login(
            Model model,
            HttpSession session
    ){
        if ((String) session.getAttribute("mensaje") != null) {
            model.addAttribute("mensaje", (String) session.getAttribute("mensaje"));
            model.addAttribute("clase", (String) session.getAttribute("clase"));
            session.removeAttribute("mensaje");
            session.removeAttribute("clase");
        }
        
        return "Login/login";
    }

    @PostMapping("/login")
    public String LoginValidate(
            Model model,
            @RequestParam String correo,
            @RequestParam String password,
            HttpSession session
    ){
        Usuario usuario = iUsuario.LoginUsuario(correo);

        if (usuario == null){
            session.setAttribute("mensaje","Credenciales incorrectas");
            session.setAttribute("clase", "danger");
            return "redirect:/login";
        }

        if (!loginServicio.verificarPassword(password, usuario.getPasswordUsuario())){
            session.setAttribute("mensaje","Usuario " + usuario.getNombreUsuario().toUpperCase() +" su contraseña no coincide");
            session.setAttribute("clase", "danger");
            return "redirect:/login";
        }

        if (!usuario.getConfirmar()){
            session.setAttribute("mensaje","Falta confirmar su cuenta. Revice su bandeja de entrada");
            session.setAttribute("clase", "danger");
            return "redirect:/login";
        }

        if (usuario.getRestablecer()){
            session.setAttribute("mensaje","Se ha solicitado restablecer su cuenta, favor de revisar su bandeja de entrada");
            session.setAttribute("clase", "danger");
            return "redirect:/login";
        }

        session.setAttribute("usuario", usuario);

        if(usuario.getCargoId().getRolId() != 3)
            return "redirect:/Dashboard/";
        else
            return "redirect:/Ecommerce/";

    }

    @GetMapping("/Register")
    public String Register(Model model, HttpSession session){
        Usuario usuario = new Usuario();

        if ((String) session.getAttribute("mensaje") != null) {
            model.addAttribute("mensaje", (String) session.getAttribute("mensaje"));
            model.addAttribute("clase", (String) session.getAttribute("clase"));
            session.removeAttribute("mensaje");
            session.removeAttribute("clase");
        }

        model.addAttribute("user", usuario);
        return "Login/register";
    }

    @PostMapping("/Register")
    public String Register(
            Model model,
            @ModelAttribute Usuario usuario,
            HttpSession session
    ){

        boolean confirmacorreo = iUsuario.correoExitente(usuario.getCorreoUsuario());

        if (confirmacorreo){
            session.setAttribute("mensaje","El correo " + usuario.getCorreoUsuario().toUpperCase() + " ya fue registrado");
            session.setAttribute("clase", "danger");
            return "redirect:/Register";
        }

        usuario.setCargoId(new Rol(3L));
        usuario.setEstadoUsuario(true);
        usuario.setPasswordUsuario(loginServicio.codificarPassword(usuario.getPasswordUsuario()));
        usuario.setConfirmar(false);
        usuario.setRestablecer(false);
        usuario.setToken(loginServicio.generarToken(usuario.getNombreUsuario(), usuario.getCorreoUsuario()));
        usuario.setCorreoUsuario(usuario.getCorreoUsuario().toUpperCase());
        iUsuario.AgregarUsuario(usuario);


        String modifiedUrl = url + "/confirmar/" + usuario.getToken();

        Correo correo = new Correo(usuario.getCorreoUsuario(), "Correo confirmacion", modifiedUrl);
        correoServicio.enviarEmail(correo);

        session.setAttribute("mensaje","Su cuenta ha sido creada. Hemos enviado un mensaje al correo " + usuario.getCorreoUsuario() + " para confirmar su cuenta");
        session.setAttribute("clase", "success");

        return "redirect:/Register";
    }

    @PostMapping("/logout")
    public String Logout(Model model, HttpSession session){
        session.removeAttribute("usuario");
        return "redirect:/Ecommerce/";
    }

    @GetMapping("/confirmar/{token}")
    public String Confirmar(
            @PathVariable String token,
            HttpSession session
    ){
        boolean confirmar = iUsuario.ConfirmarUsuario(token);

        if (confirmar) {
            session.setAttribute("mensaje", "Su cuenta ha sido confirmada con exito");
            session.setAttribute("clase", "success");
        }
        else{
            session.setAttribute("mensaje","Su cuenta ya ha sido confirmada");
            session.setAttribute("clase", "danger");
        }

        return "redirect:/login";
    }

    @GetMapping("/solicitar")
    public String Solicitar(
        Model model,
        HttpSession session
    ){
        if ((String) session.getAttribute("mensaje") != null) {
            model.addAttribute("mensaje", (String) session.getAttribute("mensaje"));
            model.addAttribute("clase", (String) session.getAttribute("clase"));
            session.removeAttribute("mensaje");
            session.removeAttribute("clase");
        }

        return "Login/forgotpassword";
    }

    @PostMapping("/solicitar")
    public String Solicitar(
            Model model,
            HttpSession session,
            @RequestParam String correo
    ){
        Usuario usuario = iUsuario.LoginUsuario(correo.toUpperCase());

        if (usuario == null){
            session.setAttribute("mensaje","No existe una cuenta con el correo " + correo.toUpperCase());
            session.setAttribute("clase", "danger");
            return "redirect:/solicitar";
        }

        boolean respuesta = iUsuario.SolicitarContrasenia(correo);

        if (respuesta){
            iUsuario.GuardarToken(loginServicio.generarToken(usuario.getNombreUsuario(), usuario.getCorreoUsuario()), usuario.getUsuarioId());
            String modifiedUrl = url +  "/restablecer/" + loginServicio.generarToken(usuario.getNombreUsuario(), usuario.getCorreoUsuario());
            Correo structurecorreo = new Correo(usuario.getCorreoUsuario(), "Restablecer cuenta", modifiedUrl);
            correoServicio.enviarEmail(structurecorreo);

            session.setAttribute("mensaje","Se envio un enlace al correo " + correo.toUpperCase() + " para el restablecimiento de su contraseña");
            session.setAttribute("clase", "success");
            return "redirect:/solicitar";

        }else {
            session.setAttribute("mensaje","El enlace ya ha sido enviado a su correo");
            session.setAttribute("clase", "danger");
            return "redirect:/solicitar";
        }

    }

    @GetMapping("/restablecer/{token}")
    public String Restablecer(
            Model model,
            HttpSession session,
            @PathVariable String token
    ){
        if ((String) session.getAttribute("mensaje") != null) {
            model.addAttribute("mensaje", (String) session.getAttribute("mensaje"));
            model.addAttribute("clase", (String) session.getAttribute("clase"));
            session.removeAttribute("mensaje");
            session.removeAttribute("clase");
        }

        model.addAttribute("token", token);
        return "Login/password";
    }

    @PostMapping("/restablecer")
    public String Restablecer(
            Model model,
            HttpSession session,
            @RequestParam String token,
            @RequestParam String contrasenia,
            @RequestParam String confirmarContrasenia
    ){
        if (!contrasenia.equals(confirmarContrasenia)){
            session.setAttribute("mensaje","Las contraseñas no coinciden");
            session.setAttribute("clase", "danger");
            return "redirect:/restablecer/" + token;
        }

        boolean restablecer = iUsuario.RestablecerContrasenia(loginServicio.codificarPassword(contrasenia), token);

        if (restablecer){
            session.setAttribute("mensaje","Su contraseña fue cambiada con exito");
            session.setAttribute("clase", "success");
            return "redirect:/login";
        }else {
            session.setAttribute("mensaje","Error, Token invalido");
            session.setAttribute("clase", "danger");
            return "redirect:/login";
        }

    }

}
