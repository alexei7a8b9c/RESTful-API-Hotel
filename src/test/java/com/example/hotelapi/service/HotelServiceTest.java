package com.example.hotelapi.service;

import com.example.hotelapi.dto.mapper.HotelMapper;
import com.example.hotelapi.dto.request.HotelCreateRequest;
import com.example.hotelapi.dto.response.HotelSummaryResponse;
import com.example.hotelapi.exception.ResourceNotFoundException;
import com.example.hotelapi.model.Hotel;
import com.example.hotelapi.repository.AmenityRepository;
import com.example.hotelapi.repository.HotelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private AmenityRepository amenityRepository;

    @Mock
    private HotelMapper hotelMapper;

    @InjectMocks
    private HotelServiceImpl hotelService;

    private Hotel testHotel;
    private HotelCreateRequest testRequest;
    private HotelSummaryResponse testResponse;

    @BeforeEach
    void setUp() {
        testHotel = new Hotel();
        testHotel.setId(1L);
        testHotel.setName("Test Hotel");

        testRequest = new HotelCreateRequest();
        testRequest.setName("Test Hotel");

        testResponse = HotelSummaryResponse.builder()
                .id(1L)
                .name("Test Hotel")
                .build();
    }

    @Test
    void getHotelById_ShouldReturnHotel_WhenExists() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(testHotel));
        when(hotelMapper.toDetailResponse(testHotel)).thenReturn(any());

        assertDoesNotThrow(() -> hotelService.getHotelById(1L));
    }

    @Test
    void getHotelById_ShouldThrowException_WhenNotFound() {
        when(hotelRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> hotelService.getHotelById(99L));
    }

    @Test
    void createHotel_ShouldReturnCreatedHotel() {
        when(hotelMapper.toEntity(any(HotelCreateRequest.class))).thenReturn(testHotel);
        when(hotelRepository.save(any(Hotel.class))).thenReturn(testHotel);
        when(hotelMapper.toSummaryResponse(any(Hotel.class))).thenReturn(testResponse);

        HotelSummaryResponse result = hotelService.createHotel(testRequest);

        assertNotNull(result);
        assertEquals("Test Hotel", result.getName());
    }
}