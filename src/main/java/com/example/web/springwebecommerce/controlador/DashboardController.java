package com.example.web.springwebecommerce.controlador;

import ch.qos.logback.core.model.INamedModel;
import com.example.web.springwebecommerce.entidad.*;
import com.example.web.springwebecommerce.implementacion.*;
import com.example.web.springwebecommerce.utilidad.login.LoginServicio;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/Dashboard")
public class DashboardController {

    @Value("${storage.location}")
    private String storageLocation;

    @Autowired
    private IUsuario iUsuario;

    @Autowired
    private IRol iRol;

    @Autowired
    private IMarca iMarca;

    @Autowired
    private ICategoria iCategoria;

    @Autowired
    private IProducto iProducto;

    @Autowired
    private LoginServicio loginServicio;

    @GetMapping("/")
    public String Dashboard(Model model){

        return "Dashboard/Dashboard";
    }

    @GetMapping("/Rol")
    public String Cargos(Model model){
        Rol rol = new Rol();
        List<Rol> list = iRol.ListarRoles();
        model.addAttribute("listadoRoles", list);
        model.addAttribute("rol", rol);
        return "Dashboard/Cargo";
    }

    @PostMapping("/Rol")
    public String Cargos(@ModelAttribute Rol rol){
        iRol.GuardarRol(rol);
        return "redirect:/Dashboard/Rol";
    }

    @GetMapping("/Rol/{rolId}")
    public ResponseEntity<Rol> BuscarCargo(@PathVariable Long rolId){
        Rol rol = iRol.BuscarRol(rolId);
        return new ResponseEntity<>(rol, HttpStatus.OK);
    }

    @GetMapping("/Usuarios")
    public String Usuarios(Model model, HttpSession session){

        if ((String) session.getAttribute("mensaje") != null) {
            model.addAttribute("mensaje", (String) session.getAttribute("mensaje"));
            model.addAttribute("clase", (String) session.getAttribute("clase"));
            session.removeAttribute("mensaje");
            session.removeAttribute("clase");
        }

        Usuario usuario = new Usuario();
        List<Rol> listarRoles = iRol.ListarRoles();
        List<Usuario> list = iUsuario.ListarUsuarios();
        model.addAttribute("listarRoles", listarRoles);
        model.addAttribute("listadoUsuarios", list);
        model.addAttribute("usuario", usuario);
        return "Dashboard/Usuarios";
    }

    @PostMapping("/Usuarios")
    public String Usuarios(@ModelAttribute Usuario usuario, HttpSession session){

        if (usuario.getUsuarioId() == 0){
            boolean confirmacorreo = iUsuario.correoExitente(usuario.getCorreoUsuario());

            if (confirmacorreo){
                session.setAttribute("mensaje","El correo " + usuario.getCorreoUsuario().toUpperCase() + " ya fue registrado");
                session.setAttribute("clase", "danger");
                return "redirect:/Dashboard/Usuarios";
            }
        }

        usuario.setConfirmar(true);
        usuario.setRestablecer(false);
        usuario.setEstadoUsuario(true);
        usuario.setCorreoUsuario(usuario.getCorreoUsuario().toUpperCase());

        if (usuario.getPasswordUsuario().isBlank() || usuario.getPasswordUsuario() == null){
            Usuario usuario1 = iUsuario.BuscarUsuario(usuario.getUsuarioId());
            usuario.setPasswordUsuario(usuario1.getPasswordUsuario());
        }else{
            usuario.setPasswordUsuario(loginServicio.codificarPassword(usuario.getPasswordUsuario()));
        }

        iUsuario.AgregarUsuario(usuario);
        return "redirect:/Dashboard/Usuarios";
    }

