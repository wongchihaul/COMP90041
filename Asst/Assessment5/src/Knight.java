

/**
 * @Author Zhihao Huang
 * @LoginID zhihhuang
 * @ClassName Knight
 * @Description TODO
 * @date 2019-10-08 00:53
 **/
public class Knight extends ChessPiece {
    Knight(int row, int column) {
        super(row, column);
    }

    public boolean validMove(int toRow, int toColumn)
    {
        return ((Math.abs(toRow - this.getRow()) == 1 && Math.abs(toColumn - this.getColumn()) == 2) ||
                    (Math.abs(toRow - this.getRow()) == 2 && Math.abs(toColumn - this.getColumn()) == 1))
                && super.validMove(toRow, toColumn);
    }

    public String toString()
    {
        return "Knight at (" + this.getRow() + "," + this.getColumn() + ")";
    }

}
