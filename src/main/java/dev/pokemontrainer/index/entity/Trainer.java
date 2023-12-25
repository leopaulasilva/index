package dev.pokemontrainer.index.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "trainer")
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String instagramProfile;

    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL)
    private List<Pokemon> pokemons = new ArrayList<>();

}
