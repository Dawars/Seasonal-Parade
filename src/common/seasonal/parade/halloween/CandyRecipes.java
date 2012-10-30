package seasonal.parade.halloween;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class CandyRecipes
{
    private static final CandyRecipes RecipeBase = new CandyRecipes();

    /** The list of Recipe results. */
    private Map RecipeList = new HashMap();
    private Map experienceList = new HashMap();
    private Map metaRecipeList = new HashMap();
    private HashMap<List<Integer>, Float> metaExperience = new HashMap<List<Integer>, Float>();

    /**
     * Used to call methods addRecipe and getRecipeResult.
     */
    public static final CandyRecipes Recipe()
    {
        return RecipeBase;
    }
    private CandyRecipes()
    {
        this.addRecipe(Item.dyePowder.shiftedIndex, 1, new ItemStack(Halloween.candyRed), 2F);
        this.addRecipe(Item.dyePowder.shiftedIndex, 10, new ItemStack(Halloween.candyGreen), 2F);
        this.addRecipe(Item.dyePowder.shiftedIndex, 2, new ItemStack(Halloween.candyGreen), 2F);
        this.addRecipe(Item.dyePowder.shiftedIndex, 12, new ItemStack(Halloween.candyBlue), 2F);
        this.addRecipe(Item.dyePowder.shiftedIndex, 4, new ItemStack(Halloween.candyBlue), 2F);
        this.addRecipe(Item.dyePowder.shiftedIndex, 11, new ItemStack(Halloween.candyYellow), 2F);
        this.addRecipe(Item.dyePowder.shiftedIndex, 14, new ItemStack(Halloween.candyOrange), 2F);
        this.addRecipe(Block.pumpkin.blockID, new ItemStack(Halloween.candyPumpkin), 2F);
        this.addRecipe(Item.dyePowder.shiftedIndex, 13, new ItemStack(Halloween.candyPink), 2F);
        
    }
    /**
     * Adds a Recipe recipe.
     */
    public void addRecipe(int par1, ItemStack par2ItemStack, float par3)
    {
        this.RecipeList.put(Integer.valueOf(par1), par2ItemStack);
        this.experienceList.put(Integer.valueOf(par2ItemStack.itemID), Float.valueOf(par3));
    }
    /**
     * Returns the Recipe result of an item.
     * Deprecated in favor of a metadata sensitive version
     */
    @Deprecated
    public ItemStack getRecipeResult(int par1)
    {
        return (ItemStack)this.RecipeList.get(Integer.valueOf(par1));
    }

    public Map getRecipeList()
    {
        return this.RecipeList;
    }

    public float getExperience(int par1)
    {
        return this.experienceList.containsKey(Integer.valueOf(par1)) ? ((Float)this.experienceList.get(Integer.valueOf(par1))).floatValue() : 0.0F;
    }

    /**
     * Grabs the amount of base experience for this item to give when pulled from the furnace slot.
     */
    public float getExperience(ItemStack item)
    {
        if (item == null || item.getItem() == null)
        {
            return 0;
        }
        float ret = item.getItem().getSmeltingExperience(item);
        if (ret < 0 && metaExperience.containsKey(Arrays.asList(item.itemID, item.getItemDamage())))
        {
            ret = metaExperience.get(Arrays.asList(item.itemID, item.getItemDamage()));
        }
        if (ret < 0 && experienceList.containsKey(item.itemID))
        {
            ret = ((Float)experienceList.get(item.itemID)).floatValue();
        }
        return (ret < 0 ? 0 : ret);
    }
    /**
     * Add a metadata-sensitive furnace recipe
     * @param itemID The Item ID
     * @param metadata The Item Metadata
     * @param itemstack The ItemStack for the result
     * @param experience...
     */
    public void addRecipe(int itemID, int metadata, ItemStack itemstack, float par3)
    {
        metaRecipeList.put(Arrays.asList(itemID, metadata), itemstack);
        this.experienceList.put(Integer.valueOf(itemstack.itemID), Float.valueOf(par3));

    }
    
    /**
     * Used to get the resulting ItemStack form a source ItemStack
     * @param item The Source ItemStack
     * @return The result ItemStack
     */
    public ItemStack getRecipeResult(ItemStack item) 
    {
        if (item == null)
        {
            return null;
        }
        ItemStack ret = (ItemStack)metaRecipeList.get(Arrays.asList(item.itemID, item.getItemDamage()));
        if (ret != null) 
        {
            return ret;
        }
        return (ItemStack)RecipeList.get(Integer.valueOf(item.itemID));
    }
}
