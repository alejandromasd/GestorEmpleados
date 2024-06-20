//CLASE Departamento -- POJO Departamento
package model;

import javax.persistence.*;

@Entity //HIbernate transforma las clases en entidades para poder realizar el mapeo.
@Table (name="departamento") //Indicamos esta entidad a qué tabla estará relacionada
public class Departamento {
    //Creamos Propiedades
    @Id //Indicamos que ID es clave primaria
    @GeneratedValue (strategy=GenerationType.IDENTITY)
    @Column (name="id_depto") //De esta forma mapeamos la columna con la propiedad
    private Integer idDepto;
    @Column (name="nom_depto")
    private String nomDepto;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id_sede")
    private Sede sedes;

    public Departamento() {
    }

    public Departamento(String nomDepto) {
        super();
        this.nomDepto = nomDepto;
    }

    public String getNomDepto() {
        return nomDepto;
    }

    public void setNomDepto(String nomDepto) {
        this.nomDepto = nomDepto;
    }

    public Sede getSedes() {
        return sedes;
    }

    public void setSedes(Sede sedes) {
        this.sedes = sedes;
    }

    public Integer getIdDepto() {
        return idDepto;
    }
}

