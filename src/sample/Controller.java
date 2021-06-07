package sample;
import edu.princeton.cs.algs4.*;
import edu.ufp.inf.lp2_aed2.projeto.*;
import edu.ufp.inf.lp2_aed2.projeto.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TablePosition;

public class Controller {
    public Group graphGroup;

    public TextField id1;
    public TextField id2;
    public TextField distField;
    public TextField tempoField;

    public TableView<User> userTable;
    public TableColumn<User, Integer> idUserCol;
    public TableColumn<User, String> nomeUserCol;
    public TableColumn<User, String> tipoUserCol;

    public TableView<Regiao> regTable;
    public TableColumn<Regiao, String> idRegCol;
    public TableColumn<Regiao, String> nomeRegCol;
    public TableColumn<Regiao, String> idGeoRegCol;

    public TableView<Geocache> geoTable;
    public TableColumn<Geocache, String> idGeoCol;
    public TableColumn<Geocache, String> tipoGeoCol;
    public TableColumn<Geocache, String> cxGeoCol;
    public TableColumn<Geocache, String> cyGeoCol;
    public TableColumn<Geocache, String> itensGeoCol;
    public TableColumn<Geocache, String> iDRegGeoCol;

    public TableView<Item> itemTable;
    public TableColumn<Item, String> idItensCol;
    public TableColumn<Item, String> geoIdItensCol;
    public TableColumn<Item, String> nomeItensCol;

    public int[][] edges;
    public String input = "";
    public double radius = 15;

    public TextField userIdField;
    public TextField userNomeField;
    public TextField userTipoField;

    public TextField regIdField;
    public TextField regNomeField;
    public TextField geoIdField;
    public TextField geoTipoField;
    public TextField cXField;
    public TextField cYField;
    public TextField geoIdRegField;

    int[] sizes = new int[8];
    protected static final int GROUP_MARGIN = 10;
    SequentialSearchST<Integer, User> user_st = new SequentialSearchST<>();
    SequentialSearchST<Integer, Geocache> geo_st = new SequentialSearchST<>();
    SequentialSearchST<Integer, Item> item_st = new SequentialSearchST<>();
    SequentialSearchST<Integer, Ligacoes> lig_st = new SequentialSearchST<>();
    SequentialSearchST<Integer, Regiao> reg_st = new SequentialSearchST<>();
    SequentialSearchST<Integer, Travelbug> tvb_st = new SequentialSearchST<>();
    RedBlackBST<Integer, HistoricoVisited> hisV_st = new RedBlackBST<>();
    SequentialSearchST<Integer, HistoricoTB> hisTB_st = new SequentialSearchST<>();

    private CriacaoGrafo gG;

    public void createGraphGroup() {        //Regiao Butao
        graphGroup.getChildren().clear();

        for (int i = 1; i < gG.V(); i++) {
            for (Edge v : gG.adj(i-1)) {
                Line l = new Line(gG.getPositionsX(i-1), gG.getPositionsY(i-1), gG.getPositionsX(v.other(i-1)), gG.getPositionsY(v.other(i-1)));

                l.setStyle("-fx-stroke-width: " + v.weight());
                graphGroup.getChildren().add(l);
            }
        }

        for (int i = 0; i < gG.V(); i++) {
            Circle c = new Circle(gG.getPositionsX(i), gG.getPositionsY(i), radius, Color.LIGHTBLUE);
            Text id = new Text(" " + (i+1));
            if (geo_st.get(i+1) != null) {
                if (geo_st.get(i+1).id_reg == 1){
                    c.setFill(Color.web("FF5959"));
                } else if (geo_st.get(i+1).id_reg == 2) {
                    c.setFill(Color.web("#FACF5A"));
                } else if (geo_st.get(i+1).id_reg == 3) {
                    c.setFill(Color.web("#49BEB7"));
                } else {
                    c.setFill(Color.web("085F63"));
                }
            }

            StackPane sp = new StackPane();
            sp.setLayoutX(gG.getPositionsX(i) - radius);
            sp.setLayoutY(gG.getPositionsY(i) - radius);
            sp.getChildren().addAll(c, id);
            graphGroup.getChildren().add(sp);
        }
    }

