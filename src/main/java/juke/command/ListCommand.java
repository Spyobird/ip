package juke.command;

import juke.exception.JukeEmptyTaskListException;
import juke.exception.JukeException;
import juke.exception.JukeInvalidParameterException;

public class ListCommand extends Command {

    @Override
    public Command checkParametersAndArguments() {
        for (String param : this.paramArgs.keySet()) {
            if (!this.isDefaultParameter(param)) {
                this.result = Result.error(new JukeInvalidParameterException(param));
                return this;
            }
        }
        if (this.hasDefaultArgument()) {
            this.result = Result.error(new JukeException("Default argument not needed."));
            return this;
        }
        return this;
    }

    @Override
    public Command execute() {
        if (this.isSuccessful()) {
            return this;
        }
        this.checkParametersAndArguments();
        if (this.isErroneous()) {
            return this;
        }
        try {
            String[] strs = this.juke.getTaskList().list();
            this.result = Result.success(strs);
        } catch (JukeEmptyTaskListException e) {
            this.result = Result.error(e);
            return this;
        }
        return this;
    }
}
