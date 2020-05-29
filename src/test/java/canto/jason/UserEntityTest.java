package canto.jason;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import canto.jason.user.User;
import canto.jason.user.UserRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@DataMongoTest
public class UserEntityTest {

	@Autowired
	private ReactiveMongoTemplate template;

	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void create() throws Exception {
		User simplePojo = new User("1", "firstUser");
		Assertions.assertThat(simplePojo.getName()).isEqualTo("firstUser");
	}

	@Test
	public void persist() throws Exception {
		User simplePojo = new User(null, "TestUser");
		Mono<User> save = template.save(simplePojo);
		StepVerifier.create(save)
				.expectNextMatches(r -> r.getName().equalsIgnoreCase("TestUser") && r.getId() != null)
				.verifyComplete();
	}

	@Test
	public void query() throws Exception {
		Flux<User> userFlux = userRepository
				.deleteAll()
				.thenMany(Flux.just("User1", "User2")
						.map(name -> new User(null, name))
						.flatMap(user -> userRepository.save(user)))
				.thenMany(userRepository.findAll());

		StepVerifier.create(userFlux)
			.expectNextCount(2)
			.verifyComplete();
	}

	@Test
	public void customQuery() throws Exception {
		Flux<User> userFlux = userRepository
				.deleteAll()
				.thenMany(Flux.just("User1", "User2")
						.map(name -> new User(null, name))
						.flatMap(user -> userRepository.save(user)))
				.thenMany(userRepository.findByName("User1"));

		StepVerifier.create(userFlux)
			.expectNextCount(1)
			.verifyComplete();
	}
}
