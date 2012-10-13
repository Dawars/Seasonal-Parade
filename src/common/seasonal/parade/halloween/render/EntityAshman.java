package seasonal.parade.halloween.render;

import seasonal.parade.halloween.DefaultProps;
import seasonal.parade.halloween.Halloween;
import net.minecraft.src.Block;
import net.minecraft.src.DamageSource;
import net.minecraft.src.EntityAIArrowAttack;
import net.minecraft.src.EntityAILookIdle;
import net.minecraft.src.EntityAINearestAttackableTarget;
import net.minecraft.src.EntityAIWander;
import net.minecraft.src.EntityAIWatchClosest;
import net.minecraft.src.EntityGolem;
import net.minecraft.src.EntityMob;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;

public class EntityAshman extends EntityGolem
{
    public EntityAshman(World par1World)
    {
        super(par1World);
        this.texture = DefaultProps.TEXTURE_PATH + "/mob/ashman.png";
        this.setSize(0.4F, 1.8F);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAIArrowAttack(this, 0.25F, 2, 20));
        this.tasks.addTask(2, new EntityAIWander(this, 0.2F));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityMob.class, 16.0F, 0, true));
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    public boolean isAIEnabled()
    {
        return true;
    }

    public int getMaxHealth()
    {
        return 8;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (this.isWet())
        {
            this.attackEntityFrom(DamageSource.drown, 1);
        }

        int var1 = MathHelper.floor_double(this.posX);
        int var2 = MathHelper.floor_double(this.posZ);
//
//        if (this.worldObj.getBiomeGenForCoords(var1, var2).getFloatTemperature() > 1.0F)
//        {
//            this.attackEntityFrom(DamageSource.onFire, 1);
//        }
//
        for (var1 = 0; var1 < 4; ++var1)
        {
            var2 = MathHelper.floor_double(this.posX + (double)((float)(var1 % 2 * 2 - 1) * 0.25F));
            int var3 = MathHelper.floor_double(this.posY);
            int var4 = MathHelper.floor_double(this.posZ + (double)((float)(var1 / 2 % 2 * 2 - 1) * 0.25F));

            if (this.worldObj.getBlockId(var2, var3, var4) == 0 && Halloween.ash.canPlaceBlockAt(this.worldObj, var2, var3, var4)) //this.worldObj.getBiomeGenForCoords(var2, var4).getFloatTemperature() < 0.8F &&
            {
                this.worldObj.setBlockWithNotify(var2, var3, var4, Halloween.ash.blockID);
            }
        }
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getDropItemId()
    {
        return Halloween.ashItem.shiftedIndex;
    }

    /**
     * Drop 0-2 items of this living's type
     */
    protected void dropFewItems(boolean par1, int par2)
    {
        int var3 = this.rand.nextInt(16);

        for (int var4 = 0; var4 < var3; ++var4)
        {
            this.dropItem(this.getDropItemId(), 1);
        }
        
        this.dropItem(Halloween.evilPumpkin.blockID, 1);

    }
}
