package com.app.Migration.service;

import com.app.Migration.Entity.TokenType;
import org.springframework.core.ParameterizedTypeReference;
import com.app.Migration.Entity.User;
import com.app.Migration.dto.MigrationRequest;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.File;
import java.util.List;
import java.util.Map;

@Service
public class MigrationService {

    @Autowired private AuthService authService;

    // 📁 Dossier temporaire pour cloner les repos
    private final String tempDir = System.getProperty("java.io.tmpdir") + "/migration-tmp";

    // ✅ Migration complète
    public void migrateAll(MigrationRequest request, User user) throws Exception {
        migrateWorkItems(request, user);
        migrateRepos(request, user);
    }

    // ✅ Migration des Work Items via Azure DevOps REST API

    public void migrateWorkItems(MigrationRequest request, User user) {
        String sourcePAT = authService.getPAT(user, TokenType.CLOUD);
        String targetPAT = authService.getPAT(user, TokenType.SERVER);

        WebClient sourceClient = WebClient.builder()
                .baseUrl(request.getSourceUrl())
                .defaultHeaders(headers -> headers.setBasicAuth("", sourcePAT))
                .build();

        WebClient targetClient = WebClient.builder()
                .baseUrl(request.getTargetUrl())
                .defaultHeaders(headers -> headers.setBasicAuth("", targetPAT))
                .build();

        // Exemple : récupérer les WIs
        List<Map<String, Object>> workItems = sourceClient.get()
                .uri("/_apis/wit/workitems?api-version=6.0")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Map<String, Object>>>() {})
                .block();

        if (workItems != null) {
            // Ensuite créer dans cible
            for (Map<String, Object> wi : workItems) {
                targetClient.post()
                        .uri("/_apis/wit/workitems/$Task?api-version=6.0")
                        .bodyValue(Map.of(
                                "fields", Map.of(
                                        "System.Title", wi.get("fields") instanceof Map ? ((Map<?, ?>) wi.get("fields")).get("System.Title") : "Imported"
                                )
                        ))
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();
            }
        }
    }

    // ✅ Migration du code source via Git
    public void migrateRepos(MigrationRequest request, User user) throws Exception {
        String sourcePAT = authService.getPAT(user, TokenType.CLOUD);
        String targetPAT = authService.getPAT(user, TokenType.SERVER);

        String sourceRepo = request.getSourceRepoUrl(); // ex: https://dev.azure.com/org/project/_git/repo
        String targetRepo = request.getTargetRepoUrl();

        File localPath = new File(tempDir + "/" + user.getUsername());
        if (localPath.exists()) {
            deleteDirectory(localPath);
        }

        // Clone
        Git git = Git.cloneRepository()
                .setURI(sourceRepo)
                .setDirectory(localPath)
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider("", sourcePAT))
                .call();

        // Push vers cible
        git.push()
                .setRemote(targetRepo)
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider("", targetPAT))
                .setPushAll()
                .call();

        git.getRepository().close();
    }

    // Utilitaire pour supprimer un dossier récursivement
    private void deleteDirectory(File dir) {
        File[] allContents = dir.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        dir.delete();
    }
}
