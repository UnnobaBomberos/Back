package ancap.demo.Repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import ancap.demo.Entidad.Modelo;


public interface ModeloRepository extends JpaRepository<Modelo, Long> {
    Modelo findByNombre(String nombre);
    List<Modelo> findByMarcaId(Long marcaId);
}

