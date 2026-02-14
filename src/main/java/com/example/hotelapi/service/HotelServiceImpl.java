package com.example.hotelapi.service;

import com.example.hotelapi.dto.mapper.HotelMapper;
import com.example.hotelapi.dto.request.HotelCreateRequest;
import com.example.hotelapi.dto.response.HotelDetailResponse;
import com.example.hotelapi.dto.response.HotelSummaryResponse;
import com.example.hotelapi.exception.ResourceNotFoundException;
import com.example.hotelapi.model.Amenity;  // Добавьте этот импорт
import com.example.hotelapi.model.Hotel;
import com.example.hotelapi.repository.AmenityRepository;
import com.example.hotelapi.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final AmenityRepository amenityRepository;
    private final HotelMapper hotelMapper;

    @Override
    @Transactional(readOnly = true)
    public List<HotelSummaryResponse> getAllHotels() {
        log.info("Fetching all hotels");
        return hotelRepository.findAll().stream()
                .map(hotelMapper::toSummaryResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public HotelDetailResponse getHotelById(Long id) {
        log.info("Fetching hotel by id: {}", id);
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + id));
        return hotelMapper.toDetailResponse(hotel);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HotelSummaryResponse> searchHotels(String name, String brand, String city, String country, String amenity) {
        log.info("Searching hotels with params - name: {}, brand: {}, city: {}, country: {}, amenity: {}",
                name, brand, city, country, amenity);
        return hotelRepository.searchHotels(name, brand, city, country, amenity).stream()
                .map(hotelMapper::toSummaryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public HotelSummaryResponse createHotel(HotelCreateRequest request) {
        log.info("Creating new hotel: {}", request.getName());
        Hotel hotel = hotelMapper.toEntity(request);
        Hotel savedHotel = hotelRepository.save(hotel);
        return hotelMapper.toSummaryResponse(savedHotel);
    }

    @Override
    public void addAmenitiesToHotel(Long hotelId, List<String> amenityNames) {
        log.info("Adding amenities to hotel id: {}", hotelId);
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + hotelId));

        List<Amenity> amenities = amenityNames.stream()
                .map(name -> amenityRepository.findByName(name)
                        .orElseGet(() -> amenityRepository.save(Amenity.builder().name(name).build())))
                .collect(Collectors.toList());

        hotel.getAmenities().addAll(amenities);
        hotelRepository.save(hotel);
    }
}