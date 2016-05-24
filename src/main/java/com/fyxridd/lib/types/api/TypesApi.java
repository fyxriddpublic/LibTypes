package com.fyxridd.lib.types.api;

import com.fyxridd.lib.types.TypesMain;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class TypesApi {
    /**
     * 重新读取类型配置
     * 会读取'插件名/types.yml'的文件
     * @param plugin 插件名,可为null(null时无效果)
     */
    public static void reloadTypes(String plugin) {
        TypesMain.reloadTypes(plugin);
    }

    /**
     * 检测实体类型
     * @param plugin 插件名,可为null(null时返回false)
     * @param type 类型名,可为null(null时返回false)
     * @param entityType 实体类型,可为null(null时返回false)
     * @return 是否符合类型
     */
    public static boolean checkEntity(String plugin, String type, EntityType entityType){
        return TypesMain.checkEntity(plugin, type, entityType);
    }

    /**
     * 检测物品类型
     * @param plugin 插件名,可为null(null时返回false)
     * @param type 类型名,可为null(null时返回false)
     * @param is 物品,可为null(null时返回false)
     * @return 是否符合类型
     */
    public static boolean checkItem(String plugin, String type, ItemStack is){
        return TypesMain.checkItem(plugin, type, is);
    }

    /**
     * 检测方块类型
     * @param plugin 插件名,可为null(null时返回false)
     * @param type 类型名,可为null(null时返回false)
     * @param material 材料,可为null(null时返回false)
     * @param data 方块小id
     * @return 是否符合类型
     */
    public static boolean checkBlock(String plugin, String type, Material material, byte data){
        return TypesMain.checkBlock(plugin, type, material, data);
    }

    /**
     * 检测是否是安全的方块
     * @param material 材料,可为null(null时返回false)
     * @param data 方块小id
     * @return 是否是安全的方块
     */
    public static boolean isSafeBlock(Material material, byte data) {
        return TypesMain.isSafeBlock(material, data);
    }
}
