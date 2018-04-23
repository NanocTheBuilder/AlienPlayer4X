package com.thilian.se4x.robot.game;

public class EconPhaseResult extends EconRollResult {
    private Fleet fleet;
    private AlienPlayer alienPlayer;

    public EconPhaseResult(AlienPlayer alienPlayer) {
        this.alienPlayer = alienPlayer;
    }

    public void setFleet(Fleet fleet) {
        this.fleet = fleet;
    }

    public Fleet getFleet() {
        return fleet;
    }

    public AlienPlayer getAlienPlayer() {
        return alienPlayer;
    }
}