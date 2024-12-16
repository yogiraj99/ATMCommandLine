import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ATMControllerTest {

    @SneakyThrows
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

    @SneakyThrows
    @Test
    void shouldSayGoodByeWhenUserLogout() {
        String username = "newUser";
        User mockedUser = new User(username);
        ATM mockATM = Mockito.mock(ATM.class);
        Display mockDisplay = Mockito.mock(Display.class);

        Mockito.when(mockATM.logoutUser(username)).thenReturn(mockedUser);

        ATMController atmController = new ATMController(mockATM, mockDisplay);
        atmController.logoutUser(username);

        Mockito.verify(mockATM).logoutUser(username);
        Mockito.verify(mockDisplay).sayGoodBye(mockedUser);
    }
}