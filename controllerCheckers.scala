
import java.util.Hashtable

class controllerCheckers extends Controller {
  def intialize : Array[Array[Int]] = {
    val arr  = Array(Array(0, 2, 0, 2, 0, 2, 0, 2),
                     Array(2, 0, 2, 0, 2, 0, 2, 0),
                     Array(0, 2, 0, 2, 0, 2, 0, 2),
                     Array(0, 0, 0, 0, 0, 0, 0, 0),
                     Array(0, 0, 0, 0, 0, 0, 0, 0),
                     Array(1, 0, 1, 0, 1, 0, 1, 0),
                     Array(0, 1, 0, 1, 0, 1, 0, 1),
                     Array(1, 0, 1, 0, 1, 0, 1, 0))
    arr
  }

  private def init_hash(charachterHash: Hashtable[Character, Integer])={
    charachterHash.put('a', 0)
    charachterHash.put('b', 1)
    charachterHash.put('c', 2)
    charachterHash.put('d', 3)
    charachterHash.put('e', 4)
    charachterHash.put('f', 5)
    charachterHash.put('g', 6)
    charachterHash.put('h', 7)
    charachterHash.put('8', 0)
    charachterHash.put('7', 1)
    charachterHash.put('6', 2)
    charachterHash.put('5', 3)
    charachterHash.put('4', 4)
    charachterHash.put('3', 5)
    charachterHash.put('2', 6)
    charachterHash.put('1', 7)

  }

  def start(output: Out,turn :Int,in:String) : Out = {
    var board = output.getBoard()
    var charachterHash = new Hashtable[Character, Integer]
    init_hash(charachterHash)
    try {
    val FromCol = charachterHash.get(in.charAt(0))
    val FromRow = charachterHash.get(in.charAt(1))
    val ToCol = charachterHash.get(in.charAt(2))
    val ToRow = charachterHash.get(in.charAt(3))
      if (turn == 1) {
        if (board(FromRow)(FromCol) == 1 && !detectKilling(output,turn) && board(ToRow)(ToCol) == 0) {
          if ((FromRow - ToRow) == 1 && Math.abs(FromCol - ToCol) == 1 && FromRow > ToRow) {
            board(FromRow)(FromCol) = 0
            if (ToRow == 0)
              board(ToRow)(ToCol) = 3
            else
              board(ToRow)(ToCol) = 1
          }
          else {
            output.setFlag(false)
            return output
          }
        }
        else if (board(FromRow)(FromCol) == 3 && !detectKilling(output,turn) && board(ToRow)(ToCol) == 0) {
          if (Math.abs(FromRow - ToRow) == 1 && Math.abs(FromCol - ToCol) == 1) {
            board(FromRow)(FromCol) = 0
            board(ToRow)(ToCol) = 3
          }
          else {
            output.setFlag(false)
            return output
          }
        }
        else if ((board(FromRow)(FromCol) != 1 && board(FromRow)(FromCol) != 3) || board(ToRow)(ToCol) != 0) {
          output.setFlag(false)
          return output
        }
        else if (detectKilling(output,turn)) {
          var killRow = (FromRow + ToRow) / 2
          var killCol = (FromCol + ToCol) / 2
          if ((Math.abs(FromRow - ToRow) == 2 && Math.abs(FromCol - ToCol) == 2) && board(ToRow)(ToCol) == 0 &&
            (board(killRow)(killCol) == 2 || board(killRow)(killCol) == 4)) {
            if (board(FromRow)(FromCol) == 1 && FromRow > ToRow) {
              board(FromRow)(FromCol) = 0
              board(killRow)(killCol) = 0
              if (ToRow == 0)
                board(ToRow)(ToCol) = 3
              else
                board(ToRow)(ToCol) = 1
            }
            else if (board(FromRow)(FromCol) == 3) {
              board(FromRow)(FromCol) = 0
              board(ToRow)(ToCol) = 3
              board(killRow)(killCol) = 0
            }
          }
          else {
            output.setFlag(false)
            return output
          }
        }
      }
      else if (turn == 2) {
        if (board(FromRow)(FromCol) == turn && !detectKilling(output,turn) && board(ToRow)(ToCol) == 0) {
          if ((ToRow - FromRow) == 1 && Math.abs(FromCol - ToCol) == 1 && ToRow > FromRow) {
            board(FromRow)(FromCol) = 0
            if (ToRow == 7) {
              board(ToRow)(ToCol) = 4
            }
            else
              board(ToRow)(ToCol) = 2
          }
          else {
            output.setFlag(false)
            return output
          }
        }
        else if (board(FromRow)(FromCol) == 4 && !detectKilling(output,turn) && board(ToRow)(ToCol) == 0) {
          if (Math.abs(FromRow - ToRow) == 1 && Math.abs(FromCol - ToCol) == 1) {
            board(FromRow)(FromCol) = 0
            board(ToRow)(ToCol) = 4
          }
          else {
            output.setFlag(false)
            return output
          }
        }
        else if (board(FromRow)(FromCol) != 2 && board(FromRow)(FromCol) != 4 || (board(ToRow)(ToCol) != 0)) {
          output.setFlag(false)
          return output
        }
        else if (detectKilling(output,turn)) {
          var killRow = (FromRow + ToRow) / 2
          var killCol = (FromCol + ToCol) / 2
          if ((Math.abs(FromRow - ToRow) == 2 && Math.abs(FromCol - ToCol) == 2) && board(ToRow)(ToCol) == 0 &&
            (board(killRow)(killCol) == 1 || board(killRow)(killCol) == 3)) {
            if (board(FromRow)(FromCol) == 2 && FromRow < ToRow) {
              board(FromRow)(FromCol) = 0
              board(killRow)(killCol) = 0
              if (ToRow == 7)
                board(ToRow)(ToCol) = 4
              else
                board(ToRow)(ToCol) = 2
            }
            else if (board(FromRow)(FromCol) == 4) {
              board(FromRow)(FromCol) = 0
              board(ToRow)(ToCol) = 4
              board(killRow)(killCol) = 0
            }
            else {
              output.setFlag(false)
              return output
            }
          }
          else {
            output.setFlag(false)
            return output
          }
        }
      }
    }
    catch {
      case e: Exception => {
        output.setFlag(false)
        return output
      }
    }
    output.setFlag(true)
    return output
  }

