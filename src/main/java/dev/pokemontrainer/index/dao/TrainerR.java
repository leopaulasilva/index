package dev.pokemontrainer.index.dao;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class TrainerDTO {

    private Long id;
    @NotBlank(message = "{message.validation.name}")
    private String name;
    @NotBlank(message = "{message.validation.email}")
    private String email;
    @NotBlank(message = "{message.validation.instagramProfile}")
    private String instagramProfile;
    private List<PokemonApi> pokemons = new ArrayList<>();

}
