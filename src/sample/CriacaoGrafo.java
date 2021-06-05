package sample;

import edu.princeton.cs.algs4.*;
import edu.ufp.inf.lp2_aed2.projeto.Geocache;
import edu.ufp.inf.lp2_aed2.projeto.Ligacoes;

import java.util.Arrays;

import static java.lang.Math.*;

public class CriacaoGrafo extends EdgeWeightedGraph {

    private int[] positionsX;
    private int[] positionsY;
    public int[][] ligs;

    public CriacaoGrafo(int[] x, SequentialSearchST<Integer, Geocache> geo, SequentialSearchST<Integer, Ligacoes> lig) {
        super(x[2]);
        positionsX = new int[x[2]];
        positionsY = new int[x[2]];
        setPositions(x[2], geo);
    }

    public CriacaoGrafo(CriacaoGrafo gG) {
        super(gG.V());
        positionsX = gG.positionsX;
        positionsY = gG.positionsY;
    }

    public CriacaoGrafo(CriacaoGrafo gG, int[] sizes, SequentialSearchST<Integer, Geocache> geo) {
        super(sizes[2]+1);
        int z = sizes[2]+1;
        positionsX = new int[z];
        positionsY = new int[z];

        for (int i = 0; i < gG.V() && i < z; i++) {
            positionsX[i] = gG.positionsX[i];
            positionsY[i] = gG.positionsY[i];
        }
        setPositions(gG.V(), geo);
    }

    private void setPositions(int b, SequentialSearchST<Integer, Geocache> geo) {
        // Descobrir o menor, e maior X e Y (nao feito) X: 37,42 Y:-7,-10| Screen 700 a 450
        float min_x = 99.9f, min_y = 99.9f, max_x = 0.0f, max_y = 0.0f;
        for (int g = 0; g < b; g++) {
            if (geo.get(g + 1) != null) {
                if (min_x > abs(geo.get(g + 1).coordenadasX)) {
                    min_x = abs(geo.get(g + 1).coordenadasX);
                }
                if (min_y > abs(geo.get(g + 1).coordenadasY)) {
                    min_y = abs(geo.get(g + 1).coordenadasY);
                }
                if (max_x < abs(geo.get(g + 1).coordenadasX)) {
                    max_x = abs(geo.get(g + 1).coordenadasX);
                }
                if (max_y < abs(geo.get(g + 1).coordenadasY)) {
                    max_y = abs(geo.get(g + 1).coordenadasY);
                }
            }
        }
        max_x = (max_x - min_x);
        max_y = (max_y - min_y);

        long x, y;

        for (int a = 0; a < b; a++) {
            if (geo.get(a + 1) != null) {
                x = (long) (abs(geo.get(a + 1).coordenadasX) * pow(10, 7));
                y = (long) (abs(geo.get(a + 1).coordenadasY) * pow(10, 7));

                x = (long) (x - (min_x * pow(10, 7)));
                y = (long) (y - (min_y * pow(10, 7)));

                x = (long) (((x * 660) / (max_x * pow(10, 7))) + 20);
                y = (long) (((y * 350) / (max_y * pow(10, 7))) + 20);

                int[] f = resolveCollision(a, (int) x, (int) y);

                positionsX[a] = f[0];
                positionsY[a] = f[1];
            }
        }
    }

    public void setPositionsX(int index, int pos) {
        positionsX[index] = pos;
    }

    public void setPositionsY(int index, int pos) {
        positionsY[index] = pos;
    }

    public int getPositionsX(int idx) {
        return positionsX[idx];
    }

    public int getPositionsY(int idx) {
        return positionsY[idx];
    }

    public boolean checkCollision(int a, int b) {
        Controller c = new Controller();
        //System.out.print("Comparacao " + a + " - " + b + " = " + abs(a - b));
        return abs(a - b) < (c.getRadius() * 3)/2;
    }

