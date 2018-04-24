package com.thilian.se4x.robot.game;

import com.thilian.se4x.robot.game.enums.Technology;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FleetBuildResult {
    List<Fleet> newFleets = new ArrayList<>();
    Map<Technology, Integer> newTechs= new HashMap<>();
    private AlienPlayer alienPlayer;

    public FleetBuildResult(AlienPlayer alienPlayer){
        this.alienPlayer = alienPlayer;
    }

    public List<Fleet> getNewFleets() {
        return newFleets;
    }

    public void addNewFleet(Fleet fleet){
        newFleets.add(fleet);
    }

    public Map<Technology, Integer> getNewTechs() {
        return newTechs;
    }

    public void addNewTech(Technology technology, Integer level){
        newTechs.put(technology, level);
    }

    public AlienPlayer getAlienPlayer() {
        return alienPlayer;
    }
}
