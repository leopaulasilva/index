package dev.pokemontrainer.index.repository;

import dev.pokemontrainer.index.entity.Ability;
import dev.pokemontrainer.index.entity.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AbilityRepository extends JpaRepository<Ability, Long> {
    Optional<Ability> findByNameAndPokemon(String name, Pokemon pokemon);
}
