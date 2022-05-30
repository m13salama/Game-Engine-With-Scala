import java.util.Scanner
import java.util.Hashtable
class controllerChess extends Controller {

def start (output:Out,turn :Int,st:String): Out={
    var charachterHash = new Hashtable[Character, Integer]
    init_hash(charachterHash)
    var numboard=output.getBoard()
    var board = setStrBoard(numboard)
    if(st.length!=4){
      output.setFlag(false)
    }
    else { 
      var c_in=charachterHash.get(st.charAt(0))
      var r_in=charachterHash.get(st.charAt(1))
      var c_out=charachterHash.get(st.charAt(2))
      var r_out=charachterHash.get(st.charAt(3))
      if (possible(r_in,c_in,r_out,c_out,turn,board)){
        apply_move(r_in,c_in,r_out,c_out,turn,board)
        output.setFlag(true)
      } else output.setFlag(false)
      numboard = setNumBoard(board)
      output.setBoard(numboard)
    }
    output
  }

  def intialize():Array[Array[Int]]={
    var numboard=Array(
      Array(11,12,13,14,15,13,12,11),
      Array(16,16,16,16,16,16,16,16),
      Array( 0, 0, 0, 0, 0, 0, 0, 0),
      Array( 0, 0, 0, 0, 0, 0, 0, 0),
      Array( 0, 0, 0, 0, 0, 0, 0, 0),
      Array( 0, 0, 0, 0, 0, 0, 0, 0),
      Array( 6, 6, 6, 6, 6, 6, 6, 6),
      Array( 1, 2, 3, 4, 5, 3, 2, 1)
    )
    numboard
  }

  private def apply_move(r_in: Int , c_in: Int , r_out: Int , c_out: Int, turn: Int , board: Array[Array[String]]): Unit = {
    var read = new Scanner(System.in)
    // Perform simple move
    board(r_out)(c_out) = board(r_in)(c_in)
    board(r_in)(c_in) = "."
    if ((turn == 1 && r_out == 7 && board(r_out)(c_out)=="p") || (turn == 2 && r_out == 0 &&board(r_out)(c_out)=="P")) {
      var flag = 1
      var counter: Int = 0
      while (flag !=  0) {
        if (flag == 1)
          printf("enter your promotion piece :\n 1) Queen \t\t\t 2) Rook\n 3) Bishop\t\t\t 4) Knight ");
        else
          printf("Please enter correct choice");
        counter = read.nextInt
        if (counter == 1) {
          board(r_out)(c_out) = "q"
          flag = 0
        }
        else if (turn == 2) {
          board(r_out)(c_out) = "r"
          flag = 0
        }
        else if (counter == 3) {
          board(r_out)(c_out) = "b"
          flag = 0
        }
        else if (counter == 4) {
          board(r_out)(c_out) = "n"
          flag = 0
        }
        else
          flag += 1
      }
      if(turn==2)
        board(r_out)(c_out)=board(r_out)(c_out).toUpperCase()
    }
  }

  private def general_range_validated(r_in: Int , c_in: Int , r_out: Int , c_out: Int , turn: Int , board: Array[Array[String]]): Boolean={
    var result = false
    var v = Math.abs(r_in - r_out)
    var h = Math.abs(c_in- c_out)
    val moving_object_type = board(r_in)(c_in).toLowerCase()
    moving_object_type match {
      case "r" =>
        if ((r_in !=  r_out) && (c_in == c_out) || (r_in == r_out) && (c_in !=  c_out))
          result = true

      case "b" =>
        if (v == h)
          result = true

      case "q" =>
        if (((r_in !=  r_out) && (c_in == c_out)) || ((r_in == r_out) && (c_in !=  c_out)) || (v == h)) result = true

      case "n" =>
        if ((v == 1 && h == 2) || (h == 1 && v == 2)) result = true

      case "k" =>
        if (v <= 1 && h <= 1) result = true

      case "p" =>
        // check if the pawn is moving forward
        var move_forward = false
        if ((r_out>r_in && turn == 2 ) || (r_out<r_in && turn == 1)) move_forward=true
        // check if the pawn can move diagonally
        var move_diagonally = false
        if(board(r_out)(c_out) !=  ".") move_diagonally = true
        // move o!= cell vertically
        if ((v == 1) && (h == 0) && move_forward ) result = true
        // the other is to move o!= cell diagonally in case an enemy is found at destination
        else if ((v == 1) && (h == 1) && move_diagonally) result = true
        // move two cells vertically
        else if (((v == 2) && (h == 0) && move_forward) && (r_in == 1 || r_in == 6)) result=true
    }
    result
  }

