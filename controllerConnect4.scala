
import java.util.Hashtable
class controllerConnect4 extends Controller {

  def intialize():Array[Array[Int]]={
  var arr = Array(Array(0, 0, 0, 0, 0, 0, 0),
                  Array(0, 0, 0, 0, 0, 0, 0),
                  Array(0, 0, 0, 0, 0, 0, 0),
                  Array(0, 0, 0, 0, 0, 0, 0),
                  Array(0, 0, 0, 0, 0, 0, 0),
                  Array(0, 0, 0, 0, 0, 0, 0))
  arr
}

  private def hash_init(hash: Hashtable[String, Integer]): Unit ={
    hash.put("a", 0)
    hash.put("b", 1)
    hash.put("c", 2)
    hash.put("d", 3)
    hash.put("e", 4)
    hash.put("f", 5)
    hash.put("g", 6)
  }

  def start(output: Out,turn :Int,in:String) : Out ={
    val hash = new Hashtable[String, Integer]
    hash_init(hash)
    val col = hash.get(in)
    if(col == null) {
      output.setFlag(false)
      return output
    }
    for (i <- 5 to 0 by -1) {
      if (output.getBoard()(i)(col) == 0) {
        output.getBoard()(i)(col) = turn
        output.setFlag(true)
        return output
      }
    }
    output.setFlag(false)
    output
  }

  def print_2d(arr: Array[Array[Int]]): Unit = {
    for (i <- 0 until 6) {
      for (j <- 0 until 7) {
        System.out.print(arr(i)(j) + " ")
      }
      System.out.println()
    }
  }
}