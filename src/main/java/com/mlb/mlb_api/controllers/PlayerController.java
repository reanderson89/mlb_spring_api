package com.mlb.mlb_api.controllers;

import com.mlb.mlb_api.entities.Player;
import com.mlb.mlb_api.repositories.PlayerRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/players")
public class PlayerController {

    private final PlayerRepository playerRepository;

    public PlayerController(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @PostMapping("/add")
    public Player addPlayer(@RequestBody Player player){
        return playerRepository.save(player);
    }
}
