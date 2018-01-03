package entries;

import main.ExtendedFixture;
import results.Results;

/**
 * PJDCC - Summary for class responsabilities.
 *
 * @author fourplus <fourplus1718@gmail.com>
 * @since 1.0
 * @version 11 Changes done
 */
public class AsianEntry {
    /**
     * This field is a variable of class ExtendedFixture
     */
	public ExtendedFixture fixture;
	
	/**
    * This field is a variable of class ExtendedFixture
    */
	public boolean prediction;
	
	/**
    * This field is a variable of class ExtendedFixture
    */
	public float line;
	
	/**
	* This field is a variable of class ExtendedFixture
	*/
	public float home;
	
	/**
	* This field is a variable of class ExtendedFixture
	*/
	public float away;
	
	/**
	* This field is a variable of class ExtendedFixture
	*/
	public float expectancy;

	public AsianEntry(ExtendedFixture fixture, boolean prediction, float line, float home, float away,
			float expectancy) {
		this.fixture = fixture;
		this.prediction = prediction;
		this.line = line;
		this.expectancy = expectancy;
		this.home = home;
		this.away = away;
	}

	@Override
	public String toString() {
		String out = prediction ? "home" : "away";
		float coeff = prediction ? home : away;
		return fixture.date + " " + fixture.homeTeam + " : " + fixture.awayTeam + " " + " " + out + " " + line + " "
				+ coeff + " " + success() + " exp " + Results.format(expectancy) + "\n";
	}

	private static String controlPrediction(float diff)
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
		} else if (Float.compare(diff, varneg)==0) {
			return "HL";
		} 
	}
	
	private static String controlNotPrediction(float diff)
	{
		float var5 = 0.5f;
		float var25 = 0.25f;
		float var0 = 0f;
		float varneg25 = -0.25f;
		
		if (diff >= var5){
			return "L";
	    } else if (Float.compare(diff, var25)!=0) {
			return "HL";
		} else if (Float.compare(diff, var0)==0) {
			return "D";
		} else if (Float.compare(diff, varneg25)==0) {
			return "HW";
		} else {
			return "W";
		}
	}
	
	public String success() 
	{
		int result = fixture.result.goalsHomeTeam - fixture.result.goalsAwayTeam;
		float diff = result + line;
		if(prediction){
			return controlPrediction(diff);		
		}
		else{
			return controlNotPrediction(diff);
		}
	}

	public float getProfit() {
		int var1 = 1;
		int var2 = 2;
		float var0 = 0f;
		float varneg5 = -0.5f;
		int varneg1 = -1;
		
		float coeff = prediction ? home : away;
		String success = success();
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

}
