package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    // Scale of the game (number of rows and columns)
    private  static final int NUM_ROWS = 12;
    private  static final int NUM_COLS = 12;
    Button[][] buttons = new Button[NUM_ROWS][NUM_COLS];
    char[][]  keys = new char[NUM_ROWS][NUM_COLS];
    int[][]  matrix = new int[NUM_ROWS][NUM_COLS];
    int flg;
    private Button solveMazeButton;
    private Button resetButton;
    boolean status;
    ArrayList<Character> a;
    //private Maze model; //not needed for tutorial
    public Boolean hasItem(String str){
        Boolean flag = false;
        for(int i = 0;i < NUM_ROWS;i++){
            for (int j = 0; j<NUM_COLS;j++){
                if (keys[i][j] == str.charAt(0)) {
                    flag = true;
                    //shall returns true when there is such item in the char arr.
                    break;
                }
            }
        }

        return flag;
    }
    public class updateButtonTask  extends AsyncTask<Void, Void, Boolean>{

        @Override
        protected Boolean doInBackground(Void... voids) {
            int[] arr = getCoordinates('S');
//            System.out.println(Arrays.toString(arr));
            int[] arr1 = getCoordinates('D');
//            System.out.println(Arrays.toString(arr1));
            matrix[arr[0]][arr[1]] = 1;
            System.out.println(Arrays.toString(arr1));

            int k = 0;
            while (matrix[arr1[0]][arr1[1]] == 0)
            {
                k +=1;
                System.out.println("K is: "+k+" and it's passed in to the function");
                boolean b = singleMove(k);
                if (!b)
                    break;
                }
            System.out.println("WHILE LOOP ENDED");
            return false;
        }
        public void onPostExecute(Boolean result){
            super.onPostExecute(result);
            Toast t = Toast.makeText(MainActivity.this, "Solved!!!", Toast.LENGTH_SHORT);
            t.show();

        }

    }
    public void updateBorder(Button b){
        ShapeDrawable shapedrawable = new ShapeDrawable();
        shapedrawable.setShape(new RectShape());
        shapedrawable.getPaint().setColor(Color.BLACK);
        shapedrawable.getPaint().setStrokeWidth(10f);
        shapedrawable.getPaint().setStyle(Paint.Style.STROKE);
        b.setBackground(shapedrawable);

    }
    public void initArr(){
        for (int i = 0;i<NUM_ROWS;i++){
            for(int j =0;j<NUM_COLS;j++){
                keys[i][j] = 'E';
                matrix[i][j] = 0;
            }
        }

    }
    public int[] getCoordinates(char c){
        int[] arr = {-1,-100};
        for (int i = 0;i<NUM_ROWS;i++){
            for (int j = 0;j<NUM_COLS;j++){
                if (keys[i][j] == c){
                    arr[0] = i;
                    arr[1] = j;
                }
            }
        }
        return arr;
    }
    public void resetButton(Button btn){
        btn.setText("E");
        btn.setBackgroundColor(Color.WHITE);
        updateBorder(btn);
    }
    public void reset(){
        System.out.println("RESET GAME AFTER A MAZE IS SOLVED");
        for (int i = 0; i<NUM_ROWS;i++){
            for (int j=0;j<NUM_COLS;j++){
                resetButton(buttons[i][j]);
                keys[i][j] = 'E';
                matrix[i][j] = 0;
            }
        }
    }
    public void buttonListener(){
        for (int i =0;i<NUM_ROWS;i++){
            for (int j =0;j<NUM_COLS;j++){
                int finalI = i;
                int finalJ = j;
                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onClick(View view) {
                        System.out.println("Do we have start: "+hasItem("S")+"Do we have Destination: "+hasItem("D"));
                        Button b = (Button)view;

                        String buttonText = b.getText().toString();
                        if (!hasItem("S") && buttonText.equals("E")){
                            //If there is no S
                            updateBorder(b);
                            b.setText("S");
                            keys[finalI][finalJ] = 'S';
                            b.setBackgroundColor(Color.parseColor("#e81316"));
                        }
                        else if (!hasItem("D")&& buttonText.equals("E")){
                            updateBorder(b);
                            b.setText("D");
                            b.setBackgroundColor(Color.parseColor("#138fe8"));
                            keys[finalI][finalJ] = 'D';

                        }
                        else if(hasItem("S") && buttonText.equals("S")||hasItem("D")&& buttonText.equals("D")){
                            b.setText("E");
                            b.setBackgroundColor(Color.WHITE);
                            updateBorder(b);
                            keys[finalI][finalJ] = 'E';

                        }
                        else if(buttonText.equals("E")){
                            //if it's not Start or Dest we will make it a wall
                            updateBorder(b);
                            b.setText("W");
                            b.setBackgroundColor(Color.YELLOW);
                            keys[finalI][finalJ] = 'W';
                        }
                        else if(buttonText.equals("W")){
                            b.setText("E");
                            b.setBackgroundColor(Color.WHITE);
                            updateBorder(b);
                            keys[finalI][finalJ] = 'E';
                        }
                    }
                });
            }
        }
    }
    public void generateGameBoard(){
        TableLayout gameLayout = findViewById(R.id.gameTable);
        TableRow tr;
        for(int i =0;i<NUM_ROWS;i++){
            tr = new TableRow(MainActivity.this);
            for(int j =0;j<NUM_COLS;j++){
                Button btn = new Button(MainActivity.this);
                btn.setText("E");
                btn.setBackgroundColor(Color.WHITE);
                updateBorder(btn);
                keys[i][j] = 'E';
                TableRow.LayoutParams p = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                p.weight =1;
                btn.setLayoutParams(p);
                buttons[i][j] = btn;
                tr.addView(btn);
            }
            gameLayout.addView(tr);
        }
    }
    public void sleep(int interval){
        try {
            Thread.sleep(interval);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
    public void dis(int[][] arr){
        for (int i =0;i< NUM_ROWS;i++){
            for(int j =0;j<NUM_COLS;j ++){
                System.out.print(arr[i][j]+" ");
            }
            System.out.println();
        }
    }
    public boolean singleMove(int round){
        boolean flg1 = false;
        for (int i = 0;i<NUM_ROWS;i++){
            for (int j =0;j<NUM_COLS;j++){
                if (matrix[i][j] == round){
                    System.out.println("ROUND IS: "+ round+" Matrix[i][j] is "+matrix[i][j]);
                    flg1 = true;
                    if (i > 0 && matrix[i-1][j] == 0 && keys[i-1][j] != 'W'){
                        matrix[i-1][j] = round+1;
                        if (keys[i-1][j] == 'E'){
                            buttons[i-1][j].setBackgroundColor(Color.GREEN);

                        }
                        sleep(200);
                    }
                    if (j>0 && matrix[i][j-1] == 0 && keys[i][j-1] != 'W'){
                        matrix[i][j-1] = round+1;
                        if (keys[i][j-1] == 'E'){
                            buttons[i][j-1].setBackgroundColor(Color.GREEN);
                        }
                        sleep(200);
                    }
                    if (i < NUM_ROWS-1 && matrix[i+1][j] == 0 && keys[i+1][j] != 'W' ){
                        matrix[i+1][j] = round+1;
                        if (keys[i+1][j] == 'E'){
                            buttons[i+1][j].setBackgroundColor(Color.GREEN);
                        }
                        sleep(200);

                    }
                    if (j < NUM_COLS-1 && matrix[i][j+1] ==0 &&  keys[i][j+1] != 'W' ){
                        matrix[i][j+1] = round+1;
                        if (keys[i][j+1] == 'E')
                        {
                            buttons[i][j+1].setBackgroundColor(Color.GREEN);
                        }
                        sleep(200);
                    }
                }
            }
        }
        dis(matrix);
        return flg1;
    }

    public void buttonState(boolean flg){
        for (int i =0;i< NUM_COLS;i++){
            for (int j=0;j<NUM_COLS;j++){
                buttons[i][j].setEnabled(flg);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        generateGameBoard();
        initArr();//init arr and assign x to each item in the array.
        flg = 0;
        a = new ArrayList<>();
        buttonListener();

        status = true;
        resetButton = (Button) findViewById(R.id.button_reset);
        solveMazeButton = (Button) findViewById(R.id.button_solve_maze);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonState(true);
                reset();
                status = true;
            }
        });
        solveMazeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                Toast t = Toast.makeText(MainActivity.this, b.getText(), Toast.LENGTH_SHORT);
                t.show();
                if (hasItem("S")&& hasItem("D")){

                    //Once we have S and D, we can now start to solve the maze. If you do not want a wall, then it's fine.
                    System.out.println("Yes, we have start and des, we can start to solve the maze.");
                    //get start and end coordinates here and start path finding.
                    if (status){
                        System.out.println("The button text is: "+solveMazeButton.getText().toString());
                        System.out.println("In status");
                        updateButtonTask updateButtonTask = new updateButtonTask();
                        AsyncTask<Void, Void, Boolean> a = updateButtonTask.execute();
                        System.out.println("X1"+a);
                        buttonState(false);
                        System.out.println();
                    }
                    else {
                        System.out.println("Not in status");

                    }
                }
                else{

                    System.out.println("It requires a start and des to start the game");
                }
            }

        });

    }
}