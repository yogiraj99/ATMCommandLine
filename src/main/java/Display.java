import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.PrintStream;

@Getter
@AllArgsConstructor
public class Display {

    private final PrintStream printStream;

    public void sayHello(User user) {
        printStream.println("Hello, " + user.getUsername());
    }

    public void sayGoodBye(User user) {
        printStream.println("Goodbye, " + user.getUsername());
    }
}
