package pl.dors.radek.kucharz.repository;

import pl.dors.radek.kucharz.domain.Przepis;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Przepis entity.
 */
public interface PrzepisRepository extends JpaRepository<Przepis,Long> {

}
