package com.example.web.springwebecommerce.repositorio;

import com.example.web.springwebecommerce.entidad.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepositorio extends JpaRepository<Pedido, Long> {
}
