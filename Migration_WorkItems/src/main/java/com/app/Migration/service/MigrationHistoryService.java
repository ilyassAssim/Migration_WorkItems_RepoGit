package com.app.Migration.service;

import com.app.Migration.Entity.MigrationHistory;
import com.app.Migration.Entity.User;
import com.app.Migration.repo.MigrationHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MigrationHistoryService {

    @Autowired
    private MigrationHistoryRepository historyRepo;

    // ✅ Enregistre une nouvelle entrée d’historique de migration
    public void logMigration(User user, String sourceUrl, String targetUrl, boolean success, String details) {
        MigrationHistory history = new MigrationHistory();
        history.setUser(user);
        history.setSourceUrl(sourceUrl);
        history.setTargetUrl(targetUrl);
        history.setSuccess(success);
        history.setDetails(details);
        history.setTimestamp(LocalDateTime.now());

        historyRepo.save(history);
    }

    // ✅ Récupère l’historique des migrations d’un utilisateur
    public List<MigrationHistory> getUserHistory(User user) {
        return historyRepo.findByUser(user);
    }

    // ✅ Récupère toutes les migrations (admin)
    public List<MigrationHistory> getAllHistory() {
        return historyRepo.findAll();
    }
}