  private def no_barriers_found(r_in: Int , c_in: Int , r_out: Int , c_out: Int, turn: Int , board: Array[Array[String]]): Boolean = {
    var x = 0
    var result = true
    var v = 0
    var h = 0
    var next_row = 0
    var next_col = 0
    var j = 0
    val moving_object = board(r_in)(c_in)
    val moving_object_type = moving_object.toLowerCase()
    moving_object_type match { // In case of rook
      case "k"=>
        result = true
      case "n"=>
        result = true
      case "r" =>
        // check moving vertically
        if (c_in == c_out) {
          next_row = Math.min(r_in, r_out) + 1
          var i = next_row
          while ( i < (next_row + Math.abs(r_in - r_out) - 1) && result) {
            if (board(i)(c_in) != ".") result = false
            i += 1
          }
        }
        else { // moving horizontally
          if (r_in == r_out) {
            next_col = Math.min(c_in, c_out) + 1
            var i = next_col
            while (i < (next_col + Math.abs(c_in - c_out) - 1)&& result) {
              if (board(r_in)(i) != ".") result = false
              i += 1
            }
          }
        }

      // In case of bishop
      case "b" =>
        next_row = Math.min(r_in, r_out) + 1
        if ((r_in - r_out) == (c_in - c_out)) {
          j = Math.min(c_in, c_out) + 1
          var i = next_row
          while ( i < next_row + Math.abs(r_in - r_out) - 1) {
            if (board(i)(j) != ".") result = false
            j += 1
            i += 1
          }
        }
        else {
          j = Math.min(c_in, c_out)
          if (j == c_in) j=c_out - 1
          else j= c_in - 1
          var i = next_row
          while ( i < next_row + Math.abs(r_in - r_out) - 1) {
            if ((board(i)(j) != ".")) result = false
            j -= 1
            i += 1
          }
        }

      // In case of Queen
      case "q" =>
        // rook role
        if (c_in == c_out) {
          next_row = Math.min(r_in, r_out) + 1
          var i = next_row
          while ( i < (next_row + Math.abs(r_in - r_out) - 1) && result) {
            if (board(i)(c_in) != ".") result = false
            i += 1
          }
        }
        else if (r_in == r_out) {
          next_col = Math.min(c_in, c_out) + 1
          var i = next_col
          while (i < (next_col + Math.abs(c_in - c_out) - 1)&& result) {
            if (board(r_in)(i) != ".") result = false
            i += 1
          }
        }
        else { // bishop role
          next_row = Math.min(r_in, r_out) + 1
          if ((r_in - r_out) == (c_in - c_out)) {
            j = Math.min(c_in, c_out) + 1
            var i = next_row
            while ( i < next_row + Math.abs(r_in - r_out) - 1) {
              if (board(i)(j) != ".") result = false
              j += 1
              i += 1
            }
          }
          else {
            j = Math.min(c_in, c_out)
            if (j == c_in) j=c_out - 1
            else j= c_in - 1
            var i = next_row
            while ( i < next_row + Math.abs(r_in - r_out) - 1) {
              if ((board(i)(j) != ".")) result = false
              j -= 1
              i += 1
            }
          }
        }

      // In case of pawns
      case "p" =>
        if (moving_object == "p" ) x=1
        else x= -1
        v = Math.abs(r_in - r_out)
        h = Math.abs(c_in - c_out)
        if (v == 2 && h == 0 && ((board(r_in - x)(c_out) != "." )|| (board(r_in - 2*x)(c_out) != "."))  ) result=false
        else if (v == 1 && h == 0 && (board(r_in - x)(c_out) != ".") ) result=false
    }
    if(turn==1 && result){
      if (board(r_out)(c_out)=="k" || board(r_out)(c_out)=="q" || board(r_out)(c_out)=="p" || board(r_out)(c_out)=="b" || board(r_out)(c_out)=="k"||board(r_out)(c_out)=="r")
        result=false
    }
    else if(turn==2 && result) {
      if (board(r_out)(c_out) == "K" || board(r_out)(c_out) == "Q" || board(r_out)(c_out) == "P" || board(r_out)(c_out) == "B" || board(r_out)(c_out) == "K" || board(r_out)(c_out) == "R")
        result=false
    }
    result
  }

