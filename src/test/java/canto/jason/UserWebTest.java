package canto.jason;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import canto.jason.user.User;
import canto.jason.user.UserConfig;
import canto.jason.user.UserRepository;
import reactor.core.publisher.Flux;

@WebFluxTest(UserConfig.class)
//@WithMockUser(username="admin",roles={"USER","ADMIN"})
public class UserWebTest {

	@Autowired
	private WebTestClient client;

	@MockBean
	private UserRepository repository;

	@Test
	public void getAllUsers() throws Exception {
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
}
