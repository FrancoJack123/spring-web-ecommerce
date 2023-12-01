package com.example.web.springwebecommerce.servicios;

import com.example.web.springwebecommerce.entidad.Usuario;
import com.example.web.springwebecommerce.implementacion.IUsuario;
import com.example.web.springwebecommerce.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServicio implements IUsuario {
    private final UsuarioRepositorio userRepositorio;

    @Autowired
    public UsuarioServicio(UsuarioRepositorio userRepositorio){
        this.userRepositorio = userRepositorio;
    }

    @Override
    public Usuario LoginUsuario(String correo){
        return userRepositorio.UsuarioLogin(correo);
    }

    @Override
    public List<Usuario> ListarUsuarios(){
        return userRepositorio.findUsuarios();
    }

    @Override
    public List<Usuario> ListarClientes(){
        return userRepositorio.findClientes();
    }

    @Override
    public Usuario AgregarUsuario(Usuario usuario){
        return userRepositorio.save(usuario);
    }

    @Override
    public Usuario ActualizarUsuario(Usuario usuario){
        return userRepositorio.save(usuario);
    }

    @Override
    public void DesactivarUsuario(Long usuarioId){
        userRepositorio.desactivateUsuario(usuarioId);
    }

    @Override
    public void ActivarUsuario(Long usuarioId) {
        userRepositorio.activateUsuario(usuarioId);
    }

    @Override
    public Usuario BuscarUsuario(Long usuarioId){
        Optional<Usuario> usuario = userRepositorio.findById(usuarioId);
        return usuario.get();
    }

    @Override
    public boolean ConfirmarUsuario(String token){
        int filas = userRepositorio.confirmarUsuario(token);
        boolean confirmar = (filas > 0) ? true : false;
        return confirmar;
    }

    @Override
    public boolean SolicitarContrasenia(String correo){
        int filas = userRepositorio.SolicitarContrasenia(correo);
        boolean confirmar = (filas > 0) ? true : false;
        return confirmar;
    }

    @Override
    public boolean RestablecerContrasenia(String contrasenia, String token){
        int filas = userRepositorio.RestablecerContrasenia(contrasenia, token);
        boolean confirmar = (filas > 0) ? true : false;
        return confirmar;
    }

    @Override
    public void GuardarToken(String token, Long usuarioId){
        userRepositorio.GuardarToken(usuarioId, token);
    }

    @Override
    public boolean correoExitente(String correo){
        return userRepositorio.existsByCorreoUsuario(correo.toUpperCase());
    }
}
