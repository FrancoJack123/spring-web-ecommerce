package com.example.web.springwebecommerce.repositorio;

import com.example.web.springwebecommerce.entidad.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepositorio extends JpaRepository<Rol, Long> {
}
