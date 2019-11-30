public class Checker {
    private boolean isRed;
    private int row;
    private int column;

    public Checker(boolean isRed){
        this.isRed = isRed;
        this.row = 1;
        this.column = 1;
    }

    public Checker(boolean isRed, int row, int column){
        this.isRed = isRed;
        if(validSquare(row, column)){
        this.row = row;
        this.column = column;
        }
        else{
            this.row = 1;
            this.column = 1;
        }
    }

    public void move(int rows, int columns){
        if(validMove(rows, columns) && validSquare(this.row+rows, this.column+columns)){
            this.row += rows;
            this.column += columns;
        }
    }

    public boolean isRed(){
        return isRed;
    }

    public int getRow(){
        return this.row;
    }

    public int getColumn(){
        return this.column;
    }

    public boolean validSquare(int row, int column){
        return (1<= row && row <= 8) && (1<= column && column <= 8) && (row % 2 == column % 2);
    }

    public boolean validMove(int rows, int columns){
        return (this.isRed() && rows == -1 && Math.abs(columns) == 1)
              || (!this.isRed() && rows == 1 && Math.abs(columns) == 1);
    }

}
