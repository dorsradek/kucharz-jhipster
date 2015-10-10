package pl.dors.radek.kucharz.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A PracochlonnoscPrzepisu.
 */
@Entity
@Table(name = "pracochlonnosc_przepisu")
public class PracochlonnoscPrzepisu implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "pracochlonnoscPrzepisu")
    @JsonIgnore
    private Set<Przepis> przepiss = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Przepis> getPrzepiss() {
        return przepiss;
    }

    public void setPrzepiss(Set<Przepis> przepiss) {
        this.przepiss = przepiss;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PracochlonnoscPrzepisu pracochlonnoscPrzepisu = (PracochlonnoscPrzepisu) o;

        if ( ! Objects.equals(id, pracochlonnoscPrzepisu.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PracochlonnoscPrzepisu{" +
                "id=" + id +
                ", name='" + name + "'" +
                '}';
    }
}
