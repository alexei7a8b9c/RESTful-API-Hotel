package com.example.hotelapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelCreateRequest {
    @NotBlank(message = "Hotel name is required")
    private String name;

    @Builder.Default
    private String description = "";

    @NotBlank(message = "Brand is required")
    private String brand;

    @NotNull(message = "Address is required")
    private AddressRequest address;

    @NotNull(message = "Contacts are required")
    private ContactsRequest contacts;

    @NotNull(message = "Arrival time is required")
    private ArrivalTimeRequest arrivalTime;
}