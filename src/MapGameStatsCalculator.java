import java.util.ArrayList;
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
  private String playerWithHighestScore = "";
  private Map<Character, Integer> alphabetChars = new HashMap<>();
  private Map<String, List<Integer>> playerScoresFromAllGames = new HashMap<>();
  private List<String> listOfPlayers = new ArrayList<>();

  public MapGameStatsCalculator(Scanner scoreInput) {
    gameCounts = new HashMap<>();
    highScores = new HashMap<>();
    
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
        alphabetizePlayerWithSameScores(name, score);
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
    double playerWithHighestAverage = 0.0;
    
    for (int i = 0; i < listOfPlayers.size(); i++) {
      if (playerWithHighestAverage < getAverageScore(listOfPlayers.get(i))) {
        playerWithHighestAverage = getAverageScore(listOfPlayers.get(i));
      }
    }

    return playerWithHighestScore;
  }

  /**
   * Returns a list of the scores a person has gotten, sorted in ascending order (lowest to highest).
   * 
   * @param person the name of the person to query
   * @return a list of the scores the person received in ascending order
   * @throws NoSuchElementException if the person does not exist in the score data
   */
  @Override
  public List<Integer> sortedScores(String person) {
    // TODO: remove this exception once you have implemented your method!
    throw new UnsupportedOperationException("Unimplemented method 'sortedScores'");

    // Uncomment this and have it as your first line once you remove the UnsupportedOperationException
    //checkPerson(person);
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

  private void alphabetizePlayerWithSameScores(String name, int score) {
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
        }
      } else {
        continue;
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