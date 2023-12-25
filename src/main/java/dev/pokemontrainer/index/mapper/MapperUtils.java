package dev.pokemontrainer.index.mapper;

import dev.pokemontrainer.index.dao.PokemonApi;
import dev.pokemontrainer.index.dao.TrainerR;
import dev.pokemontrainer.index.entity.Pokemon;
import dev.pokemontrainer.index.entity.Trainer;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Component
public class MapperUtils {

    private final ModelMapper modelMapper;

    public MapperUtils(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        configureModelMapper();
    }

    private void configureModelMapper() {
        modelMapper.typeMap(Trainer.class, TrainerR.class)
                .addMappings(mapper -> mapper.skip(TrainerR::setPokemons));
    }

    public TrainerR mapToDTO(Trainer trainer) {
        TrainerR trainerDTO = modelMapper.map(trainer, TrainerR.class);
        trainerDTO.setPokemons(mapToPokemonDTOList(trainer.getPokemons()));
        return trainerDTO;
    }



    public List<PokemonApi> mapToPokemonDTOList(List<Pokemon> pokemons) {
        Type listType = new TypeToken<List<PokemonApi>>() {}.getType();
        return modelMapper.map(pokemons, listType);
    }


}

