package com.pico52.dominion.object;

import java.util.ArrayList;

import com.pico52.dominion.Dominion;
import com.pico52.dominion.DominionSettings;

/** 
 * <b>TradeManager</b><br>
 * <br>
 * &nbsp;&nbsp;public class TradeManager
 * <br>
 * <br>
 * Controller for trade.
 */
public class TradeManager extends DominionObjectManager{

	/** 
	 * <b>TradeManager</b><br>
	 * <br>
	 * &nbsp;&nbsp;public TradeManager({@link Dominion} plugin)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link TradeManager} class.
	 * @param instance - The {@link Dominion} plugin this manager will be running on.
	 */
	public TradeManager(Dominion plugin) {
		super(plugin);
	}
	
	public boolean createTrade(int settlement1, int settlement2){
		double[] tradeValues = calculateTradeValues(settlement1, settlement2);
		return createTrade(settlement1, settlement2, tradeValues[0], tradeValues[1]);
	}
	
	public boolean createTrade(int settlement1, int settlement2, double income1, double income2){
		return db.createTrade(settlement1, settlement2, income1, income2);
	}
	
	public boolean cancelTrade(int tradeId){
		return db.remove("trade", tradeId);
	}
	
	public double[] calculateTradeValues(int settlement1, int settlement2){
		double[] tradeValues = {DominionSettings.baseTradeValue, DominionSettings.baseTradeValue};
		// - We will have to create some sort of basis for trade here.
		return tradeValues;
	}
	
	public int[] getIdsInvolvingSettlement(int settlementId) {
		int[] settlement1 = db.getSpecificIds("trade", settlementId, "settlement1_id");
		int[] settlement2 = db.getSpecificIds("trade", settlementId, "settlement2_id");
		int[] involved = new int[settlement1.length + settlement2.length];
		int i = 0;
		try{
			for(int id: settlement1){
				involved[i] = id;
				i++;
			}
			for(int id: settlement2){
				involved[i] = id;
				i++;
			}
		} catch (ArrayIndexOutOfBoundsException ex){
			ex.printStackTrace();
		}
		return involved;
	}
	
	public int[] getIdsBetweenSettlements(int settlement1Id, int settlement2Id){
		int[] settlement1 = getIdsInvolvingSettlement(settlement1Id);
		int[] settlement2 = getIdsInvolvingSettlement(settlement2Id);
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(int a: settlement1){
			for(int b: settlement2){
				if(a == b)
					list.add(a);
			}
		}
		int[] allIds = new int[list.size()];
		int i = 0;
	    for (Integer num : list){
	        allIds[i++] = num;
	    }
		return allIds;
	}
	
	public int getNumberOfActiveTradesBetween(int settlement1Id, int settlement2Id){
		return getIdsBetweenSettlements(settlement1Id, settlement2Id).length;
	}

	public double getSettlementIncome(int tradeId, int settlementId) {
		if(getSettlement1(tradeId) == settlementId)
			return getIncome1(tradeId);
		if(getSettlement2(tradeId) == settlementId)
			return getIncome2(tradeId);
		return 0;
	}
	
	// --- ACCESSORS --- //
	public int getSettlement1(int tradeId){
		return db.getSingleColumnInt("trade", "settlement1_id", tradeId, "trade_id");
	}
	
	public int getSettlement2(int tradeId){
		return db.getSingleColumnInt("trade", "settlement2_id", tradeId, "trade_id");
	}
	
	public double getIncome1(int tradeId){
		return db.getSingleColumnDouble("trade", "income1", tradeId, "trade_id");
	}
	
	public double getIncome2(int tradeId){
		return db.getSingleColumnDouble("trade", "income2", tradeId, "trade_id");
	}
}
