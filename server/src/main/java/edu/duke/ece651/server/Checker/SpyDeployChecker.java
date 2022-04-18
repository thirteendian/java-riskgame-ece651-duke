package edu.duke.ece651.server.Checker;

import edu.duke.ece651.server.Game;
import edu.duke.ece651.server.Player;
import edu.duke.ece651.server.Wrapper.AccountHashMap;
import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.shared.IO.ClientActions.DeployAction;
import edu.duke.ece651.shared.IO.ClientActions.SpyDeployAction;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;

public class SpyDeployChecker extends ActionChecker {
    Game thisgame;
    Player thisplayer;
    SpyDeployAction spyDeployAction;

    public SpyDeployChecker(GameHashMap gameHashMap, AccountHashMap accountHashMap, AccountID accountID, GameID gameID, SpyDeployAction deployAction) {
        super(gameHashMap, accountHashMap, accountID);
        this.spyDeployAction = deployAction;
        this.thisgame = this.gameHashMap.get(gameID);
        this.thisplayer = thisgame.getPlayerHashMap().get(accountID);
    }

    @Override
    public boolean doCheck() {
        //Check If TechResource is enough
        if (thisplayer.getTechResource() >= 20) {
            //Check If territory is mine(Deploy can deploy to Mine Territory)
            if (thisgame.getMap().getTerritoryList().containsKey(spyDeployAction.getTo())) {
                if (thisgame.getMap().getTerritoryList().get(spyDeployAction.getTo()).getOwnerId() == accountID) {
                    //Check If from territory enough level 1 unit
                    if (thisgame.getMap().getTerritoryList().get(spyDeployAction.getTo()).isEnoughUnitLevelOne())
                        return true;
                }
            }
        }
        return false;
    }
}