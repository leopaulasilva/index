package dev.pokemontrainer.index.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.pokemontrainer.index.dao.TrainerR;
import dev.pokemontrainer.index.entity.Trainer;
import dev.pokemontrainer.index.service.TrainerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class TrainerControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private TrainerService trainerService;

    @InjectMocks
    private TrainerController trainerController;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private TrainerController trainerControllerWithMessageSource;

    @Mock
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(trainerController).build();
    }

    @Test
    void testGetAllTrainers() throws Exception {
        List<TrainerR> trainers = Arrays.asList(/* create some trainers */);
        when(trainerService.findAllTrainers()).thenReturn(trainers);

        mockMvc.perform(get("/api/v1/trainers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(trainers.size())))

                // Add more assertions as needed
                .andReturn();

        verify(trainerService, times(1)).findAllTrainers();
        verifyNoMoreInteractions(trainerService);
    }

    @Test
    void testCreateTrainer() throws Exception {
        TrainerR trainerToCreate = new TrainerR();
        trainerToCreate.setEmail("valid@email.com");
        trainerToCreate.setName("John Doe");
        trainerToCreate.setInstagramProfile("instagramUser");

        Trainer createdTrainer = new Trainer();

        when(trainerService.save(any(TrainerR.class))).thenReturn(createdTrainer);

        mockMvc.perform(post("/api/v1/trainers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trainerToCreate)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        verify(trainerService, times(1)).save(any(TrainerR.class));
        verifyNoMoreInteractions(trainerService);
    }

    @Test
    void testDeleteTrainer() throws Exception {
        Long trainerIdToDelete = 1L;

        mockMvc.perform(delete("/api/v1/trainers/{trainerId}", trainerIdToDelete))
                .andExpect(status().isOk())
                .andReturn();

        verify(trainerService, times(1)).deleteTrainer(trainerIdToDelete);
        verifyNoMoreInteractions(trainerService);
    }

}

