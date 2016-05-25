package com.fyxridd.lib.types.elements;

import com.fyxridd.lib.core.api.CoreApi;
import org.bukkit.Material;

import java.util.HashSet;
import java.util.List;

public class BlockElement{
    /**
     * 请注意方法hashCode()与equals()比较特殊,不要直接移植到其它地方!
     */
    private static class MaterialInfo {
        Material material;
        //-1表示无限制
        byte data;

        MaterialInfo(Material material, byte data) {
            this.material = material;
            this.data = data;
        }

        @Override
        public int hashCode() {
            return material.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            MaterialInfo check = (MaterialInfo) obj;
            if (data == -1 || check.data == -1) return material.equals(check.material);
            else return data == check.data && material.equals(check.material);
        }

        /**
         * 读取材料信息
         * @param s <方块类型名|ID>[:data],data为-1表示无限制
         * @return 材料信息,异常返回null
         */
        public static MaterialInfo load(String s) {
            try {
                String[] args = s.split(":");
                switch (args.length) {
                    case 1:
                        return new MaterialInfo(CoreApi.getMaterial(args[0]), (byte)-1);
                    case 2:
                        return new MaterialInfo(CoreApi.getMaterial(args[0]), Byte.parseByte(args[1]));
                }
            } catch (Exception e) {
            }
            return null;
        }
    }

	private HashSet<MaterialInfo> materials;

	public BlockElement(List<String> list) {
        materials = new HashSet<>();
        for (String s:list) {
            MaterialInfo info = MaterialInfo.load(s);
            if (info != null) materials.add(info);
        }
	}

	public boolean check(Material material, byte data) {
		return materials.contains(new MaterialInfo(material, data));
	}
}
