package ancap.demo.Servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ancap.demo.Controlador.FileUploadController;
import ancap.demo.Entidad.Modelo;
import ancap.demo.Repositorio.ModeloRepository;

import java.io.IOException;
import java.util.List;

@Service
public class ModeloService {

    @Autowired
    private ModeloRepository modeloRepository;
    

    // Subir una imagen
   // Subir una imagen
    public void subirImagen(Long id, MultipartFile image) throws IOException {
    Modelo modelo = modeloRepository.findById(id).orElseThrow(() -> new RuntimeException("Modelo no encontrado"));
    
    // Usar el controlador de archivos para cargar la imagen
    String imagePath = uploadFile(image);  // Esta es la URL generada por el controlador de carga
    
    // Guarda la dirección (ruta) en lugar de los bytes
    modelo.setImageRes(imagePath);  
    modeloRepository.save(modelo);
}

// Subir un PDF
public void subirPdf(Long id, MultipartFile pdf) throws IOException {
    Modelo modelo = modeloRepository.findById(id).orElseThrow(() -> new RuntimeException("Modelo no encontrado"));
    
    // Usar el controlador de archivos para cargar el PDF
    String pdfPath = uploadFile(pdf);  // Esta es la URL generada por el controlador de carga
    
    // Guarda la dirección (ruta) en lugar de los bytes
    modelo.setPdf(pdfPath);  
    modeloRepository.save(modelo);
}

// Método para cargar el archivo utilizando el controlador
private String uploadFile(MultipartFile file) throws IOException {
    FileUploadController fileUploadController = new FileUploadController(); // Crear instancia del controlador

    // Llamar al método del controlador que sube el archivo y obtener la URL
    ResponseEntity<String> response = fileUploadController.uploadFile(file);
    
    // Verificar si la respuesta es exitosa y devolver la URL
    if (response.getStatusCode().is2xxSuccessful()) {
        return response.getBody();
    } else {
        throw new RuntimeException("Error al cargar el archivo: " + response.getBody());
    }
}

    // Obtener la imagen de un modelo
    public String obtenerImagen(Long id) throws IOException {
        Modelo modelo = modeloRepository.findById(id).orElseThrow(() -> new RuntimeException("Modelo no encontrado"));
        return modelo.getImageRes();
    }

    // Obtener el PDF de un modelo
    public String obtenerPdf(Long id) throws IOException {
        Modelo modelo = modeloRepository.findById(id).orElseThrow(() -> new RuntimeException("Modelo no encontrado"));
        return modelo.getPdf();
    }

    // Otros métodos CRUD
    public List<Modelo> listarModelos() {
        return modeloRepository.findAll();
    }

    public Modelo obtenerModelo(Long id) {
        return modeloRepository.findById(id).orElseThrow(() -> new RuntimeException("Modelo no encontrado"));
    }

    public Modelo crearModelo(Modelo modelo) {
        return modeloRepository.save(modelo);
    }

    public Modelo actualizarModelo(Long id, Modelo modeloDetalles) {
        Modelo modelo = modeloRepository.findById(id).orElseThrow(() -> new RuntimeException("Modelo no encontrado"));
        
        // Actualizar los detalles del modelo
        modelo.setNombre(modeloDetalles.getNombre());
        modelo.setAño(modeloDetalles.getAño());
        modelo.setMarca(modeloDetalles.getMarca()); // Actualiza la marca si es necesario
        modelo.setImageRes(modeloDetalles.getImageRes()); // Actualiza la imagen si es necesario
        modelo.setPdf(modeloDetalles.getPdf()); // Actualiza el PDF si es necesario
        
        return modeloRepository.save(modelo); // Guarda el modelo actualizado
    }
    
    public void eliminarModelo(Long id) {
        Modelo modelo = modeloRepository.findById(id).orElseThrow(() -> new RuntimeException("Modelo no encontrado"));
        modeloRepository.delete(modelo);
    }
    public void actualizarRutaImagen(Long id, String rutaImagen) {
        Modelo modelo = obtenerModelo(id);
        modelo.setImageRes(rutaImagen);  // Guarda la URL de la imagen en la base de datos
        modeloRepository.save(modelo);
    }
    
    public void actualizarRutaPdf(Long id, String rutaPdf) {
        Modelo modelo = obtenerModelo(id);
        modelo.setPdf(rutaPdf);  // Guarda la URL del PDF en la base de datos
        modeloRepository.save(modelo);
    }
     // Obtener un modelo por su nombre
     public Modelo obtenerModeloPorNombre(String nombre) {
        return modeloRepository.findByNombre(nombre); // Asumiendo que tienes un método en el repositorio para esto
    }

    // Actualizar el modelo
    public Modelo actualizarModelo(Modelo modelo) {
        return modeloRepository.save(modelo);
    }
    public Modelo buscarModeloPorNombre (String nombre){
        return modeloRepository.findByNombre(nombre);
    }
    
}