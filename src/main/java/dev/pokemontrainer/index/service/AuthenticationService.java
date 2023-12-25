package dev.pokemontrainer.index.service;

import dev.pokemontrainer.index.dao.JwtAuthentication;
import dev.pokemontrainer.index.dao.SignUp;
import dev.pokemontrainer.index.dao.Signin;

public interface AuthenticationService {
    JwtAuthentication signup(SignUp request);

    JwtAuthentication signin(Signin request);
}
