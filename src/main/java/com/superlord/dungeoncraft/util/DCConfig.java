package com.superlord.dungeoncraft.util;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Type;

@Config(modid = Reference.MOD_ID, name = "dungeoncraft_config", type = Type.INSTANCE)
public class DCConfig {
	
	@Comment("The dimension IDs")
	public static int abyssID = 2;

	@Comment("The dimension ID for the Feywild")
    public static int feywildID = 276;
	
	@Comment("Advanced Smelting")
	public static boolean advancedSmelting = true;
}
