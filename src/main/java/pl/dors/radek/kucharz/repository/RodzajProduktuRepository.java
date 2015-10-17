package pl.dors.radek.kucharz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dors.radek.kucharz.domain.RodzajProduktu;

/**
 * Spring Data JPA repository for the RodzajProduktu entity.
 */
public interface RodzajProduktuRepository extends JpaRepository<RodzajProduktu, Long> {

}
