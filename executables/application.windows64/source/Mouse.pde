import java.awt.Robot;
import java.awt.event.KeyEvent;

class Mouse {
  Robot robot;
  int w, h; // get width and height of screen
  boolean relativePositionSet; // for recalibration
  int xCurrent, yCurrent; // 
  int xRelative, yRelative; // 
  int xMouseRelative, yMouseRelative; //
  int xDiff, yDiff; // target change to relative
  int mouseWheelSensitivity;
  float sensitivity; // mouse sensitivity
  
  Mouse() {
    try {
      this.robot          = new Robot(); // initialize robot
      this.relativePositionSet = false;    
      this.w = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
      this.h = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
      this.sensitivity = 0.75;
      this.mouseWheelSensitivity = 2; // default, cannot be changed by user
    } catch (Exception e) {
    }
  }

  void move(OscMessage oscMsg) {
    this.xCurrent = (int)((float)oscMsg.arguments()[1] * w);
    this.yCurrent = (int)((float)oscMsg.arguments()[0] * h);
    if (this.relativePositionSet==false) {
      this.yRelative = this.yCurrent;
      this.xRelative = this.xCurrent;
      this.xMouseRelative = getMouse('x');
      this.yMouseRelative = getMouse('y');
      this.relativePositionSet = true;
    }
    this.xDiff = this.xCurrent-this.xRelative;
    this.yDiff = this.yCurrent-this.yRelative;
    //println("current: " + this.xCurrent+","+this.yCurrent+" relative: "+this.xRelative+","+this.yRelative + " diff: " +this.xDiff+","+this.yDiff);
    //println("4");
    int xx = (int)(this.xMouseRelative+this.xDiff*this.sensitivity);
    int yy = (int)(this.yMouseRelative+this.yDiff*this.sensitivity);
    //println("x="+xx+" y="+yy);
    this.robot.mouseMove(xx, yy);
  }
  
  void clickLeft(String type) {
    if (type.equals("press")) {
      this.robot.mousePress(InputEvent.BUTTON1_MASK);
    } else if (type.equals("release")) {
      this.robot.mouseRelease(InputEvent.BUTTON1_MASK);      
    }
  }
  
  void clickRight(String type) {
    if (type.equals("press")) {
      this.robot.mousePress(InputEvent.BUTTON3_MASK);
    } else if (type.equals("release")) {
      this.robot.mouseRelease(InputEvent.BUTTON3_MASK);
    }
  }
  
  void scrollDown() {
    this.robot.mouseWheel(this.mouseWheelSensitivity);
  }
  
  void scrollUp() {
    this.robot.mouseWheel(this.mouseWheelSensitivity * (-1));
  }
  
  int getMouse(char axis)  {
    if (axis=='x') { 
      return (int) (MouseInfo.getPointerInfo().getLocation().getX());
    } else if (axis=='y') {
      return (int) (MouseInfo.getPointerInfo().getLocation().getY());
    } else {
      return 0;
    }
  }
  
  void setSensitivity(float newState) {
    this.sensitivity = newState;
  }
  
  float getSensitivity() {
    return this.sensitivity; 
  }
}
