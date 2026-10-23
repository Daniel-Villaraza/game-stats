import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class MapGameStatsCalculatorTest {

  // gameCount tests

  @Test
  public void gameCountReturnsNumberOfGamesForPerson() {
    // Arrange
    String scoreData = "Nupur 10\n"
        + "Baya 30\n"
        + "Xinting 25\n"
        + "Nupur 40\n"
        + "Baya 50\n"
        + "Nupur 20\n"
        + "Baya 60\n"
        + "Nupur 30\n";
    GameStatsCalculator calculator = new MapGameStatsCalculator(new Scanner(scoreData));

    // Act
    int actual = calculator.gameCount("Nupur");

    // Assert
    assertEquals(4, actual);
  }

  @Test
  public void gameCountReturnsNumberOfGamesForOnePlayer() {
    // Arrange
    String scoreData = "Daniel 10\n" + "Daniel 20\n" + "Daniel 30\n";
    GameStatsCalculator calculator = new MapGameStatsCalculator(new Scanner(scoreData));
    
    // Actual
    int actual = calculator.gameCount("Daniel");

    // Assert
    assertEquals(3, actual);
  }

  @Test
  public void gameCountThrowsNullPointerExceptionForNullPerson() {
    // Arrange
    String scoreData = "Nupur 10\n"
        + "Baya 30\n"
        + "Xinting 25\n"
        + "Nupur 40\n"
        + "Baya 50\n"
        + "Nupur 20\n"
        + "Baya 60\n"
        + "Nupur 30\n";
    GameStatsCalculator calculator = new MapGameStatsCalculator(new Scanner(scoreData));

    // Act
    Executable act = () -> calculator.gameCount(null);

    // Assert
    assertThrows(NullPointerException.class, act);
  }

  @Test
  public void gameCountThrowsNoSuchElementExceptionForMissingPerson() {
    // Arrange
    String scoreData = "Nupur 10\n"
        + "Baya 30\n"
        + "Xinting 25\n"
        + "Nupur 40\n"
        + "Baya 50\n"
        + "Nupur 20\n"
        + "Baya 60\n"
        + "Nupur 30\n";
    GameStatsCalculator calculator = new MapGameStatsCalculator(new Scanner(scoreData));

    // Act
    Executable act = () -> calculator.gameCount("Rohan");

    // Assert
    assertThrows(NoSuchElementException.class, act);
  }

  // highScore tests

  @Test
  public void highScoreReturnsHighestScoreForPerson() {
    // Arrange
    String scoreData = "Nupur 10\n"
        + "Baya 30\n"
        + "Xinting 25\n"
        + "Nupur 40\n"
        + "Baya 50\n"
        + "Nupur 20\n"
        + "Baya 60\n"
        + "Nupur 30\n";
    GameStatsCalculator calculator = new MapGameStatsCalculator(new Scanner(scoreData));

    // Act
    int actual = calculator.highScore("Nupur");

    // Assert
    assertEquals(40, actual);
  }

  @Test
  public void highScoreReturnsHighestScoreForOnePlayer() {
    // Arrange
    String scoreData = "Daniel 10\n" + "Daniel 30\n" + "Daniel 20\n";
    GameStatsCalculator calculator = new MapGameStatsCalculator(new Scanner(scoreData));

    // Act
    int actual = calculator.highScore("Daniel");

    // Assert
    assertEquals(30, actual);
  }

  @Test
  public void highScoreThrowsNullPointerExceptionForNullPerson() {
    // Arrange
    String scoreData = "Nupur 10\n"
        + "Baya 30\n"
        + "Xinting 25\n"
        + "Nupur 40\n"
        + "Baya 50\n"
        + "Nupur 20\n"
        + "Baya 60\n"
        + "Nupur 30\n";
    GameStatsCalculator calculator = new MapGameStatsCalculator(new Scanner(scoreData));

    // Act
    Executable act = () -> calculator.highScore(null);

    // Assert
    assertThrows(NullPointerException.class, act);
  }

  @Test
  public void highScoreThrowsNoSuchElementExceptionForMissingPerson() {
    // Arrange
    String scoreData = "Nupur 10\n"
        + "Baya 30\n"
        + "Xinting 25\n"
        + "Nupur 40\n"
        + "Baya 50\n"
        + "Nupur 20\n"
        + "Baya 60\n"
        + "Nupur 30\n";
    GameStatsCalculator calculator = new MapGameStatsCalculator(new Scanner(scoreData));

    // Act
    Executable act = () -> calculator.highScore("Rohan");

    // Assert
    assertThrows(NoSuchElementException.class, act);
  }

  // highestScorer tests

  @Test
  public void highestScorerReturnsPersonWithHighestScore() {
    // Arrange
    String scoreData = "Nupur 10\n"
        + "Baya 30\n"
        + "Xinting 25\n"
        + "Nupur 40\n"
        + "Baya 50\n"
        + "Nupur 20\n"
        + "Baya 60\n"
        + "Nupur 30\n";
    GameStatsCalculator calculator = new MapGameStatsCalculator(new Scanner(scoreData));

    // Act
    String actual = calculator.highestScorer();

    // Assert
    assertEquals("Baya", actual);
  }

  @Test
  public void highestScorerReturnsPersonWithHighestScoreForOnePlayer() {
    // Arrange
    String scoreData = "Daniel 10\n" + "Daniel 15\n" + "Daniel 25\n";
    GameStatsCalculator calculator = new MapGameStatsCalculator(new Scanner(scoreData));

    // Act
    String actual = calculator.highestScorer();

    // Assert
    assertEquals("Daniel", actual);
  }

  @Test
  public void highestScorerReturnsPersonWithHighestScoreLexicographically() {
    // Arrange
    String scoreDate = "Daniel 30\n" + "Nupur 30\n" + "Baya 30\n";
    GameStatsCalculator calculator = new MapGameStatsCalculator(new Scanner(scoreDate));

    // Act
    String actual = calculator.highestScorer();

    // Assert
    assertEquals("Baya", actual);
  }

  @Test
  public void highestScorerThrowsNoSuchElementExceptionForEmptyScoreData() {
    // Arrange
    String scoreData = "";
    GameStatsCalculator calculator = new MapGameStatsCalculator(new Scanner(scoreData));

    // Act
    Executable act = () -> calculator.highestScorer();

    // Assert
    assertThrows(NoSuchElementException.class, act);
  }

  // getAverageScore tests

  @Test
  public void getAverageScoreReturnsAverageScoreForPerson() {
    // Arrange
    String scoreData = "Nupur 10\n"
        + "Baya 30\n"
        + "Xinting 25\n"
        + "Nupur 40\n"
        + "Baya 50\n"
        + "Nupur 20\n"
        + "Baya 60\n"
        + "Nupur 30\n";
    GameStatsCalculator calculator = new MapGameStatsCalculator(new Scanner(scoreData));

    // Act
    double actual = calculator.getAverageScore("Nupur");

    // Assert
    assertEquals(25.0, actual, 0.001);
  }

  @Test
  public void getAverageScoreReturnsAverageScoreForOnePerson() {
    // Arrange
    String scoreData = "Daniel 20\n" + "Daniel 20\n" + "Daniel 20\n" + "Daniel 20\n" + "Daniel 20\n";
    GameStatsCalculator calculator = new MapGameStatsCalculator(new Scanner(scoreData));

    // Act
    double actual = calculator.getAverageScore("Daniel");

    // Assert
    assertEquals(20.0, actual);
  }

  @Test
  public void getAverageScoreThrowsNullPointerExceptionForNullPerson() {
    // Arrange
    String scoreData = "Nupur 10\n"
        + "Baya 30\n"
        + "Xinting 25\n"
        + "Nupur 40\n"
        + "Baya 50\n"
        + "Nupur 20\n"
        + "Baya 60\n"
        + "Nupur 30\n";
    GameStatsCalculator calculator = new MapGameStatsCalculator(new Scanner(scoreData));

    // Act
    Executable act = () -> calculator.getAverageScore(null);

    // Assert
    assertThrows(NullPointerException.class, act);
  }

  @Test
  public void getAverageScoreThrowsNoSuchElementExceptionForMissingPerson() {
    // Arrange
    String scoreData = "Nupur 10\n"
        + "Baya 30\n"
        + "Xinting 25\n"
        + "Nupur 40\n"
        + "Baya 50\n"
        + "Nupur 20\n"
        + "Baya 60\n"
        + "Nupur 30\n";
    GameStatsCalculator calculator = new MapGameStatsCalculator(new Scanner(scoreData));

    // Act
    Executable act = () -> calculator.getAverageScore("Rohan");

    // Assert
    assertThrows(NoSuchElementException.class, act);
  }

  // highestAverageScorer tests

  @Test
  public void highestAverageScorerReturnsPersonWithHighestAverageScore() {
    // Arrange
    String scoreData = "Nupur 10\n"
        + "Baya 30\n"
        + "Xinting 25\n"
        + "Nupur 40\n"
        + "Baya 50\n"
        + "Nupur 20\n"
        + "Baya 60\n"
        + "Nupur 30\n";
    GameStatsCalculator calculator = new MapGameStatsCalculator(new Scanner(scoreData));

    // Act
    String actual = calculator.highestAverageScorer();

    // Assert
    assertEquals("Baya", actual);
  }

  @Test
  public void highestAverageScorerThrowsNoSuchElementExceptionForEmptyScoreData() {
    // Arrange
    String scoreData = "";
    GameStatsCalculator calculator = new MapGameStatsCalculator(new Scanner(scoreData));

    // Act
    Executable act = () -> calculator.highestAverageScorer();

    // Assert
    assertThrows(NoSuchElementException.class, act);
  }

  // sortedScores tests

  @Test
  public void sortedScoresReturnsScoresInAscendingOrder() {
    // Arrange
    String scoreData = "Nupur 10\n"
        + "Baya 30\n"
        + "Xinting 25\n"
        + "Nupur 40\n"
        + "Baya 50\n"
        + "Nupur 20\n"
        + "Baya 60\n"
        + "Nupur 30\n";
    GameStatsCalculator calculator = new MapGameStatsCalculator(new Scanner(scoreData));

    // Act
    List<Integer> actual = calculator.sortedScores("Nupur");

    // Assert
    assertEquals(List.of(10, 20, 30, 40), actual);
  }

  @Test
  public void sortedScoresThrowsNullPointerExceptionForNullPerson() {
    // Arrange
    String scoreData = "Nupur 10\n"
        + "Baya 30\n"
        + "Xinting 25\n"
        + "Nupur 40\n"
        + "Baya 50\n"
        + "Nupur 20\n"
        + "Baya 60\n"
        + "Nupur 30\n";
    GameStatsCalculator calculator = new MapGameStatsCalculator(new Scanner(scoreData));

    // Act
    Executable act = () -> calculator.sortedScores(null);

    // Assert
    assertThrows(NullPointerException.class, act);
  }

  @Test
  public void sortedScoresThrowsNoSuchElementExceptionForMissingPerson() {
    // Arrange
    String scoreData = "Nupur 10\n"
        + "Baya 30\n"
        + "Xinting 25\n"
        + "Nupur 40\n"
        + "Baya 50\n"
        + "Nupur 20\n"
        + "Baya 60\n"
        + "Nupur 30\n";
    GameStatsCalculator calculator = new MapGameStatsCalculator(new Scanner(scoreData));

    // Act
    Executable act = () -> calculator.sortedScores("Rohan");

    // Assert
    assertThrows(NoSuchElementException.class, act);
  }

  // Add more tests here!
}