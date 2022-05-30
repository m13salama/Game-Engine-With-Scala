class Out{
  private var flag =true
  private var board : Array[Array[Int]]= null
  def setBoard(board:Array[Array[Int]])= this.board = board
  def setFlag (flag:Boolean)= this.flag=flag
  def getBoard(): Array[Array[Int]] = this.board
  def getFlag(): Boolean = this.flag
}
