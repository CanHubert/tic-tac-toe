package sample;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable {
    public GridPane boxes;
    public Button buttonO,buttonX, button1, button2,button3, button4, button5, button6, button7, button8, button9;
    public Label label, playerMarkLabel;
    private String mark, markAI;
    private boolean endGame = false;
    private boolean myTurn = true;

    private Map<Integer,Button> buttonsMap = new TreeMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillMap();
    }

    private void fillMap(){
        cleanMap();
        buttonsMap.put(1,button1);
        buttonsMap.put(2,button2);
        buttonsMap.put(3,button3);
        buttonsMap.put(4,button4);
        buttonsMap.put(5,button5);
        buttonsMap.put(6,button6);
        buttonsMap.put(7,button7);
        buttonsMap.put(8,button8);
        buttonsMap.put(9,button9);
    }

    private void cleanMap(){
        buttonsMap.remove(1);
        buttonsMap.remove(2);
        buttonsMap.remove(3);
        buttonsMap.remove(4);
        buttonsMap.remove(5);
        buttonsMap.remove(6);
        buttonsMap.remove(7);
        buttonsMap.remove(8);
        buttonsMap.remove(9);
    }

    public void printMap(){
       for(Map.Entry entry : buttonsMap.entrySet())
           System.out.println(entry.getKey() +" "+ entry.getValue());
    }

    public void markBox(ActionEvent ae){
        Button btn = (Button) ae.getSource();
        if(!btn.getText().equals("")) return;
        int buttonKey = Integer.valueOf(btn.getId().substring(6));
        btn.setText(mark);
        winningConditions();
        buttonsMap.remove(buttonKey);
        myTurn= false;
       if(!endGame)AITurn();
    }

    public void chooseMark(ActionEvent ae){
        mark = ((Button)ae.getSource()).getText();
        if(mark.equals("O")) markAI = "X";
        else markAI= "O";
        playerMarkLabel.setText(mark);
        buttonO.setDisable(true);
        buttonX.setDisable(true);
        unlockButtons();
    }

    private void unlockButtons(){
        for (Object x: boxes.getChildren()){
            ((Button)x).setDisable(false);

        }
    }

    public void restartGame(){
        fillMap();
        label.setText("NEW GAME");
        playerMarkLabel.setText("");
        buttonO.setDisable(false);
        buttonX.setDisable(false);
        endGame= false;
        for (Object x: boxes.getChildren()){
            ((Button)x).setText("");
            ((Button)x).setDisable(false);

        }
    }

    private void winningConditions(){
        String text = myTurn ? mark : markAI;
        if( (button1.getText().equals(text) && button2.getText().equals(text)&& button3.getText().equals(text)) ||
                (button4.getText().equals(text) && button5.getText().equals(text)&& button6.getText().equals(text)) ||
                (button7.getText().equals(text) && button8.getText().equals(text)&& button9.getText().equals(text)) ||
                (button1.getText().equals(text) && button4.getText().equals(text)&& button7.getText().equals(text)) ||
                (button2.getText().equals(text) && button5.getText().equals(text)&& button8.getText().equals(text)) ||
                (button3.getText().equals(text) && button6.getText().equals(text)&& button9.getText().equals(text)) ||
                (button1.getText().equals(text) && button5.getText().equals(text)&& button9.getText().equals(text)) ||
                (button3.getText().equals(text) && button5.getText().equals(text)&& button7.getText().equals(text)))
        {
            if(myTurn){
                label.setText("WIN");
            }else label.setText("LOSE");
            endGame = true;
            for (Object x: boxes.getChildren()){
                ((Button)x).setDisable(true);
            }
        }

    }

    private void AITurn(){
        int rand = new Random().nextInt(9)+1;
        try{
            buttonsMap.get(rand).setText(markAI);
            winningConditions();
            buttonsMap.remove(rand);
        }catch (NullPointerException e){
             AITurn();
        }
        myTurn= true;
    }
}
