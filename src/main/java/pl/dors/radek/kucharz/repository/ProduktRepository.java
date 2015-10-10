package pl.dors.radek.kucharz.repository;

import pl.dors.radek.kucharz.domain.Produkt;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Produkt entity.
 */
public interface ProduktRepository extends JpaRepository<Produkt,Long> {

}
