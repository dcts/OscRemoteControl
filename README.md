# OSC Remote Control

Control your computers keyboard and mouse remotely with your smartphone via TouchOSC. You can find a demonstration of the programm [here](). You will need:
- computer and a smartphone connected to the same wifi network!
- either java runntime environment or processing installed
- TouchOSC app (app availible for iPhone and Android that can send OSC messages, you will need to purchase it for 5$)
- TouchOSC Editor (downloadable for free) to import the custom OscRemoteControl layout.

## Installation

Follow this step-by-step guide to install OscRemoteControl on your computer. There are two ways of running the OscRemoteControl on your computer:
1. by running the executable of your operating system which can be found in the `executables` folder. This will require you to have a java runtime environment installed on your computer. This is the recommended way of running OscRemoteControl because the program will boot much faster and you wont need to install the processing environment (which is build on top of java).
2. by installing processing and running [oscRemoteControl.pde](oscRemoteControl.pde). This will automatically install a java runtime environment, but you will need to manually install a library called oscP5.

### 1. Run OscRemoteControl with Java Runtime Environment
1. install the [java runtime environment](https://www.java.com/en/download/). If you have java already installed on your machine the installer will recognize this and check for updates. If any updates are suggested, be sure to upgrade.
2. download this repository, navigate to the `executables` folder and choose your operating system. For example if you have a windows x64 OS run the `oscRemoteControl.exe` file inside `executables/application.windows64/`. 
3. If you see the following screen, you have successfully installed OscRemoteControl on your computer:<br><br>
<img width="450px" src="https://user-images.githubusercontent.com/44790691/54880769-11d10180-4e49-11e9-9cf9-3693b464ca6c.PNG">


### 2. Run OscRemoteControl with Processing
1. download and install the processing programming environment for your operating system [here](https://processing.org/download/). 
2. Install the oscP5 library for processing. This step is mandatory, since OscRemoteControl wont work without it.
3. Open [oscRemoteControl.pde](oscRemoteControl.pde) with processing and run the scetch by pressing the play button in the top left corner.
4. Its successfully installed when you see the same screen as mentioned in 1.3. 


### Linking TouchOSC

Once the OscRemoteControl runs on your computer, you will need to download the TouchOSC app (this app costs 5$). You can download the app here:
- [Android](https://play.google.com/store/apps/details?id=net.hexler.touchosc_a&hl=en_US)
- [iPhone](https://itunes.apple.com/us/app/touchosc/id288120394)

To import the keyboard and mouse layout, you will additionaly need the TouchOSC Editor, which can be downloaded [here](https://hexler.net/software/touchosc)(be sure to scroll down to the "downloads" section and choose the right operating system). Extract the downloaded .zip file and run the TouchOSC Editor. Go to `Open` and import the previously downloaded file [\_RemoteControl.touchosc](). You should now see the keyboard layout. To import this layout click on `Sync` and follow the instructions. 

Once the layout is succesfully imported, you will need to setup the ip-address and port inside your TouchOSC app. In the "connections" setting, eneble OSC and insert the information shown by the OscRemoteControl. Now you should be connected and able to control your keyboard and mouse via smartphone! 

If you run into any issues feel free to open an Issue, I'm glad to help! 
