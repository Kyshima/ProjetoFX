package edu.ufp.inf.lp2_aed2.projeto;

import java.util.Arrays;

public class HistoricoTB {
    public String user;
    public int id_tb;
    public int tb_start;
    public int tb_end;

    public HistoricoTB(String user, int n_tb, int tb_start, int tb_end) {
        this.user = user;
        this.id_tb = n_tb;
        this.tb_start = tb_start;
        this.tb_end = tb_end;
    }

    public HistoricoTB() {
        this.user = "";
        this.id_tb = 0;
        this.tb_start = 0;
        this.tb_end = 0;
    }

    @Override
    public String toString() {
        return "Historico{" +
                ", user='" + user + '\'' +
                ", n_tb=" + id_tb +
                ", tb_start=" + tb_start +
                ", tb_end=" + tb_end +
                '}';
    }
}
