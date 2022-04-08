package com.example.tangntalk;

import com.example.tangntalk.web.account.domain.Account;
import com.example.tangntalk.web.account.domain.AccountRepository;
import com.example.tangntalk.web.account.dto.AccountDto;
import com.example.tangntalk.web.account.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TangntalkApplicationTests {

	@Autowired
	private AccountService accountService;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@Transactional
	void contextLoads() {
	}

	@BeforeEach
	void setup(){

		Account user1 = AccountDto.builder()
				.username("flaxinger1")
				.name("yohan")
				.password("mok")
				.statusMessage("Preswot")
				.type("학생")
				.accountLocation("공학관")
				.connectionStatus(true)
				.build().toAccount();

		accountRepository.save(user1);
	}
	@Test
	@Transactional
	void userInfo() throws Exception {

		mvc.perform(get("/users/flaxinger1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.user.name", is("yohan")))
				.andExpect(jsonPath("$.user.statusMessage", is("Preswot")))
				.andExpect(jsonPath("$.user.locationName", is("공학관")));
	}

	@Test
	@Transactional
	void updateMessage() throws Exception {

		Map<String, String> status = new HashMap<>();
		status.put("statusMessage", "아! 프레스왓!");

		mvc.perform(post("/users/flaxinger1/status")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(status)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success", is("true")));

		mvc.perform(get("/users/flaxinger1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.user.status_message", is("아! 프레스왓!")));
	}

	@Test
	@Transactional
	void updateLocation() throws Exception {

		Map<String, String> location = new HashMap<>();
		location.put("location_name", "백양관");

		mvc.perform(post("/users/flaxinger1/location")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(location)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success", is("true")));

		mvc.perform(get("/users/flaxinger1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.user.location_name", is("백양관")));
	}

}

