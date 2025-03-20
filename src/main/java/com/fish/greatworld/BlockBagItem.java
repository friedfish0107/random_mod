package com.fish.greatworld;

import javax.annotation.Nullable;

import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.item.ItemEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MainMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BlockBagItem
        extends BlockItem {

    @Nullable
    private String descriptionId;

    public BlockBagItem(Block p_40565_, Properties p_40566_) {
        super(p_40565_, p_40566_);

    }

    @Override
    public InteractionResult useOn(UseOnContext useOnContext) {
        ItemStack itemStack = useOnContext.getItemInHand();
        if (itemStack.getMaxDamage() - itemStack.getDamageValue() > 1) {
            itemStack.grow(1);// 抵消放置方块的消耗
            InteractionResult result = super.useOn(useOnContext);
            if (result.consumesAction()) {
                itemStack.hurtAndBreak(1,
                        useOnContext.getPlayer(),
                        p -> p.broadcastBreakEvent(useOnContext.getHand()));
                return result;
            }
            itemStack.shrink(1);// 若未成功放置方块，则撤回改动
        }
        return InteractionResult.PASS;
    }

    @Override
    public boolean isBarVisible(ItemStack itemStack) {
        return true;
    }

    @Override
    public int getBarWidth(ItemStack itemStack) {
        return (int) (13 * (itemStack.getMaxDamage() - itemStack.getDamageValue()) / 8) - 1;
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return 0xb9855c;
    }

    @Override
    protected String getOrCreateDescriptionId() {
        if (this.descriptionId == null) {
            this.descriptionId = Util.makeDescriptionId("item", BuiltInRegistries.ITEM.getKey(this));
        }

        return this.descriptionId;
    }

    @Override
    public String getDescriptionId() {
        return this.getOrCreateDescriptionId();
    }

    @SubscribeEvent
    public static void onItemPickup(EntityItemPickupEvent event) {
        Player player = event.getEntity();
        ItemStack pickedStack = event.getItem().getItem();

        if (pickedStack.getItem() == Blocks.DIRT.asItem()) {
            for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                ItemStack stackInSlot = player.getInventory().getItem(i);

                if (stackInSlot.getItem() == MainMod.DIRT_BAG.get()
                        && stackInSlot.getItem().getDamage(stackInSlot) != 0) {
                    handleItemAUpgrade(player, stackInSlot, pickedStack);
                    event.setCanceled(true);
                    break;
                }
            }
        }
    }

    private static void handleItemAUpgrade(Player player, ItemStack itemA, ItemStack itemB) {
        int countB = itemB.getCount();
        int damageToRepair = Math.min(countB, itemA.getDamageValue());

        itemA.setDamageValue(itemA.getDamageValue() - damageToRepair);
        itemB.setCount(countB - damageToRepair);

        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.containerMenu.broadcastChanges();
        }
    }

}