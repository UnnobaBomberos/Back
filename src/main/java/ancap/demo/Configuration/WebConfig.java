package ancap.demo.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.lang.NonNull;  // Asegúrate de importar esta anotación

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        // Aquí se definen los orígenes permitidos, puedes agregar más según necesites
        registry.addMapping("/**")  // Aplica a todos los endpoints
                .allowedOrigins("http://localhost:3000", "http://tudominio.com")  // Los orígenes permitidos
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Métodos permitidos
                .allowedHeaders("*")  // Permitir todos los encabezados
                .allowCredentials(true);  // Permite el envío de cookies si es necesario
    }
}
