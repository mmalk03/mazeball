package com.mmalk.mazeball.maputil.framework;

import com.mmalk.mazeball.maputil.ArrayPosition;

import java.util.Stack;

public abstract class GameMapSolution {

    protected Stack<ArrayPosition> solution;

    public GameMapSolution(GameMapSolution gameMapSolution){
        solution = (Stack<ArrayPosition>) gameMapSolution.getSolution().clone();
    }

    public GameMapSolution(Stack<ArrayPosition> solution) {
        this.solution = solution;
    }

    public Stack<ArrayPosition> getSolution() {
        return solution;
    }

    public void setSolution(Stack<ArrayPosition> solution) {
        this.solution = solution;
    }

    public ArrayPosition pop(){
        return solution.pop();
    }

    public ArrayPosition peek(){
        return solution.peek();
    }

    public int size(){
        return solution.size();
    }

    public ArrayPosition push(ArrayPosition arrayPosition){return solution.push(arrayPosition);}
}
