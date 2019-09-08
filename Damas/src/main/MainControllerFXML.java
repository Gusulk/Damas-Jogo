/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.net.URL;
import java.util.ArrayList;

import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.VerticalDirection;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import tabuleiro.Dama;
import tabuleiro.Tabuleiro;

/**
 *
 * @author gusta
 */
public class MainControllerFXML implements Initializable {

    @FXML
    private VBox paneTabuleiro;

    @FXML
    private GridPane board;

    private Circle dragCircle;
    private final DataFormat CircleFormat = new DataFormat("Circle");

    private Tabuleiro tab;

//    @FXML
//    void onClickBoard(MouseEvent event) {
//        Node node = (Node) event.getTarget();
//        int col;
//        int row;
//        if (node instanceof Circle) {
//            node = node.getParent();
//        }
//        col = GridPane.getColumnIndex(node);
//        row = GridPane.getRowIndex(node);
//        System.out.printf("Mouse entered cell [%d, %d]%n", row, col);
//    }
    public void createBoard() {

        board.setPrefSize(640, 640);
//        board.setRotate(0);
        int count = 1;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                StackPane p = new StackPane();
                p.setPrefSize(80, 80);

//                p.setOnMouseClicked(e -> {
//                    System.out.println("opa");
//                });
                if (count % 2 == 0) {
                    p.setStyle("-fx-border-color: black;-fx-background-color: white");
                } else {
                    p.setStyle("-fx-border-color: black;-fx-background-color: black");
                }
                board.add(p, i, j);
                addDropHandling(p);
                count++;
            }
            count++;
        }

    }

    public Node getNode(final int row, final int column) {
        Node result = null;
        ObservableList<Node> childrens = board.getChildren();

        for (Node node : childrens) {
            if (board.getRowIndex(node) == row && board.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }

    private void addDropHandling(StackPane pane) {
        pane.setOnDragOver(e -> {
            Dragboard db = e.getDragboard();
            if (db.hasContent(CircleFormat) && dragCircle != null) {
                e.acceptTransferModes(TransferMode.MOVE);
            }
        });

        pane.setOnDragDropped(e -> {
            Dragboard db = e.getDragboard();
            StackPane p = ((StackPane) dragCircle.getParent());
            if (db.hasContent(CircleFormat)) {
                int ccol = GridPane.getColumnIndex(p);
                int crow = GridPane.getRowIndex(p);
                int ncol = GridPane.getColumnIndex(pane);
                int nrow = GridPane.getRowIndex(pane);
                Dama d = tab.findDama(crow, ccol);
                if (tab.verificaMovimento(d, nrow, ncol)) {
                    p.getChildren().remove(dragCircle);
                    pane.getChildren().add(dragCircle);
                    tab.removeDama(crow, ccol);
                    tab.putDama(nrow, ncol, d.getColor());
                }
                e.setDropCompleted(true);
                dragCircle = null;
            }
        });
    }

    public void moveDama(int crow, int ccol, int nrow, int ncol) {
        StackPane cpane = (StackPane) getNode(crow,ccol);
        StackPane rpane = (StackPane) getNode(nrow,ncol);
        
        Circle c = (Circle) cpane.getChildren().get(0);
        Dama d = tab.findDama(crow, ccol);
        cpane.getChildren().remove(c);
        rpane.getChildren().add(c);
        tab.removeDama(crow, ccol);
        tab.putDama(nrow, ncol, d.getColor());
      
    }

    public void setDamas(ArrayList<Dama> damas) {

        for (Dama d : damas) {
            StackPane p = (StackPane) getNode(d.getRow(), d.getCol());
            Circle c = new Circle(30);
            Color colorFill;
            if (d.getColor().equals("RED")) {
                colorFill = Color.RED;
            } else {
                colorFill = Color.BLUE;
            }
            c.setFill(colorFill);
            c.setStroke(Color.GRAY);
            c.setOnDragDetected((event) -> {
                Dragboard db = c.startDragAndDrop(TransferMode.MOVE);
                db.setDragView(c.snapshot(null, null));

                ClipboardContent cc = new ClipboardContent();
                cc.put(CircleFormat, " ");
                db.setContent(cc);
                dragCircle = c;

            });
            p.getChildren().add(c);
            StackPane.setAlignment(c, Pos.CENTER);
        }
    }

    public void startGame() {
        tab = new Tabuleiro();
        ArrayList<Dama> damas = tab.getDamas();
        setDamas(damas);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        createBoard();
        startGame();
        
    }

}
