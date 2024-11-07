package ancap.demo.Servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ancap.demo.Entidad.Modelo;
import ancap.demo.Repositorio.ModeloRepository;

import java.io.IOException;
import java.util.List;

@Service
public class ModeloService {

    @Autowired
    private ModeloRepository modeloRepository;

    // Subir una imagen
    public void subirImagen(Long id, MultipartFile image) throws IOException {
        Modelo modelo = modeloRepository.findById(id).orElseThrow(() -> new RuntimeException("Modelo no encontrado"));
        modelo.setImageRes(image.getBytes());  // Guardamos el archivo como un byte array
        modeloRepository.save(modelo);
    }

    // Subir un PDF
    public void subirPdf(Long id, MultipartFile pdf) throws IOException {
        Modelo modelo = modeloRepository.findById(id).orElseThrow(() -> new RuntimeException("Modelo no encontrado"));
        modelo.setPdf(pdf.getBytes());  // Guardamos el archivo como un byte array
        modeloRepository.save(modelo);
    }

    // Obtener la imagen de un modelo
    public byte[] obtenerImagen(Long id) throws IOException {
        Modelo modelo = modeloRepository.findById(id).orElseThrow(() -> new RuntimeException("Modelo no encontrado"));
        return modelo.getImageRes();
    }

    // Obtener el PDF de un modelo
    public byte[] obtenerPdf(Long id) throws IOException {
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
        modelo.setNombre_modelo(modeloDetalles.getNombre_modelo());
        modelo.setAño(modeloDetalles.getAño());
        return modeloRepository.save(modelo);
    }

    public void eliminarModelo(Long id) {
        Modelo modelo = modeloRepository.findById(id).orElseThrow(() -> new RuntimeException("Modelo no encontrado"));
        modeloRepository.delete(modelo);
    }
}
