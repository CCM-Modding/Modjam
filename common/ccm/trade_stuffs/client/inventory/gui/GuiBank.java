/**
 * CCM Modding, ModJam
 */
package ccm.trade_stuffs.client.inventory.gui;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet250CustomPayload;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import ccm.nucleum.omnium.utils.handler.ResourceHandler;
import ccm.trade_stuffs.inventory.ContainerBank;
import ccm.trade_stuffs.inventory.InventoryBank;
import ccm.trade_stuffs.inventory.InventoryBankCoins;
import ccm.trade_stuffs.inventory.InventoryBankItems;
import ccm.trade_stuffs.items.ModItems;
import ccm.trade_stuffs.tileentity.TileEntityBank;
import ccm.trade_stuffs.utils.lib.Guis;

/**
 * GUITrade
 * <p>
 * 
 * @author Captain_Shadows
 */
@SideOnly(Side.CLIENT)
public class GuiBank extends GuiContainer
{

    private final TileEntityBank bank;
    private final InventoryBank upperInventory;
    private final InventoryPlayer lowerInventory;

    public byte selectedTab = 0;

    private final ItemStack displayCoins = new ItemStack(ModItems.coin.itemID, 1, 0);
    private final ItemStack displayItems = new ItemStack(Block.stone.blockID, 1, 0);

    public GuiBank(final InventoryPlayer lowerInventory, final InventoryBank upperInventory, final TileEntityBank tile, final byte tab)
    {
        super(new ContainerBank(lowerInventory, upperInventory, tile, tab));
        this.lowerInventory = lowerInventory;
        this.upperInventory = upperInventory;
        bank = tile;
        xSize = 176;
        ySize = 238;
        allowUserInput = false;
        selectedTab = tab;
    }

    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int button)
    {
        super.mouseClicked(mouseX, mouseY, button);
        if ((mouseX > (guiLeft - 27)) && (mouseX <= guiLeft))
        {
            if ((mouseY > (guiTop + 17)) && (mouseY <= (guiTop + 38)))
            {
                selectedTab = 0;
                sendTabUpdate();
            } else if ((mouseY > (guiTop + 38)) && (mouseY <= (guiTop + 59)))
            {
                selectedTab = 1;
                sendTabUpdate();
            }
        }
    }

    public void sendTabUpdate()
    {
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        final DataOutputStream dos = new DataOutputStream(bos);
        try
        {
            dos.writeInt(10);
            dos.writeInt(bank.xCoord);
            dos.writeInt(bank.yCoord);
            dos.writeInt(bank.zCoord);
            dos.writeInt(bank.worldObj.provider.dimensionId);

            dos.writeByte(selectedTab);
        } catch (final Exception e)
        {
            e.printStackTrace();
        }
        final Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel = "TradeStuffs";
        packet.data = bos.toByteArray();
        packet.length = bos.size();
        PacketDispatcher.sendPacketToServer(packet);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(final float opacity, final int mouseX, final int mouseY)
    {
        GL11.glColor4f(1, 1, 1, 1);
        ResourceHandler.bindGUI(mc, Guis.BANK_NAME);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        if (selectedTab == 0)
        {
            drawTexturedModalRect(guiLeft - 24, guiTop + 17, 229, 0, 27, 21);
        } else
        {
            drawTexturedModalRect(guiLeft - 24, guiTop + 17, 229, 21, 27, 21);
        }
        if (selectedTab == 1)
        {
            drawTexturedModalRect(guiLeft - 24, guiTop + 17 + 21, 229, 0, 27, 21);
        } else
        {
            drawTexturedModalRect(guiLeft - 24, guiTop + 17 + 21, 229, 21, 27, 21);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY)
    {
        itemRenderer.renderItemAndEffectIntoGUI(fontRenderer, mc.getTextureManager(), displayCoins, -18, 20);
        itemRenderer.renderItemAndEffectIntoGUI(fontRenderer, mc.getTextureManager(), displayItems, -18, 41);

        fontRenderer.drawString(lowerInventory.isInvNameLocalized() ? lowerInventory.getInvName() : I18n.getStringParams(lowerInventory.getInvName()), 8, ySize - 93, 4210752);
        fontRenderer.drawString(upperInventory.isInvNameLocalized() ? upperInventory.getInvName() : I18n.getStringParams(upperInventory.getInvName()), 8, 6, 4210752);
        fontRenderer.drawString(mc.thePlayer.username, 80, 6, 4210752);

        if (upperInventory instanceof InventoryBankCoins)
        {
            fontRenderer.drawString("Coins: " + Integer.toString(bank.getCoinBalance()), 80, ySize - 93, 4210752);
        } else if (upperInventory instanceof InventoryBankItems)
        {
            fontRenderer.drawString("Items: " + Integer.toString(bank.getItemCount()), 80, ySize - 93, 4210752);
        }
    }
}