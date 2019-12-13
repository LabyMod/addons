# Labymod Addons
 
This folder contains all published Labymod addons created by TebosBrime. You can find the working directory [in this fork](https://github.com/TebosBrime/addons/).

## Contributing

### Setup

You can find a step-by-step guide in projects readme.

In a nutshell: If this is your first LabyMod addon project, start with the following command:
```bash
Windows: "gradlew setupDecompWorkspace"
Linux/Mac OS: "./gradlew setupDecompWorkspace"
```
In any case, you must initialize the project with these command: 
```bash
Windows: "gradlew IDEA"
Linux/Mac OS: "./gradlew IDEA
```

### Debugging

To debug the addon, you have to edit the created run configuration of Forge (which starts the client) and put the following VM start options in:
```-Dfml.coreMods.load=net.labymod.core.asm.LabyModCoreMod -DdebugMode=true -Daddonresources=addon.json```

### Build

To create your jar, run `./gradlew build` in the project root directory. Your final jar will be placed in **./build/libs/modid-1.0.jar**

### FYI

All my project include an aotomatic labymod api update task written by Janrupf. For this reason it is not necessary to update the API manually.

## Contact

If you have any problems or questions, you can contact me using the methods given in vendor.json. In case of problems the better way is to contact an expert on the LabyMod Discord.