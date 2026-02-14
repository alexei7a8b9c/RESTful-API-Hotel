package com.example.hotelapi.controller;

import com.example.hotelapi.service.HistogramService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/property-view")
@RequiredArgsConstructor
@Tag(name = "Histogram", description = "Histogram endpoints for hotel statistics")
public class HistogramController {

    private final HistogramService histogramService;

    @GetMapping("/histogram/{param}")
    @Operation(summary = "Get histogram", description = "Returns a histogram of hotels grouped by the specified parameter")
    public ResponseEntity<Map<String, Long>> getHistogram(
            @Parameter(description = "Parameter to group by (brand, city, country, amenities)", required = true)
            @PathVariable String param) {
        return ResponseEntity.ok(histogramService.getHistogram(param));
    }
}