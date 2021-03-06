package pl.dors.radek.kucharz.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Produkt.
 */
@Entity
@Table(name = "produkt")
public class Produkt implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column(name = "name")
    private String name;

    @ManyToOne
    private MiaraProduktu miaraProduktu;

    @ManyToOne
    private RodzajProduktu rodzajProduktu;

    @OneToMany(mappedBy = "produkt")
    @JsonIgnore
    private Set<PrzepisPartProdukt> przepisPartProdukts = new HashSet<>();

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

    public MiaraProduktu getMiaraProduktu() {
        return miaraProduktu;
    }

    public void setMiaraProduktu(MiaraProduktu miaraProduktu) {
        this.miaraProduktu = miaraProduktu;
    }

    public RodzajProduktu getRodzajProduktu() {
        return rodzajProduktu;
    }

    public void setRodzajProduktu(RodzajProduktu rodzajProduktu) {
        this.rodzajProduktu = rodzajProduktu;
    }

    public Set<PrzepisPartProdukt> getPrzepisPartProdukts() {
        return przepisPartProdukts;
    }

    public void setPrzepisPartProdukts(Set<PrzepisPartProdukt> przepisPartProdukts) {
        this.przepisPartProdukts = przepisPartProdukts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Produkt produkt = (Produkt) o;

        return Objects.equals(id, produkt.id);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Produkt{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
