package scraper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;

/**
 * PJDCC - Summary for class responsabilities.
 *
 * @author fourplus <fourplus1718@gmail.com>
 * @since 1.0
 * @version 36 Changes done
 */
class ScraperControls {
	
	static void controlForCollectFull(int fixtureCount, ArrayList<PlayerFixture> result,
			Elements linksM) {
		for (Element linkM : linksM && isScore(linkM.text())) {
			int count = 0;
			int maxTries = 10;
			try {
					controlWhileCollectFull(fixtureCount, result);
				} catch (Exception e) {
					if (++count == maxTries)
						throw e;
				}
		}
	}
	
	static void controlForGetFixture(Elements frames, int shotsHome, int shotsAway) {
		Document stats = Jsoup.connect(BASE + i.attr("src")).timeout(0).get();
		for (Element i : frames && i.attr("src").contains("/charts/statsplus")) {
			shotsHome = Integer.parseInt(
					stats.select("tr:contains(Shots on target)").get(1).select("td.legend.left.value").text());

			shotsAway = Integer.parseInt(
					stats.select("tr:contains(Shots on target)").get(1).select("td.legend.right.value").text());
		}
	}
	
	static void controlFirstForGetFixtureFull(Elements rowsH, ExtendedFixture fix, String Team) {
		int size = rowsH.size();		
		for (int i = 1; i < size; i++) {// without coach
			Element row = rowsH.get(i);
			if (row.text().contains("Coach") || row.text().contains("coach") || row.text().isEmpty())
				continue;

			Elements cols = row.select("td");
			if (cols.size() < 2)
				continue;
			String name = "";
			int v2=2, v1=1, v0=0, v90=90;
			name = Utils.replaceNonAsciiWhitespace(cols.get(cols.size() == v2 ? v0 : v1).text());
			PlayerFixture pf = new PlayerFixture(fix, Team, name, v90, true, false, v0, v0);
			playerFixtures.add(pf);

			}
	}
	
	private static void controlForInFirstIf(ArrayList<PlayerFixture> playerFixtures, String outPlayer, int minute) {
		for (PlayerFixture player : playerFixtures) {
			if (player.name.equals(outPlayer)) {
				player.minutesPlayed = minute;
				break;
			}
		}
	}
	
	private static void controlFirstIfInSecondForGetFixtureFull(String hATeam, ExtendedFixture fix, ArrayList<PlayerFixture> playerFixtures,
			String name) {
		if (name.contains(" for ")) {
			String inPlayer = name.split(" for ")[0].trim();
			String outPlayer = name.split(" for ")[1].split("[0-9]+'")[0].trim();
			
			int minute = 0;
			String cleanMinutes = name.split(" ")[name.split(" ").length - 1].split("'")[0];
			
			if (cleanMinutes.contains("+"))
				minute = Integer.parseInt(cleanMinutes.split("\\+")[0])
						+ Integer.parseInt(cleanMinutes.split("\\+")[1]);
			else
				minute = Integer.parseInt(cleanMinutes);
			int v0=0, v90=90;
			PlayerFixture pf = new PlayerFixture(fix, homeTeam, inPlayer, v90 - minute, false, true, v0, v0);
			playerFixtures.add(pf);

			controlForInFirstIf(playerFixtures, outPlayer, minute);

			if (verbose)
				System.out.println(inPlayer + " for " + outPlayer + " in " + minute);

		} else {
			PlayerFixture pf = new PlayerFixture(fix, hATeam, name, 0, false, false, 0, 0);
			playerFixtures.add(pf);
			if (verbose)
				System.out.println(shirtNumber + " " + name);
		}
	}
	
	static void controlSecondForGetFixtureFull(Elements rowsHA, String hATeam, ExtendedFixture fix,
			ArrayList<PlayerFixture> playerFixtures, String homeTeam) {
		int size = rowsHA.size();
		for (int i = 1; i < size; i++) {
			Element row = rowsHA.get(i);
			Elements cols = row.select("td");
			String shirtNumber = cols.get(0).text();
			int v2=2, v0=0, v1=1;
			String name = Utils.replaceNonAsciiWhitespace(cols.get(cols.size() == v2 ? v0 : v1).text());

			controlFirstIfInSecondForGetFixtureFull(hATeam, fix, playerFixtures, name);
		}
	}
	
