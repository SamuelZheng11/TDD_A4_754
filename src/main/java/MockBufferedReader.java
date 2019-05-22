import java.util.LinkedList;

public class MockBufferedReader implements IBufferedReader {
    private int type;
    private LinkedList<String> inputStreamNoError = new LinkedList<String>();

    public MockBufferedReader(int type) {
        // typed used to represent if this is an input or error buffer reader
        this.type = type;

        inputStreamNoError.add("Linter completed with no linting errors");
    }

    public String readLine() {
        if (type == 0) {
            if (inputStreamNoError.size() != 0) {
                String returnValue = inputStreamNoError.getFirst();
                inputStreamNoError.removeFirst();
                return returnValue;
            } else {
                return null;
            }

        } else {
            return "";
        }
    }
}
