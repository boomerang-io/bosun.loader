package net.boomerangplatform.migration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.mongobee.Mongobee;

@Component
public class MongobeeRunner {

    private final Logger logger = LoggerFactory.getLogger(MongobeeRunner.class);

    @Autowired(required = false)
    private BoomerangMigration migrationTool;

    public void run() {

        try {
            if (migrationTool == null) {
                return;
            }
            final Mongobee mongoBee = migrationTool.mongobee();
            mongoBee.execute();
        } catch (final Exception e) {
            logger.error("Error running migration:", e);
            System.exit(1);
        }
    }
}
