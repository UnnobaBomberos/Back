package ancap.demo.Entidad;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
@Entity
@Table(name = "marcas")
public class Marca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre_marca")
    private String nombre_marca;
    @Column(name="logo")
    private String logoUrl; // Nuevo campo para la URL del logo
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre_marca() {
        return nombre_marca;
    }
    public void setNombre_marca(String nombre_marca) {
        this.nombre_marca = nombre_marca;
    }
    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}
