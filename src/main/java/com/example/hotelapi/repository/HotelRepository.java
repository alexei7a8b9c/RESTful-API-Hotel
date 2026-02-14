package com.example.hotelapi.repository;

import com.example.hotelapi.model.Hotel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    @EntityGraph(attributePaths = {"amenities"})
    @Override
    List<Hotel> findAll();

    @EntityGraph(attributePaths = {"amenities"})
    @Override
    Optional<Hotel> findById(Long id);

    @Query("SELECT DISTINCT h FROM Hotel h " +
            "LEFT JOIN FETCH h.amenities a " +
            "WHERE (:name IS NULL OR LOWER(h.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:brand IS NULL OR LOWER(h.brand) LIKE LOWER(CONCAT('%', :brand, '%'))) " +
            "AND (:city IS NULL OR LOWER(h.address.city) LIKE LOWER(CONCAT('%', :city, '%'))) " +
            "AND (:country IS NULL OR LOWER(h.address.country) LIKE LOWER(CONCAT('%', :country, '%'))) " +
            "AND (:amenity IS NULL OR LOWER(a.name) LIKE LOWER(CONCAT('%', :amenity, '%')))")
    List<Hotel> searchHotels(@Param("name") String name,
                             @Param("brand") String brand,
                             @Param("city") String city,
                             @Param("country") String country,
                             @Param("amenity") String amenity);

    @Query("SELECT h.address.city as key, COUNT(h) as value FROM Hotel h GROUP BY h.address.city")
    List<Object[]> countByCity();

    @Query("SELECT h.address.country as key, COUNT(h) as value FROM Hotel h GROUP BY h.address.country")
    List<Object[]> countByCountry();

    @Query("SELECT h.brand as key, COUNT(h) as value FROM Hotel h GROUP BY h.brand")
    List<Object[]> countByBrand();

    @Query("SELECT a.name as key, COUNT(h) as value FROM Hotel h JOIN h.amenities a GROUP BY a.name")
    List<Object[]> countByAmenity();
}