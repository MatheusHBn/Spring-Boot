package com.Matheuszin;

import com.Matheuszin.config.TestContainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("itest")
@Import(TestContainersConfiguration.class)
class UserServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
