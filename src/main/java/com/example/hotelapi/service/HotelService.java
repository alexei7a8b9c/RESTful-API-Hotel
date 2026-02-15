package com.example.hotelapi.service;

import com.example.hotelapi.dto.request.HotelCreateRequest;
import com.example.hotelapi.dto.response.HotelDetailResponse;
import com.example.hotelapi.dto.response.HotelSummaryResponse;
import java.util.List;

public interface HotelService {
    List<HotelSummaryResponse> getAllHotels();
    HotelDetailResponse getHotelById(Long id);
    List<HotelSummaryResponse> searchHotels(String name, String brand, String city, String country, List<String> amenities);
    HotelSummaryResponse createHotel(HotelCreateRequest request);
    void addAmenitiesToHotel(Long hotelId, List<String> amenities);
}