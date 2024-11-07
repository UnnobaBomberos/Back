package ancap.demo.Servicio;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class FileService {

    public String uploadFile(MultipartFile file) throws IOException {
        // Guardar el archivo en el servidor local
        String fileName = file.getOriginalFilename();
        Path path = Path.of("uploaded_files/logos/" + fileName);  // Cambia la ruta si es necesario
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
    
        // Devolver la URL del archivo
        return "http://localhost:8090/api/files/files/" + fileName;
    }
    
}
