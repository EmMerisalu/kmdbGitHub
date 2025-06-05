package merisalu.kmdb.config;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import merisalu.kmdb.service.UtilityCleanupService;

@Component
public class StartupCleanupRunner {

    private final UtilityCleanupService cleanupService;

    public StartupCleanupRunner(UtilityCleanupService cleanupService) {
        this.cleanupService = cleanupService;
    }

    @PostConstruct
    public void runCleanupOnStartup() {
        System.out.println("🔧 Running cleanup of dangling relationships on startup...");
        cleanupService.cleanOrphanedJoinTableEntries();
    }
}
