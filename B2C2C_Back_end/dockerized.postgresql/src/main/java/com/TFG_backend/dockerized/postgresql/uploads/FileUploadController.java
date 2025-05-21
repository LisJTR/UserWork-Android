package com.TFG_backend.dockerized.postgresql.uploads;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.*;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
        	//Limpiamos el nombre y reemplazamos los espacios
            String filename = StringUtils.cleanPath(file.getOriginalFilename().replaceAll(" ", "_"));
            Path uploadPath = Paths.get(uploadDir);

            // Creamos la carpeta si no existe
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
                System.out.println("Carpeta /uploads creada");
            }

            //Guardamos el archivo en el servidos
            Path filePath = uploadPath.resolve(filename);

            System.out.println("Nombre del archivo recibido: " + filename);
            System.out.println("Ruta completa esperada: " + filePath.toAbsolutePath());
            
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Archivo copiado correctamente");

            // Guardamos la ruta relativa para guardarla en la BBBDD
            String fileUrl = "/uploads/" + filename;
            return ResponseEntity.ok(fileUrl);

        } catch (IOException e) {
            e.printStackTrace(); // <<-- Asegúrate de tener esto para ver el error en consola
            return ResponseEntity.status(500).body("❌ Error al subir el archivo: " + e.getMessage());
        }
    }

}