    public int[] resolveCollision(int a, int x, int y) {
        int collisions = 0, px = 0, py = 0;

        for (int z = 0; z < a; z++) {
            if (checkCollision(positionsX[z], x))
            if (checkCollision(positionsY[z], y))
                collisions++;
        }

        while (collisions > 0) {
            Controller c = new Controller();
            if (checkCollision(positionsX[px], x)) {
                if (x < 700/2) {
                    x += c.getRadius() *2;
                } else {
                    x -= (c.getRadius() *2) + 1;
                }
            } else {
                px++;
            }

            if (checkCollision(positionsY[py], y)) {
                if (y < 450/2) {
                    y += c.getRadius() *2;
                } else {
                    y -= (c.getRadius() *2) + 1;
                }
            } else {
                py++;
            }

            collisions = 0;
            for (int z = 0; z < a; z++) {
                if (checkCollision(positionsX[z], x))
                    if (checkCollision(positionsY[z], y))
                        collisions++;
            }
        }
        return new int[]{x, y};
        }

    public int[][] create_arraysLig(int[] sizes, SequentialSearchST<Integer, Ligacoes> lig){
        int sl = sizes[5];
        int pos = 0;
        ligs = new int[sizes[2]][10];
        for(int g = 1; g <= sizes[2]; g++){
            for(int l = 1; l < sl; l++){
                if(lig.get(l) != null && Integer.parseInt(lig.get(l).id_1.replace("geocache","")) == g){
                    ligs[g-1][pos] = Integer.parseInt(lig.get(l).id_2.replace("geocache",""));
                    pos++;
                }
//                else {sl --;}
            }
            pos = 0;
            sl = sizes[5];
        }
        /*for(int j=1 ; j <= sizes[2]; j++)
        System.out.println(j + " " + Arrays.toString(ligs[j-1]));*/

        return ligs;
    }

    public void edgesDist(int[][] ligs, CriacaoGrafo gG, SequentialSearchST<Integer, Ligacoes> lig, int[] sizes){
        int k;
        // g percorre 18 geocaches
        for(int g = 0; g < sizes[2]; g++){
            //System.out.println("\n" + g);
            // percorre enquanto existir informaçao
            for (int s = 0; ligs[g][s] != 0; s++){
                //System.out.print("\t" + s);
                    // de 1 a 50
                    for(k = 1; k < sizes[5]; k++) {
                        //System.out.println((Integer.parseInt(lig.get(k).id_1.replace("geocache", "")) + " " + (g+1) + " " + (Integer.parseInt(lig.get(k).id_2.replace("geocache", "")) + " " + (ligs[g][s]))));
                        if ((Integer.parseInt(lig.get(k).id_1.replace("geocache", "")) == g + 1) && (Integer.parseInt(lig.get(k).id_2.replace("geocache", "")) == ligs[g][s]))
                            break;
                    }
                //System.out.println("Acertou");
                    Edge e = new Edge(g, ligs[g][s]-1,lig.get(k).distancia);
                    gG.addEdge(e);
            }
        }
    }

    public void edgesTemp(int[][] ligs, CriacaoGrafo gG, SequentialSearchST<Integer, Ligacoes> lig, int[] sizes){
        int k;
        // g percorre 18 geocaches
        for(int g = 0; g < sizes[2]; g++){
            //System.out.println("\n" + g);
            // percorre enquanto existir informaçao
            for (int s = 0; ligs[g][s] != 0; s++){
                //System.out.print("\t" + s);
                // de 1 a 50
                for(k = 1; k < sizes[5]; k++) {
                    //System.out.println((Integer.parseInt(lig.get(k).id_1.replace("geocache", "")) + " " + (g+1) + " " + (Integer.parseInt(lig.get(k).id_2.replace("geocache", "")) + " " + (ligs[g][s]))));
                    if ((Integer.parseInt(lig.get(k).id_1.replace("geocache", "")) == g + 1) && (Integer.parseInt(lig.get(k).id_2.replace("geocache", "")) == ligs[g][s]))
                        break;
                }
                //System.out.println("Acertou");
                Edge e = new Edge(g, ligs[g][s]-1, lig.get(k).tempo);
                gG.addEdge(e);
            }
        }
    }

    public int[][] getLigs() {
        return ligs;
    }
}
