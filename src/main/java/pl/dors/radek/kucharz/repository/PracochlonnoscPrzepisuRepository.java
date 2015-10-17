package pl.dors.radek.kucharz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dors.radek.kucharz.domain.PracochlonnoscPrzepisu;

/**
 * Spring Data JPA repository for the PracochlonnoscPrzepisu entity.
 */
public interface PracochlonnoscPrzepisuRepository extends JpaRepository<PracochlonnoscPrzepisu, Long> {

}
