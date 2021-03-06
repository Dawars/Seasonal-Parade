package seasonal.parade.halloween.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityGiantZombie;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.ModelBase;
import net.minecraft.src.RenderLiving;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderHeadless extends RenderLiving
{
	/** Scale of the model to use */
    private float scale;
    public RenderHeadless(float scale)
    {
        super(new ModelHeadless(), 0.5F);
        this.scale = scale;
    }
    public void renderHeadless(EntityHeadless par1EntityHeadless, double par2, double par4, double par6, float par8, float par9)
    {
        super.doRenderLiving(par1EntityHeadless, par2, par4, par6, par8, par9);
    }

    public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
    {
        this.renderHeadless((EntityHeadless)par1EntityLiving, par2, par4, par6, par8, par9);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.renderHeadless((EntityHeadless)par1Entity, par2, par4, par6, par8, par9);
    }
    
    /**
     * Applies the scale to the transform matrix
     */
    protected void preRenderScale(EntityHeadless par1EntityGiantZombie, float par2)
    {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLiving par1EntityLiving, float par2)
    {
        this.preRenderScale((EntityHeadless)par1EntityLiving, par2);
    }
}
