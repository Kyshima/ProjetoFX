package sample;

import edu.princeton.cs.algs4.*;
import edu.ufp.inf.lp2_aed2.projeto.Geocache;
import edu.ufp.inf.lp2_aed2.projeto.Ligacoes;

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
                y = (long) (((y * 380) / (max_y * pow(10, 7))) + 20);

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

    public int[][] create_arraysLig(int[] sizes, SequentialSearchST<Integer, Geocache> geo, SequentialSearchST<Integer, Ligacoes> lig){
        int sl = sizes[5];
        int pos = 0;
        int ligss = sizes[2];
        for (int i = 0; i < ligss; i++) {
            if(geo.get(i) == null) ligss++;
        }
        ligs = new int[ligss][10];
        int sg = sizes[2];
        for(int g = 1; g <= sg; g++) {
            if (geo.get(g) != null) {
                for (int l = 1; l < sl; l++) {
                    if (lig.get(l) != null) {
                        if (Integer.parseInt(lig.get(l).id_1.replace("geocache", "")) == g) {
                            ligs[g - 1][pos] = Integer.parseInt(lig.get(l).id_2.replace("geocache", ""));
                            pos++;
                        }
                    } else {
                        sl++;
                    }
                }
                pos = 0;
                sl = sizes[5];
            }else sg++;
        }
        return ligs;
    }

    public void edgesDist(int[][] ligs, CriacaoGrafo gG, SequentialSearchST<Integer, Geocache> geo_st, SequentialSearchST<Integer, Ligacoes> lig_st, int[] sizes){
        float max_d = 0.0f;
        int rs = sizes[5];
        for(int r = 1; r < rs; r++){
            if(lig_st.get(r) != null) {
                if (lig_st.get(r).distancia > max_d) max_d = lig_st.get(r).distancia;
            } else rs++;
        }

        int k;
        // g percorre 18 geocaches
        int sg = sizes[2];
        for(int g = 0; g < sg; g++) {
            if (geo_st.get(g + 1) != null) {
                // percorre enquanto existir informaçao
                for (int s = 0; ligs[g][s] != 0; s++) {
                    // de 1 a 50
                    int sk = sizes[5];
                    for (k = 1; k < sk; k++) {
                        if (lig_st.get(k) != null) {
                            if ((Integer.parseInt(lig_st.get(k).id_1.replace("geocache", "")) == g + 1) && (Integer.parseInt(lig_st.get(k).id_2.replace("geocache", "")) == ligs[g][s]))
                                break;
                        } else sk++;
                    }
                    float d = lig_st.get(k).distancia;
                    d = ((d * 3) / (max_d) + 0.4f);

                    Edge e = new Edge(g, ligs[g][s] - 1, d);
                    gG.addEdge(e);
                }
            } else sg++;
        }
    }

    public void edgesTemp(int[][] ligs, CriacaoGrafo gG, SequentialSearchST<Integer, Ligacoes> lig, int[] sizes){
        float max_t = 0.0f;
        for(int r = 1; r < sizes[5]; r++){
            if(lig.get(r) != null && lig.get(r).tempo > max_t) max_t = lig.get(r).tempo;
        }

        int k;
        // g percorre 18 geocaches
        for(int g = 0; g < sizes[2]; g++){
            // percorre enquanto existir informaçao
            for (int s = 0; ligs[g][s] != 0; s++){
                for(k = 1; k < sizes[5]; k++) {
                    if ((Integer.parseInt(lig.get(k).id_1.replace("geocache", "")) == g + 1) && (Integer.parseInt(lig.get(k).id_2.replace("geocache", "")) == ligs[g][s]))
                        break;
                }
                float t = lig.get(k).tempo;
                t = ((t * 3) / (max_t) + 0.4f);

                Edge e = new Edge(g, ligs[g][s]-1,t);
                gG.addEdge(e);
            }
        }
    }

    public int[][] getLigs() {
        return ligs;
    }
}
