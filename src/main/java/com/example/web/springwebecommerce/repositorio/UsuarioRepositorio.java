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

    boolean existsByCorreoUsuario(String correo);

    @Query("SELECT c FROM Usuario c WHERE c.cargoId.rolId = 3 AND c.estadoUsuario = true")
    List<Usuario> findClientes();

    @Query("SELECT c FROM Usuario c WHERE c.cargoId.rolId <> 3")
    List<Usuario> findUsuarios();

    @Query("SELECT u FROM Usuario u WHERE u.correoUsuario = :usuarioCorreo")
    Usuario UsuarioLogin(@Param("usuarioCorreo") String usuarioCorreo);

    @Modifying
    @Transactional
    @Query("UPDATE Usuario u SET u.estadoUsuario = false WHERE u.usuarioId = :usuarioId")
    void desactivateUsuario(@Param("usuarioId") Long usuarioId);

    @Modifying
    @Transactional
    @Query("UPDATE Usuario u SET u.estadoUsuario = true WHERE u.usuarioId = :usuarioId")
    void activateUsuario(@Param("usuarioId") Long usuarioId);

    @Modifying
    @Transactional
    @Query("UPDATE Usuario u SET u.confirmar = true, u.token = null WHERE u.token = :token")
    int confirmarUsuario(@Param("token") String token);

    @Modifying
    @Transactional
    @Query("UPDATE Usuario u SET u.restablecer = true WHERE u.correoUsuario = :correo")
    int SolicitarContrasenia(@Param("correo") String correo);

    @Modifying
    @Transactional
    @Query("UPDATE Usuario u SET u.restablecer = false, u.passwordUsuario = :password, u.token = null WHERE u.token = :token")
    int RestablecerContrasenia(@Param("password") String password, @Param("token") String token);

    @Modifying
    @Transactional
    @Query("UPDATE Usuario u SET u.token = :token WHERE u.usuarioId = :idusuario")
    void GuardarToken(@Param("idusuario") Long idusuario, @Param("token") String token);
}
