package com.example.andrei.scorekeeper;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    int activePlayer = 0; //0 no player, 1 Aka, 2 Shiro
    int scoreAka = 0;
    int scoreShiro = 0;
    int joGaiAka = 0;
    int joGaiShiro = 0;
    int wazaAri = 4;
    int ippon = 8;
    int winScore = 40;

    String infoString = "Info displayed here!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setStateButtons(false);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        System.out.println("onSave");
        outState.putInt("KEY_ap", activePlayer);
        outState.putInt("KEY_sa", scoreAka);
        outState.putInt("KEY_ss", scoreShiro);
        outState.putInt("KEY_js" + joGaiShiro, joGaiShiro);
        outState.putInt("KEY_ja" + joGaiAka, joGaiAka);
        outState.putString("KEY_info", infoString);
        outState.putBoolean("KEY_b1", findViewById(R.id.btn_ippon).isEnabled());
        outState.putBoolean("KEY_b2", findViewById(R.id.btn_waza_ari).isEnabled());
        outState.putBoolean("KEY_b3", findViewById(R.id.btn_jo_gai).isEnabled());
        outState.putBoolean("KEY_b4", findViewById(R.id.btn_hansoku).isEnabled());
        outState.putBoolean("KEY_b5", findViewById(R.id.btn_aka).isEnabled());
        outState.putBoolean("KEY_b6", findViewById(R.id.btn_shiro).isEnabled());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        System.out.println("onRestore");
        super.onRestoreInstanceState(savedInstanceState);

        activePlayer = savedInstanceState.getInt("KEY_ap");
        scoreAka = savedInstanceState.getInt("KEY_sa");
        scoreShiro = savedInstanceState.getInt("KEY_ss");
        joGaiAka = savedInstanceState.getInt("KEY_ja");
        joGaiShiro = savedInstanceState.getInt("KEY_js");
        infoString = savedInstanceState.getString("KEY_info");


        Button btnAka = (Button) findViewById(R.id.btn_aka);
        Button btnShiro = (Button) findViewById(R.id.btn_shiro);

        btnAka.setEnabled(savedInstanceState.getBoolean("KEY_b5"));
        btnShiro.setEnabled(savedInstanceState.getBoolean("KEY_b6"));

        if(activePlayer == 1) btnAka.setText("<Aka>");
        else if(activePlayer == 2) btnShiro.setText("<Shiro>");


        ((TextView) findViewById(R.id.score_aka)).setText("Score: " + scoreAka);
        ((TextView) findViewById(R.id.score_shiro)).setText("Score: " + scoreShiro);
        ((TextView) findViewById(R.id.jo_gai_aka)).setText("Jo-gai: " + joGaiAka);
        ((TextView) findViewById(R.id.jo_gai_shiro)).setText("Jo-gai: " + joGaiShiro);
        ((TextView) findViewById(R.id.info)).setText(infoString);

        findViewById(R.id.btn_ippon).setEnabled(savedInstanceState.getBoolean("KEY_b1"));
        findViewById(R.id.btn_waza_ari).setEnabled(savedInstanceState.getBoolean("KEY_b2"));
        findViewById(R.id.btn_jo_gai).setEnabled(savedInstanceState.getBoolean("KEY_b3"));
        findViewById(R.id.btn_hansoku).setEnabled(savedInstanceState.getBoolean("KEY_b4"));


    }

    public void hansoku(View view){
        if(activePlayer == 1){
            infoString = "Aka did Hansoku! Shiro wins!";
        }
        else{
            infoString = "Shiro did Hansoku! Aka wins!";
        }
        endGame();
    }

    public void ippon(View view){

        //gets here only if the button is active
        if(activePlayer == 1){
            scoreAka += ippon;
            infoString = "Aka scored an Ippon!";
        }
        else{
            scoreShiro += ippon;
            infoString = "Shiro scored an Ippon!";
        }
        if(scoreShiro >= winScore || scoreAka >= winScore)
            setWinner();
        update();
    }

    public void wazaAri(View view){
        //gets here only if the button is active
        if(activePlayer == 1){
            scoreAka += wazaAri;
            infoString = "Aka scored an Waza-ari!";
        }
        else{
            infoString = "Shiro scored an Waza-ari!";
            scoreShiro += wazaAri;
        }
        if(scoreShiro >= 80 || scoreAka >= 80)
            setWinner();
        update();
    }

    public void joGai(View view){
        //2 jo-gai means one waza-ari for the enemy
        if(activePlayer == 1){
            infoString = "Aka Jo-gai!";
            joGaiAka++;
            if(joGaiAka == 2){
                joGaiAka = 0;
                scoreShiro += wazaAri;
            }
        }
        else{
            infoString = "Shiro Jo-gai!";
            joGaiShiro++;
            if(joGaiShiro == 2){
                joGaiShiro = 0;
                scoreAka += wazaAri;
            }
        }
        if(scoreShiro >= 80 || scoreAka >= 80)
            setWinner();
        update();
    }

    public void update(){

        TextView TW_scoreAka = (TextView) findViewById(R.id.score_aka);
        TW_scoreAka.setText("Score: " + scoreAka);

        TextView TW_scoreShiro = (TextView) findViewById(R.id.score_shiro);
        TW_scoreShiro.setText("Score: " + scoreShiro);

        TextView TW_joGaiAka = (TextView) findViewById(R.id.jo_gai_aka);
        TW_joGaiAka.setText("Jo-gai: " + joGaiAka);

        TextView TW_joGaiShiro = (TextView) findViewById(R.id.jo_gai_shiro);
        TW_joGaiShiro.setText("Jo-gai: " + joGaiShiro);

        TextView TW_info = (TextView) findViewById(R.id.info);
        TW_info.setText("" + infoString);

    }

    public void setWinner(){
        if(scoreAka >= winScore){
            infoString = "Aka won";
        }
        else{
            infoString = "Shiro won";
        }
        endGame();
    }

    public void endGame(){
        TextView TW_info = (TextView) findViewById(R.id.info);
        TW_info.setText("" + infoString);
        setStateButtons( false);
        Button btnAka = (Button) findViewById(R.id.btn_aka);
        Button btnShiro = (Button) findViewById(R.id.btn_shiro);
        btnAka.setEnabled(false);
        btnShiro.setEnabled(false);
        update();
    }

    public void changeToAka(View view){
        if(activePlayer == 1){
            activePlayer = 0;
            infoString = "No player selected";
            setStateButtons(false);

            Button btnAka = (Button) findViewById(R.id.btn_aka);
            btnAka.setText("Aka");
        }
        else {
            activePlayer = 1;
            infoString = "Selected player is Aka!";
            setStateButtons(true);

            Button btnAka = (Button) findViewById(R.id.btn_aka);
            btnAka.setText("<Aka>");
            Button btnShiro = (Button) findViewById(R.id.btn_shiro);
            btnShiro.setText("Shiro");
        }
        update();
    }

    public void changeToShiro(View view){
        if(activePlayer == 2){
            activePlayer = 0;
            infoString = "No player selected";
            setStateButtons(false);

            Button btnShiro = (Button) findViewById(R.id.btn_shiro);
            btnShiro.setText("Shiro");
        }
        else {
            activePlayer = 2;
            infoString = "Selected player is Shiro!";
            setStateButtons(true);

            Button btnAka = (Button) findViewById(R.id.btn_aka);
            btnAka.setText("Aka");
            Button btnShiro = (Button) findViewById(R.id.btn_shiro);
            btnShiro.setText("<Shiro>");
        }
        update();
    }

    public void reset(View view){
        activePlayer = 0; //0 no player, 1 Aka, 2 Shiro
        scoreAka = 0;
        scoreShiro = 0;
        joGaiAka = 0;
        joGaiShiro = 0;
        infoString = "Select player";

        Button btnAka = (Button) findViewById(R.id.btn_aka);
        Button btnShiro = (Button) findViewById(R.id.btn_shiro);

        btnShiro.setText("Shiro");
        btnAka.setText("Aka");
        setStateButtons(false);
        btnAka.setEnabled(true);
        btnShiro.setEnabled(true);
        update();
    }

    public void setStateButtons(boolean answer){
        update();
        Button btnIppon = (Button) findViewById(R.id.btn_ippon);
        Button btnWazaAri = (Button) findViewById(R.id.btn_waza_ari);
        Button btnJoGai = (Button) findViewById(R.id.btn_jo_gai);
        Button btnHansoku = (Button) findViewById(R.id.btn_hansoku);

        btnIppon.setEnabled(answer);
        btnWazaAri.setEnabled(answer);
        btnJoGai.setEnabled(answer);
        btnHansoku.setEnabled(answer);
    }

}
