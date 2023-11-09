package lk.ijse.dep.service;

import lk.ijse.dep.controller.BoardController;

import javax.print.DocFlavor;

public class BoardImpl<win> implements Board {
    private static Piece [][] pieces = new Piece [NUM_OF_COLS][NUM_OF_ROWS] ;
    private BoardUI boardUI;
    private int colIndex;
    private int rowIndex;
    private static int getWinner;



    public static Piece[][] getPieces() {return pieces;}

    public BoardImpl(BoardUI boardUI) {
        this.boardUI=boardUI;

        for (int col=0;col<6;col++){
            for (int row=0;row<5;row++){
                pieces[col][row]=Piece.EMPTY;
            }
        }

    }

    @Override
    public BoardUI getBoardUI() {
        return boardUI;
    }

    @Override
    public int findNextAvailableSpot(int col) {
        for(int i=0; i<5; i++){
            if(pieces[col][i]==Piece.EMPTY){
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean isLegalMove(int col) {

        if(findNextAvailableSpot(col) != -1){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean existLegalMove() {
        for (int i=0; i<6; i++){
            for (int j=0; j<5; j++) {
                if(pieces[i][j] == Piece.EMPTY){
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public void updateMove(int col, Piece move) {
        colIndex= col;
        L1: for(int i=0; i<5; i++){
            if(pieces[col][i]==Piece.EMPTY){
                pieces[col][i]=move;
                rowIndex=i;
                break L1;
            }
        }
    }

    @Override
    public Winner findWinner() {
        Winner win;
        Piece color = pieces[colIndex][rowIndex];
        int count=0;
        int col1=0;
        int row1=0;
        int col2=0;
        int row2=0;
        int LoopCol;
        int LoopRow;
       L1: while(true) {

            for (int i = rowIndex; i >= 0; i--) {
                if (pieces[colIndex][i] == color) {
                    count++;
                    LoopRow = i;

                } else {
                    break;
                }
                if (count == 4) {
                    win = new Winner(colIndex, LoopRow, colIndex, rowIndex, color);
                    if(color==Piece.BLUE){
                        getWinner=-1;
                    }else{
                        getWinner=+1;
                    }
                    return win;
                }
            }

            L2:while (true) {
                count = 0;
                LoopCol = 0;
                LoopRow = 0;
                L3:
                for (int i = colIndex; i < pieces.length; i++) {
                    if (pieces[i][rowIndex] == color) {
                        count++;
                        LoopCol = i;
                        LoopRow = rowIndex;

                        if (count == 4) {
                            col1 = colIndex;
                            row1 = rowIndex;
                            col2 = i;
                            row2 = rowIndex;
                            win = new Winner(col1, row1, col2, row2, color);
                            if(color==Piece.BLUE){
                                getWinner=-1;
                            }else{
                                getWinner=+1;
                            }
                            return win;
                        }

                    } else {
                        break L3;
                    }

                }

                if (count > 1 && count < 4) {
                    col2 = LoopCol;
                    row2 = LoopRow;
                }

                if (colIndex == 0) {
                    break L1;
                }

                for (int i = colIndex; i >= 0; i--) {
                    if (i>0 && pieces[i - 1][rowIndex] == color) {
                        count++;
                        LoopCol = i;
                        LoopRow = rowIndex;

                        if (count == 4) {
                            if (col2 != 0) {
                                col1 = LoopCol-1;
                                row1 = LoopRow;
                                win = new Winner(col1, row1, col2, row2, color);
                                return win;
                            } else if (col2 == 0) {
                                col1 = i-1;
                                row1 = rowIndex;
                                col2 = colIndex;
                                row2 = rowIndex;
                                win = new Winner(col1, row1, col2, row2, color);
                                if(color==Piece.BLUE){
                                    getWinner=-1;
                                }else{
                                    getWinner=+1;
                                }
                                return win;
                            }
                        }
                    } else {
                        break L1;
                    }
                }
            }
        }
        getWinner=0;
        return null;
    }


//    @Override
//    public int methodAi() {
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
////        int colIn;
////        int rowIn;
////        int count;
////           L2: for (colIn = 0; colIn < pieces.length; colIn++) {
////                if (pieces[colIn][4] == Piece.EMPTY && pieces[colIn][3]!=Piece.BLUE) {
////                    count=0;
////                    L3: for (int j =3; j > 0; j--) {
////                        if(Piece.GREEN==pieces[colIn][j]){
////                            count++;
////                            if (count==3){
////                                return colIn;
////                            }
////                        }
////                    }
////                    count=0;
////                    L4: for (int k=2; k>=0; k--){
////                        if(Piece.GREEN==pieces[colIn][k]){
////                            count++;
////                            if (count==3){
////                                return colIn;
////                            }
////                        }else{
////                            count=0;
////                        }
////                    }
////                }else{
////                    count=0;
////                }
////            }
////
////            //Horizontally
////        for (rowIn = 0; rowIn< pieces[0].length; rowIn++){
////            count=0;
////            for (int i=0; i<pieces.length; i++){
////                if (Piece.GREEN==pieces[i][rowIn]){
////                    count++;
////                    if(count==3){
////                        if((i!=5) && (pieces[i+1][rowIn]==Piece.EMPTY)){
////                            return i+1;
////                        }else if(i!=2 && pieces[i-3][rowIn]==Piece.EMPTY){
////                            return i-3;
////                        }
////                    }
////                }else{
////                    count=0;
////                }
////            }
////        }
////
////            //block
////        for (colIn = 0; colIn < pieces.length; colIn++) {
////            if (pieces[colIn][4] == Piece.EMPTY&& pieces[colIn][3]!=Piece.GREEN) {
////                count=0;
////                for (int j =3; j > 0; j--) {
////                    if(Piece.BLUE==pieces[colIn][j]){
////                        count++;
////                        if (count==3){
////                            return colIn;
////                        }
////                    }
////                }
////                count=0;
////                for (int k=2; k>=0; k--){
////                    if(Piece.BLUE==pieces[colIn][k]){
////                        count++;
////                        if (count==3){
////                            return colIn;
////                        }
////                    }else{
////                        count=0;
////                    }
////                }
////            }else{
////                count=0;
////            }
////        }
////
////        for (rowIn = 0; rowIn< pieces[0].length; rowIn++){
////            count=0;
////            for (int i=0; i<pieces.length; i++){
////                if (Piece.BLUE==pieces[i][rowIn]){
////                    count++;
////                    if(count==3){
////                        if((i<5) && (pieces[i+1][rowIn]==Piece.EMPTY)){
////                            return i+1;
////                        }else if(i>2 && pieces[i-3][rowIn]==Piece.EMPTY){
////                            return i-3;
////                        }
////                    }
////                }else{
////                    count=0;
////                }
////            }
////        }
////
////        int col;
////        do{
////            col = (int)(Math.random() * 6);
////        }while(!(isLegalMove(col)));
////        return col;
////    }
//        return 2;
//    }
}