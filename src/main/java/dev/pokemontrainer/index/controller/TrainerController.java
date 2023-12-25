package dev.pokemontrainer.index.controller;

import dev.pokemontrainer.index.dao.TrainerR;
import dev.pokemontrainer.index.entity.Trainer;
import dev.pokemontrainer.index.service.TrainerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.rmi.ServerException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/trainers")
public class TrainerController {

    @Autowired
    private final TrainerService trainerService;
    @Autowired
    private final MessageSource messageSource;
    public TrainerController(TrainerService trainerService, MessageSource messageSource) {
        this.trainerService = trainerService;
        this.messageSource = messageSource;
    }
    private String getMessage(String messageCode, Object... args) {
        return messageSource.getMessage(messageCode, args, LocaleContextHolder.getLocale());
    }
    @GetMapping
    public List<TrainerR> getAllTrainers() {
        return trainerService.findAllTrainers();
    }

    @PostMapping
    public ResponseEntity<Trainer> createTrainer(@Valid @RequestBody TrainerR trainer) throws ServerException {
        Trainer savedTrainer = trainerService.save(trainer);
        return new ResponseEntity<>(savedTrainer, HttpStatus.CREATED);

    }

    @DeleteMapping("/{trainerId}")
    public ResponseEntity<String> deleteTrainer(@PathVariable @NotNull Long trainerId) {
        trainerService.deleteTrainer(trainerId);
        return ResponseEntity.ok(getMessage("message.trainer.delete", trainerId));
    }
}
