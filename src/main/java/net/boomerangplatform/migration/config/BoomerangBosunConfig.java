package net.boomerangplatform.migration.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.github.mongobee.Mongobee;

import net.boomerangplatform.migration.BoomerangMigration;

@Configuration
@Profile("bosun")
public class BoomerangBosunConfig implements BoomerangMigration {

	private final Logger logger = LoggerFactory.getLogger(BoomerangBosunConfig.class);

	@Value("${spring.data.mongodb.uri}")
	private String mongodbUri;

	@Override
	public Mongobee mongobee() {

		logger.info("Creating MongoDB Configuration for: Bosun");

		final Mongobee runner = new Mongobee(mongodbUri);
		runner.setChangelogCollectionName("sys_changelog_bosun");
		runner.setLockCollectionName("sys_lock_bosun");
		runner.setChangeLogsScanPackage("net.boomerangplatform.migration.changesets.bosun");

		return runner;
	}
}
