package dev.pokemontrainer.index.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PokemonApi {
    private Long id;
    private String name;
    private int weight;
    @JsonProperty("base_experience")
    private int baseExperience;
    private List<PokemonApiAbility> abilities;
}
