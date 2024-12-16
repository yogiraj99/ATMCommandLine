import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.PrintStream;

class DisplayTest {

    @Test
    void shouldSayHelloUser() {
        String username = "newUser";
        User user = new User(username);
        PrintStream mockPrintStream = Mockito.mock(PrintStream.class);
        Display display = new Display(mockPrintStream);
        display.sayHello(user);

        Mockito.verify(mockPrintStream).println("Hello, " + username);
    }

    @Test
    void shouldSayGoodByeUser() {
        String username = "newUser";
        User user = new User(username);
        PrintStream mockPrintStream = Mockito.mock(PrintStream.class);
        Display display = new Display(mockPrintStream);
        display.sayGoodBye(user);

        Mockito.verify(mockPrintStream).println("Goodbye, " + username);
    }

}