package com.example.web.springwebecommerce.repositorio;

import com.example.web.springwebecommerce.entidad.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetallePedidoRepositorio extends JpaRepository<DetallePedido, Long> {
}
