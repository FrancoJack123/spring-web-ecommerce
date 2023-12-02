package com.example.web.springwebecommerce.servicios;

import com.example.web.springwebecommerce.entidad.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuario {
    Usuario LoginUsuario(String correo);

    List<Usuario> ListarUsuarios();

    List<Usuario> ListarClientes();

    Usuario AgregarUsuario(Usuario usuario);

    Usuario ActualizarUsuario(Usuario usuario);

    void DesactivarUsuario(Long usuarioId);

    void ActivarUsuario(Long usuarioId);

    Usuario BuscarUsuario(Long usuarioId);

    boolean ConfirmarUsuario(String token);

    boolean SolicitarContrasenia(String correo);

    boolean RestablecerContrasenia(String contrasenia, String token);

    void GuardarToken(String token, Long usuarioId);

    boolean correoExitente(String correo);
}
