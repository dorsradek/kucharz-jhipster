package pl.dors.radek.kucharz.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A MiaraProduktu.
 */
@Entity
@Table(name = "miara_produktu")
public class MiaraProduktu implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column(name = "name")
    private String name;

    @Column(name = "shortcut")
    private String shortcut;

    @OneToMany(mappedBy = "miaraProduktu")
    @JsonIgnore
    private Set<Produkt> produkts = new HashSet<>();

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

    public String getShortcut() {
        return shortcut;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    public Set<Produkt> getProdukts() {
        return produkts;
    }

    public void setProdukts(Set<Produkt> produkts) {
        this.produkts = produkts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MiaraProduktu miaraProduktu = (MiaraProduktu) o;

        if (!Objects.equals(id, miaraProduktu.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MiaraProduktu{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", shortcut='" + shortcut + "'" +
            '}';
    }
}
