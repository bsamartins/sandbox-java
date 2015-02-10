package org.bmartins.javasandbox.springmongosession.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
@EnableMongoRepositories
public class MongoConfiguration extends AbstractMongoConfiguration {

	@Override
	protected String getDatabaseName() {		
		return "web_session";
	}

	@Override
	public Mongo mongo() throws Exception {
		MongoClient client = new MongoClient("localhost", 27017);
		return client;
	}
	
	@Override
	public MappingMongoConverter mappingMongoConverter() throws Exception {
		MappingMongoConverter mmc = super.mappingMongoConverter();
		mmc.setTypeMapper(new CustomMongoTypeMapper());
		//System.out.println(mmc.getTypeMapper());
		return mmc;
	}
	
	class CustomMongoTypeMapper extends DefaultMongoTypeMapper {
	}
}
