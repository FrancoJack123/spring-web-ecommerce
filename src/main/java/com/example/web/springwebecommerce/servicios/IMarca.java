package com.example.web.springwebecommerce.servicios;

import com.example.web.springwebecommerce.entidad.Marca;

import java.util.List;
import java.util.Optional;

public interface IMarca {
    List<Marca> ListarMarca();

    void GuardarMarca(Marca marca);

    Marca BuscarMarca(Long marcaId);
}
