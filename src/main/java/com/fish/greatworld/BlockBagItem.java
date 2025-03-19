package com.fish.greatworld;

import javax.annotation.Nullable;

import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;

public class BlockBagItem
      extends BlockItem {

   @Nullable
   private String descriptionId;

   public BlockBagItem(Block p_40565_, Properties p_40566_) {
      super(p_40565_, p_40566_);

   }

   @Override
   public InteractionResult useOn(UseOnContext useOnContext) {
      useOnContext.getItemInHand().grow(1);// 抵消放置方块的消耗
      InteractionResult result = super.useOn(useOnContext);
      if (result.consumesAction()) {
         useOnContext.getItemInHand().hurtAndBreak(1,
               useOnContext.getPlayer(),
               p -> p.broadcastBreakEvent(useOnContext.getHand()));
         return result;
      }
      useOnContext.getItemInHand().shrink(1);// 若未成功放置方块，则撤回改动
      return InteractionResult.PASS;
   }

   @Override
   public boolean isBarVisible(ItemStack itemStack) {
      return true;
   }

   @Override
   public int getBarWidth(ItemStack itemStack) {
      return 13 * (itemStack.getMaxDamage() - itemStack.getDamageValue());
   }

   @Override
   public int getBarColor(ItemStack stack) {
      return 0x00ff00;
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

}