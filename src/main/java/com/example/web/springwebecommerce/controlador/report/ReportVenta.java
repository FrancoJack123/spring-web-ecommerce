package com.example.web.springwebecommerce.controlador.report;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportVenta {
    private Long codigo;
    private String nombre;
    private Double precio;
    private Integer cantidad;
    private Double monto;
    private Double desc;

    public ReportVenta(String nombre, Double precio, Integer cantidad, Double desc) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        if (desc == null){
            this.desc = 0.0;
            this.monto = precio * cantidad;
        }else {
            this.desc = desc;
            this.monto = ((precio - (precio * (desc / 100))) * cantidad);
        }
    }
}
