package canto.jason.user;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
public class UserService {

	@Autowired
	private UserRepository repository;

	public Mono<ServerResponse> findAll(ServerRequest request) {
		var result = repository.findAll();
		return ok().contentType(APPLICATION_JSON).body(result, User.class);
	}

	public Mono<ServerResponse> findById(ServerRequest request) {
		var id = request.pathVariable("id");
		var result = repository.findById(id);
		return ok().contentType(APPLICATION_JSON).body(result, User.class);
	}

	public Mono<ServerResponse> create(ServerRequest request) {
		var userMono = request.bodyToMono(User.class);
		var result = userMono.flatMap(user -> repository.save(user));
		return ok().contentType(APPLICATION_JSON).body(result, User.class);
	}

	public Mono<ServerResponse> update(ServerRequest request) {
		var id = request.pathVariable("id");
		var result = request.bodyToMono(User.class)
				.map(user -> {
					user.setId(id);
					return user;
				})
				.flatMap(user -> repository.save(user));
		return ok().contentType(APPLICATION_JSON).body(result, User.class);
	}

	public Mono<ServerResponse> delete(ServerRequest request) {
		var id = request.pathVariable("id");
		var result = repository.deleteById(id);
		return ok().contentType(APPLICATION_JSON).build(result);
	}

}
