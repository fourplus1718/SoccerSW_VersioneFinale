package scraper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * PJDCC - Summary for class responsabilities.
 *
 * @author fourplus <fourplus1718@gmail.com>
 * @since 1.0
 * @version 36 Changes done
 */
public class ScraperControls_3 {

	static void controlTwoGoalLines(ArrayList<main.Line> goalLines, int indexMinDiff, GoalLines GLS){
		int var1=-1;
		int var2=2;
		int var0=0;
		int var3=3;
		int var4=1;
		if (goalLines.size() <= 5 && goalLines.size() == 4 && indexMinDiff - 2 < 0) {
			GLS = new GoalLines(new main.Line(var1, var1, var1, "Pinn"), goalLines.get(var0), goalLines.get(var4),
					goalLines.get(var2), goalLines.get(var3));
		} else {
			GLS = new GoalLines(goalLines.get(var0), goalLines.get(var4), goalLines.get(var2), goalLines.get(var3),
				  new main.Line(var1, var1, var1, "Pinn"));
		}
		if (goalLines.size() <= 5 && goalLines.size() == 1)
			GLS.main = goalLines.get(0);
		if (goalLines.size() <= 5 && goalLines.size() != 4) {
			System.out.println("tuka");
		}
	}
	
	static void controlForOverUnder(ArrayList<main.Line> goalLines, float overOdds, float underOdds){
		for (main.Line line : goalLines) {
			if (line.line == 2.5) {
				overOdds = line.home;
				underOdds = line.away;
				break;
			}
		}
	}

	static void controlTwoAndHalf(main.Line twoAndHalf, WebElement div25, ArrayList<main.Line> goalLines, float overOdds, float underOdds){
		if (twoAndHalf == null) {
			System.out.println("Missing 2.5 goal line");
			div25.click();
	
			WebElement goalLineTable = div25.findElement(By.xpath("//table"));
	
			List<WebElement> rowsGoals = goalLineTable.findElements(By.xpath("//tbody/tr"));
		} else {
			controlForOverUnder(goalLines, overOdds, underOdds);
		}
	}
	
	static void controlOverTwo(float asianHome, ArrayList<main.Line> lines, float line, float asianAway, WebElement currentDiv, Actions actions){
		if (asianHome != -1f)
			lines.add(new main.Line(line, asianHome, asianAway, "Pinn"));
	
		List<WebElement> closeLink = currentDiv.findElements(By.className("odds-co"));
		if (!closeLink.isEmpty()) {
			actions.moveToElement(closeLink.get(0)).click().perform();
		}
	
		if (lines.size() == 6)
			break;
	}

	static void controlAsianLines(ArrayList<main.Line> lines, int expectedCaseSize, int start, int end, int indexMinDiff, AsianLines asianLines){
		int var11=1;
		int var12=2;
		int var13=3;
		if (lines.size() > 5 && expectedCaseSize == 5) {
			ArrayList<main.Line> bestLines = new ArrayList<>();
			for (int c = start; c <= end; c++) {
				bestLines.add(lines.get(c));
				}
			int var6=0;
			int var7=1;
			int var8=2;
			int var9=3;
			int var10=4;
			asianLines = new AsianLines(lines.get(var6), lines.get(var7), lines.get(var8), lines.get(var9), lines.get(var10));
			} 
		if (lines.size() > 5 && expectedCaseSize == 4 && indexMinDiff - 2 < 0) {
			asianLines = new AsianLines(lines.get(indexMinDiff - var11), lines.get(indexMinDiff),
					lines.get(indexMinDiff + var11), lines.get(indexMinDiff + var12), lines.get(indexMinDiff + var13));
		} 
		if (lines.size() > 5 && expectedCaseSize == 4 && indexMinDiff + 2 > lines.size() - 1) {
			asianLines = new AsianLines(lines.get(indexMinDiff - var13), lines.get(indexMinDiff - var12),
					lines.get(indexMinDiff - var11), lines.get(indexMinDiff), lines.get(indexMinDiff + var11));
		}
	}
	
