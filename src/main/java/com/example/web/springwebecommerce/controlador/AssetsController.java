package com.example.web.springwebecommerce.controlador;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/assets")
public class AssetsController {

    @Value("${storage.location}")
    private String storageLocation;

    @GetMapping("/{imagen:.+}")
    public Resource RecursosImg(
            @PathVariable("imagen") String imagen
    ) throws MalformedURLException {
        Path archivo = Paths.get(storageLocation).resolve(imagen);
        Resource recurso = new UrlResource(archivo.toUri());
        return recurso;
    }
}
