package sudoku;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.lang.*;

public class Main 
{   	
  public static Integer[][] GenerateSudoku(Integer number){
    Integer[][] sudoku;
    if(number == 1){
      sudoku = new Integer[][]{
        {0,0,0,0,1,3,5,0,0},
        {0,0,1,0,0,0,6,9,0},
        {0,0,0,0,6,0,0,8,0},
        {0,0,9,0,8,0,0,2,3},
        {0,0,0,5,0,2,0,0,0},
        {6,2,0,0,4,0,1,0,0},
        {0,4,0,0,5,0,0,0,0},
        {0,1,7,0,0,0,2,0,0},
        {0,0,5,7,9,0,0,0,0}
      };
    }
    else if (number == 2){
      sudoku = new Integer[][]{
        {0,9,1,8,0,0,5,0,0},
        {5,0,0,0,4,3,0,7,0},
        {7,4,0,0,1,0,0,0,3},
        {0,0,0,1,5,0,6,9,0},
        {1,5,0,0,0,0,0,8,4},
        {0,8,7,0,3,9,0,0,0},
        {9,0,0,0,8,0,0,6,2},
        {0,1,0,5,7,0,0,0,9},
        {0,0,6,0,0,4,7,1,0}
      };
    }
    else{
      sudoku = null;
    }
    return sudoku;
  }

  //Object
  public static class Board extends JFrame{     

    // public properties
    public JButton[][] grids = new JButton[9][9];
    public Boolean[][] blankBoxes = new Boolean[9][9];
    public long framerate = 30;    //ms
    
    // Constructor
    public Board(Integer[][] sudoku){     
      setLayout(new GridLayout(9, 9)); 
      int count = 0;
      for(int i = 0; i < grids.length; i++, count++) {
        for(int j = 0; j < grids.length; j++) {
          String text = sudoku[i][j] == 0 ? "" : Integer.toString(sudoku[i][j]);
          grids[i][j] = new JButton(text);
          grids[i][j].setEnabled(sudoku[i][j] == 0);

          grids[i][j].setBackground(Color.WHITE);
          add(grids[i][j]);
          count++;
        }
      }
      InitializeBlankBoxes();
    }

    // public methods
    public boolean Solve(){
      return SolveSudoku(0,0);
    }

    //private methods
    private boolean SolveSudoku(int row, int column){
      if(row == 9) return true;
      if(blankBoxes[row][column])
        {
          for(Integer i = 1; i <= 9; i++)
          {
            if(IsValid(i, row, column))
            {
              Update(i, row, column);
              if(column < 8){
                if(SolveSudoku(row, column+1)) return true;
                Update(0, row, column);
              }
              else{
                if(SolveSudoku(row+1, 0)) return true;
                Update(0, row, column);
              }
            }
          }
          Update(0, row, column);
        }
        else
        {
          if(column < 8)
          {
            if(SolveSudoku(row, column+1)) return true;
          }
          else
          {
            if(SolveSudoku(row+1, 0)) return true;
          } 
        }
      return false;
    }
    
    private void InitializeBlankBoxes(){
      for (Integer i=0; i < 9; i++)
      {
        for (Integer j=0; j < 9; j++)
        {
          if(TextToInt(grids[i][j]) == 0) 
          {
            blankBoxes[i][j] = true;
          }
          else blankBoxes[i][j] = false;
        }
      }
    }

    private boolean IsValid(Integer input, int row, int column){
      for(int a = 0; a < 9; a++)
      {
        if(TextToInt(grids[a][column]) == input) return false;
      }
      //判断同一行是否有相同
      for(int a = 0; a < 9; a++)
      {
        if(TextToInt(grids[row][a]) == input) return false;
      }
      //判断九宫格
      int startRow = row - row % 3;
        int startcolumn = column - column %3;     
        for (int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                if(TextToInt(grids[startRow + i][startcolumn + j]) == input)
                {
                    return false;
                }
            }
        }
      return true;
    }
    
    private void Update(Integer value, Integer row, Integer column){ 
      String text = value == 0 ? "" : Integer.toString(value);
      grids[row][column].setText(text);
      grids[row][column].setForeground(Color.GREEN);
      
      try {
          Thread.sleep((long) ((1.0/framerate) * 1000));
      } catch(InterruptedException e) {
          System.out.println("got interrupted!");
      }
    
    
    
    }
    
    private Integer TextToInt(JButton btn){
      String s = btn.getText();
      if(s == "") return 0;
      else return Integer.parseInt(s);
    }
  }
  
	public static void main(String[] args) {

    //Initialization
        System.out.println("start!");
		JFrame board = new Board(GenerateSudoku(2));
		
		board.setTitle("sudoku");
		board.setSize(500, 500);
		board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		board.setVisible(true);
//		((Board)board).framerate = 5.0;
		((Board)board).Solve();




	}
}