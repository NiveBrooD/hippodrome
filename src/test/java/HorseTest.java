import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class HorseTest {
    static Stream<String> arguments(){
        return Stream.of("", "\n", "\t", " ");
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

}