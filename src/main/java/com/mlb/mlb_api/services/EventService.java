package com.mlb.mlb_api.services;

import com.mlb.mlb_api.entities.Event;
import com.mlb.mlb_api.entities.Player;
import com.mlb.mlb_api.entities.Team;
import com.mlb.mlb_api.repositories.EventRepository;
import com.mlb.mlb_api.repositories.TeamRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final TeamRepository teamRepository;

    private final PlayerService playerService;

    private final TeamService teamService;

    public EventService(EventRepository eventRepository,
                        TeamRepository teamRepository, PlayerService playerService, TeamService teamService) {
        this.eventRepository = eventRepository;
        this.teamRepository = teamRepository;
        this.playerService = playerService;
        this.teamService = teamService;
    }

    public Iterable<Event> findAllEvents(){
        return eventRepository.findAll();
    }

    public Event getEventById(Integer id){
        Optional<Event> optionalEvent = eventRepository.findById(id);

        if(optionalEvent.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found with id: " + id);
        }
//        gets the event out of the optional container and returns it to the controller
        return optionalEvent.get();
    }


    public Event addEvent(Event event){
        Event savedEvent = eventRepository.save(event);
        Team team = teamService.getTeamById(event.getTeamId());
        Player player = playerService.getPlayerById(event.getPlayerId());
        playerService.handlePlayerEvent(player, team);
        return savedEvent;
    }

    public Event updateEvent(Integer id, Event updates){
        Event eventToUpdate = getEventById(id);

        if(updates.getPlayerId() != null){
            eventToUpdate.setPlayerId(updates.getPlayerId());
        }
        if(updates.getTeamId() != null){
            eventToUpdate.setTeamId(updates.getTeamId());
        }

        return eventRepository.save(eventToUpdate);
    }

    public HashMap<String, Object> deleteEvent(Integer id){
        HashMap<String, Object> responseMap = new HashMap<>();

        Optional<Event> optionalEvent = eventRepository.findById(id);

        if(optionalEvent.isEmpty()){
//            if the event does not exist, this is what will be returned
            responseMap.put("wasDeleted", false);
            responseMap.put("eventInfo", null);
            responseMap.put("Message", "Event not found with id: " + id);
            return responseMap;
        }
        eventRepository.deleteById(id);
        responseMap.put("wasDeleted", true);
        responseMap.put("eventInfo", optionalEvent.get());

        return responseMap;
    }

    public List<HashMap<String, String>> findAllWithPlayerAndTeamNames() {
        return eventRepository.findAllWithPlayerAndTeamNames();
    }
}
