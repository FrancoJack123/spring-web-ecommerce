package com.example.web.springwebecommerce.repositorio;

import com.example.web.springwebecommerce.entidad.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetallePedidoRepositorio extends JpaRepository<DetallePedido, Long> {
    @Query("SELECT d FROM DetallePedido d WHERE d.pedidoId.pedidoId = :pedidoId")
    public List<DetallePedido> ListaDetallePedidoId(@Param("pedidoId")Long pedidoId);
}
