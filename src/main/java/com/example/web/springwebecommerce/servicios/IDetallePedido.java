package com.example.web.springwebecommerce.servicios;

import com.example.web.springwebecommerce.entidad.DetallePedido;

import java.util.List;

public interface IDetallePedido {
    List<DetallePedido> ListaDetallePedidoId(Long pedidoId);
}
