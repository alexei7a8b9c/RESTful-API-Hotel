package com.example.hotelapi.service;

import com.example.hotelapi.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class HistogramService {

    private final HotelRepository hotelRepository;

    @Transactional(readOnly = true)
    public Map<String, Long> getHistogram(String param) {
        log.info("Generating histogram for param: {}", param);

        List<Object[]> results = switch (param.toLowerCase()) {
            case "city" -> hotelRepository.countByCity();
            case "country" -> hotelRepository.countByCountry();
            case "brand" -> hotelRepository.countByBrand();
            case "amenities" -> hotelRepository.countByAmenity();
            default -> throw new IllegalArgumentException("Invalid histogram parameter: " + param);
        };

        return results.stream()
                .collect(Collectors.toMap(
                        arr -> (String) arr[0],
                        arr -> (Long) arr[1],
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                ));
    }
}