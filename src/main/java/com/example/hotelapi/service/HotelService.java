package com.example.hotelapi.service;

import com.example.hotelapi.dto.request.HotelCreateRequest;
import com.example.hotelapi.dto.response.HotelDetailResponse;
import com.example.hotelapi.dto.response.HotelSummaryResponse;
import java.util.List;
import java.util.Map;

public interface HotelService {
    List<HotelSummaryResponse> getAllHotels();
    HotelDetailResponse getHotelById(Long id);
    List<HotelSummaryResponse> searchHotels(String name, String brand, String city, String country, String amenity);
    HotelSummaryResponse createHotel(HotelCreateRequest request);
    void addAmenitiesToHotel(Long hotelId, List<String> amenities);
}