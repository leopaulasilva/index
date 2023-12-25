package dev.pokemontrainer.index.mapper;

import dev.pokemontrainer.index.dao.PokemonApi;
import dev.pokemontrainer.index.dao.PokemonApiAbility;
import dev.pokemontrainer.index.dao.TrainerDTO;
import dev.pokemontrainer.index.entity.Ability;
import dev.pokemontrainer.index.entity.Pokemon;
import dev.pokemontrainer.index.entity.Trainer;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MapperUtils {

    private final ModelMapper modelMapper;

    public MapperUtils(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        configureModelMapper();
    }

    private void configureModelMapper() {
        modelMapper.typeMap(Trainer.class, TrainerDTO.class)
                .addMappings(mapper -> mapper.skip(TrainerDTO::setPokemons));
    }

    public TrainerDTO mapToDTO(Trainer trainer) {
        TrainerDTO trainerDTO = modelMapper.map(trainer, TrainerDTO.class);
        trainerDTO.setPokemons(mapToPokemonDTOList(trainer.getPokemons()));
        return trainerDTO;
    }

    public List<TrainerDTO> mapToDTOList(List<Trainer> trainers) {
        return trainers.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public PokemonApi mapToDTO(Pokemon pokemon) {
        PokemonApi pokemonApiResponse = modelMapper.map(pokemon, PokemonApi.class);
        pokemonApiResponse.setAbilities(mapToAbilityDTOList(pokemon.getAbilities()));
        return pokemonApiResponse;
    }

    public List<PokemonApi> mapToPokemonDTOList(List<Pokemon> pokemons) {
        Type listType = new TypeToken<List<PokemonApi>>() {}.getType();
        return modelMapper.map(pokemons, listType);
    }

    public PokemonApiAbility mapToDTO(Ability ability) {
        return modelMapper.map(ability, PokemonApiAbility.class);
    }

    public List<PokemonApiAbility> mapToAbilityDTOList(List<Ability> abilityList) {
        Type listType = new TypeToken<List<PokemonApiAbility>>() {}.getType();
        return modelMapper.map(abilityList, listType);
    }
}

