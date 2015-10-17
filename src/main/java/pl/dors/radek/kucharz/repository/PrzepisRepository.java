package pl.dors.radek.kucharz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dors.radek.kucharz.domain.Przepis;

/**
 * Spring Data JPA repository for the Przepis entity.
 */
public interface PrzepisRepository extends JpaRepository<Przepis, Long> {

}
