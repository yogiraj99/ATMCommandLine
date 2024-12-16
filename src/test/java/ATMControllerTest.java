import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ATMControllerTest {

    @Test
    void shouldSayHelloWhenUserLogin() {
        String username = "newUser";
        User mockedUser = new User(username);
        ATM mockATM = Mockito.mock(ATM.class);
        Display mockDisplay = Mockito.mock(Display.class);

        Mockito.when(mockATM.loginUser(username)).thenReturn(mockedUser);

        ATMController atmController = new ATMController(mockATM, mockDisplay);
        atmController.loginUser(username);

        Mockito.verify(mockATM).loginUser(username);
        Mockito.verify(mockDisplay).sayHello(mockedUser);
    }

    @Test
    void shouldSayGoodByeWhenUserLogout() {
        String username = "newUser";
        User mockedUser = new User(username);
        ATM mockATM = Mockito.mock(ATM.class);
        Display mockDisplay = Mockito.mock(Display.class);

        Mockito.when(mockATM.loginUser(username)).thenReturn(mockedUser);

        ATMController atmController = new ATMController(mockATM, mockDisplay);
        atmController.loginUser(username);

        Mockito.verify(mockATM).loginUser(username);
        Mockito.verify(mockDisplay).sayHello(mockedUser);
    }
}