package controller.commands;

import java.util.LinkedList;

import view.FView;

public class CommandDisplayHint implements Command {
    
    private FView view;
    private String hint;
    
    public CommandDisplayHint(FView v) 
    {
        this.view=v;
    }
    @Override
    public void execute() 
    {
        this.view.displayHint(hint);
    }

    @Override
    public void init(LinkedList<String> params) 
    {
        LinkedList<String> i = params;
        this.hint=params.removeFirst();
    }

}