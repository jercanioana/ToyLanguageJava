package View;

import Controller.Controller;

public class RunExample extends Command {
    private Controller ctrl;

    public RunExample(String k, String d, Controller c){
        super(k,d);
        this.ctrl = c;
    }

    public Controller getCtrl(){
        return ctrl;
    }

    @Override
    public void execute() {
        try{
            ctrl.allStep();
        }catch(Exception e){
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }
}
