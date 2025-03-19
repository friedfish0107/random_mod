package com.fish.greatworld;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;

public class BlockBagItem
      extends BlockItem {

   public BlockBagItem(Block p_40565_, Properties p_40566_) {
      super(p_40565_, p_40566_);

   }

   @Override
   public InteractionResult useOn(UseOnContext useOnContext) {
      InteractionResult result = super.useOn(useOnContext);
      if (result.consumesAction()) {
         useOnContext.getItemInHand().hurtAndBreak(1,
               useOnContext.getPlayer(),
               p -> p.broadcastBreakEvent(useOnContext.getHand()));
         return result;
      }
      return InteractionResult.PASS;
   }

   @Override
   public boolean isBarVisible(ItemStack itemStack) {
      return true;
   }

   @Override
   public int getBarWidth(ItemStack itemStack) {
      return 13 * itemStack.getDamageValue();
   }

   @Override
   public int getBarColor(ItemStack stack) {
      return 0x00ff00;
   }

}