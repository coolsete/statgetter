package com.hello.devtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DevtestApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	protected ObjectMapper mapper;

	@Test
	@SneakyThrows
	public void statisticControllerShouldAcceptCorrectInput() {
		mockMvc.perform(post("/event")
						.contentType(MediaType.APPLICATION_JSON)
						.content(String.format("%s,0.0442672968,1282509067", System.currentTimeMillis())))
				.andExpect(status().isAccepted())
				.andDo(print())
				.andReturn();
	}

	@Test
	@SneakyThrows
	public void statisticControllerShouldReturnBadRequestWithInCorrectInput() {
		mockMvc.perform(post("/event")
						.contentType(MediaType.APPLICATION_JSON)
						.content("hddgfhdfhh,0.0442672968,1282509067"))
				.andExpect(status().isBadRequest())
				.andDo(print())
				.andReturn();
	}

	@Test
	@SneakyThrows
	public void statisticControllerShouldReturnOkToGetStats() {
		mockMvc.perform(post("/event")
						.contentType(MediaType.APPLICATION_JSON)
						.content(String.format("%s,0.0442672968,1282509067", System.currentTimeMillis())))
				.andExpect(status().isAccepted())
				.andDo(print())
				.andReturn();

		mockMvc.perform(get("/stats"))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();
	}

	@Test
	@SneakyThrows
	public void statisticControllerShouldReturnOkToGetStatsWithoutInit() {
		mockMvc.perform(get("/stats"))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();
	}

}
