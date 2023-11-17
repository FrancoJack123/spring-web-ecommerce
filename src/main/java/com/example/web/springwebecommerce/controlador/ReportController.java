package com.example.web.springwebecommerce.controlador;

import com.example.web.springwebecommerce.controlador.report.ReportVenta;
import com.example.web.springwebecommerce.entidad.Pedido;
import com.example.web.springwebecommerce.servicios.PedidoServico;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
@RequestMapping("/report/web/app")
public class ReportController {

    @Autowired
    private PedidoServico pedidoServico;

    @PostMapping("/detalleVenta")
    public void GenerarReporte(
            @RequestParam Long pedidoId,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws Exception {
        Pedido pedido = pedidoServico.BuscarPedido(pedidoId);

        List<ReportVenta> reportVentas = new ArrayList<>();
        for (int i = 0; i < pedido.getDetalle().size(); i++){
            ReportVenta reportVenta = new ReportVenta(
                    pedido.getDetalle().get(i).getProductoId().getProductoId(),
                    pedido.getDetalle().get(i).getProductoId().getNombreProducto(),
                    pedido.getDetalle().get(i).getProductoId().getPrecioProducto(),
                    pedido.getDetalle().get(i).getCantidadDetalle(),
                    pedido.getDetalle().get(i).getProductoId().getDescProducto()
            );
            reportVentas.add(reportVenta);
        }

        JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile("src/main/resources/static/jasper/ReportProductos.jasper");
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource((Collection<?>) reportVentas);

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("ds", ds);
        parameters.put("codCompra", pedido.getPedidoId());

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline;filename=reporteVenta.pdf");

        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }
}
