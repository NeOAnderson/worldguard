// $Id$
/*
 * WorldGuard
 * Copyright (C) 2010 sk89q <http://www.sk89q.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.sk89q.worldguard.protection.flags;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.CommandSender;

import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.bukkit.BukkitUtil;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

/**
 *
 * @author sk89q
 */
public class VectorFlag extends Flag<Vector> {
    
    public VectorFlag(String name, char legacyCode) {
        super(name, legacyCode);
    }

    public VectorFlag(String name) {
        super(name);
    }

    @Override
    public Vector parseInput(WorldGuardPlugin plugin, CommandSender sender,
            String input) throws InvalidFlagFormat {
        input = input.trim();
        
        try {
            return BukkitUtil.toVector(plugin.checkPlayer(sender).getLocation());
        } catch (CommandException e) {
            throw new InvalidFlagFormat(e.getMessage());
        }
    }

    @Override
    public Vector unmarshal(Object o) {
        if (o instanceof Map<?, ?>) {
            Map<?, ?> map  = (Map<?, ?>) o;

            Object rawX = map.get("x");
            Object rawY = map.get("y");
            Object rawZ = map.get("z");
            
            if (rawX == null || rawY == null || rawZ == null) {
                return null;
            }
            
            return new Vector(toNumber(rawX), toNumber(rawY), toNumber(rawZ));
        }
        
        return null;
    }

    @Override
    public Object marshal(Vector o) {
        Map<String, Object> vec = new HashMap<String, Object>();
        vec.put("x", o.getX());
        vec.put("y", o.getY());
        vec.put("z", o.getZ());
        return vec;
    }
    
    private double toNumber(Object o) {
        if (o instanceof Integer) {
            return (Integer) o;
        } else if (o instanceof Long) {
            return (Long) o;
        } else if (o instanceof Float) {
            return (Float) o;
        } else if (o instanceof Double) {
            return (Double) o;
        } else {
            return 0;
        }
    }
}
