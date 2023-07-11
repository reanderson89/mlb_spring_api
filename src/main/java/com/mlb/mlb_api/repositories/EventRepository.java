package com.mlb.mlb_api.repositories;

import com.mlb.mlb_api.entities.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Integer> {

    Iterable<Event> findAllByTeamId(Integer id);
}
