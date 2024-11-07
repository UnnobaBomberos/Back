package ancap.demo.Controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import ancap.demo.Entidad.Marca;
import ancap.demo.Entidad.Modelo;
import ancap.demo.Servicio.MarcaService;
import ancap.demo.Servicio.ModeloService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/modelos")
public class ModeloController {
    
    @Autowired
    private MarcaService marcaService;
    
    @Autowired
    private ModeloService modeloService;

    @GetMapping
    public List<Modelo> obtenerTodosLosModelos() {
        return modeloService.listarModelos();
    }

    // Obtener un modelo por su id
    @GetMapping("/{id}")
    public Modelo obtenerModeloPorId(@PathVariable Long id) {
        return modeloService.obtenerModelo(id);
    }

    // Crear un nuevo modelo
    @PostMapping
    public ResponseEntity<Modelo> crearModelo(
        @RequestParam("nombre_modelo") String nombreModelo,
        @RequestParam("año") int año,
        @RequestParam("id_marca") Long idMarca,
        @RequestParam(value = "imageRes", required = false) MultipartFile imageRes,
        @RequestParam(value = "pdf", required = false) MultipartFile pdf) {
    try {
        // Crea el objeto Modelo, sin los archivos
        Modelo nuevoModelo = new Modelo();
        nuevoModelo.setNombre_modelo(nombreModelo);
        nuevoModelo.setAño(año);
        // Puedes recuperar la marca desde la base de datos si es necesario
        Marca marca = marcaService.obtenerMarca(idMarca);
        nuevoModelo.setMarca(marca);
        
        // Guarda el modelo sin los archivos
        Modelo modeloGuardado = modeloService.crearModelo(nuevoModelo);
        
        // Si se sube una imagen, guárdala
        if (imageRes != null) {
            modeloService.subirImagen(modeloGuardado.getId(), imageRes);
        }

        // Si se sube un PDF, guárdalo
        if (pdf != null) {
            modeloService.subirPdf(modeloGuardado.getId(), pdf);
        }

        return ResponseEntity.ok(modeloGuardado);
    } catch (IOException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    // Actualizar un modelo existente
    @PutMapping("/{id}")
    public Modelo actualizarModelo(@PathVariable Long id, @RequestBody Modelo modeloDetalles) {
        return modeloService.actualizarModelo(id, modeloDetalles);
    }

    // Eliminar un modelo
    @DeleteMapping("/{id}")
    public void eliminarModelo(@PathVariable Long id) {
        modeloService.eliminarModelo(id);
    }

    // Subir una imagen (por ejemplo, para el modelo de auto)
    @PostMapping("/{id}/uploadImage")
    public ResponseEntity<String> uploadImage(@PathVariable Long id, @RequestParam("image") MultipartFile image) {
        try {
            modeloService.subirImagen(id, image);
            return ResponseEntity.ok("Imagen cargada exitosamente");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al cargar la imagen: " + e.getMessage());
        }
    }

    // Subir un PDF (por ejemplo, para el modelo de auto)
    @PostMapping("/{id}/uploadPdf")
    public ResponseEntity<String> uploadPdf(@PathVariable Long id, @RequestParam("pdf") MultipartFile pdf) {
        try {
            modeloService.subirPdf(id, pdf);
            return ResponseEntity.ok("PDF cargado exitosamente");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al cargar el PDF: " + e.getMessage());
        }
    }

    // Obtener imagen de un modelo por id
    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        try {
            byte[] imageBytes = modeloService.obtenerImagen(id);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "image/jpeg") // O "image/png" según el tipo
                    .body(imageBytes);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Obtener PDF de un modelo por id
    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> getPdf(@PathVariable Long id) {
        try {
            byte[] pdfBytes = modeloService.obtenerPdf(id);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
                    .body(pdfBytes);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
