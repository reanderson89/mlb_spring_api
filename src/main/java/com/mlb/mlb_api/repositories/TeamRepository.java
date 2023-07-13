package com.mlb.mlb_api.repositories;

import com.mlb.mlb_api.entities.Team;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.HashMap;
import java.util.List;

public interface TeamRepository extends CrudRepository<Team, Integer> {

    Team findByName(String name);


}
