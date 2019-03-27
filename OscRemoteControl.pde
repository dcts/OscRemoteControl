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

import oscP5.*;
import netP5.*;
import java.net.Inet4Address;
import java.awt.Robot.*;
import java.awt.event.InputEvent;
import java.awt.MouseInfo;

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

void settings() {
  size(700,300); // initialize canvas
}

void setup() {
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

void draw() {
  background(0,0,0);
  textSize(30);
  text(this.headerMsg, 10, 40);
  textSize(14);
  text(this.instructionMsg, 10, 120);
  textSize(18);
  text(this.oscMsgString, 10, 160);
}

// incoming osc message are forwarded to the oscEvent method. 
void oscEvent(OscMessage oscMsg) {
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
      this.mouse.setSensitivity((1-x)+0.1);
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
