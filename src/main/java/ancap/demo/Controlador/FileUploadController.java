package ancap.demo.Controlador;

import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    private static final String UPLOAD_DIR = "C:/Users/Tobia/OneDrive/Documentos/bomberosApp/Back/demo/uploaded_files/";
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
    try {
        Path path = Paths.get(UPLOAD_DIR);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
        String fileName = file.getOriginalFilename();
        Path filePath = path.resolve(fileName);
        file.transferTo(filePath.toFile());

        // Solo devuelve el nombre del archivo
        return ResponseEntity.ok(fileName);
    } catch (IOException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al cargar archivo: " + e.getMessage());
    }
    }   
    @GetMapping("/files/{fileName}")
    public ResponseEntity<byte[]> getFile(@PathVariable String fileName) {
    try {
        Path filePath = Paths.get(UPLOAD_DIR).resolve(fileName);
        File file = filePath.toFile();
        if (file.exists()) {
            byte[] fileContent = Files.readAllBytes(filePath);
            
            // Determina el tipo de contenido (MIME type) del archivo
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream"; // Tipo genérico en caso de no poder determinarlo
            }
            
            return ResponseEntity.ok()
                    .header("Content-Type", contentType) // Agregar el tipo de contenido
                    .body(fileContent);
        } else {
            return ResponseEntity.notFound().build();
        }
    } catch (IOException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    }
    @GetMapping("/list")
    public ResponseEntity<List<String>> listFiles() {
        try {
            Path dirPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(dirPath)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
            }
        List<String> fileNames = Files.list(dirPath)
                .filter(Files::isRegularFile)
                .map(path -> path.getFileName().toString())
                .collect(Collectors.toList());
            return ResponseEntity.ok(fileNames);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        try {
            // Ruta al archivo en el servidor
            Path filePath = Paths.get("C:/Users/Tobia/OneDrive/Documentos/bomberosApp/Back/demo/uploaded_files/").resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            // Configurar encabezados para la descarga
            return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(resource);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}