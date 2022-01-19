package exception;

public class JukeTaskListFullException extends JukeException {
    public JukeTaskListFullException(String cmd) {
        super(cmd);
    }
    
    @Override
    public String getMessage() {
        return "Task list is full, " + this.getCommand() + " could not add new task to list.";
    }
}
