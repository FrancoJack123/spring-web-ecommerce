package com.example.web.springwebecommerce.servicios;

import com.example.web.springwebecommerce.entidad.Rol;

import java.util.List;
import java.util.Optional;

public interface IRol {
    List<Rol> ListarRoles();

    void GuardarRol(Rol rol);

    Rol BuscarRol(Long rolId);
}
