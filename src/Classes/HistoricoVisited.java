package Classes;

import java.util.Arrays;

public class HistoricoVisited {
    public String user;
    public int n_visited;
    public int[] visited;
    public Date[] date;

    public HistoricoVisited(String user, int n_visited, int[] visited, Date[] date) {
        this.user = user;
        this.n_visited = n_visited;
        this.visited = visited;
        this.date = date;
    }

    public HistoricoVisited() {
        this.user = "";
        this.n_visited = 0;
        this.n_visited = 0;
    }

    @Override
    public String toString() {
        return "Historico{" +
                ", user='" + user + '\'' +
                ", n_visited=" + n_visited +
                ", visited=" + Arrays.toString(visited) +
                ", date=" + Arrays.toString(date) +
                '}';
    }

}
