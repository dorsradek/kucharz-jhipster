package pl.dors.radek.kucharz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dors.radek.kucharz.domain.MiaraProduktu;

/**
 * Spring Data JPA repository for the MiaraProduktu entity.
 */
public interface MiaraProduktuRepository extends JpaRepository<MiaraProduktu, Long> {

}
