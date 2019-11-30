public class CheckerChecker {

    public static boolean validColor(Checker c, boolean color)
    {
        return c.isRed() == color;
    }

    public static boolean validRowAndColumn(Checker c, int correctRow, int correctColumn)
    {
        return c.getColumn() == correctColumn && c.getRow() == correctRow;
    }

//    public static boolean validBoundary(int row, int column)
//    {
//        return (1 <= row && row <= 8)
//                && (1 <= column && column <= 8);
//    }



    public static boolean validSquare( int row, int column)
    {
        return (row % 2 == column % 2) && (1 <= row && row <= 8) && (1 <= column && column <= 8);
    }


    public static boolean validReset(int row, int column)
    {
        return row == 1 && column == 1;
    }

//    public static boolean validMove(test.Checker c, int rows, int columns)
//    {
//        if ((c.isRed() && rows == -1 && Math.abs(columns) == 1)
//                || (!c.isRed() && rows == 1 && Math.abs(columns) == 1))
//        {
//            return validBoundary(c.getRow()+rows, c.getColumn()+columns);
//        }
//        else
//            return false;
//    }


    public static boolean doNothing(Checker beforeMove, Checker afterMove)
    {
        return beforeMove.getRow() == afterMove.getRow() && beforeMove.getColumn() == afterMove.getColumn();
    }

    public static void main(String[] args)
    {
        boolean flag = true;
        //About Constructor:
            //construct correct color?
        Checker c1 = new Checker(false);
        Checker c2 = new Checker(true);
        Checker c3 = new Checker(false, 1,3);
        Checker c4 = new Checker(true, 7,5);
        Checker c_extra1 = new Checker(true, 5,7);
        Checker c_extra2 = new Checker(false, 2, 8);
        if(!validColor(c1, false) || !validColor(c2, true) || !validColor(c3, false) || !validColor(c4, true))
        {
            flag = false;
        }


            //construct correct row and column?
        if(!validReset(c1.getRow(), c1.getColumn()) || !validReset(c2.getRow(), c2.getColumn()))
        {
            flag = false;
        }
        if(!validRowAndColumn(c3, 1, 3) || !validRowAndColumn(c4, 7, 5) || !validRowAndColumn(c_extra2, 2, 8))
        {
            flag = false;
        }

            //construct correct position in valid square?
        Checker c5 = new Checker(true, 7,4);
        Checker c6 = new Checker(false, 2, 3);
        Checker c7 = new Checker(true, 7,11);
        Checker c8 = new Checker(false, -1, 3);
        if(!validSquare(7, 4))
        {
            if(!validReset(c5.getRow(), c5.getColumn()))
            {
                flag = false;
            }
        }
        if(!validSquare(2, 3))
        {
            if(!validReset(c6.getRow(), c6.getColumn()))
            {
                flag = false;
            }
        }
        if(!validSquare(7, 11))
        {
            if(!validReset(c7.getRow(), c7.getColumn()))
            {
                flag = false;
            }
        }
        if(!validSquare(-1, 3))
        {
            if(!validReset(c8.getRow(), c8.getColumn()))
            {
                flag = false;
            }
        }

//            //construct a checker within boundary?
//        test.Checker c7 = new test.Checker(true, 9,11);
//        test.Checker c8 = new test.Checker(false, -1, -3);
//        if(!validBoundary(9, 11))
//        {
//            if(!validReset(c7.getRow(), c7.getColumn()))
//            {
//                flag = false;
//            }
//        }
//
//        if(!validBoundary(-1, -3))
//        {
//            if(!validReset(c8.getRow(), c8.getColumn()))
//            {
//                flag = false;
//            }
//        }

        //About movement
            //whether move() remains the correct color?
        Checker c9  = new Checker(true, 6,2);
        Checker c10 = new Checker(false, 3,7);
        Checker c11 = new Checker(true, 8,8);
        Checker c12 = new Checker(true, 7,1);
        Checker c_extra3 = new Checker(false, 8,8);
        Checker c13 = new Checker(false, 1,1);
        Checker c14 = new Checker(false, 2,8);
        Checker c_extra4 = new Checker(true, 1,1);
            //below this line there are  valid move()s.
        c9.move(-1,1);
        c10.move(1,-1);
        if(!validColor(c9, true) || !validColor(c10, false))
        {
            flag = false;
        }
        if(!validRowAndColumn(c9, 5, 3) || !validRowAndColumn(c10, 4, 6))
        {
            flag = false;
        }

            //if they remain correct singularity(i.e. valid square) and within the boundary
        if(!validSquare(c9.getRow(), c9.getColumn()) || !validSquare(c10.getRow(), c10.getColumn()))
        {
            flag = false;
        }
//            //still within the boundary
//        if(!validBoundary(c9.getRow(), c9.getColumn()) || !validBoundary(c10.getRow(), c10.getColumn()))
//        {
//            flag = false;
//        }

        c9.move(-1,-1);
        c10.move(1,1);
        if(!validColor(c9, true) || !validColor(c10, false))
        {
            flag = false;
        }
        if(!validRowAndColumn(c9, 4, 2) || !validRowAndColumn(c10, 5, 7))
        {
            flag = false;
        }
        if(!validSquare(c9.getRow(), c9.getColumn()) || !validSquare(c10.getRow(), c10.getColumn()))
        {
            flag = false;
        }
//        if(!validBoundary(c9.getRow(), c9.getColumn()) || !validBoundary(c10.getRow(), c10.getColumn()))
//        {
//            flag = false;
//        }

            //below this line there are invalid move()s.
        Checker rec1 = new Checker(c9.isRed(), c9.getRow(), c9.getColumn());
        Checker rec2 = new Checker(c10.isRed(), c10.getRow(), c10.getColumn());
                //red one's moving rows must be -1, while white one's must be 1;
        c9.move(1,-1);
        c10.move(-1,1);
        if(!doNothing(rec1, c9) || !doNothing(rec2, c10))
        {
            flag = false;
        }
                // |rows| and |columns| should be 1;
        c9.move(-1, 3);
        c10.move(1, -4);
        if(!doNothing(rec1, c9) || !doNothing(rec2, c10))
        {
            flag = false;
        }
        c9.move(-3, 1);
        c10.move(2, -1);
        if(!doNothing(rec1, c9) || !doNothing(rec2, c10))
        {
            flag = false;
        }
        c9.move(0, 1);
        c10.move(1, 0);
        if(!doNothing(rec1, c9) || !doNothing(rec2, c10))
        {
            flag = false;
        }
        c9.move(-2, 2);
        c10.move(2, -2);
        if(!doNothing(rec1, c9) || !doNothing(rec2, c10))
        {
            flag = false;
        }


                //After moving checkers should stay within boundary;
        Checker rec3 = new Checker(c11.isRed(), c11.getRow(), c11.getColumn());
        Checker rec4 = new Checker(c12.isRed(), c12.getRow(), c12.getColumn());
        Checker rec5 = new Checker(c13.isRed(), c13.getRow(), c13.getColumn());
        Checker rec6 = new Checker(c14.isRed(), c14.getRow(), c14.getColumn());
        Checker rec7 = new Checker(c_extra3.isRed(), c_extra3.getRow(), c_extra3.getColumn());
        Checker rec8 = new Checker(c_extra4.isRed(), c_extra4.getRow(), c_extra4.getColumn());
        c11.move(-1, 1);
        c12.move(-1, -1);
        c13.move(1, -1);
        c14.move(1,1);
        c_extra3.move(1,-1);
        c_extra4.move(-1, 1);

        if(!doNothing(rec3, c11) || !doNothing(rec4, c12) || !doNothing(rec5, c13) || !doNothing(rec6, c14) || !doNothing(rec7, c_extra3) || !doNothing(rec8, c_extra4))
        {
            flag = false;
        }


        //Maye it is done. Oh my gosh, I should think tons of conditions what it will happen.
        if(flag)
        {
            System.out.println("CORRECT");
        }
        else
        {
            System.out.println("BUG");
        }


    }
}
