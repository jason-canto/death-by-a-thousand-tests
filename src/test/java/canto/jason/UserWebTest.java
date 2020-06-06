package canto.jason;

//import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

import canto.jason.user.User;
import canto.jason.user.UserConfig;
import canto.jason.user.UserRepository;
import canto.jason.user.UserService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest(UserConfig.class)
@Import(UserService.class)
//@WithMockUser(username="admin",roles={"USER","ADMIN"})
public class UserWebTest {

	@Autowired
	private WebTestClient client;

	@MockBean
	private UserRepository repository;

	@Test
	public void whenAllUsersThenOk() throws Exception {
		Mockito.when(repository.findAll())
			.thenReturn(Flux.just(new User("1", "firstUser"), new User("2", "secondUser")));

		this.client
			.get()
			.uri("/users")
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MediaType.APPLICATION_JSON)
			.expectBody()
				.jsonPath("@.[0].name").isEqualTo("firstUser")
				.jsonPath("@.[0].id").isEqualTo("1")
				.jsonPath("@.[1].name").isEqualTo("secondUser")
				.jsonPath("@.[1].id").isEqualTo("2");
	}

	@Test
	public void whenPostNewUserThenReturnOk() throws Exception {
		var user = new User(null, "test");
		this.client
			//.mutateWith(csrf())
			.post()
			.uri("/test")
			.contentType(MediaType.APPLICATION_JSON)
			.body(Mono.just(user), User.class)
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MediaType.APPLICATION_JSON);

		//Mockito.verify(repository).save(Mockito.any());
	}

	@Test
	public void whenSingleUserThenReturnOk() throws Exception {

		Mockito.when(repository.findById(Mockito.anyString()))
			.thenReturn(Mono.just(new User("1", "firstUser")));

		this.client
			.get()
			.uri("/users/{id}", "1")
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.consumeWith(response -> Assertions.assertThat(response.getResponseBody()).isNotNull());
	}

	@Test
	public void whenNonExistentRouteThenNotFound() throws Exception {
		Mockito.when(repository.findById(Mockito.anyString()))
			.thenReturn(Mono.empty());

		this.client
			.get()
			.uri("/testWrongRoute")
			.exchange()
			.expectStatus().isNotFound();
	}
}
