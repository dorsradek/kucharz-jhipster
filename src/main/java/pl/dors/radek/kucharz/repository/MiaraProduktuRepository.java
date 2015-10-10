package pl.dors.radek.kucharz.repository;

import pl.dors.radek.kucharz.domain.MiaraProduktu;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the MiaraProduktu entity.
 */
public interface MiaraProduktuRepository extends JpaRepository<MiaraProduktu,Long> {

}
