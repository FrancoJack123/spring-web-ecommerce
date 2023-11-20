package com.example.web.springwebecommerce.utilidad.correo.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Correo {
    private String para;
    private String asunto;
    private String mensaje;
}
