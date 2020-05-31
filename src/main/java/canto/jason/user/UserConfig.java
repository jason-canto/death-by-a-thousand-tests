package canto.jason.user;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
public class UserConfig {

	@Autowired
	private UserHandler userHandler;

	@Bean
	RouterFunction<ServerResponse> routes2(UserRepository repository) {
		return route().GET("/users", request -> ok().body(repository.findAll(), User.class)).build();
	}

}
