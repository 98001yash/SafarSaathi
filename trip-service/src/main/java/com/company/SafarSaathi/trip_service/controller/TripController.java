package com.company.SafarSaathi.trip_service.controller;


import com.company.SafarSaathi.trip_service.dtos.TripCreateRequestDto;
import com.company.SafarSaathi.trip_service.dtos.TripDto;
import com.company.SafarSaathi.trip_service.dtos.TripUpdateRequestDto;
import com.company.SafarSaathi.trip_service.service.TripService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/core")
@Slf4j
public class TripController {

    private final TripService tripService;

    @PostMapping
    public ResponseEntity<TripDto> createTrip(@RequestBody TripCreateRequestDto request){
        log.info("Creating new trip");
        TripDto trip = tripService.createTrip(request);
        return new ResponseEntity<>(trip, HttpStatus.CREATED);
    }

    @GetMapping("/{tripId}")
    public ResponseEntity<TripDto> getTripById(@PathVariable Long tripId){
        log.info("Fetching trip with id: {}", tripId);
        TripDto trip = tripService.getTripById(tripId);
        return ResponseEntity.ok(trip);
    }

    @GetMapping("/me")
    public ResponseEntity<List<TripDto>> getAllMyTrips(){
        log.info("Fetching all trips for current user");
        List<TripDto> trips = tripService.getAllMyTrips();
        return ResponseEntity.ok(trips);
    }

    @GetMapping("/public")
    public ResponseEntity<List<TripDto>> getAllPublicTrips(){
        log.info("Fetching all public trips");
        List<TripDto> trips = tripService.getAllPublicTrips();
        return ResponseEntity.ok(trips);
    }
    @PutMapping("/{tripId}")
    public ResponseEntity<TripDto> updateTrip(
            @PathVariable Long tripId,
            @RequestBody TripUpdateRequestDto request
    ) {
        log.info("Updating trip with ID: {}", tripId);
        TripDto trip = tripService.updateTrip(tripId, request);
        return ResponseEntity.ok(trip);
    }

    @DeleteMapping("/{tripId}")
    public ResponseEntity<Void> deleteTrip(@PathVariable Long tripId) {
        log.info("Deleting trip with ID: {}", tripId);
        tripService.deleteTrip(tripId);
        return ResponseEntity.noContent().build();
    }


    @PatchMapping("/{tripId}/start")
    public ResponseEntity<TripDto> startTrip(@PathVariable Long tripId) {
        log.info("Starting trip with ID: {}", tripId);
        TripDto trip = tripService.startTrip(tripId);
        return ResponseEntity.ok(trip);
    }


    @PatchMapping("/{tripId}/complete")
    public ResponseEntity<TripDto> completeTrip(@PathVariable Long tripId) {
        log.info("Completing trip with ID: {}", tripId);
        TripDto trip = tripService.completeTrip(tripId);
        return ResponseEntity.ok(trip);
    }

    @PatchMapping("/{tripId}/cancel")
    public ResponseEntity<TripDto> cancelTrip(@PathVariable Long tripId) {
        log.info("Cancelling trip with ID: {}", tripId);
        TripDto trip = tripService.cancelTrip(tripId);
        return ResponseEntity.ok(trip);
    }
}
