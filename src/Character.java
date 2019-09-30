import javafx.scene.image.*;
import java.io.*;
import javafx.event.*;
import javafx.util.Duration;
import javafx.animation.*;

public class Character{

  public int x, y; // in term of array
  public int speed= 500; // in ms
  public ImageView current;
  public Direction dir;
  public Timeline moving;
  public View view;
  protected ImageView cat = new ImageView(new Image(new File("../sprites/cat.png").toURI().toString()));
  protected ImageView demon = new ImageView(new Image(new File("../sprites/demon.png").toURI().toString()));
  protected ImageView fish = new ImageView(new Image(new File("../sprites/fish.png").toURI().toString()));

  public Character(int x, int y, Charac c, View view){
    this.view=view;
    this.x=x;
    this.y=y;
    this.dir = Direction.Down;
    if( c == Charac.hero )
      this.current = cat;
    else if ( c == Charac.badguy )
      this.current = demon;
    else if ( c == Charac.food )
      this.current = fish;
  }

  public void startMoving(){
    KeyFrame kf= new KeyFrame(Duration.millis(speed), new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent t) {
        switch(dir){
          case Left:
            translate(-1,0);
            break;
          case Right:
            translate(1,0);
            break;
          case Up:
            translate(0,-1);
            break;
          case Down:
            translate(0,1);
            break;
        }
      }
    });
    Timeline timeline =new Timeline(kf);
    timeline.setCycleCount(Timeline.INDEFINITE);
    moving = timeline;
    timeline.play();
  }

  public void translate(int transX, int transY){
    if( x+transX < view.BOARD_WIDTH && x+transX >= 0
        && y+transY < view.BOARD_HEIGHT && y+transY >= 0
        && view.graph[y+transY][x+transX] != null){
      x = x+transX;
      y = y+transY;
    }
    current.setTranslateX(x*view.SIZE_RECTANGLE);
    current.setTranslateY(y*view.SIZE_RECTANGLE - view.SIZE_RECTANGLE/2 );
  }

  public void moveTo(int toX, int toY){
    x = toX;
    y = toY;
    current.setTranslateX(x*view.SIZE_RECTANGLE);
	  current.setTranslateY(y*view.SIZE_RECTANGLE - view.SIZE_RECTANGLE);
  }


  // public void setDirection(String dir){
  //   switch(dir){
  //     case "left":
  //       currentAnim = animLeft;
  //       break;
  //     case "right":
  //       currentAnim = animRight;
  //       break;
  //     case "up":
  //       currentAnim = animUp;
  //       break;
  //     case "down":
  //       currentAnim = animDown;
  //       break;
  //   }
  // }


}