	static void controlTwoAsianLines(ArrayList<main.Line> lines, int indexMinDiff, AsianLines asianLines){
		int var14=0, var15=2, var16=3, var17=0, var18=-1, var19=1;
		if (lines.size() == 4 && indexMinDiff - 2 < 0) {
			asianLines = new AsianLines(new main.Line(var18, var18, var18, "Pinn"), lines.get(var17), lines.get(var14),
					lines.get(var15), lines.get(var16));
		} 
		if(lines.size() == 4 && indexMinDiff - 2 >= 0) {
			asianLines = new AsianLines(lines.get(var14), lines.get(var19), lines.get(var15), lines.get(var16),
					new main.Line(var18, var18, var18, "Pinn"));
		} 
		if (lines.size() != 4 && lines.size() == 1)
			asianLines.main = lines.get(0);
	}
	
	static void controlGoalAssist(boolean verbose, Element divGoals) {
		if (divGoals != null) {
		Element table = divGoals.select("div.content").first().select("table").first();
		Elements rows = table.select("table").first().select("tr");
		int size = rows.size();
		for (int i = 0; i < size; i++) {
			Element row = rows.get(i);
			ScraperControls.controlVerbose(verbose, rows.text());
			Elements cols = row.select("td");
			ScraperControls.controlForGoals(cols, verbose, playerFixtures);
			}
		}
	}
	
	static void controlSubstitutes(Elements divSubstitutes, Elements divsPlayers, String homeTeam, String awayTeam, 
			ExtendedFixture fix, ArrayList<PlayerFixture> playerFixtures) {
		if (divSubstitutes != null) {
			Element tableHome = divSubstitutes.select("div.container.left").first();
			Element tableAway = divSubstitutes.select("div.container.right").first();
	
			Elements rowsHome = tableHome.select("table").first().select("tr");
			try {
				ScraperControls.controlSecondForGetFixtureFull(rowsHome, homeTeam, fix, playerFixtures);
			} catch (Exception e) {
				System.err.println("da ddassd");
			}
	
			Elements rowsAway = tableAway.select("table").first().select("tr");
			try {
				ScraperControls.controlSecondForGetFixtureFull(rowsAway, awayTeam, fix, playerFixtures);
				//gli if sono uguali a quelli di sopra, tranne per le prime 2 variabili
			} catch (Exception e) {
				System.out.println("parse");
			}
		}
	}
	
	static void controlHomeAwayOdds(Odds i, AsianOdds pinnAsianOdds, AsianOdds trueAsianOdds){
		AsianOdds m = (AsianOdds) i;
		int var1=100;
		if (m.homeOdds > pinnAsianOdds.homeOdds)
			System.out.println(i.bookmaker + " H " + m.line + " at " + m.homeOdds + " true: "
					+ Utils.format(trueAsianOdds.homeOdds) + " "
					+ Utils.format(var1 * m.homeOdds / trueAsianOdds.homeOdds - var1) + "%");
		if (m.awayOdds > pinnAsianOdds.awayOdds)
			System.out.println(i.bookmaker + " A " + m.line + " at " + m.awayOdds + " true: "
					+ Utils.format(trueAsianOdds.awayOdds) + " "
					+ Utils.format(100 * m.awayOdds / trueAsianOdds.awayOdds - 100) + "%");
	}
	
	static void controlIfHomeAwayOdds(ArrayList<Odds> matchOdds, Odds trueOdds, Odds pinnOdds){
		if (matchOdds.get(0) instanceof AsianOdds) {
			AsianOdds trueAsianOdds = (AsianOdds) trueOdds;
			AsianOdds pinnAsianOdds = (AsianOdds) pinnOdds;
			for (Odds i : matchOdds) {
				controlHomeAwayOdds(i, pinnAsianOdds, trueAsianOdds);
			}
		}
	}
	
	static void controlOverUnderOdds(Odds i, OverUnderOdds pinnOverUnderOdds, OverUnderOdds trueOverUnderOdds){
		OverUnderOdds m = (OverUnderOdds) i;
		int var2=100;
		if (m.overOdds > pinnOverUnderOdds.overOdds)
			System.out.println(i.bookmaker + " O " + m.line + " at " + m.overOdds + " true: "
					+ Utils.format(trueOverUnderOdds.overOdds) + " "
					+ Utils.format(var2 * m.overOdds / trueOverUnderOdds.overOdds - var2) + "%");
		if (m.underOdds > pinnOverUnderOdds.underOdds)
			System.out.println(i.bookmaker + " U " + m.line + " at " + m.underOdds + " true: "
					+ Utils.format(trueOverUnderOdds.underOdds) + " "
					+ Utils.format(var2 * m.underOdds / trueOverUnderOdds.underOdds - var2) + "%");
	}
	
