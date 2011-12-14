package monopoly;

import java.util.List;
import java.util.ArrayList;

public class TownManager
{

	public List<Town> towns;

	public TownManager()
	{
		towns = new ArrayList<Town>();
	}

	public Town get(int i)
	{
		return towns.get(i);
	}
	
	public int size()
	{
		return towns.size();
	}

	private boolean streetIsFull(Street s)
	{
		int count = 0;
		for (int i = 0; i < towns.size(); i++)
		{
			if (((Town) towns.get(i)).street == s)
			{
				if (++count == 4)
					return true;
			}
		}
		return false;
	}
	
	public boolean isInvalid(int index)
	{
		return 
		(
			index == 0 || index == 10 || index == 20 || index == 30 ||//Corners
			index == 2 || index == 17 || index == 33 || //General Funds
			index == 7 || index == 22 || index == 36 || //Random Funds
			index == 4 || index == 38//Taxes
		);
	}
	
	public void setOwner(byte pId, byte sIndex)
	{
		Town t = towns.get(sIndex);
		t.setOwner(Game.Monopoly().getPlayer(pId));
		towns.set(sIndex, t);
	}

	public boolean isBuyable(int index)
	{
		if (towns.get(index).getPlayer() == null)
		{
			SpecialTown t = towns.get(index).getType();
			if(t == SpecialTown.Normaal || t == SpecialTown.Voorzieningen)
				return true;
			return false;
		}
		else
			return false;
	}

	public int getCost(int index)
	{
		Town t = towns.get(index);
		if (t.getType() == SpecialTown.Normaal)
			return t.getCost();
		else if (t.getType() == SpecialTown.Station)
		{
			int[] cost = new int[] { 1250, 5000, 10000, 20000 };
			int stationCount = 0;
			byte ownerId = t.getOwnerId();
			if(towns.get(5).isSameOwner(ownerId))
				stationCount++;
			if(towns.get(15).isSameOwner(ownerId))
				stationCount++;
			if(towns.get(25).isSameOwner(ownerId))
				stationCount++;
			if(towns.get(35).isSameOwner(ownerId))
				stationCount++;
			return cost[stationCount - 1];
		}
		else
		// SpecialTown.Voorziening
		{
			int voorzieningCount = 1;
			if(towns.get(12).isSameOwner(towns.get(28).getOwnerId()))
				voorzieningCount = 2;
			return Game.Monopoly().diceEyesCount() * 500 * voorzieningCount;
		}
	}

	public void Add(String townName, Street streetName, int Price, int Cost)
	{
		Add(townName, streetName, Price, Cost, SpecialTown.Normaal);
	}

	public void Add(String townName, Street streetName, int Price, int Cost, SpecialTown Special)
	{
		if (streetIsFull(streetName))
		{
			Game.Log("Can not Add \"" + townName + "\" in " + streetName.toString() + ": Street is full");
			return;
		}
		towns.add(new Town(townName, streetName, Price, Cost, Special));
	}

}
