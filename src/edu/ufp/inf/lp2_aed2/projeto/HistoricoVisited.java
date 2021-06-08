package edu.ufp.inf.lp2_aed2.projeto;

import java.util.Arrays;

public class HistoricoVisited {
    public int id;
    public String user;
    public int n_visited;
    public String visited_s;
    public int[] visited;
    public String date_s;
    public Date[] date;

    public HistoricoVisited(String user, int n_visited, int[] visited, Date[] date) {
        this.user = user;
        this.n_visited = n_visited;
        this.visited = visited;
        this.date = date;
    }

    public HistoricoVisited(int id, String user, int n_visited, int[] visited, Date[] date) {
        this.id = id;
        this.user = user;
        this.n_visited = n_visited;
        this.visited = visited;
        this.date = date;
    }

    public HistoricoVisited(int id, String user, int n_visited, String visited_s, String date_s) {
        this.id = id;
        this.user = user;
        this.n_visited = n_visited;
        this.visited_s = visited_s;
        this.date_s = date_s;
    }

    public HistoricoVisited() {
        this.user = "";
        this.n_visited = 0;
        this.n_visited = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getN_visited() {
        return n_visited;
    }

    public void setN_visited(int n_visited) {
        this.n_visited = n_visited;
    }

    public String getVisited_s() {
        return visited_s;
    }

    public void setVisited_s(String visited_s) {
        this.visited_s = visited_s;
    }

    public int[] getVisited() {
        return visited;
    }

    public void setVisited(int[] visited) {
        this.visited = visited;
    }

    public String getDate_s() {
        return date_s;
    }

    public void setDate_s(String date_s) {
        this.date_s = date_s;
    }

    public Date[] getDate() {
        return date;
    }

    public void setDate(Date[] date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "HistoricoVisited{" +
                "id=" + id +
                ", user='" + user + '\'' +
                ", n_visited=" + n_visited +
                ", visited_s='" + visited_s + '\'' +
                ", visited=" + Arrays.toString(visited) +
                ", date_s='" + date_s + '\'' +
                ", date=" + Arrays.toString(date) +
                '}';
    }
}
