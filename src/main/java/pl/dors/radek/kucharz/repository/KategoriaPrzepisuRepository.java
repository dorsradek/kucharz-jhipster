package pl.dors.radek.kucharz.repository;

import pl.dors.radek.kucharz.domain.KategoriaPrzepisu;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the KategoriaPrzepisu entity.
 */
public interface KategoriaPrzepisuRepository extends JpaRepository<KategoriaPrzepisu,Long> {

}
