package sample;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.SequentialSearchST;
import edu.ufp.inf.lp2_aed2.projeto.Geocache;

import java.util.Arrays;

import static java.lang.Math.*;

public class CriacaoGrafo extends Graph {

    private int[] positionsX;
    private int[] positionsY;

    public CriacaoGrafo(int x, SequentialSearchST<Integer, Geocache> geo) {
        super(x);
        positionsX = new int[x];
        positionsY = new int[x];
        setPositions(x, geo);
    }

    public CriacaoGrafo(CriacaoGrafo gG) {
        super(gG.V());
        positionsX = gG.positionsX;
        positionsY = gG.positionsY;
    }

    public CriacaoGrafo(CriacaoGrafo gG, int newSize, SequentialSearchST<Integer, Geocache> geo) {
        super(newSize);
        positionsX = new int[newSize];
        positionsY = new int[newSize];

        for (int i = 0; i < gG.V() && i < newSize; i++) {
            positionsX[i] = gG.positionsX[i];
            positionsY[i] = gG.positionsY[i];
        }

        setPositions(gG.V(), geo);

        for (int v = 0; v < gG.V(); v++) {
            for (Integer adj : gG.adj(v)) {
                this.addEdge(v, adj);
            }
        }
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

                x = (long) ((x * 700) / (max_x * pow(10, 7)));
                y = (long) ((y * 390) / (max_y * pow(10, 7)));
                System.out.println(x + " " + y);

                int[] f = resolveCollision(a, (int) x, (int) y);
                System.out.println(Arrays.toString(f));

                positionsX[a] = f[0];
                positionsY[a] = f[1];
                //System.out.println(Arrays.toString(positionsX) + "\n" + Arrays.toString(positionsY) + "\n\n");
            }
        }
    }

    public boolean containsEdge(int v, int a) {
        for (Integer adj : this.adj(v))
            if (adj == a) return true;

        for (Integer adj : this.adj(a))
            if (adj == v) return true;

        return false;
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
        int backx = 0, backy = 0;

        for (int z = 0; z < a; z++) {
            if (checkCollision(positionsX[z], x))
            if (checkCollision(positionsY[z], y))
                collisions++;
            //System.out.println(z + " Comparacao " + positionsX[z] + " - " + x + " = " + abs(positionsX[z] - x) + " | " + positionsY[z] + " - " + y + " = " + abs(positionsY[z] - y) + "\t");
        }
        //System.out.println(a + " " + collisions + "\n");

        while (collisions > 0) {
            Controller c = new Controller();
            if (checkCollision(positionsX[px], x)) {
                if (/*x + c.getRadius() < 690 && backx != 1 && */x < 700/2) {
                    x += c.getRadius() *2;
                } else {
                    x -= (c.getRadius() *2) + 1;
                    //backx = 1;
                }
            } else {
                //backx = 0;
                px++;
            }

            if (checkCollision(positionsY[py], y)) {
                if (/*y + c.getRadius() < 440 && backy != 1 && */y < 450/2) {
                    y += c.getRadius() *2;
                } else {
                    y -= (c.getRadius() *2) + 1;
                    //backy = 1;
                }
            } else {
                //backy = 0;
                py++;
            }

            collisions = 0;
            for (int z = 0; z < a; z++) {
                //System.out.println(positionsX[z] + " " + x + "\t" + positionsY[z] + " " + y);
                if (checkCollision(positionsX[z], x))
                    if (checkCollision(positionsY[z], y))
                        collisions++;
                //System.out.println(z + " Comparacao " + positionsX[z] + " - " + x + " = " + abs(positionsX[z] - x) + " | " + positionsY[z] + " - " + y + " = " + abs(positionsY[z] - y) + "\t");
            }
            //System.out.println(a + " " + collisions + "\n");
        }
        return new int[]{x, y};



            /*while (collisionsx > 0 && w < a) {
                System.out.print("x > 0 : " + a + " " + w + " " + positionsX[w] + " " + x + "\t");
                Controller c = new Controller();
                if (checkCollision(positionsX[w], x)) {
                    System.out.print(" colisao ");

                    if (x + c.getRadius() < 690 && back != 1) {
                        y += c.getRadius();
                        System.out.print(" dentro ");
                    }
                    else {
                        x -= (c.getRadius() + 1); back = 1;
                        System.out.print("sai");
                    }
                    flag = 1;
                    w = 0;
                } else {
                    System.out.print(" continuei ");
                    if (flag == 1) collisionsx--;
                    flag = 0;
                    back = 0;
                    w++;
                }
                System.out.println();
            }
            System.out.print(" cabou colisao " + x);
            flag = 0;
            back = 0;

            while (collisionsy > 0 && v < a) {
                System.out.print("y > 0");
                Controller c = new Controller();
                if (checkCollision(positionsY[v], y)) {
                    System.out.print(" colisao ");
                    if (y + c.getRadius() < 440 && back != 1) {y += c.getRadius();
                        System.out.print(" dentro ");}
                    else {
                        System.out.print(" sai ");
                        y -= (c.getRadius() + 1);
                        back = 1;
                    }
                    flag = 1;
                    v = 0;
                } else {
                    System.out.print(" continuei ");
                    if (flag == 1) collisionsy--;
                    flag = 0;
                    back = 0;
                    v++;
                }
            }
            System.out.println(" cabou colisao ");

            collisionsx = 0;
            collisionsy = 0;
            for (int z = 0; z < a; z++) {
                if (checkCollision(positionsX[z], x)) collisionsx++;
                if (checkCollision(positionsY[z], y)) collisionsy++;
            }
            flag = 0;
            back = 0;
            w = 0;
            v = 0;
            System.out.println(collisionsx + " " + collisionsy + "\t");*/

            //System.out.println(collisionsx + " " + collisionsy);

        }
}
