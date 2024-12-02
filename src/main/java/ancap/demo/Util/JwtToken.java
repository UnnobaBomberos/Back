package ancap.demo.Util;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JwtToken {
    private static final String TOKEN_SECRET = "SRFFewe3223423t65ndfdsfdsg";
    private static final Algorithm algorithm = Algorithm.HMAC512(TOKEN_SECRET);
    private String subject;
    
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    public String generateToken(String subject){
        try {
            // Configura el tiempo de expiración a 10 días a partir de la fecha actual
            Date expirar = new Date(System.currentTimeMillis() + 864000000); 
            setSubject(subject);
            // Genera el token JWT
            String token = JWT.create()
                .withIssuer(subject) // Configura el subject como issuer
                .withExpiresAt(expirar)
                .sign(algorithm);
            return "Bearer " + token;
        } catch (JWTCreationException exception) {
            return exception.getMessage();
        }
    }
    
    //verifica si el token es correcto
    public boolean verify(String token){
        try {
            // Configura el verificador con el algoritmo HMAC 512 y el mismo issuer
            JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(getSubject()) // Reemplazar "alejinho" con el username configurado en generateToken
                .build();
            verifier.verify(token.replace("Bearer ", "")); // Remueve "Bearer " del inicio
            return true;
        } catch (JWTVerificationException jwtVerificationException) {
            return false;
        }
    }
    
    //devuelve el nombre del usuario que posee el token
    public String getSubject(String token){
        try {
            // Decodifica el token para obtener el subject
            DecodedJWT decodedJWT = JWT.decode(token.replace("Bearer ", ""));
            return decodedJWT.getIssuer(); // Retorna el username como subject
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

}