package dev.pokemontrainer.index.controller;

import dev.pokemontrainer.index.dao.JwtAuthentication;
import dev.pokemontrainer.index.dao.SignUp;
import dev.pokemontrainer.index.dao.Signin;
import dev.pokemontrainer.index.service.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest {

    @InjectMocks
    private AuthenticationController authenticationController;

    @Mock
    private AuthenticationService authenticationService;

    @Test
    public void testSignup() {
        SignUp signupRequest = new SignUp();
        JwtAuthentication expectedJwtAuthentication = new JwtAuthentication();

        when(authenticationService.signup(Mockito.any(SignUp.class))).thenReturn(expectedJwtAuthentication);

        ResponseEntity<JwtAuthentication> response = authenticationController.signup(signupRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedJwtAuthentication, response.getBody());
    }

    @Test
    public void testSignin() {
        Signin signinRequest = new Signin();
        JwtAuthentication expectedJwtAuthentication = new JwtAuthentication();

        when(authenticationService.signin(Mockito.any(Signin.class))).thenReturn(expectedJwtAuthentication);

        ResponseEntity<JwtAuthentication> response = authenticationController.signin(signinRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedJwtAuthentication, response.getBody());
    }
}

