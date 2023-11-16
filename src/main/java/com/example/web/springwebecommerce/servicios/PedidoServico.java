package com.example.web.springwebecommerce.servicios;

import com.example.web.springwebecommerce.entidad.Pedido;
import com.example.web.springwebecommerce.repositorio.PedidoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PedidoServico {
    private final PedidoRepositorio pedidoRepositorio;

    @Autowired
    public PedidoServico(PedidoRepositorio pedidoRepositorio){
        this.pedidoRepositorio = pedidoRepositorio;
    }

    public void AgregarPedido(Pedido pedido){
        pedidoRepositorio.save(pedido);
    }

    public Page<Pedido> ListarPedidosClienteId(Long clienteId, Pageable pageable){ return pedidoRepositorio.ListarPedidosUsuarioId(clienteId, pageable); }
}
