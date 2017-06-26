package controller.commands;

import java.util.LinkedList;

import model.FModel;

public class CommandGetHint implements Command {
    private FModel model;
    
    
    public CommandGetHint(FModel m) 
    {
        this.model =m;
    }
    
    
    @Override
    public void execute() 
    {
        this.model.getHint();
    }

    @Override
    public void init(LinkedList<String> params) 
    {
        // i don't need shit
    }

}
