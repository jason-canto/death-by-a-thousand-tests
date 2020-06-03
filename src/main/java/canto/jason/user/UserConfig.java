package canto.jason.user;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class UserConfig {

	@Autowired
	private UserService service;

	@Bean
	RouterFunction<ServerResponse> routes() {
		return route(GET("/users").and(accept(APPLICATION_JSON)), service::findAll)
				.andRoute(GET("/users/{id}").and(accept(APPLICATION_JSON)), service::findById)
				.andRoute(POST("/users").and(accept(APPLICATION_JSON)).and(contentType(APPLICATION_JSON)),
						service::create)
				.andRoute(PUT("/users/{id}").and(accept(APPLICATION_JSON)).and(contentType(APPLICATION_JSON)),
						service::update)
				.andRoute(DELETE("/users/{id}").and(accept(APPLICATION_JSON)), service::delete);

		/*
		 * Alternative route without handler function .andRoute(POST("/users")
		 * .and(accept(APPLICATION_JSON)) .and(contentType(APPLICATION_JSON)), req ->
		 * req.bodyToMono(User.class) .doOnNext(repository::save) .then(ok().build()));
		 */
	}

}
