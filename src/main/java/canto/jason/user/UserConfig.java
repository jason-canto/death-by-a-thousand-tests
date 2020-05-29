package canto.jason.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class UserConfig {

	@Bean
	RouterFunction<ServerResponse> routes(UserRepository repository) {
		return route()
				.GET("/users", request -> ok().body(repository.findAll(), User.class))
				.build();
	}
}
