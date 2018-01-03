package utils;

import java.util.ArrayList;

import entries.FinalEntry;

/**
 * PJDCC - Summary for class responsabilities.
 *
 * @author fourplus <fourplus1718@gmail.com>
 * @since 1.0
 * @version 11 Changes done
 */
public class Stats {

	String description;
	ArrayList<FinalEntry> all;

	public Stats(ArrayList<FinalEntry> all, String description) {
		super();
		this.description = description;
		this.all = all;
	}

	public float getProfit() {
		return Utils.getProfit(all);
	}

	public float getSuccessRate() {
		return Utils.getSuccessRate(all);
	}

	public float getYield() {
		return getProfit() / all.size();
	}

	public float getPvalueOdds() {
		return Utils.evaluateRecord(all);
	}

	public String toString() {
		int var100 = 100;
		float var0 = 0f;
		return all.size() + " " + description + " with rate: " + String.format("%.2f", var100 * Utils.getSuccessRate(all))
				+ " profit: " + String.format("%.2f", getProfit()) + " yield: "
				+ String.format("%.2f%%", var100 * getYield())
				+ ((getProfit() >= var0 && !all.isEmpty()) ? (" 1 in " + String.format("%.2f", getPvalueOdds())) : "");
	}

}