	static void controlIfOverUnderOdds(ArrayList<Odds> matchOdds, Odds trueOdds, Odds pinnOdds){
		if (matchOdds.get(0) instanceof OverUnderOdds) {
			OverUnderOdds trueOverUnderOdds = (OverUnderOdds) trueOdds;
			OverUnderOdds pinnOverUnderOdds = (OverUnderOdds) pinnOdds;
			for (Odds i : matchOdds) {
				controlOverUnderOdds(i, pinnOverUnderOdds, trueOverUnderOdds);	
			}
		}
	}

	static void controlDiv25(List<WebElement> divs, WebElement div25){
		for (WebElement div : divs) {
			if (div.getText().contains("+2.5")) {
				div25 = div;
				div.click();
				break;
			}
		}
	}
	
	static void controlIfElse(String resString, Result fullResult, Result htResult){
		int var1=2, var2=3, var=1, var4=4;
		if (resString.contains("(") && resString.contains(")")) {
			String full = resString.split(" ")[var1];
			String half = resString.split(" ")[var2].substring(var, var4);
	
			fullResult = new Result(Integer.parseInt(full.split(":")[0]), Integer.parseInt(full.split(":")[1]));
			htResult = new Result(Integer.parseInt(half.split(":")[0]), Integer.parseInt(half.split(":")[1]));
		} else {
			fullResult = new Result(-1, -1);
			htResult = new Result(-1, -1);
		}
	}
	
	static void controlTryForIfGetOddsFixture(List<WebElement> divsAsian, float min, WebElement opt, String home, String away, WebDriver driver) {
		try {
			for (WebElement div : divsAsian) {
				ScraperControls.controlSplit(div, min, opt);
			}
		} catch (Exception e) {
			System.out.println("asian problem" + home + " " + away);
		}
	
		float line = -1f, asianHome = -1f, asianAway = -1f;
	
		if (opt != null) {
			try {
				Actions actions = new Actions(driver);
				actions.moveToElement(opt).click().perform();
			} catch (Exception e) {
				System.out.println("click error ah line ==");
				System.out.println("Something was wrong");
			}
	
			WebElement AHTable = opt.findElement(By.xpath("//table"));
	
			// find the row
			List<WebElement> rowsAsian = AHTable.findElements(By.xpath("//tr"));
	
			ScraperControls.controlRow(rowsAsian, line, asianHome, asianAway);
		}
	}
	
	static void controlForIfPinnIndex(List<WebElement> customer, int pinnIndex, int Index365) {
		for (WebElement row : customer) {
			ScraperControls.controlPinnIndex(row, customer, pinnIndex);
		}
		
		if (pinnIndex < 0) {
			System.out.println("Could not find pinnacle");
			pinnIndex = Index365;
			pinnIndex = 1;
		}
	}
	
	void fullMatchOddsOverPinnacle(WebDriver driver) {
		WebElement table = driver.findElement(By.xpath("//div[@id='odds-data-table']"));
		List<WebElement> rows = table.findElements(By.xpath("//div[1]/table/tbody/tr"));
		Odds pinnOdds = null;

		ArrayList<Odds> matchOdds = createArrayListOdds();
		for (WebElement row : rows) {
			List<WebElement> columns = row.findElements(By.xpath("td"));
			if (columns.size() < 4)
				continue;
			String bookmaker = columns.get(0).getText().trim();
			if (Arrays.asList(MinMaxOdds.FAKEBOOKS).contains(bookmaker))
				continue;
			int v1=1, v2=2, v3=3;
			float homeOdds = Float.parseFloat(columns.get(v1).getText().trim());
			float drawOdds = Float.parseFloat(columns.get(v2).getText().trim());
			float awayOdds = Float.parseFloat(columns.get(v3).getText().trim());

			Odds modds = new MatchOdds(bookmaker, new Date(), homeOdds, drawOdds, awayOdds);
			matchOdds.add(modds);

			if (bookmaker.equals("Pinnacle"))
				pinnOdds = modds;
		}

		checkValueOverPinnacleOdds(matchOdds, pinnOdds);

	}

