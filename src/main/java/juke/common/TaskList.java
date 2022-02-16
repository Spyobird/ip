package juke.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import juke.exception.JukeEmptyTaskListException;
import juke.task.Task;

/**
 * Abstraction for list of tasks.
 */
public class TaskList implements Iterable<Task> {
    /**
     * Internal list for tasks.
     */
    private final ArrayList<Task> taskList;

    /**
     * Constructor to initialize empty internal list.
     */
    public TaskList() {
        this.taskList = new ArrayList<>();
    }

    /**
     * Constructor to initialize internal list with elements of a collection.
     *
     * @param tasks Collection of tasks.
     */
    public TaskList(Collection<Task> tasks) {
        taskList = new ArrayList<>();
        taskList.addAll(tasks);
    }

    /**
     * Adds a task to the end of the list.
     *
     * @param task Task to add.
     * @return True.
     */
    public boolean add(Task task) {
        return taskList.add(task);
    }

    /**
     * Removes the task at a specific index.
     * Returns null if index out of bounds.
     *
     * @param index Index to remove.
     * @return Task at the index.
     */
    public Task remove(int index) {
        try {
            return taskList.remove(index);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * Returns the task at a specific index.
     * Returns null if index out of bounds.
     *
     * @param index Index to return.
     * @return Task at the index.
     */
    public Task get(int index) {
        try {
            return taskList.get(index);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return Number of tasks.
     */
    public int size() {
        return taskList.size();
    }

    /**
     * Lists the tasks in the task list.
     *
     * @return String array of task descriptions.
     * @throws JukeEmptyTaskListException Throws if task list is empty.
     */
    public String[] list() throws JukeEmptyTaskListException {
        if (taskList.size() == 0) {
            throw new JukeEmptyTaskListException();
        }
        String[] strs = new String[taskList.size()];
        for (int i = 0; i < taskList.size(); i++) {
            strs[i] = taskList.get(i).toString();
        }
        return strs;
    }

    /**
     * Marks the task at the given index.
     * Returns false if the index is invalid.
     *
     * @param index Index to return.
     * @return Boolean.
     */
    public boolean markTask(int index) {
        try {
            taskList.get(index).markAsDone();
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    /**
     * Unmarks the task at the given index.
     * Returns false if the index is invalid.
     *
     * @param index Index to return.
     * @return Boolean.
     */
    public boolean unmarkTask(int index) {
        try {
            taskList.get(index).markAsNotDone();
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    /**
     * Queries for all tasks containing the given string.
     *
     * @param query Query string.
     * @return List containing the results of the query.
     */
    public List<Task> search(String query) {
        ArrayList<Task> queryList = new ArrayList<>();
        for (Task task : taskList) {
            if (task.getDescription().contains(query)) {
                queryList.add(task);
            }
        }
        return queryList;
    }

    /**
     * Returns the iterator over the elements of the list.
     *
     * @return Iterator.
     */
    @Override
    public Iterator<Task> iterator() {
        return taskList.iterator();
    }
}