  private def check_my_king_safety(r_in: Int , c_in: Int , r_out: Int , c_out: Int, turn: Int , board: Array[Array[String]]) : Boolean ={
    var result = true
    var remove = board(r_out)(c_out)
    var mov = board(r_in)(c_in)
    apply_move(r_in,c_in,r_out,c_out,turn,board)
    var i=0
    var j =0
    var king_r=0
    var king_c=0

    if(turn==1){
      // get place of white king
      while (i < 8){
        j=0
        while (j < 8){
          if (board(i)(j)=="k"){
            king_r=i
            king_c=j
          }
          j+=1
        }
        i+=1
      }
      i=0;j=0
      while (i < 8){
        j=0
        while (j < 8){
          if (board(i)(j)=="Q" || board(i)(j)=="P" || board(i)(j)=="B" || board(i)(j)=="K"||board(i)(j)=="R"||board(i)(j)=="N"){
            if (general_range_validated(i , j , king_r , king_c ,2,board) && no_barriers_found(i , j , king_r , king_c , 2 ,board)) {
              result = false
            }
          }
          j+=1
        }
        i+=1
      }

    }
    else if(turn==2){
      // get place of white king
      while (i < 8){
        j=0
        while (j < 8){
          if (board(i)(j)=="K"){
            king_r=i
            king_c=j
          }
          j+=1
        }
        i+=1
      }
      i=0;j=0
      while (i < 8){
        j=0
        while (j < 8){
          if (board(i)(j)=="q" || board(i)(j)=="p" || board(i)(j)=="b" || board(i)(j)=="k"||board(i)(j)=="r"||board(i)(j)=="n"){
            if (general_range_validated(i , j , king_r , king_c ,1,board) && no_barriers_found(i , j , king_r , king_c,1 ,board)) {
              result = false
            }
          }
          j+=1
        }
        i+=1
      }

    }
    board(r_out)(c_out) =remove
    board(r_in)(c_in) = mov
    result
  }

  private def possible(r_in: Int , c_in: Int , r_out: Int , c_out: Int, turn: Int , board: Array[Array[String]]): Boolean ={
    var result = true
    if (r_in > 7 || r_in<0 || r_out >7 || r_out<0 || c_in> 7 || c_in < 0 || c_out>7 || c_out<0 || board(r_in)(c_in)=="." )
      result = false
    else
      result=general_range_validated(r_in,c_in,r_out,c_out,turn,board) && no_barriers_found(r_in,c_in,r_out,c_out, turn ,board) && check_my_king_safety(r_in,c_in,r_out,c_out,turn,board)

    result ;
  }

  private def setStrBoard(numboard: Array[Array[Int]]): Array[Array[String]] = {
    var board = Array.ofDim[String](8, 8)
    var i = 0
    var j = 0
    while (i < 8) {
      j = 0
      while (j < 8) {
        numboard(i)(j) match {
          case 0 => board(i)(j) = "."
          case 1 => board(i)(j) = "r"
          case 2 => board(i)(j) = "n"
          case 3 => board(i)(j) = "b"
          case 4 => board(i)(j) = "q"
          case 5 => board(i)(j) = "k"
          case 6 => board(i)(j) = "p"
          case 11 => board(i)(j) = "R"
          case 12 => board(i)(j) = "N"
          case 13 => board(i)(j) = "B"
          case 14 => board(i)(j) = "Q"
          case 15 => board(i)(j) = "K"
          case 16 => board(i)(j) = "P"
        }
        j += 1
      }
      i += 1
    }
    board
  }

  private def setNumBoard(board: Array[Array[String]]) = {
    var numboard = Array.ofDim[Int](8, 8)
    var i = 0
    var j = 0
    while (i<8){
      j=0
      while (j<8){
        board(i)(j) match {
          case "." => numboard(i)(j)=0
          case "r" => numboard(i)(j)=1
          case "n" => numboard(i)(j)=2
          case "b" => numboard(i)(j)=3
          case "q" => numboard(i)(j)=4
          case "k" => numboard(i)(j)=5
          case "p" => numboard(i)(j)=6
          case "R" => numboard(i)(j)=11
          case "N" => numboard(i)(j)=12
          case "B" => numboard(i)(j)=13
          case "Q" => numboard(i)(j)=14
          case "K" => numboard(i)(j)=15
          case "P" => numboard(i)(j)=16
        }
        j+=1
      }
      i+=1
    }
    numboard
  }

  def print_2d(board: Array[Array[Int]]): Unit = {
    var i = 0
    var j = 0
    while (i<8){
      j=0
      while (j<8){
        print(board(i)(j))
        print(" ")
        j+=1
      }
      println(" ")
      i+=1
    }
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
}
