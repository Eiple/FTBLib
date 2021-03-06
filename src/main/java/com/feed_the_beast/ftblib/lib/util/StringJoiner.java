package com.feed_the_beast.ftblib.lib.util;

import net.minecraft.util.math.MathHelper;

import java.util.Collection;

/**
 * @author LatvianModder
 */
public abstract class StringJoiner
{
	public static StringJoiner with(String string)
	{
		if (string.isEmpty())
		{
			return WithString.WITH_NOTHING;
		}
		else if (string.length() == 1)
		{
			return with(string.charAt(0));
		}
		else if (string.equals(WithString.WITH_COMMA_AND_SPACE.s))
		{
			return WithString.WITH_NOTHING;
		}

		return new WithString(string);
	}

	public static StringJoiner with(char character)
	{
		switch (character)
		{
			case ',':
				return WithChar.WITH_COMMA;
			case ' ':
				return WithChar.WITH_SPACE;
			default:
				return new WithChar(character);
		}
	}

	public static StringJoiner properties()
	{
		return new PropertiesJoiner();
	}

	private static class WithString extends StringJoiner
	{
		private static final WithString WITH_NOTHING = new WithString("");
		private static final WithString WITH_COMMA_AND_SPACE = new WithString(", ");

		private final String s;

		private WithString(String _s)
		{
			s = _s;
		}

		@Override
		protected void append(StringBuilder builder)
		{
			builder.append(s);
		}
	}

	private static class WithChar extends StringJoiner
	{
		private static final WithChar WITH_COMMA = new WithChar(',');
		private static final WithChar WITH_SPACE = new WithChar(' ');

		private final char c;

		private WithChar(char _c)
		{
			c = _c;
		}

		@Override
		protected void append(StringBuilder builder)
		{
			builder.append(c);
		}
	}

	private static class PropertiesJoiner extends StringJoiner
	{
		private int index = 0;

		@Override
		protected void append(StringBuilder builder)
		{
			if (index % 2 == 0)
			{
				builder.append(", ");
			}
			else
			{
				builder.append('=');
			}

			index++;
		}
	}

	protected abstract void append(StringBuilder builder);

	public String joinObjects(Object... objects)
	{
		StringBuilder builder = new StringBuilder();
		boolean first = true;

		for (Object object : objects)
		{
			if (first)
			{
				first = false;
			}
			else
			{
				append(builder);
			}

			builder.append(object);
		}

		return builder.toString();
	}

	public String join(Iterable objects)
	{
		if (objects instanceof Collection && ((Collection) objects).isEmpty())
		{
			return "";
		}

		StringBuilder builder = new StringBuilder();
		boolean first = true;

		for (Object object : objects)
		{
			if (first)
			{
				first = false;
			}
			else
			{
				append(builder);
			}

			builder.append(object);
		}

		return builder.toString();
	}

	public String joinDoubles(double... values)
	{
		StringBuilder builder = new StringBuilder();
		boolean first = true;

		for (double d : values)
		{
			if (first)
			{
				first = false;
			}
			else
			{
				append(builder);
			}

			builder.append(StringUtils.formatDouble(d));
		}

		return builder.toString();
	}

	public String joinFloorDoubles(double... values)
	{
		StringBuilder builder = new StringBuilder();
		boolean first = true;

		for (double d : values)
		{
			if (first)
			{
				first = false;
			}
			else
			{
				append(builder);
			}

			builder.append(MathHelper.lfloor(d));
		}

		return builder.toString();
	}
}