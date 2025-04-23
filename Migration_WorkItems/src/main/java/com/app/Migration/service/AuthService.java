package com.app.Migration.service;

import com.app.Migration.Entity.AuthToken;
import com.app.Migration.Entity.TokenType;
import com.app.Migration.Entity.User;
import com.app.Migration.repo.AuthTokenRepository;
import com.app.Migration.repo.UserRepository;
import com.app.Migration.security.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired private UserRepository userRepo;
    @Autowired private AuthTokenRepository authTokenRepo;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtService jwtService;

    //  Enregistrement d’un utilisateur
    public void register(String username, String password) {
        if (userRepo.findByUsername(username).isPresent()) {
            throw new RuntimeException("Nom d'utilisateur déjà utilisé !");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));

        userRepo.save(user);
    }

    //  Connexion → retourne un JWT
    public String login(String username, String password) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        } catch (AuthenticationException e) {
            throw new RuntimeException("Identifiants incorrects !");
        }

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé."));

        return jwtService.generateToken(user);
    }

    // ✅ Stocke un PAT (cloud ou serveur)
    public void savePAT(User user, TokenType type, String token) {
        AuthToken pat = authTokenRepo.findByUserAndType(user, type).orElse(new AuthToken());
        pat.setUser(user);
        pat.setType(type);
        pat.setToken(token);
        authTokenRepo.save(pat);
    }

    // ✅ (optionnel) Récupère un PAT pour usage backend
    public String getPAT(User user, TokenType type) {
        return authTokenRepo.findByUserAndType(user, type)
                .map(AuthToken::getToken)
                .orElseThrow(() -> new RuntimeException("Aucun PAT enregistré pour " + type));
    }
}
