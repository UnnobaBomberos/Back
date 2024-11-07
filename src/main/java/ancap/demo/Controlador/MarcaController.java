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
    public ResponseEntity<Marca> actualizarMarca(@PathVariable Long id,
                                                @RequestParam String nombre_marca,
                                                @RequestParam String logoUrl) {
        try {
            // Actualizar la marca con el nombre y la URL del logo
            Marca marcaActualizada = marcaService.actualizarMarca(id, nombre_marca, logoUrl);
            return ResponseEntity.ok(marcaActualizada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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
}
