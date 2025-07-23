package com.company.SafarSaathi.companion_service.client;


import com.company.SafarSaathi.companion_service.dtos.TripDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "trip-service")
public interface TripClient {

    @GetMapping("/api/v1/trip/core/{tripId}")
    TripDto getTripById(@PathVariable("tripId") Long tripId);
}
