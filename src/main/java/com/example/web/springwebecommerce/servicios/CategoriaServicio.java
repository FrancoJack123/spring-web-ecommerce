package com.example.web.springwebecommerce.servicios;

import com.example.web.springwebecommerce.entidad.Categoria;
import com.example.web.springwebecommerce.implementacion.ICategoria;
import com.example.web.springwebecommerce.repositorio.CategoriaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServicio implements ICategoria {
    private final CategoriaRepositorio categoriaRepositorio;

    @Autowired
    public CategoriaServicio(CategoriaRepositorio categoriaRepositorio){
        this.categoriaRepositorio = categoriaRepositorio;
    }

    @Override
    public List<Categoria> ListarCategoria(){
        return categoriaRepositorio.findAll();
    }

    @Override
    public void GuardarCategoria(Categoria categoria){
        categoriaRepositorio.save(categoria);
    }

    @Override
    public Categoria BuscarCategoria(Long categoriaId){
        Optional<Categoria> categoria = categoriaRepositorio.findById(categoriaId);
        return categoria.get();
    }
}
