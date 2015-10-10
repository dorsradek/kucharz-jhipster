package pl.dors.radek.kucharz.repository;

import pl.dors.radek.kucharz.domain.PracochlonnoscPrzepisu;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PracochlonnoscPrzepisu entity.
 */
public interface PracochlonnoscPrzepisuRepository extends JpaRepository<PracochlonnoscPrzepisu,Long> {

}
