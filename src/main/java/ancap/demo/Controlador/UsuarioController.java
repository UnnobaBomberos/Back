package ancap.demo.Controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ancap.demo.Servicio.UsuarioService;
import ancap.demo.Util.PasswordEncoder;
import ancap.demo.Repositorio.UsuarioRepository;
import ancap.demo.Entidad.Usuario;
import java.util.List;
import java.util.Map;



@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public List<Usuario> obtenerTodosLosUsuarios(){
        return usuarioService.listarUsuario();
    }
    // Obtener un usuario por su id
    @GetMapping("/{id}")
    public Usuario obtenerUsuarioPorId(@PathVariable Long id){
        return usuarioService.obtenerUsuario(id);
    }
    // Crear un nuevo usuario
    @PostMapping
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        // Asegúrate de que el usuario tenga un nombreUsuario no nulo antes de guardarlo
        if (usuario.getNombreUsuario() == null || usuario.getNombreUsuario().isEmpty()) {
            throw new RuntimeException("El nombre de usuario no puede estar vacío");
        }
        return usuarioService.crearUsuario(usuario);
    }    
    // Eliminar un modelo
    @DeleteMapping("/{id}")
    public void eliminarUsuario(@PathVariable Long id){
        usuarioService.eliminarUsuario(id);
    }
   @PostMapping("/autenticar")
    public ResponseEntity<?> autenticarUsuario(@RequestBody Usuario loginRequest) {
        String nombreUsuario = loginRequest.getNombreUsuario();
        String contrasenia = loginRequest.getContrasenia();
        Usuario usuario = usuarioRepository.findByNombreUsuario(nombreUsuario);
    if (usuario == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(Map.of("success", false, "message", "Usuario no encontrado"));
    }

    boolean esValida = passwordEncoder.verify(contrasenia, usuario.getContrasenia());

    if (!esValida) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(Map.of("success", false, "message", "Contraseña incorrecta"));
    }

    return ResponseEntity.ok(Map.of("success", true, "message", "Autenticación exitosa"));
}

}
    
  


