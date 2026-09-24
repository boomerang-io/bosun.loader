package net.boomerangplatform.migration.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.ConnectionString;
import io.mongock.driver.mongodb.sync.v4.driver.MongoSync4Driver;
import io.mongock.runner.core.executor.MongockRunner;
import io.mongock.runner.springboot.MongockSpringboot;
import net.boomerangplatform.migration.BoomerangMigration;

@Configuration
@Profile("bosun")
public class BoomerangBosunConfig implements BoomerangMigration {

	private final Logger logger = LoggerFactory.getLogger(BoomerangBosunConfig.class);

	@Value("${spring.data.mongodb.uri}")
	private String mongodbUri;

	@Override
	public MongockRunner mongock() {

		logger.info("Creating MongoDB Configuration for: Bosun");

		ConnectionString connectionString = new ConnectionString(mongodbUri);
		MongoClient mongoClient = MongoClients.create(connectionString);

		return MongockSpringboot.builder()
				.setDriver(MongoSync4Driver.withDefaultLock(mongoClient, connectionString.getDatabase()))
				.addChangeLogsScanPackage("net.boomerangplatform.migration.changesets.bosun")
				.buildRunner();
	}
}
