package com.thilian.se4x.robot.game;

import java.util.ArrayList;
import java.util.List;

import com.thilian.se4x.robot.game.enums.FleetType;
import com.thilian.se4x.robot.game.enums.ShipType;

public class Fleet {

	private int fleetCP;
	private FleetType fleetType;

	private List<Group> groups = new ArrayList<>();
	private AlienPlayer ap;
	private String name;

	public Fleet(AlienPlayer ap, FleetType fleetType, int fleetCP) {
		this.ap = ap;
		this.fleetType = fleetType;
		this.fleetCP = fleetCP;
		this.name = ap.findFleetName(fleetType);
		ap.getFleets().add(this);
	}

	public int getBuildCost() {
		int sum = 0;
		for(Group group : groups){
			sum += group.getSize() * group.getShipType().getCost();
		}
		return sum;
	}

	public int getRemainigCP() {
		return fleetCP - getBuildCost();
	}

	public boolean canBuyMoreShips(){
		return getRemainigCP() >= ShipType.SCOUT.getCost();
	}
	
	public boolean isBuilt() {
		return !groups.isEmpty();
	}

	public FleetType getFleetType() {
		return fleetType;
	}

	public void setFleetType(FleetType fleetType) {
		this.fleetType = fleetType;
		this.name = ap.findFleetName(fleetType);
	}

	public int getFleetCP() {
		return fleetCP;
	}

	public List<Group> getGroups() {
		return groups;
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
		for(Group group : groups){
			if(group.getShipType().equals(shipType))
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
}
