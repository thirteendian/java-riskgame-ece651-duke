package edu.duke.ece651.server.Checker;

import edu.duke.ece651.server.Wrapper.AccountHashMap;
import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.shared.Game;
import edu.duke.ece651.shared.Player;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;

import java.util.ArrayList;

public class UpgradeTechChecker extends ActionChecker{
    private Integer nextLevel;
    private Integer currTechResource;
    private Boolean isTechUpgraded;
    private ArrayList<Integer> TechLevelUpgradeList;
    private int cost;
    public UpgradeTechChecker(AccountID accountID,
                              GameHashMap gameHashMap,
                              AccountHashMap accountHashMap,
                              Boolean isTechUpgraded,
                              Integer _nextLevel,
                              Integer _currTechResource,
                              ArrayList<Integer> _TechLevelUpgradeList
                              ){
        super( gameHashMap,accountHashMap ,accountID);
        this.isTechUpgraded = isTechUpgraded;
        this.nextLevel = _nextLevel;
        this.currTechResource = _currTechResource;
        this.TechLevelUpgradeList = _TechLevelUpgradeList;
        this.cost = TechLevelUpgradeList.get(nextLevel);
    }


    @Override
    public boolean doCheck(){
        //valid iff nextLevel <= 6 and player has enough tech resource
        if (!this.isTechUpgraded && nextLevel <= 6 && cost <= currTechResource) {
            return true;
        }
        return false;
    }

    public int getCost(){return cost;}

    public int getNextLevel(){return this.nextLevel;}
}
