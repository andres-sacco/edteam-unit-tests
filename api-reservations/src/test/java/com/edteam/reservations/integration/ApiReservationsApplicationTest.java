package com.edteam.reservations.integration;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tags(@Tag("integration"))
@DisplayName("Check the functionality of the application")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiReservationsApplicationTest {

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Tag("success-case")
    @DisplayName("should return the information of the reservation")
    @Test
    void should_get_a_reservation() throws Exception {
        mockMvc.perform(get("/reservation/1").contentType("application/json")).andExpect(status().is2xxSuccessful());
    }

    @Tag("error-case")
    @DisplayName("should not return the information of the reservation")
    @Test
    void should_not_get_a_reservation() throws Exception {
        mockMvc.perform(get("/reservation/100").contentType("application/json")).andExpect(status().isNotFound());
    }

}
