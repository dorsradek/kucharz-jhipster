package pl.dors.radek.kucharz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dors.radek.kucharz.domain.PrzepisProdukt;

import java.util.Set;

/**
 * Spring Data JPA repository for the PrzepisProdukt entity.
 */
public interface PrzepisProduktRepository extends JpaRepository<PrzepisProdukt, Long> {

    Set<PrzepisProdukt> findAllByPrzepisId(Long przepisId);

}
