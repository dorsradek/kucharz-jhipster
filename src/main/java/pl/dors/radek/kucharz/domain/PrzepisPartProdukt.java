package pl.dors.radek.kucharz.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PrzepisPartProdukt.
 */
@Entity
@Table(name = "przepis_part_produkt")
public class PrzepisPartProdukt implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "quantity")
    private Double quantity;

    @ManyToOne
    private Produkt produkt;

    @ManyToOne
    @JsonIgnore
    private PrzepisPart przepisPart;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Produkt getProdukt() {
        return produkt;
    }

    public void setProdukt(Produkt produkt) {
        this.produkt = produkt;
    }

    public PrzepisPart getPrzepisPart() {
        return przepisPart;
    }

    public void setPrzepisPart(PrzepisPart przepisPart) {
        this.przepisPart = przepisPart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PrzepisPartProdukt przepisPartProdukt = (PrzepisPartProdukt) o;

        return Objects.equals(id, przepisPartProdukt.id);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PrzepisPartProdukt{" +
            "id=" + id +
            ", quantity='" + quantity + "'" +
            '}';
    }
}
