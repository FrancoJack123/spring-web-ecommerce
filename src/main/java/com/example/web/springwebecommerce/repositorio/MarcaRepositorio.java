package com.example.web.springwebecommerce.repositorio;

import com.example.web.springwebecommerce.entidad.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarcaRepositorio extends JpaRepository<Marca, Long> {
}
