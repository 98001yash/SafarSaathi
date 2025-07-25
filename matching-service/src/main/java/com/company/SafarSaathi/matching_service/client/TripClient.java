package com.company.SafarSaathi.matching_service.client;


import com.company.SafarSaathi.matching_service.dtos.TripDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "trip-service", path = "/trip/core")
public interface TripClient {

    @GetMapping("/{tripId}")
    TripDto getTripById(@PathVariable("tripId") Long tripId);
}
