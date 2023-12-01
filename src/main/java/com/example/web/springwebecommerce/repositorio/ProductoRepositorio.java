package com.example.web.springwebecommerce.repositorio;

import com.example.web.springwebecommerce.entidad.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductoRepositorio extends JpaRepository<Producto, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Producto p SET p.estadoProducto = false, p.cantidadProducto = 0 WHERE p.productoId = :productoId")
    void desactivateProducto(@Param("productoId") Long productoId);

    @Modifying
    @Transactional
    @Query("UPDATE Producto p SET p.estadoProducto = true, p.cantidadProducto = :cantidad WHERE p.productoId = :productoId")
    void activateProducto(@Param("productoId") Long productoId, @Param("cantidad") Integer cantidad);

    //@Query("SELECT p FROM Producto p WHERE p.categoriaId.categoriaId = :categoriaId AND p.marcaId.marcaId = :marcaId")
    @Query("SELECT p FROM Producto p WHERE (p.categoriaId.categoriaId = :categoriaId OR :categoriaId = 0) AND (p.marcaId.marcaId = :marcaId OR :marcaId = 0)")
    Page<Producto> ListarProductoCategoriaMarcas(@Param("categoriaId")Long categoriaId, @Param("marcaId")Long marcaId, Pageable pageable);

    @Query("SELECT p FROM Producto p WHERE (p.categoriaId.categoriaId = :categoriaId OR :categoriaId = 0) AND (p.marcaId.marcaId = :marcaId OR :marcaId = 0)")
    List<Producto> ListarProductoRelacionados(@Param("categoriaId")Long categoriaId, @Param("marcaId")Long marcaId);
}
