package pl.dors.radek.kucharz.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A RodzajProduktu.
 */
@Entity
@Table(name = "rodzaj_produktu")
public class RodzajProduktu implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "rodzajProduktu")
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

        RodzajProduktu rodzajProduktu = (RodzajProduktu) o;

        if ( ! Objects.equals(id, rodzajProduktu.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RodzajProduktu{" +
                "id=" + id +
                ", name='" + name + "'" +
                '}';
    }
}
