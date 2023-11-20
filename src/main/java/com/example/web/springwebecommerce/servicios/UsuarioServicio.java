package com.example.web.springwebecommerce.servicios;

import com.example.web.springwebecommerce.entidad.Usuario;
import com.example.web.springwebecommerce.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServicio {
    private final UsuarioRepositorio userRepositorio;

    @Autowired
    public UsuarioServicio(UsuarioRepositorio userRepositorio){
        this.userRepositorio = userRepositorio;
    }

    public Usuario LoginUsuario(String correo){
        return userRepositorio.UsuarioLogin(correo);
    }

    public List<Usuario> ListarUsuarios(){
        return userRepositorio.findUsuarios();
    }

    public List<Usuario> ListarClientes(){
        return userRepositorio.findClientes();
    }

    public Usuario AgregarUsuario(Usuario usuario){
        return userRepositorio.save(usuario);
    }

    public Usuario ActualizarUsuario(Usuario usuario){
        return userRepositorio.save(usuario);
    }

    public void DesactivarUsuario(Long usuarioId){
        userRepositorio.desactivateUsuario(usuarioId);
    }

    public Usuario BuscarUsuario(Long usuarioId){
        Optional<Usuario> usuario = userRepositorio.findById(usuarioId);
        return usuario.get();
    }

    public boolean ConfirmarUsuario(String token){
        int filas = userRepositorio.confirmarUsuario(token);
        boolean confirmar = (filas > 0) ? true : false;
        return confirmar;
    }

    public boolean SolicitarContrasenia(String correo){
        int filas = userRepositorio.SolicitarContrasenia(correo);
        boolean confirmar = (filas > 0) ? true : false;
        return confirmar;
    }

    public boolean RestablecerContrasenia(String contrasenia, String token){
        int filas = userRepositorio.RestablecerContrasenia(contrasenia, token);
        boolean confirmar = (filas > 0) ? true : false;
        return confirmar;
    }

    public void GuardarToken(String token, Long usuarioId){
        userRepositorio.GuardarToken(usuarioId, token);
    }

    public boolean correoExitente(String correo){
        return userRepositorio.existsByCorreoUsuario(correo.toUpperCase());
    }

}
