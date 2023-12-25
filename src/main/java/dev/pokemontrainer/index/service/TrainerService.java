package dev.pokemontrainer.index.service;

import dev.pokemontrainer.index.dao.TrainerR;
import dev.pokemontrainer.index.entity.Trainer;

import java.util.List;

public interface TrainerService {

    Trainer save(TrainerR dto);
    List<TrainerR> findAllTrainers();

    void deleteTrainer(Long trainerId);

}
