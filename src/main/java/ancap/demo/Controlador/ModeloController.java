package ancap.demo.Controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;

import ancap.demo.Entidad.Marca;
import ancap.demo.Entidad.Modelo;
import ancap.demo.Servicio.MarcaService;
import ancap.demo.Servicio.ModeloService;


@RestController
@RequestMapping("/api/modelos")
public class ModeloController {

    @Autowired
    private MarcaService marcaService;
    @Autowired
    private ModeloService modeloService;
    @Autowired
    private FileUploadController fileUploadController;

    // Obtener todos los modelos
    @GetMapping
    public List<Modelo> obtenerTodosLosModelos() {
        return modeloService.listarModelos();
    }

    // Obtener un modelo por su id
    @GetMapping("/{id}")
    public ResponseEntity<Modelo> obtenerModeloPorId(@PathVariable Long id) {
        Modelo modelo = modeloService.obtenerModelo(id);
        if (modelo != null) {
            return ResponseEntity.ok(modelo);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Crear un nuevo modelo con archivos (multipart/form-data)
    @PostMapping
    public ResponseEntity<?> crearModelo(
        @RequestParam("nombre") String nombre,
        @RequestParam("año") int año,
        @RequestParam("marca_id") Long marcaId,
        @RequestParam("imageRes") MultipartFile imageRes,
        @RequestParam("pdf") MultipartFile pdf) {

        try {
            // Buscar la marca correspondiente
            Marca marca = marcaService.obtenerMarca(marcaId);
            if (marca == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Marca no encontrada.");
            }

            // Crear el modelo
            Modelo modelo = new Modelo();
            modelo.setNombre(nombre);
            modelo.setAño(año);
            modelo.setMarca(marca);

            // Subir la imagen y el PDF
            String imageUrl = fileUploadController.uploadFile(imageRes).getBody();
            String pdfUrl = fileUploadController.uploadFile(pdf).getBody();

            modelo.setImageRes(imageUrl);
            modelo.setPdf(pdfUrl);

            // Guardar el modelo
            Modelo modeloGuardado = modeloService.crearModelo(modelo);
            return ResponseEntity.ok(modeloGuardado);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el modelo: " + e.getMessage());
        }
    }

    // Actualizar un modelo existente con archivos (multipart/form-data)
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarModelo(
        @PathVariable Long id,
        @RequestParam("nombre") String nombre,
        @RequestParam("año") int año,
        @RequestParam("marca_id") Long marca_Id,
        @RequestParam(value = "imageRes", required = false) MultipartFile imageRes,
        @RequestParam(value = "pdf", required = false) MultipartFile pdf) {

        try {
            // Obtener el modelo actual
            Modelo modelo = modeloService.obtenerModelo(id);
            if (modelo == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Modelo no encontrado.");
            }

            // Actualizar los detalles del modelo
            modelo.setNombre(nombre);
            modelo.setAño(año);
            Marca marca = marcaService.obtenerMarca(marca_Id);
            if (marca != null) {
                modelo.setMarca(marca);
            }

            // Subir y actualizar la imagen, si se ha proporcionado una
            if (imageRes != null) {
                String imageUrl = fileUploadController.uploadFile(imageRes).getBody();
                modelo.setImageRes(imageUrl);
            }

            // Subir y actualizar el PDF, si se ha proporcionado uno
            if (pdf != null) {
                String pdfUrl = fileUploadController.uploadFile(pdf).getBody();
                modelo.setPdf(pdfUrl);
            }

            // Guardar el modelo actualizado
            Modelo modeloActualizado = modeloService.actualizarModelo(modelo);
            return ResponseEntity.ok(modeloActualizado);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el modelo: " + e.getMessage());
        }
    }

    // Eliminar un modelo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarModelo(@PathVariable Long id) {
        try {
            modeloService.eliminarModelo(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Subir una imagen
    @PostMapping("/{id}/uploadImage")
    public ResponseEntity<String> uploadImage(@PathVariable Long id, @RequestParam("image") MultipartFile image) {
        try {
            // Subir el archivo de imagen
            ResponseEntity<String> response = fileUploadController.uploadFile(image);
            if (response.getStatusCode() == HttpStatus.OK) {
                String imageUrl = response.getBody();
                modeloService.actualizarRutaImagen(id, imageUrl);  // Guarda la URL de la imagen en la base de datos
                return ResponseEntity.ok("Imagen cargada exitosamente en la ruta: " + imageUrl);
            } else {
                return ResponseEntity.status(response.getStatusCode()).body("Error al cargar la imagen");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al cargar la imagen: " + e.getMessage());
        }
    }

    // Subir un PDF
    @PostMapping("/{id}/uploadPdf")
    public ResponseEntity<String> uploadPdf(@PathVariable Long id, @RequestParam("pdf") MultipartFile pdf) {
        try {
            ResponseEntity<String> response = fileUploadController.uploadFile(pdf);
            if (response.getStatusCode() == HttpStatus.OK) {
                String pdfUrl = response.getBody();
                modeloService.actualizarRutaPdf(id, pdfUrl);  // Guarda la URL en la base de datos
                return ResponseEntity.ok("PDF cargado exitosamente en la ruta: " + pdfUrl);
            } else {
                return ResponseEntity.status(response.getStatusCode()).body("Error al cargar el PDF");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al cargar el PDF: " + e.getMessage());
        }
    }
}