  def detectKilling(output: Out,turn :Int):Boolean = {
    if(turn ==1) {
      for (i <- 0 until 8) {
        for (j <- 0 until 8) {
          if ((output.getBoard()(i)(j) == 1 || output.getBoard()(i)(j) ==3) && (i-2)>=0 && (j+2) <8) {
            if ((output.getBoard()(i - 1)(j + 1) == 2 || output.getBoard()(i - 1)(j + 1) == 4) && output.getBoard()(i - 2)(j + 2) == 0) {
              return true
            }
          }

          if((output.getBoard()(i)(j) == 1 || output.getBoard()(i)(j) ==3)  && (i-2)>=0 && (j-2) >=0){
            if((output.getBoard()(i - 1)(j - 1) == 2 || output.getBoard()(i - 1)(j - 1) == 4) && output.getBoard()(i-2)(j-2) == 0) {
              return true
            }
          }

          if(output.getBoard()(i)(j) ==3  && (i+2)<8 && (j-2) >=0){
            if((output.getBoard()(i + 1)(j - 1) == 2 || output.getBoard()(i + 1)(j - 1) == 4) && output.getBoard()(i+2)(j-2) == 0) {
              return true
            }
          }

          if(output.getBoard()(i)(j) ==3  && (i+2)<8 && (j+2) <8){
            if((output.getBoard()(i + 1)(j + 1) == 2 || output.getBoard()(i + 1)(j + 1) == 4) && output.getBoard()(i+2)(j+2) == 0) {
              return true
            }
          }

        }
      }
    }
    else if(turn == 2){
      for (i <- 0 until 8) {
        for (j <- 0 until 8) {

          if (output.getBoard()(i)(j) == 4  && (i-2)>=0 && (j+2) <8) {
            if ((output.getBoard()(i - 1)(j + 1) == 1 || output.getBoard()(i - 1)(j + 1) == 3) && output.getBoard()(i - 2)(j + 2) == 0) {
              return true
            }
          }

          if(output.getBoard()(i)(j) == 4  && (i-2)>=0 && (j-2) >=0){
            if((output.getBoard()(i - 1)(j - 1) == 1 || output.getBoard()(i - 1)(j - 1) == 3) && output.getBoard()(i-2)(j-2) == 0) {
              return true
            }
          }

          if((output.getBoard()(i)(j) ==2||output.getBoard()(i)(j) ==4)  && (i+2)<8 && (j-2) >=0){
            if((output.getBoard()(i + 1)(j - 1) == 1 || output.getBoard()(i + 1)(j - 1) == 3) && output.getBoard()(i+2)(j-2) == 0) {
              return true
            }
          }

          if((output.getBoard()(i)(j) ==2 || output.getBoard()(i)(j) ==4) && (i+2)<8 && (j+2) <8){
            if((output.getBoard()(i + 1)(j + 1) == 1 || output.getBoard()(i + 1)(j + 1) == 3) && output.getBoard()(i+2)(j+2) == 0) {
              return true
            }
          }
        }
      }
    }
     false
  }

  def print_2d(arr: Array[Array[Int]]): Unit = {
    var counter = 8
    for (i <- 0 until 8) {
      print(counter + "   ")
      counter -= 1
      for (j <- 0 until 8) {
        System.out.print(arr(i)(j) + " ")
      }
      System.out.println()
    }
    print("\n")
    print("    a b c d e f g h")
    print("\n")
  }
}
