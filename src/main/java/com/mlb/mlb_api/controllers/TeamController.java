package com.mlb.mlb_api.controllers;


import com.mlb.mlb_api.entities.Event;
import com.mlb.mlb_api.entities.Player;
import com.mlb.mlb_api.entities.Team;
import com.mlb.mlb_api.services.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Team>> findAllTeams(){
        return ResponseEntity.ok(teamService.findAllTeams());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable Integer id){
        return ResponseEntity.ok(teamService.getTeamById(id));
    }

    @GetMapping("/name")
    public ResponseEntity<ArrayList<Player>> getAllPlayersByTeamName(@RequestParam String name){
        return ResponseEntity.ok(teamService.findAllPlayersByTeamName(name));
    }

    @PostMapping
    public ResponseEntity<Team> addTeam(@RequestBody Team player){
        Team savedTeam = teamService.addTeam(player);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/players/{id}")
                .buildAndExpand(savedTeam.getId())
                .toUri();

        return ResponseEntity.created(location).body(player);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Team>  updatedTeam(@PathVariable Integer id, @RequestBody Team updates){
        return ResponseEntity.ok(teamService.updateTeam(id, updates));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> deleteTeam(@PathVariable Integer id){

        HashMap<String, Object> responseMap = teamService.deleteTeam(id);

        if(responseMap.get("playerInfo") == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMap);
        }

        return ResponseEntity.ok(responseMap);
    }

}
