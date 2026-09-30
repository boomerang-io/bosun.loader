package net.boomerangplatform.migration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MongockRunner {

    private final Logger logger = LoggerFactory.getLogger(MongockRunner.class);

    @Autowired(required = false)
    private BoomerangMigration migrationTool;

    public void run() {

        try {
            if (migrationTool == null) {
                return;
            }
            final io.mongock.runner.core.executor.MongockRunner mongock = migrationTool.mongock();
            mongock.execute();
        } catch (final Exception e) {
            logger.error("Error running migration:", e);
            System.exit(1);
        }
    }
}
