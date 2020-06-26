package com.mintfintech.card_verified;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.mintfintech.card_verified.exception.BadGatewayException;
import com.mintfintech.card_verified.exception.BadRequestException;
import com.mintfintech.card_verified.service.CardService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = {"classpath:application.properties"})
public class ApplicationTests {

	@Autowired
	private CardService cardService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void test_invalidCard() {

		try {
			cardService.verify("xxxxxxx");
			fail();
		} catch (BadGatewayException | BadRequestException | JsonProcessingException ex) {
			assertTrue(true);
		}
	}

	@Test
	public void test_validCard() throws BadGatewayException, BadRequestException, JsonProcessingException {

		assertNotNull(cardService.verify("45717360"));
	}
}
