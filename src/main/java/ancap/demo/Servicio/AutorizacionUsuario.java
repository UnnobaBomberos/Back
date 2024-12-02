package ancap.demo.Servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.auth0.jwt.exceptions.JWTVerificationException;
import ancap.demo.Entidad.Usuario;
import ancap.demo.Exception.SolicitudException;
import ancap.demo.Util.JwtToken;

@Service
public class AutorizacionUsuario{
    
    @Autowired
    JwtToken jwtTokenUtil;

    @Autowired
    UsuarioService userService;
    
    public Usuario authorize(String token) throws SolicitudException {
        try {
            jwtTokenUtil.verify(token);
            String subject = jwtTokenUtil.getSubject(token);
            Usuario usuario = userService.findByNombreUsuario(subject);
            if (usuario == null) {
                throw new SolicitudException("Usuario no encontrado", 404);
            }
            return usuario;

        } catch (JWTVerificationException jwtVerificationException) {
            throw new JWTVerificationException("Token inválido", jwtVerificationException);
        }
    }
}