	static ArrayList<Odds> createArrayListOdds(){
		return new ArrayList<Odds>();
	}
	
	static void checkValueOverPinnacleOdds(ArrayList<Odds> matchOdds, Odds pinnOdds) {
		if (matchOdds.isEmpty() || pinnOdds == null)
			return;

		Odds trueOdds = pinnOdds.getTrueOddsMarginal();

		if (matchOdds.get(0) instanceof MatchOdds) {
			int v20=100;
			MatchOdds trueMatchOdds = (MatchOdds) trueOdds;
			MatchOdds pinnMatchOdds = (MatchOdds) pinnOdds;
			List<MatchOdds> casted = matchOdds.stream().map(MatchOdds.class::cast).collect(Collectors.toList());

			casted.sort(Comparator.comparing(MatchOdds::getHomeOdds).reversed());
			casted.stream().filter(m -> m.homeOdds > pinnMatchOdds.homeOdds)
					.forEach(i -> System.out.println(
							i.bookmaker + " 1 at " + i.homeOdds + " true: " + Utils.format(trueMatchOdds.homeOdds) + " "
									+ Utils.format(v20 * i.homeOdds / trueMatchOdds.homeOdds - v20) + "%"));
			casted.sort(Comparator.comparing(MatchOdds::getDrawOdds).reversed());
			casted.stream().filter(m -> m.drawOdds > pinnMatchOdds.drawOdds)
					.forEach(i -> System.out.println(
							i.bookmaker + " X at " + i.drawOdds + " true: " + Utils.format(trueMatchOdds.drawOdds) + " "
									+ Utils.format(v20 * i.drawOdds / trueMatchOdds.drawOdds - v20) + "%"));
			casted.sort(Comparator.comparing(MatchOdds::getAwayOdds).reversed());
			casted.stream().filter(m -> m.awayOdds > pinnMatchOdds.awayOdds)
					.forEach(i -> System.out.println(
							i.bookmaker + " 2 at " + i.awayOdds + " true: " + Utils.format(trueMatchOdds.awayOdds) + " "
									+ Utils.format(v20 * i.awayOdds / trueMatchOdds.awayOdds - v20) + "%"));

		}

		controlIfHomeAwayOdds(matchOdds, trueOdds, pinnOdds);

		controlIfOverUnderOdds(matchOdds, trueOdds, pinnOdds);

	}
	
	static void controlTryForAsian(int lower, int higher, List<WebElement> divsAsian, WebDriver driver) {
		try {
			for (int j = lower; j <= higher; j++) {
				WebElement currentDiv = divsAsian.get(j);
				if (currentDiv == null || currentDiv.getText().split("\n").length < 3)
					continue;
	
				// currentDiv.click();
				Actions actions = new Actions(driver);
				actions.moveToElement(currentDiv).click().perform();
	
				WebElement AHTable = currentDiv.findElement(By.xpath("//table"));
	
				List<WebElement> rowsGoals = AHTable.findElements(By.xpath("//tbody/tr"));
				float line = -1f, home = -1f, away = -1f;
	
				Odds pinnOdds = null;
	
				ArrayList<Odds> matchOdds = createArrayListOdds();
				controlRowHomeUnder(rowsGoals, line, home, away, matchOdds, pinnOdds);
	
				checkValueOverPinnacleOdds(matchOdds, pinnOdds);
	
				List<WebElement> closeLink = currentDiv.findElements(By.className("odds-co"));
				if (!closeLink.isEmpty()) {
					actions.moveToElement(closeLink.get(0)).click().perform();
				}
	
			}
		} catch (Exception e) {
			continue;
		}
	}

