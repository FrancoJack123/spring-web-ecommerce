package com.example.web.springwebecommerce.controlador;

import com.example.web.springwebecommerce.entidad.Rol;
import com.example.web.springwebecommerce.entidad.Usuario;
import com.example.web.springwebecommerce.servicios.UsuarioServicio;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    private final UsuarioServicio usuarioServicio;

    @Autowired
    public LoginController(UsuarioServicio usuarioServicio){
        this.usuarioServicio = usuarioServicio;
    }

    @GetMapping("/login")
    public String Login(Model model){
        return "Login/login";
    }

    @PostMapping("/login")
    public String LoginValidate(
            Model model,
            @RequestParam String correo,
            @RequestParam String password,
            HttpSession session
    ){
        Usuario usuario = usuarioServicio.LoginUsuario(correo, password);
        if (usuario == null){
            return "Login/login";
        }

        session.setAttribute("usuario", usuario);
        if(usuario.getCargoId().getRolId() != 3){
            return "redirect:/Dashboard/";
        }else {
            return "redirect:/Ecommerce/";
        }
    }

    @GetMapping("/Register")
    public String Register(Model model){
        Usuario usuario = new Usuario();
        model.addAttribute("user", usuario);
        return "Login/register";
    }

    @PostMapping("/Register")
    public String Register(
            Model model,
            @ModelAttribute Usuario usuario,
            HttpSession session
    ){
        usuario.setCargoId(new Rol(3L));
        usuario.setEstadoUsuario(true);
        usuarioServicio.AgregarUsuario(usuario);
        session.setAttribute("usuario", usuario);
        return "redirect:/Ecommerce/";
    }

    @PostMapping("/logout")
    public String Logout(Model model, HttpSession session){
        session.removeAttribute("usuario");
        return "redirect:/Ecommerce/";
    }

}
