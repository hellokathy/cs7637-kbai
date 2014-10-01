package project2;

public class AntecedentRotationalSymmetryOrder implements Antecedent
{
	private final int order;

	public AntecedentRotationalSymmetryOrder(int order)
	{
		this.order = order;
	}

	@Override
	public boolean applies(Context context)
	{
		final Object rotationalSymmetryOrder = context.get("x:rotational-symmetry-order");

		if (rotationalSymmetryOrder == null || (!(rotationalSymmetryOrder instanceof Integer)))
		{
			return false;
		}

		return ((Integer)rotationalSymmetryOrder).equals(order);
	}
}
