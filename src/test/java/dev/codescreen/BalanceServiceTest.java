package dev.codescreen;

import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.codescreen.BalanceService.Transaction;
import dev.codescreen.BalanceService.TransactionRequest;

@SpringBootTest
@AutoConfigureMockMvc
class BalanceServiceTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BalanceService balanceService;

	private List<Transaction> transactionHistory;
	private TransactionRequest transactionRequest;

	@BeforeEach
	void setUp() {
		transactionHistory = new ArrayList<>();
		transactionHistory.add(new Transaction(100.0, "Load", LocalDateTime.now(), "Success"));
		transactionHistory.add(new Transaction(50.0, "Authorization", LocalDateTime.now(), "Success"));

		transactionRequest = new TransactionRequest();
		transactionRequest.setUserId(123);
		transactionRequest.setAmount(50.0);
	}

	@Test
	void testGetTransactionHistory() throws Exception {
		when(balanceService.getTransactionHistory(123)).thenReturn(transactionHistory);

		mockMvc.perform(MockMvcRequestBuilders.get("/transaction-history?userId=123"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].amount").value(100.0))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].type").value("Authorization"));
	}

	@Test
	void testProcessLoad() throws Exception {
		when(balanceService.processLoad(any(TransactionRequest.class))).thenReturn(150.0);

		mockMvc.perform(MockMvcRequestBuilders.put("/loads").content(asJsonString(transactionRequest))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("150.0"));
	}

	private String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}