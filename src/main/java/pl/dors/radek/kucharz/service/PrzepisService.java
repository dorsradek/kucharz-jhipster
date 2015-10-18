package pl.dors.radek.kucharz.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dors.radek.kucharz.domain.Przepis;
import pl.dors.radek.kucharz.domain.PrzepisDescription;
import pl.dors.radek.kucharz.domain.PrzepisProdukt;
import pl.dors.radek.kucharz.repository.PrzepisDescriptionRepository;
import pl.dors.radek.kucharz.repository.PrzepisProduktRepository;
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
    private PrzepisDescriptionRepository przepisDescriptionRepository;

    @Inject
    private PrzepisProduktRepository przepisProduktRepository;

    public Optional<Przepis> getPrzepis(Long id) {
        log.debug("REST request to get Przepis : {}", id);
        return Optional.ofNullable(przepisRepository.findOne(id))
            .map(przepis -> {
                Set<PrzepisDescription> przepisDescriptions = przepisDescriptionRepository.findAllByPrzepisId(przepis.getId());
                przepis.setPrzepisDescriptions(przepisDescriptions);
                Set<PrzepisProdukt> przepisProdukts = przepisProduktRepository.findAllByPrzepisId(przepis.getId());
                przepis.setPrzepisProdukts(przepisProdukts);
                return przepis;
            });
    }

    public void delete(Long id) {
        log.debug("REST request to get Przepis : {}", id);
        Optional.ofNullable(przepisRepository.findOne(id))
            .map(przepis -> {
                przepisDescriptionRepository.deleteByPrzepisId(przepis.getId());
                przepisProduktRepository.deleteByPrzepisId(przepis.getId());
                przepisRepository.delete(przepis.getId());
                return przepis;
            });
    }

}
