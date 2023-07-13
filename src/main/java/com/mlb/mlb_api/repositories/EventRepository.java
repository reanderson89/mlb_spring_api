package com.mlb.mlb_api.repositories;

import com.mlb.mlb_api.entities.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.HashMap;
import java.util.List;

public interface EventRepository extends CrudRepository<Event, Integer> {

    Iterable<Event> findAllByTeamId(Integer id);

    @Query("SELECT new map( " +
            "p.name as playerName, " +
            "t.name as teamName, " +
            "e.type as eventType) " +
            "FROM Event e " +
            "JOIN Player p ON e.playerId = p.id " +
            "JOIN Team t ON e.teamId = t.id")
    List<HashMap<String, String>> findAllWithPlayerAndTeamNames();
}
