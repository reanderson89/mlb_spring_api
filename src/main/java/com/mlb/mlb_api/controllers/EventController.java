package com.mlb.mlb_api.controllers;

import com.mlb.mlb_api.entities.Event;
import com.mlb.mlb_api.services.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Event>> findAllEvents(){
        return ResponseEntity.ok(eventService.findAllEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Integer id){
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    @PostMapping
    public ResponseEntity<Event> addEvent(@RequestBody Event event){
        Event savedEvent = eventService.addEvent(event);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/players/{id}")
                .buildAndExpand(savedEvent.getId())
                .toUri();

        return ResponseEntity.created(location).body(event);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event>  updatedEvent(@PathVariable Integer id, @RequestBody Event updates){
        return ResponseEntity.ok(eventService.updateEvent(id, updates));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> deleteEvent(@PathVariable Integer id){

        HashMap<String, Object> responseMap = eventService.deleteEvent(id);

        if(responseMap.get("playerInfo") == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMap);
        }

        return ResponseEntity.ok(responseMap);
    }

}
