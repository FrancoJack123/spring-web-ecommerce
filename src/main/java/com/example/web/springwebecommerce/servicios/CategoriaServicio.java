package com.example.web.springwebecommerce.servicios;

import com.example.web.springwebecommerce.entidad.Categoria;
import com.example.web.springwebecommerce.repositorio.CategoriaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServicio {
    private final CategoriaRepositorio categoriaRepositorio;

    @Autowired
    public CategoriaServicio(CategoriaRepositorio categoriaRepositorio){
        this.categoriaRepositorio = categoriaRepositorio;
    }

    public List<Categoria> ListarCategoria(){
        return categoriaRepositorio.findAll();
    }

    public void GuardarCategoria(Categoria categoria){
        categoriaRepositorio.save(categoria);
    }

    public Categoria BuscarCategoria(Long categoriaId){
        Optional<Categoria> categoria = categoriaRepositorio.findById(categoriaId);
        return categoria.get();
    }
}
