package com.app.Migration.service;

import com.app.Migration.Entity.User;
import com.app.Migration.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // ✅ Récupère l'utilisateur actuellement connecté
    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof User) {
            return (User) principal;
        } else {
            throw new RuntimeException("Utilisateur non authentifié !");
        }
    }
}
