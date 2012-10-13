package seasonal.parade.halloween;

import net.minecraft.src.NBTTagCompound;
import seasonal.parade.halloween.network.EntityNetData;
import buildcraft.api.liquids.ILiquidTank;
import buildcraft.api.liquids.LiquidStack;

public class TankSlot
  implements ILiquidTank
{

  @EntityNetData(staticSize = 0)
  public int capacity = 0;

  @EntityNetData(staticSize = 0)
  public int liquidId = 0;

  @EntityNetData(staticSize = 0)
  public int liquidMeta = 0;

  @EntityNetData(staticSize = 0)
  public int quantity = 0;

  public TankSlot(int capacity)
  {
    this.capacity = capacity;
  }

  public void writeToNBT(NBTTagCompound nbttagcompound) {
    nbttagcompound.setLong("liquidId", this.liquidId);
    nbttagcompound.setLong("liquidMeta", this.liquidMeta);
    nbttagcompound.setLong("quantity", this.quantity);
  }

  public void readFromNBT(NBTTagCompound nbttagcompound) {
    this.liquidId = nbttagcompound.getShort("liquidId");
    this.liquidMeta = nbttagcompound.getShort("liquidMeta");

    if (this.liquidId != 0)
      this.quantity = nbttagcompound.getShort("quantity");
    else
      this.quantity = 0;
  }

  public LiquidStack asLiquidStack() {
    return new LiquidStack(this.liquidId, this.quantity, this.liquidMeta);
  }

  public LiquidStack getLiquid()
  {
    return asLiquidStack();
  }

  public void setLiquid(LiquidStack liquid)
  {
    this.liquidId = liquid.itemID;
    this.quantity = liquid.amount;
    this.liquidMeta = liquid.itemMeta;
  }

  public int getCapacity()
  {
    return this.capacity;
  }

  public void setCapacity(int capacity)
  {
    this.capacity = capacity;
  }

  public int fill(LiquidStack resource, boolean doFill)
  {
    int filled = 0;

    if ((this.quantity != 0) && ((this.liquidId != resource.itemID) || (this.liquidMeta != resource.itemMeta))) {
      filled = 0;
    }
    else if (this.quantity + resource.amount <= this.capacity)
    {
      if (doFill) {
        this.quantity += resource.amount;
      }
      this.liquidId = resource.itemID;
      this.liquidMeta = resource.itemMeta;
      filled = resource.amount;
    }
    else
    {
      int used = this.capacity - this.quantity;

      if (doFill) {
        this.quantity = this.capacity;
      }
      this.liquidId = resource.itemID;
      this.liquidMeta = resource.itemMeta;
      filled = used;
    }

    return filled;
  }

  public LiquidStack drain(int maxDrain, boolean doDrain)
  {
    int used = maxDrain;
    if (this.quantity < maxDrain) {
      used = this.quantity;
    }
    if (doDrain) {
      this.quantity -= used;

      if ((this.quantity <= 0) && (this.liquidId > 0)) {
        this.liquidId = 0;
        this.liquidMeta = 0;
      }
    }

    return new LiquidStack(this.liquidId, used, this.liquidMeta);
  }
}