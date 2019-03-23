# OSC Listener and Keyboard Controller

With this simple processing scetch you can use TouchOSC to control your keyboard while watching youtube videos. When away from keyboard, you can use the following functionality:
- play/pause (Keyboard press `K`)
- 10 seconds back (Keyboard press `J`)
- 10 seconds forward (Keyboard press `L`)
- fullscreenmode on/off (Keyboard press `F`)
- subtitles on/off (Keyboard press `C`)
- volume up/down (Keyboard press `UP` or `DOWN`)
- mute on/off (Keyboard press `M`)

There are three steps involved in achieving this:
1. listening for OSC messages on your computer (with [pscP5](https://github.com/sojamo/oscp5))
2. sending OSC messages from your phone to your computer (with the app [TouchOSC](https://itunes.apple.com/us/app/touchosc/id288120394))
3. transforming recieved messages to keyboard presses (with the java class [java.atw.Robot](https://docs.oracle.com/javase/7/docs/api/java/awt/Robot.html))

You need to have Processing installed. Also you will need to install `oscP5` (a library for processing) following the instructions [here](http://www.sojamo.de/libraries/oscP5/#installation). I created the layout inside the TouchOSC app myself with [TouchOSC editor](https://hexler.net/software/touchosc) (scroill down to "download" section), which you will need if you want to import my layout or create a layout yourself. And at last, you need to set the ip-adress inside your TouchOSC app as well as the port yourself. I used the port 4559 but you can use whatever port you want. 

If you run into problems feel free to open an issue, I'm glad to help.

### Yotube-Demo

[Link to Youtube-Demo](https://youtu.be/pqAowDbvelY) <br>

### Screenshot
<img src="https://user-images.githubusercontent.com/44790691/54577312-87793f80-49fb-11e9-9aa9-d8ed1f4710cf.jpeg" width="300px">
