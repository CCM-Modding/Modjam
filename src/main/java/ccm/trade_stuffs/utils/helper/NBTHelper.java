/**
 * CCM Modding, ModJam
 */
package ccm.trade_stuffs.utils.helper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * NBTHelper
 * <p>
 * 
 * @author Captain_Shadows
 */
public class NBTHelper
{
    public static void initCompound(final ItemStack item)
    {
        if (item.getTagCompound() == null)
        {
            item.setTagCompound(new NBTTagCompound());
        }
    }

    public static void setBoolean(final ItemStack item, final String keyName, final boolean value)
    {
        initCompound(item);

        item.getTagCompound().setBoolean(keyName, value);
    }

    public static boolean getBoolean(final ItemStack item, final String keyName)
    {
        initCompound(item);

        if (!(item.getTagCompound().hasKey(keyName)))
        {

        }
        return item.getTagCompound().getBoolean(keyName);
    }
}