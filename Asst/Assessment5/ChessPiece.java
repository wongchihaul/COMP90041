public abstract class ChessPiece {
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


    public boolean validMove(int toRow, int toColumn)
    {
        return (toRow != this.getRow() || toColumn != this.getColumn()) &&
                validSquare(toRow, toColumn);
    }

    private boolean validSquare(int row, int column)
    {
        return (1 <= row && row <= 8) && (1 <= column && column <= 8);
    }

//    public boolean reachEdge(int row, int column)
//    {
//        return(row == 1 || row == 8) || (column == 1 || column == 8);
//    }
}
