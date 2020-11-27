package object;

public class Step {
    public String numberBR;
    public String action;
    public String precondition;
    public String check;

    public Step(String numberBR, String action, String precondition, String check) {
        this.numberBR = numberBR;
        this.action = action;
        this.precondition = precondition;
        this.check = check;
    }
}
