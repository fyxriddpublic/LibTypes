package com.fyxridd.lib.types.elements;

import com.fyxridd.lib.core.api.hashList.HashList;
import com.fyxridd.lib.core.api.hashList.HashListImpl;
import org.bukkit.configuration.MemorySection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemElement{
	private HashList<Integer> ids = null;
	private int dura = -1;
	private String name = null;
	private List<String> lore = null;
	private HashMap<Enchantment, Integer> enchants = null;
	
	public ItemElement(MemorySection ms) {
		//ids
		if (ms.contains("ids")) {
			ids = new HashListImpl<Integer>();
			ids.convert(ms.getIntegerList("ids"), false);
		}
		//dura
		if (ms.contains("dura")) dura = ms.getInt("dura");
		//name
		if (ms.contains("name")) name = ms.getString("name");
		//lore
		if (ms.contains("lore")) lore = ms.getStringList("lore");
		//enchants
		if (ms.contains("enchants")) {
			enchants = new HashMap<Enchantment, Integer>();
			for (String ss:ms.getStringList("enchants")) {
				int enchantId = Integer.parseInt(ss.split(" ")[0]);
				int level = Integer.parseInt(ss.split(" ")[1]);
				enchants.put(Enchantment.getById(enchantId), level);
			}
		}
	}

	/**
	 * @param obj 是ItemStack的实例
	 * @return 类型是否满足
	 */
	public boolean check(Object obj) {
		ItemStack is = (ItemStack) obj;
		//ids
		if (ids != null) {
			if (!ids.has(is.getTypeId())) {
				return false;
			}
		}
		//dura
		if (dura != -1) {
			if ((int)is.getDurability() != dura) return false;
		}
		//name
		ItemMeta im = is.getItemMeta();
		if (name != null) {
			if (im == null) {
				if (!name.isEmpty()) return false;
			}else if (im.getDisplayName() == null){
				if (!name.isEmpty()) return false;
			}else if (!im.getDisplayName().equals(name)) return false;
		}
		//lore
		if (lore != null) {
			if (im == null || !isSame(im.getLore(), lore)) return false;
		}
		//enchants
		if (enchants != null) {
			if (im == null || !isSame(im.getEnchants(), enchants)) return false;
		}
		//成功
		return true;
	}

	private boolean isSame(List<String> list1, List<String> list2) {
		if (list1 == null) return list2 == null;
		else if (list2 == null) return false;
		//两个都不为null
		if (list1.size() != list2.size()) return false;
		for (int i=0;i<list1.size();i++) {
			if (!list1.get(i).equals(list2.get(i))) return false;
		}
		return true;
	}

	private boolean isSame(Map<Enchantment, Integer> e1, HashMap<Enchantment, Integer> e2) {
		if (e1 == null) return e2 == null;
		else if (e2 == null) return false;
		//两个都不为null
		if (e1.size() != e2.size()) return false;
		for (Enchantment e:e1.keySet()) {
			if (e2.containsKey(e)) {
				if (e1.get(e).intValue() != e2.get(e).intValue()) return false;
			}else return false;
		}
		return true;
	}
}
