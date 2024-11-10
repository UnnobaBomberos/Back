package ancap.demo.Entidad;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
//Transformamos la clase Marca en una entidad
@Entity
//nombre de la table es marcas
@Table(name = "marcas")
public class Marca {
    //Marca va a tener una PK
    @Id
    //Esa PK la va a setear el sistema, y va a ser autoincrementable
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //transformamos el atributo nombre es una columna
    @Column(name = "nombre")
    private String nombre;
    //transformamos logoUrl en columna 
    @Column(name="logo")
    private String logoUrl; // Nuevo campo para la URL del logo
    //setters y getters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}
