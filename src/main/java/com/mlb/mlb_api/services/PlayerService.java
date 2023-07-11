package com.mlb.mlb_api.services;

import com.mlb.mlb_api.entities.Player;
import com.mlb.mlb_api.entities.Team;
import com.mlb.mlb_api.repositories.PlayerRepository;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Transactional
    public Iterable<Player> findAllPlayers(){
        Iterable<Player> allPlayers = playerRepository.findAll();
        for (Player player : allPlayers) {
            Hibernate.initialize(player.getCurrentTeam());
        }
        return allPlayers;
    }

    public Player getPlayerById(Integer id){
        Optional<Player> optionalPlayer = playerRepository.findById(id);

        if(optionalPlayer.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found with id: " + id);
        }
//        gets the player out of the optional container and returns it to the controller
        return optionalPlayer.get();
    }

    public Player addPlayer(Player player){
        return playerRepository.save(player);
    }

    public Player updatePlayer(Integer id, Player updates){
        Player playerToUpdate = getPlayerById(id);

        if(!updates.getName().isEmpty()){
            playerToUpdate.setName(updates.getName());
        }
        if(updates.getAge() != null){
            playerToUpdate.setAge(updates.getAge());
        }
        if(updates.getRating() != null){
            playerToUpdate.setRating(updates.getRating());
        }
        if(updates.getYearsOfExperience() != null){
            playerToUpdate.setYearsOfExperience(updates.getYearsOfExperience());
        }

        return playerRepository.save(playerToUpdate);
    }

    public HashMap<String, Object> deletePlayer(Integer id){
        HashMap<String, Object> responseMap = new HashMap<>();

        Optional<Player> optionalPlayer = playerRepository.findById(id);

        if(optionalPlayer.isEmpty()){
//            if the player does not exist, this is what will be returned
            responseMap.put("wasDeleted", false);
            responseMap.put("playerInfo", null);
            responseMap.put("Message", "Player not found with id: " + id);
            return responseMap;
        }
        playerRepository.deleteById(id);
        responseMap.put("wasDeleted", true);
        responseMap.put("playerInfo", optionalPlayer.get());

        return responseMap;
    }

    public void handlePlayerEvent(Player player, Team team){
        player.setCurrentTeam(team);
        playerRepository.save(player);
    }

}
