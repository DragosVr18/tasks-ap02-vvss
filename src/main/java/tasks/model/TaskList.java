package tasks.model;

import org.apache.log4j.Logger;
import tasks.services.TaskIO;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public abstract class TaskList implements Iterable<Task>, Serializable  {
    public abstract void add(Task task);
    public abstract boolean remove(Task task);
    public abstract int size();
    public abstract Task getTask(int index);
    public abstract List<Task> getAll();

    public abstract Iterator<Task> iterator();

    private static final Logger log = Logger.getLogger(TaskList.class);

    public TaskList incoming(Date from, Date to){
        TaskList incomingTasks;
        if (this instanceof ArrayTaskList){
            incomingTasks = new ArrayTaskList();
        }
        else {
            incomingTasks = new LinkedTaskList();
        }

        for(int i = 0; i < this.size(); i++){
            if(getTask(i).nextTimeAfter(from) != null && getTask(i).nextTimeAfter(from).before(to)){
                incomingTasks.add(getTask(i));
                log.info(getTask(i).getTitle());
            }
        }
        return incomingTasks;
    }

}
