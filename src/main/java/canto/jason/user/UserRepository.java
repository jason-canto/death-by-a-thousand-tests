package canto.jason.user;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Flux;

public interface UserRepository extends ReactiveCrudRepository<User, String> {

	Flux<User> findByName(String name);
}
