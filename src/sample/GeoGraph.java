package sample;
import edu.princeton.cs.algs4.Graph;
import java.util.Random;

public class GeoGraph extends Graph {

    private int[] positionsX;
    private int[] positionsY;

    public GeoGraph(int V) {
        super(V);
        positionsX = new int[V];
        positionsY = new int[V];

        setRandomPositions();
    }

    private void setRandomPositions() {
        for(int i = 0; i < V(); i++) {
            Random r = new Random();
            setPositionsX(i, (int) r.nextDouble() * 600);
            setPositionsY(i, (int) r.nextDouble() * 371);
        }
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
