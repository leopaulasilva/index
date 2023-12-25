package dev.pokemontrainer.index.service.impl;

import dev.pokemontrainer.index.dao.PokemonApiAbility;
import dev.pokemontrainer.index.dao.PokemonApi;
import dev.pokemontrainer.index.dao.TrainerR;
import dev.pokemontrainer.index.entity.Ability;
import dev.pokemontrainer.index.entity.Pokemon;
import dev.pokemontrainer.index.entity.Trainer;
import dev.pokemontrainer.index.mapper.MapperUtils;
import dev.pokemontrainer.index.repository.AbilityRepository;
import dev.pokemontrainer.index.repository.PokemonRepository;
import dev.pokemontrainer.index.repository.TrainerRepository;
import dev.pokemontrainer.index.service.TrainerService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrainerImplementation implements TrainerService {

    private static final Logger logger = LoggerFactory.getLogger(TrainerService.class);
    @Autowired
    private final TrainerRepository trainerRepository;

    @Autowired
    private final PokemonRepository pokemonRepository;

    @Autowired
    private final AbilityRepository abilityRepository;

    @Autowired
    private final MapperUtils mapperUtils;

    @Autowired
    private final MessageSource messageSource;

    private final RestTemplate restTemplate;


    @Value("${pokeApiUrlByName}")
    private String pokeApiUrlByName;

    public TrainerImplementation(TrainerRepository trainerRepository, PokemonRepository pokemonRepository, AbilityRepository abilityRepository, MessageSource messageSource, RestTemplate restTemplate, ModelMapper modelMapper, MapperUtils mapperUtils) {
        this.trainerRepository = trainerRepository;
        this.pokemonRepository = pokemonRepository;
        this.abilityRepository = abilityRepository;
        this.messageSource = messageSource;
        this.restTemplate = restTemplate;
        this.mapperUtils = mapperUtils;
    }

    private String getMessage(String messageCode, Object... args) {
        return messageSource.getMessage(messageCode, args, LocaleContextHolder.getLocale());
    }

    @Override
    @Transactional
    public Trainer save(TrainerR dto) {
        Optional<Trainer> existingTrainer = trainerRepository.findByName(dto.getName());

        existingTrainer.ifPresentOrElse(
                existing -> logger.info(getMessage("message.trainer.update", dto.getName())),
                () -> logger.info(getMessage("message.trainer.create", dto.getName())));

        Trainer trainer;
        trainer = existingTrainer.orElseGet(Trainer::new);
        trainer.setName(dto.getName());
        trainer.setEmail(dto.getEmail());
        trainer.setInstagramProfile(dto.getInstagramProfile());

        List<Pokemon> pokemons = new ArrayList<>();
        for(PokemonApi list : dto.getPokemons()){
            PokemonApi details = getPokedexDetails(list.getName());
            Pokemon pokemon = getPokemon(details,trainer);
            pokemons.add(pokemon);
        }
        trainer.setPokemons(pokemons);

        return trainerRepository.save(trainer);
    }

    private Pokemon getPokemon(PokemonApi details, Trainer trainer) {
        Pokemon pokemon;
        Optional<Pokemon> existingPokemon = Optional.empty();

        if (trainer.getId() != null) {
            existingPokemon = pokemonRepository.findByNameAndTrainer(details.getName(), trainer);
        }

        if (existingPokemon.isPresent()) {
            logger.info(getMessage("message.pokemon.update", details.getName()));
            pokemon = existingPokemon.get();
        } else {
            logger.info(getMessage("message.pokemon.create", details.getName()));
            pokemon = new Pokemon();
            pokemon.setTrainer(trainer);
        }

        updatePokemonDetails(pokemon, details);
        updateAbilities(pokemon, details);

        return pokemon;
    }

    private void updatePokemonDetails(Pokemon pokemon, PokemonApi details) {
        pokemon.setPokemonId(details.getId());
        pokemon.setName(details.getName());
        pokemon.setWeight(details.getWeight());
        pokemon.setBaseExperience(details.getBaseExperience());
    }

    private void updateAbilities(Pokemon pokemon, PokemonApi details) {
        List<Ability> abilities = new ArrayList<>();

        for (PokemonApiAbility abilityName : details.getAbilities()) {
            Ability ability = new Ability();
            ability.setName(abilityName.getAbility().getName());

            ability.setPokemon(pokemon);

            if (pokemon.getId() != null) {
                Optional<Ability> existingAbility = abilityRepository.findByNameAndPokemon(abilityName.getAbility().getName(), pokemon);
                existingAbility.ifPresentOrElse(
                        existing -> abilities.add(existing),
                        () -> abilities.add(ability)
                );
            } else {
                abilities.add(ability);
            }
        }

        if (!abilities.isEmpty()) {
            pokemon.setAbilities(abilities);
        }
    }

    public PokemonApi getPokedexDetails(String value) {
        ResponseEntity<PokemonApi> response = restTemplate.getForEntity(pokeApiUrlByName.replace("{value}", value), PokemonApi.class);
        return response.getStatusCode().is2xxSuccessful()
                ? response.getBody()
                : handleFailedRequest(response);
    }

    private PokemonApi handleFailedRequest(ResponseEntity<PokemonApi> response) {
        throw new RuntimeException(getMessage("message.fetchError.details", response.getStatusCodeValue()) );
    }

    @Override
    public List<TrainerR> findAllTrainers() {
        List<Trainer> trainers = trainerRepository.findAll();
        logger.info(getMessage("message.fetchSuccess", trainers.size()));
        return trainers.stream()
                .map(mapperUtils::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTrainer(Long trainerId) {
        if (trainerRepository.existsById(trainerId)) {
            trainerRepository.deleteById(trainerId);
        } else {
            throw new EntityNotFoundException(getMessage("message.noData", trainerId));
        }
    }
}
