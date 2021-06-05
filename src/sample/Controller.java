package sample;

import edu.princeton.cs.algs4.*;
import edu.ufp.inf.lp2_aed2.projeto.*;

import edu.ufp.inf.lp2_aed2.projeto.Date;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Controller {
    protected static final int GROUP_MARGIN = 10;
    public TextField id1;
    public TextField id2;
    public TextArea details;
    public int[][] edges;
    public Group graphGroup;
    public double radius = 15;
    int[] sizes = new int[8];
    SequentialSearchST<Integer, User> user_st = new SequentialSearchST<>();
    SequentialSearchST<Integer, Geocache> geo_st = new SequentialSearchST<>();
    SequentialSearchST<Integer, Item> item_st = new SequentialSearchST<>();
    SequentialSearchST<Integer, Ligacoes> lig_st = new SequentialSearchST<>();
    SequentialSearchST<Integer, Regiao> reg_st = new SequentialSearchST<>();
    SequentialSearchST<Integer, Travelbug> tvb_st = new SequentialSearchST<>();
    RedBlackBST<Integer, HistoricoVisited> hisV_st = new RedBlackBST<>();
    SequentialSearchST<Integer, HistoricoTB> hisTB_st = new SequentialSearchST<>();
    private CriacaoGrafo gG;

    public void startController(int s[]) {
        gG = new CriacaoGrafo(s, geo_st, lig_st);
        createGraphGroup();
    }

    public void createGraphGroup() {
        for (int i = 0; i < gG.V(); i++) {
            Circle c = new Circle(gG.getPositionsX(i), gG.getPositionsY(i), radius, Color.LIGHTBLUE);
            Text id = new Text(" " + (i + 1));
            if (geo_st.get(i) != null && geo_st.get(i).tipo.equals("premium")) {
                c.setFill(Color.LIGHTPINK);
            }

            StackPane sp = new StackPane();
            sp.setLayoutX(gG.getPositionsX(i) - radius);
            sp.setLayoutY(gG.getPositionsY(i) - radius);
            sp.getChildren().addAll(c, id);
            graphGroup.getChildren().add(sp);

            for (Edge v : gG.adj(i)) {
                //System.out.println(gG.getPositionsX(i) + " " + gG.getPositionsY(i) + " " + gG.getPositionsX(v.other(i)) + " " + gG.getPositionsY(v.other(i)));
                Line l = new Line(gG.getPositionsX(i), gG.getPositionsY(i), gG.getPositionsX(v.other(i)), gG.getPositionsY(v.other(i)));
                l.setStyle("-fx-stroke: lightgrey");
                l.setStyle("-fx-opacity: 0.1");

                graphGroup.getChildren().add(l);
            }
        }
    }

    public void createNewGraph(int[] nVertices) {
        if (gG == null) {
            gG = new CriacaoGrafo(nVertices, geo_st, lig_st);
        } else {
            gG = new CriacaoGrafo(gG, nVertices, geo_st);
        }
    }

    public void startMain(javafx.event.ActionEvent actionEvent) {
        for (int i = 0; i < 8; i++) sizes[i] = 0;

        // Leitura do ficheiro input.txt
        try {
            Scanner scan = new Scanner(new BufferedReader(new FileReader("data/input.txt")));
            sizes[0] = scan.nextInt();
            scan.nextLine();
            // Leitura do user
            for (int i = 0; i < sizes[0]; i++) {
                User u = new User();

                String[] data = scan.nextLine().split(", ");
                int id = Integer.parseInt(data[0]);
                u.nome = data[1];
                u.tipo = data[2];

                user_st.put(id, u);
            }

            //Leitura da Regiao
            sizes[1] = scan.nextInt();
            scan.nextLine();

            for (int i = 0; i < sizes[1]; i++) {
                Regiao reg = new Regiao();

                String[] data = scan.nextLine().split(", ");
                reg.nome = data[0];
                reg.n_caches = Integer.parseInt(data[1]);


                // Leitura da Geocache
                for (int j = 0; j < reg.n_caches; j++) {
                    sizes[2]++;
                    Geocache geo = new Geocache();

                    String[] data1 = scan.nextLine().split(", ");
                    geo.id = data1[0];
                    int idgeo = Integer.parseInt(geo.id.replace("geocache", ""));
                    //System.out.println(idgeo);
                    geo.tipo = data1[1];
                    geo.coordenadasX = Float.parseFloat(data1[2]);
                    geo.coordenadasY = Float.parseFloat(data1[3]);
                    geo.n_itens = Integer.parseInt(data1[4]);
                    geo.id_reg = i + 1;

                    // Leitura dos itens
                    for (int k = 1; k <= geo.n_itens; k++) {
                        Item item = new Item();

                        item.item = data1[4 + k];
                        item.id_geo = geo.id;
                        item_st.put(sizes[3] + 1, item);
                        sizes[3]++;
                    }
                    geo_st.put(idgeo, geo);
                }
                reg_st.put(i + 1, reg);
            }

            // Leitura das ligacoes
            sizes[5] = scan.nextInt();
            scan.nextLine();

            for (int i = 0; i < sizes[5]; i++) {
                Ligacoes l = new Ligacoes();

                String[] data = scan.nextLine().split(", ");
                l.id_1 = data[0];
                l.id_2 = data[1];
                l.distancia = Float.parseFloat(data[2]);
                l.tempo = Integer.parseInt(data[3]);

                lig_st.put(i + 1, l);
            }

            // Leitura das travelBugs
            sizes[4] = scan.nextInt();
            scan.nextLine();

            for (int i = 0; i < sizes[4]; i++) {
                Travelbug tb = new Travelbug();

                String[] data = scan.nextLine().split(", ");
                tb.id = data[0];
                tb.user = data[1];
                tb.geo_inicial = data[2];
                tb.geo_destino = data[3];

                tvb_st.put(i + 1, tb);
            }

            // Leitura do Historico de visitas
            scan = new Scanner(new BufferedReader(new FileReader("data/logs.txt")));
            sizes[6] = scan.nextInt();
            scan.nextLine();

            for (int i = 1; i <= sizes[6]; i++) {
                HistoricoVisited histV = new HistoricoVisited();
                String[] data = scan.nextLine().split(", ");
                histV.user = data[0];
                histV.n_visited = Integer.parseInt(data[1]);
                histV.visited = new int[histV.n_visited];
                histV.date = new Date[histV.n_visited];

                for (int y = 0; y < histV.n_visited; y++) {
                    histV.visited[y] = Integer.parseInt(data[y + 2]);
                }

                // Data
                String[] data1 = scan.nextLine().split(", ");
                for (int y = 0; y < histV.n_visited; y++) {
                    String[] aux = data1[y].split("/");
                    histV.date[y] = new Date(Integer.parseInt(aux[0]), Integer.parseInt(aux[1]), Integer.parseInt(aux[2]));
                    //System.out.println(y + "   " + hist.date[y]);
                }
                hisV_st.put(i, histV);
            }


            // Leitura do historico de TB
            sizes[7] = scan.nextInt();
            scan.nextLine();
            for (int z = 1; z <= sizes[7]; z++) {
                HistoricoTB histTB = new HistoricoTB();
                String[] data2 = scan.nextLine().split(", ");
                histTB.user = data2[0];
                histTB.id_tb = Integer.parseInt(data2[1]);
                histTB.tb_start = Integer.parseInt(data2[2]);
                histTB.tb_end = Integer.parseInt(data2[3]);
                hisTB_st.put(z, histTB);
            }
        } catch (FileNotFoundException erro) {
            System.out.println(erro);
        }

        gG = new CriacaoGrafo(sizes, geo_st, lig_st);
        edges = gG.create_arraysLig(sizes, lig_st);
        gG.edgesDist(edges, gG, lig_st, sizes);
        //System.out.println(gG);
        createGraphGroup();

    }

    public double getRadius() {
        return radius;
    }

    public void handleButtonAdd(javafx.event.ActionEvent actionEvent) {
        try {
            int flag = 1;
            int s = Integer.parseInt(id1.getText().replace("geocache", ""));
            int t = Integer.parseInt(id2.getText().replace("geocache", ""));


            int[][] lig = gG.getLigs();

            if (t != 0) {
                for (int p = 1; p < sizes[2]; p++) {
                    if (p == s) {
                        for (int l = 0; l < 10; l++) {
                            if (lig[p][l] != t) {
                                String[] lines = details.getText().split(";");
                                double weight = Double.parseDouble(lines[1]);
                                flag = 2;
                                Edge edge = new Edge(s - 1, t - 1, weight);
                                gG.addEdge(edge);
                                break;
                            }
                        }
                    }
                }
                    graphGroup.getChildren().clear();
                    createGraphGroup();

            } else {
                for (int p = 1; p < sizes[2]; p++) {
                    if (p == s) {
                        for (int l = 0; l < 10; l++) {
                            String[] lines = details.getText().split("\n");
                            for (String line : lines) {
                                String[] position = line.split(";");
                                int r = Integer.parseInt(position[0]);
                                double weight = Double.parseDouble(position[1]);
                                if (lig[p][l] != r) {
                                    flag = 3;
                                    Edge edge = new Edge(s - 1, r - 1, weight);
                                    gG.addEdge(edge);
                                }
                            }
                        }
                    }
                }
                graphGroup.getChildren().clear();
                createGraphGroup();
            }
            if (flag == 2) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Vertice Adicionado", ButtonType.CLOSE);
                alert.showAndWait();
            } else if (flag == 3) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Vertices Adicionados", ButtonType.CLOSE);
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Vertice nao Existe", ButtonType.CLOSE);
                alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            System.out.println(e);
        }
    }

    public void handleButtonEdit(javafx.event.ActionEvent actionEvent) {
    }

    public void handleButtonRemove(javafx.event.ActionEvent actionEvent) {
    }

    public void clearMain(ActionEvent actionEvent) {
        graphGroup.getChildren().clear();
        gG = null;
    }
}