    public void createGraphGroup_PremBasic() {           //Premium/Basic Butao
        graphGroup.getChildren().clear();
        for (int i = 0; i < gG.V(); i++) {
            for (Edge v : gG.adj(i)) {
                Line l = new Line(gG.getPositionsX(i), gG.getPositionsY(i), gG.getPositionsX(v.other(i)), gG.getPositionsY(v.other(i)));

                l.setStyle("-fx-stroke-width: " + v.weight());
                graphGroup.getChildren().add(l);
            }
        }
        for (int i = 0; i < gG.V(); i++) {
            Circle c = new Circle(gG.getPositionsX(i), gG.getPositionsY(i), radius, Color.web("#49BEB7",1));
            Text id = new Text(" " + (i + 1));

            if (geo_st.get(i+1) != null && geo_st.get(i+1).tipo.equals("premium")) {
                c.setFill(Color.web("FF5959"));
            }


            StackPane sp = new StackPane();
            sp.setLayoutX(gG.getPositionsX(i) - radius);
            sp.setLayoutY(gG.getPositionsY(i) - radius);
            sp.getChildren().addAll(c, id);
            graphGroup.getChildren().add(sp);
        }
    }

    public void createGraphGroup_Basic(ActionEvent actionEvent) {
        graphGroup.getChildren().clear();
        for (int i = 0; i < gG.V(); i++) {
            for (Edge v : gG.adj(i)) {
                if(geo_st.get(v.other(i)+1) != null && geo_st.get(i+1) != null && geo_st.get(v.other(i)+1).tipo.equals("basic") && geo_st.get(i+1).tipo.equals("basic")){
                    Line l = new Line(gG.getPositionsX(i), gG.getPositionsY(i), gG.getPositionsX(v.other(i)), gG.getPositionsY(v.other(i)));
                    l.setStyle("-fx-stroke-width: " + v.weight());
                    graphGroup.getChildren().add(l);
                }
            }
        }

        for (int i = 0; i < gG.V(); i++) {
            if(geo_st.get(i+1) != null && geo_st.get(i+1).tipo.equals("basic")) {
                Circle c = new Circle(gG.getPositionsX(i), gG.getPositionsY(i), radius, Color.web("#49BEB7"));
                Text id = new Text(" " + (i + 1));

                StackPane sp = new StackPane();
                sp.setLayoutX(gG.getPositionsX(i) - radius);
                sp.setLayoutY(gG.getPositionsY(i) - radius);
                sp.getChildren().addAll(c, id);
                graphGroup.getChildren().add(sp);
            }
        }
    }

