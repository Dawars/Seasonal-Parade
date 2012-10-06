package seasonal.parade.halloween.render;

import net.minecraft.src.Block;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.RenderLiving;
import net.minecraftforge.client.IItemRenderer;
import static net.minecraftforge.client.IItemRenderer.ItemRenderType.*;
import static net.minecraftforge.client.IItemRenderer.ItemRendererHelper.*;
import net.minecraftforge.client.MinecraftForgeClient;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import org.lwjgl.opengl.GL11;

import seasonal.parade.halloween.client.EntityAshman;
import seasonal.parade.halloween.client.ModelAshman;
import seasonal.parade.halloween.common.Halloween;

@SideOnly(Side.CLIENT)
public class RenderAshman extends RenderLiving
{
    /** A reference to the Snowman model in RenderSnowMan. */
    private ModelAshman evilSnowmanModel;

    public RenderAshman()
    {
        super(new ModelAshman(), 0.5F);
        this.evilSnowmanModel = (ModelAshman)super.mainModel;
        this.setRenderPassModel(this.evilSnowmanModel);
    }

    /**
     * Renders this snowman's pumpkin.
     */
    protected void renderSnowmanPumpkin(EntityAshman par1EntitySnowman, float par2)
    {
        super.renderEquippedItems(par1EntitySnowman, par2);
        ItemStack var3 = new ItemStack(Halloween.evilLantern, 1);

        if (var3 != null && var3.getItem() instanceof ItemBlock)
        {
            GL11.glPushMatrix();
            this.evilSnowmanModel.head.postRender(0.0625F);

            IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(var3, EQUIPPED);
            boolean is3D = (customRenderer != null && customRenderer.shouldUseRenderHelper(EQUIPPED, var3, BLOCK_3D));

            if (is3D || RenderBlocks.renderItemIn3d(Block.blocksList[var3.itemID].getRenderType()))
            {
                float var4 = 0.625F;
                GL11.glTranslatef(0.0F, -0.34375F, 0.0F);
                GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(var4, -var4, var4);
            }

            this.renderManager.itemRenderer.renderItem(par1EntitySnowman, var3, 0);
            GL11.glPopMatrix();
        }
    }

    protected void renderEquippedItems(EntityLiving par1EntityLiving, float par2)
    {
        this.renderSnowmanPumpkin((EntityAshman)par1EntityLiving, par2);
    }
}
