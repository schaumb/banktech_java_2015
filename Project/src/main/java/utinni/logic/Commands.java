package utinni.logic;

import eu.loxon.centralcontrol.WsCoordinate;
import eu.loxon.centralcontrol.WsDirection;

import java.util.ArrayList;
import java.util.List;

public class Commands implements Cloneable {

    private ArrayList<Command> commands = new ArrayList<>();

    private WsCoordinate from;

    public Commands(WsCoordinate from) {
        this.from = from;
    }

    public Commands(Commands copy) {
        commands = new ArrayList<>(copy.commands);
        from = copy.from;
    }

    public ArrayList<Command> getCommands() {
        return commands;
    }

    public void addCommand(Command command) {
        assert(getLastCoordinate().equals(command.fieldFrom)) ;
        commands.add(command);
    }

    public Integer getCost() {
        return commands.stream().mapToInt((Command command) -> command.getCost()).sum();
    }

    public Integer getCostMax(int max) {
        int result = 0;
        for(Command c : commands) {
            if(result + c.getCost()  > max) {
                break;
            }
            else {
                result += c.getCost();
            }
        }
        return result;
    }

    public Command getFirstCommand() {
        if(commands.size() == 0) {
            return null;
        }
        return commands.get(0);
    }

    public WsCoordinate getLastCoordinate() {
        if(commands.size() == 0) {
            return from;
        }
        return commands.get(commands.size() - 1).getAffectCoordinate();
    }

    public List<Command> getNextStep() {
        List<Command> result =  new ArrayList<>();
        WsCoordinate from = getLastCoordinate();
        for(WsDirection wsDirection : WsDirection.values()) {
            result.add(new Command(from, wsDirection));
        }
        return result;
    }


    public Command.Type getLastType() {
        if(commands.size() == 0) {
            return null;
        }
        return commands.get(commands.size() - 1).getCommandType();
    }

    public boolean lastCommandIsNotMove() {
        if(commands.size() == 0) {
            return false;
        }
        return getLastType() != Command.Type.Move;
    }

    @Override
    public String toString() {
        return "[" + commands.stream()
                .map(Object::toString)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString() + " " + getCost() + "]";
    }
}
