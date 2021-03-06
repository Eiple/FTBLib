package com.feed_the_beast.ftblib.lib.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Objects;

/**
 * @author LatvianModder
 */
public final class ItemEntry
{
	public static final ItemEntry EMPTY = new ItemEntry(ItemStack.EMPTY);

	public static ItemEntry get(ItemStack stack)
	{
		return stack.isEmpty() ? EMPTY : new ItemEntry(stack);
	}

	public final Item item;
	public final int metadata;
	public final NBTTagCompound nbt;
	private int hashCode;
	private ItemStack stack = null;

	private ItemEntry(ItemStack stack)
	{
		item = stack.getItem();
		metadata = stack.getMetadata();
		NBTTagCompound nbt0 = stack.getTagCompound();
		nbt = (nbt0 == null || nbt0.hasNoTags()) ? null : nbt0;
		hashCode = 0;
	}

	public boolean isEmpty()
	{
		return this == EMPTY;
	}

	public int hashCode()
	{
		if (hashCode == 0)
		{
			hashCode = Objects.hash(item, metadata, nbt);

			if (hashCode == 0)
			{
				hashCode = 1;
			}
		}

		return hashCode;
	}

	public boolean equalsEntry(ItemEntry entry)
	{
		if (entry == this)
		{
			return true;
		}

		return item == entry.item && metadata == entry.metadata && Objects.equals(nbt, entry.nbt);
	}

	public boolean equals(Object o)
	{
		return o == this || o != null && o.getClass() == ItemEntry.class && equalsEntry((ItemEntry) o);
	}

	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		toString(builder);
		return builder.toString();
	}

	public void toString(StringBuilder builder)
	{
		builder.append(item.getRegistryName());
		builder.append(' ');

		if (metadata == 32767)
		{
			builder.append('*');
		}
		else
		{
			builder.append(metadata);
		}

		if (nbt != null)
		{
			builder.append(' ');
			builder.append(nbt);
		}
	}

	public ItemStack getStack(int count, boolean copy)
	{
		if (count <= 0 || isEmpty())
		{
			return ItemStack.EMPTY;
		}

		if (stack == null)
		{
			stack = new ItemStack(item, 1, metadata);
			stack.setTagCompound(nbt);
		}

		ItemStack stack1;

		if (copy)
		{
			stack1 = stack.copy();
		}
		else
		{
			stack1 = stack;
		}

		stack1.setCount(count);
		return stack1;
	}
}