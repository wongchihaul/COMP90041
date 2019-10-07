public class ChessPiece {
    private int row;
    private int column;
    ChessPiece(int row, int column)
    {
        if(validSquare(row, column))
        {
            this.row = row;
            this.column = column;
        }
    }

    int getRow() {
        return row;
    }

    int getColumn() {
        return column;
    }

    public String toString()
    {
        return " at (" + this.getRow() + "," + this.getColumn() + ")";
    }

    public boolean validMove(int toRow, int toColumn)
    {
        return (toRow != 0 || toColumn != 0) &&
                validSquare(this.getRow() +toRow, this.getColumn() + toColumn);
    }

    private boolean validSquare(int row, int column)
    {
        return (1 <= row && row <= 8) && (1 <= column && column <= 8);
    }


}
