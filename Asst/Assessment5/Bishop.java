/**
 * @Author Zhihao Huang
 * @LoginID zhihhuang
 * @ClassName Bishop
 * @Description TODO
 * @date 2019-10-08 00:53
 **/
public class Bishop extends ChessPiece {
    Bishop(int row, int column) {
        super(row, column);
    }
    public boolean validMove(int toRow, int toColumn)
    {
        return (Math.abs(toRow - this.getRow()) == Math.abs(toColumn - this.getColumn()))
                && super.validMove(toRow, toColumn)
//                && reachEdge(toRow, toColumn)
                ;
    }

    public String toString()
    {
        return "Bishop at (" + this.getRow() + "," + this.getColumn() + ")";
    }
}
