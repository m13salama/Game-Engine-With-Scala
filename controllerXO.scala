class controllerXO extends Controller {

  // turn : true --> player 1 , false --> player 2
  //  var turn : Boolean = true  // get from the game engine
  def intialize():Array[Array[Int]]={
    var arr = Array(Array(0, 0, 0),
      Array(0, 0, 0),
      Array(0, 0, 0))
    arr
  }

  def start(state : Out,turn : Int, input : String) : Out= {
    if(input.size != 2) {
      state.setFlag(false)
      println("ERROR! Wrong move")
      return state
    }
    val y = input.charAt(0) match {
      case 'a' | 'A' => 0
      case 'b' | 'B' => 1
      case 'c' | 'C' => 2
      case _ => 3
    }
    val x = input.charAt(1) match {
      case '1' => 2
      case '2' => 1
      case '3' => 0
      case _ => 3
    }

    // wrong move
    if(x == 3 || y==3) {
      println("ERROR! Wrong move")
      return state
    }
// x -> 1           o -> 2
    if(turn == 1 && state.getBoard()(x)(y) != 1 && state.getBoard()(x)(y) != 2) {
      state.getBoard()(x)(y) = 1
      state.setFlag(true)
    }
    else if(turn == 2 && state.getBoard()(x)(y) != 1 && state.getBoard()(x)(y) != 2) {
      state.getBoard()(x)(y) = 2
      state.setFlag(true)
    }
    else {
      println("ERROR! Wrong move")
      state.setFlag(false)
    }
    state
  }

  def print_2d(arr: Array[Array[Int]]): Unit = {
    for (i <- 0 until 3) {
      for (j <- 0 until 3) {
        System.out.print(arr(i)(j) + " ")
      }
      System.out.println()
    }
  }
}
