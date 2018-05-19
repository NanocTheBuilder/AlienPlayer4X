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
