/**
 * CCM Modding, ModJam
 */
package ccm.trade_stuffs.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

import ccm.nucleum.omnium.utils.helper.item.InventoryHelper;

/**
 * BaseInventory
 * <p>
 * 
 * @author Captain_Shadows
 */
public abstract class BaseInventory extends TileEntity implements IInventory
{
    protected ItemStack[] inventory = new ItemStack[1];

    @Override
    public int getSizeInventory()
    {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(final int slot)
    {
        return inventory[slot];
    }

    @Override
    public ItemStack decrStackSize(final int slot, final int amount)
    {
        return InventoryHelper.decrStackSize(this, slot, amount);
    }

    @Override
    public ItemStack getStackInSlotOnClosing(final int slot)
    {
        if (inventory[slot] != null)
        {
            final ItemStack itemStack = inventory[slot];
            inventory[slot] = null;
            return itemStack;
        } else
        {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(final int slot, final ItemStack itemStack)
    {
        inventory[slot] = itemStack;
    }

    @Override
    public String getInvName()
    {
        return null;
    }

    @Override
    public boolean isInvNameLocalized()
    {
        return false;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(final EntityPlayer player)
    {
        return player.getDistance(xCoord, yCoord, yCoord) <= 10;
    }

    @Override
    public boolean isItemValidForSlot(final int i, final ItemStack itemstack)
    {
        return false;
    }

    @Override
    public void openChest()
    {}

    @Override
    public void closeChest()
    {}

    public void setInventory(final ItemStack[] inventory)
    {
        this.inventory = inventory;
    }

    @Override
    public Packet getDescriptionPacket()
    {
        final NBTTagCompound nbt = new NBTTagCompound();
        writeToNBT(nbt);
        return new Packet132TileEntityData(xCoord, yCoord, zCoord, 2, nbt);
    }

    @Override
    public void onDataPacket(final INetworkManager net, final Packet132TileEntityData pkt)
    {
        readFromNBT(pkt.data);
    }
}