package com.example.web.springwebecommerce.entidad;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productoId;

    private String nombreProducto;

    @Column(length = 10000)
    private String descripProducto;

    private Double descProducto;

    private Double precioProducto;
    private Integer cantidadProducto;

    private Boolean estadoProducto = false;

    @Temporal(TemporalType.DATE)
    @CreationTimestamp
    @Column(name = "fecha_ingreso_producto", updatable = false)
    private Date fechaRegistroProducto;

    private String foto;

    @ManyToOne
    @JoinColumn(name = "categoriaId")
    Categoria categoriaId;

    @ManyToOne
    @JoinColumn(name = "marcaId")
    Marca marcaId;

}
