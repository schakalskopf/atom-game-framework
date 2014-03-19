package sg.atom.managex.api.action;

/**
 * AtomAction is modeled with BouyUI event style
 *
 * @author hungcuong
 */
public abstract class AtomEditorAction implements Runnable{

    public abstract boolean isCancelTrigger(String name);

    public static enum ActionStatus {

        ACTION_INITED, ACTION_STARTED, ACTION_CONTINUE, ACTION_PAUSE, ACTION_FINISHED
    }
    
    String actionName;
    String objectName;
    String targetName;
    String helperName;
    ActionStatus status;

    public AtomEditorAction(String actionName) {
        this.actionName = actionName;
        this.status = ActionStatus.ACTION_INITED;
    }

    public void actionStart() {
        this.status = ActionStatus.ACTION_STARTED;
    }

    public void actionCancel() {
        actionFinish();
    }

    public AtomEditorCommand actionFinish() {
        this.status = ActionStatus.ACTION_FINISHED;
        return new AtomEditorCommand(this);
    }

    public abstract void actionPerformed(String name);
    // SETTERs & GETTERs

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }
}
