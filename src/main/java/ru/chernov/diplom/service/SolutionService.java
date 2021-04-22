package ru.chernov.diplom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chernov.diplom.domain.entity.Solution;
import ru.chernov.diplom.repository.SolutionRepository;

/**
 * @author Pavel Chernov
 */
@Service
public class SolutionService {

    private final SolutionRepository solutionRepository;

    @Autowired
    public SolutionService(SolutionRepository solutionRepository) {
        this.solutionRepository = solutionRepository;
    }

    public Solution save(Solution solution) {
        return solution != null ? solutionRepository.save(solution) : null;
    }

    public Solution findById(long id) {
        return solutionRepository.findById(id).orElse(null);
    }

    public void deleteById(long id) {
        solutionRepository.deleteById(id);
    }
}
