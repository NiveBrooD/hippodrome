import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HorseTest {
    static  Horse horse;
    static Stream<String> arguments(){
        return Stream.of("", "\n", "\t", " ");
    }

    @BeforeAll
    static void setUp(){
        horse = new Horse("Horsey", 20, 30.0);
    }

    @Test
    void nullInConstructor() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(null, 3.0, 20.0));
        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("arguments")
    void blankInConstructor(String input) {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(input, 3.0, 20.0));
        assertEquals("Name cannot be blank.", exception.getMessage());

    }

    @Test
    void negativeSpeed() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse("Horsea", -3.0, 20.0));
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    void negativeDistance() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse("Horsea", 3.0, -20.0));
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    void getName() {
        assertEquals("Horsey", horse.getName());
    }

    @Test
    void getSpeed() {
        assertEquals(20.0, horse.getSpeed());
    }

    @Test
    void getDistance() {
        assertEquals(30.0, horse.getDistance());
        assertEquals(0, new Horse("Bob", 20).getDistance());
    }

    @Test
    void moveCallsGetRandomMethod() {
        Horse testHorse = new Horse("Ted", 2);
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
//            horseMockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(0.3);
            testHorse.move();
            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @CsvSource({
            "Bob, 0.3, 10.0, 5.0, 11.5",
            "Bob, 0.5, 10.0, 5.0, 12.5",
            "Bob, 0.8, 10.0, 5.0, 14.0",
            "Bob, 0.77, 10.33, 11.7, 19.339"
    })
    void testWithRandomValues(String name, double randomValue, double distance, double speed, double expected) {
        Horse testHorse = new Horse(name, speed, distance);
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            horseMockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(randomValue);
            testHorse.move();
            assertEquals(testHorse.getDistance(), expected);
        }
    }
}