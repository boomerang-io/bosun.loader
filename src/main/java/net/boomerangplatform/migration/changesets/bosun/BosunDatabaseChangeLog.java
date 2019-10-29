package net.boomerangplatform.migration.changesets.bosun;

import java.io.IOException;
import java.util.List;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import net.boomerangplatform.migration.FileLoadingService;
import net.boomerangplatform.migration.SpringContextBridge;

@ChangeLog
public class BosunDatabaseChangeLog {

	private static FileLoadingService fileloadingService;

	private final Logger logger = LoggerFactory.getLogger(BosunDatabaseChangeLog.class);

	public BosunDatabaseChangeLog() {
		fileloadingService = SpringContextBridge.services().getFileLoadingService();
	}

	@ChangeSet(order = "001", id = "001", author = "Marcus Roy")
	public void initialSetup(MongoDatabase db) {
		logger.info("Running change log: #1 - Creating Collections");
		db.createCollection("bosun_teams");
		db.createCollection("bosun_policies");
		db.createCollection("bosun_activities");
		db.createCollection("bosun_definitions");
	}

	@ChangeSet(order = "002", id = "002", author = "Marcus Roy")
	public void loadPolicies(MongoDatabase db) throws IOException {

		BasicDBObject document = new BasicDBObject();
		final MongoCollection<Document> policyCollection = db.getCollection("bosun_definitions");
		policyCollection.deleteMany(document);
		final List<String> policyFiles = fileloadingService.loadFiles("bosun/002/bosun_policies/*.json");
		for (final String fileContents : policyFiles) {
			final Document doc = Document.parse(fileContents);
			policyCollection.insertOne(doc);
		}

		final MongoCollection<Document> collection = db.getCollection("bosun_teams");
		collection.deleteMany(document);
		final List<String> teamsCollection = fileloadingService.loadFiles("bosun/002/bosun_teams/*.json");
		for (final String fileContents : teamsCollection) {
			final Document doc = Document.parse(fileContents);
			collection.insertOne(doc);
		}
	}
}
