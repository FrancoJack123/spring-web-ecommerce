package com.example.web.springwebecommerce.implementacion;

import com.example.web.springwebecommerce.entidad.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IProducto {
    List<Producto> ListarProductos();

    List<Producto> ListarProductosRelacionados(Long marcaId, Long categoriaId);

    Page<Producto> ListarProductoCategoriaMarca(Long marcaId, Long categoriaId, Pageable pageable);

    void GuardarProducto(Producto producto);

    Producto BuscarProducto(Long productoId);

    void DesactivarProducto(Long productoId);

    void ActivarProducto(Long productoId, Integer cantidad);
}
