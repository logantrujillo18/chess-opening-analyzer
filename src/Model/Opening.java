package Model;

import Helper.Helper;
import Main.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.Random;
import java.util.Scanner;

import static Main.Main.high_elo_opening;
import static Main.Main.my_results;

public class Opening {

    public static ObservableList<Opening> allOpenings = FXCollections.observableArrayList();
    public static ObservableList<String> openingNames = FXCollections.observableArrayList();
    public static ObservableList<String> whiteOpenings = FXCollections.observableArrayList();
    public static ObservableList<String> blackOpenings = FXCollections.observableArrayList();
    public static ObservableList<Opening> playerResults = FXCollections.observableArrayList();


    private String openingName;
    private String side;
    private int numGames;
    private int performanceRating;
    private int averageRating;
    private float percentageWin;
    private float percentageDraw;
    private float percentageLoss;
    private String moves;

    public Opening (String openingName, String side, int numGames, int performanceRating, int averageRating, float percentageWin, float percentageDraw, float percentageLoss, String moves) {

        this.openingName = openingName;
        this.side = side;
        this.numGames = numGames;
        this.performanceRating = performanceRating;
        this.averageRating = averageRating;
        this.percentageWin = percentageWin;
        this.percentageDraw = percentageDraw;
        this.percentageLoss = percentageLoss;
        this.moves = moves;

    }

    public String getOpeningName() { return openingName; }

    public void setOpeningName(String openingName) { this.openingName = openingName; }

    public String getSide() { return side; }

    public void setSide(String side) { this.side = side; }

    public int getNumGames() { return numGames; }

    public void setNumGames(int numGames) { this.numGames = numGames; }

    public int getPerformanceRating() { return performanceRating; }

    public void setPerformanceRating(int performanceRating) { this.performanceRating = performanceRating; }

    public int getAverageRating() { return averageRating; }

    public void setAverageRating(int averageRating) { this.averageRating = averageRating; }

    public float getPercentageWin() { return percentageWin; }

    public void setPercentageWin(float percentageWin) { this.percentageWin = percentageWin; }

    public float getPercentageDraw() { return percentageDraw; }

    public void setPercentageDraw(float percentageDraw) { this.percentageDraw = percentageDraw; }

    public float getPercentageLoss() { return percentageLoss; }

    public void setPercentageLoss(float percentageLoss) { this.percentageLoss = percentageLoss; }

    public String getMoves() { return moves; }

    public void setMoves(String moves) { this.moves = moves; }

