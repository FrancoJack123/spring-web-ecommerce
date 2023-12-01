package com.example.web.springwebecommerce.servicios;

import com.example.web.springwebecommerce.entidad.DetallePedido;
import com.example.web.springwebecommerce.implementacion.IDetallePedido;
import com.example.web.springwebecommerce.repositorio.DetallePedidoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetallePedidoServicio implements IDetallePedido {
    private final DetallePedidoRepositorio pedidoRepositorio;

    @Autowired
    public DetallePedidoServicio(DetallePedidoRepositorio detallePedidoRepositorio){
        this.pedidoRepositorio = detallePedidoRepositorio;
    }

    @Override
    public List<DetallePedido> ListaDetallePedidoId(Long pedidoId){
        return pedidoRepositorio.ListaDetallePedidoId(pedidoId);
    }
}
