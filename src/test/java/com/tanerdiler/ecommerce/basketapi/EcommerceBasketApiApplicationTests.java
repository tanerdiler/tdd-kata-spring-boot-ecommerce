package com.tanerdiler.ecommerce.basketapi;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EcommerceBasketApiApplicationTests
{
	@Test
	public void contextLoads()
	{
		boolean condition = true;
		assertThat(condition).isTrue();
	}
}
