package ancap.demo.Repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import ancap.demo.Entidad.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByNombreUsuario(String nombreUsuario); // Buscar usuario por nombreUsuario
}
