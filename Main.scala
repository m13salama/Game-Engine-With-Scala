object Main {
  def main(args: Array[String]): Unit = {
      var turn = 1
      val controller = new controllerCheckers()
      var board = controller.intialize
      controller.print_2d(board)
      while (true) {
        var output = new Out()
        output.setBoard(board)
        var value = scala.io.StdIn.readLine()
        output = controller.start(output,turn,value)
        if (output.getFlag()) {
          if (turn == 1) {
            turn = 2
          }
          else {
            turn = 1
          }
        }
        else print("ERROR! Enter another move and the turn of player number:  "+ turn+"\n")
        controller.print_2d(output.getBoard())
      }
    }
  }


