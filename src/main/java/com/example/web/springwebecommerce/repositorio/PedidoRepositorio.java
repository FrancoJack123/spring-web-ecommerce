package com.example.web.springwebecommerce.repositorio;

import com.example.web.springwebecommerce.entidad.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepositorio extends JpaRepository<Pedido, Long> {
    @Query("SELECT p FROM Pedido p WHERE p.usuarioId.usuarioId = :clienteId")
    Page<Pedido> ListarPedidosUsuarioId(@Param("clienteId")Long clienteId, Pageable pageable);
}
