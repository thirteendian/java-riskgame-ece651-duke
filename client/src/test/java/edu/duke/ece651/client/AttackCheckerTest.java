package edu.duke.ece651.client;

import edu.duke.ece651.shared.Map;
import edu.duke.ece651.shared.Unit;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class AttackCheckerTest {

    public AttackChecker createAttackChecker(String to){
        int id = 1;
        Map myMap = new Map(3);
        myMap.getTerritoryList().get("a1").setOwner(1);
        myMap.getTerritoryList().get("a1").getUnits().put(1, new ArrayList<>());
        //add 3 level-1 units to a1
        myMap.getTerritoryList().get("a1").getUnits().get(1).add(new Unit(1));
        myMap.getTerritoryList().get("a1").getUnits().get(1).add(new Unit(1));
        myMap.getTerritoryList().get("a1").getUnits().get(1).add(new Unit(1));

        myMap.getTerritoryList().get("a2").setOwner(1);
        myMap.getTerritoryList().get("a2").getUnits().put(1, new ArrayList<>());
        myMap.getTerritoryList().get("b1").setOwner(2);
        myMap.getTerritoryList().get("b1").getUnits().put(1, new ArrayList<>());

        myMap.getTerritoryList().get("b3").setOwner(2);
        myMap.getTerritoryList().get("b3").getUnits().put(1, new ArrayList<>());

        HashMap<Integer, Integer> attackUnits = new HashMap<>();

        attackUnits.put(1, 3); // move 3 level-1 attack units
        String from = "a1";
        AttackChecker myChecker = new AttackChecker(myMap, attackUnits, from, to, 1);
        return myChecker;
    }

    @Test
    void doCheck() {
        String output;
        //case 1: correct case
        MoveChecker myChecker = createAttackChecker("b1");
        output = myChecker.doCheck(1, myChecker.getFrom_name(), myChecker.getTo_name());
        assertEquals(output, null);

        //case 2: same owners
        MoveChecker myChecker2 = createAttackChecker("a2");
        output = myChecker2.doCheck(1, myChecker2.getFrom_name(), myChecker2.getTo_name());
        System.out.println(output);
        assertEquals(output, "Terr1 and Terr2 belong to the same(wrong) player.");

        //case 3: path does not exist
        MoveChecker myChecker3 = createAttackChecker("b3");
        output = myChecker3.doCheck(1, myChecker3.getFrom_name(), myChecker3.getTo_name());
        System.out.println(output);
        assertEquals(output, "Attack Error: Territory \"a1\" and Territory \"b3\" are not adjacent!");
    }
}