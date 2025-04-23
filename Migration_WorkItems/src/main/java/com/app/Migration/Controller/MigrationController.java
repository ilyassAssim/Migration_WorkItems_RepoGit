package com.app.Migration.Controller;

import com.app.Migration.dto.MigrationRequest;
import com.app.Migration.service.MigrationService;
import com.app.Migration.service.UserService;
import com.app.Migration.service.MigrationHistoryService;
import com.app.Migration.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/migration")
public class MigrationController {

    @Autowired
    private MigrationService migrationService;
    @Autowired
    private MigrationHistoryService historyService;
    @Autowired
    private UserService userService;

    // ✅ Lance la migration (Work Items + Repos)
    @PostMapping
    public ResponseEntity<?> migrate(@RequestBody MigrationRequest request) {
        User user = userService.getCurrentUser();

        try {
            migrationService.migrateAll(request, user);
            historyService.logMigration(
                    user,
                    request.getSourceProject(),
                    request.getTargetProject(),
                    true,
                    "Migration exécutée avec succès."
            );

            return ResponseEntity.ok("✅ Migration réussie !");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("❌ Erreur de migration : " + e.getMessage());
        }
    }

}
