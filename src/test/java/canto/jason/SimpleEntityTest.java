package canto.jason;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import canto.jason.model.SimplePojo;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@DataMongoTest
public class SimpleEntityTest {

	@Autowired
	private ReactiveMongoTemplate template;

	@Test
	public void persist() throws Exception {
		SimplePojo simplePojo = new SimplePojo(null, "TestEntity");
		Mono<SimplePojo> save = template.save(simplePojo);
		StepVerifier.create(save)
				.expectNextMatches(r -> r.getName().equalsIgnoreCase("TestEntity") && r.getId() != null)
				.verifyComplete();
	}
}
