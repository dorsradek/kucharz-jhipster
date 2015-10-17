package pl.dors.radek.kucharz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dors.radek.kucharz.domain.KategoriaPrzepisu;

/**
 * Spring Data JPA repository for the KategoriaPrzepisu entity.
 */
public interface KategoriaPrzepisuRepository extends JpaRepository<KategoriaPrzepisu, Long> {

}