    public void createGraphGroup_Prem(ActionEvent actionEvent) {
        graphGroup.getChildren().clear();
        for (int i = 0; i < gG.V(); i++) {
            for (Edge v : gG.adj(i)) {
                if(geo_st.get(v.other(i)+1) != null && geo_st.get(i+1) != null && geo_st.get(v.other(i)+1).tipo.equals("premium") && geo_st.get(i+1).tipo.equals("premium")){
                    Line l = new Line(gG.getPositionsX(i), gG.getPositionsY(i), gG.getPositionsX(v.other(i)), gG.getPositionsY(v.other(i)));
                    l.setStyle("-fx-stroke-width: " + v.weight());
                    graphGroup.getChildren().add(l);
                }
            }
        }

        for (int i = 0; i < gG.V(); i++) {
            if(geo_st.get(i+1) != null && geo_st.get(i+1).tipo.equals("premium")) {
                Circle c = new Circle(gG.getPositionsX(i), gG.getPositionsY(i), radius, Color.web("FF5959"));
                Text id = new Text(" " + (i + 1));

                StackPane sp = new StackPane();
                sp.setLayoutX(gG.getPositionsX(i) - radius);
                sp.setLayoutY(gG.getPositionsY(i) - radius);
                sp.getChildren().addAll(c, id);
                graphGroup.getChildren().add(sp);
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

    public ObservableList<User> userOL() {
        int s = sizes[0];
        ObservableList<User> user = FXCollections.observableArrayList();
        for (int i = 1; i <= s; i++) {
            if (user_st.get(i) != null) {
                user.add(new User(i, user_st.get(i).nome, user_st.get(i).tipo));
            }
            else s++;
        }
        return user;
    }

    public ObservableList<Regiao> regOL() {
        int s = sizes[1];
        ObservableList<Regiao> reg = FXCollections.observableArrayList();
        for (int i = 1; i <= s; i++) {
            if (reg_st.get(i) != null) {
                reg.add(new Regiao(i, reg_st.get(i).nome, reg_st.get(i).getN_caches()));
            }
            else s++;
        }
        return reg;
    }

    public ObservableList<Geocache> geoOL() {
        int s = sizes[2];
        ObservableList<Geocache> geo = FXCollections.observableArrayList();
        for (int i = 1; i <= s; i++) {
            if (geo_st.get(i) != null) {
                geo.add(new Geocache(geo_st.get(i).id, geo_st.get(i).tipo, geo_st.get(i).coordenadasX, geo_st.get(i).coordenadasY, geo_st.get(i).n_itens, geo_st.get(i).getId_reg()));
            }
            else s++;
        }
        return geo;
    }

    public ObservableList<Item> itemOL() {
        ObservableList<Item> item = FXCollections.observableArrayList();
        int s = sizes[3];
        for (int i = 1; i <= s; i++) {
            if (item_st.get(i) != null) {
                item.add(new Item(i, item_st.get(i).getId_geo(), item_st.get(i).item));
            }
            else s++;
        }
        return item;
    }

    public double getRadius() {
        return radius;
    }

    public void handleButtonAdd(javafx.event.ActionEvent actionEvent) {
        try {
            int flag = 1;
            int t = -1;
            int s = Integer.parseInt(id1.getText().replace("geocache", ""));
            String u = id2.getText();
            if (!u.equals("")) t = Integer.parseInt(u.replace("geocache", ""));

            //System.out.println(u + " " + s);
            int existe1 = 0;

            int[][] lig = gG.getLigs();

            if (t != -1) {           // Linhas
                for (int p = 1; p < sizes[2]; p++) {            // percorre as geocaches
                    if (p == s) {                               // quando acha a 1 geocache inserida
                        if (geo_st.contains(t)) {
                            for (int l = 0; lig[p][l] != 0; l++) {      //percorre array ligacoes
                                if (lig[p][l] == t) {
                                    existe1++;
                                }
                            }
                            if (existe1 == 0) {
                                /*String[] lines = details.getText().split(";");
                                int tempo = Integer.parseInt(lines[0]);
                                float distancia = Float.parseFloat(lines[1]);*/
                                int tempo = Integer.parseInt(tempoField.getText());
                                float distancia = Float.parseFloat(distField.getText());

                                Ligacoes n_lig = new Ligacoes();
                                sizes[5]++;

                                n_lig.id_1 = id1.getText();
                                n_lig.id_2 = id2.getText();
                                n_lig.tempo = tempo;
                                n_lig.distancia = distancia;

                                lig_st.put(sizes[5] - 1, n_lig);

                                flag = 3;

                                break;
                            }

                        }
                    }
                }
                graphGroup.getChildren().clear();
                gG = null;
                gG = new CriacaoGrafo(sizes, geo_st, lig_st);
                int[][] array = gG.create_arraysLig(sizes, geo_st, lig_st);
                gG.edgesDist(array, gG, geo_st, lig_st, sizes);
                createGraphGroup();

            }if (flag == 3) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Edge Adicionada", ButtonType.CLOSE);
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Vertice nao Existe", ButtonType.CLOSE);
                alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            System.out.println(e);
        }
    }

    public void handleButtonEdit(ActionEvent actionEvent) {
    }

    public void handleButtonRemove(ActionEvent actionEvent) {
    }

    public void createDist(ActionEvent actionEvent) {
        graphGroup.getChildren().clear();
        gG = null;

        gG = new CriacaoGrafo(sizes, geo_st, lig_st);
        edges = gG.create_arraysLig(sizes, geo_st, lig_st);
        gG.edgesDist(edges, gG, geo_st, lig_st, sizes);
        createGraphGroup();
    }

    public void createTempo(ActionEvent actionEvent) {
        graphGroup.getChildren().clear();
        gG = null;

        gG = new CriacaoGrafo(sizes, geo_st, lig_st);
        edges = gG.create_arraysLig(sizes, geo_st, lig_st);
        gG.edgesTemp(edges, gG, lig_st, sizes);
        createGraphGroup();
    }

    public void startMain(ActionEvent actionEvent) {

        lerFile();

        graphGroup.getChildren().clear();
        gG = null;

        gG = new CriacaoGrafo(sizes, geo_st, lig_st);
        edges = gG.create_arraysLig(sizes, geo_st, lig_st);
        gG.edgesDist(edges, gG, geo_st, lig_st, sizes);
        createGraphGroup();

        createTables();
    }

    private void createTables() {
        idUserCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomeUserCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tipoUserCol.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        userTable.setItems(userOL());
        userTable.setEditable(true);

        idRegCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomeRegCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
        idGeoRegCol.setCellValueFactory(new PropertyValueFactory<>("n_caches"));
        regTable.setItems(regOL());

        idGeoCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        tipoGeoCol.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        cxGeoCol.setCellValueFactory(new PropertyValueFactory<>("coordenadasX"));
        cyGeoCol.setCellValueFactory(new PropertyValueFactory<>("coordenadasY"));
        itensGeoCol.setCellValueFactory(new PropertyValueFactory<>("n_itens"));
        iDRegGeoCol.setCellValueFactory(new PropertyValueFactory<>("id_reg"));
        geoTable.setItems(geoOL());

        idItensCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        geoIdItensCol.setCellValueFactory(new PropertyValueFactory<>("id_geo"));
        nomeItensCol.setCellValueFactory(new PropertyValueFactory<>("item"));
        itemTable.setItems(itemOL());

    }

    public void addAndEditUser() {
        userTable.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (userTable.getEditingCell() == null) {
                if (event.getCode().isLetterKey() || event.getCode().isDigitKey()) {
                    System.out.print(input + "\t");
                    input = input + event.getText();
                    System.out.println(input);
                }
            }


            if (event.getCode() == KeyCode.ENTER && !input.equals("")) {
                //System.out.println(input);
                //editPosUser();
                System.out.println(input);
            }
            if (event.getCode() == KeyCode.HOME && input.equals("")) {
                TablePosition pos = userTable.getFocusModel().getFocusedCell();

                if (pos.getRow() == -1) {
                    userTable.getSelectionModel().select(0);
                } else if (pos.getRow() == userTable.getItems().size() - 1) {
                    //addRowUser();
                } else if (pos.getRow() < userTable.getItems().size() - 1) {
                    userTable.getSelectionModel().clearAndSelect(pos.getRow() + 1, pos.getTableColumn());
                }
            }
            if (event.getCode() == KeyCode.DELETE) {
                TablePosition pos = userTable.getFocusModel().getFocusedCell();

                if (pos.getRow() == -1) {
                    userTable.getSelectionModel().select(0);
                } else {
                    removeSelectedRowsUser();
                }
            }
        });

        userTable.getSelectionModel().setCellSelectionEnabled(true);
        userTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        userTable.getSelectionModel().selectFirst();

    }

    public void removeSelectedRowsUser() {
        userTable.getItems().removeAll(userTable.getSelectionModel().getSelectedItems());
        userTable.getSelectionModel().clearSelection();
    }

    public void lerFile() {
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
                u.id = id;
                u.nome = data[1];
                u.tipo = data[2];

                user_st.put(id, u);
            }

            //Leitura da Regiao
            sizes[1] = scan.nextInt();
            scan.nextLine();
            int reg_id = 1;
            int item_id = 1;
            for (int i = 0; i < sizes[1]; i++) {
                Regiao reg = new Regiao();

                String[] data = scan.nextLine().split(", ");
                reg.id = reg_id;
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
                        item.id = item_id;
                        item.item = data1[4 + k];
                        item.id_geo = data1[0];
                        item_st.put(sizes[3] + 1, item);
                        sizes[3]++;
                        item_id++;
                    }
                    geo_st.put(idgeo, geo);
                }
                reg_id++;
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
    }

    public void userAdd(ActionEvent actionEvent) {
        User user = new User();
        int id = 0; String nome = "0",tipo = "0";

        if(!userIdField.getText().equals("")){
            id = Integer.parseInt(userIdField.getText());
            if(!userNomeField.getText().equals("") && !userTipoField.getText().equals("")){
                if(user_st.contains(id)){
                    Alert alert = new Alert(Alert.AlertType.WARNING, "O User ja existe!", ButtonType.OK);
                    alert.showAndWait();
                    return;
                }else{
                    tipo = userTipoField.getText();
                    if(tipo.equals("basic") || tipo.equals("premium") || tipo.equals("admin")){
                        nome = userNomeField.getText();
                        user.addUser(id,nome,tipo,sizes,user_st);
                        createTables();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING, "O Tipo não existe!", ButtonType.OK);
                        alert.showAndWait();
                        return;
                    }
                }
            }else if(!userNomeField.getText().equals("") && userTipoField.getText().equals("")){
                if(user_st.contains(id)){
                    nome = userNomeField.getText();
                    user.editUser("nome",nome,id,user_st);
                    createTables();
                }else{
                    Alert alert = new Alert(Alert.AlertType.WARNING, "O User não existe!", ButtonType.OK);
                    alert.showAndWait();
                    return;
                }
            }else if(userNomeField.getText().equals("") && !userTipoField.getText().equals("")){
                if(user_st.contains(id)){
                    tipo = userTipoField.getText();
                    if(tipo.equals("basic") || tipo.equals("premium") || tipo.equals("admin")){
                        user.editUser("tipo",tipo,id,user_st);
                        createTables();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING, "O Tipo não existe!", ButtonType.OK);
                        alert.showAndWait();
                        return;
                    }
                }else{
                    Alert alert = new Alert(Alert.AlertType.WARNING, "O User não existe!", ButtonType.OK);
                    alert.showAndWait();
                    return;
                }
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING, "Escreva o ID que pretende adicionar!", ButtonType.OK);
            alert.showAndWait();
            return;
        }
    }

    public void userRemove(ActionEvent actionEvent) {
        User u = new User();
        if(!userIdField.getText().equals("")){
            int id = Integer.parseInt(userIdField.getText());
            u.removeUser(id,sizes,user_st);
            createTables();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Escreva o ID que pretende remover!", ButtonType.OK);
            alert.showAndWait();
            return;
        }
    }

    public void addReg(ActionEvent actionEvent) {
        Regiao reg = new Regiao();
        int id = 0; String nome = "0";

        if(!regIdField.getText().equals("")){
            id = Integer.parseInt(regIdField.getText());
            if(!regNomeField.getText().equals("")){
                if(reg_st.contains(id)){
                    nome = regNomeField.getText();
                    reg.editRegiao("nome",nome,id,reg_st);
                    createTables();
                }else{
                        nome = regNomeField.getText();
                        reg.addRegiao(id,nome,sizes,reg_st);
                        createTables();
                }
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING, "Escreva o ID que pretende adicionar!", ButtonType.OK);
            alert.showAndWait();
            return;
        }
    }

    public void removeReg(ActionEvent actionEvent) {
        Regiao r = new Regiao();
        if(!regIdField.getText().equals("")){
            int id = Integer.parseInt(regIdField.getText());
            r.removeRegiao(id,sizes,reg_st,geo_st,item_st,lig_st,hisV_st);

            gG = new CriacaoGrafo(sizes, geo_st, lig_st);
            edges = gG.create_arraysLig(sizes, geo_st, lig_st);
            gG.edgesDist(edges, gG, geo_st, lig_st, sizes);
            createGraphGroup();

            createTables();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Escreva o ID que pretende remover!", ButtonType.OK);
            alert.showAndWait();
            return;
        }
    }

    public void addGeocache(ActionEvent actionEvent) {
        Geocache geo = new Geocache();
        String id = geoIdField.getText();
        String tipo = geoTipoField.getText();
        Float cX = Float.parseFloat(cXField.getText());
        Float cY = Float.parseFloat(cYField.getText());
        int id_reg = Integer.parseInt(geoIdRegField.getText());
        geo.addGeocache(id,tipo,cX,cY,id_reg,sizes,geo_st,reg_st);

        gG = new CriacaoGrafo(sizes, geo_st, lig_st);
        edges = gG.create_arraysLig(sizes, geo_st, lig_st);
        gG.edgesDist(edges, gG, geo_st, lig_st, sizes);
        createGraphGroup();
        createTables();
    }
}
