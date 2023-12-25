package dev.pokemontrainer.index.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "pokemon")
public class Pokemon {
    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("pokemon_id")
    private Long pokemonId;
    private String name;
    private int baseExperience;
    private int weight;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    @JsonIgnore
    private Trainer trainer;

    @OneToMany(mappedBy = "pokemon", cascade = CascadeType.ALL)
    private List<Ability> abilities = new ArrayList<>();;

}