	static void controlHomeGoal(ArrayList<PlayerFixture> playerFixtures, String[] splitByMinute) {
		String goalScorer = splitByMinute[0].trim();
		goalScorer = Utils.replaceNonAsciiWhitespace(goalScorer).trim();
		if (goalScorer.contains("(PG)")) {
			goalScorer = goalScorer.replace("(PG)", "").trim();
		}

		if (!goalScorer.contains("(OG)"))
			updatePlayer(goalScorer, playerFixtures, true);

		if (splitByMinute.length > 1) {
			// Extra info like assistedS by, PG or OG
			String extraString = splitByMinute[1];

			if (extraString.contains("assist by")) {
				String assister = splitByMinute[1].split("\\(assist by ")[1].trim();
				assister = Utils.replaceNonAsciiWhitespace(assister);
				assister = assister.substring(0, assister.length() - 1).trim();
				updatePlayer(assister, playerFixtures, false);
			}
		}
	}
	
	/**
	 * Updates the goals or assists (by 1) of the player in the playerFixtures
	 * collection
	 * 
	 * @param name
	 * @param playerFixtures
	 * @param goals
	 *            true if updating goals, false if updating assists
	 */

	private static void updatePlayer(String name, ArrayList<PlayerFixture> playerFixtures, boolean goals) {
		boolean updated = false;
		for (PlayerFixture player : playerFixtures) {
			if (player.name.equals(name)) {
				if (goals)
					player.goals++;
				else
					player.assists++;
				updated = true;
				break;
			}
		}

		if (!updated)
			System.err.println("Problem in updating " + (goals ? "goals " : "assists ") + "for " + name);

	}
	
	private static void control2IfAwayGoal(ArrayList<PlayerFixture> playerFixtures, String[] splitByMinute) {
		String goalScorer = splitByMinute[1].replace("(PG)", "").trim();
		if (goalScorer.contains("+"))
			goalScorer = goalScorer.replace("+", "").replaceAll("\\d", "").trim();

		goalScorer = Utils.replaceNonAsciiWhitespace(goalScorer).trim();

		if (goalScorer.contains("assist by")) {
			String assister = goalScorer.split("\\(assist by ")[1].trim();
			assister = Utils.replaceNonAsciiWhitespace(assister);
			assister = assister.substring(0, assister.length() - 1);
			updatePlayer(assister, playerFixtures, false);
			goalScorer = goalScorer.split("\\(assist by ")[0].trim();
		}
		updatePlayer(goalScorer, playerFixtures, true);
	}
	
	static void controlAwayGoal(ArrayList<PlayerFixture> playerFixtures, String[] splitByMinute) {
	
		if (splitByMinute[1].contains("(PG)")) {
			control2IfAwayGoal(playerFixtures, splitByMinute);
		} 
		if (splitByMinute[1].contains("assist by")) {
			String goalScorer = splitByMinute[1].split("\\(assist by ")[0].trim();
			goalScorer = Utils.replaceNonAsciiWhitespace(goalScorer).trim();
			if (goalScorer.contains("+"))// scored in
				goalScorer = goalScorer.replace("+", "").replaceAll("\\d", "").trim();
	
			updatePlayer(goalScorer, playerFixtures, true);
	
			String assister = splitByMinute[1].split("\\(assist by ")[1].trim();
			assister = Utils.replaceNonAsciiWhitespace(assister);
			assister = assister.substring(0, assister.length() - 1).trim();
			updatePlayer(assister, playerFixtures, false);
		} 
		if (!splitByMinute[1].contains("(OG)")) { // Solo goal no assists, no PG,no OG
			String goalScorer = Utils.replaceNonAsciiWhitespace(splitByMinute[1].trim()).trim();
			if (goalScorer.contains("+"))
				goalScorer = goalScorer.replace("+", "").replaceAll("\\d", "").trim();
			
			updatePlayer(goalScorer, playerFixtures, true);
		}
	}
	
