/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package ai;

import java.util.List;

/**
 *
 * @author Rohan
 */
public class TimeAgent extends OthelloPlayer {
    long maxtime;
    double bestscoremax;
    double bestscoremin;
    int scoremax;
    int scoremin;

    public TimeAgent(long maxtime) {
        this.maxtime=maxtime;
    }
    @Override
    public OthelloMove getMove(OthelloState state) {
        long starttime=System.currentTimeMillis();
         List<OthelloMove> moves = state.generateMoves(0);
        OthelloMove move = null;
        double best = Double.NEGATIVE_INFINITY;
        double worst=Double.POSITIVE_INFINITY;
        for (int i = 0; i < moves.size(); i++) {
            int result = (int) Maxvalue(state, maxtime,starttime,(int) best,(int)worst);
            if (result > best) {
                best = result;
                move = moves.get(i);
            }
        }
        return move;
    }

    public double Maxvalue(OthelloState state, long maxtime,long starttime, int alpha, int beta) {
        //long starttime=System.currentTimeMillis();
        
        if (state.gameOver()) {
            return state.score();
        } 
            List<OthelloMove> moves = state.generateMoves(0);
            if (System.currentTimeMillis()-starttime <=maxtime) {
                if (!moves.isEmpty()) {
                    bestscoremax = Double.NEGATIVE_INFINITY;
                    for (int i = 0; i < moves.size(); i++) {

                        OthelloState temp = state.applyMoveCloning(moves.get(i));

                        scoremax = (int) MinValue(temp, maxtime,starttime, alpha, beta);
                        if (scoremax > bestscoremax) {

                            bestscoremax = scoremax;
                        }
                       if(bestscoremax>=beta)
                           return bestscoremax;
                       if(bestscoremax>alpha)
                           alpha=(int)bestscoremax;
                           
                    }
                    moves.clear();
                    return bestscoremax;
                } else {
                    return state.score();
                }

            } else {
                return state.score();
            }
        }
    

    public double MinValue(OthelloState state, long maxtime, long starttime, int alpha, int beta) {
//long starttime=System.currentTimeMillis();
        if (state.gameOver()) {
            return state.score();
        } 
            if (System.currentTimeMillis()-starttime<=maxtime) {
                List<OthelloMove> movesmin = state.generateMoves(1);
                if (!movesmin.isEmpty()) {
                    bestscoremin = Double.POSITIVE_INFINITY;
                    for (int i = 0; i < movesmin.size(); i++) {
                        OthelloState temp = state.applyMoveCloning(movesmin.get(i));
                        scoremin = (int) Maxvalue(temp, maxtime,starttime, alpha,beta);
                        if (scoremin < bestscoremin) {
                            bestscoremin = scoremin;
                        }
                        if(bestscoremin<=alpha)
                            return bestscoremin;
                        if(bestscoremin<beta)
                            beta=(int)bestscoremin;
                    }
                    movesmin.clear();
                    return bestscoremin;
                } else {
                    return state.score();
                }
            } else {
                return state.score();
            }

        }
    }
    

