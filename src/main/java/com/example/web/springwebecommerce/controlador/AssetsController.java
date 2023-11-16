package com.example.web.springwebecommerce.controlador;

import com.example.web.springwebecommerce.entidad.DetallePedido;
import com.example.web.springwebecommerce.servicios.DetallePedidoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/assets")
public class AssetsController {
    @Value("${storage.location}")
    private String storageLocation;

    @GetMapping("/{filename:.+}")
    public Resource obtenerRecurso(@PathVariable("filename") String filename) throws MalformedURLException {
        Path archivo = Paths.get(storageLocation).resolve(filename);
        Resource recurso = new UrlResource(archivo.toUri());
        return recurso;
    }
}
