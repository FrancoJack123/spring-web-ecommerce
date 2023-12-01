package com.example.web.springwebecommerce.servicios;

import com.example.web.springwebecommerce.entidad.Marca;
import com.example.web.springwebecommerce.implementacion.IMarca;
import com.example.web.springwebecommerce.repositorio.MarcaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MarcaServicio implements IMarca {
    private final MarcaRepositorio marcaRepositorio;

    @Autowired
    public MarcaServicio(MarcaRepositorio marcaRepositorio){
        this.marcaRepositorio = marcaRepositorio;
    }

    @Override
    public List<Marca> ListarMarca(){
        return marcaRepositorio.findAll();
    }

    @Override
    public void GuardarMarca(Marca marca){
        marcaRepositorio.save(marca);
    }

    @Override
    public Marca BuscarMarca(Long marcaId){
        Optional<Marca> marca = marcaRepositorio.findById(marcaId);
        return marca.get();
    }

}
