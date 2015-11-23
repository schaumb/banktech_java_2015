package utinni.logic;

import eu.loxon.centralcontrol.WsCoordinate;
import eu.loxon.centralcontrol.WsDirection;

import java.util.ArrayList;
import java.util.List;

public class Commands {

    private ArrayList<Command> commands = new ArrayList<>();

    private WsCoordinate from;

    public Commands(WsCoordinate from) {
        this.from = from;
    }

    // TODO This is just creates a shallow copy. That was your original intention?
    public Commands(Commands copy) {
        commands = new ArrayList<>(copy.commands);
        from = copy.from;
    }

    public ArrayList<Command> getCommands() {
        return commands;
    }

    public void addCommand(Command command) {
        // Do not forget, you have to use the -enableassertions option at run to enable this
        assert(getLastStandingCoordinate().equals(command.fieldFrom));
        commands.add(command);
    }

    public Integer getCost() {
        return commands.stream().mapToInt((Command command) -> command.getCost()).sum();
    }

    public Integer getCostMax(int max) {
        int result = 0;
        for(Command c : commands) {
            if(result + c.getCost()  > max) {
                break; // TODO Why not continue? Maybe the next command will cheaper
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

    public WsCoordinate getLastAffectingCoordinate() {
        if(commands.size() == 0) {
            return from;
        }
        return commands.get(commands.size() - 1).getAffectCoordinate();
    }

    public WsCoordinate getLastStandingCoordinate() {
        if(commands.size() == 0) {
            return from;
        }
        if(commands.get(commands.size() - 1).getCommandType() == Command.Type.Move) {
            return commands.get(commands.size() - 1).getAffectCoordinate();
        }
        else {
            return commands.get(commands.size() - 1).fieldFrom;
        }
    }

    public List<Command> getNextStep() {
        List<Command> result =  new ArrayList<>();
        WsCoordinate from = getLastStandingCoordinate();
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

    public WsDirection getLastDirection() {
        if(commands.size() == 0) {
            return null;
        }
        return commands.get(commands.size() - 1).getDirection();
    }

    public boolean lastCommandIsNotMove() {
        if(commands.size() == 0) {
            return false;
        }
        return getLastType() != Command.Type.Move;
    }

    public boolean noExplode() {
        return commands.stream().allMatch((Command command) -> command.getCommandType() != Command.Type.Explode);
    }

    @Override
    public String toString() {
        return "[" + commands.stream()
                .map(Object::toString)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString() + " " + getCost() + "]";
    }
}
