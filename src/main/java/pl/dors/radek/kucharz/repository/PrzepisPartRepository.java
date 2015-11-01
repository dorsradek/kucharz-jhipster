package pl.dors.radek.kucharz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dors.radek.kucharz.domain.PrzepisPart;

import java.util.Set;

/**
 * Spring Data JPA repository for the PrzepisPartRepository entity.
 */
public interface PrzepisPartRepository extends JpaRepository<PrzepisPart, Long> {

    Set<PrzepisPart> findAllByPrzepisId(Long przepisId);

    Long deleteByPrzepisId(Long przepisId);

}
