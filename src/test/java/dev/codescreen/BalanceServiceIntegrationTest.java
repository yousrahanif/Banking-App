package dev.codescreen;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.codescreen.BalanceService.TransactionRequest;

@SpringBootTest
@AutoConfigureMockMvc
class BalanceServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Test
    void testProcessLoadIntegration() throws Exception {
        TransactionRequest request = new TransactionRequest();
        request.setUserId(123);
        request.setAmount(50.0);

        mockMvc.perform(MockMvcRequestBuilders.put("/loads")
                .content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("50.0"));
    }


    @Test
    void testProcessAuthorizationIntegration() throws Exception {
        TransactionRequest loadRequest = new TransactionRequest();
        loadRequest.setUserId(12345);
        loadRequest.setAmount(50.0);

        mockMvc.perform(MockMvcRequestBuilders.put("/loads")
                .content(asJsonString(loadRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        TransactionRequest authRequest = new TransactionRequest();
        authRequest.setUserId(12345);
        authRequest.setAmount(30.0);

        mockMvc.perform(MockMvcRequestBuilders.put("/authorizations")
                .content(asJsonString(authRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string("20.0")); // Expect the updated balance after authorization
    }

    

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}



