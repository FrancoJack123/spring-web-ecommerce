package com.example.web.springwebecommerce.servicios;

import com.example.web.springwebecommerce.entidad.Marca;
import com.example.web.springwebecommerce.repositorio.MarcaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MarcaServicio {
    private final MarcaRepositorio marcaRepositorio;

    @Autowired
    public MarcaServicio(MarcaRepositorio marcaRepositorio){
        this.marcaRepositorio = marcaRepositorio;
    }

    public List<Marca> ListarMarca(){
        return marcaRepositorio.findAll();
    }

    public void GuardarMarca(Marca marca){
        marcaRepositorio.save(marca);
    }

    public Marca BuscarMarca(Long marcaId){
        Optional<Marca> marca = marcaRepositorio.findById(marcaId);
        return marca.get();
    }

}
