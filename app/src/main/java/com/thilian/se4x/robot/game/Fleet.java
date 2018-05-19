/*
 * Copyright (C) 2018 Balázs Péter
 *
 * This file is part of Alien Player 4X.
 *
 * Alien Player 4X is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Alien Player 4X is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Alien Player 4X.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.thilian.se4x.robot.game;

import java.util.ArrayList;
import java.util.List;

import com.thilian.se4x.robot.game.enums.FleetType;
import com.thilian.se4x.robot.game.enums.ShipType;

public class Fleet {

    private int fleetCP;
    private FleetType fleetType;

    private List<Group> groups = new ArrayList<>();
    private List<Group> freeGroups = new ArrayList<>();
    private AlienPlayer ap;
    private String name;
    private boolean hadFirstCombat;

    public Fleet(AlienPlayer ap, FleetType fleetType, int fleetCP) {
        this.ap = ap;
        this.fleetType = fleetType;
        this.fleetCP = fleetCP;
        this.name = ap.findFleetName(fleetType);
        ap.getFleets().add(this);
    }

    public int getBuildCost() {
        return getAllGroupCost() - getFreeGroupCost();
    }

    public int getAllGroupCost() {
        return sumGroupCost(groups);
    }

    public int getFreeGroupCost() {
        return sumGroupCost(freeGroups);
    }

    public int getRemainingCP() {
        return fleetCP - getBuildCost();
    }

    public boolean canBuyMoreShips() {
        return getRemainingCP() >= ShipType.SCOUT.getCost();
    }

    public FleetType getFleetType() {
        return fleetType;
    }

    public void setFleetType(FleetType fleetType) {
        if(!getFleetType().isSameNameSequence(fleetType)) {
            this.name = ap.findFleetName(fleetType);
        }
        this.fleetType = fleetType;
    }

    public int getFleetCP() {
        return fleetCP;
    }

    public void addFleetCp(int amount) {
        fleetCP += amount;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void addFreeGroup(Group group) {
        addGroup(group);
        freeGroups.add(group);
    }

    public void addGroup(Group group) {
        if (group.getSize() > 0) {
            Group existingGroup = findGroup(group.getShipType());
            if (existingGroup != null)
                existingGroup.addShips(group.getSize());
            else
                groups.add(group);
        }
    }

    public Group findGroup(ShipType shipType) {
        for (Group group : groups) {
            if (group.getShipType().equals(shipType))
                return group;
        }
        return null;
    }

    public AlienPlayer getAp() {
        return ap;
    }

    public String getName() {
        return name;
    }

    private int sumGroupCost(List<Group> groups) {
        int sum = 0;
        for (Group group : groups) {
            sum += group.getSize() * group.getShipType().getCost();
        }
        return sum;
    }

    public int getIndex(){
        return getAp().getFleets().indexOf(this);
    }

    public boolean hadFirstCombat() {
        return hadFirstCombat;
    }

    public void setFirstCombat(boolean hadFirstCombat) {
        this.hadFirstCombat = hadFirstCombat;
    }

    public void setAlienPlayer(AlienPlayer alienPlayer) {
        this.ap = alienPlayer;
    }
}
