package merisalu.kmdb.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import merisalu.kmdb.service.UtilityCleanupService;

@RestController
@RequestMapping("/admin/cleanup")
public class CleanupController {

    private final UtilityCleanupService cleanupService;

    public CleanupController(UtilityCleanupService cleanupService) {
        this.cleanupService = cleanupService;
    }

    @PostMapping
    public String cleanup() {
        cleanupService.cleanOrphanedJoinTableEntries();
        return "Join table cleanup completed.";
    }
}
