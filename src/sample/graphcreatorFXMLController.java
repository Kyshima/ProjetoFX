package sample;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

import java.awt.*;

public class graphcreatorFXMLController {

    public TextField numberOfVerticesField;
    public TextArea edgesField;
    public AnchorPane graphGroup;

    GeoGraph gG;

    protected static final int Radius = 20;

    private void creatGraphGroup() {
        graphGroup.getChildren().clear();

        for(int i = 0; i < gG.V(); i++){
            Circle c = new Circle(gG.getPositionsX(i), gG.getPositionsY(i), Radius, Color.WHITE);
            Text id = new Text(" " + i);

            StackPane sp = new StackPane();
            sp.setLayoutX(gG.getPositionsX(i) - Radius);
            sp.setLayoutY(gG.getPositionsY(i) - Radius);
            sp.getChildren().addAll(c, id);

            graphGroup.getChildren().add(sp);

            for(Integer v : gG.adj(i)){
                Line l = new Line(gG.getPositionsX(i), gG.getPositionsY(i), gG.getPositionsX(v), gG.getPositionsY(v));
            }

        }
    }

    public void handleVerticesButtonAction(ActiveEvent actionEvent){
        gG = new GeoGraph(Integer.parseInt(this.numberOfVerticesField.getText()));
        creatGraphGroup();
    }

    public void handleEdgesButtonAction(ActiveEvent actionEvent){
        gG = new GeoGraph(Integer.parseInt(numberOfVerticesField.getText()));
        String[] lines = this.edgesField.getText().split("\n");
        for(String line : lines){
            String[] aux = line.split(";");
            gG.addEdge(Integer.parseInt(aux[0]), Integer.parseInt(aux[1]));
        }

    }
    public void handleClearButtonAction(ActiveEvent actionEvent){
        this.graphGroup.getChildren().clear();
        this.numberOfVerticesField.setText("");
        this.edgesField.setText("");
        this.gG = null;
    }
}