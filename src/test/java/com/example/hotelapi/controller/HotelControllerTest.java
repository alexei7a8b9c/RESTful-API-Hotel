package com.example.hotelapi.controller;

import com.example.hotelapi.dto.request.HotelCreateRequest;
import com.example.hotelapi.dto.response.HotelSummaryResponse;
import com.example.hotelapi.service.HotelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HotelController.class)
class HotelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private HotelService hotelService;

    private HotelSummaryResponse testHotel;

    @BeforeEach
    void setUp() {
        testHotel = HotelSummaryResponse.builder()
                .id(1L)
                .name("Test Hotel")
                .description("Test Description")
                .address("Test Address")
                .phone("+1234567890")
                .build();
    }

    @Test
    void getAllHotels_ShouldReturnListOfHotels() throws Exception {
        List<HotelSummaryResponse> hotels = Arrays.asList(testHotel);
        when(hotelService.getAllHotels()).thenReturn(hotels);

        mockMvc.perform(get("/property-view/hotels"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Test Hotel"));
    }

    @Test
    void getHotelById_ShouldReturnHotel_WhenExists() throws Exception {
        when(hotelService.getHotelById(1L)).thenReturn(any());

        mockMvc.perform(get("/property-view/hotels/1"))
                .andExpect(status().isOk());
    }

    @Test
    void createHotel_ShouldReturnCreatedHotel() throws Exception {
        HotelCreateRequest request = new HotelCreateRequest();
        request.setName("New Hotel");

        when(hotelService.createHotel(any(HotelCreateRequest.class))).thenReturn(testHotel);

        mockMvc.perform(post("/property-view/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Hotel"));
    }
}