	static void controlBigForOverUnderOver(int lower, int higher, List<WebElement> divsGoals, WebDriver driver) {
		for (int j = lower; j <= higher; j++) {
			WebElement currentDiv = divsGoals.get(j);
			if (currentDiv == null || currentDiv.getText().split("\n").length < 3)
				continue;
	
			Actions actions = createAction(driver);
			actions.moveToElement(currentDiv).click().perform();
	
			WebElement goalLineTable = currentDiv.findElement(By.xpath("//table"));
	
			// find the row
			List<WebElement> rowsGoals = goalLineTable.findElements(By.xpath("//tbody/tr"));
			float line = -1f, over = -1f, under = -1f;
	
			Odds pinnOdds = null;
	
			ArrayList<Odds> matchOdds = createArrayListOdds();
			try {
				ScraperControls.controlRowHomeUnder(rowsGoals, line, over, under, matchOdds, pinnOdds);
				
			} catch (Exception e) {
				continue;
			}
	
			checkValueOverPinnacleOdds(matchOdds, pinnOdds);
	
			List<WebElement> closeLink = currentDiv.findElements(By.className("odds-co"));
			if (!closeLink.isEmpty()) {
				actions.moveToElement(closeLink.get(0)).click().perform();
			}
		}
	}
	
	static void controlBigForGetFullFixture(int lower, int higher, List<WebElement> divsGoals, WebDriver driver, main.Line twoAndHalf) {
		
		for (int j = lower; j <= higher; j++) {
			WebElement currentDiv = divsGoals.get(j);
			if (currentDiv == null || currentDiv.getText().split("\n").length < 3)
				continue;
	
			Actions actions = createAction(driver);
			actions.moveToElement(currentDiv).click().perform();
			WebElement goalLineTable = currentDiv.findElement(By.xpath("//table"));
	
			// find the row
			List<WebElement> rowsGoals = goalLineTable.findElements(By.xpath("//tbody/tr"));
			float line = -1f, over = -1f, under = -1f;
	
			controlRowsGoals(rowsGoals, line, over, under);
	
			controlOver(over, goalLines,line, over, under, twoAndHalf, currentDiv, actions);
		}
	}
	
	static void controlFirstForOnIf(int lower, int higher, List<WebElement> divsAsian, WebDriver driver, float line) {
		for (int j = lower; j <= higher; j++) {
			WebElement currentDiv = divsAsian.get(j);
			if (currentDiv == null || currentDiv.getText().split("\n").length < 3)
				continue;
	
			// currentDiv.click();
			Actions actions = createAction(driver);
			actions.moveToElement(currentDiv).click().perform();
	
			WebElement AHTable = currentDiv.findElement(By.xpath("//table"));
	
			// find the row
			List<WebElement> rowsAsian = AHTable.findElements(By.xpath("//tbody/tr"));
			float line = -1f, asianHome = -1f, asianAway = -1f;
	
			controlRowsGoals(rowsAsian, line, asianHome, asianAway);
	
			controlOverTwo(asianHome, lines, line, asianAway, currentDiv, actions);
		}
	}
	
	static void controlTwoAndHalfPart2(main.Line twoAndHalf, float overOdds, float underOdds) {
		if (twoAndHalf == null && rowsGoals.size() >= 2) {
			WebElement row = rowsGoals.get(1);
			String textOdds = row.getText();
			try {
				overOdds = Float.parseFloat(textOdds.split("\n")[2].trim());
				underOdds = Float.parseFloat(textOdds.split("\n")[3].trim());
			} catch (Exception e) {
				System.out.println("Error");
			}
		}
	}
	
	static void controlBigIfGetFullFixture(WebElement optGoals, int indexOfOptimalGoals, List<WebElement> divsGoals, WebDriver driver, main.Line twoAndHalf, 
			ArrayList<main.Line> goalLines, GoalLines GLS, float overOdds, float underOdds){
		
		if (optGoals != null) {
		int lower = (indexOfOptimalGoals - 6) < 0 ? 0 : (indexOfOptimalGoals - 6);
		int higher = (indexOfOptimalGoals + 6) > (divsGoals.size() - 1) ? (divsGoals.size() - 1)
				: (indexOfOptimalGoals + 6);

		long startt = System.currentTimeMillis();
		ScraperControls.controlBigForGetFullFixture(lower, higher, divsGoals, driver, twoAndHalf);
		
		System.out.println(goalLines);

		int indexMinDiff = -1;
		float minDiff = 100f;
		ScraperControls.controlDiff(goalLines, minDiff, indexMinDiff);

		int start = indexMinDiff - 2 < 0 ? 0 : indexMinDiff - 2;
		int end = indexMinDiff + 2 > goalLines.size() - 1 ? goalLines.size() - 1 : indexMinDiff + 2;

		int expectedCaseSize = end - start + 1;
		int v0=0, v1=1, v2=2, v3=3, v4=4;
		if (goalLines.size() == 5) {
			GLS = new GoalLines(goalLines.get(v0), goalLines.get(v1), goalLines.get(v2), goalLines.get(v3),
					goalLines.get(v4));
		}
		
		controlGoalLines(goalLines, expectedCaseSize, start, end, indexMinDiff, GLS);
		
		controlTwoGoalLines(goalLines, indexMinDiff, GLS);

		controlTwoAndHalf(twoAndHalf, div25, goalLines, overOdds, underOdds);
		
		controlTwoAndHalfPart2(twoAndHalf, overOdds, underOdds);
		}
	}

