package lk.ijse.dep.service;

import javafx.beans.property.adapter.JavaBeanStringProperty;

public class AiPlayer extends Player {
    int boardClone[][]=new int[6][5];
    public AiPlayer(Board board) {
        super(board);
    }
    @Override
    public void movePiece(int col) {
//        do{
//            col = (int)(Math.random() * 6);
//        }while(!(board.isLegalMove(col)));
//        col=board.methodAi();
        col= bestMove();

        if(board.isLegalMove(col)){
            board.updateMove(col,Piece.GREEN);
            board.getBoardUI().update(col, false);
            Winner winner = board.findWinner();

            if(winner!=null){
                board.getBoardUI().notifyWinner(winner);
            }else{
                if(!board.existLegalMove()){
                    board.getBoardUI().notifyWinner(new Winner(Piece.EMPTY));
                }
            }
        }
    }

    public int bestMove(){

        Piece [][]pieces=BoardImpl.getPieces();
        for (int i=0; i<6; i++){
            for (int j =0; j<5; j++){
                if(pieces[i][j]==Piece.BLUE){
                    boardClone[i][j]=-1;
                }else if(pieces[i][j]==Piece.GREEN){
                    boardClone[i][j]=1;
                }else if(pieces[i][j]==Piece.EMPTY)
                    boardClone[i][j]=0;

            }
        }

        int bestScore= (int) Double.NEGATIVE_INFINITY;
        int bestMove=0;
        for (int i=0; i<boardClone.length; i++){
//                  int count=0;
            for (int j=0; j<boardClone[0].length; j++){
//                if(count==1){
//                    break;
//                }
                if(boardClone[i][j]==0){
                    //  count++;
                    boardClone[i][j]=1;
                    int score=minimax(boardClone,0,false,i,j);
                    boardClone[i][j]=0;
                    if(score> bestScore){
                        bestScore = score;
                        bestMove=i;
                    }
                }
            }
        }
        return bestMove;
    }


    int minimax(int boardClone[][],int depth, boolean isMax, int l, int m){

        int winner=findWinner(boardClone,l,m);
        if(depth == 4 || winner!=0){
            if(winner == 0)
                return 0;
            return findWinner(boardClone,l,m);

        }

        int bestScore;
        if(isMax){
            bestScore = (int) Double.NEGATIVE_INFINITY;
            for (int i=0; i<boardClone.length; i++){
//              int count=0;
                for (int j=0; j<boardClone[0].length; j++){
//                    if(count==1){
//                        break;
//                    }
                    if(boardClone[i][j]==0){
                        //count++;
                        boardClone[i][j]=1;
                        int score=minimax(boardClone,depth+1,false, i, j);
                        boardClone[i][j]=0;
                        bestScore=Math.max(score,bestScore);
                    }
                }
            }
            return bestScore;
        }else{
            int bestMove=0;
            bestScore = (int) Double.POSITIVE_INFINITY;
            for (int i=0; i<boardClone.length; i++){
//              int count=0;
                for (int j=0; j<boardClone[0].length; j++){
//                    if(count==1){
//                        break;
//                    }
                    if(boardClone[i][j]==0){
                        //count++;
                        boardClone[i][j]=-1;
                        int score=minimax(boardClone,depth+1,true, i, j);
                        boardClone[i][j]=0;
                        bestScore=Math.min(score,bestScore);
                    }
                }
            }
            return bestScore;
        }
    }


    public int findWinner(int boardClone[][],int colIndex,int rowIndex) {
        int player=boardClone[colIndex][rowIndex];
        int count=0;
        int LoopCol;
        int LoopRow;
        L1: while(true) {

            for (int i = rowIndex; i >= 0; i--) {
                if (boardClone[colIndex][i] == player) {
                    count++;
                    LoopRow = i;

                } else {
                    break;
                }
                if (count == 4) {
                    return player;
                }
            }

            L2:while (true) {
                count = 0;
                LoopCol = 0;
                LoopRow = 0;
                L3:
                for (int i = colIndex; i < boardClone.length; i++) {
                    if (boardClone[i][rowIndex] == player) {
                        count++;
                        LoopCol = i;
                        LoopRow = rowIndex;

                        if (count == 4) {

                            return player;
                        }

                    } else {
                        break L3;
                    }

                }

                if (colIndex == 0) {
                    break L1;
                }

                for (int i = colIndex; i >= 0; i--) {
                    if (i>0 && boardClone[i - 1][rowIndex] == player) {
                        count++;
                        LoopCol = i;
                        LoopRow = rowIndex;

                        if (count == 4) {

                            return player;

                        }
                    } else {
                        break L1;
                    }
                }
            }
        }
        return 0;
    }
}