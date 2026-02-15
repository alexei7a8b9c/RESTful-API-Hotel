package com.example.hotelapi.service;

import com.example.hotelapi.dto.mapper.HotelMapper;
import com.example.hotelapi.dto.request.HotelCreateRequest;
import com.example.hotelapi.dto.response.HotelDetailResponse;
import com.example.hotelapi.dto.response.HotelSummaryResponse;
import com.example.hotelapi.exception.ResourceNotFoundException;
import com.example.hotelapi.model.Address;
import com.example.hotelapi.model.ArrivalTime;
import com.example.hotelapi.model.Contacts;
import com.example.hotelapi.model.Hotel;
import com.example.hotelapi.repository.AmenityRepository;
import com.example.hotelapi.repository.HotelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
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
    private HotelSummaryResponse testSummaryResponse;
    private HotelDetailResponse testDetailResponse;

    @BeforeEach
    void setUp() {
        Address address = Address.builder()
                .houseNumber(9)
                .street("Test Street")
                .city("Test City")
                .country("Test Country")
                .postCode("12345")
                .build();

        Contacts contacts = Contacts.builder()
                .phone("+1234567890")
                .email("test@test.com")
                .build();

        ArrivalTime arrivalTime = ArrivalTime.builder()
                .checkIn("14:00")
                .checkOut("12:00")
                .build();

        testHotel = Hotel.builder()
                .id(1L)
                .name("Test Hotel")
                .description("Test Description")
                .brand("Test Brand")
                .address(address)
                .contacts(contacts)
                .arrivalTime(arrivalTime)
                .build();

        testRequest = new HotelCreateRequest();
        testRequest.setName("Test Hotel");
        testRequest.setDescription("Test Description");
        testRequest.setBrand("Test Brand");

        testSummaryResponse = HotelSummaryResponse.builder()
                .id(1L)
                .name("Test Hotel")
                .description("Test Description")
                .address("9 Test Street, Test City, 12345, Test Country")
                .phone("+1234567890")
                .build();

        testDetailResponse = HotelDetailResponse.builder()
                .id(1L)
                .name("Test Hotel")
                .description("Test Description")
                .brand("Test Brand")
                .amenities(Arrays.asList("Free WiFi", "Free parking"))
                .build();
    }

    @Test
    void getAllHotels_ShouldReturnListOfHotels() {
        when(hotelRepository.findAll()).thenReturn(Arrays.asList(testHotel));
        when(hotelMapper.toSummaryResponse(any(Hotel.class))).thenReturn(testSummaryResponse);

        List<HotelSummaryResponse> result = hotelService.getAllHotels();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Hotel", result.get(0).getName());
    }

    @Test
    void getHotelById_ShouldReturnHotel_WhenExists() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(testHotel));
        when(hotelMapper.toDetailResponse(testHotel)).thenReturn(testDetailResponse);

        HotelDetailResponse result = hotelService.getHotelById(1L);

        assertNotNull(result);
        assertEquals("Test Hotel", result.getName());
        assertEquals("Test Brand", result.getBrand());
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
        when(hotelMapper.toSummaryResponse(any(Hotel.class))).thenReturn(testSummaryResponse);

        HotelSummaryResponse result = hotelService.createHotel(testRequest);

        assertNotNull(result);
        assertEquals("Test Hotel", result.getName());
        verify(hotelRepository, times(1)).save(any(Hotel.class));
    }

    @Test
    void searchHotels_ShouldReturnFilteredResults() {
        List<String> amenities = Arrays.asList("WiFi", "parking");
        when(hotelRepository.searchHotels(eq("Test"), eq("Brand"), eq("City"), eq("Country"), eq("WiFi,parking")))
                .thenReturn(Arrays.asList(testHotel));
        when(hotelMapper.toSummaryResponse(any(Hotel.class))).thenReturn(testSummaryResponse);

        List<HotelSummaryResponse> result = hotelService.searchHotels("Test", "Brand", "City", "Country", amenities);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void searchHotels_WithNullAmenities_ShouldWork() {
        when(hotelRepository.searchHotels(eq("Test"), eq("Brand"), eq("City"), eq("Country"), isNull()))
                .thenReturn(Arrays.asList(testHotel));
        when(hotelMapper.toSummaryResponse(any(Hotel.class))).thenReturn(testSummaryResponse);

        List<HotelSummaryResponse> result = hotelService.searchHotels("Test", "Brand", "City", "Country", null);

        assertNotNull(result);
        assertEquals(1, result.size());
    }
}