package com.thilian.se4x.robot.game;

import com.thilian.se4x.robot.game.enums.ShipType;

public class Group {

	private int size;
	private ShipType shipType;

	public Group(ShipType shipType, int size) {
		this.shipType = shipType;
		this.size = size;
	}

	public int getSize() {
		return size;
	}
	
	public ShipType getShipType() {
		return shipType;
	}
	
	public int addShips(int ships) {
		return size += ships;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((shipType == null) ? 0 : shipType.hashCode());
		result = prime * result + size;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Group other = (Group) obj;
		if (shipType != other.shipType)
			return false;
		if (size != other.size)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Group [size=" + size + ", shipType=" + shipType + "]";
	}

}
