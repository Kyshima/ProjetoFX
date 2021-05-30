package edu.ufp.inf.lp2_aed2.projeto;

import edu.princeton.cs.algs4.SequentialSearchST;
import edu.ufp.inf.lp2_aed2.projeto.Geocache;
import edu.ufp.inf.lp2_aed2.projeto.Ligacoes;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

import java.awt.*;


public class Controller {

    public AnchorPane graphGroup;

    protected static final int Radius = 7;

    public Controller(CriacaoGrafo gG) {
        createGraphGroup(gG);
    }

    public void createGraphGroup(CriacaoGrafo gG) {

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
}