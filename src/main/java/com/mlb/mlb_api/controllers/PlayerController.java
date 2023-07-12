package com.mlb.mlb_api.controllers;

import com.mlb.mlb_api.entities.Player;
import com.mlb.mlb_api.services.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Player>> findAllPlayers(){
        return ResponseEntity.ok(playerService.findAllPlayers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable Integer id){
        return ResponseEntity.ok(playerService.getPlayerById(id));
    }

    @PostMapping
    public ResponseEntity<Player> addPlayer(@RequestBody Player player){
        Player savedPlayer = playerService.addPlayer(player);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/players/{id}")
                .buildAndExpand(savedPlayer.getId())
                .toUri();

        return ResponseEntity.created(location).body(player);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Player>  updatedPlayer(@PathVariable Integer id, @RequestBody Player updates){
        return ResponseEntity.ok(playerService.updatePlayer(id, updates));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> deletePlayer(@PathVariable Integer id){

        HashMap<String, Object> responseMap = playerService.deletePlayer(id);

        if(responseMap.get("playerInfo") == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMap);
        }

        return ResponseEntity.ok(responseMap);
    }




}
