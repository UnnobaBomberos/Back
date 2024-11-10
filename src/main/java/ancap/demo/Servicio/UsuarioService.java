package ancap.demo.Servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.stereotype.Service;
import ancap.demo.Entidad.Usuario;
import ancap.demo.Exception.*;
import ancap.demo.Repositorio.UsuarioRepository;
import ancap.demo.Util.JwtToken;
import ancap.demo.Util.PasswordEncoder;

import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    JwtToken jwtTokenUtil;
    public List<Usuario> listarUsuario(){
        return usuarioRepository.findAll();
    }
    public Usuario obtenerUsuario(Long id){
        return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuariono encontrado"));
    }
    public Usuario crearUsuario(Usuario usuario){
        //encripto la contraseña en un atributo
        String contraseniaEncriptada = passwordEncoder.encode(usuario.getContrasenia());
        //la seteo en el usuario
        usuario.setContrasenia(contraseniaEncriptada);
        //si el usuario ya esta creado, levanto excepcion antes.
        obtenerUsuario(usuario.getId());
        //si el usuario no se encontro se guarda el usuario
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
        // Generar el token JWT si la autenticación es exitosa
        jwtTokenUtil.generateToken(usuario.getNombreUsuario()); 
    }

    public Usuario findByNombreUsuario(String nombreUsuario){
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }
    
}