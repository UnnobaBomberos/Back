package ancap.demo.Servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ancap.demo.Entidad.Usuario;
import ancap.demo.Exception.*;
import ancap.demo.Repositorio.UsuarioRepository;
import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    public List<Usuario> listarUsuario(){
        return usuarioRepository.findAll();
    }
    public Usuario obtenerUsuario(Long id){
        return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuariono encontrado"));
    }
    public Usuario crearUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }
    public void eliminarUsuario(Long id){
        Usuario usuario= usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuarioRepository.delete(usuario);
    }
    public void autenticarUsuario(String nombreUsuario, String contrasenia) throws SolicitudException {
        Usuario usuario = usuarioRepository.findByNombreUsuario(nombreUsuario);
        if (usuario == null) {
            throw new SolicitudException("Usuario no encontrado", 404);
        }
    
        // Compara la contraseña de la base de datos con la enviada en la solicitud
        if (!usuario.getContrasenia().equals(contrasenia)) {
            throw new SolicitudException("Contraseña incorrecta", 401);
        }
    }
    
}