package edu.duke.ece651.shared.Visitor;

import edu.duke.ece651.shared.IO.ClientActions.*;

public interface ActionVisitor {

    void visit(AttackAction attackAction);

    void visit(CommitAction commitAction);

    void visit(DeployAction deployAction);

    void visit(JoinAction joinAction);

    void visit(LoginAction loginAction);

    void visit(LogoutAction logoutAction);

    void visit(MoveAction moveAction);

    void visit(NewGameAction newGameAction);

    void visit(PlayAgainAction playAgainAction);

    void visit(QuitGameAction quitGameAction);

    void visit(SignUpAction signUpAction);

    void visit(SwitchGameAction switchGameAction);

    void visit(UpdateTechAction updateTechAction);

    void visit(UpdateUnitsAction updateUnitsAction);

}