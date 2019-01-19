package com.adaptionsoft.games.trivia

import java.io.{ByteArrayOutputStream, PrintStream}
import java.util.Random

import com.adaptionsoft.games.trivia.runner.GameRunner
import com.adaptionsoft.games.uglytrivia.Game
import org.scalatest.{FunSpec, Matchers}

import scala.io.Source

class GameSpec extends FunSpec with Matchers {
  describe("Game") {
    it("should should output expected values") {
      val randomizer = new Random(123455)
      val resultStream = new ByteArrayOutputStream

      Console.withOut(new PrintStream(resultStream)) {
        (1 until 15).foreach(_ => GameRunner.playGame(randomizer))
      }

      val expected = Source.fromFile("../java/src/test/java/com/adaptionsoft/games/trivia/GameTest.itsLockedDown.approved.txt").mkString
      val result = resultStream.toString
      result shouldBe expected
    }

    it("Should not allow less than 2") {
      val aGame = new Game
      aGame.add("Chet")

      checkGame(aGame, "lessThanTwo")

    }

    it("Should not allow more than 6") {
      val aGame = new Game
      1 to 7 map(s"Player" + _) foreach { aGame.add }

      checkGame(aGame, "moreThanSix")

    }
  }

  private def checkGame(aGame: Game, file: String) = {
    val randomizer = new Random(123455)
    val resultStream = new ByteArrayOutputStream

    Console.withOut(new PrintStream(resultStream)) {
      GameRunner.playFullGame(randomizer, aGame)
    }

    val expected = Source.fromFile(s"src/test/scala/com/adaptionsoft/games/trivia/GameTest.$file.approved.txt").mkString
    val result = resultStream.toString
    result shouldBe expected
  }
}
