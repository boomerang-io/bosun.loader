package net.boomerangplatform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import net.boomerangplatform.migration.MongockRunner;

@SpringBootApplication
public class Application implements CommandLineRunner {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    private final Logger logger = LoggerFactory.getLogger(Application.class);

    @Autowired
    private MongockRunner runner;

    @Override
    public void run(String... args) throws Exception {
        logger.info("Boomerang Mongo DB Migration Script.");
        runner.run();
    }
}
