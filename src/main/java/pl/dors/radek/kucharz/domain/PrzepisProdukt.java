package pl.dors.radek.kucharz.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PrzepisProdukt.
 */
@Entity
@Table(name = "przepis_produkt")
public class PrzepisProdukt implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column(name = "quantity")
    private Double quantity;

    @ManyToOne
    private Produkt produkt;

    @ManyToOne
    private Przepis przepis;

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

        PrzepisProdukt przepisProdukt = (PrzepisProdukt) o;

        if (!Objects.equals(id, przepisProdukt.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PrzepisProdukt{" +
            "id=" + id +
            ", quantity='" + quantity + "'" +
            '}';
    }
}
