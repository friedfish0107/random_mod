package com.fish.greatworld;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("greatworld")
public class MainMod {
    public static final String MODID = "greatworld";
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public static final RegistryObject<Item> DIRT_BAG = ITEMS.register("dirt_bag",
            () -> new BlockBagItem(Blocks.DIRT, new Item.Properties()
                    .durability(256)
                    .setNoRepair()));

    public static final RegistryObject<Item> AAA = ITEMS.register("aaa",
            () -> new Item(new Item.Properties().durability(32)));

    @SuppressWarnings("removal")
    public MainMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(modEventBus);
    }
}
