package com.adaptionsoft.games.uglytrivia

import scala.collection.mutable

case class Player(name: String,
                  var place: Int = 0,
                  var purse: Int = 0,
                  var inPenaltyBox: Boolean = false) {
  override def toString: String = name
}

class Game() {

  val players = mutable.ListBuffer[Player]()


  val popQuestions = mutable.ListBuffer[String]()
  val scienceQuestions = mutable.ListBuffer[String]()
  val sportsQuestions = mutable.ListBuffer[String]()
  val rockQuestions = mutable.ListBuffer[String]()
  var currentPlayer = 0
  var isGettingOutOfPenaltyBox: Boolean = false

  (0 until 50).foreach { i =>
    popQuestions.append("Pop Question " + i)
    scienceQuestions.append("Science Question " + i)
    sportsQuestions.append("Sports Question " + i)
    rockQuestions.append(createRockQuestion(i))
  }

  def createRockQuestion(index: Int): String = "Rock Question " + index

  def isPlayable: Boolean = howManyPlayers >= 2 && howManyPlayers <= 6

  def add(playerName: String): Boolean = {
    players.append(Player(name = playerName))
    println(playerName + " was added")
    println("They are player number " + players.size)
    true
  }

  def currPlayer = players(currentPlayer)

  def howManyPlayers: Int = players.size

  def roll(roll: Int): Unit = {
    if(!isPlayable) {
      println("Not playable")
      return
    }
    println(players(currentPlayer) + " is the current player")
    println("They have rolled a " + roll)
    if (currPlayer.inPenaltyBox) if (roll % 2 != 0) {
      isGettingOutOfPenaltyBox = true
      println(players(currentPlayer) + " is getting out of the penalty box")
      movePlayerAndAskQuestion(roll)
    } else {
      println(players(currentPlayer) + " is not getting out of the penalty box")
      isGettingOutOfPenaltyBox = false
    } else
      movePlayerAndAskQuestion(roll)
  }

  private def movePlayerAndAskQuestion(roll: Int): Unit = {
    currPlayer.place = currPlayer.place + roll
    if (currPlayer.place > 11) currPlayer.place = currPlayer.place - 12
    println(players(currentPlayer) + "'s new location is " + currPlayer.place)
    println("The category is " + currentCategory)
    askQuestion()
  }

  private def askQuestion(): Unit = {
    if (currentCategory eq "Pop") println(popQuestions.remove(0))
    if (currentCategory eq "Science") println(scienceQuestions.remove(0))
    if (currentCategory eq "Sports") println(sportsQuestions.remove(0))
    if (currentCategory eq "Rock") println(rockQuestions.remove(0))
  }

  private def currentCategory: String = {
    if (currPlayer.place == 0) return "Pop"
    if (currPlayer.place == 4) return "Pop"
    if (currPlayer.place == 8) return "Pop"
    if (currPlayer.place == 1) return "Science"
    if (currPlayer.place == 5) return "Science"
    if (currPlayer.place == 9) return "Science"
    if (currPlayer.place == 2) return "Sports"
    if (currPlayer.place == 6) return "Sports"
    if (currPlayer.place == 10) return "Sports"
    "Rock"
  }

  def wasCorrectlyAnswered: Boolean = {

    if(!isPlayable) {
      println("Not playable")
      return false
    }
    if (currPlayer.inPenaltyBox) {
      if (isGettingOutOfPenaltyBox) {
        println("Answer was correct!!!!")
        currentPlayer += 1
        if (currentPlayer == players.size) currentPlayer = 0
        currPlayer.purse += 1
        println(players(currentPlayer) + " now has " + currPlayer.purse + " Gold Coins.")
        val winner = didPlayerWin
        winner
      } else {
        currentPlayer += 1
        if (currentPlayer == players.size) currentPlayer = 0
        true
      }
    } else {
      println("Answer was corrent!!!!")
      currPlayer.purse += 1
      println(players(currentPlayer) + " now has " + currPlayer.purse + " Gold Coins.")
      val winner = didPlayerWin
      currentPlayer += 1
      if (currentPlayer == players.size) currentPlayer = 0
      winner
    }
  }

  def wrongAnswer: Boolean = {
    if(!isPlayable) {
      println("Not playable")
      return false
    }
    println("Question was incorrectly answered")
    println(players(currentPlayer) + " was sent to the penalty box")
    currPlayer.inPenaltyBox = true
    currentPlayer += 1
    if (currentPlayer == players.size) currentPlayer = 0
    true
  }


  private def didPlayerWin: Boolean = !(currPlayer.purse == 6)
}
