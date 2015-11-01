package pl.dors.radek.kucharz.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A PrzepisDescription.
 */
@Entity
@Table(name = "przepis_part")
public class PrzepisPart implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "przepisPart")
    private Set<PrzepisPartProdukt> przepisPartProdukts = new HashSet<>();

    @ManyToOne
    @JsonIgnore
    private Przepis przepis;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<PrzepisPartProdukt> getPrzepisPartProdukts() {
        return przepisPartProdukts;
    }

    public void setPrzepisPartProdukts(Set<PrzepisPartProdukt> przepisPartProdukts) {
        this.przepisPartProdukts = przepisPartProdukts;
    }

    public Przepis getPrzepis() {
        return przepis;
    }

    public void setPrzepis(Przepis przepis) {
        this.przepis = przepis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PrzepisPart przepisPart = (PrzepisPart) o;

        return Objects.equals(id, przepisPart.id);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PrzepisPart{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
