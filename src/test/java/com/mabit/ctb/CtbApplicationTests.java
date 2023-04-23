package com.mabit.ctb;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import jakarta.validation.constraints.AssertTrue;

@SpringBootTest
class CtbApplicationTests {

	@Test
	void contextLoads() {
		for(int i = 1; i < 5; i++){
			var s = i;
		}
	}

}
