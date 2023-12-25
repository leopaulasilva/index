package dev.pokemontrainer.index.dao;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUp {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
