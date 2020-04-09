package net.boomerangplatform.migration.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.MongockBuilder;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import net.boomerangplatform.migration.BoomerangMigration;

@Configuration
@Profile("bosun")
public class BoomerangBosunConfig implements BoomerangMigration {

	private final Logger logger = LoggerFactory.getLogger(BoomerangBosunConfig.class);

	@Value("${spring.data.mongodb.uri}")
	private String mongodbUri;

	@Override
	public Mongock mongock() {

		logger.info("Creating MongoDB Configuration for: Bosun");

		MongoClientURI uri = new MongoClientURI(mongodbUri);
	    MongoClient mongoclient = new MongoClient(uri);
		
		MongockBuilder mongockBuilder = new MongockBuilder(mongoclient, uri.getDatabase(),
	        "net.boomerangplatform.migration.changesets.bosun");
	    mongockBuilder.setChangeLogCollectionName("sys_changelog_ci");
	    mongockBuilder.setLockCollectionName("sys_lock_ci");

	    return mongockBuilder.setLockQuickConfig().build();
	}
}
