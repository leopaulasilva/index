package dev.pokemontrainer.index.service.impl;

import dev.pokemontrainer.index.dao.JwtAuthentication;
import dev.pokemontrainer.index.dao.SignUp;
import dev.pokemontrainer.index.dao.Signin;
import dev.pokemontrainer.index.entity.User;
import dev.pokemontrainer.index.repository.UserRepository;
import dev.pokemontrainer.index.service.AuthenticationService;
import dev.pokemontrainer.index.service.JwtService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import dev.pokemontrainer.index.enums.Role;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final MessageSource messageSource;

    private String getMessage(String messageCode, Object... args) {
        return messageSource.getMessage(messageCode, args, LocaleContextHolder.getLocale());
    }

    @Override
    public JwtAuthentication signup(SignUp request) {
        var user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER).build();
        userRepository.save(user);

        var jwt = jwtService.generateToken(user);
        return JwtAuthentication.builder().token(jwt).build();
    }

    @Override
    public JwtAuthentication signin(Signin request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException(getMessage("message.email.error")));
        var jwt = jwtService.generateToken(user);
        return JwtAuthentication.builder().token(jwt).build();
    }
}