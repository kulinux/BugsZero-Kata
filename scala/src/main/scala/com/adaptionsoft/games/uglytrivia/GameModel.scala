package com.adaptionsoft.games.uglytrivia

import scala.collection.mutable

class GameModel{
  val questions = mutable.ListBuffer[String]()
  var index = 0

  def append(str: String) = questions.append(str)
  def remove(ind: Int) = questions.remove(ind)
}


case class Player(name: String,
                  var place: Int = 0,
                  var purse: Int = 0,
                  var inPenaltyBox: Boolean = false) {
  override def toString: String = name
}




class Questions {
  val popQuestions = new GameModel()
  val scienceQuestions = new GameModel()
  val sportsQuestions = new GameModel()
  val rockQuestions = new GameModel()

  (0 until 50).foreach { i =>
    popQuestions.append("Pop Question " + i)
    scienceQuestions.append("Science Question " + i)
    sportsQuestions.append("Sports Question " + i)
    rockQuestions.append(createRockQuestion(i))
  }

  def createRockQuestion(index: Int): String = "Rock Question " + index

  def askQuestion(currentCategory: String): Unit = {
    if (currentCategory eq "Pop") println(popQuestions.remove(0))
    if (currentCategory eq "Science") println(scienceQuestions.remove(0))
    if (currentCategory eq "Sports") println(sportsQuestions.remove(0))
    if (currentCategory eq "Rock") println(rockQuestions.remove(0))
  }

}

