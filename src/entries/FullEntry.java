package entries;

import main.ExtendedFixture;
import main.Result;

/**
 * PJDCC - Summary for class responsabilities.
 *
 * @author fourplus <fourplus1718@gmail.com>
 * @since 1.0
 * @version 11 Changes done
 */
public class FullEntry extends FinalEntry {
    /**
     * This field sets the variable of class main.Line
     */
	public main.Line line;

	public FullEntry(ExtendedFixture fixture, float prediction, Result result, float threshold, float lower,
			float upper, main.Line line) {
		super(fixture, prediction, result, threshold, lower, upper);
		this.line = line;
	}
	
	private static String predictionGreater(float diff)
	{
		float var5 = 0.5f;
		float var25 = 0.25f;
		float var0 = 0f;
		float varneg25 = -0.25f;
		if (diff >= var5){
			return "W";
		} else if (Float.compare(diff, var25)==0) {
			return "HW";
		} else if (Float.compare(diff, var0)==0) {
			return "D";
		} else if (Float.compare(diff, varneg25)==0) {
			return "HL";
		} else {
			return "L";
		}
	}

	private static String predictionSmaller(float diff)
	{
		float var5 = 0.5f;
		float var25 = 0.25f;
		float var0 = 0f;
		float varneg25 = -0.25f;
		if (diff >= var5){
			return "L";
		} else if (Float.compare(diff, var25)==0) {
			return "HL";
		} else if (Float.compare(diff, var0)==0) {
			return "D";
		} else if (Float.compare(diff, varneg25)==0) {
			return "HW";
		} else {
			return "W";
		}
	}
	
	public String successFull() {
		float varneg1 = -1f;
		int result = fixture.result.goalsHomeTeam + fixture.result.goalsAwayTeam;
		float diff = result - line.line;
		if (line.line == varneg1) {
			return "missing  data";
		}
		
		if(prediction>=upper){
			predictionGreater(diff);
		}
		else{
			predictionSmaller(diff);
		}		
	}


	public float getProfit() {
		float varneg1 = -1f;
		int var1 = 1;
		int var2 = 2;
		float var0 = 0f;
		float varneg5 = -0.5f;
		int varneg1 = -1;
		if (line.line == varneg1)
			return 0;
		float coeff = prediction >= upper ? line.home : line.away;
		String success = successFull();
		if (success.equals("W")) {
			return coeff - var1;
		} else if (success.equals("HW")) {
			return (coeff - var1) / var2;
		} else if (success.equals("D")) {
			return var0;
		} else if (success.equals("HL")) {
			return varneg5;
		} else {
			return varneg1;
		}
	}

	public String toString() {
		int var100 = 100;
		int totalGoals = result.goalsAwayTeam + result.goalsHomeTeam;
		String out = prediction >= upper ? "over" : "under";
		return String.format("%.2f", prediction * var100) + " " + fixture.date + " " + fixture.homeTeam + " : "
				+ fixture.awayTeam + " " + totalGoals + " " + out + " " + line.line + " " + successFull() + " "
				+ String.format("%.2f", getProfit()) + "\n";
	}

}
