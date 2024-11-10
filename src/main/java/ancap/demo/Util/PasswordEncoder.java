package ancap.demo.Util;

import com.password4j.Password;

public class PasswordEncoder {

        //Password.hash(rawPassword) tomamos la contraseña en texto plano
        //encriptamos la contraseña del usuario con un algoritmo Bcrypt que genera el hash y
        // lo mostramos con getResult()
        public String encode(String rawPassword){
            return Password.hash(rawPassword).withBcrypt().getResult(); 
        }
        
        public boolean verify(String rawPassword,String encodedPassword){
            //checkea si la contraseña rawPassword es igual a la encriptada encodedPassword.
            return Password.check(rawPassword, encodedPassword).withBcrypt();
        }
    
}
