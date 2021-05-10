package ru.chernov.diplom.service;

import org.springframework.stereotype.Service;
import ru.chernov.diplom.domain.entity.Solution;
import ru.chernov.diplom.repository.SolutionRepository;

import java.time.LocalDateTime;

/**
 * @author Pavel Chernov
 */
@Service
public class SolutionService {

    private final SolutionRepository solutionRepository;

    public SolutionService(SolutionRepository solutionRepository) {
        this.solutionRepository = solutionRepository;
    }

    public Solution save(Solution solution) {
        if (solution != null) {
            solution.setCreationDateTime(LocalDateTime.now());
            return solutionRepository.save(solution);
        }
        return null;
    }

    public Solution findById(long id) {
        return solutionRepository.findById(id).orElse(null);
    }
}
