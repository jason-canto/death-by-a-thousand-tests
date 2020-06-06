package canto.jason.user;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class UserConfig {

	@Autowired
	private UserService service;

	@Autowired
	private UserRepository repository;

	@Bean
	RouterFunction<ServerResponse> routes() {
		return route(GET("/users").and(accept(APPLICATION_JSON)), service::findAll)
				.andRoute(GET("/users/{id}").and(accept(APPLICATION_JSON)), service::findById)
				.andRoute(POST("/users").and(accept(APPLICATION_JSON)).and(contentType(APPLICATION_JSON)),
						service::create)
				.andRoute(PUT("/users/{id}").and(accept(APPLICATION_JSON)).and(contentType(APPLICATION_JSON)),
						service::update)
				.andRoute(DELETE("/users/{id}").and(accept(APPLICATION_JSON)), service::delete);
	}

	@Bean
	RouterFunction<ServerResponse> addUser() {
		return route(POST("/test")
			.and(accept(APPLICATION_JSON))
			.and(contentType(APPLICATION_JSON)), req -> req.bodyToMono(User.class)
			.doOnNext(repository::save)
			.transform(response -> ServerResponse.ok().contentType(APPLICATION_JSON).body(response, User.class)));
	}

	@Bean
	RouterFunction<ServerResponse> findUsers(UserRepository repository) {
		return route().GET("/users", request -> ok().body(repository.findAll(), User.class)).build();
	}

}
