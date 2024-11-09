package ancap.demo.Repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import ancap.demo.Entidad.Marca;

public interface MarcaRepository extends JpaRepository<Marca, Long> {
    Marca findByNombreContainingIgnoreCase(String nombre);
}

