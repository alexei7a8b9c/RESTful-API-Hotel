package com.example.hotelapi.dto.mapper;

import com.example.hotelapi.dto.request.AddressRequest;
import com.example.hotelapi.dto.request.ArrivalTimeRequest;
import com.example.hotelapi.dto.request.ContactsRequest;
import com.example.hotelapi.dto.request.HotelCreateRequest;
import com.example.hotelapi.dto.response.AddressResponse;
import com.example.hotelapi.dto.response.ArrivalTimeResponse;
import com.example.hotelapi.dto.response.ContactsResponse;
import com.example.hotelapi.dto.response.HotelDetailResponse;
import com.example.hotelapi.dto.response.HotelSummaryResponse;
import com.example.hotelapi.model.Address;
import com.example.hotelapi.model.ArrivalTime;
import com.example.hotelapi.model.Contacts;
import com.example.hotelapi.model.Hotel;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-14T18:33:47+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class HotelMapperImpl implements HotelMapper {

    @Override
    public Hotel toEntity(HotelCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        Hotel hotel = new Hotel();

        hotel.setName( request.getName() );
        hotel.setDescription( request.getDescription() );
        hotel.setBrand( request.getBrand() );
        hotel.setAddress( toEntity( request.getAddress() ) );
        hotel.setContacts( toEntity( request.getContacts() ) );
        hotel.setArrivalTime( toEntity( request.getArrivalTime() ) );

        return hotel;
    }

    @Override
    public Address toEntity(AddressRequest request) {
        if ( request == null ) {
            return null;
        }

        Address.AddressBuilder address = Address.builder();

        address.houseNumber( request.getHouseNumber() );
        address.street( request.getStreet() );
        address.city( request.getCity() );
        address.country( request.getCountry() );
        address.postCode( request.getPostCode() );

        return address.build();
    }

    @Override
    public Contacts toEntity(ContactsRequest request) {
        if ( request == null ) {
            return null;
        }

        Contacts.ContactsBuilder contacts = Contacts.builder();

        contacts.phone( request.getPhone() );
        contacts.email( request.getEmail() );

        return contacts.build();
    }

    @Override
    public ArrivalTime toEntity(ArrivalTimeRequest request) {
        if ( request == null ) {
            return null;
        }

        ArrivalTime.ArrivalTimeBuilder arrivalTime = ArrivalTime.builder();

        arrivalTime.checkIn( request.getCheckIn() );
        arrivalTime.checkOut( request.getCheckOut() );

        return arrivalTime.build();
    }

    @Override
    public HotelSummaryResponse toSummaryResponse(Hotel hotel) {
        if ( hotel == null ) {
            return null;
        }

        HotelSummaryResponse.HotelSummaryResponseBuilder hotelSummaryResponse = HotelSummaryResponse.builder();

        hotelSummaryResponse.phone( hotelContactsPhone( hotel ) );
        hotelSummaryResponse.id( hotel.getId() );
        hotelSummaryResponse.name( hotel.getName() );
        hotelSummaryResponse.description( hotel.getDescription() );

        hotelSummaryResponse.address( formatAddress(hotel) );

        return hotelSummaryResponse.build();
    }

    @Override
    public HotelDetailResponse toDetailResponse(Hotel hotel) {
        if ( hotel == null ) {
            return null;
        }

        HotelDetailResponse.HotelDetailResponseBuilder hotelDetailResponse = HotelDetailResponse.builder();

        hotelDetailResponse.id( hotel.getId() );
        hotelDetailResponse.name( hotel.getName() );
        hotelDetailResponse.description( hotel.getDescription() );
        hotelDetailResponse.brand( hotel.getBrand() );
        hotelDetailResponse.address( toResponse( hotel.getAddress() ) );
        hotelDetailResponse.contacts( toResponse( hotel.getContacts() ) );
        hotelDetailResponse.arrivalTime( toResponse( hotel.getArrivalTime() ) );

        hotelDetailResponse.amenities( mapAmenities(hotel) );

        return hotelDetailResponse.build();
    }

    @Override
    public AddressResponse toResponse(Address address) {
        if ( address == null ) {
            return null;
        }

        AddressResponse.AddressResponseBuilder addressResponse = AddressResponse.builder();

        addressResponse.houseNumber( address.getHouseNumber() );
        addressResponse.street( address.getStreet() );
        addressResponse.city( address.getCity() );
        addressResponse.country( address.getCountry() );
        addressResponse.postCode( address.getPostCode() );

        return addressResponse.build();
    }

    @Override
    public ContactsResponse toResponse(Contacts contacts) {
        if ( contacts == null ) {
            return null;
        }

        ContactsResponse.ContactsResponseBuilder contactsResponse = ContactsResponse.builder();

        contactsResponse.phone( contacts.getPhone() );
        contactsResponse.email( contacts.getEmail() );

        return contactsResponse.build();
    }

    @Override
    public ArrivalTimeResponse toResponse(ArrivalTime arrivalTime) {
        if ( arrivalTime == null ) {
            return null;
        }

        ArrivalTimeResponse.ArrivalTimeResponseBuilder arrivalTimeResponse = ArrivalTimeResponse.builder();

        arrivalTimeResponse.checkIn( arrivalTime.getCheckIn() );
        arrivalTimeResponse.checkOut( arrivalTime.getCheckOut() );

        return arrivalTimeResponse.build();
    }

    private String hotelContactsPhone(Hotel hotel) {
        if ( hotel == null ) {
            return null;
        }
        Contacts contacts = hotel.getContacts();
        if ( contacts == null ) {
            return null;
        }
        String phone = contacts.getPhone();
        if ( phone == null ) {
            return null;
        }
        return phone;
    }
}
