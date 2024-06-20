package model;
import javax.persistence.*;

@Entity
@Table(name="empleado_datos_prof")
public class EmpleadoDatosProf {

    @Id
    @Column(name="dni")
    private String dni;

    @ManyToOne
    @JoinColumn(name="categoria")
    private Categoria categoria;

    @Column(name="sueldo_bruto_anual")
    private double sueldoBrutoAnual;

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public double getSueldoBrutoAnual() {
        return sueldoBrutoAnual;
    }

    public void setSueldoBrutoAnual(double sueldoBrutoAnual) {
        this.sueldoBrutoAnual = sueldoBrutoAnual;
    }
}
