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
    public ResponseEntity<String> uploadFile(
        @RequestParam("file") MultipartFile file,
        @RequestParam(value = "oldFile", required = false) String oldFile
    ) {
        try {
            // 1. Crear carpeta si no existe
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
                System.out.println("Carpeta /uploads creada");
            }

         // 2. Borrar archivo anterior si existe y es diferente del nuevo
            if (oldFile != null && !oldFile.isEmpty()) {
                String filename = StringUtils.cleanPath(file.getOriginalFilename().replaceAll(" ", "_"));
                String oldFileNameOnly = Paths.get(oldFile).getFileName().toString();

                // Solo borrar si los nombres de archivo son diferentes
                if (!filename.equals(oldFileNameOnly)) {
                    Path oldFilePath = uploadPath.resolve(oldFileNameOnly);
                    if (Files.exists(oldFilePath)) {
                        Files.delete(oldFilePath);
                        System.out.println("Archivo anterior eliminado: " + oldFilePath);
                    }
                }
            }

            // 3. Guardar el nuevo archivo
            String filename = StringUtils.cleanPath(file.getOriginalFilename().replaceAll(" ", "_"));
            Path filePath = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Archivo nuevo guardado correctamente: " + filename);
            String fileUrl = "/uploads/" + filename;
            return ResponseEntity.ok(fileUrl);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("❌ Error al subir el archivo: " + e.getMessage());
        }
    }


}