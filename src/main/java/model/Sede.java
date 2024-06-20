//CLASE SEDE -- POJO SEDE
package model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table (name="sede")
public class Sede {

    @Id
    @GeneratedValue (strategy=GenerationType.IDENTITY)
    @Column (name="id_sede")
    private Integer idSede;
    @Column (name="nom_sede")
    private String nomSede;


    @OneToMany(mappedBy="sedes", cascade = CascadeType.ALL)
    private List<Departamento> departamentos;

    public Sede() {
    }

    public Sede(String nomSede) {
        this.nomSede = nomSede;
    }

    public Integer getIdSede() {
        return idSede;
    }

    public void setIdSede(Integer idSede) {
        this.idSede = idSede;
    }

    public String getNomSede() {
        return nomSede;
    }

    public void setNomSede(String nomSede) {
        this.nomSede = nomSede;
    }

    public List<Departamento> getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(List<Departamento> departamentos) {
        this.departamentos = departamentos;
    }
}
