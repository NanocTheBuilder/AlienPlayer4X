package com.thilian.se4x.robot.game;

public class EconRollResult {
    private int fleetCP;
    private int techCP;
    private int defCP;
    private int extraEcon;

    public int getFleetCP() {
        return fleetCP;
    }

    public void setFleetCP(int fleetCP) {
        this.fleetCP = fleetCP;
    }

    public int getTechCP() {
        return techCP;
    }

    public void setTechCP(int techCP) {
        this.techCP = techCP;
    }

    public int getDefCP() {
        return defCP;
    }

    public void setDefCP(int defCP) {
        this.defCP = defCP;
    }

    public void setExtraEcon(int extraEcon) {
        this.extraEcon = extraEcon;
    }

    public int getExtraEcon() {
        return extraEcon;
    }

    public void add(EconRollResult rollResult) {
        fleetCP += rollResult.fleetCP;
        techCP += rollResult.techCP;
        defCP += rollResult.defCP;
        extraEcon += rollResult.extraEcon;
    }
}
