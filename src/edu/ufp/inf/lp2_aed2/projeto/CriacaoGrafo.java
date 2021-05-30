package edu.ufp.inf.lp2_aed2.projeto;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SequentialSearchST;

import static java.lang.Math.pow;

public class CriacaoGrafo extends Graph {

    private int[] positionsX;
    private int[] positionsY;

    public CriacaoGrafo(int x, SequentialSearchST<Integer, Geocache> geo) {
        super(x);
        positionsX = new int[x];
        positionsY = new int[x];
        setPositions(x,geo);
    }

    public CriacaoGrafo(CriacaoGrafo gG) {
        super(gG.V());
        positionsX = gG.positionsX;
        positionsY = gG.positionsY;
    }

    public CriacaoGrafo(CriacaoGrafo gG, int newSize, SequentialSearchST<Integer, Geocache> geo){
        super(newSize);
        positionsX = new int[newSize];
        positionsY = new int[newSize];

        for(int i = 0 ; i < gG.V() && i < newSize ; i++ ){
            positionsX[i] = gG.positionsX[i];
            positionsY[i] = gG.positionsY[i];
        }

        setPositions(gG.V(),geo);

        for(int v = 0 ; v < gG.V() ; v++){
            for (Integer adj: gG.adj(v)){
                this.addEdge(v,adj);
            }
        }
    }

    private void setPositions(int b, SequentialSearchST<Integer, Geocache> geo){
        // Descobrir o menor, e maior X e Y (nao feito) X: 37,42 Y:-7,-10| Screen 700 a 450
        long x, y;
        for(int a = 0; a < b; a++) {
            if (geo.get(a + 1) != null) {
                x = (long) (geo.get(a + 1).coordenadasX * pow(10, 7));
                y = (long) (geo.get(a + 1).coordenadasY * pow(10, 7));

                x = (long) (x - (37 * pow(10, 7)));
                y = (long) ((y + (7 * pow(10, 7))) * (-1));

                x = (long) ((x * 700) / (5 * pow(10, 7)));
                y = (long) ((y * 450) / (3 * pow(10, 7)));

                positionsX[a] = (int) x;
                positionsY[a] = (int) y;
                //System.out.println(positionsX[a] + " " + positionsY[a]);
            }
        }
    }

    public boolean containsEdge(int v, int a){
        for(Integer adj: this.adj(v))
            if(adj == a) return true;

        for(Integer adj: this.adj(a))
            if(adj == v) return true;

        return false;
    }

    public void setPositionsX(int index ,int pos) {
        positionsX[index] = pos;
    }

    public void setPositionsY(int index ,int pos) {
        positionsY[index] = pos;
    }

    public int getPositionsX(int idx) {
        return positionsX[idx];
    }

    public int getPositionsY(int idx) {
        return positionsY[idx];
    }
}
