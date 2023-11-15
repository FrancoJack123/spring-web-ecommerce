package com.example.web.springwebecommerce.controlador;

import com.example.web.springwebecommerce.entidad.*;
import com.example.web.springwebecommerce.servicios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/Dashboard")
public class DashboardController {
    @Value("${storage.location}")
    private String storageLocation;
    private final UsuarioServicio usuarioServicio;
    private final RolServicio rolServicio;
    private final MarcaServicio marcaServicio;
    private final CategoriaServicio categoriaServicio;
    private final ProductoServicio productoServicio;

    @Autowired
    public DashboardController(UsuarioServicio u, RolServicio r, MarcaServicio m, CategoriaServicio c, ProductoServicio p){
        this.usuarioServicio = u;
        this.rolServicio = r;
        this.marcaServicio = m;
        this.categoriaServicio = c;
        this.productoServicio = p;
    }

    @GetMapping("/")
    public String Dashboard(Model model){

        return "Dashboard/Dashboard";
    }

    @GetMapping("/Rol")
    public String Cargos(Model model){
        Rol rol = new Rol();
        List<Rol> list = rolServicio.ListarRoles();
        model.addAttribute("listadoRoles", list);
        model.addAttribute("rol", rol);
        return "Dashboard/Cargo";
    }

    @PostMapping("/Rol")
    public String Cargos(@ModelAttribute Rol rol){
        rolServicio.GuardarRol(rol);
        return "redirect:/Dashboard/Rol";
    }

    @GetMapping("/Rol/{rolId}")
    public ResponseEntity<Rol> BuscarCargo(@PathVariable Long rolId){
        Rol rol = rolServicio.BuscarRol(rolId);
        return new ResponseEntity<>(rol, HttpStatus.OK);
    }

    @GetMapping("/Usuarios")
    public String Usuarios(Model model){
        Usuario usuario = new Usuario();
        List<Rol> listarRoles = rolServicio.ListarRoles();
        List<Usuario> list = usuarioServicio.ListarUsuarios();
        model.addAttribute("listarRoles", listarRoles);
        model.addAttribute("listadoUsuarios", list);
        model.addAttribute("usuario", usuario);
        return "Dashboard/Usuarios";
    }

    @PostMapping("/Usuarios")
    public String Usuarios(@ModelAttribute Usuario usuario){
        usuario.setEstadoUsuario(true);
        usuarioServicio.AgregarUsuario(usuario);
        return "redirect:/Dashboard/Usuarios";
    }

    @GetMapping("/Usuarios/{usuarioId}")
    public ResponseEntity<Usuario> BuscarUsuario(@PathVariable Long usuarioId){
        Usuario usuario = usuarioServicio.BuscarUsuario(usuarioId);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @PostMapping("/DesactivarUsuarios")
    public String DesactivarUsuario(@RequestParam Long usuarioId){
        usuarioServicio.DesactivarUsuario(usuarioId);
        return "redirect:/Dashboard/Usuarios";
    }

    @GetMapping("/Marcas")
    public String Marcas(Model model){
        Marca marca = new Marca();
        List<Marca> list = marcaServicio.ListarMarca();
        model.addAttribute("listadoMarca", list);
        model.addAttribute("marca", marca);
        return "Dashboard/Marcas";
    }

    @PostMapping("/Marcas")
    public String GuardarMarcas(@ModelAttribute Marca marca){
        marcaServicio.GuardarMarca(marca);
        return "redirect:/Dashboard/Marcas";
    }

    @GetMapping("/Marcas/{marcaId}")
    public ResponseEntity<Marca> BuscarMarca(@PathVariable Long marcaId){
        Marca marca = marcaServicio.BuscarMarca(marcaId);
        return new ResponseEntity<>(marca, HttpStatus.OK);
    }

    @GetMapping("/Categoria")
    public String Categoria(Model model){
        Categoria categoria = new Categoria();
        List<Categoria> list = categoriaServicio.ListarCategoria();
        model.addAttribute("listadoCategoria", list);
        model.addAttribute("categoria", categoria);
        return "Dashboard/Categoria";
    }

    @PostMapping("/Categoria")
    public String GuardarCategoria(@ModelAttribute Categoria categoria){
        categoriaServicio.GuardarCategoria(categoria);
        return "redirect:/Dashboard/Categoria";
    }

    @GetMapping("/Categoria/{categoriaId}")
    public ResponseEntity<Categoria> BuscarCategoria(@PathVariable Long categoriaId){
        Categoria categoria = categoriaServicio.BuscarCategoria(categoriaId);
        return new ResponseEntity<>(categoria, HttpStatus.OK);
    }

    @GetMapping("/Productos")
    public String Productos(Model model){
        List<Producto> list = productoServicio.ListarProductos();
        List<Categoria> listarCategoria = categoriaServicio.ListarCategoria();
        List<Marca> listarMarca = marcaServicio.ListarMarca();
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
            Producto producto1 = productoServicio.BuscarProducto(producto.getProductoId());
            producto.setFoto(producto1.getFoto());
        }

        producto.setEstadoProducto(true);
        productoServicio.GuardarProducto(producto);
        return "redirect:/Dashboard/Productos";
    }

    @GetMapping("/Productos/{productoid}")
    public ResponseEntity<Producto> BuscarProducto(@PathVariable Long productoid){
        Producto producto = productoServicio.BuscarProducto(productoid);
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    @PostMapping("/DesactivarProducto")
    public String DesactivarProducto(@RequestParam Long productoId){
        productoServicio.DesactivarProducto(productoId);
        return "redirect:/Dashboard/Productos";
    }

    @GetMapping("/Clientes")
    public String Clientes(Model model){
        Usuario usuario = new Usuario();
        List<Usuario> list = usuarioServicio.ListarClientes();
        model.addAttribute("listadoClientes", list);
        model.addAttribute("cliente", usuario);
        return "Dashboard/Clientes";
    }
}
