package com.example.web.springwebecommerce.entidad;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pedidoId;

    private Double montoPedido;

    @Temporal(TemporalType.DATE)
    @CreationTimestamp
    @Column(name = "fecha_ingreso_pedido", updatable = false)
    private Date fechaIngresoPedido;

    @ManyToOne
    @JoinColumn(name = "usuarioId")
    Usuario usuarioId;

    @OneToMany(mappedBy = "pedidoId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePedido> detalle = new ArrayList<>();
}
