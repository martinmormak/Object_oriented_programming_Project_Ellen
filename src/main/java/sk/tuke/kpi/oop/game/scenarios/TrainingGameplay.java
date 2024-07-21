package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.Scenario;
import sk.tuke.kpi.gamelib.map.MapMarker;
import sk.tuke.kpi.oop.game.*;
import sk.tuke.kpi.oop.game.items.Hammer;
import sk.tuke.kpi.oop.game.items.Wrench;

import java.util.Map;

public class TrainingGameplay extends Scenario {

    @Override
    public void setupPlay(@NotNull Scene scene) {
        Map<String, MapMarker> markers = scene.getMap().getMarkers();
        MapMarker reactorArea1 = markers.get("reactor-area-1");
        Reactor reactor = new Reactor();
        scene.addActor(reactor, reactorArea1.getPosX(), reactorArea1.getPosY());
        reactor.turnOn();
        Cooler cooler = new Cooler(reactor);
        MapMarker coolerArea = markers.get("cooler-area-1");
        scene.addActor(cooler, coolerArea.getPosX(), coolerArea.getPosY());
        new ActionSequence<>(new Wait<>(5), new Invoke<>(cooler::turnOn)).scheduleFor(cooler);
        Hammer hammer = new Hammer();
        scene.addActor(hammer, 64, 0);
        new When<>(() -> reactor.getTemperature() >= 3000, new Invoke<>(() -> hammer.useWith(reactor))).scheduleFor(reactor);
        Light light = new Light();
        reactor.addDevice(light);
        scene.addActor(light, 100, 70);
        Computer computer = new Computer();
        reactor.addDevice(computer);
        MapMarker computerArea = markers.get("computer-area");
        scene.addActor(computer, computerArea.getPosX(), computerArea.getPosY());
        PowerSwitch reactorSwitch = new PowerSwitch(reactor);
        scene.addActor(reactorSwitch, 0, 70);
        PowerSwitch coolerSwitch = new PowerSwitch(cooler);
        scene.addActor(coolerSwitch, 0, 128);
        PowerSwitch lightswitch = new PowerSwitch(light);
        scene.addActor(lightswitch, 0, 64);
        DefectiveLight defectivelight = new DefectiveLight();
        reactor.addDevice(defectivelight);
        scene.addActor(defectivelight, 200, 380);
        Wrench wrench = new Wrench();
        scene.addActor(wrench, 100, 380);
        //teleport1.setDestination(teleport2);
        Teleport teleport3 = new Teleport();
        scene.addActor(teleport3, 250, 60);
        //teleport2.setDestination(teleport3);
        Teleport teleport2 = new Teleport(teleport3);
        scene.addActor(teleport2, 250, 250);
        Teleport teleport1 = new Teleport(teleport2);
        scene.addActor(teleport1, 60, 250);
        ChainBomb chainBomb1 = new ChainBomb(10);
        //scene.addActor(chainBomb1, 292, 292);
        scene.addActor(chainBomb1, 292, 292);
        ChainBomb chainBomb2 = new ChainBomb(10);
        //scene.addActor(chainBomb2, 292, 350);
        scene.addActor(chainBomb2, 292, 349);
        ChainBomb chainBomb3 = new ChainBomb(10);
        //scene.addActor(chainBomb3, 292, 234);
        scene.addActor(chainBomb3, 292, 235);
        ChainBomb chainBomb4 = new ChainBomb(10);
        //scene.addActor(chainBomb4, 234, 292);
        scene.addActor(chainBomb4, 235, 292);
        ChainBomb chainBomb5 = new ChainBomb(10);
        //scene.addActor(chainBomb5, 350, 292);
        scene.addActor(chainBomb5, 349, 292);
    }
}
