package tables;

import java.util.ArrayList;
import java.util.Collections;

import main.ExtendedFixture;

/**
 * PJDCC - Summary for class responsabilities.
 *
 * @author fourplus <fourplus1718@gmail.com>
 * @since 1.0
 * @version 11 Changes done
 */
public class Table {
    /**
     * This field sets the variable of class String
     */
	public String league;
    /**
     * This field sets the int variable
     */
	public int year;
    /**
     * This field sets the int variable
     */
	public int matchday;
    /**
     * This field sets the arraylist with positions
     */
	public ArrayList<Position> positions;

	public Table(String league, int year, int matchday) {
		this.league = league;
		this.year = year;
		this.matchday = matchday;
		positions = new ArrayList<>();
	}

	public void sort() {
		Collections.sort(positions, Collections.reverseOrder());
	}

	public int getPosition(String team) {
		int size = positions.size();
		for (int i = 0; i < size; i++) {
			if (positions.get(i).team.equals(team))
				return i + 1;
		}

		return Integer.MIN_VALUE;
	}

	public int getPositionDiff(ExtendedFixture f) {
		return getPosition(f.homeTeam) - getPosition(f.awayTeam);
	}

	public float getMedian() {
		int v1=1,v2=2;
		if (positions.size() % v2 == v1)
			return positions.get((positions.size() - v1) / v2).points;
		else
			return (float) (positions.get(positions.size() / v2).points
					+ +positions.get(positions.size() / v2 - v1).points) / v2;
	}

	public float getFirstQuartile() {
		int v1=1,v2=2,v4=4;
		if ((positions.size() / v2) % v2 == v1)
			return positions.get((positions.size() - v1) / v4).points;
		else
			return (float) (positions.get(positions.size() / v4).points
					+ +positions.get(positions.size() / v4 - v1).points) / v2;
	}

	public float getThirdQuartile() {
		int v1=1,v2=2,v4=4,v3=3;
		if ((positions.size() / v2) % v2 == v1)
			return positions.get((positions.size() / v2 + (positions.size() / v2 + v1) / v2) - v1).points;
		else
			return (float) (positions.get(v3 * positions.size() / v4).points
					+ positions.get(v3 * positions.size() / v4 - v1).points) / v2;
	}

	public ArrayList<String> getTopTeams() {
		ArrayList<String> result = new ArrayList<>();
		for (Position i : positions) {
			if (i.points < getFirstQuartile())
				break;
			result.add(i.team);
		}
		return result;
	}

	public ArrayList<String> getMiddleTeams() {
		ArrayList<String> result = new ArrayList<>();
		for (Position i : positions) {
			if (i.points < getFirstQuartile() && i.points > getThirdQuartile())
				result.add(i.team);
		}
		return result;
	}

	public ArrayList<String> getBottomTeams() {
		ArrayList<String> result = new ArrayList<>();
		for (Position i : positions) {
			if (i.points <= getThirdQuartile())
				result.add(i.team);
		}
		return result;
	}

	public ArrayList<String> getSimilarTeams(String team) {
		if (getTopTeams().contains(team))
			return getTopTeams();
		else if (getMiddleTeams().contains(team))
			return getMiddleTeams();
		else
			return getBottomTeams();
	}

	public String toString() {
		int num = 1;
		String result = "";
		for (Position i : positions) {
			String curr = String.format("%3d ", num++) + String.format("%-25s", i.team) + String.format("%3d", i.played)
					+ String.format("%3d", i.wins) + String.format("%3d", i.draws) + String.format("%3d", i.losses)
					+ String.format("%4d", i.scored) + " : " + String.format("%-4d", i.conceded)
					+ String.format("%+3d", i.diff) + String.format("%4d", i.points) + "\n";
			result += curr;
		}
		return result;
	}
}
