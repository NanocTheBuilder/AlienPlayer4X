package com.thilian.se4x.robot.game.enums;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum ShipType {
	RAIDER(12, 2, 99), 
	CARRIER(12, 1, 99), 
	FIGHTER(5, 1, 99), 
	BASE(12, 3, 99), 
	MINE(5, 1, 99), 
	SCOUT(6, 1, 1), 
	DESTROYER(9, 1, 2), 
	CRUISER(12, 2, 3),
	BATTLECRUISER(15, 2, 4), 
	BATTLESHIP(20, 3, 5), 
	DREADNAUGHT(24, 3, 6),
	TITAN(32, 5, 7),
	
	TRANSPORT(6, 1, 99),
	INFANTRY(2, 1, 99),
	MARINE(3, 2, 99),
	HEAVY_INFANTRY(3, 2, 99),
	GRAV_ARMOR(4, 2, 99),
	BOARDING_SHIP(12, 2, 99)
	;
	
	private int cost;
	private int hullSize;
	private int requiredShipSize;

	private ShipType(int cost, int hullSize, int size) {
		this.cost = cost;
		this.hullSize = hullSize;
		this.requiredShipSize = size;
	}

	public int getCost() {
		return cost;
	}
	
	public int getRequiredShipSize() {
		return requiredShipSize;
	}

	public static ShipType findCheapest(int minHullSize) {
		for(ShipType type : ShipType.values()){
			if(type.requiredShipSize != 99 && type.hullSize >= minHullSize){
				return type;
			}
		}
		return null;
	}
	
	public static List<ShipType> getBiggerTypesInReverse(ShipType type) {
		List<ShipType> types = new ArrayList<>();
		for(ShipType t : ShipType.values()) {
			if (t.requiredShipSize != 99 && t.cost > type.cost) {
				types.add(t);
			}
		}
		Collections.sort(types, new Comparator<ShipType>() {
			@Override
			public int compare(ShipType t1, ShipType t2) {
				return t2.cost - t1.cost;
			}
		});
		return types;
	}
	
	public static ShipType findBiggest(int availableCP, int shipSizeLevel) {
		if(canBuild(availableCP, shipSizeLevel, TITAN))
			return TITAN;
		if(canBuild(availableCP, shipSizeLevel, DREADNAUGHT))
			return DREADNAUGHT;
		if(canBuild(availableCP, shipSizeLevel, BATTLESHIP))
			return BATTLESHIP;
		if(canBuild(availableCP, shipSizeLevel, BATTLECRUISER))
			return BATTLECRUISER;
		if(canBuild(availableCP, shipSizeLevel, CRUISER))
			return CRUISER;
		if(canBuild(availableCP, shipSizeLevel, DESTROYER))
			return DESTROYER;
		if(canBuild(availableCP, shipSizeLevel, SCOUT))
			return SCOUT;
		else
			return null;
	}

	public static boolean canBuild(int availableCP, int shipSizeLevel, ShipType shipType) {
		return availableCP >= shipType.cost && shipSizeLevel >= shipType.requiredShipSize;
	}

}
