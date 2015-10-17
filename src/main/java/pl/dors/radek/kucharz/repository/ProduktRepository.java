package pl.dors.radek.kucharz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dors.radek.kucharz.domain.Produkt;

/**
 * Spring Data JPA repository for the Produkt entity.
 */
public interface ProduktRepository extends JpaRepository<Produkt, Long> {

}
