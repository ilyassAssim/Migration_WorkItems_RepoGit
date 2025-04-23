package com.app.Migration.Entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<AuthToken> tokens;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<MigrationHistory> migrationHistories;

    // Getters & Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<AuthToken> getTokens() {
        return tokens;
    }

    public void setTokens(List<AuthToken> tokens) {
        this.tokens = tokens;
    }

    public List<MigrationHistory> getMigrationHistories() {
        return migrationHistories;
    }

    public void setMigrationHistories(List<MigrationHistory> migrationHistories) {
        this.migrationHistories = migrationHistories;
    }
}
