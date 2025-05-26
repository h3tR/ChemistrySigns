package chem_signs.compat;

import gregtech.api.items.toolitem.ToolClasses;
import gregtech.api.items.toolitem.ToolHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.Optional;

//Gregtech compatibility
public class GTCompat {

    @Optional.Method(modid = "gregtech")
    public static boolean UseScrewdriver(EntityPlayer playerIn, EnumHand hand){
        ItemStack itemStack = playerIn.getHeldItem(hand);
        if(itemStack.getItem().getToolClasses(itemStack).contains(ToolClasses.SCREWDRIVER)) {
            ToolHelper.damageItem(itemStack, playerIn);
            return true;
        }
        return false;
    }
}
