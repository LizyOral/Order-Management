package com.example.orderservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createOrderReturnsCreatedAndPersistsOrder() throws Exception {
        String payload = "{\"productName\":\"Laptop\",\"quantity\":2,\"price\":999.99}";

        mockMvc.perform(post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.productName").value("Laptop"))
            .andExpect(jsonPath("$.quantity").value(2))
            .andExpect(jsonPath("$.price").value(999.99));
    }

    @Test
    void invalidOrderRequestReturnsBadRequest() throws Exception {
        String payload = "{\"productName\":\"\",\"quantity\":0,\"price\":0}";

        mockMvc.perform(post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
            .andExpect(status().isBadRequest());
    }

    @Test
    void getOrderByIdReturnsSavedOrder() throws Exception {
        String payload = "{\"productName\":\"Monitor\",\"quantity\":1,\"price\":249.99}";

        String createdOrder = mockMvc.perform(post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getContentAsString();

        Long id = objectMapper.readTree(createdOrder).get("id").asLong();

        mockMvc.perform(get("/api/v1/orders/{id}", id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.productName").value("Monitor"));
    }
}
