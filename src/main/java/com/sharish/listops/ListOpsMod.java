package com.sharish.listops;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(ListOpsMod.MODID)
public class ListOpsMod {
    public static final String MODID = "listops";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ListOpsMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        // Регистрируем команду
        ListOpsCommands.register(bus);
        LOGGER.info("ListOpsMod initialized");
    }
}
