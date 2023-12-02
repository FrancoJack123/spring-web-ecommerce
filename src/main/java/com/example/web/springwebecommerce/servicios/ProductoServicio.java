package com.example.web.springwebecommerce.servicios;

import com.example.web.springwebecommerce.entidad.Producto;
import com.example.web.springwebecommerce.repositorio.ProductoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServicio implements IProducto {

    private final ProductoRepositorio productoRepositorio;

    @Autowired
    public ProductoServicio(ProductoRepositorio productoRepositorio){
        this.productoRepositorio = productoRepositorio;
    }

    @Override
    public List<Producto> ListarProductos(){
        return productoRepositorio.findAll();
    }

    @Override
    public List<Producto> ListarProductosRelacionados(Long marcaId, Long categoriaId){
        return productoRepositorio.ListarProductoRelacionados(categoriaId, marcaId);
    }

    @Override
    public Page<Producto> ListarProductoCategoriaMarca(Long marcaId, Long categoriaId, Pageable pageable){
        return productoRepositorio.ListarProductoCategoriaMarcas(categoriaId, marcaId, pageable);
    }

    @Override
    public void GuardarProducto(Producto producto){
        productoRepositorio.save(producto);
    }

    @Override
    public Producto BuscarProducto(Long productoId){
        Optional<Producto> producto = productoRepositorio.findById(productoId);
        return producto.get();
    }

    @Override
    public void DesactivarProducto(Long productoId){
        productoRepositorio.desactivateProducto(productoId);
    }

    @Override
    public void ActivarProducto(Long productoId, Integer cantidad) {
        productoRepositorio.activateProducto(productoId, cantidad);
    }

}
