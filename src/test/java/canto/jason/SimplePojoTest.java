package canto.jason;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import canto.jason.model.SimplePojo;

public class SimplePojoTest {

	@Test
	public void create() throws Exception {
		SimplePojo simplePojo = new SimplePojo("1", "firstCreationTest");
		Assertions.assertThat(simplePojo.getName()).isEqualTo("firstCreationTest");
	}

}
