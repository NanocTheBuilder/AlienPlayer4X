package com.thilian.se4x.robot.game;

public class EconPhaseResult extends EconRollResult {
    private Fleet fleet;
    private AlienPlayer alienPlayer;
    private boolean moveTechRolled;

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

    public boolean getMoveTechRolled() {
        return moveTechRolled;
    }

    public boolean isMoveTechRolled() {
        return moveTechRolled;
    }

    public void setMoveTechRolled(boolean moveTechRolled) {
        this.moveTechRolled = moveTechRolled;
    }
}