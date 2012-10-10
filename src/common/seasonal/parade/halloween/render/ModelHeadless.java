package seasonal.parade.halloween.render;

import net.minecraft.src.Entity;
import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelRenderer;

public class ModelHeadless extends ModelBase
{
      
    ModelRenderer horsebody;
    ModelRenderer horsehead;
    ModelRenderer horseneck;
    ModelRenderer horseear1;
    ModelRenderer horseear2;
    ModelRenderer horselegleft1;
    ModelRenderer horselegleft2;
    ModelRenderer horselegright1;
    ModelRenderer horselegright2;
    ModelRenderer horsetail;
    ModelRenderer humanbody;
    ModelRenderer humanrightarm;
    ModelRenderer humanleftarm;
    ModelRenderer humanrightleg;
    ModelRenderer humanleftleg;
    ModelRenderer humanlefthead;
    ModelRenderer humanrightspear;
    ModelRenderer humancoatfront;
    ModelRenderer humancoatside;
    ModelRenderer humancoatside2;
    ModelRenderer humancoattop;
  
    public ModelHeadless()
    {
    textureWidth = 64;
    textureHeight = 32;
    
      horsebody = new ModelRenderer(this, 0, 21);
      horsebody.addBox(0F, -0.5F, 0F, 4, 4, 7);
      horsebody.setRotationPoint(-1.5F, 17F, -2F);
      horsebody.setTextureSize(64, 32);
      horsebody.mirror = true;
      setRotation(horsebody, 0F, 0F, 0F);
      horsehead = new ModelRenderer(this, 0, 0);
      horsehead.addBox(-1F, -2F, -4F, 3, 3, 4);
      horsehead.setRotationPoint(0F, 16F, -3F);
      horsehead.setTextureSize(64, 32);
      horsehead.mirror = true;
      setRotation(horsehead, 0F, 0F, 0F);
      horseneck = new ModelRenderer(this, 0, 26);
      horseneck.addBox(-0.5F, -1F, -1F, 2, 2, 4);
      horseneck.setRotationPoint(0F, 16F, -3F);
      horseneck.setTextureSize(64, 32);
      horseneck.mirror = true;
      setRotation(horseneck, -0.6981317F, 0F, 0F);
      horseear1 = new ModelRenderer(this, 0, 30);
      horseear1.addBox(-1F, -2.5F, -1.5F, 1, 1, 1);
      horseear1.setRotationPoint(0F, 16F, -3F);
      horseear1.setTextureSize(64, 32);
      horseear1.mirror = true;
      setRotation(horseear1, 0F, 0F, 0F);
      horseear2 = new ModelRenderer(this, 0, 30);
      horseear2.addBox(1F, -2.5F, -1.5F, 1, 1, 1);
      horseear2.setRotationPoint(0F, 16F, -3F);
      horseear2.setTextureSize(64, 32);
      horseear2.mirror = true;
      setRotation(horseear2, 0F, 0F, 0F);
      horselegleft1 = new ModelRenderer(this, 0, 26);
      horselegleft1.addBox(0F, 0F, 0F, 1, 4, 2);
      horselegleft1.setRotationPoint(1.5F, 20F, -2F);
      horselegleft1.setTextureSize(64, 32);
      horselegleft1.mirror = true;
      setRotation(horselegleft1, 0F, 0F, 0F);
      horselegleft2 = new ModelRenderer(this, 0, 26);
      horselegleft2.addBox(0F, 0F, 0F, 1, 4, 2);
      horselegleft2.setRotationPoint(1.5F, 20F, 3F);
      horselegleft2.setTextureSize(64, 32);
      setRotation(horselegleft2, 0F, 0F, 0F);
      horselegleft2.mirror = false;
      horselegright1 = new ModelRenderer(this, 0, 26);
      horselegright1.addBox(-1F, 0F, 0F, 1, 4, 2);
      horselegright1.setRotationPoint(-0.5F, 20F, -2F);
      horselegright1.setTextureSize(64, 32);
      horselegright1.mirror = true;
      setRotation(horselegright1, 0F, 0F, 0F);
      horselegright2 = new ModelRenderer(this, 0, 26);
      horselegright2.addBox(-1F, 0F, 0F, 1, 4, 2);
      horselegright2.setRotationPoint(-0.5F, 20F, 3F);
      horselegright2.setTextureSize(64, 32);
      horselegright2.mirror = true;
      setRotation(horselegright2, 0F, 0F, 0F);
      horsetail = new ModelRenderer(this, 0, 25);
      horsetail.addBox(1F, 1.5F, 6F, 2, 5, 2);
      horsetail.setRotationPoint(-1.5F, 17F, -2F);
      horsetail.setTextureSize(64, 32);
      horsetail.mirror = true;
      setRotation(horsetail, 0.3141593F, 0F, 0F);
      humanbody = new ModelRenderer(this, 48, 16);
      humanbody.addBox(0.5F, -0.5F, 0.5F, 4, 6, 2);
      humanbody.setRotationPoint(-2F, 10F, 0F);
      humanbody.setTextureSize(64, 32);
      humanbody.mirror = true;
      setRotation(humanbody, 0F, 0F, 0F);
      humanrightarm = new ModelRenderer(this, 56, 0);
      humanrightarm.addBox(-1.5F, -0.5F, 1F, 2, 5, 2);
      humanrightarm.setRotationPoint(-2F, 10F, 0F);
      humanrightarm.setTextureSize(64, 32);
      humanrightarm.mirror = true;
      setRotation(humanrightarm, 0F, 0F, 0.0523599F);
      humanleftarm = new ModelRenderer(this, 56, 0);
      humanleftarm.addBox(-0.5F, -0.5F, 1F, 2, 5, 2);
      humanleftarm.setRotationPoint(3F, 9F, 2F);
      humanleftarm.setTextureSize(64, 32);
      setRotation(humanleftarm, -1.570796F, 0F, -0.0523599F);
      humanleftarm.mirror = false;
      humanrightleg = new ModelRenderer(this, 50, 9);
      humanrightleg.addBox(0.5F, -0.5F, -4F, 2, 2, 5);
      humanrightleg.setRotationPoint(-2F, 15F, 2F);
      humanrightleg.setTextureSize(64, 32);
      humanrightleg.mirror = true;
      setRotation(humanrightleg, 0.0872665F, 0.3141593F, 0F);
      humanleftleg = new ModelRenderer(this, 50, 9);
      humanleftleg.addBox(-0.5F, -0.5F, -4.5F, 2, 2, 5);
      humanleftleg.setRotationPoint(1F, 15F, 2F);
      humanleftleg.setTextureSize(64, 32);
      humanleftleg.mirror = true;
      setRotation(humanleftleg, 0.0872665F, -0.3141593F, 0F);
      humanlefthead = new ModelRenderer(this, 52, 26);
      humanlefthead.addBox(-1F, 0.5F, -7F, 3, 3, 3);
      humanlefthead.setRotationPoint(3F, 9F, 2F);
      humanlefthead.setTextureSize(64, 32);
      humanlefthead.mirror = true;
      setRotation(humanlefthead, 0F, 0F, -0.0523599F);
      humanrightspear = new ModelRenderer(this, 22, 20);
      humanrightspear.addBox(-1F, 3F, -7F, 1, 1, 11);
      humanrightspear.setRotationPoint(-2F, 10F, 0F);
      humanrightspear.setTextureSize(64, 32);
      humanrightspear.mirror = true;
      setRotation(humanrightspear, 0F, 0F, 0.0523599F);
      humancoatfront = new ModelRenderer(this, 48, 0);
      humancoatfront.addBox(0.5F, -0.5F, -0.5F, 4, 4, 0);
      humancoatfront.setRotationPoint(-2F, 10F, 0F);
      humancoatfront.setTextureSize(64, 32);
      humancoatfront.mirror = true;
      setRotation(humancoatfront, 0.0523599F, 0F, 0F);
      humancoatside = new ModelRenderer(this, 36, -6);
      humancoatside.addBox(0.5F, -0.5F, -0.5F, 0, 4, 6);
      humancoatside.setRotationPoint(-2F, 10F, 0F);
      humancoatside.setTextureSize(64, 32);
      humancoatside.mirror = true;
      setRotation(humancoatside, 0.0523599F, 0F, 0F);
      humancoatside2 = new ModelRenderer(this, 36, -6);
      humancoatside2.addBox(4.5F, -0.5F, -0.5F, 0, 4, 6);
      humancoatside2.setRotationPoint(-2F, 10F, 0F);
      humancoatside2.setTextureSize(64, 32);
      humancoatside2.mirror = true;
      setRotation(humancoatside2, 0.0523599F, 0F, 0F);
      humancoatside2.mirror = false;
      humancoattop = new ModelRenderer(this, 24, 0);
      humancoattop.addBox(0.5F, -0.5F, -0.5F, 4, 0, 4);
      humancoattop.setRotationPoint(-2F, 10F, 0F);
      humancoattop.setTextureSize(64, 32);
      humancoattop.mirror = true;
      setRotation(humancoattop, 0.0523599F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5);
    horsebody.render(f5);
    horsehead.render(f5);
    horseneck.render(f5);
    horseear1.render(f5);
    horseear2.render(f5);
    horselegleft1.render(f5);
    horselegleft2.render(f5);
    horselegright1.render(f5);
    horselegright2.render(f5);
    horsetail.render(f5);
    humanbody.render(f5);
    humanrightarm.render(f5);
    humanleftarm.render(f5);
    humanrightleg.render(f5);
    humanleftleg.render(f5);
    humanlefthead.render(f5);
    humanrightspear.render(f5);
    humancoatfront.render(f5);
    humancoatside.render(f5);
    humancoatside2.render(f5);
    humancoattop.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5);
  }

}
