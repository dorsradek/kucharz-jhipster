package pl.dors.radek.kucharz.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dors.radek.kucharz.domain.Przepis;
import pl.dors.radek.kucharz.domain.PrzepisPart;
import pl.dors.radek.kucharz.domain.PrzepisPartProdukt;
import pl.dors.radek.kucharz.repository.PrzepisPartProduktRepository;
import pl.dors.radek.kucharz.repository.PrzepisPartRepository;
import pl.dors.radek.kucharz.repository.PrzepisRepository;

import javax.inject.Inject;
import java.util.Optional;
import java.util.Set;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class PrzepisService {

    private final Logger log = LoggerFactory.getLogger(PrzepisService.class);

    @Inject
    private PrzepisRepository przepisRepository;

    @Inject
    private PrzepisPartRepository przepisPartRepository;

    @Inject
    private PrzepisPartProduktRepository przepisPartProduktRepository;

    public Optional<Przepis> getPrzepis(Long id) {
        log.debug("REST request to get Przepis : {}", id);
        return Optional.ofNullable(przepisRepository.findOne(id))
            .map(przepis -> {
                Set<PrzepisPart> przepisParts = przepisPartRepository.findAllByPrzepisId(przepis.getId());
                przepis.setPrzepisParts(przepisParts);
                for (PrzepisPart przepisPart : przepisParts) {
                    Set<PrzepisPartProdukt> przepisPartProdukts = przepisPartProduktRepository.findAllByPrzepisPartId(przepisPart.getId());
                    przepisPart.setPrzepisPartProdukts(przepisPartProdukts);
                }
                return przepis;
            });
    }

    public void delete(Long id) {
        log.debug("REST request to delete Przepis : {}", id);
        Optional.ofNullable(przepisRepository.findOne(id))
            .map(przepis -> {
                Set<PrzepisPart> przepisPartList = przepisPartRepository.findAllByPrzepisId(przepis.getId());
                for (PrzepisPart przepisPart : przepisPartList) {
                    przepisPartProduktRepository.deleteByPrzepisPartId(przepisPart.getId());
                }
                przepisPartRepository.deleteByPrzepisId(przepis.getId());
                przepisRepository.delete(przepis.getId());
                return przepis;
            });
    }

}
