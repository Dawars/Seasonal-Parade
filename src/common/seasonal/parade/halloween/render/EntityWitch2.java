package seasonal.parade.halloween.render;

import net.minecraft.src.DamageSource;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityAIAttackOnCollide;
import net.minecraft.src.EntityAILookIdle;
import net.minecraft.src.EntityAIMoveTwardsRestriction;
import net.minecraft.src.EntityAINearestAttackableTarget;
import net.minecraft.src.EntityAISwimming;
import net.minecraft.src.EntityAIWander;
import net.minecraft.src.EntityAIWatchClosest;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityMob;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntitySmallFireball;
import net.minecraft.src.Item;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Potion;
import net.minecraft.src.PotionEffect;
import net.minecraft.src.World;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

public class EntityWitch2 extends EntityMob
{
    /** Random offset used in floating behaviour */
    private float heightOffset = 0.5F;

    /** ticks until heightOffset is randomized */
    private int heightOffsetUpdateTime;
    private int field_70846_g;

    public EntityWitch2(World par1World)
    {
        super(par1World);
        this.texture = "/seasonal/parade/halloween/gfx/mob/Witch.png";
        this.attackStrength = 6;
        this.experienceValue = 10;
        this.getNavigator().setAvoidsWater(true);
        
        this.tasks.addTask(0, new EntityAIMoveTwardsRestriction(this, this.moveSpeed));
        
        this.tasks.addTask(1, new EntityAIAttackOnCollide(this, EntityPlayer.class, this.moveSpeed, false));

        this.tasks.addTask(2, new EntityAIWander(this, this.moveSpeed));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        
        this.targetTasks.addTask(0, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 16.0F, 0, true));

    }

    public int getMaxHealth()
    {
        return 20;
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte((byte)0));
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        return "mob.blaze.breathe";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "mob.blaze.hit";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "mob.blaze.death";
    }

    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float par1)
    {
        return 15728880;
    }

    /**
     * Gets how bright this entity is.
     */
    public float getBrightness(float par1)
    {
        return 1.0F;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        if (!this.worldObj.isRemote)
        {
            if (this.isWet())
            {
                this.attackEntityFrom(DamageSource.drown, 1);
            }

            --this.heightOffsetUpdateTime;

            if (this.heightOffsetUpdateTime <= 0)
            {
                this.heightOffsetUpdateTime = 100;
                this.heightOffset = 0.5F + (float)this.rand.nextGaussian() * 3.0F;
            }

            if (this.getEntityToAttack() != null && this.getEntityToAttack().posY + (double)this.getEntityToAttack().getEyeHeight() > this.posY + (double)this.getEyeHeight() + (double)this.heightOffset)
            {
                this.motionY += (0.30000001192092896D - this.motionY) * 0.30000001192092896D;
            }
        }


        if (!this.onGround && this.motionY < 0.0D)
        {
            this.motionY *= 0.6D;
        }

        for (int var1 = 0; var1 < 2; ++var1)
        {
            this.worldObj.spawnParticle("largesmoke", this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
        }

        super.onLivingUpdate();
    }

    /**
     * Basic mob attack. Default to touch of death in EntityCreature. Overridden by each mob to define their attack.
     */
    protected void attackEntity(Entity par1Entity, float par2)
    {
        if (this.attackTime <= 0 && par2 < 2.0F && par1Entity.boundingBox.maxY > this.boundingBox.minY && par1Entity.boundingBox.minY < this.boundingBox.maxY)
        {
            this.attackTime = 20;
            this.attackEntityAsMob(par1Entity);
        }
        else if (par2 < 30.0F)
        {
            double var3 = par1Entity.posX - this.posX;
            double var5 = par1Entity.boundingBox.minY + (double)(par1Entity.height / 2.0F) - (this.posY + (double)(this.height / 2.0F));
            double var7 = par1Entity.posZ - this.posZ;

            if (this.attackTime == 0)
            {
                ++this.field_70846_g;

                if (this.field_70846_g == 1)
                {
                    this.attackTime = 60;
                }
                else if (this.field_70846_g <= 4)
                {
                    this.attackTime = 6;
                }
                else
                {
                    this.attackTime = 100;
                    this.field_70846_g = 0;
                }

                if (this.field_70846_g > 1)
                {
                    float var9 = MathHelper.sqrt_float(par2) * 0.5F;
                    this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1009, (int)this.posX, (int)this.posY, (int)this.posZ, 0);

//                    for (int var10 = 0; var10 < 1; ++var10)
//                    {
//                        EntitySmallFireball var11 = new EntitySmallFireball(this.worldObj, this, var3 + this.rand.nextGaussian() * (double)var9, var5, var7 + this.rand.nextGaussian() * (double)var9);
//                        var11.posY = this.posY + (double)(this.height / 2.0F) + 0.5D;
//                        this.worldObj.spawnEntityInWorld(var11);
//                    }
                }
            }

            this.rotationYaw = (float)(Math.atan2(var7, var3) * 180.0D / Math.PI) - 90.0F;
            this.hasAttacked = true;
        }
    }
//
//    public boolean attackEntityAsMob(Entity par1Entity)
//    {
//        if (super.attackEntityAsMob(par1Entity))
//        {
//            if (par1Entity instanceof EntityLiving)
//            {
//                byte var2 = 0;
//
//                if (this.worldObj.difficultySetting > 1)
//                {
//                    if (this.worldObj.difficultySetting == 2)
//                    {
//                        var2 = 7;
//                    }
//                    else if (this.worldObj.difficultySetting == 3)
//                    {
//                        var2 = 15;
//                    }
//                }
//
//                if (var2 > 0)
//                {
//                    ((EntityLiving)par1Entity).addPotionEffect(new PotionEffect(Potion.poison.id, var2 * 20, 0));
//                }
//            }
//
//            return true;
//        }
//        else
//        {
//            return false;
//        }
//    }
    /**
     * Called when the mob is falling. Calculates and applies fall damage.
     */
    protected void fall(float par1) {}

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getDropItemId()
    {
        return Item.blazeRod.shiftedIndex;
    }

    /**
     * Drop 0-2 items of this living's type
     */
    protected void dropFewItems(boolean par1, int par2)
    {
        if (par1)
        {
            int var3 = this.rand.nextInt(2 + par2);

            for (int var4 = 0; var4 < var3; ++var4)
            {
                this.dropItem(Item.blazeRod.shiftedIndex, 1);
            }
        }
    }


    /**
     * Checks to make sure the light is not too bright where the mob is spawning
     */
    protected boolean isValidLightLevel()
    {
        return true;
    }
}
