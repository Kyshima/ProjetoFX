package sample;

import edu.princeton.cs.algs4.*;
import edu.ufp.inf.lp2_aed2.projeto.*;

import edu.ufp.inf.lp2_aed2.projeto.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Objects;
import java.util.Scanner;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TablePosition;
import javafx.util.Callback;

public class Controller {
    public Group graphGroup;

    public TextField id1;
    public TextField id2;
    public TextArea details;

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

    public TextField idUserField;
    public TextField nomeUserField;
    public TextField tipoUserField;

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

    public void createGraphGroup() {
        for (int i = 0; i < gG.V(); i++) {
            for (Edge v : gG.adj(i)) {
                Line l = new Line(gG.getPositionsX(i), gG.getPositionsY(i), gG.getPositionsX(v.other(i)), gG.getPositionsY(v.other(i)));

                l.setStyle("-fx-stroke-width: " + v.weight());
                graphGroup.getChildren().add(l);
            }
        }

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
        }
    }

    public void createNewGraph(int[] nVertices) {
        if (gG == null) {
            gG = new CriacaoGrafo(nVertices, geo_st, lig_st);
        } else {
            gG = new CriacaoGrafo(gG, nVertices, geo_st);
        }
    }

    public ObservableList<User> userOL(){
        ObservableList<User> user = FXCollections.observableArrayList();
        for(int i = 1; i <= sizes[0]; i++){
            if(user_st.get(i) != null){
                user.add(new User(user_st.get(i).getId(), user_st.get(i).nome, user_st.get(i).tipo));
            }
        }
        return user;
    }

    public ObservableList<Regiao> regOL(){
        ObservableList<Regiao> reg = FXCollections.observableArrayList();
        for(int i = 1; i <= sizes[1]; i++){
            if(reg_st.get(i) != null){
                reg.add(new Regiao(reg_st.get(i).getId(), reg_st.get(i).nome, reg_st.get(i).getN_caches()));
            }
        }
        return reg;
    }

    public ObservableList<Geocache> geoOL(){
        ObservableList<Geocache> geo = FXCollections.observableArrayList();
        for(int i = 0; i <= sizes[2]; i++){
            if(geo_st.get(i) != null){
                geo.add(new Geocache(geo_st.get(i).id,geo_st.get(i).tipo,geo_st.get(i).coordenadasX,geo_st.get(i).coordenadasY,geo_st.get(i).n_itens,geo_st.get(i).getId_reg()));
            }
        }
        return geo;
    }

    public ObservableList<Item> itemOL(){
        ObservableList<Item> item = FXCollections.observableArrayList();
        for(int i = 0; i <= sizes[3]; i++){
            if(item_st.get(i) != null){
                item.add(new Item(item_st.get(i).getId(), item_st.get(i).getId_geo(), item_st.get(i).item));
            }
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
            if(!u.equals("")) t = Integer.parseInt(u.replace("geocache", ""));

            //System.out.println(u + " " + s);
            int existe1 = 0, existe2 = 0;

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
                                String[] lines = details.getText().split(";");
                                int tempo = Integer.parseInt(lines[0]);
                                float distancia = Float.parseFloat(lines[1]);

                                Ligacoes n_lig = new Ligacoes();
                                sizes[5]++;

                                n_lig.id_1 = id1.getText();
                                n_lig.id_2 = id2.getText();
                                n_lig.tempo = tempo;
                                n_lig.distancia = distancia;

                                lig_st.put(sizes[5]-1, n_lig);

                                flag = 3;

                                break;
                            }

                        }
                    }
                }
                    graphGroup.getChildren().clear();
                    gG = null;
                    gG = new CriacaoGrafo(sizes, geo_st, lig_st);
                    int[][] array = gG.create_arraysLig(sizes, lig_st);
                    gG.edgesDist(array, gG, lig_st, sizes);
                    createGraphGroup();

            } else {                // Vertices
                for (int p = 1; p < sizes[2]; p++) {
                    if(!geo_st.contains(s)){
                        Geocache geo = new Geocache();
                        String[] lines = details.getText().split(";");
                        geo.id = id1.getText();
                        geo.tipo = lines[0];
                        geo.coordenadasX = Float.parseFloat(lines[1]);
                        geo.coordenadasY = Float.parseFloat(lines[2]);
                        geo.n_itens = Integer.parseInt(lines[3]);
                        geo.id_reg = Integer.parseInt(lines[4]);
                        sizes[2]++;
                        geo_st.put(sizes[2], geo);
                        flag = 2;
                        break;
                    }
                }
                graphGroup.getChildren().clear();
                gG = null;
                gG = new CriacaoGrafo(sizes, geo_st, lig_st);
                int[][] array = gG.create_arraysLig(sizes, lig_st);
                gG.edgesDist(array, gG, lig_st, sizes);
                createGraphGroup();
            }
            if (flag == 2) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Vertice Adicionado", ButtonType.CLOSE);
                alert.showAndWait();
            } else if (flag == 3) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Edges Adicionados", ButtonType.CLOSE);
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
        edges = gG.create_arraysLig(sizes, lig_st);
        gG.edgesDist(edges, gG, lig_st, sizes);
        createGraphGroup();
    }

    public void createTempo(ActionEvent actionEvent) {
        graphGroup.getChildren().clear();
        gG = null;

        gG = new CriacaoGrafo(sizes, geo_st, lig_st);
        edges = gG.create_arraysLig(sizes, lig_st);
        gG.edgesTemp(edges, gG, lig_st, sizes);
        createGraphGroup();
    }

    public void startMain(javafx.event.ActionEvent actionEvent) {

        lerFile();

        graphGroup.getChildren().clear();
        gG = null;

        gG = new CriacaoGrafo(sizes, geo_st, lig_st);
        edges = gG.create_arraysLig(sizes, lig_st);
        gG.edgesDist(edges, gG, lig_st, sizes);
        createGraphGroup();

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

        addAndEditUser();
        addAndEditReg();
        addAndEditGeo();
        addAndEditItens();
    }

    public void addAndEditUser(){
        userTable.addEventFilter(KeyEvent.KEY_PRESSED, event -> {

            if( event.getCode() == KeyCode.ENTER) {
                //System.out.println(input);
                editPosUser();
                input = "";
                return;
            }

            if(userTable.getEditingCell() == null) {
                if(event.getCode().isLetterKey() || event.getCode().isDigitKey()) {
                    input = input + event.getText();
                }
            }
        });

        userTable.addEventFilter(KeyEvent.KEY_RELEASED, event -> {

            if(event.getCode() == KeyCode.ENTER && input.equals("")) {
                TablePosition pos = userTable.getFocusModel().getFocusedCell();

                if (pos.getRow() == -1) {
                    userTable.getSelectionModel().select(0);
                } else if (pos.getRow() == userTable.getItems().size() -1) {
                    addRowUser();
                } else if (pos.getRow() < userTable.getItems().size() -1) {
                    userTable.getSelectionModel().clearAndSelect( pos.getRow() + 1, pos.getTableColumn());
                }
            }
            if(event.getCode() == KeyCode.DELETE){
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

    public void addRowUser() {
        TablePosition pos = userTable.getFocusModel().getFocusedCell();
        userTable.getSelectionModel().clearSelection();
        User user = new User(0,"0","0");
        userTable.getItems().add(user);
        int row = userTable.getItems().size() - 1;
        userTable.getSelectionModel().select( row, pos.getTableColumn());
        userTable.scrollTo(user);
    }

    public void editPosUser() {
        TablePosition pos = userTable.getFocusModel().getFocusedCell();
        int r = pos.getRow();
        User user;
        switch(pos.getColumn()){
            case 0: user = new User(Integer.parseInt(input),user_st.get(r+1).nome,user_st.get(r+1).tipo);
                    break;
            case 1: user = new User(user_st.get(r+1).id,input,user_st.get(r+1).tipo);
                    break;
            case 2: user = new User(user_st.get(r+1).id,user_st.get(r+1).nome,input);
                    break;
            default: user = new User(0,"0","0");
        }
        userTable.getItems().add(user);
        userTable.getSelectionModel().select( r, pos.getTableColumn());
        removeSelectedRowsUser();
        userTable.scrollTo(user);
    }

    public void removeSelectedRowsUser() {
        userTable.getItems().removeAll(userTable.getSelectionModel().getSelectedItems());
        userTable.getSelectionModel().clearSelection();
    }

    public void addAndEditReg(){
        regTable.addEventFilter(KeyEvent.KEY_PRESSED, event -> {

            if( event.getCode() == KeyCode.ENTER) {
                //System.out.println(input);
                editPosReg();
                input = "";
                return;
            }

            if(regTable.getEditingCell() == null) {
                if(event.getCode().isLetterKey() || event.getCode().isDigitKey()) {
                    input = input + event.getText();
                }
            }
        });

        regTable.addEventFilter(KeyEvent.KEY_RELEASED, event -> {

            if(event.getCode() == KeyCode.ENTER && input.equals("")) {
                TablePosition pos = regTable.getFocusModel().getFocusedCell();

                if (pos.getRow() == -1) {
                    regTable.getSelectionModel().select(0);
                } else if (pos.getRow() == regTable.getItems().size() -1) {
                    addRowReg();
                } else if (pos.getRow() < regTable.getItems().size() -1) {
                    regTable.getSelectionModel().clearAndSelect( pos.getRow() + 1, pos.getTableColumn());
                }
            }
            if(event.getCode() == KeyCode.DELETE){
                TablePosition pos = userTable.getFocusModel().getFocusedCell();

                if (pos.getRow() == -1) {
                    userTable.getSelectionModel().select(0);
                } else {
                    removeSelectedRowsReg();
                }
            }
        });

        regTable.getSelectionModel().setCellSelectionEnabled(true);
        regTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        regTable.getSelectionModel().selectFirst();
    }

    public void addRowReg() {
        TablePosition pos = regTable.getFocusModel().getFocusedCell();
        regTable.getSelectionModel().clearSelection();
        Regiao reg = new Regiao(0,"0",0);
        regTable.getItems().add(reg);
        int row = regTable.getItems().size() - 1;
        regTable.getSelectionModel().select( row, pos.getTableColumn());
        regTable.scrollTo(reg);
    }

    public void editPosReg() {
        TablePosition pos = regTable.getFocusModel().getFocusedCell();
        int r = pos.getRow();
        Regiao reg;
        switch(pos.getColumn()){
            case 0: reg = new Regiao(Integer.parseInt(input),reg_st.get(r+1).nome,reg_st.get(r+1).n_caches);
                break;
            case 1: reg = new Regiao(reg_st.get(r+1).id,input,reg_st.get(r+1).n_caches);
                break;
            case 2: reg = new Regiao(reg_st.get(r+1).id,reg_st.get(r+1).nome,Integer.parseInt(input));
                break;
            default: reg = new Regiao(0,"0",0);
        }
        regTable.getItems().add(reg);
        regTable.getSelectionModel().select( r, pos.getTableColumn());
        removeSelectedRowsReg();
        regTable.scrollTo(reg);
    }

    public void removeSelectedRowsReg() {
        regTable.getItems().removeAll(regTable.getSelectionModel().getSelectedItems());
        regTable.getSelectionModel().clearSelection();
    }

    public void addAndEditGeo(){
        geoTable.addEventFilter(KeyEvent.KEY_PRESSED, event -> {

            if( event.getCode() == KeyCode.ENTER) {
                //System.out.println(input);
                editPosGeo();
                input = "";
                return;
            }

            if(geoTable.getEditingCell() == null) {
                if(event.getCode().isLetterKey() || event.getCode().isDigitKey()) {
                    input = input + event.getText();
                }
            }
        });

        geoTable.addEventFilter(KeyEvent.KEY_RELEASED, event -> {

            if(event.getCode() == KeyCode.ENTER && input.equals("")) {
                TablePosition pos = geoTable.getFocusModel().getFocusedCell();

                if (pos.getRow() == -1) {
                    geoTable.getSelectionModel().select(0);
                } else if (pos.getRow() == geoTable.getItems().size() -1) {
                    addRowGeo();
                } else if (pos.getRow() < geoTable.getItems().size() -1) {
                    geoTable.getSelectionModel().clearAndSelect( pos.getRow() + 1, pos.getTableColumn());
                }
            }
            if(event.getCode() == KeyCode.DELETE){
                TablePosition pos = geoTable.getFocusModel().getFocusedCell();

                if (pos.getRow() == -1) {
                    geoTable.getSelectionModel().select(0);
                } else {
                    removeSelectedRowsGeo();
                }
            }
        });

        geoTable.getSelectionModel().setCellSelectionEnabled(true);
        geoTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        geoTable.getSelectionModel().selectFirst();
    }

    public void addRowGeo() {
        TablePosition pos = geoTable.getFocusModel().getFocusedCell();
        geoTable.getSelectionModel().clearSelection();
        Geocache geocache = new Geocache("geocache", "0", 0.0f, 0.0f, 0,0);
        geoTable.getItems().add(geocache);
        int row = geoTable.getItems().size() - 1;
        geoTable.getSelectionModel().select( row, pos.getTableColumn());
        geoTable.scrollTo(geocache);
    }

    public void editPosGeo() {
        TablePosition pos = geoTable.getFocusModel().getFocusedCell();
        int r = pos.getRow();
        Geocache geo;
        switch(pos.getColumn()){
            case 0: geo = new Geocache("geocache"+Integer.parseInt(input),geo_st.get(r+1).tipo,
                    geo_st.get(r+1).coordenadasX,geo_st.get(r+1).coordenadasY,geo_st.get(r+1).id_reg, geo_st.get(r+1).n_itens);
                break;
            case 1: geo = new Geocache(geo_st.get(r+1).id,input,
                    geo_st.get(r+1).coordenadasX,geo_st.get(r+1).coordenadasY,geo_st.get(r+1).id_reg, geo_st.get(r+1).n_itens);
                break;
            case 2: geo = new Geocache(geo_st.get(r+1).id,geo_st.get(r+1).tipo,
                    Float.parseFloat(input),geo_st.get(r+1).coordenadasY,geo_st.get(r+1).id_reg, geo_st.get(r+1).n_itens);
                break;
            case 3: geo = new Geocache(geo_st.get(r+1).id,geo_st.get(r+1).tipo,
                    geo_st.get(r+1).coordenadasX,Float.parseFloat(input),geo_st.get(r+1).id_reg, geo_st.get(r+1).n_itens);
                break;
            case 4: geo = new Geocache(geo_st.get(r+1).id,geo_st.get(r+1).tipo,
                    geo_st.get(r+1).coordenadasX,geo_st.get(r+1).coordenadasY,Integer.parseInt(input), geo_st.get(r+1).n_itens);
                break;
            case 5: geo = new Geocache(geo_st.get(r+1).id,geo_st.get(r+1).tipo,
                    geo_st.get(r+1).coordenadasX,geo_st.get(r+1).coordenadasY,geo_st.get(r+1).id_reg, Integer.parseInt(input));
                break;
            default: geo = new Geocache("geocache0", "0", 0.0f, 0.0f, 0,0);
        }
        geoTable.getItems().add(geo);
        geoTable.getSelectionModel().select( r, pos.getTableColumn());
        removeSelectedRowsReg();
        geoTable.scrollTo(geo);
    }

    public void removeSelectedRowsGeo() {
        geoTable.getItems().removeAll(geoTable.getSelectionModel().getSelectedItems());
        geoTable.getSelectionModel().clearSelection();
    }

    public void addAndEditItens(){
        itemTable.addEventFilter(KeyEvent.KEY_PRESSED, event -> {

            if( event.getCode() == KeyCode.ENTER) {
                //System.out.println(input);
                editPosItens();
                input = "";
                return;
            }

            if(itemTable.getEditingCell() == null) {
                if(event.getCode().isLetterKey() || event.getCode().isDigitKey()) {
                    input = input + event.getText();
                }
            }
        });

        itemTable.addEventFilter(KeyEvent.KEY_RELEASED, event -> {

            if(event.getCode() == KeyCode.ENTER && input.equals("")) {
                TablePosition pos = itemTable.getFocusModel().getFocusedCell();

                if (pos.getRow() == -1) {
                    itemTable.getSelectionModel().select(0);
                } else if (pos.getRow() == itemTable.getItems().size() -1) {
                    addRowItens();
                } else if (pos.getRow() < itemTable.getItems().size() -1) {
                    itemTable.getSelectionModel().clearAndSelect( pos.getRow() + 1, pos.getTableColumn());
                }
            }
            if(event.getCode() == KeyCode.DELETE){
                TablePosition pos = itemTable.getFocusModel().getFocusedCell();

                if (pos.getRow() == -1) {
                    itemTable.getSelectionModel().select(0);
                } else {
                    removeSelectedRowsItens();
                }
            }
        });

        itemTable.getSelectionModel().setCellSelectionEnabled(true);
        itemTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        itemTable.getSelectionModel().selectFirst();
    }

    public void addRowItens() {
        TablePosition pos = itemTable.getFocusModel().getFocusedCell();
        itemTable.getSelectionModel().clearSelection();
        Item item = new Item(0,"geocache0","");
        itemTable.getItems().add(item);
        int row = itemTable.getItems().size() - 1;
        itemTable.getSelectionModel().select( row, pos.getTableColumn());
        itemTable.scrollTo(item);
    }

    public void editPosItens() {
        TablePosition pos = itemTable.getFocusModel().getFocusedCell();
        int r = pos.getRow();
        Item item;
        switch(pos.getColumn()){
            case 0: item = new Item(Integer.parseInt(input),item_st.get(r+1).id_geo,item_st.get(r+1).item);
                break;
            case 1: item = new Item(item_st.get(r+1).id,"geocache"+Integer.parseInt(input),item_st.get(r+1).item);
                break;
            case 2: item = new Item(item_st.get(r+1).id,item_st.get(r+1).id_geo,input);
                break;
            default: item = new Item(0,"geocache0","");
        }
        itemTable.getItems().add(item);
        itemTable.getSelectionModel().select( r, pos.getTableColumn());
        removeSelectedRowsItens();
        itemTable.scrollTo(item);
    }

    public void removeSelectedRowsItens() {
        itemTable.getItems().removeAll(itemTable.getSelectionModel().getSelectedItems());
        itemTable.getSelectionModel().clearSelection();
    }

    public void lerFile(){
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
}