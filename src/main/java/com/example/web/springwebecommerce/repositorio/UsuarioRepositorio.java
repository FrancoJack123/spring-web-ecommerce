package com.example.web.springwebecommerce.repositorio;

import com.example.web.springwebecommerce.entidad.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    @Query("SELECT c FROM Usuario c WHERE c.cargoId.rolId = 3 AND c.estadoUsuario = true")
    List<Usuario> findClientes();

    @Query("SELECT c FROM Usuario c WHERE c.cargoId.rolId <> 3 AND c.estadoUsuario = true")
    List<Usuario> findUsuarios();

    @Query("SELECT u FROM Usuario u WHERE u.correoUsuario = :usuarioCorreo AND u.passwordUsuario = :usuarioContrasenia")
    Usuario UsuarioLogin(@Param("usuarioCorreo") String usuarioCorreo, @Param("usuarioContrasenia") String usuarioContrasenia);

    @Modifying
    @Transactional
    @Query("UPDATE Usuario u SET u.estadoUsuario = false WHERE u.usuarioId = :usuarioId")
    void desactivateUsuario(@Param("usuarioId") Long usuarioId);
}
