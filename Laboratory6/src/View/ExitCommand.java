package View;

public class ExitCommand extends Command {
    private String key;
    private String description;
    public ExitCommand(String k, String d){
        super(k, d);
    }

    @Override
    public void execute() {
        System.exit(0);
    }
}
