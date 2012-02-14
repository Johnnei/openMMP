package server.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import monopoly.Town;

import server.MMP;

public class Packet09BuyHouse extends Packet
{
	private final int BUY = 1;
	private final int SELL = 0;
	
	byte index;
	byte data;
	
	public Packet09BuyHouse()
	{
		
	}

	@Override
	public void readData(DataInputStream d) throws IOException
	{
		index = d.readByte();
		data = d.readByte();
	}

	@Override
	public void writeData(DataOutputStream d) throws IOException
	{
	}

	@Override
	public void handle()
	{
		MMP.Log("Buying/Selling House at index " + index + "...");
		if(index == -1)
			MMP.Log("Invalid Street Supplied");
		if(MMP.getServer().Monopoly().getTownManager().isInvalid(index)) {
			MMP.Log("Invalid House Spot");
			return;
		}
		if(!MMP.getServer().Monopoly().getTownManager().hasCompleteStreet(MMP.getServer().Monopoly().getCurrentPlayer().getId(), index)) {
			MMP.Log("Incomplete Street");
			return;
		}
		
		Town t = MMP.getServer().Monopoly().getTownManager().get(index);
		int housePrice = MMP.getServer().Monopoly().getTownManager().getHousePrice(index);
		
		if(data == BUY) {
			MMP.Log("House Data: Buy");
			if(MMP.getServer().Monopoly().getPlayer(t.getOwnerId()).getMoney() >= housePrice) { 
				MMP.getServer().Monopoly().getTownManager().setHouseCount(index, t.getHouseCount() + 1);
				MMP.getServer().Monopoly().sendPacket(new Packet14ChangeMoney(t.getOwnerId(), -MMP.getServer().Monopoly().getTownManager().getHousePrice(index)));
				MMP.getServer().Monopoly().sendPacket(new Packet10HouseCount((byte)index, (byte)(t.getHouseCount() + 1)));
			}
		} else if(data == SELL) {
			MMP.Log("House Data: Sell");
			MMP.getServer().Monopoly().getTownManager().setHouseCount(index, t.getHouseCount() - 1);
			MMP.getServer().Monopoly().sendPacket(new Packet14ChangeMoney(t.getOwnerId(), (int)(housePrice * 0.5)));
			MMP.getServer().Monopoly().sendPacket(new Packet10HouseCount((byte)index, (byte)(t.getHouseCount() - 1)));
		} //Else invalid
	}

}
