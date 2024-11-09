package ancap.demo.Controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ancap.demo.Servicio.MarcaService;
import ancap.demo.Entidad.Marca;
import java.util.List;

@RestController
@RequestMapping("/api/marcas")
public class MarcaController {

    @Autowired
    private MarcaService marcaService;

    @GetMapping
    public List<Marca> obtenerTodasLasMarcas() {
        return marcaService.listarMarcas();
    }

    @GetMapping("/{id}")
    public Marca obtenerMarcaPorId(@PathVariable Long id) {
        return marcaService.obtenerMarca(id);
    }

    @PostMapping
    public Marca crearMarca(@RequestBody Marca marca) {
        return marcaService.crearMarca(marca);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Marca> actualizarMarca(@PathVariable Long id, @RequestBody Marca marca) {
        try {
            // Obtener la marca existente por ID
            Marca marcaExistente = marcaService.obtenerMarca(id);
    
            // Actualizar el nombre de la marca si se proporciona
            if (marca.getNombre() != null && !marca.getNombre().isEmpty()) {
                marcaExistente.setNombre(marca.getNombre());
            }
    
            // Actualizar el logo de la marca si se proporciona
            if (marca.getLogoUrl() != null && !marca.getLogoUrl().isEmpty()) {
                marcaExistente.setLogoUrl(marca.getLogoUrl());
            }
    
            // Guardar la marca actualizada
            Marca marcaActualizada = marcaService.actualizarMarca(id, marcaExistente.getNombre(), marcaExistente.getLogoUrl());
    
            return ResponseEntity.ok(marcaActualizada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    

    @DeleteMapping("/{id}")
    public void eliminarMarca(@PathVariable Long id) {
        marcaService.eliminarMarca(id);
    }
    @DeleteMapping("/vaciar")
    public void vaciarMarcas() {
        marcaService.vaciarMarcas();    
    }
    // Método para buscar marcas por nombre
    @GetMapping("/buscar/{nombre}")
    public Marca obtenerMarcaPorNombre(@PathVariable String nombre) {
        return marcaService.buscarMarcasPorNombre_marca(nombre);
    }
      
}
