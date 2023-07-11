package com.mlb.mlb_api.services;

import com.mlb.mlb_api.entities.Event;
import com.mlb.mlb_api.entities.Player;
import com.mlb.mlb_api.entities.Team;
import com.mlb.mlb_api.repositories.EventRepository;
import com.mlb.mlb_api.repositories.PlayerRepository;
import com.mlb.mlb_api.repositories.TeamRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final EventRepository eventRepository;
    private final PlayerRepository playerRepository;

    public TeamService(TeamRepository teamRepository,
                       EventRepository eventRepository,
                       PlayerRepository playerRepository) {
        this.teamRepository = teamRepository;
        this.eventRepository = eventRepository;
        this.playerRepository = playerRepository;
    }

    public Iterable<Team> findAllTeams(){
        return teamRepository.findAll();
    }

    public Team getTeamById(Integer id){
        Optional<Team> optionalTeam = teamRepository.findById(id);

        if(optionalTeam.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found with id: " + id);
        }
//        gets the team out of the optional container and returns it to the controller
        return optionalTeam.get();
    }

    public ArrayList<Player> findAllPlayersByTeamName(String name){
        Team team = teamRepository.findByName(name);
        return playerRepository.findAllByCurrentTeam(team);
    }

    public Team addTeam(Team team){
        return teamRepository.save(team);
    }

    public Team updateTeam(Integer id, Team updates){
        Team teamToUpdate = getTeamById(id);

        if(!updates.getName().isEmpty()){
            teamToUpdate.setName(updates.getName());
        }
        if(updates.getLocation() != null){
            teamToUpdate.setLocation(updates.getLocation());
        }

        return teamRepository.save(teamToUpdate);
    }

    public HashMap<String, Object> deleteTeam(Integer id){
        HashMap<String, Object> responseMap = new HashMap<>();

        Optional<Team> optionalTeam = teamRepository.findById(id);

        if(optionalTeam.isEmpty()){
//            if the team does not exist, this is what will be returned
            responseMap.put("wasDeleted", false);
            responseMap.put("teamInfo", null);
            responseMap.put("Message", "Team not found with id: " + id);
            return responseMap;
        }

        teamRepository.deleteById(id);
        responseMap.put("wasDeleted", true);
        responseMap.put("teamInfo", optionalTeam.get());

        return responseMap;
    }

}
