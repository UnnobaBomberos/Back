package ancap.demo.Entidad;

import jakarta.persistence.Id;
import jakarta.persistence.Lob;
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

    @Column(name = "nombre_modelo")  // Mapeo del nombre de la columna en la base de datos
    private String nombre_modelo;

    @Column(name = "año")  // Mapeo explícito (aunque no es necesario en este caso, por claridad)
    private int año;

    @Lob
    @Column(name = "image_res")  // Mapeo de la columna image_res en la base de datos
    private byte[] imageRes;

    @Lob
    @Column(name = "pdf")  // Mapeo de la columna pdf en la base de datos
    private byte[] pdf;

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

    public String getNombre_modelo() {
        return nombre_modelo;
    }

    public void setNombre_modelo(String nombre_modelo) {
        this.nombre_modelo = nombre_modelo;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public byte[] getImageRes() {
        return imageRes;
    }

    public void setImageRes(byte[] imageRes) {
        this.imageRes = imageRes;
    }

    public byte[] getPdf() {
        return pdf;
    }

    public void setPdf(byte[] pdf) {
        this.pdf = pdf;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }
}
