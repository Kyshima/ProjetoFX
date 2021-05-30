package edu.ufp.inf.lp2_aed2.projeto;

import edu.princeton.cs.algs4.SequentialSearchST;
import edu.ufp.inf.lp2_aed2.projeto.Geocache;
import edu.ufp.inf.lp2_aed2.projeto.Ligacoes;

import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

import java.awt.*;

public class Controller {
    protected static final int GROUP_MARGIN = 10;

    private CriacaoGrafo gG;
    public Group graphGroup;
    private double radius = 7;

    public Controller(int nVertices,SequentialSearchST<Integer, Geocache> geo, CriacaoGrafo gG) {
        createNewGraph(nVertices,geo);
        createGraphGroup(gG);
    }

    public void createGraphGroup(CriacaoGrafo gG) {
        //graphGroup.getChildren().clear();
        for(int i = 0; i < gG.V(); i++){
            Circle c = new Circle(gG.getPositionsX(i), gG.getPositionsY(i), radius, Color.WHITE);
            Text id = new Text(" " + i);

            StackPane sp = new StackPane();
            sp.setLayoutX(gG.getPositionsX(i) - radius);
            sp.setLayoutY(gG.getPositionsY(i) - radius);
            sp.getChildren().addAll(c, id);

            if(graphGroup.getChildren() != null)graphGroup.getChildren().add(sp);

            for(Integer v : gG.adj(i)){
                Line l = new Line(gG.getPositionsX(i), gG.getPositionsY(i), gG.getPositionsX(v), gG.getPositionsY(v));
            }
        }
    }

    public void createNewGraph(int nVertices,SequentialSearchST<Integer, Geocache> geo){
        if(gG == null){
            gG = new CriacaoGrafo(nVertices,geo);
        } else {
            gG = new CriacaoGrafo(gG, nVertices,geo);
        }
    }
}