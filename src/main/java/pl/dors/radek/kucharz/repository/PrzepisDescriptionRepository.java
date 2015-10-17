package pl.dors.radek.kucharz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dors.radek.kucharz.domain.PrzepisDescription;

/**
 * Spring Data JPA repository for the PrzepisDescription entity.
 */
public interface PrzepisDescriptionRepository extends JpaRepository<PrzepisDescription, Long> {

}