    @GetMapping("/Usuarios/{usuarioId}")
    public ResponseEntity<Usuario> BuscarUsuario(@PathVariable Long usuarioId){
        Usuario usuario = iUsuario.BuscarUsuario(usuarioId);
        usuario.setPasswordUsuario("");
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @PostMapping("/DesactivarUsuarios")
    public String DesactivarUsuario(@RequestParam Long usuarioId){
        iUsuario.DesactivarUsuario(usuarioId);
        return "redirect:/Dashboard/Usuarios";
    }

    @PostMapping("/ActivarUsuario")
    public String ActivarUsuario(@RequestParam Long usuarioIdActivar){
        iUsuario.ActivarUsuario(usuarioIdActivar);
        return "redirect:/Dashboard/Usuarios";
    }

    @GetMapping("/Marcas")
    public String Marcas(Model model){
        Marca marca = new Marca();
        List<Marca> list = iMarca.ListarMarca();
        model.addAttribute("listadoMarca", list);
        model.addAttribute("marca", marca);
        return "Dashboard/Marcas";
    }

    @PostMapping("/Marcas")
    public String GuardarMarcas(@ModelAttribute Marca marca){
        iMarca.GuardarMarca(marca);
        return "redirect:/Dashboard/Marcas";
    }

    @GetMapping("/Marcas/{marcaId}")
    public ResponseEntity<Marca> BuscarMarca(@PathVariable Long marcaId){
        Marca marca = iMarca.BuscarMarca(marcaId);
        return new ResponseEntity<>(marca, HttpStatus.OK);
    }

    @GetMapping("/Categoria")
    public String Categoria(Model model){
        Categoria categoria = new Categoria();
        List<Categoria> list = iCategoria.ListarCategoria();
        model.addAttribute("listadoCategoria", list);
        model.addAttribute("categoria", categoria);
        return "Dashboard/Categoria";
    }

    @PostMapping("/Categoria")
    public String GuardarCategoria(@ModelAttribute Categoria categoria){
        iCategoria.GuardarCategoria(categoria);
        return "redirect:/Dashboard/Categoria";
    }

    @GetMapping("/Categoria/{categoriaId}")
    public ResponseEntity<Categoria> BuscarCategoria(@PathVariable Long categoriaId){
        Categoria categoria = iCategoria.BuscarCategoria(categoriaId);
        return new ResponseEntity<>(categoria, HttpStatus.OK);
    }

    @GetMapping("/Productos")
    public String Productos(Model model){
        List<Producto> list = iProducto.ListarProductos();
        List<Categoria> listarCategoria = iCategoria.ListarCategoria();
        List<Marca> listarMarca = iMarca.ListarMarca();
        Producto producto = new Producto();
        model.addAttribute("listarCategoria", listarCategoria);
        model.addAttribute("listarMarca", listarMarca);
        model.addAttribute("listadoProducto", list);
        model.addAttribute("producto", producto);
        return "Dashboard/Productos";
    }

    @PostMapping("/Productos")
    public String GuardarProducto(@ModelAttribute Producto producto, @RequestParam("imagen")MultipartFile imagen){
        if(!imagen.isEmpty()){
            //Path directorio = Paths.get("src//main//resources//static/img");
            //String rutaAbsoluta = directorio.toFile().getAbsolutePath();
            //String rutaAbsoluta = "C://Users//Jack Moreno//Pictures//imgSpring";
            try {
                byte[] bytesimg = imagen.getBytes();
                //Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename());
                Files.write(Paths.get(storageLocation).resolve(imagen.getOriginalFilename()), bytesimg);
                producto.setFoto(imagen.getOriginalFilename());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            Producto producto1 = iProducto.BuscarProducto(producto.getProductoId());
            producto.setFoto(producto1.getFoto());
        }

        producto.setEstadoProducto(true);
        iProducto.GuardarProducto(producto);
        return "redirect:/Dashboard/Productos";
    }

    @GetMapping("/Productos/{productoid}")
    public ResponseEntity<Producto> BuscarProducto(@PathVariable Long productoid){
        Producto producto = iProducto.BuscarProducto(productoid);
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    @PostMapping("/DesactivarProducto")
    public String DesactivarProducto(@RequestParam Long productoId){
        iProducto.DesactivarProducto(productoId);
        return "redirect:/Dashboard/Productos";
    }

    @PostMapping("/ActivarProducto")
    public String ActivarProducto(@RequestParam Long productoId, @RequestParam Integer cantidad){
        if (cantidad <= 0){
            return "redirect:/Dashboard/Productos";
        }
        iProducto.ActivarProducto(productoId, cantidad);
        return "redirect:/Dashboard/Productos";
    }

    @GetMapping("/Clientes")
    public String Clientes(Model model){
        Usuario usuario = new Usuario();
        List<Usuario> list = iUsuario.ListarClientes();
        model.addAttribute("listadoClientes", list);
        model.addAttribute("cliente", usuario);
        return "Dashboard/Clientes";
    }
}
