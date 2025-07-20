package com.company.SafarSaathi.trip_service.service;


import com.company.SafarSaathi.trip_service.auth.UserContextHolder;
import com.company.SafarSaathi.trip_service.dtos.TripCreateRequestDto;
import com.company.SafarSaathi.trip_service.dtos.TripDto;
import com.company.SafarSaathi.trip_service.dtos.TripUpdateRequestDto;
import com.company.SafarSaathi.trip_service.entity.Trip;
import com.company.SafarSaathi.trip_service.enums.ModeOfTravel;
import com.company.SafarSaathi.trip_service.enums.TripStatus;
import com.company.SafarSaathi.trip_service.exceptions.BadRequestException;
import com.company.SafarSaathi.trip_service.exceptions.ResourceNotFoundException;
import com.company.SafarSaathi.trip_service.repository.TripRepository;
import com.company.SafarSaathi.trip_service.utils.GeocodingUtil;
import com.company.SafarSaathi.trip_service.utils.TripSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TripService {

    private final TripRepository tripRepository;
    private final ModelMapper modelMapper;
    private final GeocodingUtil geocodingUtil;



    public TripDto createTrip(TripCreateRequestDto request){
        Long userId = UserContextHolder.getCurrentUserId();

        Trip trip = modelMapper.map(request, Trip.class);
        trip.setUserId(userId);
        trip.setCurrentTravelers(1);
        trip.setStatus(TripStatus.PLANNED);

        GeocodingUtil.Coordinates originCoords = geocodingUtil.getCoordinates(request.getOrigin());
        if (originCoords != null) {
            trip.setOriginLat(originCoords.getLat());
            trip.setOriginLng(originCoords.getLng());
        }

        // Set coordinates for destination
        GeocodingUtil.Coordinates destCoords = geocodingUtil.getCoordinates(request.getDestination());
        if (destCoords != null) {
            trip.setDestinationLat(destCoords.getLat());
            trip.setDestinationLng(destCoords.getLng());
        }

        log.info("Origin: {}, lat: {}, lng: {}", trip.getOrigin(), trip.getOriginLat(), trip.getOriginLng());
        log.info("Destination: {}, lat: {}, lng: {}", trip.getDestination(), trip.getDestinationLat(), trip.getDestinationLng());


        Trip savedTrip = tripRepository.save(trip);
        log.info("Trip created with ID: {}", savedTrip.getId());

        return modelMapper.map(savedTrip, TripDto.class);
    }


    public TripDto getTripById(Long id){
        Trip trip = tripRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Trip not found with ID: "+id));

        return modelMapper.map(trip, TripDto.class);
    }

    public List<TripDto> getAllMyTrips(){
        Long userId = UserContextHolder.getCurrentUserId();
        List<Trip> trips = tripRepository.findByUserId(userId);
        return trips.stream()
                .map(trip->modelMapper.map(trip, TripDto.class))
                .collect(Collectors.toList());
    }

    public List<TripDto> getAllPublicTrips(){
        List<Trip> trips = tripRepository.findByIsPrivateFalse();
        return trips.stream()
                .map(trip->modelMapper.map(trip, TripDto.class))
                .collect(Collectors.toList());
    }

    public TripDto updateTrip(Long tripId, TripUpdateRequestDto request) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new ResourceNotFoundException("Trip not found with ID: " + tripId));

        Long userId = UserContextHolder.getCurrentUserId();
        if (!trip.getUserId().equals(userId)) {
            throw new BadRequestException("You are not allowed to update this trip");
        }
        TripStatus existingStatus = trip.getStatus();
        modelMapper.map(request, trip);
        trip.setStatus(existingStatus);

        GeocodingUtil.Coordinates originCoords = geocodingUtil.getCoordinates(request.getOrigin());
        if (originCoords != null) {
            trip.setOriginLat(originCoords.getLat());
            trip.setOriginLng(originCoords.getLng());
        }

        // Set coordinates for destination
        GeocodingUtil.Coordinates destCoords = geocodingUtil.getCoordinates(request.getDestination());
        if (destCoords != null) {
            trip.setDestinationLat(destCoords.getLat());
            trip.setDestinationLng(destCoords.getLng());
        }

        log.info("Origin: {}, lat: {}, lng: {}", trip.getOrigin(), trip.getOriginLat(), trip.getOriginLng());
        log.info("Destination: {}, lat: {}, lng: {}", trip.getDestination(), trip.getDestinationLat(), trip.getDestinationLng());


        Trip updatedTrip = tripRepository.save(trip);
        log.info("Trip updated with ID: {}", updatedTrip.getId());
        return modelMapper.map(updatedTrip, TripDto.class);
    }


    public void deleteTrip(Long tripId){
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(()->new ResourceNotFoundException("Trip not found with ID: "+tripId));

        Long userId = UserContextHolder.getCurrentUserId();
        if(!trip.getUserId().equals(userId)){
            throw new BadRequestException("You are not allowed to delete this trip");
        }

        tripRepository.delete(trip);
        log.info("Trip deleted with ID: {}", tripId);
    }

    public TripDto startTrip(Long tripId){
        return updateTripStatus(tripId, TripStatus.ONGOING);
    }

    public TripDto completeTrip(Long tripId){
        return updateTripStatus(tripId, TripStatus.COMPLETED);
    }

    public TripDto cancelTrip(Long tripId){
        return updateTripStatus(tripId, TripStatus.CANCELLED);
    }


    public TripDto updateTripStatus(Long tripId, TripStatus status){
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(()->new ResourceNotFoundException("Trip not found with ID: "+tripId));

        Long userId = UserContextHolder.getCurrentUserId();
        if(!trip.getUserId().equals(userId)){
            throw new BadRequestException("You are not allowed to modify this trip's status");
        }
        trip.setStatus(status);
        Trip saved = tripRepository.save(trip);
        log.info("Trip status updated to {} for ID: {}",status, tripId);
        return modelMapper.map(saved, TripDto.class);
    }

    public Page<TripDto> searchTrips(String destination, String origin, ModeOfTravel modeOfTravel, TripStatus status,
                                     LocalDateTime startDateFrom, LocalDateTime startDateTo,
                                     int page, int size, String sortBy, String direction) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending()
        );

        Specification<Trip> spec = (root, query, cb) -> cb.conjunction();

        if (destination != null && !destination.trim().isEmpty()) {
            spec = spec.and(TripSpecification.hasDestination(destination));
        }
        if (origin != null && !origin.trim().isEmpty()) {
            spec = spec.and(TripSpecification.hasOrigin(origin));
        }
        if (modeOfTravel != null) {
            spec = spec.and(TripSpecification.hasMode(modeOfTravel));
        }
        if (status != null) {
            spec = spec.and(TripSpecification.hasStatus(status));
        }
        if (startDateFrom != null || startDateTo != null) {
            spec = spec.and(TripSpecification.startDateBetween(startDateFrom, startDateTo));
        }

        return tripRepository.findAll(spec, pageable)
                .map(trip -> modelMapper.map(trip, TripDto.class));
    }



}
