package seasonal.parade.halloween;

import net.minecraft.src.Item;

public class LiquidHelper {
	public static Item[] candyContainers = new Item[]{
		Halloween.rawCandyBucket,
		Halloween.rawCandyCell,
		Halloween.rawCandyCan,
		Halloween.rawCandyWaxCapsule,
		Halloween.rawCandyRefractoryCapsule
	};
	public static Item[] milkContainers = new Item[]{
		Item.bucketMilk,
		Halloween.milkCell,
		Halloween.milkCan,
		Halloween.milkWaxCapsule,
		Halloween.milkRefractoryCapsule
	};
	public static Item[] emptyContainers = new Item[]{
			Item.bucketEmpty,
			Halloween.cell,
			Halloween.canEmpty,
			Halloween.wax_capsule,
			Halloween.refractory_capsule
	};
	
	public static <T> boolean contains( final T[] array, final T v ) {
        for ( final T e : array )
            if ( e == v || v != null && v.equals( e ) )
                return true;

        return false;
    }
}
