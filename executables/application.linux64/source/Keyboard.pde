import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.Toolkit;

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
  
  void pressKey(String k) {
    println("keyboard is pressing key " + k);
    if (k.matches("ä|ö|ü|@|!|\\?")) { // escaping question mark with "\\?"
      this.pressAltAndNumpad(specialCharMap.get(k));
    } else {
      if (capslockOn) { this.robot.keyPress(this.keyMap.get("shift"));} // press shift
      this.robot.keyPress(this.keyMap.get(k));
    }
  }
  
  void releaseKey(String k) {
    println("keyboard is releasing key " + k);
    if (k.matches("ä|ö|ü|@|!|\\?")) { // escaping question mark with "\\?"
      this.releaseAltAndNumpad(specialCharMap.get(k));
    } else {
      this.robot.keyRelease(this.keyMap.get(k));
      if (capslockOn) { this.robot.keyRelease(this.keyMap.get("shift"));} // release shift
    }
  }
  
  void pressAltAndNumpad(String numbers) {
    this.robot.keyPress(18); // ALT
    for (char number: numbers.toCharArray()) {
      this.robot.keyPress(this.numMap.get(number+"")); //convert to string by adding empty string
    }
  }
  
  void releaseAltAndNumpad(String numbers) {
    for (char number: numbers.toCharArray()) {
      this.robot.keyRelease(this.numMap.get(number+"")); //convert to string by adding empty string
    }
    this.robot.keyRelease(18); // ALT    
  }
  
  void pressCombination(String key1, String key2) {
    this.pressKey(key1); 
    this.pressKey(key2); 
    this.releaseKey(key2);
    this.releaseKey(key1);
  }
  
  void pressCombination(String key1, String key2, String key3) {
    this.pressKey(key1); 
    this.pressKey(key2); 
    this.pressKey(key3); 
    this.releaseKey(key3);
    this.releaseKey(key2);
    this.releaseKey(key1);
  }
  
  boolean getCapslockOn() {
    return this.capslockOn; 
  }
  
  void setCapslockOn(boolean newState) {
    this.capslockOn = newState;
  }
  
  void initKeyMap() {
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
  
  void initNumMap() {
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
  
  void initSpecialCharMap() {
    this.specialCharMap.put("ä", "132");
    this.specialCharMap.put("ö", "148");
    this.specialCharMap.put("ü", "129");
    this.specialCharMap.put("?", "063"); 
    this.specialCharMap.put("@", "064");
    // this.specialCharMap.put("!", "033"); // doesnt work because two times the same number wont be recognized..
  }
}
