package com.company.SafarSaathi.trip_service.utils;

import com.company.SafarSaathi.trip_service.entity.Trip;
import com.company.SafarSaathi.trip_service.enums.ModeOfTravel;
import com.company.SafarSaathi.trip_service.enums.TripStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class TripSpecification {

    public static Specification<Trip> hasDestination(String destination) {
        if (destination == null || destination.trim().isEmpty()) return null;

        return (root, query, cb) ->
                cb.like(cb.lower(root.get("destination")), "%" + destination.toLowerCase() + "%");
    }

    public static Specification<Trip> hasOrigin(String origin) {
        if (origin == null || origin.trim().isEmpty()) return null;

        return (root, query, cb) ->
                cb.like(cb.lower(root.get("origin")), "%" + origin.toLowerCase() + "%");
    }

    public static Specification<Trip> hasMode(ModeOfTravel mode) {
        if (mode == null) return null;

        return (root, query, cb) ->
                cb.equal(root.get("modeOfTravel"), mode);
    }

    public static Specification<Trip> hasStatus(TripStatus status) {
        if (status == null) return null;

        return (root, query, cb) ->
                cb.equal(root.get("status"), status);
    }

    public static Specification<Trip> startDateBetween(LocalDateTime from, LocalDateTime to) {
        if (from == null && to == null) return null;

        return (root, query, cb) -> {
            if (from != null && to != null) {
                return cb.between(root.get("startDate"), from, to);
            } else if (from != null) {
                return cb.greaterThanOrEqualTo(root.get("startDate"), from);
            } else {
                return cb.lessThanOrEqualTo(root.get("startDate"), to);
            }
        };
    }
}
