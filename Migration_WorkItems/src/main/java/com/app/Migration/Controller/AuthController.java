package com.app.Migration.Controller;

import com.app.Migration.dto.LoginRequest;
import com.app.Migration.dto.RegisterRequest;
import com.app.Migration.dto.PatRequest;
import com.app.Migration.dto.JwtResponse;
import com.app.Migration.Entity.TokenType;
import com.app.Migration.Entity.User;
import com.app.Migration.service.AuthService;
import com.app.Migration.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    // ✅ Inscription
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        authService.register(request.getUsername(), request.getPassword());
        return ResponseEntity.ok("✅ Utilisateur enregistré avec succès !");
    }

    // ✅ Connexion → retourne JWT
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String jwt = authService.login(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    // ✅ Enregistrer un Personal Access Token (cloud ou server)
    @PostMapping("/pat")
    public ResponseEntity<?> savePAT(@RequestBody PatRequest patRequest) {
        User user = userService.getCurrentUser();
        authService.savePAT(user, patRequest.getType(), patRequest.getToken());
        return ResponseEntity.ok("🔐 PAT enregistré avec succès.");
    }

    // ✅ Récupérer l'utilisateur actuellement connecté
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        User user = userService.getCurrentUser();
        return ResponseEntity.ok(user);
    }
}
