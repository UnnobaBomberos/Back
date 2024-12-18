package ancap.demo.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.lang.NonNull;  // Asegúrate de importar esta anotación

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        // Aquí se definen los orígenes permitidos, puedes agregar más según necesites
        registry.addMapping("/**")  // Aplica a todos los endpoints
                .allowedOrigins("http://localhost:3000", "http://10.0.2.2")  // Los orígenes permitidos
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Métodos permitidos
                .allowedHeaders("*")  // Permitir todos los encabezados
                .allowCredentials(true);  // Permite el envío de cookies si es necesario
    }
    @Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/files/**")
                .addResourceLocations("file:/C:/uploaded_files/");  // Esta es la carpeta donde están los archivos
    }
}
