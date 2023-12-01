package com.example.web.springwebecommerce.servicios;

import com.example.web.springwebecommerce.entidad.Rol;
import com.example.web.springwebecommerce.implementacion.IRol;
import com.example.web.springwebecommerce.repositorio.RolRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolServicio implements IRol{
    private final RolRepositorio rolRepositorio;

    @Autowired
    public RolServicio(RolRepositorio rolRepositorio){
        this.rolRepositorio = rolRepositorio;
    }

    @Override
    public List<Rol> ListarRoles(){
        return rolRepositorio.findAll();
    }

    @Override
    public void GuardarRol(Rol rol){
        rolRepositorio.save(rol);
    }

    @Override
    public Rol BuscarRol(Long rolId){
        Optional<Rol> rol = rolRepositorio.findById(rolId);
        return rol.get();
    }

}
