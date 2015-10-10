package pl.dors.radek.kucharz.repository;

import pl.dors.radek.kucharz.domain.PrzepisProdukt;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PrzepisProdukt entity.
 */
public interface PrzepisProduktRepository extends JpaRepository<PrzepisProdukt,Long> {

}
