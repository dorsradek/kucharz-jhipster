package pl.dors.radek.kucharz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dors.radek.kucharz.domain.PrzepisPartProdukt;

import java.util.Set;

/**
 * Spring Data JPA repository for the PrzepisPartProdukt entity.
 */
public interface PrzepisProduktRepository extends JpaRepository<PrzepisPartProdukt, Long> {

    Set<PrzepisPartProdukt> findAllByPrzepisId(Long przepisId);

    Long deleteByPrzepisId(Long przepisId);

}
