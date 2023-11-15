package com.example.web.springwebecommerce.entidad;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DetallePedidos")
public class DetallePedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long detallePedidoId;

    private Integer cantidadDetalle;
    private Double precioVentaDetalle;

    @ManyToOne
    @JoinColumn(name = "productoId")
    Producto productoId;

    @ManyToOne
    @JoinColumn(name = "pedidoId")
    Pedido pedidoId;

    public DetallePedido(Integer cantidadDetalle, Double precioVentaDetalle, Producto productoId) {
        this.cantidadDetalle = cantidadDetalle;
        this.precioVentaDetalle = precioVentaDetalle;
        this.productoId = productoId;
    }
}
