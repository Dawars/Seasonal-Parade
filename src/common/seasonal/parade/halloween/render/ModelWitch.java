package seasonal.parade.halloween.render;

import net.minecraft.src.Entity;
import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelRenderer;

public class ModelWitch extends ModelBase
{
      public ModelWitch()
      {
        head = new ModelRenderer(this, 40, 0);
        head.addBox(-3F, -3F, -3F, 6, 4, 6);
        head.setRotationPoint(0F, 0F, 0F);
        head.rotateAngleX = 0F;
        head.rotateAngleY = -0.20944F;
        head.rotateAngleZ = 0F;
        head.mirror = false;
        body = new ModelRenderer(this, 0, 18);
        body.addBox(-4F, 1F, -2F, 8, 10, 4);
        body.setRotationPoint(0F, 0F, 0F);
        body.rotateAngleX = -0.08727F;
        body.rotateAngleY = -1.5708F;
        body.rotateAngleZ = 0F;
        body.mirror = false;
        rightarm = new ModelRenderer(this, 52, 16);
        rightarm.addBox(-2F, -1F, -7F, 3, 10, 3);
        rightarm.setRotationPoint(1F, 2F, 0F);
        rightarm.rotateAngleX = 0F;
        rightarm.rotateAngleY = 0F;
        rightarm.rotateAngleZ = 0F;
        rightarm.mirror = true;
        leftarm = new ModelRenderer(this, 52, 16);
        leftarm.addBox(0F, -1F, 1F, 3, 10, 3);
        leftarm.setRotationPoint(-1F, 2F, 3F);
        leftarm.rotateAngleX = 0F;
        leftarm.rotateAngleY = 0F;
        leftarm.rotateAngleZ = 0F;
        leftarm.mirror = true;
        leftleg = new ModelRenderer(this, 0, 22);
        leftleg.addBox(-4F, -2F, -2F, 8, 6, 4);
        leftleg.setRotationPoint(2F, 12F, 0F);
        leftleg.rotateAngleX = -0.43633F;
        leftleg.rotateAngleY = -1.5708F;
        leftleg.rotateAngleZ = 0F;
        leftleg.mirror = false;
        stick = new ModelRenderer(this, 22, 11);
        stick.addBox(-1F, 0F, 0F, 2, 2, 19);
        stick.setRotationPoint(0F, 11F, -8F);
        stick.rotateAngleX = 0F;
        stick.rotateAngleY = 0F;
        stick.rotateAngleZ = 0F;
        stick.mirror = false;
        hood = new ModelRenderer(this, 0, 0);
        hood.addBox(-5F, -5F, -5F, 10, 2, 10);
        hood.setRotationPoint(0F, 0F, 0F);
        hood.rotateAngleX = 0F;
        hood.rotateAngleY = -0.20944F;
        hood.rotateAngleZ = 0F;
        hood.mirror = false;
        hood1 = new ModelRenderer(this, 0, 0);
        hood1.addBox(-3.5F, -7F, -3.5F, 7, 3, 7);
        hood1.setRotationPoint(0F, 0F, 0F);
        hood1.rotateAngleX = 0F;
        hood1.rotateAngleY = -0.20944F;
        hood1.rotateAngleZ = 0F;
        hood1.mirror = false;
        hood11 = new ModelRenderer(this, 0, 0);
        hood11.addBox(-3F, -8F, -3F, 6, 2, 6);
        hood11.setRotationPoint(0F, 0F, 0F);
        hood11.rotateAngleX = 0F;
        hood11.rotateAngleY = -0.20944F;
        hood11.rotateAngleZ = 0F;
        hood11.mirror = false;
        hood111 = new ModelRenderer(this, 0, 0);
        hood111.addBox(-2.5F, -9F, -2.5F, 5, 2, 5);
        hood111.setRotationPoint(0F, 0F, 0F);
        hood111.rotateAngleX = 0F;
        hood111.rotateAngleY = -0.20944F;
        hood111.rotateAngleZ = 0F;
        hood111.mirror = false;
        hood1111 = new ModelRenderer(this, 0, 0);
        hood1111.addBox(-2F, -10F, -2F, 4, 2, 4);
        hood1111.setRotationPoint(0F, 0F, 0F);
        hood1111.rotateAngleX = 0F;
        hood1111.rotateAngleY = -0.20944F;
        hood1111.rotateAngleZ = 0F;
        hood1111.mirror = false;
        hood11111 = new ModelRenderer(this, 0, 0);
        hood11111.addBox(-1.5F, -11F, -1.5F, 3, 2, 3);
        hood11111.setRotationPoint(0F, 0F, 0F);
        hood11111.rotateAngleX = 0F;
        hood11111.rotateAngleY = -0.20944F;
        hood11111.rotateAngleZ = 0F;
        hood11111.mirror = false;
        hood111111 = new ModelRenderer(this, 0, 0);
        hood111111.addBox(-1F, -12F, -1F, 2, 2, 2);
        hood111111.setRotationPoint(0F, 0F, 0F);
        hood111111.rotateAngleX = 0F;
        hood111111.rotateAngleY = -0.20944F;
        hood111111.rotateAngleZ = 0F;
        hood111111.mirror = false;
        leftleg1 = new ModelRenderer(this, 0, 22);
        leftleg1.addBox(-4F, 2.7F, -3.55F, 8, 6, 4);
        leftleg1.setRotationPoint(2F, 12F, 0F);
        leftleg1.rotateAngleX = 0.01745F;
        leftleg1.rotateAngleY = -1.5708F;
        leftleg1.rotateAngleZ = 0F;
        leftleg1.mirror = false;
        broom = new ModelRenderer(this, 27, 12);
        broom.addBox(-2F, -1F, 18F, 4, 4, 3);
        broom.setRotationPoint(0F, 11F, -8F);
        broom.rotateAngleX = 0F;
        broom.rotateAngleY = 0F;
        broom.rotateAngleZ = 0F;
        broom.mirror = false;
        broom1 = new ModelRenderer(this, 27, 12);
        broom1.addBox(-1.5F, -0.5F, 17F, 3, 3, 3);
        broom1.setRotationPoint(0F, 11F, -8F);
        broom1.rotateAngleX = 0F;
        broom1.rotateAngleY = 0F;
        broom1.rotateAngleZ = 0F;
        broom1.mirror = false;
        broom2 = new ModelRenderer(this, 29, 12);
        broom2.addBox(-1.5F, -0.5F, 19F, 3, 3, 3);
        broom2.setRotationPoint(0F, 11F, -8F);
        broom2.rotateAngleX = 0F;
        broom2.rotateAngleY = 0F;
        broom2.rotateAngleZ = 0F;
        broom2.mirror = false;
        broom21 = new ModelRenderer(this, 29, 12);
        broom21.addBox(-1F, 0F, 21F, 2, 2, 2);
        broom21.setRotationPoint(0F, 11F, -8F);
        broom21.rotateAngleX = 0F;
        broom21.rotateAngleY = 0F;
        broom21.rotateAngleZ = 0F;
        broom21.mirror = false;
        broom211 = new ModelRenderer(this, 29, 12);
        broom211.addBox(-0.5F, 0.5F, 22F, 1, 1, 1);
        broom211.setRotationPoint(0F, 11F, -8F);
        broom211.rotateAngleX = 0F;
        broom211.rotateAngleY = 0F;
        broom211.rotateAngleZ = 0F;
        broom211.mirror = false;
      }

      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
      {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5);
        head.render(f5);
        body.render(f5);
        rightarm.render(f5);
        leftarm.render(f5);
        leftleg.render(f5);
        stick.render(f5);
        hood.render(f5);
        hood1.render(f5);
        hood11.render(f5);
        hood111.render(f5);
        hood1111.render(f5);
        hood11111.render(f5);
        hood111111.render(f5);
        leftleg1.render(f5);
        broom.render(f5);
        broom1.render(f5);
        broom2.render(f5);
        broom21.render(f5);
        broom211.render(f5);
      }

      public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
      {
        super.setRotationAngles(f, f1, f2, f3, f4, f5);
      }

      //fields
        ModelRenderer head;
        ModelRenderer body;
        ModelRenderer rightarm;
        ModelRenderer leftarm;
        ModelRenderer leftleg;
        ModelRenderer stick;
        ModelRenderer hood;
        ModelRenderer hood1;
        ModelRenderer hood11;
        ModelRenderer hood111;
        ModelRenderer hood1111;
        ModelRenderer hood11111;
        ModelRenderer hood111111;
        ModelRenderer leftleg1;
        ModelRenderer broom;
        ModelRenderer broom1;
        ModelRenderer broom2;
        ModelRenderer broom21;
        ModelRenderer broom211;
}
