package juke.command;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.function.Supplier;

import juke.common.Parser;
import juke.exception.JukeInvalidCommandException;
import juke.task.TaskStatus;
import juke.task.TaskType;

/**
 * Handles command initialization and an interface for interacting with commands.
 */
public class CommandHandler {
    /**
     * Collection of command objects mapped to its command name.
     */
    public static final HashMap<String, Supplier<Command>> COMMANDS = new HashMap<>();

    /**
     * Initializes the commands.
     */
    public static void registerCommands() {
        COMMANDS.put("echo", () -> new EchoCommand());
        COMMANDS.put("bye", () -> new ExitCommand());
        COMMANDS.put("list", () -> new ListCommand());
        EnumSet.allOf(TaskStatus.class)
                .forEach(status -> COMMANDS.put(status.getCommandName(), () -> new MarkCommand(status)));
        EnumSet.allOf(TaskType.class)
                .forEach(type -> COMMANDS.put(type.getCommandName(), () -> new AddCommand(type)));
        COMMANDS.put("delete", () -> new DeleteCommand());
        COMMANDS.put("find", () -> new FindCommand());
    }

    /**
     * Fetches the command with the given command string.
     * Returns null on a blank string.
     * Throws an exception on an invalid command.
     *
     * @param input Sting input.
     * @return Command.
     * @throws JukeInvalidCommandException Throws on invalid command.
     */
    public static Command fetchCommand(String input) throws JukeInvalidCommandException {
        ArrayList<String[]> paramSplit = Parser.parseCommand(input);
        if (paramSplit.get(0)[0].isBlank()) {
            return null;
        }
        Supplier<Command> cmdSup = CommandHandler.COMMANDS.get(paramSplit.get(0)[0]);
        if (cmdSup == null) {
            throw new JukeInvalidCommandException(paramSplit.get(0)[0]);
        }
        Command cmd = cmdSup.get().addParameter("", paramSplit.remove(0)[1]);
        for (String[] args : paramSplit) {
            cmd = cmd.addParameter(args[0], args[1]);
        }
        return cmd;
    }

    /**
     * Executes the given command and returns it.
     * Returns null if the input is null.
     *
     * @param command Command to execute.
     * @return The same command after executed.
     */
    public static Command execute(Command command) {
        if (command == null) {
            return null;
        }
        return command.execute();
    }

    /**
     * Fetches the result of the command.
     * Returns an empty result if the input is null.
     *
     * @param command Command to fetch result.
     * @return Result.
     */
    public static Result fetchResult(Command command) {
        if (command == null) {
            return Result.empty();
        }
        return command.getResult();
    }
}
