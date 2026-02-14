package com.example.hotelapi.dto.mapper;

import com.example.hotelapi.dto.request.*;
import com.example.hotelapi.dto.response.*;
import com.example.hotelapi.model.*;
import org.mapstruct.*;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HotelMapper {

    // Request to Entity mappings
    Hotel toEntity(HotelCreateRequest request);

    Address toEntity(AddressRequest request);

    Contacts toEntity(ContactsRequest request);

    ArrivalTime toEntity(ArrivalTimeRequest request);

    // Entity to Response mappings
    @Mapping(target = "address", expression = "java(formatAddress(hotel))")
    @Mapping(target = "phone", source = "contacts.phone")
    HotelSummaryResponse toSummaryResponse(Hotel hotel);

    @Mapping(target = "amenities", expression = "java(hotel.getAmenities().stream().map(Amenity::getName).collect(java.util.stream.Collectors.toList()))")
    HotelDetailResponse toDetailResponse(Hotel hotel);

    AddressResponse toResponse(Address address);

    ContactsResponse toResponse(Contacts contacts);

    ArrivalTimeResponse toResponse(ArrivalTime arrivalTime);

    default String formatAddress(Hotel hotel) {
        if (hotel.getAddress() == null) return "";
        Address addr = hotel.getAddress();
        return String.format("%d %s, %s, %s, %s",
                addr.getHouseNumber(),
                addr.getStreet(),
                addr.getCity(),
                addr.getPostCode(),
                addr.getCountry()
        );
    }
}