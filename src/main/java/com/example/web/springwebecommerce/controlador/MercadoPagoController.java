package com.example.web.springwebecommerce.controlador;

import com.example.web.springwebecommerce.entidad.DetallePedido;
import com.example.web.springwebecommerce.entidad.Pedido;
import com.example.web.springwebecommerce.entidad.Usuario;
import com.example.web.springwebecommerce.implementacion.IPedido;
import com.mercadopago.MercadoPago;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.preference.BackUrls;
import com.mercadopago.resources.datastructures.preference.Item;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/MercadoPago")
public class MercadoPagoController {

    @Value("${spring.localhost.direccion}")
    private String url;

    @Value("${mercadopago.client-id}")
    private String clientId;

    @Value("${mercadopago.client-secret}")
    private String clientSecret;

    @Autowired
    private IPedido iPedido;

    @GetMapping("/FinalizarCompra")
    public String CreateInstance(
            HttpSession session
    ) throws MPException {

        MercadoPago.SDK.setClientId(clientId);
        MercadoPago.SDK.setClientSecret(clientSecret);

        Preference preference = new Preference();
        preference.setBackUrls(
                new BackUrls().setFailure(url + "/MercadoPago/Falled")
                        .setPending(url + "/MercadoPago/Pending")
                        .setSuccess(url + "/MercadoPago/Success")
        );

        List<DetallePedido> carrito = (List<DetallePedido>) session.getAttribute("carrito");

        for (int i = 0; i < carrito.size(); i++){
                Item item = new Item();
                item.setTitle(carrito.get(i).getProductoId().getNombreProducto())
                        .setQuantity(carrito.get(i).getCantidadDetalle())
                        .setUnitPrice(carrito.get(i).getPrecioUnitario().floatValue())
                        .setCategoryId(carrito.get(i).getProductoId().getCategoriaId().getNombreCategoria())
                        .setPictureUrl("/assets/" + carrito.get(i).getProductoId().getFoto());
                preference.appendItem(item);
        }

        var result = preference.save();
        return "redirect:" + result.getSandboxInitPoint();
    }

    @GetMapping("/Falled")
    public String Falled(
            @RequestParam(value = "collection_id", required = false) String collectionId,
            @RequestParam(value = "collection_status", required = false) String collectionStatus,
            @RequestParam(value = "payment_id", required = false) String paymentId,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "external_reference", required = false) String externalReference,
            @RequestParam(value = "payment_type", required = false) String paymentType,
            @RequestParam(value = "merchant_order_id", required = false) String merchantOrderId,
            @RequestParam(value = "preference_id") String preferenceId,
            @RequestParam(value = "site_id") String siteId,
            @RequestParam(value = "processing_mode", required = false) String processingMode,
            @RequestParam(value = "merchant_account_id", required = false) String merchantAccountId,
            Model model
    ){
        model.addAttribute("icon","fa-circle-exclamation");
        model.addAttribute("color", "#991724");
        model.addAttribute("text", "No se ha podido cancelar su compra, Por favor vuelva a intentarlo nuevamente !!");
        model.addAttribute("preference_id", preferenceId);
        return "MercadoPago/HttpResponse";
    }

    @GetMapping("/Pending")
    public String Pending(
            @RequestParam(value = "collection_id", required = false) String collectionId,
            @RequestParam(value = "collection_status", required = false) String collectionStatus,
            @RequestParam(value = "payment_id", required = false) String paymentId,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "external_reference", required = false) String externalReference,
            @RequestParam(value = "payment_type", required = false) String paymentType,
            @RequestParam(value = "merchant_order_id", required = false) String merchantOrderId,
            @RequestParam(value = "preference_id") String preferenceId,
            @RequestParam(value = "site_id") String siteId,
            @RequestParam(value = "processing_mode", required = false) String processingMode,
            @RequestParam(value = "merchant_account_id", required = false) String merchantAccountId,
            Model model
    ){
        model.addAttribute("icon","fa-triangle-exclamation");
        model.addAttribute("color", "#ec991c");
        model.addAttribute("text", "Advertencia !!");
        model.addAttribute("preference_id", preferenceId);
        return "MercadoPago/HttpResponse";
    }

    @GetMapping("/Success")
    public String Success(
            @RequestParam(value = "collection_id", required = false) String collectionId,
            @RequestParam(value = "collection_status", required = false) String collectionStatus,
            @RequestParam(value = "payment_id", required = false) String paymentId,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "external_reference", required = false) String externalReference,
            @RequestParam(value = "payment_type", required = false) String paymentType,
            @RequestParam(value = "merchant_order_id", required = false) String merchantOrderId,
            @RequestParam(value = "preference_id") String preferenceId,
            @RequestParam(value = "site_id") String siteId,
            @RequestParam(value = "processing_mode", required = false) String processingMode,
            @RequestParam(value = "merchant_account_id", required = false) String merchantAccountId,
            HttpSession session,
            Model model
    ){

        List<DetallePedido> carrito = (List<DetallePedido>) session.getAttribute("carrito");;
        Pedido pedido = new Pedido();

        Usuario cliente = (Usuario) session.getAttribute("usuario");
        pedido.setUsuarioId(cliente);

        double monto = carrito.stream().mapToDouble(DetallePedido::getPrecioVentaDetalle).sum();
        pedido.setMontoPedido(monto);

        carrito.forEach(detallePedido -> detallePedido.setPedidoId(pedido));
        pedido.setDetalle(carrito);

        iPedido.AgregarPedido(pedido);

        carrito.clear();

        model.addAttribute("icon","fa-check");
        model.addAttribute("color", "#217524");
        model.addAttribute("text", "Su compra ha sido finalizada con éxito revise sus compras!!");
        model.addAttribute("preference_id", preferenceId);
        return "MercadoPago/HttpResponse";
    }

}
