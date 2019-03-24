# OSC Remote Control

Control your computers keyboard and mouse remotely with your smartphone via TouchOSC. You can find a demonstration of the programm [here](). You will need:
- computer and a smartphone connected to the same wifi network!
- either java runntime environment or processing installed
- TouchOSC smartphone app (availible for iPhone and Android, can be purchased for 5$)
- TouchOSC Editor (downloadable for free) to import the custom OscRemoteControl layout.

## Installation

Follow this step-by-step guide to install OscRemoteControl on your computer. There are two ways of running the OscRemoteControl on your computer:

### 1. Run OscRemoteControl with Java Runtime Environment (recommended)
1. Install the [java runtime environment](https://www.java.com/en/download/). If you have java already installed on your machine the installer will recognize this and check for updates. If any updates are suggested, be sure to upgrade.
2. Download this repository and run the executable for your operating system. For example, if you have a windows x64 OS, run `oscRemoteControl.exe` which is located inside `executables/application.windows64/`. 
3. If you see the following screen, you have successfully installed OscRemoteControl on your computer:<br>
<img width="400px" src="https://user-images.githubusercontent.com/44790691/54880769-11d10180-4e49-11e9-9cf9-3693b464ca6c.PNG">

### 2. Run OscRemoteControl with Processing
1. Download and install the processing programming environment for your operating system [here](https://processing.org/download/). 
2. Install the [oscP5](http://www.sojamo.de/libraries/oscP5/) library for processing. This step is mandatory, since OscRemoteControl wont work without it.
3. Open [OscRemoteControl.pde](OscRemoteControl.pde) with processing and run the scetch by pressing the play button in the top left corner.
4. The installation was successful when you see the same screen as mentioned in 1.3. 


### Linking TouchOSC

Once the OscRemoteControl runs on your computer, you will need to download the TouchOSC app (this app costs 5$). You can download the app here:
- [Android](https://play.google.com/store/apps/details?id=net.hexler.touchosc_a&hl=en_US)
- [iPhone](https://itunes.apple.com/us/app/touchosc/id288120394)

To import the keyboard and mouse layout, you will additionaly need the TouchOSC Editor, which can be downloaded [here](https://hexler.net/software/touchosc) (be sure to scroll down to the "downloads" section and choose the right operating system). Extract the downloaded .zip file and run the TouchOSC Editor. Go to `Open` and import the previously downloaded file `TouchOSC-Layout\_RemoteControl.touchosc` from this repository. You should now see the keyboard layout. To import this layout to your smartphone click on `Sync` and follow the instructions. 

Once the layout is succesfully loaded, you will need to setup the ip-address and port inside your TouchOSC app. In the `CONNECTIONS` settings of the app, eneble OSC and insert the information shown by OscRemoteControl. Now you should be connected and able to control your keyboard and mouse via smartphone.

If you run into any issues feel free to open an Issue, I'm glad to help! 