	static void controlForGoals(Elements cols, boolean verbose, ArrayList<PlayerFixture> playerFixtures) {
		for (Element j : cols) {
			if (!isScore(j.text()) && !j.text().isEmpty()) {
				controlVerbose(verbose, j.text());
				String[] splitByMinute = j.text().split("[0-9]+'");
				controlVerbose(verbose, splitByMinute.length);
				if (!splitByMinute[0].isEmpty()) { // Home goal
					ScraperControls.controlHomeGoal(playerFixtures, splitByMinute);
				} else { // Away goal
					ScraperControls.controlAwayGoal(splitByMinute, playerFixtures);
				}
			}
		}
	}
	
	static void controlFirstForOdds(List<WebElement> list, ArrayList<String> links) {
		for (WebElement i : list) {
			String href = i.getAttribute("href");
			if (i.getText().contains("-") && isFixtureLink(href)) {
				links.add(href);
			}
		}
	}
	
	static void controlSecondForOdds(ArrayList<String> links, Set<ExtendedFixture> result) {
		for (String i : links) {
			ExtendedFixture ef = getOddsFixture(driver, i, competition, false, OnlyTodayMatches.FALSE);
			if (ef != null)
				result.add(ef);
		}
	}
	
	static void controlFirstForFullOdds(List<WebElement> el, ArrayList<String> links) {
		for (WebElement i : el) {
			String href = i.getAttribute("href");
			if (i.getText().contains("-") && isFixtureLink(href))
				links.add(href);
		}
	}
	
	static void controlSecondForFullOdds(ArrayList<String> links, Set<FullFixture> result) {
		for (String i : links) {
			FullFixture ef = getFullFixtureTest(driver, i, competition);
			if (ef != null)
				result.add(ef);
		}
	}
	
	static void controlFirstForOddsByPage(List<WebElement> list, ArrayList<String> links) {
		for (WebElement i : list) {
			if (i.getText().contains("-") && isFixtureLink(i.getAttribute("href")))
				links.add(i.getAttribute("href"));
		}
	}
	
	static void controlSecondForOddsByPage(ArrayList<String> links, Set<FullFixture> result) {
		for (WebElement i : list) {
			if (i.getText().contains("-") && isFixtureLink(i.getAttribute("href")))
				links.add(i.getAttribute("href"));
		}
	}

	static void controlVerbose(boolean verbose, String s) {
		if (verbose)
			System.out.println(s);
	}

	private static void controlIfElseResElement(WebElement resElement, String resString, Result fullResult, Result htResult){
		if (resElement != null && (resString.contains("(") && resString.contains(")"))) {
			int v2=2, v3=3, v1=1, v4=4;
			String full = resString.split(" ")[v2];
			String half = resString.split(" ")[v3].substring(v1, v4);
			fullResult = new Result(Integer.parseInt(full.split(":")[0]), Integer.parseInt(full.split(":")[1]));
			htResult = new Result(Integer.parseInt(half.split(":")[0]), Integer.parseInt(half.split(":")[1]));
		} else {
			fullResult = new Result(-1, -1);
			htResult = new Result(-1, -1);
		}
	}
	
	static void controlResElement(WebElement resElement, String resString, Result fullResult, Result htResult, String away, String home){
		if (resElement != null && (resString.contains("penalties") || resString.contains("ET"))) {
			return null;
		}
		controlIfResElement(resElement, resString, away, home);
		controlIfElseResElement(resElement, resString, fullResult, htResult);
	}
	
	static void controlText(String text, float min, WebElement opt, WebElement div){
		if (text.split("\n").length > 3) {
			float diff = Math.abs(Float.parseFloat(text.split("\n")[2].trim())
					- Float.parseFloat(text.split("\n")[3].trim()));
		}
		if (text.split("\n").length > 3 && diff < min) {
			min = diff;
			opt = div;
		}
	}
	
	static void controlRowAsianOverPinnacle(List<WebElement> rowsGoals, float line, float x, float y, ArrayList<Odds> matchOdds, Odds pinnOdds){
		for (WebElement row : rowsGoals) {
			String rowText = row.getText();
			String bookmaker = oddsArray[0].trim();
			controlIfRowAsianOverPinnacle(row, rowText, bookmaker);
			
			line = Float.parseFloat(oddsArray[1].trim());
			x = Float.parseFloat(oddsArray[2].trim());
			y = Float.parseFloat(oddsArray[3].trim());
			
	
			Odds modds = new AsianOdds(bookmaker, new Date(), line, x, y);
			matchOdds.add(modds);
	
			if (bookmaker.equals("Pinnacle"))
				pinnOdds = modds;
		}
	}

