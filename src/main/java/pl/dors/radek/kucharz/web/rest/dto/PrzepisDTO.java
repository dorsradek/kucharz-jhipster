package pl.dors.radek.kucharz.web.rest.dto;

/**
 * Created by rdors on 2015-10-24.
 */
public class PrzepisDTO {

    private Long id;
    private String name;
    private String image;
    private String pracochlonnosc;
    private String duration;
    private String category;


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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPracochlonnosc() {
        return pracochlonnosc;
    }

    public void setPracochlonnosc(String pracochlonnosc) {
        this.pracochlonnosc = pracochlonnosc;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
