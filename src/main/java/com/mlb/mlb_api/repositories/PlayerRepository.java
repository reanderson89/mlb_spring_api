package com.mlb.mlb_api.repositories;

import com.mlb.mlb_api.entities.Player;
import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository<Player, Integer> {

}
