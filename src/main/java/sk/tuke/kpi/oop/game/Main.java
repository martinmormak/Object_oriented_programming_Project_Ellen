package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.backends.lwjgl.LwjglBackend;
import sk.tuke.kpi.oop.game.scenarios.EscapeRoom;
import sk.tuke.kpi.oop.game.scenarios.MyScenar;


public class Main {
    public static void main(String[] args) {
        // nastavenie okna hry: nazov okna a jeho rozmery
        WindowSetup windowSetup = new WindowSetup("Project Ellen", 800, 600);

        // vytvorenie instancie hernej aplikacie
        // pouzijeme implementaciu rozhrania `Game` triedou `GameApplication`
        Game game = new GameApplication(windowSetup, new LwjglBackend());  // v pripade Mac OS bude druhy parameter "new Lwjgl2Backend()"

        // vytvorenie sceny pre hru
        //Scene scene = new World("mission-impossible", "maps/mission-impossible.tmx", new MissionImpossible.Factory());
        Scene scene = new World("escape-room", "maps/my-room.tmx", new MyScenar.Factory());
        //Scene scene = new World("world","maps/mission-impossible.tmx");

        //vytvorenie sceny pre inspector
        //Scene scene = new InspectableScene(new World("world"), List.of("sk.tuke.kpi"));

        //FirstSteps firstSteps=new FirstSteps();

        //MissionImpossible missionImpossible =new MissionImpossible();

        //EscapeRoom escapeRoom = new EscapeRoom();

        MyScenar myScenar=new MyScenar();

        //pridanie scenara na scenu
        scene.addListener(myScenar);

        //FirstSteps firstSteps=new FirstSteps();

        //pridanie scenara na scenu
        //scene.addListener(firstSteps);

        // pridanie sceny do hry
        game.addScene(scene);

        //ukoncenie hry
        game.getInput().onKeyPressed(Input.Key.ESCAPE, game::stop);//game.getInput().onKeyPressed(Input.Key.ESCAPE, () -> game.stop());

        // spustenie hry
        game.start();
    }
}
