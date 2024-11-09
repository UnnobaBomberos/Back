package ancap.demo.Servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ancap.demo.Entidad.Marca;
import ancap.demo.Repositorio.MarcaRepository;
import java.util.List;

@Service
public class MarcaService {
    @Autowired
    private MarcaRepository marcaRepository;

    public List<Marca> listarMarcas() {
        return marcaRepository.findAll();
    }

    public Marca obtenerMarca(Long id) {
        return marcaRepository.findById(id).orElse(null);
    }

    public Marca crearMarca(Marca marca) {
        return marcaRepository.save(marca);
    }
    public Marca actualizarMarca(Long marcaId, String nombre_marca, String logoUrl) {
        Marca marca = obtenerMarca(marcaId);
        if (marca != null) {
            // Actualizar el nombre de la marca
            if (nombre_marca != null) {
                marca.setNombre(nombre_marca);
            }
    
            // Actualizar la URL del logo con la proporcionada
            if (logoUrl != null && !logoUrl.isEmpty()) {
                marca.setLogoUrl(logoUrl); // Asignar la URL del logo proporcionada
            }
    
            return marcaRepository.save(marca);
        }
        return null;
    }    
    public void vaciarMarcas() {
        marcaRepository.deleteAll();
    }    
    public void eliminarMarca(Long id) {
        marcaRepository.deleteById(id);
    }
    public Marca buscarMarcasPorNombre_marca(String nombre) {
        return marcaRepository.findByNombreContainingIgnoreCase(nombre);
    }
  
}
