package ancap.demo.Entidad;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name="modelo")
public class Modelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")  // Mapeo del nombre de la columna en la base de datos
    private String nombre;

    @Column(name = "año")  // Mapeo explícito (aunque no es necesario en este caso, por claridad)
    private int año;

    @Column(name = "image_res")  // Mapeo de la columna image_res en la base de datos
    private String imageRes;


    @Column(name = "pdf")  // Mapeo de la columna pdf en la base de datos
    private String pdf;

    @ManyToOne
    @JoinColumn(name = "id_marca")  // Relación con la tabla marca, usando id_marca como clave foránea
    private Marca marca;  // Esto te permitirá acceder a la marca asociada

    // Getters y Setters
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

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }
    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public String getImageRes() {
        return imageRes;
    }

    public void setImageRes(String imageRes) {
        this.imageRes = imageRes;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }
    
}
