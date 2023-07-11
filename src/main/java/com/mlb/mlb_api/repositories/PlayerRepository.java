package com.mlb.mlb_api.repositories;

import com.mlb.mlb_api.entities.Player;
import com.mlb.mlb_api.entities.Team;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public interface PlayerRepository extends CrudRepository<Player, Integer> {
ArrayList<Player> findAllByCurrentTeam(Team team);
}
