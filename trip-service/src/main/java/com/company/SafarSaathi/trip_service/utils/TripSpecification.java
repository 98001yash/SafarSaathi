package com.company.SafarSaathi.trip_service.utils;

import com.company.SafarSaathi.trip_service.entity.Trip;
import com.company.SafarSaathi.trip_service.enums.ModeOfTravel;
import com.company.SafarSaathi.trip_service.enums.TripStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class TripSpecification {

    public static Specification<Trip> hasDestination(String destination){
        return (root, query, ch)->
                destination==null ? null : ch.like(ch.lower(root.get("destination")), "%" + destination.toLowerCase() + "%");
    }

    public static Specification<Trip> hasOrigin(String origin) {
        return (root, query, cb) ->
                origin == null ? null : cb.like(cb.lower(root.get("origin")), "%" + origin.toLowerCase() + "%");
    }

    public static Specification<Trip> hasMode(ModeOfTravel mode) {
        return (root, query, cb) ->
                mode == null ? null : cb.equal(root.get("modeOfTravel"), mode);
    }

    public static Specification<Trip> hasStatus(TripStatus status) {
        return (root, query, cb) ->
                status == null ? null : cb.equal(root.get("status"), status);
    }

    public static Specification<Trip> startDateBetween(LocalDateTime from, LocalDateTime to) {
        return (root, query, cb) -> {
            if (from != null && to != null)
                return cb.between(root.get("startDate"), from, to);
            else if (from != null)
                return cb.greaterThanOrEqualTo(root.get("startDate"), from);
            else if (to != null)
                return cb.lessThanOrEqualTo(root.get("startDate"), to);
            else
                return null;
        };
    }
}
