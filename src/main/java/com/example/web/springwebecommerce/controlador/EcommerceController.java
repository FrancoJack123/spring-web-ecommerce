package com.example.web.springwebecommerce.controlador;

import com.example.web.springwebecommerce.entidad.*;
import com.example.web.springwebecommerce.implementacion.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/Ecommerce")
public class EcommerceController {

    @Autowired
    private IProducto iProducto;

    @Autowired
    private IMarca iMarca;

    @Autowired
    private ICategoria iCategoria;

    @Autowired
    private IPedido iPedido;

    @Autowired
    private IDetallePedido iDetallePedido;

    @GetMapping("/")
    public String ListarProductoCategoriaMarcas(
            @RequestParam(name = "categoriaId", required = false, defaultValue = "0")Long categoriaId,
            @RequestParam(name = "marcaId", required = false, defaultValue = "0")Long marcaId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "6") int size,
            Model model
    ){
        PageRequest pageable = PageRequest.of(page, size);
        Page<Producto> Listado = iProducto.ListarProductoCategoriaMarca(marcaId,categoriaId,pageable);
        List<Marca> marcas = iMarca.ListarMarca();
        List<Categoria> categorias = iCategoria.ListarCategoria();

        //Metodo para la paginacion
        int cantLis = (int) Listado.getTotalElements();
        
        int cantPag = Listado.getTotalPages();

        model.addAttribute("cantPag", cantPag);
        model.addAttribute("cantLis", cantLis);
        model.addAttribute("page", page);
        model.addAttribute("size", size);

        model.addAttribute("marcaId",marcaId);
        model.addAttribute("categoriaId",categoriaId);
        model.addAttribute("marcas",marcas);
        model.addAttribute("categorias",categorias);
        model.addAttribute("ProducMarcaCategoria", Listado);

        return "Ecommerce/ListProducts";
    }

    @GetMapping("/view/{idproducto}")
    public String BuscarProductoDetaills(
            @PathVariable Long idproducto,
            Model model
    ){
        Producto producto = iProducto.BuscarProducto(idproducto);
        List<Producto> Listadoproductos = iProducto.ListarProductosRelacionados(producto.getMarcaId().getMarcaId(), producto.getCategoriaId().getCategoriaId());

        Listadoproductos.remove(producto);

        if(Listadoproductos.size() >= 5){
            Listadoproductos = Listadoproductos.subList(0,4);
        }

        model.addAttribute("Listadoproductos", Listadoproductos);
        model.addAttribute("producto", producto);
        return "Ecommerce/ItemProduct";
    }

    private List<DetallePedido> obtenerCarrito(HttpSession session){
        List<DetallePedido> carrito = (List<DetallePedido>) session.getAttribute("carrito");
        if(carrito == null){
            carrito = new ArrayList<DetallePedido>();
            this.guardarCarrito(carrito, session);
        }
        return carrito;
    }

    private void guardarCarrito(List<DetallePedido> carrito, HttpSession session){
        session.setAttribute("carrito", carrito);
    }

    @PostMapping("/guardarProducto")
    public String GuardarProducto(
            @RequestParam(name = "productoId", defaultValue = "0") Long productoId,
            @RequestParam(name = "cantidad", defaultValue = "1") Integer cantidad,
            HttpSession session
    ){

        List<DetallePedido> carrito = this.obtenerCarrito(session);
        Producto producto = iProducto.BuscarProducto(productoId);


        if ((producto == null)){
            return "redirect:/Ecommerce/";
        }

        if (producto.getCantidadProducto() <= 0){
            return "redirect:/Ecommerce/";
        }

        double precioDetalleSinCantidad = producto.getPrecioProducto();

        if(producto.getDescProducto() != null){
            precioDetalleSinCantidad = producto.getPrecioProducto() - (producto.getPrecioProducto() * (producto.getDescProducto() / 100));
        }

        if (!carrito.isEmpty()){
            for (DetallePedido car : carrito){
                if(car.getProductoId().getProductoId().equals(producto.getProductoId())){
                    car.setPrecioVentaDetalle(car.getPrecioVentaDetalle() + (precioDetalleSinCantidad * cantidad));
                    car.setCantidadDetalle(car.getCantidadDetalle() + cantidad);
                    this.guardarCarrito(carrito, session);
                    return "redirect:/Ecommerce/";
                }
            }
        }

        carrito.add(new DetallePedido(cantidad, precioDetalleSinCantidad,(precioDetalleSinCantidad * cantidad), producto));

        this.guardarCarrito(carrito, session);

        return "redirect:/Ecommerce/";
    }

    @PostMapping("/eliminarProducto")
    public String ElimnarProductoCarrito(
            @RequestParam(name = "productoId", defaultValue = "0")Long productoId,
            HttpSession session
    ){
        List<DetallePedido> carrito = this.obtenerCarrito(session);

        boolean eliminado = carrito.removeIf(detallePedido -> detallePedido.getProductoId().getProductoId().equals(productoId));

        return "redirect:/Ecommerce/carrito";
    }

    @GetMapping("/carrito")
    public String VerCarrito(
            Model model,
            @ModelAttribute("mensaje") String mensaje,
            HttpSession session
    ){
        List<DetallePedido> carrito = this.obtenerCarrito(session);
        model.addAttribute("carrito", carrito);

        model.addAttribute("mensaje", mensaje);
        model.addAttribute("montoTotal", carrito.stream().mapToDouble(DetallePedido::getPrecioVentaDetalle).sum());

        return "Ecommerce/CarritoCompras";
    }

    @PostMapping("/comprar")
    public String ComprarPedido(
            HttpSession session
    ){
        List<DetallePedido> carrito = this.obtenerCarrito(session);
        Pedido pedido = new Pedido();

        Usuario cliente = (Usuario) session.getAttribute("usuario");
        pedido.setUsuarioId(cliente);

        double monto = carrito.stream().mapToDouble(DetallePedido::getPrecioVentaDetalle).sum();
        pedido.setMontoPedido(monto);

        carrito.forEach(detallePedido -> detallePedido.setPedidoId(pedido));
        pedido.setDetalle(carrito);

        iPedido.AgregarPedido(pedido);

        carrito.clear();

        return "redirect:/Ecommerce/";
    }

    @GetMapping("/detalleVenta")
    public String DetalleVenta(
        Model model,
        HttpSession session,
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "3") int size
    ){
        Usuario cliente = (Usuario) session.getAttribute("usuario");
        PageRequest pageable = PageRequest.of(page, size);
        Page<Pedido> list = iPedido.ListarPedidosClienteId(cliente.getUsuarioId(), pageable);

        //Metodo para la paginacion
        int cantLis = (int) list.getTotalElements();
        int cantPag = list.getTotalPages();

        model.addAttribute("cantPag", cantPag);
        model.addAttribute("cantLis", cantLis);
        model.addAttribute("page", page);
        model.addAttribute("size", size);


        model.addAttribute("ListaPedidos", list);
        return "Ecommerce/DetalleCompra";
    }

    @GetMapping("/listaDetallePedido/{pedidoID}")
    public ResponseEntity<List<DetallePedido>> ListarPedidosUsuarioId(@PathVariable Long pedidoID){
        List<DetallePedido> list = iDetallePedido.ListaDetallePedidoId(pedidoID);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
