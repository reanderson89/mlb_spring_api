package com.mlb.mlb_api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;

@Entity
@Table(name = "players")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Integer id;
    @Column(nullable = false)
    private String name;
    private Integer age;
    @Column(name = "years_of_experience")
    private Double yearsOfExperience;
//            (use the @Column annotation and set the name = “years_of_experience” so that it keeps to the naming convention for mysql)
    private Double rating;

    @ManyToOne
    @JoinColumn(name = "current_team_id") // foreign key
    @JsonIgnore
    private Team currentTeam;

//    public Player(String teamName) {
//        this.teamName = teamName;
//        this.name = "";
//        this.age = null;
//        this.rating = null;
//        this.yearsOfExperience = null;
//    }
//
//    public Player(){
//
//    }


    public Team getCurrentTeam() {
        return currentTeam;
    }

    public void setCurrentTeam(Team currentTeam) {
        this.currentTeam = currentTeam;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(Double yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

}
