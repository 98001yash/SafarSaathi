package com.company.SafarSaathi.trip_service.service;


import com.company.SafarSaathi.trip_service.auth.UserContextHolder;
import com.company.SafarSaathi.trip_service.dtos.TripCreateRequestDto;
import com.company.SafarSaathi.trip_service.dtos.TripDto;
import com.company.SafarSaathi.trip_service.entity.Trip;
import com.company.SafarSaathi.trip_service.enums.TripStatus;
import com.company.SafarSaathi.trip_service.exceptions.ResourceNotFoundException;
import com.company.SafarSaathi.trip_service.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TripService {

    private final TripRepository tripRepository;
    private final ModelMapper modelMapper;


    public TripDto createTrip(TripCreateRequestDto request){
        Long userId = UserContextHolder.getCurrentUserId();

        Trip trip = modelMapper.map(request, Trip.class);
        trip.setUserId(userId);
        trip.setCurrentTravelers(1);
        trip.setStatus(TripStatus.PLANNED);

        Trip savedTrip = tripRepository.save(trip);
        log.info("Trip created with ID: {}", savedTrip.getId());

        return modelMapper.map(savedTrip, TripDto.class);
    }


    public TripDto getTripById(Long id){
        Trip trip = tripRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Trip not found with ID: "+id));

        return modelMapper.map(trip, TripDto.class);
    }
}
