package pl.dors.radek.kucharz.repository;

import pl.dors.radek.kucharz.domain.RodzajProduktu;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the RodzajProduktu entity.
 */
public interface RodzajProduktuRepository extends JpaRepository<RodzajProduktu,Long> {

}
