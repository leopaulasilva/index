package dev.pokemontrainer.index.repository;

import dev.pokemontrainer.index.entity.Pokemon;
import dev.pokemontrainer.index.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, Long> {
    Optional<Pokemon> findByNameAndTrainer(String name, Trainer trainer);
}
