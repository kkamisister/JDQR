// package com.example.backend.common.config;
//
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
// import org.springframework.data.mongodb.core.MongoTemplate;
// import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
//
// import com.mongodb.ConnectionString;
// import com.mongodb.MongoClientSettings;
// import com.mongodb.client.MongoClient;
// import com.mongodb.client.MongoClients;
//
// @Configuration
// public class MongoConfig extends AbstractMongoClientConfiguration {
// 	@Value("${spring.data.mongodb.uri}")
// 	private String uri;
//
// 	@Value("${spring.data.mongodb.database}")
// 	private String databaseName;
//
// 	@Override
// 	protected String getDatabaseName() {
// 		return databaseName;
// 	}
//
// 	@Override
// 	public MongoClient mongoClient(){
// 		ConnectionString connectionString = new ConnectionString(uri);
//
// 		MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
// 			.applyConnectionString(connectionString)
// 			.build();
//
// 		return MongoClients.create(mongoClientSettings);
// 	}
//
// 	@Bean
// 	public MongoTemplate mongoTemplate(){
// 		return new MongoTemplate(new SimpleMongoClientDatabaseFactory(mongoClient(), getDatabaseName()));
// 	}
// }
