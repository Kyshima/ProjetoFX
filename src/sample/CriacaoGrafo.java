package sample;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.SequentialSearchST;
import edu.ufp.inf.lp2_aed2.projeto.Geocache;
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

        /*float min_x = 99.9f, min_y = 99.9f, max_x = 0.0f, max_y = 0.0f;
        for(int g = 0; g < b; g++){
            if (geo.get(g + 1) != null) {
                if(min_x > geo.get(g + 1).coordenadasX){ min_x = geo.get(g + 1).coordenadasX;}
                if(min_y > geo.get(g + 1).coordenadasY){ min_y = geo.get(g + 1).coordenadasY;}
                if(max_x < geo.get(g + 1).coordenadasX){ max_x = geo.get(g + 1).coordenadasX * -1;}
                if(max_y < geo.get(g + 1).coordenadasY){ max_y = geo.get(g + 1).coordenadasY;}
            }
        }
        System.out.println(max_x + " " + max_y + " " + min_x + " " + min_y);
        max_x = max_x - min_x;
        max_y = max_y - min_y;*/

        long x, y;


        for(int a = 0; a < b; a++) {
            if (geo.get(a + 1) != null) {
                x = (long) (geo.get(a + 1).coordenadasX * pow(10, 7));
                y = (long) (geo.get(a + 1).coordenadasY * pow(10, 7));

                x = (long) (x - (31.5f * pow(10, 7)));
                y = (long) ((y - (2.5f * pow(10, 7))) * (-1));

                x = (long) ((x * 700) / (3.5f * pow(10, 7)));
                y = (long) ((y * 450) / (1.5f * pow(10, 7)));

                System.out.print(x + " " + y + "\t");

                for(int z = 0; z < a; z++){
                    if(positionsX[z] - x > (long) 12 && positionsX[z] - x > (long) 0){
                        x = x + (long) 14;
                    }
                    else if(positionsX[z] - x < (long) 12 && positionsX[z] - x <= (long) 0){
                        x = x - (long) 14;
                    }

                    if(positionsY[z] - y > (long) 12 && positionsY[z] - y > (long) 0){
                        y = y + (long) 14;
                    }
                    else if(positionsY[z] - y < (long) 12 && positionsY[z] - y <= (long) 0){
                        y = y - (long) 14;
                    }
                }
                System.out.println(x + " " + y);
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
