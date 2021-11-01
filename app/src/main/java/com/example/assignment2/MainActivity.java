package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    // Scale of the game (number of rows and columns)
    private  static final int NUM_ROWS = 10;
    private  static final int NUM_COLS = 10;
    Button[][] buttons = new Button[NUM_ROWS][NUM_COLS];
    char[][]  keys = new char[NUM_ROWS][NUM_COLS];
    int[][]  matrix = new int[NUM_ROWS][NUM_COLS];
    int flg;
    private Button solveMazeButton;
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
    public class updateButtonTask  extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            int[] arr = getCoordinates('S');
//            System.out.println(Arrays.toString(arr));
            int[] arr1 = getCoordinates('D');
//            System.out.println(Arrays.toString(arr1));
            matrix[0][0] =1;
            System.out.println(Arrays.toString(arr1));
//
//            System.out.println(Arrays.deepToString(keys));
            int k = 0;



//
//            System.out.println("END: X: "+arr1[0]+" Y: "+arr1[1]);//3:1
//            System.out.println("FLAG: "+flg);
//            System.out.println("Matrix: "+matrix[3][1]);
//            System.out.println("keys:  "+keys[3][1]);
//            System.out.println("MAXTRIX:::\n");
//            System.out.println(Arrays.deepToString(keys));


            System.out.println("Array1: X: "+arr1[0]+" Y: "+arr1[1]);//9,9, (2,2)=>test
            System.out.println("Matrix Destination: "+matrix[arr1[0]][arr1[1]]);
            //TEST CASE: WHEN THERE ARE NO WALLS, IT WORKS FINE.
            //TEST CASE: WHEN OBSTACLES BLOCKS
                while (matrix[arr1[0]][arr1[1]] == 0)
                {
                    k +=1;
                    System.out.println("K is: "+k+" and it's passed in to the function");
                    boolean b = singleMove(k);
                    sleep(1);
                    if (!b)
                        break;

                }
                System.out.println("WHILE LOOP ENDED");
//
//            singleMove(1);
//            singleMove(2);

//            System.out.println("A: items");
//            System.out.println(a);
//            display();
            return null;
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
        for (int i = 0;i<10;i++){
            for(int j =0;j<10;j++){
                keys[i][j] = 'E';
                matrix[i][j] = 0;
            }
        }

    }
    public int[] getCoordinates(char c){
        int[] arr = {-1,-1};
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
                int finalI = i;
                int finalJ = j;
                btn.setOnClickListener(new View.OnClickListener() {
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
            gameLayout.addView(tr);

        }

    }
    public void display(){
        for (int i = 0;i<10;i++){
            System.out.println(Arrays.toString(matrix[i]));
        }
    }
    public void sleep(int period){
        try {
            Thread.sleep(200);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
    public void dis(int[][] arr){
        for (int i =0;i< 10;i++){
            for(int j =0;j<10;j ++){
                System.out.print(arr[i][j]+" ");
            }
            System.out.println();
        }
    }
    public boolean singleMove(int round){
        boolean flg1 = false;

        for (int i = 0;i<10;i++){
            for (int j =0;j<10;j++){
                if (matrix[i][j] == round){
                    System.out.println("ROUND IS: "+ round+" Matrix[i][j] is "+matrix[i][j]);
                    flg1 = true;
                    if (i > 0 && matrix[i-1][j] == 0 && keys[i-1][j] != 'W'){
                        matrix[i-1][j] = round+1;
                        buttons[i-1][j].setBackgroundColor(Color.GREEN);
                        sleep(11);

                    }
                    if (j>0 && matrix[i][j-1] == 0 && keys[i][j-1] != 'W'){
                        matrix[i][j-1] = round+1;
                        buttons[i][j-1].setBackgroundColor(Color.GREEN);
                        sleep(11);
                    }
                    if (i < 9 && matrix[i+1][j] == 0 && keys[i+1][j] != 'W' ){
                        matrix[i+1][j] = round+1;
                        buttons[i+1][j].setBackgroundColor(Color.GREEN);
                        sleep(11);

                    }
                    if (j < 9 && matrix[i][j+1] ==0 &&  keys[i][j+1] != 'W' ){
                        matrix[i][j+1] = round+1;
                        buttons[i][j+1].setBackgroundColor(Color.GREEN);
                        sleep(11);

                    }
                }



            }


        }
        dis(matrix);
        return flg1;
//        System.out.println(Arrays.deepToString(matrix));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        generateGameBoard();
        initArr();//init arr and assign x to each item in the array.
        flg = 0;
        a = new ArrayList<>();
        solveMazeButton = (Button) findViewById(R.id.button_solve_maze);
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
                    updateButtonTask updateButtonTask = new updateButtonTask();
                    updateButtonTask.execute();
                    System.out.println("end of ");



                }
                else{

                    System.out.println("It requires a start and des to start the game");
                }
            }

        });

    }
}