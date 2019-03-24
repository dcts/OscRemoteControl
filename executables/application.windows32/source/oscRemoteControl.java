import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import oscP5.*; 
import netP5.*; 
import java.net.Inet4Address; 
import java.awt.Robot.*; 
import java.awt.event.InputEvent; 
import java.awt.MouseInfo; 
import java.awt.Robot; 
import java.awt.event.KeyEvent; 
import java.awt.Toolkit; 
import java.awt.Robot; 
import java.awt.event.KeyEvent; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class OscRemoteControl extends PApplet {

/* OSC Remote Control
 * Version 1.0 / 23.03.2019
 * by dcts
 */
 
/* This program allows you to control the keyboard and mouse 
 * of your desktop computer with your smartphone. Its build
 * and tested for Windows, although it also should work on Linux.
 * See full documentation and installation instructions here:
 * GITHUB: 
 */








// main objects
OscP5 oscP5;
Keyboard keyboard;
Mouse mouse;

// display information on canvas
int port = 4559;  // set default port to 4559
String myIp = ""; // IP address of listener
String headerMsg = "Listening for OSC messages on port " + port + "\nIP address: ";
String instructionMsg = "";
String oscMsgString = "no messages recieved yet";

public void settings() {
  size(700,300); // initialize canvas
}

public void setup() {
  try {
    this.oscP5 = new OscP5(this, port); // start oscP5, listening for incoming messages at port 4559
    this.keyboard = new Keyboard();     // setup keyboard robot
    this.mouse = new Mouse();           // setup mouse robot
    this.myIp = Inet4Address.getLocalHost().getHostAddress(); // get ip address
    if (myIp.startsWith("192.168.")) { // check if ip address is valid
      this.headerMsg += myIp; 
    } else { // wrong ip... you will need to check ip manually to setup OSC connection
      this.headerMsg += "unknown"; 
      this.instructionMsg = "(go to whatismyip.com and use the address starting with 192.168...)";
    }
  } catch (Exception e) {
    e.printStackTrace();
  }
}

public void draw() {
  background(0,0,0);
  textSize(30);
  text(this.headerMsg, 10, 40);
  textSize(14);
  text(this.instructionMsg, 10, 120);
  textSize(18);
  text(this.oscMsgString, 10, 160);
}

// incoming osc message are forwarded to the oscEvent method. 
public void oscEvent(OscMessage oscMsg) {
  // GET INFORMATION OF OSC MESSAGE TO PRINT TO CANVAS
  StringBuilder sb = new StringBuilder();
  sb.append("sender: " + oscMsg.toString().split(" | ")[0] + "\n");
  sb.append("message: " + oscMsg.addrPattern() + "\n");
  sb.append("values: \n" + Bytes.getAsString(oscMsg.arguments()));
  this.oscMsgString = sb.toString(); // print osc message to canvas
    
  // KEYBOARD AND MOUSE BUTTONS EVENT MAPPING
  try {
    // KEYBOARD EVENTS--------------------------------------------
    if (oscMsg.addrPattern().contains("/keyboard/capslock")) {
      if ((float)oscMsg.arguments()[0]==1) {
        this.keyboard.setCapslockOn(true);
        println("setting capslock off");
      } else {
        this.keyboard.setCapslockOn(false);
        println("setting capslock off");
      }
    } else if (oscMsg.addrPattern().contains("/keyboard/")) {
      String action = oscMsg.addrPattern().split("keyboard/")[1];
      // translate special characters
      if (action.equals("ue")) { action = "ü";}
      else if (action.equals("ae")) { action = "ä";}
      else if (action.equals("oe")) { action = "ö";} 
      else if (action.equals("at")) { action = "@";} 
      // perform keyboardpress and release!
      this.keyboard.pressKey(action);
      this.keyboard.releaseKey(action);
    }
    
    // MOUSE EVENTS-----------------------------------------------
    if (oscMsg.addrPattern().contains("/xy")) {
      this.mouse.move(oscMsg);
    }
    if (oscMsg.addrPattern().contains("/scroll-down")) {
      this.mouse.scrollDown();
    } 
    if (oscMsg.addrPattern().contains("/scroll-up")) {
      this.mouse.scrollUp();
    }
    if (oscMsg.addrPattern().contains("/mouse/sensitivity")) { 
      float x = (float)(oscMsg.arguments()[0]);
      this.mouse.setSensitivity((1-x)+0.1f);
      println("sensitivity : " + mouse.getSensitivity());
    } 
    if (oscMsg.addrPattern().contains("/mouse/leftclick")) {
      if ((float)(oscMsg.arguments()[0])==1) {
        this.mouse.clickLeft("press");
      } else if ((float)(oscMsg.arguments()[0])==0) {
        this.mouse.clickLeft("release");
      }
    }
    if (oscMsg.addrPattern().contains("/mouse/rightclick")) {
      if ((float)(oscMsg.arguments()[0])==1) {
        this.mouse.clickRight("press");
      } else if ((float)(oscMsg.arguments()[0])==0) {
        this.mouse.clickRight("release");
      }
    }
    if (oscMsg.addrPattern().contains("/mouse/zoom+")) {
      this.keyboard.pressCombination("windows", "+");
    }
    if (oscMsg.addrPattern().contains("/mouse/zoom-")) {
      this.keyboard.pressCombination("windows", "-");
    }
    if (oscMsg.addrPattern().contains("/mouse/zoom-exit")) {
      this.keyboard.pressCombination("windows", "-");
      this.keyboard.pressCombination("windows", "-");
      this.keyboard.pressCombination("windows", "-");
      this.keyboard.pressCombination("windows", "esc");
    }
    if (oscMsg.addrPattern().matches("/mouse/recalibrate|/mouse/show-mouse")) {
      this.keyboard.pressKey("ctrl");
      this.keyboard.releaseKey("ctrl");
    }
    
    // KEYBOARD SHORTCUTS-----------------------------------------
    if (oscMsg.addrPattern().contains("/shortcuts/arrow-left")) {
      this.keyboard.pressKey("arrow-left");
      this.keyboard.releaseKey("arrow-left");
    }
    if (oscMsg.addrPattern().contains("/shortcuts/arrow-up")) {
      this.keyboard.pressKey("arrow-up");
      this.keyboard.releaseKey("arrow-up");
    }
    if (oscMsg.addrPattern().contains("/shortcuts/arrow-right")) {
      this.keyboard.pressKey("arrow-right");
      this.keyboard.releaseKey("arrow-right");
    }
    if (oscMsg.addrPattern().contains("/shortcuts/arrow-down")) {
      this.keyboard.pressKey("arrow-down");
      this.keyboard.releaseKey("arrow-down");
    }
    if (oscMsg.addrPattern().contains("/shortcuts/copy")) {
      this.keyboard.pressCombination("ctrl", "c");
    }
    if (oscMsg.addrPattern().contains("/shortcuts/paste")) {
      this.keyboard.pressCombination("ctrl", "v");
    }
    if (oscMsg.addrPattern().contains("/shortcuts/home")) {
      this.keyboard.pressKey("home");
      this.keyboard.releaseKey("home");
    }
    if (oscMsg.addrPattern().contains("/shortcuts/end")) {
      this.keyboard.pressKey("end");
      this.keyboard.releaseKey("end");
    }
    if (oscMsg.addrPattern().contains("/shortcuts/f5")) {
      this.keyboard.pressKey("f5");
      this.keyboard.releaseKey("f5");
    }
    if (oscMsg.addrPattern().contains("/shortcuts/alt-f4")) {
      this.keyboard.pressCombination("alt", "f4");
    }
    if (oscMsg.addrPattern().contains("/shortcuts/alt-tab")) {
      this.keyboard.pressCombination("alt", "tab");
    }
    if (oscMsg.addrPattern().contains("/shortcuts/tab-close")) {
      this.keyboard.pressCombination("ctrl", "w");
    }
    if (oscMsg.addrPattern().contains("/shortcuts/tab-reopen")) {
      this.keyboard.pressCombination("ctrl", "shift", "t");
    }
    if (oscMsg.addrPattern().contains("/shortcuts/tab-next")) {
      this.keyboard.pressCombination("ctrl", "tab");
    }
    if (oscMsg.addrPattern().contains("/shortcuts/tab-previous")) {
      this.keyboard.pressCombination("ctrl", "shift", "tab");
    }
    
    // YOUTUBE CONTROLLER-----------------------------------------
    if (oscMsg.addrPattern().contains("/youtube/play")) {
      this.keyboard.pressKey("k");
      this.keyboard.releaseKey("k");
    }
    if (oscMsg.addrPattern().contains("/youtube/-10")) {
      this.keyboard.pressKey("j");
      this.keyboard.releaseKey("j");
    }
    if (oscMsg.addrPattern().contains("/youtube/+10")) {
      this.keyboard.pressKey("l");
      this.keyboard.releaseKey("l");
    }
    if (oscMsg.addrPattern().contains("/youtube/vol-up")) {
      this.keyboard.pressKey("arrow-up");
      this.keyboard.releaseKey("arrow-up");
    }
    if (oscMsg.addrPattern().contains("/youtube/vol-down")) {
      this.keyboard.pressKey("arrow-down");
      this.keyboard.releaseKey("arrow-down");
    }
    if (oscMsg.addrPattern().contains("/youtube/mute")) {
      this.keyboard.pressKey("m");
      this.keyboard.releaseKey("m");
    }
    if (oscMsg.addrPattern().contains("/youtube/fullscreen")) {
      this.keyboard.pressKey("f");
      this.keyboard.releaseKey("f");
    }
    
    // RESET MOUSEPOSITION (do on each osc event except while using "XY-touchpad")
    if (oscMsg.addrPattern().contains("/xy")==false) {
      mouse.relativePositionSet = false;
    }
  } catch (Exception e) {
    e.printStackTrace();
  }
}




class Keyboard {
  Robot robot;
  HashMap<String, Integer> keyMap; 
  HashMap<String, Integer> numMap; // maps number on numpad to keycode
  HashMap<String, String> specialCharMap; 
  boolean capslockOn; // does only work for regular characters (not working for "ä","ö","ü")
  
  Keyboard() {
    try {
      this.robot          = new Robot(); // initialize robot
      this.keyMap         = new HashMap<String, Integer>();
      this.numMap         = new HashMap<String, Integer>();
      this.specialCharMap = new HashMap<String, String>();
      this.capslockOn     = false;
      this.initKeyMap();
      this.initNumMap();
      this.initSpecialCharMap();
      println("Keyboard initialized!");
    } catch (Exception e) { 
      e.printStackTrace();
    }
  }
  
  public void pressKey(String k) {
    println("keyboard is pressing key " + k);
    if (k.matches("ä|ö|ü|@|!|\\?")) { // escaping question mark with "\\?"
      this.pressAltAndNumpad(specialCharMap.get(k));
    } else {
      if (capslockOn) { this.robot.keyPress(this.keyMap.get("shift"));} // press shift
      this.robot.keyPress(this.keyMap.get(k));
    }
  }
  
  public void releaseKey(String k) {
    println("keyboard is releasing key " + k);
    if (k.matches("ä|ö|ü|@|!|\\?")) { // escaping question mark with "\\?"
      this.releaseAltAndNumpad(specialCharMap.get(k));
    } else {
      this.robot.keyRelease(this.keyMap.get(k));
      if (capslockOn) { this.robot.keyRelease(this.keyMap.get("shift"));} // release shift
    }
  }
  
  public void pressAltAndNumpad(String numbers) {
    this.robot.keyPress(18); // ALT
    for (char number: numbers.toCharArray()) {
      this.robot.keyPress(this.numMap.get(number+"")); //convert to string by adding empty string
    }
  }
  
  public void releaseAltAndNumpad(String numbers) {
    for (char number: numbers.toCharArray()) {
      this.robot.keyRelease(this.numMap.get(number+"")); //convert to string by adding empty string
    }
    this.robot.keyRelease(18); // ALT    
  }
  
  public void pressCombination(String key1, String key2) {
    this.pressKey(key1); 
    this.pressKey(key2); 
    this.releaseKey(key2);
    this.releaseKey(key1);
  }
  
  public void pressCombination(String key1, String key2, String key3) {
    this.pressKey(key1); 
    this.pressKey(key2); 
    this.pressKey(key3); 
    this.releaseKey(key3);
    this.releaseKey(key2);
    this.releaseKey(key1);
  }
  
  public boolean getCapslockOn() {
    return this.capslockOn; 
  }
  
  public void setCapslockOn(boolean newState) {
    this.capslockOn = newState;
  }
  
  public void initKeyMap() {
    this.keyMap.put("0", 48);
    this.keyMap.put("1", 49);
    this.keyMap.put("2", 50);
    this.keyMap.put("3", 51);
    this.keyMap.put("4", 52);
    this.keyMap.put("5", 53);
    this.keyMap.put("6", 54);
    this.keyMap.put("7", 55);
    this.keyMap.put("8", 56);
    this.keyMap.put("9", 57);
    this.keyMap.put("q", 81);
    this.keyMap.put("w", 87);
    this.keyMap.put("e", 69);
    this.keyMap.put("r", 82);
    this.keyMap.put("t", 84);
    this.keyMap.put("z", 90);
    this.keyMap.put("u", 85); 
    this.keyMap.put("i", 73);
    this.keyMap.put("o", 79);
    this.keyMap.put("p", 80);
    this.keyMap.put("a", 65);
    this.keyMap.put("s", 83);
    this.keyMap.put("d", 68);
    this.keyMap.put("f", 70);
    this.keyMap.put("g", 71);
    this.keyMap.put("h", 72);
    this.keyMap.put("j", 74);
    this.keyMap.put("k", 75);
    this.keyMap.put("l", 76);
    this.keyMap.put("y", 89);
    this.keyMap.put("x", 88);
    this.keyMap.put("c", 67);
    this.keyMap.put("v", 86);
    this.keyMap.put("b", 66);
    this.keyMap.put("n", 78);
    this.keyMap.put("m", 77);
    this.keyMap.put(".", 46);
    this.keyMap.put("-", 45);
    this.keyMap.put("+", 107);
    this.keyMap.put("f1", 112);
    this.keyMap.put("f2", 113);
    this.keyMap.put("f3", 114);
    this.keyMap.put("f4", 115);
    this.keyMap.put("f5", 116);
    this.keyMap.put("f6", 117);
    this.keyMap.put("f7", 118);
    this.keyMap.put("f8", 119);
    this.keyMap.put("f9", 120);
    this.keyMap.put("f10", 121);
    this.keyMap.put("f11", 122);
    this.keyMap.put("f12", 123);
    this.keyMap.put("arrow-left", 37);
    this.keyMap.put("arrow-up", 38);
    this.keyMap.put("arrow-right", 39);
    this.keyMap.put("arrow-down", 40);
    this.keyMap.put("home", 36);
    this.keyMap.put("end", 35);
    this.keyMap.put("backspace", 8);
    this.keyMap.put("space", 32);
    this.keyMap.put("enter", 10);
    this.keyMap.put("del", 127);
    this.keyMap.put("windows", 524);
    this.keyMap.put("ctrl", 17);
    this.keyMap.put("shift", 16);
    this.keyMap.put("alt", 18);
    this.keyMap.put("esc", 27);
    this.keyMap.put("tab", KeyEvent.VK_TAB);
    //this.keyMap.put("capslock", 20); // not used anymore, tracked by global variable "capslockOn"
  }
  
  public void initNumMap() {
    this.numMap.put("0", 96);
    this.numMap.put("1", 97);
    this.numMap.put("2", 98);
    this.numMap.put("3", 99);
    this.numMap.put("4", 100);
    this.numMap.put("5", 101);
    this.numMap.put("6", 102);
    this.numMap.put("7", 103);
    this.numMap.put("8", 104);
    this.numMap.put("9", 105);    
  }
  
  public void initSpecialCharMap() {
    this.specialCharMap.put("ä", "132");
    this.specialCharMap.put("ö", "148");
    this.specialCharMap.put("ü", "129");
    this.specialCharMap.put("?", "063"); 
    this.specialCharMap.put("@", "064");
    // this.specialCharMap.put("!", "033"); // doesnt work because two times the same number wont be recognized..
  }
}



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
      this.sensitivity = 0.75f;
      this.mouseWheelSensitivity = 2; // default, cannot be changed by user
    } catch (Exception e) {
    }
  }

  public void move(OscMessage oscMsg) {
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
  
  public void clickLeft(String type) {
    if (type.equals("press")) {
      this.robot.mousePress(InputEvent.BUTTON1_MASK);
    } else if (type.equals("release")) {
      this.robot.mouseRelease(InputEvent.BUTTON1_MASK);      
    }
  }
  
  public void clickRight(String type) {
    if (type.equals("press")) {
      this.robot.mousePress(InputEvent.BUTTON3_MASK);
    } else if (type.equals("release")) {
      this.robot.mouseRelease(InputEvent.BUTTON3_MASK);
    }
  }
  
  public void scrollDown() {
    this.robot.mouseWheel(this.mouseWheelSensitivity);
  }
  
  public void scrollUp() {
    this.robot.mouseWheel(this.mouseWheelSensitivity * (-1));
  }
  
  public int getMouse(char axis)  {
    if (axis=='x') { 
      return (int) (MouseInfo.getPointerInfo().getLocation().getX());
    } else if (axis=='y') {
      return (int) (MouseInfo.getPointerInfo().getLocation().getY());
    } else {
      return 0;
    }
  }
  
  public void setSensitivity(float newState) {
    this.sensitivity = newState;
  }
  
  public float getSensitivity() {
    return this.sensitivity; 
  }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "OscRemoteControl" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