	static void controlTripleFloat(List<WebElement> rows, Odds pinnOdds) {
	
		ArrayList<Odds> matchOdds = new ArrayList<>();
		for (WebElement row : rows) {
			List<WebElement> columns = row.findElements(By.xpath("td"));
			
			ScraperControls.controlColumns(columns);
			int v1=1, v2=2, v3=3;
			float homeOdds = Float.parseFloat(columns.get(v1).getText().trim());
			float drawOdds = Float.parseFloat(columns.get(v2).getText().trim());
			float awayOdds = Float.parseFloat(columns.get(v3).getText().trim());
	
			Odds modds = new MatchOdds(bookmaker, new Date(), homeOdds, drawOdds, awayOdds);
			matchOdds.add(modds);
	
			if (bookmaker.equals("Pinnacle"))
				pinnOdds = modds;
		}
	
		checkValueOverPinnacleOdds(matchOdds, pinnOdds);
		
	}
	
	static void controlForMinDiff(ArrayList<main.Line> lines) {
		int size = lines.size();
		for (int l = 0; l < size; l++) {
			float diff = Math.abs(lines.get(l).home - lines.get(l).away);
			if (diff < minDiff) {
				minDiff = diff;
				indexMinDiff = l;
			}
		}
	}
	
	static void controlFinalBigIf(WebElement opt, int indexOfOptimal, List<WebElement> divsAsian, WebElement driver,
			ArrayList<main.Line> lines, AsianLines asianLines) {
		
		if (opt != null) {
			int lower = (indexOfOptimal - 5) < 0 ? 0 : (indexOfOptimal - 5);
			int higher = (indexOfOptimal + 5) > (divsAsian.size() - 1) ? (divsAsian.size() - 1) : (indexOfOptimal + 5);
	
			ScraperControls.controlFirstForOnIf(lower, higher, divsAsian, driver, line);
			System.out.println(lines);
	
			int indexMinDiff = -1;
			float minDiff = 100f;
			
			ScraperControls.controlForMinDiff(lines);
			
			int start = indexMinDiff - 2 < 0 ? 0 : indexMinDiff - 2;
			int end = indexMinDiff + 2 > lines.size() - 1 ? lines.size() - 1 : indexMinDiff + 2;
	
			int expectedCaseSize = end - start + 1;
			if (lines.size() == 5) {
				int v0=0, v1=1, v2=2, v3=3, v4=4;
				asianLines = new AsianLines(lines.get(v0), lines.get(v1), lines.get(v2), lines.get(v3), lines.get(v4));
			}
	
			ScraperControls.controlAsianLines(lines, expectedCaseSize, start, end, indexMinDiff, asianLines);
			
			ScraperControls.controlTwoAsianLines(lines, indexMinDiff, asianLines);
		} 
	}
	
	static void controlSplit(WebElement div, float x, WebElement y){
		String text = div.getText();
		if (text.split("\n").length > 3) {
			float diff = Math.abs(Float.parseFloat(text.split("\n")[2].trim())
					- Float.parseFloat(text.split("\n")[3].trim()));
		}
		if (text.split("\n").length > 3 && diff < min) {
			x = diff;
			y = div;
		}
	}

	static void controlRow(List<WebElement> rowsAsian, float line, float asianHome, float asianAway){
		for (WebElement row : rowsAsian) {
			if (row.getText().contains("Pinnacle")) {
				String textOdds = row.getText();
				line = Float.parseFloat(textOdds.split("\n")[1].trim());
				asianHome = Float.parseFloat(textOdds.split("\n")[2].trim());
				asianAway = Float.parseFloat(textOdds.split("\n")[3].trim());
				break;
			}
		}
	}
	
}
