import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class MapGameStatsCalculator implements GameStatsCalculator {

  /**
   * A map of names to # of games completed.
   * 
   * For example if Nupur completed 3 games, Baya completed 5 games, and Xinting completed one game,
   * the map would look something like:
   * 
   * {
   *  "Nupur": 3,
   *  "Baya": 5,
   *  "Xinting": 1
   * }
   */
  private Map<String, Integer> gameCounts;

  // For some waves you will need to add more private instance variables here!
  private Map<String, Integer> highScores;
  private int highestScore = 0;
  private double highestAverage = 0.0;
  private String playerWithHighestScore = "";
  private String playerWithHighestAverage = "";
  private Map<Character, Integer> alphabetChars = new HashMap<>();
  private Map<String, List<Integer>> playerScoresFromAllGames = new HashMap<>();
  private List<String> listOfPlayers = new ArrayList<>();
  private Map<String, List<Integer>> scoreMap;

  public MapGameStatsCalculator(Scanner scoreInput) {
    gameCounts = new HashMap<>();
    highScores = new HashMap<>();
    scoreMap = new HashMap<>();
    
    while(scoreInput.hasNext()) {
      String name = scoreInput.next();
      int score = scoreInput.nextInt();

      // related to Wave 1
      if (!gameCounts.containsKey(name)) {
        gameCounts.put(name, 1);
      } else {
        gameCounts.put(name, gameCounts.get(name) + 1);
      }

      // related to Wave 2
      if (!highScores.containsKey(name)) {
        highScores.put(name, score);
      } else {
        if (score > highScores.get(name)) {
          highScores.put(name, score);
        }
      }

      // related to Wave 3
      if (highestScore < score) {
        highestScore = score;
        playerWithHighestScore = name;
      } else if (highestScore == score) {
        alphabetizePlayersWithSameScores(name, score);
      }
      
      // related to Wave 4
      List<Integer> listOfScores = new ArrayList<>();
      
      if (!playerScoresFromAllGames.containsKey(name)) {
        listOfScores.clear();
        listOfScores.add(score);
        playerScoresFromAllGames.put(name, listOfScores);
      } else {
        playerScoresFromAllGames.get(name).add(score);
      }

      // related to Wave 5
      if (!listOfPlayers.contains(name)) {
        listOfPlayers.add(name);
      }
          
      // related to Wave 6
      if (!scoreMap.containsKey(name)) {
          List<Integer> scores = new ArrayList<>();
          scores.add(score);
          scoreMap.put(name, scores);
      } else {
          scoreMap.get(name).add(score);
      }
    }
  }

  /**
   * Returns the number of games a person has played.
   * 
   * @param person the name of the person to query
   * @return the number of games the person played
   * @throws NoSuchElementException if the person does not exist in the score data
   */
  @Override
  public int gameCount(String person) {
    checkPerson(person);
    return gameCounts.get(person);
  }

  /**
   * Returns the highest score a person has gotten.
   * 
   * @param person the name of the person to query
   * @return the highest score the person received
   * @throws NoSuchElementException if the person does not exist in the score data
   */
  @Override
  public int highScore(String person) {
    checkPerson(person);
    return highScores.get(person);
  }

  /**
   * Returns the name of the person who has the highest score.
   * 
   * If multiple people are tied for the highest score, returns the person whose name
   * appears first lexicographically (alphabetically).
   * 
   * @return the name of the person with the highest score
   * @throws NoSuchElementException if there is no score data
   * 
   * NOTE: Got stuck on how to implement this method based on Wave 3 instructions; 
   * modified and created new instance variables to implement this method
   */
  @Override
  public String highestScorer() {
    checkScoreData();
    return playerWithHighestScore;
  }

  // NOTE: The commented code below is my partner's code for highestScorer(). We both discussed
  // how we'd implement the method, but decided to implement the method our own way.
  // public String highestScorer() {
  //   checkScoreData();
  //   String highestScorer = "";
  //   int highestScore = -100000000;
  //   boolean firstScore = false;
  //   for (String name : highScores.keySet()) {
  //       int score = highScores.get(name);
  //       if (firstScore == false) {
  //           highestScore = score;
  //           highestScorer = name;
  //           firstScore = true;
  //       } else if(highestScore < score) {
  //           highestScore = score;
  //           highestScorer = name;
  //       } else if (highestScore == score) {
  //           if (name.compareTo(highestScorer) < 0) {
  //           highestScorer = name;
  //           }
  //       }
  //   } return highestScorer;
  // }

  /**
   * Returns the average score a person has gotten.
   * 
   * @param person the name of the person to query
   * @return the average score the person received
   * @throws NoSuchElementException if the person does not exist in the score data
   */
  @Override
  public double getAverageScore(String person) {
    checkPerson(person);
    List<Integer> listOfScores = new ArrayList<>();
    listOfScores = playerScoresFromAllGames.get(person);
    int sumOfScores = 0;

    for (int i = 0; i < listOfScores.size(); i++) {
      sumOfScores += listOfScores.get(i);
    }
    
    return 1.0 * sumOfScores / gameCount(person);
  }

  /**
   * Returns the name of the person who has the highest average score.
   * 
   * If multiple people are tied for the highest average score, returns the person whose name
   * appears first lexicographically (alphabetically).
   * 
   * @return the name of the person with the highest average score
   * @throws NoSuchElementException if there is no score data
   */
  @Override
  public String highestAverageScorer() {
    checkScoreData();
    
    for (int i = 0; i < listOfPlayers.size(); i++) {
      if (highestAverage < getAverageScore(listOfPlayers.get(i))) {
        playerWithHighestAverage = listOfPlayers.get(i);
        highestAverage = getAverageScore(listOfPlayers.get(i));
      } else if (highestAverage == getAverageScore(listOfPlayers.get(i))) {
        alphabetizePlayersWithSameAverages(listOfPlayers.get(i), getAverageScore(listOfPlayers.get(i)));
      }
    }

    return playerWithHighestAverage;
  }

  // NOTE: The commented code below is my partner's code for highestAverageScorer(). We both discussed
  // how we'd implement the method, but decided to implement the method our own way.
  //  public String highestAverageScorer() {
  //       checkScoreData();
  //       String highestAverageScorer = "";
  //       double avg = 0;
  //       double highestAvg = 0;
  //       boolean isFirst = false;
  //       for(String name : scoreTotal.keySet()) {
  //           avg = getAverageScore(name);
  //           if (isFirst == false) {
  //               highestAvg = getAverageScore(name);
  //               highestAverageScorer = name;
  //               isFirst = true;
  //           } else if (highestAvg < avg){
  //               highestAvg = avg;
  //               highestAverageScorer = name;
  //           } else if (highestAvg == avg) {
  //               if (name.compareTo(highestAverageScorer) < 0) { 
  //                   highestAverageScorer = name;
  //               }
  //           }
  //       }
  //       return highestAverageScorer;
  //   }

  /**
   * Returns a list of the scores a person has gotten, sorted in ascending order (lowest to highest).
   * 
   * @param person the name of the person to query
   * @return a list of the scores the person received in ascending order
   * @throws NoSuchElementException if the person does not exist in the score data
   */
  @Override
  public List<Integer> sortedScores(String person) {
    checkPerson(person);
    Collections.sort(scoreMap.get(person));
    return scoreMap.get(person);
  }
  
  /**
   * Throws an exception if the given person is null or was not present in the score data.
   * Does nothing if the person is present.
   * 
   * @param person the person to check
   * @throws NullPointerException if the person String is null
   * @throws NoSuchElementException if the person does not appear in the gameCounts
   */
  private void checkPerson(String person) {
    if(person == null) {
      throw new NullPointerException("Cannot query for null person");
    }
    if(!gameCounts.containsKey(person)) {
      throw new NoSuchElementException("Person " + person + " does not exist in the score data");
    }
  }

  /**
   * Throws an exception if there is no score data in gameCounts. Does nothing if score data exists.
   * 
   * @throws NoSuchElementException if there is no score data
   */
  private void checkScoreData() {
    if(gameCounts.isEmpty()) {
      throw new NoSuchElementException("No score data parsed");
    }
  }

  private void alphabetizePlayersWithSameScores(String name, int score) {
    char[] player1 = playerWithHighestScore.toLowerCase().toCharArray();
    char[] player2 = name.toLowerCase().toCharArray();

    int minimumLength = 0;

    if (player1.length > player2.length) {
      minimumLength = player2.length;
    } else if (player1.length < player2.length) {
      minimumLength = player1.length;
    } else {
      minimumLength = player1.length;
    }
    
    assignAlphabetMap();

    for (int i = 0; i < minimumLength; i++) {
      if (player1[i] != player2[i]) {
        if (alphabetChars.get(player1[i]) > alphabetChars.get(player2[i])) {
          highestScore = score;
          playerWithHighestScore = name;
          break;
        } else if (alphabetChars.get(player1[i]) == alphabetChars.get(player2[i])) {
          continue;
        } else {
          break;
        }
      } 
    }
  }

  private void alphabetizePlayersWithSameAverages(String name, double average) {
    char[] player1 = playerWithHighestAverage.toLowerCase().toCharArray();
    char[] player2 = name.toLowerCase().toCharArray();

    int minimumLength = 0;

    if (player1.length > player2.length) {
      minimumLength = player2.length;
    } else if (player1.length < player2.length) {
      minimumLength = player1.length;
    } else {
      minimumLength = player1.length;
    }
    
    assignAlphabetMap();

    for (int i = 0; i < minimumLength; i++) {
      if (player1[i] != player2[i]) {
        if (alphabetChars.get(player1[i]) > alphabetChars.get(player2[i])) {
          highestAverage = average;
          playerWithHighestAverage = name;
          break;
        } else if (alphabetChars.get(player1[i]) == alphabetChars.get(player2[i])) {
          continue;
        } else {
          break;
        }
      } 
    }
  }

  private void assignAlphabetMap() {
    alphabetChars.put('a', 0);
    alphabetChars.put('b', 1);
    alphabetChars.put('c', 2);
    alphabetChars.put('d', 3);
    alphabetChars.put('e', 4);
    alphabetChars.put('f', 5);
    alphabetChars.put('g', 6);
    alphabetChars.put('h', 7);
    alphabetChars.put('i', 8);
    alphabetChars.put('j', 9);
    alphabetChars.put('k', 10);
    alphabetChars.put('l', 11);
    alphabetChars.put('m', 12);
    alphabetChars.put('n', 13);
    alphabetChars.put('o', 14);
    alphabetChars.put('p', 15);
    alphabetChars.put('q', 16);
    alphabetChars.put('r', 17);
    alphabetChars.put('s', 18);
    alphabetChars.put('y', 19);
    alphabetChars.put('u', 20);
    alphabetChars.put('v', 21);
    alphabetChars.put('w', 22);
    alphabetChars.put('x', 23);
    alphabetChars.put('y', 24);
    alphabetChars.put('z', 25);
  }
}