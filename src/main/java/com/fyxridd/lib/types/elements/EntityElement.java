package com.fyxridd.lib.types.elements;

import com.fyxridd.lib.core.api.hashList.HashList;
import com.fyxridd.lib.core.api.hashList.HashListImpl;
import org.bukkit.entity.EntityType;

import java.util.List;

public class EntityElement{
	private HashList<EntityType> entities;
	
	public EntityElement(List<EntityType> list) {
		entities = new HashListImpl<EntityType>();
		entities.convert(list, false);
	}

	/**
	 * @param obj 是EntityType
	 */
	public boolean check(Object obj) {
		EntityType type = (EntityType) obj;
		return entities.has(type);
	}
}
