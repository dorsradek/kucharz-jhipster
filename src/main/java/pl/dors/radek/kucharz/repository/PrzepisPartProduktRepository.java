package pl.dors.radek.kucharz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dors.radek.kucharz.domain.PrzepisPartProdukt;

import java.util.Set;

/**
 * Spring Data JPA repository for the PrzepisPartProdukt entity.
 */
public interface PrzepisPartProduktRepository extends JpaRepository<PrzepisPartProdukt, Long> {

    Set<PrzepisPartProdukt> findAllByPrzepisPartId(Long przepisId);

    Long deleteByPrzepisPartId(Long przepisId);

}
