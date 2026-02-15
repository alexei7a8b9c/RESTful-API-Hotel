package com.example.hotelapi.controller;

import com.example.hotelapi.dto.request.HotelCreateRequest;
import com.example.hotelapi.dto.response.HotelDetailResponse;
import com.example.hotelapi.dto.response.HotelSummaryResponse;
import com.example.hotelapi.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/property-view")
@RequiredArgsConstructor
@Tag(name = "Hotels", description = "Hotel management endpoints")
public class HotelController {

    private final HotelService hotelService;

    @GetMapping("/hotels")
    @Operation(summary = "Get all hotels", description = "Returns a list of all hotels with brief information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<HotelSummaryResponse>> getAllHotels() {
        return ResponseEntity.ok(hotelService.getAllHotels());
    }

    @GetMapping("/hotels/{id}")
    @Operation(summary = "Get hotel by ID", description = "Returns detailed information about a specific hotel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved hotel"),
            @ApiResponse(responseCode = "404", description = "Hotel not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<HotelDetailResponse> getHotelById(
            @Parameter(description = "Hotel ID", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(hotelService.getHotelById(id));
    }

    @GetMapping("/search")
    @Operation(summary = "Search hotels", description = "Search hotels by various parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved search results"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<HotelSummaryResponse>> searchHotels(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) List<String> amenities) {
        return ResponseEntity.ok(hotelService.searchHotels(name, brand, city, country, amenities));
    }

    @PostMapping("/hotels")
    @Operation(summary = "Create a new hotel", description = "Creates a new hotel and returns its summary information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Hotel successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<HotelSummaryResponse> createHotel(
            @Valid @RequestBody HotelCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(hotelService.createHotel(request));
    }

    @PostMapping("/hotels/{id}/amenities")
    @Operation(summary = "Add amenities to hotel", description = "Adds a list of amenities to a specific hotel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Amenities successfully added"),
            @ApiResponse(responseCode = "404", description = "Hotel not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> addAmenitiesToHotel(
            @Parameter(description = "Hotel ID", required = true)
            @PathVariable Long id,
            @RequestBody List<String> amenities) {
        hotelService.addAmenitiesToHotel(id, amenities);
        return ResponseEntity.ok().build();
    }
}