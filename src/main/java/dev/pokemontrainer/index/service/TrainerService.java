package dev.pokemontrainer.index.service;

import dev.pokemontrainer.index.dao.TrainerDTO;
import dev.pokemontrainer.index.entity.Trainer;

import java.util.List;

public interface TrainerService {

    Trainer save(TrainerDTO dto);
    List<TrainerDTO> findAllTrainers();

    void deleteTrainer(Long trainerId);

}
