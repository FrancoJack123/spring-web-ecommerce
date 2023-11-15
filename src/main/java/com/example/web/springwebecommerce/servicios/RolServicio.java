package com.example.web.springwebecommerce.servicios;

import com.example.web.springwebecommerce.entidad.Rol;
import com.example.web.springwebecommerce.repositorio.RolRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolServicio {
    private final RolRepositorio rolRepositorio;

    @Autowired
    public RolServicio(RolRepositorio rolRepositorio){
        this.rolRepositorio = rolRepositorio;
    }

    public List<Rol> ListarRoles(){
        return rolRepositorio.findAll();
    }

    public void GuardarRol(Rol rol){
        rolRepositorio.save(rol);
    }

    public Rol BuscarRol(Long rolId){
        Optional<Rol> rol = rolRepositorio.findById(rolId);
        return rol.get();
    }

}
