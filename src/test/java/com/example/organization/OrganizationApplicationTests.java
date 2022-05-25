package com.example.organization;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest(classes = OrganizationApplicationTests.class)
class OrganizationApplicationTests {

    @Test
    void contextLoads() {
        String str = "ADD COLUMN `preparation_name` varchar(200) NULL COMMENT '制备名称' AFTER `flag_type`,\n" +
                "ADD COLUMN `material_alias` varchar(200) NULL COMMENT '物料别名' AFTER `preparation_name`,\n" +
                "ADD COLUMN `u8_code` varchar(200) NULL COMMENT 'U8编码' AFTER `material_alias`,\n" +
                "ADD COLUMN `u8_name` varchar(200) NULL COMMENT 'U8名称' AFTER `u8_code`,\n" +
                "ADD COLUMN `appearance_color` varchar(100) NULL COMMENT '外观、触觉-花色' AFTER `u8_name`,\n" +
                "ADD COLUMN `appearance_style` varchar(15) NULL COMMENT '外观、触觉-样式' AFTER `appearance_color`,\n" +
                "ADD COLUMN `appearance_shape` varchar(15) NULL COMMENT '外观、触觉-形状' AFTER `appearance_style`,\n" +
                "ADD COLUMN `appearance_texture` varchar(15) NULL COMMENT '外观、触觉-质感' AFTER `appearance_shape`,\n" +
                "ADD COLUMN `appearance_brand` varchar(15) NULL COMMENT '外观、触觉-品牌' AFTER `appearance_texture`,\n" +
                "ADD COLUMN `kind_series` varchar(100) NULL COMMENT '种类-系列' AFTER `appearance_brand`,\n" +
                "ADD COLUMN `kind_accessory` varchar(15) NULL COMMENT '种类-配件' AFTER `kind_series`,\n" +
                "ADD COLUMN `kind_model_number` varchar(50) NULL COMMENT '种类-型号' AFTER `kind_accessory`,\n" +
                "ADD COLUMN `kind_function` varchar(50) NULL COMMENT '种类-功能' AFTER `kind_model_number`,\n" +
                "ADD COLUMN `kind_strut_member` varchar(15) NULL COMMENT '种类-支撑件' AFTER `kind_function`,\n" +
                "ADD COLUMN `kind_use` varchar(15) NULL COMMENT '种类-用途' AFTER `kind_strut_member`,\n" +
                "ADD COLUMN `physicochemical_texture` varchar(15) NULL COMMENT '理化-材质' AFTER `kind_use`,\n" +
                "ADD COLUMN `physicochemical_panel_texture` varchar(15) NULL COMMENT '理化-（面板）材质' AFTER `physicochemical_texture`,\n" +
                "ADD COLUMN `physicochemical_density` varchar(15) NULL COMMENT '理化-密度' AFTER `physicochemical_panel_texture`,\n" +
                "ADD COLUMN `physicochemical_volume_weight` varchar(15) NULL COMMENT '理化-容重' AFTER `physicochemical_density`,\n" +
                "ADD COLUMN `physicochemical_gram_weight` varchar(15) NULL COMMENT '理化-克重' AFTER `physicochemical_volume_weight`,\n" +
                "ADD COLUMN `physicochemical_pressure` decimal(18, 6) NULL COMMENT '理化-压强(kpa)' AFTER `physicochemical_gram_weight`,\n" +
                "ADD COLUMN `physicochemical_fire_level` varchar(8) NULL COMMENT '理化-防火等级' AFTER `physicochemical_ pressure`,\n" +
                "ADD COLUMN `physicochemical_flame_retardant_level` varchar(8) NULL COMMENT '理化-阻燃等级' AFTER `physicochemical_fire_level`,\n" +
                "ADD COLUMN `physicochemical_environmental_level` varchar(8) NULL COMMENT '理化-环保等级' AFTER `physicochemical_flame_retardant_level`,\n" +
                "ADD COLUMN `physicochemical_waterproof_level` varchar(15) NULL COMMENT '理化-防水等级' AFTER `physicochemical_environmental_level`,\n" +
                "ADD COLUMN `physicochemical_steal_level` varchar(8) NULL COMMENT '理化-防盗等级' AFTER `physicochemical_waterproof_level`,\n" +
                "ADD COLUMN `physicochemical_energy_level` varchar(15) NULL COMMENT '理化-能效等级' AFTER `physicochemical_steal_level`,\n" +
                "ADD COLUMN `physicochemical_exhaust_air` varchar(15) NULL COMMENT '理化-排风量' AFTER `physicochemical_energy_level`,\n" +
                "ADD COLUMN `physicochemical_power` varchar(20) NULL COMMENT '理化-功率（W)' AFTER `physicochemical_exhaust_air`,\n" +
                "ADD COLUMN `physicochemical_support` varchar(8) NULL COMMENT '理化-支撑力度' AFTER `physicochemical_power`,\n" +
                "ADD COLUMN `physicochemical_load` varchar(15) NULL COMMENT '理化-负荷力度' AFTER `physicochemical_support`,\n" +
                "ADD COLUMN `physicochemical_voltage` decimal(18, 6) NULL COMMENT '理化-电压（V)' AFTER `physicochemical_load`,\n" +
                "ADD COLUMN `physicochemical_electricity` decimal(18, 6) NULL COMMENT '理化-电流（A）' AFTER `physicochemical_voltage`,\n" +
                "ADD COLUMN `physicochemical_color_temp` varchar(15) NULL COMMENT '理化-色温（K)' AFTER `physicochemical_electricity`,\n" +
                "ADD COLUMN `specification` varchar(100) NULL COMMENT '规格' AFTER `physicochemical_color_temp`,\n" +
                "ADD COLUMN `specification_length` decimal(18, 6) NULL COMMENT '规格-长度（mm）' AFTER `specification`,\n" +
                "ADD COLUMN `specification_width` decimal(18, 6) NULL COMMENT '规格-宽度（mm）' AFTER `specification_length`,\n" +
                "ADD COLUMN `specification_height` decimal(18, 6) NULL COMMENT '规格-高度（mm）' AFTER `specification_width`,\n" +
                "ADD COLUMN `specification_thick` decimal(18, 6) NULL COMMENT '规格-厚度（mm）' AFTER `specification_height`,\n" +
                "ADD COLUMN `specification_wall_thick` varchar(20) NULL COMMENT '规格-壁厚（mm）' AFTER `specification_thick`,\n" +
                "ADD COLUMN `specification_diameter` decimal(18, 6) NULL COMMENT '规格-直径（mm）' AFTER `specification_wall_thick`,\n" +
                "ADD COLUMN `specification_weight` varchar(8) NULL COMMENT '规格-重量' AFTER `specification_diameter`,\n" +
                "ADD COLUMN `specification_capacity` varchar(8) NULL COMMENT '规格-容量' AFTER `specification_weight`,\n" +
                "ADD COLUMN `specification_flush` varchar(10) NULL COMMENT '规格-冲水量（L）' AFTER `specification_capacity`,\n" +
                "ADD COLUMN `specification_hole_size` varchar(20) NULL COMMENT '规格-开孔尺寸' AFTER `specification_flush`,\n" +
                "ADD COLUMN `specification_pit_distance` int(11) NULL COMMENT '规格-坑距' AFTER `specification_hole_size`,\n" +
                "ADD COLUMN `specification_screen_size` varchar(15) NULL COMMENT '规格-屏幕尺寸' AFTER `specification_pit_distance`,\n" +
                "ADD COLUMN `specification_outerframe_height` int(11) NULL COMMENT '规格-外侧门框高度（mm）' AFTER `specification_screen_size`,\n" +
                "ADD COLUMN `specification_innerframe_height` int(11) NULL COMMENT '规格-内侧门框高度（mm）' AFTER `specification_outerframe_height`,\n" +
                "ADD COLUMN `specification_min_package` decimal(18, 6) NULL AUTO_INCREMENT COMMENT '规格-最小包装规格' AFTER `specification_innerframe_height`,\n" +
                "ADD COLUMN `craft_construct` varchar(15) NULL COMMENT '工艺-构造' AFTER `specification_min_package`,\n" +
                "ADD COLUMN `craft_surface` varchar(50) NULL COMMENT '工艺-表层工艺' AFTER `craft_construct`,\n" +
                "ADD COLUMN `craft_process` varchar(50) NULL COMMENT '工艺-加工工艺' AFTER `craft_surface`,\n" +
                "ADD COLUMN `craft_handshandle` varchar(15) NULL COMMENT '工艺-拉手类型' AFTER `craft_process`,\n" +
                "ADD COLUMN `craft_window_edge` varchar(15) NULL COMMENT '工艺-门窗套边' AFTER `craft_handshandle`,\n" +
                "ADD COLUMN `craft_lock_type` varchar(15) NULL COMMENT '工艺-锁体类型' AFTER `craft_window_edge`,\n" +
                "ADD COLUMN `craft_floor_drain` varchar(15) NULL COMMENT '工艺-地漏样式' AFTER `craft_lock_type`,\n" +
                "ADD COLUMN `craft_glass_type` varchar(15) NULL COMMENT '工艺-玻璃类型' AFTER `craft_floor_drain`,\n" +
                "ADD COLUMN `craft_install_way` varchar(15) NULL COMMENT '工艺-安装方式' AFTER `craft_glass_type`,\n" +
                "ADD COLUMN `craft_control_way` varchar(15) NULL COMMENT '工艺-控制方式' AFTER `craft_install_way`,\n" +
                "ADD COLUMN `craft_drain_water` varchar(15) NULL COMMENT '工艺-排水方式' AFTER `craft_control_way`,\n" +
                "ADD COLUMN `craft_drain_smoke` varchar(15) NULL COMMENT '工艺-排烟方式' AFTER `craft_drain_water`,\n" +
                "ADD COLUMN `craft_light_type` varchar(15) NULL COMMENT '工艺-光源类型' AFTER `craft_drain_smoke`,\n" +
                "ADD COLUMN `craft_slot_way` varchar(40) NULL COMMENT '工艺-开槽方式' AFTER `craft_light_type`,\n" +
                "ADD COLUMN `craft_track_type` varchar(30) NULL COMMENT '工艺-轨道类型' AFTER `craft_slot_way`,\n" +
                "ADD COLUMN `craft_window_ear` varchar(8) NULL COMMENT '工艺-窗耳类型' AFTER `craft_track_type`,\n" +
                "ADD COLUMN `craft_open_direction` varchar(10) NULL COMMENT '工艺-开启方向' AFTER `craft_window_ear`";
        String[] split = str.split("`,");
        for (String s : split) {
            String type = "String";
            if (s.contains("varchar")) {
                type = "String";
            } else if (s.contains("int")) {
                type = "Integer";
            } else if (s.contains("decimal")) {
                type = "BigDecimal";
            }
            String[] s1 = s.split("`");
            String tableField = s1[1];
            String field = lineToHump(tableField);
            String desc = s.split("'")[1];
            String res = "@TableField(\""+ tableField +"\")\n" +
                    "@ApiModelProperty(value = \""+ desc +"\")\n" +
                    "private "+ type +" "+ field +";\n";
            System.out.println(res);
        }
    }

    public static String lineToHump(String str) {
        Pattern linePattern = Pattern.compile("_(\\w)");
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