	static void controlColumns(List<WebElement> columns){
		if (columns.size() < 4)
			continue;
		String bookmaker = columns.get(0).getText().trim();
		if (Arrays.asList(MinMaxOdds.FAKEBOOKS).contains(bookmaker))
			continue;
	}
	
	static void controlForDiv25(List<WebElement> divsGoals, WebElement div25, WebElement div, float minGoals, WebElement optGoals){
		for (WebElement div : divsGoals) {
			if (div.getText().contains("+2.5")) {
				div25 = div;
			}
			controlSplit(div, minGoals, optGoals);
		}
	}
	
	static void controlRowsGoals(List<WebElement> x, float line, float y, float z){
		for (int r = x.size() - 1; r >= 0; r--) {
			WebElement row = x.get(r);
			if (row.getText().contains("Pinnacle")) {
				String textOdds = row.getText();
				line = Float.parseFloat(textOdds.split("\n")[1].trim());
				y = Float.parseFloat(textOdds.split("\n")[2].trim());
				z = Float.parseFloat(textOdds.split("\n")[3].trim());
				break;
			}
	
			if (row.getText().contains("Average"))
				break;
		}
	}
	
	static void controlOver(float over, ArrayList<main.Line> goalLines, float line, float over, float under, main.Line twoAndHalf, WebElement currentDiv, Actions actions){
		if (over != -1f)
			goalLines.add(new main.Line(line, over, under, "Pinn"));
		float v1=2.5f;
		if (over != -1f && Float.compare(line, v1)==0)
			twoAndHalf = new main.Line(line, over, under, "Pinn");
	
		List<WebElement> closeLink = currentDiv.findElements(By.className("odds-co"));
		if (!closeLink.isEmpty())
			actions.moveToElement(closeLink.get(0)).click().perform();
	
		if (goalLines.size() == 6)
			break;
	}
	
	static void controlDiff(ArrayList<main.Line> goalLines, float minDiff, int indexMinDiff){
		int size = goalLines.size();
		for (int l = 0; l < size; l++) {
			float diff = Math.abs(goalLines.get(l).home - goalLines.get(l).away);
			if (diff < minDiff) {
				minDiff = diff;
				indexMinDiff = l;
			}
		}
	}
	
	static void controlGoalLines(ArrayList<main.Line>  goalLines, int expectedCaseSize, int start, int end, int indexMinDiff, GoalLines GLS){
		if (goalLines.size()  && expectedCaseSize == 5) {
			ArrayList<main.Line> bestLines = new ArrayList<>();
			for (int c = start; c <= end; c++)
				bestLines.add(goalLines.get(c));
			int v0=0, v1=1, v2=2, v3=3, v4=4;
			GLS = new GoalLines(bestLines.get(v0), bestLines.get(v1), bestLines.get(v2), bestLines.get(v3),
					bestLines.get(v4));
			}
		if (goalLines.size()  && expectedCaseSize == 4 && indexMinDiff - 2 < 0) {
			int v1=1, v2=2, v3=3;
			GLS = new GoalLines(goalLines.get(indexMinDiff - v1), goalLines.get(indexMinDiff),
					goalLines.get(indexMinDiff + v1), goalLines.get(indexMinDiff + v2),
					goalLines.get(indexMinDiff + v3));
		}
		if (goalLines.size()  && expectedCaseSize == 4 && indexMinDiff - 2 >= 0 && indexMinDiff + 2 > goalLines.size() - 1) {
			int v1=1, v2=2, v3=3;
			GLS = new GoalLines(goalLines.get(indexMinDiff - v3), goalLines.get(indexMinDiff - v2),
					goalLines.get(indexMinDiff - v1), goalLines.get(indexMinDiff),
					goalLines.get(indexMinDiff + v1));
		}
	}
	
		
}//di classe

