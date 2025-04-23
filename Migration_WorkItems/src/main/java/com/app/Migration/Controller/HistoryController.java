package com.app.Migration.Controller;

import com.app.Migration.Entity.MigrationHistory;
import com.app.Migration.Entity.User;
import com.app.Migration.service.MigrationHistoryService;
import com.app.Migration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history")
public class HistoryController {

    @Autowired private MigrationHistoryService historyService;
    @Autowired private UserService userService;

    @GetMapping
    public List<MigrationHistory> getHistory() {
        User user = userService.getCurrentUser();
        return historyService.getUserHistory(user); // ✔️ Appelle correct
    }
}
