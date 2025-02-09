package br.com.chain.occupation;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OccupationService {

    private final OccupationRepository occupationRepository;

    public OccupationService(OccupationRepository occupationRepository) {
        this.occupationRepository = occupationRepository;
    }

    public List<Occupation> getAllOccupations() {
        return occupationRepository.findAll();
    }

    public Occupation getOccupationById(Long id) {
        return occupationRepository.findById(id).orElse(null);
    }

    public Occupation createOccupation(Occupation occupation) {
        return occupationRepository.save(occupation);
    }

    public Occupation updateOccupation(Long id, Occupation occupation) {
        Occupation existingOccupation = occupationRepository.findById(id).orElse(null);

        if (existingOccupation != null) {
            existingOccupation.setName(occupation.getName());
            existingOccupation.setCode(occupation.getCode());
            return occupationRepository.save(existingOccupation);
        }
        return null;
    }

    public void deleteOccupation(Long id) {
        occupationRepository.deleteById(id);
    }
}