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

    public Usuario LoginUsuario(String correo, String contrasenia){
        return userRepositorio.UsuarioLogin(correo, contrasenia);
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

}