    public static void loadOpenings() throws Exception {

        String file = high_elo_opening;
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] object = line.split(",");
                String openingName = object[0];
                String side = object[1];
                int numGames = Integer.parseInt(object[2]);
                int performanceRating = Integer.parseInt(object[3]);
                int averageRating = Integer.parseInt(object[4]);
                float percentageWin = Float.parseFloat(object[5]);
                float percentageDraw = Float.parseFloat(object[6]);
                float percentageLoss = Float.parseFloat(object[7]);
                String moves = object[8];
                Opening opening = new Opening(openingName, side, numGames, performanceRating, averageRating, percentageWin, percentageDraw, percentageLoss, moves);
                Opening.allOpenings.add(opening);
                Opening.openingNames.add(openingName);
                if (side.equals("white")) {
                    Opening.whiteOpenings.add(openingName);
                } else {
                    Opening.blackOpenings.add(openingName);
                }
            }
        }
    }

    public static void loadResults() throws Exception {

        String file = my_results;
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] object = line.split(",");
                String openingName = object[0];
                String side = object[1];
                int numGames = Integer.parseInt(object[2]);
                int performanceRating = Integer.parseInt(object[3]);
                int averageRating = Integer.parseInt(object[4]);
                float percentageWin = Float.parseFloat(object[5]);
                float percentageDraw = Float.parseFloat(object[6]);
                float percentageLoss = Float.parseFloat(object[7]);
                String moves = object[8];
                Opening opening = new Opening(openingName, side, numGames, performanceRating, averageRating, percentageWin, percentageDraw, percentageLoss, moves);
                Opening.playerResults.add(opening);

            }
        }

    }

    public static void WriteToCsv(Opening opening) throws Exception {

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(Main.my_results, true))) {

                writer.write(opening.getOpeningName() + ",");
                writer.write(opening.getSide() + ",");
                writer.write(opening.getNumGames() + ",");
                writer.write(opening.getPerformanceRating() + ",");
                writer.write(opening.getAverageRating() + ",");
                writer.write(opening.getPercentageWin() + ",");
                writer.write(opening.getPercentageDraw() + ",");
                writer.write(opening.getPercentageLoss() + ",");
                writer.write(opening.getMoves() + ",");
                writer.newLine();

                Opening.playerResults.add(opening);

                System.out.println("Wrote to file");

        }
    }

    public static void Suggestion(String side, int myElo, int oppElo) {

        int ratingDifference = oppElo - myElo;
        System.out.println("Elo difference is " + ratingDifference);
        double eloProbability = GetEloProbability(ratingDifference);

        int averageRating;
        float playerWinPercentage = 0;
        float playerCombinedPercentage = 0;
        float winPercentage = 0;
        float drawPercentage = 0;
        float combinedPercentage = 0;
        ObservableList<Opening> lessKnownOpenings = FXCollections.observableArrayList();
        ObservableList<Opening> mostKnownOpenings = FXCollections.observableArrayList();

        Opening suggestion1 = null; //best win percentage historically
        Opening suggestion2 = null; //best combined percentage historically
        Opening suggestion3 = null; //best user win percentage
        Opening suggestion4 = null; //best user combined win percentage
        Opening suggestion5 = null; //either a random lesser known opening or random more known opening depending on Elo

        if (side.equals("white")) {
            for (int i = 0; i < Opening.allOpenings.size(); i++) {
                if ((Opening.allOpenings.get(i).getSide().equals("white")) && (winPercentage < (Opening.allOpenings.get(i).getPercentageWin()))) {
                    suggestion1 = Opening.allOpenings.get(i);
                    winPercentage = Opening.allOpenings.get(i).getPercentageWin();
                }
                if ((Opening.allOpenings.get(i).getPercentageDraw() + Opening.allOpenings.get(i).getPercentageWin() > combinedPercentage) && (Opening.allOpenings.get(i).getSide().equals("white"))) {
                    suggestion2 = Opening.allOpenings.get(i);
                    combinedPercentage = Opening.allOpenings.get(i).getPercentageDraw() + Opening.allOpenings.get(i).getPercentageWin();
                }
                if ((Opening.allOpenings.get(i).getNumGames() < 500) && Opening.allOpenings.get(i).getSide().equals("white")) {
                    lessKnownOpenings.add(Opening.allOpenings.get(i));
                }
                if ((Opening.allOpenings.get(i).getNumGames() > 10000) && Opening.allOpenings.get(i).getSide().equals("white")) {
                    mostKnownOpenings.add(Opening.allOpenings.get(i));
                }
            }
            for (int j = 0; j < Opening.playerResults.size(); j++) {
                float playerWinPercentageTemp = (playerResults.get(j).percentageWin / (playerResults.get(j).getPercentageWin() + playerResults.get(j).getPercentageDraw() + playerResults.get(j).getPercentageLoss()));
                float playerCombinedPercentageTemp = ((playerResults.get(j).percentageDraw + playerResults.get(j).percentageWin) / (playerResults.get(j).getPercentageWin() + playerResults.get(j).getPercentageDraw() + playerResults.get(j).getPercentageLoss()));
                if ((playerWinPercentageTemp > playerWinPercentage) && Opening.playerResults.get(j).getSide().equals("white")) {
                    suggestion3 = playerResults.get(j);
                    playerWinPercentage = (playerResults.get(j).percentageWin / (playerResults.get(j).getPercentageWin() + playerResults.get(j).getPercentageDraw() + playerResults.get(j).getPercentageLoss()));
                }
                if ((playerCombinedPercentageTemp > playerCombinedPercentage) && Opening.playerResults.get(j).getSide().equals("white")) {
                    suggestion4 = playerResults.get(j);
                    playerCombinedPercentage = ((playerResults.get(j).percentageDraw + playerResults.get(j).percentageWin) / (playerResults.get(j).getPercentageWin() + playerResults.get(j).getPercentageDraw() + playerResults.get(j).getPercentageLoss()));
                }

            }

            String suggestion1String = "The opening with the best win percentage for your side historically has been " + suggestion1.getOpeningName() + ", characterized by " + suggestion1.getMoves() + ". This is my first suggestion. \n\n";
            String suggestion2String = "If you are in a position such that a draw is an acceptable result, I would suggest playing " + suggestion2.getOpeningName() + ", characterized by " + suggestion2.getMoves() + ". This opening has a high combined win + draw percentage. \n\n";
            String suggestion3String = "It's also worth considering what you already know. For that reason, I would like to make you aware that your best opening for this side has historically been " + suggestion3.getOpeningName() + ". As a reminder, the moves for this opening are " + suggestion3.getMoves() + ". \n\n";
            String suggestion4String = "";
            String suggestion5String = "";
            if (suggestion3.getOpeningName().equals(suggestion4.getOpeningName())) {
                suggestion4String = "Interestingly, if you only need at least a draw, your history would also suggest you play the same opening: " + suggestion4.getOpeningName() + ". This seems to clearly be your strongest opening for this side. \n\n";
            }
            else {
                suggestion4String = "If you only need at least a draw, your history would suggest you play " + suggestion4.getOpeningName() + ". To do this, try to play " + suggestion4.getMoves() + ". \n\n";
            }
            if (eloProbability > 24.00 && eloProbability < 76.00) {
                Random random = new Random();
                suggestion5 = mostKnownOpenings.get(random.nextInt(mostKnownOpenings.size()));
                System.out.println("Random = " + suggestion5.getOpeningName());
                suggestion5String = "Taking Elo into account, your skill level is roughly the same as your opponent's. Therefore, it may be best to stick to an opening with plenty of established theory behind it, that you can study, and therefore play the game on your terms." +
                        " One possible opening fitting this criteria is " + suggestion5.getOpeningName() + ", which is " + suggestion5.getMoves();
            }
            else if (eloProbability >= 76.00) {
                Random random = new Random();
                suggestion5 = lessKnownOpenings.get(random.nextInt(lessKnownOpenings.size()));
                System.out.println("Random = " + suggestion5.getOpeningName());
                suggestion5String = "Taking Elo into account, your skill level is significantly greater than your opponent's. Therefore, it may be best to use an experimental opening that is less-often played. This can help ensure you don't accidentally walk into your opponent's preferred opening." +
                        " One possible opening fitting this criteria is " + suggestion5.getOpeningName() + ", which is " + suggestion5.getMoves();
            }
            else {
                Random random = new Random();
                suggestion5 = lessKnownOpenings.get(random.nextInt(lessKnownOpenings.size()));
                System.out.println("Random = " + suggestion5.getOpeningName());
                suggestion5String = "Taking Elo into account, your skill level is significantly weaker than your opponent's. You are already likely to lose. Therefore, it may be best to use an experimental opening that is less-often played to see if you can catch your opponent by surprise to even the odds." +
                        " One possible opening fitting this criteria is " + suggestion5.getOpeningName() + ", which is " + suggestion5.getMoves();
            }

            Helper.Alert(suggestion1String + suggestion2String + suggestion3String + suggestion4String + suggestion5String);

        }

        else {
            for (int i = 0; i < Opening.allOpenings.size(); i++) {
                if ((Opening.allOpenings.get(i).getSide().equals("black")) && (winPercentage < (Opening.allOpenings.get(i).getPercentageWin()))) {
                    suggestion1 = Opening.allOpenings.get(i);
                    winPercentage = Opening.allOpenings.get(i).getPercentageWin();
                }
                if ((Opening.allOpenings.get(i).getPercentageDraw() + Opening.allOpenings.get(i).getPercentageWin() > combinedPercentage) && (Opening.allOpenings.get(i).getSide().equals("black"))) {
                    suggestion2 = Opening.allOpenings.get(i);
                    combinedPercentage = Opening.allOpenings.get(i).getPercentageDraw() + Opening.allOpenings.get(i).getPercentageWin();
                }
                if ((Opening.allOpenings.get(i).getNumGames() < 500) && Opening.allOpenings.get(i).getSide().equals("black")) {
                    lessKnownOpenings.add(Opening.allOpenings.get(i));
                }
                if ((Opening.allOpenings.get(i).getNumGames() > 10000) && Opening.allOpenings.get(i).getSide().equals("black")) {
                    mostKnownOpenings.add(Opening.allOpenings.get(i));
                }
            }
            for (int j = 0; j < Opening.playerResults.size(); j++) {
                float playerWinPercentageTemp = (playerResults.get(j).percentageWin / (playerResults.get(j).getPercentageWin() + playerResults.get(j).getPercentageDraw() + playerResults.get(j).getPercentageLoss()));
                float playerCombinedPercentageTemp = ((playerResults.get(j).percentageDraw + playerResults.get(j).percentageWin) / (playerResults.get(j).getPercentageWin() + playerResults.get(j).getPercentageDraw() + playerResults.get(j).getPercentageLoss()));
                if ((playerWinPercentageTemp > playerWinPercentage) && Opening.playerResults.get(j).getSide().equals("black")) {
                    suggestion3 = playerResults.get(j);
                    playerWinPercentage = (playerResults.get(j).percentageWin / (playerResults.get(j).getPercentageWin() + playerResults.get(j).getPercentageDraw() + playerResults.get(j).getPercentageLoss()));
                }
                if ((playerCombinedPercentageTemp > playerCombinedPercentage) && Opening.playerResults.get(j).getSide().equals("black")) {
                    suggestion4 = playerResults.get(j);
                    playerCombinedPercentage = ((playerResults.get(j).percentageDraw + playerResults.get(j).percentageWin) / (playerResults.get(j).getPercentageWin() + playerResults.get(j).getPercentageDraw() + playerResults.get(j).getPercentageLoss()));
                }

            }

            String suggestion1String = "The opening with the best win percentage for your side historically has been " + suggestion1.getOpeningName() + ", characterized by " + suggestion1.getMoves() + ". This is my first suggestion. \n\n";
            String suggestion2String = "If you are in a position such that a draw is an acceptable result, I would suggest playing " + suggestion2.getOpeningName() + ", characterized by " + suggestion2.getMoves() + ". This opening has a high combined win + draw percentage. \n\n";
            String suggestion3String = "It's also worth considering what you already know. For that reason, I would like to make you aware that your best opening for this side has historically been " + suggestion3.getOpeningName() + ". As a reminder, the moves for this opening are " + suggestion3.getMoves() + ". \n\n";
            String suggestion4String = "";
            String suggestion5String = "";
            if (suggestion3.getOpeningName().equals(suggestion4.getOpeningName())) {
                suggestion4String = "Interestingly, if you only need at least a draw, your history would also suggest you play the same opening: " + suggestion4.getOpeningName() + ". This seems to clearly be your strongest opening for this side. \n\n";
            } else {
                suggestion4String = "If you only need at least a draw, your history would suggest you play " + suggestion4.getOpeningName() + ". To do this, try to play " + suggestion4.getMoves() + ". \n\n";
            }
            if (eloProbability > 24.00 && eloProbability < 76.00) {
                Random random = new Random();
                suggestion5 = mostKnownOpenings.get(random.nextInt(mostKnownOpenings.size()));
                System.out.println("Random = " + suggestion5.getOpeningName());
                suggestion5String = "Taking Elo into account, your skill level is roughly the same as your opponent's. Therefore, it may be best to stick to an opening with plenty of established theory behind it, that you can study, and therefore play the game on your terms." +
                        " One possible opening fitting this criteria is " + suggestion5.getOpeningName() + ", which is " + suggestion5.getMoves();
            } else if (eloProbability >= 76.00) {
                Random random = new Random();
                suggestion5 = lessKnownOpenings.get(random.nextInt(lessKnownOpenings.size()));
                System.out.println("Random = " + suggestion5.getOpeningName());
                suggestion5String = "Taking Elo into account, your skill level is significantly greater than your opponent's. Therefore, it may be best to use an experimental opening that is less-often played. This can help ensure you don't accidentally walk into your opponent's preferred opening." +
                        " One possible opening fitting this criteria is " + suggestion5.getOpeningName() + ", which is " + suggestion5.getMoves();
            } else {
                Random random = new Random();
                suggestion5 = lessKnownOpenings.get(random.nextInt(lessKnownOpenings.size()));
                System.out.println("Random = " + suggestion5.getOpeningName());
                suggestion5String = "Taking Elo into account, your skill level is significantly weaker than your opponent's. You are already likely to lose. Therefore, it may be best to use an experimental opening that is less-often played to see if you can catch your opponent by surprise to even the odds." +
                        " One possible opening fitting this criteria is " + suggestion5.getOpeningName() + ", which is " + suggestion5.getMoves();
            }

            Helper.Alert(suggestion1String + suggestion2String + suggestion3String + suggestion4String + suggestion5String);
        }


    }

    public static void Prediction(Opening opening, int myElo, int oppElo) throws Exception {

        double prediction = 0;

        int ratingDifference = oppElo - myElo;
        System.out.println("Elo difference is " + ratingDifference);
        double eloProbability = GetEloProbability(ratingDifference);


        boolean playerHasPlayedThisBefore = false;
        Opening playerResult;

        int numGames = opening.getNumGames();
        int averageRating = opening.getAverageRating();
        float percentageWin = opening.getPercentageWin();
        float percentageLoss = opening.getPercentageLoss();
        float perentageDraw = opening.getPercentageDraw();
        String moves = opening.getMoves();

        for (int i = 0; i < Opening.playerResults.size(); i++) {
            if (opening.getOpeningName().equals(Opening.playerResults.get(i).getOpeningName())) {
                System.out.println("Player has played this before");
                playerHasPlayedThisBefore = true;
                playerResult = Opening.playerResults.get(i);

                double truePlayerWin;
                double truePlayerDraw;
                String playerNumGamesConsideration;

                truePlayerWin = (playerResult.getPercentageWin() / (playerResult.getPercentageWin() + playerResult.getPercentageDraw() + playerResult.getPercentageLoss()));
                System.out.println("True player win = " + truePlayerWin);
                truePlayerDraw = (playerResult.getPercentageDraw() / (playerResult.getPercentageWin() + playerResult.getPercentageDraw() + playerResult.getPercentageLoss()));
                System.out.println("True player win = " + truePlayerDraw);

                if (playerResult.getNumGames() < 3) {
                    prediction = ((eloProbability * 0.9) + ((truePlayerWin * 100) * 0.1));
                    playerNumGamesConsideration = "Since you have less than 3 games with this opening, your history will be weighted minimally.";
                }
                else if (playerResult.getNumGames() >= 3 && playerResult.getNumGames() < 6 ) {
                    prediction = ((eloProbability * 0.8) + ((truePlayerWin * 100) * 0.2));
                    playerNumGamesConsideration = "Since you have between 3 and 6 games with this opening, your history will be weighted somewhat.";
                }
                else if (playerResult.getNumGames() >= 6 && playerResult.getNumGames() < 9 ) {
                    prediction = ((eloProbability * 0.7) + ((truePlayerWin * 100) * 0.3));
                    playerNumGamesConsideration = "Since you have between 6 and 9 games with this opening, your history will be weighted moderately.";
                }
                else if (playerResult.getNumGames() >= 9 && playerResult.getNumGames() < 12 ) {
                    prediction = ((eloProbability * 0.5) + ((truePlayerWin * 100) * 0.5));
                    playerNumGamesConsideration = "Since you have between 9 and 12 games with this opening, your history will be weighted significantly.";
                }
                else if (playerResult.getNumGames() >= 15 && playerResult.getNumGames() < 18 ) {
                    prediction = ((eloProbability * 0.4) + ((truePlayerWin * 100) * 0.6));
                    playerNumGamesConsideration = "Since you have between 15 and 18 games with this opening, your history will be weighted largely, even more than Elo implied probability.";
                }
                else if (playerResult.getNumGames() >= 21 && playerResult.getNumGames() < 24 ) {
                    prediction = ((eloProbability * 0.3) + ((truePlayerWin * 100) * 0.7));
                    playerNumGamesConsideration = "Since you have between 21 and 24 games with this opening, your history will be weighted very largely, significantly more than Elo implied probability.";
                }
                else {
                    prediction = ((eloProbability * 0.25) + ((truePlayerWin * 100) * 0.75));
                    playerNumGamesConsideration = "Since you have more than 24 games with this opening, your history will be weighted to the maximum.";
                }


                Helper.Alert("Based on raw Elo probability, your chance to win is " + eloProbability + "%. However, you have history playing with this opening. " +
                        playerNumGamesConsideration + " Taking all of this into account, my prediction is you would have a " + prediction + "% chance to win this game if you play this opening.");

                break;


            }

            break;

        }

        String numGamesConsideration;

        if (numGames < 500) {
            prediction = ((eloProbability * 0.95) + ((percentageWin) * 0.05));
            numGamesConsideration = "Since there are less than 500 recorded master games with this opening, its history will be weighted minimally.";
        }
        else if (numGames >= 500 && numGames < 1000) {
            prediction = ((eloProbability * 0.92) + ((percentageWin) * 0.08));
            numGamesConsideration = "Since there are between 500 and 1000 games with this opening, its history will be weighted somewhat.";
        }
        else if (numGames >= 1000 && numGames < 2000) {
            prediction = ((eloProbability * 0.9) + ((percentageWin) * 0.1));
            numGamesConsideration = "Since there are between 1000 and 2000 games with this opening, its history will be weighted moderately.";
        }
        else if (numGames >= 2000 && numGames < 5000) {
            prediction = ((eloProbability * 0.87) + ((percentageWin) * 0.13));
            numGamesConsideration = "Since there are between 2000 and 5000 games with this opening, its history will be weighted significantly.";
        }
        else if (numGames >= 5000 && numGames < 10000) {
            prediction = ((eloProbability * 0.84) + ((percentageWin) * 0.16));
            numGamesConsideration = "Since there are between 5000 and 10000 games with this opening, its history will be weighted largely, even more than Elo implied probability.";
        }
        else if (numGames >= 10000 && numGames < 15000) {
            prediction = ((eloProbability * 0.82) + ((percentageWin) * 0.18));
            numGamesConsideration = "Since there are between 10000 and 15000 games with this opening, its history will be weighted very largely, significantly more than Elo implied probability.";
        }
        else {
            prediction = ((eloProbability * 0.8) + ((percentageWin) * 0.2));
            numGamesConsideration = "Since there are more than 15000 games with this opening, its history will be weighted to the maximum.";
        }

        if (playerHasPlayedThisBefore == false) {
            Helper.Alert("Based on raw Elo probability, your chance to win is " + eloProbability + "%. However, this opening has history of being used by masters. " +
                    numGamesConsideration + " Taking all of this into account, my prediction is you would have a " + prediction + "% chance to win this game if you play this opening.");
        }

        return;

    }

    public static double GetEloProbability (int ratingDifference) {

        double eloProbability = 0;

        if (ratingDifference >= 800) {
            eloProbability = 0.99;
        }
        else if (ratingDifference >= 750 && ratingDifference < 800) {
            eloProbability = 1.32;
        }
        else if (ratingDifference >= 700 && ratingDifference < 750) {
            eloProbability = 1.75;
        }
        else if (ratingDifference >= 650 && ratingDifference < 700) {
            eloProbability = 2.32;
        }
        else if (ratingDifference >= 600 && ratingDifference < 650) {
            eloProbability = 3.07;
        }
        else if (ratingDifference >= 550 && ratingDifference < 600) {
            eloProbability = 4.05;
        }
        else if (ratingDifference >= 500 && ratingDifference < 550) {
            eloProbability = 5.32;
        }
        else if (ratingDifference >= 450 && ratingDifference < 500) {
            eloProbability = 6.98;
        }
        else if (ratingDifference >= 400 && ratingDifference < 450) {
            eloProbability = 9.09;
        }
        else if (ratingDifference >= 350 && ratingDifference < 400) {
            eloProbability = 11.77;
        }
        else if (ratingDifference >= 300 && ratingDifference < 350) {
            eloProbability = 15.10;
        }
        else if (ratingDifference >= 250 && ratingDifference < 300) {
            eloProbability = 19.17;
        }
        else if (ratingDifference >= 200 && ratingDifference < 250) {
            eloProbability = 24.03;
        }
        else if (ratingDifference >= 150 && ratingDifference < 200) {
            eloProbability = 29.66;
        }
        else if (ratingDifference >= 100 && ratingDifference < 150) {
            eloProbability = 35.99;
        }
        else if (ratingDifference >= 50 && ratingDifference < 100) {
            eloProbability = 42.85;
        }
        else if (ratingDifference >= 0 && ratingDifference < 50) {
            eloProbability = 50.00;
        }
        else if (ratingDifference >= -50 && ratingDifference < 0) {
            eloProbability = 57.15;
        }
        else if (ratingDifference >= -100 && ratingDifference < -50) {
            eloProbability = 64.01;
        }
        else if (ratingDifference >= -150 && ratingDifference < -100) {
            eloProbability = 70.34;
        }
        else if (ratingDifference >= -200 && ratingDifference < -150) {
            eloProbability = 75.97;
        }
        else if (ratingDifference >= -250 && ratingDifference < -200) {
            eloProbability = 80.83;
        }
        else if (ratingDifference >= -300 && ratingDifference < -250) {
            eloProbability = 84.90;
        }
        else if (ratingDifference >= -350 && ratingDifference < -300) {
            eloProbability = 88.23;
        }
        else if (ratingDifference >= -400 && ratingDifference < -350) {
            eloProbability = 90.91;
        }
        else if (ratingDifference >= -450 && ratingDifference < -400) {
            eloProbability = 93.02;
        }
        else if (ratingDifference >= -500 && ratingDifference < -450) {
            eloProbability = 94.68;
        }
        else if (ratingDifference >= -550 && ratingDifference < -500) {
            eloProbability = 95.95;
        }
        else if (ratingDifference >= -600 && ratingDifference < -550) {
            eloProbability = 96.93;
        }
        else if (ratingDifference >= -650 && ratingDifference < -600) {
            eloProbability = 97.68;
        }
        else if (ratingDifference >= -700 && ratingDifference < -650) {
            eloProbability = 98.25;
        }
        else if (ratingDifference >= -750 && ratingDifference < -700) {
            eloProbability = 98.68;
        }
        else if (ratingDifference < -750) {
            eloProbability = 99.01;
        }

        System.out.println(eloProbability);
        return eloProbability;

    }

}


