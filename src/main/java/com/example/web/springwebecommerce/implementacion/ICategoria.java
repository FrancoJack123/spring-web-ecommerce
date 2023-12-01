package com.example.web.springwebecommerce.implementacion;

import com.example.web.springwebecommerce.entidad.Categoria;

import java.util.List;

public interface ICategoria {
    List<Categoria> ListarCategoria();
    void GuardarCategoria(Categoria categoria);
    Categoria BuscarCategoria(Long categoriaId);
}
