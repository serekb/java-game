import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import java.util.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.concurrent.*;

import javafx.beans.*;
import javafx.beans.value.*;
import gp.*;

//Wszedzie Gp_values->Gp_values
// class Gp_values
//  {
//   int x, y;
//
//   public Gp_values()
//    {
//     x = 10;
//     y = 10;
//    }
//  }
 


public class JavaFXApp extends Application implements ChangeListener<Gp_values>
 {
  private static final int FRAME_WIDTH  = 640;
  private static final int FRAME_HEIGHT = 480;
  Stage stage;
  Canvas canvas = new Canvas(FRAME_WIDTH, FRAME_HEIGHT);
  AnchorPane root = new AnchorPane();
  GraphicsContext gc = canvas.getGraphicsContext2D();

  Game_service g_s;
  Grid grid = new Grid(10,5);
  Cursor cursor = new Cursor(grid);


  public static void main(String[] args) {
    launch(args);

}

@Override
  public void start(Stage primaryStage) {
  primaryStage.setTitle("JavaFX App");
    stage = primaryStage;


  Menu menu1 = new Menu("File");
   
  MenuItem menuItem1 = new MenuItem("Item 1");

  MenuItem menuItem2 = new MenuItem("Exit");
 
  menuItem2.setOnAction(e -> {
                              System.out.println("Exit Selected");
                             
                              exit_dialog();

                             });
 
  menu1.getItems().add(menuItem1);
  menu1.getItems().add(menuItem2);


  MenuBar menuBar = new MenuBar();
 
  menuBar.getMenus().add(menu1);
 
  VBox vBox = new VBox(menuBar,canvas);

  Scene scene = new Scene(vBox, 960, 600);

  primaryStage.setScene(scene);
 
  primaryStage.setOnCloseRequest(e -> {
                                       e.consume();
                                       exit_dialog();
                                      });
                                     
  g_s = new Game_service();
 
  g_s.valueProperty().addListener(this::changed);    
 
  g_s.start();    
 
  primaryStage.show();
 
 }
 
  public void changed(ObservableValue<? extends Gp_values> observable,
                      Gp_values oldValue,
                      Gp_values newValue)
   {
  gc.clearRect(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
  int height = 0;
  for(int i = 0; i <grid.size;i++)
    {
    int width = 0;
        for(int j = 0; j <grid.size;j++)
        {
         grid.tab[i][j].display(width,height,gc);
         width += 30;

        }
    height += 30;
    }

  if(newValue != null) System.out.println("changed method called, x = " + newValue + "y = " + newValue);
   
   
   }
 
 

 public void item_1()
  {
   System.out.println("item 1");
  }
 
 public void exit_dialog()
  {
   System.out.println("exit dialog");
   

   Alert alert = new Alert(AlertType.CONFIRMATION,
                           "Do you really want to exit the program?.",
     ButtonType.YES, ButtonType.NO);

   alert.setResizable(true);
   alert.onShownProperty().addListener(e -> {
                                             Platform.runLater(() -> alert.setResizable(false));
                                            });

  Optional<ButtonType> result = alert.showAndWait();
  if (result.get() == ButtonType.YES)
   {
    Platform.exit();
   }
  else
   {
   }

  }
}

class Field
{
    boolean revealed;
    boolean has_bomb;
    boolean has_flag;
    boolean selected;
    int x;
    int y;


    public Field(int x, int y){
        this.revealed = false;
        this.has_bomb = false;
        this.has_flag = false;
        this.selected = false;
        this.x = x;
        this.y = y;
    }

    public void reveal(){
    if(this.revealed || this.has_flag) return;
        this.revealed = true;
        if(this.has_bomb){
            // wybucha
        }
        else{
            //pokaz liczbe
            //jesli 0 to po prostu zmienia kolor
        }
    }

    public void set_bomb(){
        this.has_bomb = true;
    }
    public void set_flag()
    {
        if(this.revealed) return;
        this.has_flag = true;
    }
    public void unset_flag()
    {
        if(this.revealed) return;
        this.has_flag = false;
    }
    public void display(int width,int height,GraphicsContext gc)
    {

        if(this.revealed && this.has_bomb)
        {
        System.out.println("1");
        gc.setStroke(Color.RED);
        }
        else if(this.revealed)
        {
        gc.setStroke(Color.YELLOW);
        }
        else if(this.has_flag)
        {
            gc.setStroke(Color.GREEN);
        }
        else
        {
        gc.setStroke(Color.GREEN);
        }
        gc.setStroke(Color.GREEN);
//         System.out.println(gc.getStroke());
        gc.fillRoundRect(width, height, 30, 30, 0, 0);
    }

}

class Grid
{
    int size;
    Field[][] tab;

    public Grid(int size,int bomb_percentage){
        this.size = size;
        this.tab = new Field[size][size];
        for( int i =0; i < this.size; i++)
        {
            for(int j =0; j < this.size; j++)
            {
                this.tab[i][j] = new Field(i, j);
            }
        }
        int nBombs = (int) Math.round(0.01*size*size * bomb_percentage);
        int i = 0;
        while(i < nBombs){
            int x  =(int) ((Math.random() * (size)));
            int y = (int) ((Math.random() * (size)));
            if( !tab[x][y].has_bomb){
                tab[x][y].set_bomb();
                i++;
            }
        }
    }
}
class Cursor
{
    Grid grid;
    int x;
    int y;

    public Cursor(Grid grid)
    {
        this.x = 0;
        this.y = 0;
        this.grid = grid;
    }

    public void move(String direction)
    {
        this.grid.tab[this.x][this.y].selected = false;
        if(direction == "UP")
        {
            if(this.y > 0 )
            {
            this.y -= 1;
            return;
            }
        }

        if(direction == "DOWN")
        {
            if(this.y < this.grid.size - 1 )
            {
            this.y += 1;
            return;
            }
        }
        if(direction == "LEFT")
        {
            if(this.x > 0 )
            {
            this.x -= 1;
            return;
            }
        }

        if(direction == "RIGHT")
        {
            if(this.x < this.grid.size - 1 )
            {
            this.x += 1;
            return;
            }
        }

        this.grid.tab[this.x][this.y].selected = true;
        return;
    }

}

class G_task extends Task<Gp_values>
 {
  final  int VENDOR_ID   = 0x0810;

  int i, result;

    Gp gamepad;
  Gp_values g_move;

  public G_task()
   {

     gamepad = new Gp();
     System.out.println(gamepad);

     result = gamepad.gp_open(VENDOR_ID, 0x0001);

     if(result == -1) System.out.println("gamepad not opened");



   }

  @Override
  protected Gp_values call() throws Exception
   {

    while(true)
     {

     if(false) return g_move;
      System.out.println("Task's call method");

      updateValue(null);
      updateValue(g_move);
      Gp_values curr_v;



     for(;;)
      {

       curr_v = gamepad.get_values();

       for(i = 0; i < curr_v.buttons.length; i++)
        {
         switch(i)
          {
           case 0:
            {
             if(curr_v.buttons[i]) System.out.println("TL");
            }
           break;

           case 1:
            {
             if(curr_v.buttons[i]) System.out.println("TR");
            }
           break;

           case 2:
            {
             if(curr_v.buttons[i]) System.out.println("ZL");
            }
           break;

           case 3:
            {
             if(curr_v.buttons[i]) System.out.println("ZR");
            }
           break;

           case 4:
            {
             if(curr_v.buttons[i]) System.out.println("SELECT");
            }
           break;

           case 5:
            {
             if(curr_v.buttons[i]) System.out.println("START");
            }
           break;

           case 6:
            {
             if(curr_v.buttons[i]) System.out.println("ANALOG");
            }
           break;

           case 7:
            {
             if(curr_v.buttons[i]) System.out.println("1");
            }
           break;

           case 8:
            {
             if(curr_v.buttons[i]) System.out.println("2");
            }
           break;

           case 9:
            {
             if(curr_v.buttons[i]){ System.out.println("3");


             }
            }
           break;

           case 10:
            {
             if(curr_v.buttons[i]) System.out.println("4");
            }
           break;

           case 11:
            {
             if(curr_v.buttons[i]) System.out.println("N");
            }
           break;

           case 12:
            {
             if(curr_v.buttons[i]) System.out.println("E");
            }
           break;

           case 13:
            {
             if(curr_v.buttons[i]) System.out.println("S");
            }
           break;

           case 14:
            {
             if(curr_v.buttons[i]) System.out.println("W");
            }
           break;

           case 15:
            {
             if(curr_v.buttons[i]) System.out.println("L_PRESSED");
            }
           break;

           case 16:
            {
             if(curr_v.buttons[i]) System.out.println("R_PRESSED");
            }
           break;
          }
         g_move = curr_v;
         updateValue(null);
         updateValue(g_move);

        }

       if(curr_v.analog_mode)
        {
         System.out.println("LX:" + curr_v.LX);
         System.out.println("LY:" + curr_v.LY);
         System.out.println("RX:" + curr_v.RX);
         System.out.println("RY:" + curr_v.RY);
        }

        }

     }



    }
 }

 class Game_service extends Service<Gp_values>
  {

   Task t;

   public Game_service()
    {

    }

   protected Task createTask()
    {
     t = new G_task();

     return t;

    }

   }
