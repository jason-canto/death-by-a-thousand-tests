package canto.jason.user;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class UserHandler {

	@Autowired
	private UserRepository userRepository;

	public Mono<ServerResponse> findAll(ServerRequest request) {
		Flux<User> result = userRepository.findAll();
		return ok().contentType(APPLICATION_JSON).body(result, User.class);
	}

	public Mono<ServerResponse> findById(ServerRequest request) {
		String id = request.pathVariable("id");
		Mono<User> result = userRepository.findById(id);
		return ok().contentType(APPLICATION_JSON).body(result, User.class);
	}

	public Mono<ServerResponse> create(ServerRequest request) {
		Mono<User> userMono = request.bodyToMono(User.class);
		Mono<User> result = userMono.flatMap(user -> userRepository.save(user));
		return ok().contentType(APPLICATION_JSON).body(result, User.class);
	}

	public Mono<ServerResponse> delete(ServerRequest request) {
		String id = request.pathVariable("id");
		Mono<Void> result = userRepository.deleteById(id);
		return ok().contentType(APPLICATION_JSON).build(result);
	}

}
