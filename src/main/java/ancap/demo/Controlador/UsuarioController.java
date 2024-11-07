package ancap.demo.Controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ancap.demo.Servicio.UsuarioService;
import ancap.demo.Exception.*;
import ancap.demo.Entidad.Usuario;
import ancap.demo.Response.ResponseMessage;
import java.util.List;



@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

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
    public ResponseEntity<Object> autenticarUsuario(@RequestBody Usuario usuario) {
        try {
            usuarioService.autenticarUsuario(usuario.getNombreUsuario(), usuario.getContrasenia());
            // Retorna un objeto con un campo 'success' y el mensaje de autenticación
            return ResponseEntity.ok(new ResponseMessage(true, "Usuario autenticado"));
        } catch (SolicitudException exception) {
            // Retorna un objeto con 'success' como false y el mensaje de error
            return ResponseEntity.status(exception.getStatus()).body(new ResponseMessage(false, exception.getMessage()));
        }
    }
}
    
  

