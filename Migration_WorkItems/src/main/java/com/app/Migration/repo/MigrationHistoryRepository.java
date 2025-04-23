package com.app.Migration.repo;


import com.app.Migration.Entity.MigrationHistory;
import com.app.Migration.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MigrationHistoryRepository extends JpaRepository<MigrationHistory, Long> {
    List<MigrationHistory> findByUser(User user);
}