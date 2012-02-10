package monopoly;

import server.MMP;
import server.packet.Packet14ChangeMoney;
import server.packet.Packet15SetIndex;
import server.packet.Packet17CardMove;

public class Card
{
	String text; //Text on card
	int moneyChange = 0; //Money gain
	byte gotoIndex = -1; //Forced goto index
	byte moveIndex = 0; //Forced move amount
	boolean gotoIndexNormal; //If the movement should apply normal effects
	
	public Card(String txt, int moneyChange)
	{
		this.text = txt;
		this.moneyChange = moneyChange;
	}
	
	public Card(String text, int index, boolean move, boolean normal)
	{
		this(text, 0);
		gotoIndexNormal = move;
		if(move)
			moveIndex = (byte)index;
		else
			gotoIndex = (byte)index;
	}
	
	public Card(String text, int index, boolean move)
	{
		this(text, index, move, false);
	}
	
	public String[] getStateStrings()
	{
		if(text.contains("\n"))
			return text.split("\n");
		return new String[] { text };
	}
	
	public void Handle()
	{
		if(moneyChange != 0)
		{
			MMP.getServer().Monopoly().getCurrentPlayer().getMoney(moneyChange);
			MMP.getServer().Monopoly().sendPacket(new Packet14ChangeMoney(MMP.getServer().Monopoly().getCurrentPlayer().getId(), moneyChange));
		}
		if(gotoIndex > -1)
		{
			if(gotoIndexNormal)
			{
				MMP.getServer().Monopoly().sendPacket(new Packet17CardMove(moveIndex));
			}
			else
			{
				MMP.getServer().Monopoly().sendPacket(new Packet15SetIndex(MMP.getServer().Monopoly().getCurrentPlayer().getId(), gotoIndex));
			}
		}
		if(moveIndex != 0)
		{
			if(gotoIndexNormal)
			{
				MMP.getServer().Monopoly().sendPacket(new Packet17CardMove(moveIndex));
			}
			else
			{
				byte index = MMP.getServer().Monopoly().getCurrentPlayer().getIndex();
				index += moveIndex;
				MMP.getServer().Monopoly().sendPacket(new Packet15SetIndex(MMP.getServer().Monopoly().getCurrentPlayer().getId(), index));
			}
		}
			
	}
}
