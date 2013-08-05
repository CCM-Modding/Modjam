/**
 * CCM Modding, ModJam
 */
package ccm.trade_stuffs.api.drops;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

/**
 * EntityDrop
 * <p>
 * 
 * @author Captain_Shadows
 */
public class EntityDropHandler
{
    /**
     * Name of the mod registering this coin
     */
    private final String                            modID;

    /**
     * Item to drop
     */
    private final ItemStack                         item;
    /**
     * The amount to drop
     */
    private final int                               amount;
    /**
     * The drop rate of it
     */
    private final float                             dropRate;
    /**
     * The entity that should drop it
     */
    private final Class<? extends EntityLivingBase> entity;

    /**
     * @param modID
     *            ID of the mod adding this drop
     * @param item
     *            The Item to drop
     * @param dropRate
     *            the Rate at which to drop it
     * @param entitys
     *            the entity's that are allowed to drop it
     */
    public EntityDropHandler(final String modID,
                      final ItemStack item,
                      final int amount,
                      final float dropRate,
                      final Class<? extends EntityLivingBase> entity)
    {
        this.modID = modID;
        this.item = item;
        this.amount = amount;
        this.dropRate = dropRate;
        this.entity = entity;
    }

    /**
     * @return The ID of the mod that registered this coin
     */
    public String getModID()
    {
        return modID;
    }

    /**
     * @return The item to drop
     */
    public ItemStack getDrop()
    {
        return item;
    }

    /**
     * @return The amount
     */
    public float getAmount()
    {
        return amount;
    }

    /**
     * @return The drop rate
     */
    public float getDropRate()
    {
        return dropRate;
    }

    /**
     * @return The entity that should drop it
     */
    public Class<? extends EntityLivingBase> getEntity()
    {
        return entity;
    }

    public boolean shouldDrop(final Class<? extends EntityLivingBase> entity)
    {
        return entity.equals(this.entity);
    }

    public void dropItem(final EntityLivingBase entity)
    {
        if (shouldDrop(entity.getClass()))
        {
            final double rand = Math.random();

            if (rand < dropRate)
            {
                entity.dropItem(item.itemID, amount);
            }
        }
    }
}