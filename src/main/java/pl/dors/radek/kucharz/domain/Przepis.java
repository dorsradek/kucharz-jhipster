package pl.dors.radek.kucharz.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import pl.dors.radek.kucharz.domain.util.CustomDateTimeDeserializer;
import pl.dors.radek.kucharz.domain.util.CustomDateTimeSerializer;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Przepis.
 */
@Entity
@Table(name = "przepis")
public class Przepis implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    
    @Column(name = "duration")
    private String duration;
    
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "creation_date")
    private DateTime creationDate;
    
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "modification_date")
    private DateTime modificationDate;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "description")
    private String description;

    @ManyToOne
    private KategoriaPrzepisu kategoriaPrzepisu;

    @ManyToOne
    private PracochlonnoscPrzepisu pracochlonnoscPrzepisu;

    @OneToMany(mappedBy = "przepis")
    @JsonIgnore
    private Set<PrzepisProdukt> przepisProdukts = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public DateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    public DateTime getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(DateTime modificationDate) {
        this.modificationDate = modificationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public KategoriaPrzepisu getKategoriaPrzepisu() {
        return kategoriaPrzepisu;
    }

    public void setKategoriaPrzepisu(KategoriaPrzepisu kategoriaPrzepisu) {
        this.kategoriaPrzepisu = kategoriaPrzepisu;
    }

    public PracochlonnoscPrzepisu getPracochlonnoscPrzepisu() {
        return pracochlonnoscPrzepisu;
    }

    public void setPracochlonnoscPrzepisu(PracochlonnoscPrzepisu pracochlonnoscPrzepisu) {
        this.pracochlonnoscPrzepisu = pracochlonnoscPrzepisu;
    }

    public Set<PrzepisProdukt> getPrzepisProdukts() {
        return przepisProdukts;
    }

    public void setPrzepisProdukts(Set<PrzepisProdukt> przepisProdukts) {
        this.przepisProdukts = przepisProdukts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Przepis przepis = (Przepis) o;

        if ( ! Objects.equals(id, przepis.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Przepis{" +
                "id=" + id +
                ", duration='" + duration + "'" +
                ", creationDate='" + creationDate + "'" +
                ", modificationDate='" + modificationDate + "'" +
                ", name='" + name + "'" +
                ", description='" + description + "'" +
                '}';
    }
}
