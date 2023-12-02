package com.example.web.springwebecommerce.servicios;

import com.example.web.springwebecommerce.entidad.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPedido {
    Pedido BuscarPedido(Long pedidoId);

    void AgregarPedido(Pedido pedido);

    Page<Pedido> ListarPedidosClienteId(Long clienteId, Pageable pageable);
}
