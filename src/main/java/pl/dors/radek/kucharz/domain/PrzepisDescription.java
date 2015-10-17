package pl.dors.radek.kucharz.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PrzepisDescription.
 */
@Entity
@Table(name = "przepis_description")
public class PrzepisDescription implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column(name = "title")
    private String title;

    @Column(name = "text")
    private String text;

    @ManyToOne
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

        PrzepisDescription przepisDescription = (PrzepisDescription) o;

        return Objects.equals(id, przepisDescription.id);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PrzepisDescription{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", text='" + text + "'" +
            '}';
    }
}
