package pl.dors.radek.kucharz.web.rest.dto;

import org.joda.time.DateTime;
import pl.dors.radek.kucharz.domain.KategoriaPrzepisu;
import pl.dors.radek.kucharz.domain.PracochlonnoscPrzepisu;
import pl.dors.radek.kucharz.domain.PrzepisDescription;
import pl.dors.radek.kucharz.domain.PrzepisPartProdukt;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by rdors on 2015-10-24.
 */
public class PrzepisDetailDTO {

    private Long id;
    private String duration;
    private DateTime creationDate;
    private DateTime modificationDate;
    private String name;
    private KategoriaPrzepisu kategoriaPrzepisu;
    private PracochlonnoscPrzepisu pracochlonnoscPrzepisu;
    private Set<PrzepisPartProdukt> przepisPartProdukts = new HashSet<>();
    private Set<PrzepisDescription> przepisDescriptions = new HashSet<>();
    private String image;

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

    public Set<PrzepisPartProdukt> getPrzepisPartProdukts() {
        return przepisPartProdukts;
    }

    public void setPrzepisPartProdukts(Set<PrzepisPartProdukt> przepisPartProdukts) {
        this.przepisPartProdukts = przepisPartProdukts;
    }

    public Set<PrzepisDescription> getPrzepisDescriptions() {
        return przepisDescriptions;
    }

    public void setPrzepisDescriptions(Set<PrzepisDescription> przepisDescriptions) {
        this.przepisDescriptions = przepisDescriptions;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    @Override
    public String toString() {
        return "PrzepisDetailDTO{" +
            "id=" + id +
            ", duration='" + duration + "'" +
            ", creationDate='" + creationDate + "'" +
            ", modificationDate='" + modificationDate + "'" +
            ", name='" + name + "'" +
            '}';
    }

}
