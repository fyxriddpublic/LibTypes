package com.fyxridd.lib.types.manager;

import com.fyxridd.lib.core.api.CoreApi;
import com.fyxridd.lib.core.api.UtilApi;
import com.fyxridd.lib.core.api.event.ReloadConfigEvent;
import com.fyxridd.lib.types.TypesPlugin;
import com.fyxridd.lib.types.api.TypesApi;
import com.fyxridd.lib.types.elements.BlockElement;
import com.fyxridd.lib.types.elements.EntityElement;
import com.fyxridd.lib.types.elements.ItemElement;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.EventExecutor;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypesManager{
	//插件名 类型名 类型信息
	private static Map<String, Map<String, BlockElement>> blocks = new HashMap<>();
    private static Map<String, Map<String, EntityElement>> entitys = new HashMap<>();
    private static Map<String, Map<String, ItemElement>> items = new HashMap<>();

	public TypesManager() {
	    //注册事件
	    {
	        //请求重新读取配置
	        Bukkit.getPluginManager().registerEvent(ReloadConfigEvent.class, TypesPlugin.instance, EventPriority.HIGHEST, new EventExecutor() {
                @Override
                public void execute(Listener listener, Event e) throws EventException {
                    ReloadConfigEvent event = (ReloadConfigEvent) e;
                    if (event.getPlugin().equals(TypesPlugin.instance.pn)) reloadTypes(TypesPlugin.instance.pn);
                }
            }, TypesPlugin.instance);
	    }
	    //重载
	    reloadTypes(TypesPlugin.instance.pn);
	}
	
	/**
	 * @see TypesApi#reloadTypes(String)
	 */
	public void reloadTypes(String pn) {
		if (pn == null) return;
		ConfigurationSection config = UtilApi.loadConfigByUTF8(new File(CoreApi.pluginPath, pn+File.separator+"types.yml"));
		//清除
		entitys.put(pn, new HashMap<String, EntityElement>());
		items.put(pn, new HashMap<String, ItemElement>());
        blocks.put(pn, new HashMap<String, BlockElement>());
		//读取实体类型
		if (config.contains("entity")) {
		    ConfigurationSection ms = (ConfigurationSection) config.get("entity");
            if (ms != null) {
                Map<String, Object> map = ms.getValues(false);
                for (String key : map.keySet()) loadEntityType(pn, ms, key);
            }
		}
		//读取物品类型
		if (config.contains("item")) {
		    ConfigurationSection ms = (ConfigurationSection) config.get("item");
            if (ms != null) {
                Map<String, Object> map = ms.getValues(false);
                for (String key : map.keySet()) loadItemType(pn, ms, key);
            }
		}
        //读取方块类型
        if (config.contains("block")) {
            ConfigurationSection ms = (ConfigurationSection) config.get("block");
            if (ms != null) {
                Map<String, Object> map = ms.getValues(false);
                for (String key : map.keySet()) loadBlockType(pn, ms, key);
            }
        }
	}

	/**
     * @see com.fyxridd.lib.types.api.TypesApi#checkEntity(String, String, org.bukkit.entity.EntityType)
	 */
	public boolean checkEntity(String pn, String type, EntityType entityType){
        if (pn == null || type == null || entityType == null) return false;

		try {
			return entitys.get(pn).get(type).check(entityType);
		} catch (Exception e) {
            e.printStackTrace();
            return false;
		}
	}

	/**
     * @see com.fyxridd.lib.types.api.TypesApi#checkItem(String, String, org.bukkit.inventory.ItemStack)
	 */
	public boolean checkItem(String pn, String type, ItemStack is) {
        if (pn == null || type == null || is == null) return false;

        try {
			return items.get(pn).get(type).check(is);
		} catch (Exception e) {
            e.printStackTrace();
            return false;
		}
	}

    /**
     * @see com.fyxridd.lib.types.api.TypesApi#checkBlock(String, String, org.bukkit.Material, byte)
     */
    public boolean checkBlock(String pn, String type, Material material, byte data) {
        if (pn == null || type == null || material == null) return false;

        try {
            BlockElement blockElement = blocks.get(pn).get(type);
            return blockElement.check(material, data);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @see TypesApi#isSafeBlock(Material, byte)
     */
    public boolean isSafeBlock(Material material, byte data) {
        if (material == null) return false;

        return checkBlock(TypesPlugin.instance.pn, "safeBlocks", material, data);
    }

	/**
	 * 从配置中读取指定的实体类型
	 * @param pn 插件名,不为null
	 * @param ms 类型从哪个配置中读取,不为null
	 * @param type 类型,不为null
	 */
	private void loadEntityType(String pn, ConfigurationSection ms, String type) {
		//已经读取过此类型
		if (entitys.get(pn).containsKey(type)) return;
		//读取
		List<String> list = ms.getStringList(type);
		List<EntityType> entityList = new ArrayList<EntityType>();
		for (String s:list) {
			EntityType entityType = CoreApi.getEntityType(s);
			if (entityType != null) entityList.add(entityType);
		}
		entitys.get(pn).put(type, new EntityElement(entityList));
	}

	/**
	 * 从配置中读取指定的物体类型
	 * @param pn 插件名,不为null
	 * @param ms 类型从哪个配置中读取,不为null
	 * @param type 类型,不为null
	 */
	private void loadItemType(String pn, ConfigurationSection ms, String type) {
		//已经读取过此类型
		if (items.get(pn).containsKey(type)) return;
		//读取
		MemorySection section = (MemorySection) ms.get(type);
		if (section != null) {
			ItemElement itemElement = new ItemElement(section);
			items.get(pn).put(type, itemElement);
		}
	}

    /**
     * 从配置中读取指定的方块类型
     * @param pn 插件名,不为null
     * @param ms 类型从哪个配置中读取,不为null
     * @param type 类型,不为null
     */
    private void loadBlockType(String pn, ConfigurationSection ms, String type) {
        //已经读取过此类型
        if (blocks.get(pn).containsKey(type)) return;
        //读取
        List<String> list = ms.getStringList(type);
        blocks.get(pn).put(type, new BlockElement(list));
    }
}
