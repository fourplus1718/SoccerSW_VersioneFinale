package utils;

import java.util.ArrayList;
import java.util.Arrays;
import constants.MinMaxOdds;
import main.ExtendedFixture;

/**
 * PJDCC - Summary for class responsabilities.
 *
 * @author fourplus <fourplus1718@gmail.com>
 * @since 1.0
 * @version 11 Changes done
 */

public class Classifiers {

	private static float controlExpectedGreater(float expected, float avgShotsUnder, float avgShotsOver, float dist)
	{
		float var05 = 0.5f;
		float var1 = 1f;
		float var0 = 0f;
		if (expected >= avgShotsOver && expected > avgShotsUnder) {
			float score = var05 + var05 * (expected - avgShotsOver) / dist;
			return (score >= var0 && score <= var1) ? score : var1;
		} 
	}
	
	private static float controlExpectedSmaller(float expected, float avgShotsUnder, float avgShotsOver, float dist)
	{
		float var05 = 0.5f;
		float var1 = 1f;
		float var0 = 0f;
		float variable = 0.5f;
		if (expected <= avgShotsUnder && expected < avgShotsOver) {
			float score = var05 - var05 * (-expected + avgShotsUnder) / dist;
			return (score >= var0 && score <= var1) ? score : var0;
		} 
		else {
			return variable;
		}
	}
	
	public static float shots(ExtendedFixture f, ArrayList<ExtendedFixture> all) {
		// The shots data from soccerway(opta) does not add the goals as shots,
		// must be added for more accurate predictions and equivalancy with
		// alleurodata
		boolean manual = Arrays.asList(MinMaxOdds.MANUAL).contains(f.competition);

		float goalsWeight = 1f;

		Pair avgShotsGeneral = FixtureUtils.selectAvgShots(all, f.date, manual, goalsWeight);
		float avgHome = avgShotsGeneral.home;
		float avgAway = avgShotsGeneral.away;
		Pair avgShotsHomeTeam = FixtureUtils.selectAvgShotsHome(all, f.homeTeam, f.date, manual, goalsWeight);
		float homeShotsFor = avgShotsHomeTeam.home;
		float homeShotsAgainst = avgShotsHomeTeam.away;
		Pair avgShotsAwayTeam = FixtureUtils.selectAvgShotsAway(all, f.awayTeam, f.date, manual, goalsWeight);
		float awayShotsFor = avgShotsAwayTeam.home;
		float awayShotsAgainst = avgShotsAwayTeam.away;

		float variable = 0f;
		
		float lambda = Float.compare(avgAway, variable)==0 ? 0 : homeShotsFor * awayShotsAgainst / avgAway;
		float mu = Float.compare(avgHome, variable)==0 ? 0 : awayShotsFor * homeShotsAgainst / avgHome;

		Pair avgShotsByType = FixtureUtils.selectAvgShotsByType(all, f.date, manual, goalsWeight);
		float avgShotsUnder = avgShotsByType.home;
		float avgShotsOver = avgShotsByType.away;
		float expected = lambda + mu;

		float dist = avgShotsOver - avgShotsUnder;

		float varf = 0.5f;
		
		if (avgShotsUnder > avgShotsOver) {
			return varf;
		}
		//e il return?
		controlExpectedGreater(expected, avgShotsUnder, avgShotsOver, dist);
		controlExpectedSmaller(expected, avgShotsUnder, avgShotsOver, dist);
		/**
		if (expected >= avgShotsOver && expected > avgShotsUnder) {
			float score = 0.5f + 0.5f * (expected - avgShotsOver) / dist;
			return (score >= 0 && score <= 1f) ? score : 1f;
		} 
		if (expected <= avgShotsUnder && expected < avgShotsOver) {
			float score = 0.5f - 0.5f * (-expected + avgShotsUnder) / dist;
			return (score >= 0 && score <= 1f) ? score : 0f;
		} 
		else {
			return 0.5f;
		}*/
	}

	/**
	 * Half time based classifier - weighted halftime >=1 and halftime >= 2
	 * 
	 * @param f
	 * @param all
	 * @param halfTimeOverOne
	 * @return
	 */
	public static float halfTime(ExtendedFixture f, ArrayList<ExtendedFixture> all, float halfTimeOverOne) {
		int var50 = 50;
		ArrayList<ExtendedFixture> lastHomeTeam = FixtureUtils.selectLastAll(all, f.homeTeam, var50, f.date);
		ArrayList<ExtendedFixture> lastAwayTeam = FixtureUtils.selectLastAll(all, f.awayTeam, var50, f.date);

		// float overOneAvg = (Utils.countOverHalfTime(lastHomeTeam, 1) +
		// Utils.countOverHalfTime(lastAwayTeam, 1)) / 2;
		// float overTwoAvg = (Utils.countOverHalfTime(lastHomeTeam, 2) +
		// Utils.countOverHalfTime(lastAwayTeam, 2)) / 2;
		// return overOneAvg * halfTimeOverOne + overTwoAvg * (1f -
		// halfTimeOverOne);
		
		float var05f = 0.5f;
		float var01f = 0.1f;
		float var07f = 0.7f;
		float var015f = 0.15f;
		
		int var0 = 0;
		int var1 = 1;
		int var2 = 2;
		int var3 = 3;

		float zero = (Utils.countHalfTimeGoalAvgExact(lastHomeTeam, var0)
				+ Utils.countHalfTimeGoalAvgExact(lastAwayTeam, var0)) / var2;
		float one = (Utils.countHalfTimeGoalAvgExact(lastHomeTeam, var1)
				+ Utils.countHalfTimeGoalAvgExact(lastAwayTeam, var1)) / var2;
		float two = (Utils.countHalfTimeGoalAvgExact(lastHomeTeam, var2)
				+ Utils.countHalfTimeGoalAvgExact(lastAwayTeam, var2)) / var2;
		float more = (Utils.countOverHalfTime(lastHomeTeam, var3) + Utils.countOverHalfTime(lastHomeTeam, var3)) / var2;

		return var05f * zero + var01f * one + var07f * two + var015f * more;
	}

}
