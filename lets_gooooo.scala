
import javax.swing._;
import java.awt._;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import util.control.Breaks._
import java.util.Hashtable

  
class game_engine
{
    //create frame and panels
    var frame: JFrame = new JFrame("GAMOTOPIA");
    var welcome: JPanel = new JPanel();
	var chess: JPanel = new JPanel();
	var xo: JPanel = new JPanel();
	var checkers: JPanel = new JPanel();
	var connect_four: JPanel = new JPanel();

    //load icons
    var chess_icon = new ImageIcon("asserts/chess3.png")
    var xo_icon = new ImageIcon("asserts/xo.png")
    var c4_icon = new ImageIcon("asserts/c4_3.png")
    var checkers_icon = new ImageIcon("asserts/checkers.png")



    def Engine(drawer: (Array[Array[Int]], (String) => Unit) => Unit, controller: (Out, Int, String) => Out, arr_initialize:() => Array[Array[Int]]): Unit={
        var turn =1
        var out = new Out()
        var arr = arr_initialize()

        var str_res:String = null
        def result(str2: String): Unit={
            str_res = str2;
        }

        out.setBoard(arr)
        var str:String = ""
        var x = 1000
        drawer(out.getBoard(), result)
        //game loop
        var timer:Timer = new Timer(30, new ActionListener() {
            def actionPerformed(evt:ActionEvent): Unit = {
                if(str_res != null){
                    out = controller(out, turn, str_res)
                    if (out.getFlag()) {
                        drawer(out.getBoard(), result)
                        if (turn == 1) {
                            turn = 2
                        }
                        else {
                            turn = 1
                        }
                    }else{
                        println("unvalid move ya we74")
                    }
                }
                str_res = null
            }
        });
        timer.start();

    }
    //========================================================================= 
    def Welcome(): Unit={
        //change frame settings
		frame.setResizable(false);
		frame.setBounds(20, 20, 1000, 1000);
		frame.setDefaultCloseOperation(3);
		frame.getContentPane().setLayout(new CardLayout(0, 0));

        //welcome panel
        welcome.setBackground(new Color(200, 160, 120));
        frame.getContentPane().add(welcome, "name_28977430852976");
		welcome.setLayout(null);

        //chess panel
        chess.setBackground(new Color(200, 160, 120));
		frame.getContentPane().add(chess, "name_28981144881058");
		chess.setLayout(null);

        //xo panel
        xo.setBackground(new Color(200, 160, 120));
		frame.getContentPane().add(xo, "name_28985778486006");
		xo.setLayout(null);

        //checkers panel
        checkers.setBackground(new Color(200, 160, 120));
		frame.getContentPane().add(checkers, "name_28981144881058");
		checkers.setLayout(null);

        //connect four panel
        connect_four.setBackground(new Color(200, 160, 120));
		frame.getContentPane().add(connect_four, "name_28981144881058");
		connect_four.setLayout(null);

        //show that panel
        welcome.setVisible(true);
        chess.setVisible(false);
        xo.setVisible(false);
        checkers.setVisible(false);
        connect_four.setVisible(false);
        // welcome.removeAll()
        // welcome.updateUI()


        //welcome to gamotopia labels
        var welcome_txt = new JLabel("WELCOME To")
        var font = new Font("Calibri", Font.BOLD,56);
        welcome_txt.setFont(font)
        var str_width = welcome_txt.getFontMetrics(font).stringWidth(welcome_txt.getText())
        var str_height = welcome_txt.getFontMetrics(font).getHeight()
        welcome_txt.setBounds(frame.getWidth/2-str_width/2, 25, str_width, str_height)
        welcome.add(welcome_txt)
        var gamotopia_txt = new JLabel("GAMOTOPIA")
        gamotopia_txt.setFont(font)
        str_width = gamotopia_txt.getFontMetrics(font).stringWidth(gamotopia_txt.getText())
        var str_height2 = gamotopia_txt.getFontMetrics(font).getHeight()
        gamotopia_txt.setBounds(frame.getWidth/2-str_width/2, 25+str_height, str_width, str_height2)
        welcome.add(gamotopia_txt)
        var choose_txt = new JLabel("please choose a game")
        font = new Font("Calibri", Font.PLAIN,32);
        var myColor:Color = new Color(255,255,255);
        choose_txt.setForeground(myColor);
        choose_txt.setFont(font)
        str_width = choose_txt.getFontMetrics(font).stringWidth(choose_txt.getText())
        var str_height3 = choose_txt.getFontMetrics(font).getHeight()
        choose_txt.setBounds(frame.getWidth/2-str_width/2, 25+str_height2+str_height, str_width, str_height3)
        welcome.add(choose_txt)

        //x-o image and button
        var xo_resized = xo_icon.getImage().getScaledInstance(450,236,java.awt.Image.SCALE_SMOOTH)
        xo_icon = new ImageIcon(xo_resized)
        var display_xo = new JLabel("")
        var height_xo = 50+str_height+str_height2+str_height3
        display_xo.setBounds(25,height_xo,450,236)
        display_xo.setIcon(xo_icon) 
        welcome.add(display_xo)
        var b_xo: JButton =new JButton("X-O");
        b_xo.setBounds(170,height_xo+265,160,60);
        b_xo.addActionListener(new ActionListener {
        override def actionPerformed(e: ActionEvent): Unit = {
			var obj_xo = new controllerXO()
            Engine(Xo_drawer, obj_xo.start, obj_xo.intialize)
        } })
        welcome.add(b_xo);

        //chess image and button
        var chess_resized = chess_icon.getImage().getScaledInstance(450,450,java.awt.Image.SCALE_SMOOTH)
        chess_icon = new ImageIcon(chess_resized)
        var display_chess = new JLabel("")
        var height_chess = height_xo+260
        display_chess.setBounds(25,height_chess,450,450)
        display_chess.setIcon(chess_icon) 
        welcome.add(display_chess)
        var b_chess=new JButton("CHESS");
        b_chess.setBounds(170,height_chess+400,160,60);
        b_chess.addActionListener(new ActionListener {
        override def actionPerformed(e: ActionEvent): Unit = {
			var obj_chess = new controllerChess()
            Engine(Chess_drawer, obj_chess.start, obj_chess.intialize)        
        } })

        welcome.add(b_chess);     

        //connect_4 image and button
        var c4_resized = c4_icon.getImage().getScaledInstance(275,275,java.awt.Image.SCALE_SMOOTH)
        c4_icon = new ImageIcon(c4_resized)
        var display_c4 = new JLabel("")
        var height_c4 = 50+str_height+str_height2+str_height3
        display_c4.setBounds(600,height_c4-20,275,275)
        display_c4.setIcon(c4_icon) 
        welcome.add(display_c4)
        var b_c4=new JButton("CONNECT 4");
        b_c4.setBounds(660,height_c4+265,160,60); 
        b_c4.addActionListener(new ActionListener {
        override def actionPerformed(e: ActionEvent): Unit = {
			var obj_c4 = new controllerConnect4()
            Engine(Connect_four_drawer, obj_c4.start, obj_c4.intialize)
        } })
        welcome.add(b_c4);

        //checkers image and button
        var checkers_resized = checkers_icon.getImage().getScaledInstance(450,450,java.awt.Image.SCALE_SMOOTH)
        checkers_icon = new ImageIcon(checkers_resized)
        var display_checkers = new JLabel("")
        var height_checkers = height_xo+275
        display_checkers.setBounds(550,height_checkers,450,450)
        display_checkers.setIcon(checkers_icon) 
        welcome.add(display_checkers)
        var b_checkers=new JButton("CHECKERS");
        b_checkers.setBounds(660,height_checkers+400,160,60); 
        b_checkers.addActionListener(new ActionListener {
        override def actionPerformed(e: ActionEvent): Unit = {
			var obj_checkers = new controllerCheckers()
            Engine(Cheeckers_drawer, obj_checkers.start,obj_checkers.intialize)
        } })
        welcome.add(b_checkers); 

        frame.setVisible(true);

    }
    //======================================================================================================
    def Chess_drawer(arr: Array[Array[Int]], inp: (String) => Unit): Unit={
        welcome.setVisible(false);
        chess.setVisible(true);
        chess.removeAll()
        chess.updateUI()

        val hash = new Hashtable[Integer, String]
        hash.put(1, "asserts/rook.png")
        hash.put(2, "asserts/knight.png")
        hash.put(3, "asserts/bishop_w.png")
        hash.put(4, "asserts/king.png")
        hash.put(5, "asserts/queen.png")
        hash.put(6, "asserts/pawn_w.png")
        hash.put(11, "asserts/rook_b.png")
        hash.put(12, "asserts/knight_b.png")
        hash.put(13, "asserts/bishop_b.png")
        hash.put(14, "asserts/king_b.png")
        hash.put(15, "asserts/queen_b.png")
        hash.put(16, "asserts/pawn_b.png")

        var welcome_txt = new JLabel("WELCOME To CHESS")
        var font = new Font("Calibri", Font.BOLD,56);
        welcome_txt.setFont(font)
        var str_width = welcome_txt.getFontMetrics(font).stringWidth(welcome_txt.getText())
        var str_height = welcome_txt.getFontMetrics(font).getHeight()
        welcome_txt.setBounds(frame.getWidth/2-str_width/2, 25, str_width, str_height)
        chess.add(welcome_txt)
        var board = new JPanel(){
            override def paintComponent( g: Graphics): Unit={
                var x: Int = 0
                var y: Int = 0
                var z: Int = 0
                var w: Int = 0
                this.setBackground(Color.darkGray)
                for (i <- 0 to 7) {
                    for (j <- 0 to 7) {
                        if (((i % 2 == 0) && (j % 2 == 0)) || ((i % 2 != 0) && (j % 2 != 0))) {
                            g.setColor(new Color(231,187,156))
                            g.fill3DRect(x, y, 100, 100, true)
                        } else {
                            g.setColor(new Color(143,86,58))
                            g.fill3DRect(x, y, 100, 100, true)
                        }
                        x += 100
                    }
                    x = 0; y += 100
                }
            }
        }
        board.setLayout(null);
        board.setBounds(100,100,800,800)
        chess.add(board)

        var x:Int =0
        var y:Int =0
        for(i <- 0 to 7){
            for (j <- 0 to 7){
                if(arr(i)(j) != 0){
                    var icon = new ImageIcon(hash.get(arr(i)(j)))
                    var resized = icon.getImage().getScaledInstance(100,100,java.awt.Image.SCALE_SMOOTH)
                    icon = new ImageIcon(resized)
                    var display = new JLabel("")
                    display.setBounds(x,y,100,100)
                    display.setIcon(icon) 
                    board.add(display)
                }
                x+=100
            }
            y+=100
            x=0
        }

        //button and input box
        var text1:JTextField = new JTextField();
        var label_enter = new JLabel("Please enter a move");
        label_enter.setBounds(100,905,400,60)
        font = new Font("Calibri", Font.BOLD,26);
        label_enter.setFont(font)
        chess.add(label_enter)
        text1.setBounds(450,905,200,60);
        chess.add(text1)

        var str:String = null
        var button1:JButton = new JButton("Play");
        button1.setBounds(700,905,150,60)
        button1.addActionListener(new ActionListener {
        override def actionPerformed(e: ActionEvent): Unit = {
            str = text1.getText();
            inp(str)
        } })
        chess.add(button1)
    }
    //=============================================================================================================================
    def Cheeckers_drawer(arr: Array[Array[Int]], inp: (String) => Unit): Unit={
        welcome.setVisible(false);
        checkers.setVisible(true);
        checkers.removeAll()
        checkers.updateUI()

        //show welcome message
        var welcome_txt = new JLabel("WELCOME To CHECKERS")
        var font = new Font("Calibri", Font.BOLD,56);
        welcome_txt.setFont(font)
        var str_width = welcome_txt.getFontMetrics(font).stringWidth(welcome_txt.getText())
        var str_height = welcome_txt.getFontMetrics(font).getHeight()
        welcome_txt.setBounds(frame.getWidth/2-str_width/2, 25, str_width, str_height)
        checkers.add(welcome_txt)

        //draw the board
        var board = new JPanel(){
            override def paintComponent( g: Graphics): Unit={
                var x: Int = 0
                var y: Int = 0
                var z: Int = 0
                var w: Int = 0
                for (i <- 0 to 7) {
                    for (j <- 0 to 7) {
                    if (((i % 2 == 0) && (j % 2 == 0)) || ((i % 2 != 0) && (j % 2 != 0))) {
                        g.setColor(new Color(231,187,156))
                        g.fill3DRect(x, y, 100, 100, true)
                    } else {
                        g.setColor(new Color(143,86,58))
                        g.fill3DRect(x, y, 100, 100, true)
                        z = (i * 100) + 5
                        w = (j * 100) + 5
                        if (arr(i)(j) == 1) {
                        g.setColor(Color.white)
                        g.fillOval(w, z, 90, 90)
                        } else if (arr(i)(j) == 2) {
                        g.setColor(Color.BLACK)
                        g.fillOval(w, z, 90, 90)
                        } else if (arr(i)(j) == 3) {
                        g.setColor(Color.white)
                        g.fillOval(w, z, 90, 90)
                        g.setColor(Color.BLACK)
                        g.drawLine(w+12, z+12, w+78, z+78)

                        } else if (arr(i)(j) == 4) {
                        g.setColor(Color.BLACK)
                        g.fillOval(w, z, 90, 90)
                        g.setColor(Color.white)
                        g.drawLine(w+12, z+12, w+78, z+78)
                        }
                    }
                    x += 100
                    }
                    x = 0; y += 100
                }

            }
        }
        board.setBounds(100,100,800,800)
        checkers.add(board)

        //button and input box
        var text1:JTextField = new JTextField();
        var label_enter = new JLabel("Please enter a move");
        label_enter.setBounds(100,905,400,60)
        font = new Font("Calibri", Font.BOLD,26);
        label_enter.setFont(font)
        checkers.add(label_enter)
        text1.setBounds(450,905,200,60);
        checkers.add(text1)

        var str:String = null
        var button1:JButton = new JButton("Play");
        button1.setBounds(700,905,150,60)
        button1.addActionListener(new ActionListener {
        override def actionPerformed(e: ActionEvent): Unit = {
            str = text1.getText();
            inp(str)
        } })
        checkers.add(button1)
    }
    //==============================================================================================================
    def Xo_drawer(arr: Array[Array[Int]], inp: (String) => Unit): Unit={
        welcome.setVisible(false);
        xo.setVisible(true);
        xo.removeAll()
        xo.updateUI()

        //show welcome message
        var welcome_txt = new JLabel("WELCOME To XO")
        var font = new Font("Calibri", Font.BOLD,56);
        welcome_txt.setFont(font)
        var str_width = welcome_txt.getFontMetrics(font).stringWidth(welcome_txt.getText())
        var str_height = welcome_txt.getFontMetrics(font).getHeight()
        welcome_txt.setBounds(frame.getWidth/2-str_width/2, 25, str_width, str_height)
        xo.add(welcome_txt)

        val hash = new Hashtable[Integer, String]
        hash.put(1, "asserts/x1.png")
        hash.put(2, "asserts/o1.png")
        
        var board = new JPanel(){
            override def paintComponent( g: Graphics): Unit={
                g.setColor(Color.WHITE)
                g.drawLine(0,0,800,0)
                g.drawLine(0,265,800,265)
                g.drawLine(0,530,800,530)
                g.drawLine(0,798,798,798)
                g.drawLine(0,0,0,800)
                g.drawLine(265,0,265,800)
                g.drawLine(530,0,530,800)
                g.drawLine(798,0,798,800)
        }
    }
        board.setLayout(null);
        board.setBounds(100,100,800,800)
        xo.add(board)
        var x = 0
        var y =0
        for (i <- 0 to 2) {
            for (j <- 0 to 2) {
                var icon = new ImageIcon(hash.get(arr(i)(j)))
                var resized = icon.getImage().getScaledInstance(250,250,java.awt.Image.SCALE_SMOOTH)
                icon = new ImageIcon(resized)
                var display = new JLabel("")
                display.setBounds(x,y,250,250)
                display.setIcon(icon) 
                board.add(display)
                x += 265
            }
            y+= 265
            x =0
        }
        //button and input box
        var text1:JTextField = new JTextField();
        var label_enter = new JLabel("Please enter a move");
        label_enter.setBounds(100,905,400,60)
        font = new Font("Calibri", Font.BOLD,26);
        label_enter.setFont(font)
        xo.add(label_enter)
        text1.setBounds(450,905,200,60);
        xo.add(text1)

        var str:String = null
        var button1:JButton = new JButton("Play");
        button1.setBounds(700,905,150,60)
        button1.addActionListener(new ActionListener {
        override def actionPerformed(e: ActionEvent): Unit = {
            str = text1.getText();
            inp(str)
        } })
        xo.add(button1)
}
    //===================================================================================================================
    def Connect_four_drawer(arr: Array[Array[Int]], inp: (String) => Unit): Unit={
        welcome.setVisible(false);
        connect_four.setVisible(true);
        connect_four.removeAll()
        connect_four.updateUI()

        //show welcome message
        var welcome_txt = new JLabel("WELCOME To CONNECT4")
        var font = new Font("Calibri", Font.BOLD,56);
        welcome_txt.setFont(font)
        var str_width = welcome_txt.getFontMetrics(font).stringWidth(welcome_txt.getText())
        var str_height = welcome_txt.getFontMetrics(font).getHeight()
        welcome_txt.setBounds(frame.getWidth/2-str_width/2, 25, str_width, str_height)
        connect_four.add(welcome_txt)

        var board = new JPanel(){
            override def paintComponent( g: Graphics): Unit={
                var x:Int = 50
                var y:Int = 100
                this.setBackground(Color.BLUE)
                for( i <- 0 to 5){
                for( j <- 0 to 6) {

                    if (arr(i)(j) == 0) {
                    g.setColor(Color.white)
                    } else if (arr(i)(j) == 1) {
                    g.setColor(Color.RED)
                    } else {
                    g.setColor(Color.YELLOW)
                    }
                    g.fillOval(x,y,90,90)
                    x += 95
                }
                x = 50
                y += 95
                }
                }
            }
        board.setLayout(null);
        board.setBounds(100,100,800,800)
        connect_four.add(board)

        //button and input box 
        var text1:JTextField = new JTextField();
        var label_enter = new JLabel("Please enter a move");
        label_enter.setBounds(100,850,400,60)
        font = new Font("Calibri", Font.BOLD,26);
        label_enter.setFont(font)
        connect_four.add(label_enter)
        text1.setBounds(450,850,200,60);
        connect_four.add(text1)

        var str:String = null
        var button1:JButton = new JButton("Play");
        button1.setBounds(700,850,150,60)
        button1.addActionListener(new ActionListener {
        override def actionPerformed(e: ActionEvent): Unit = {
            str = text1.getText();
            inp(str)
        } })

        connect_four.add(button1)
    }



}
object Main1
{
     
    // Main method
    def main(args: Array[String]): Unit={
        // Class object
        var obj = new game_engine();
        obj.Welcome()
    }
}
