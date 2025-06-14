import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import java.util.concurrent.TimeUnit;


@Disabled
class MainTest {

    @Test
    @Timeout(value = 22, unit = TimeUnit.SECONDS)
    void timeoutTestForMain() throws Exception {
        String[] args = {};
        Main.main(args);
    }
}