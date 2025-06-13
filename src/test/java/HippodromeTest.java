import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HippodromeTest {
    static List<Horse> horses;
    @BeforeEach
    void setUp() {
        horses = new ArrayList<>();
    }

    @Test
    void nullListInConstructor() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    void emptyListInConstructor() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    void getHorsesReturnsListInRightOrderAndRightObjects() {

        for (int i = 0; i < 30; i++) {
            horses.add(new Horse("Horse" + i, i));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        List<Horse> horsesFromGetter = hippodrome.getHorses();
        for (int i = 0; i < horses.size(); i++) {
            assertEquals(horses.get(i), horsesFromGetter.get(i));
        }
    }

    @Test
    void moveCallsMoveMethodForEveryHorse() {
        for (int i = 0; i < 50; i++) {
            horses.add(Mockito.mock(Horse.class));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();
        horses.forEach(horse -> Mockito.verify(horse).move());
    }

    @Test
    void getWinnerMethodReturnsHorseWithTHeLargestDistance() {
        Horse horseWithZeroDistance = new Horse("Zero", 1);
        Horse horseWithSmallDistance = new Horse("Small", 1, 30);
        Horse horseWithMediumDistance = new Horse("Medium", 1, 100);
        Horse horseWithLargeDistance = new Horse("Large", 1, 150);
        Horse horseWithAnotherLargeDistance = new Horse("Large", 1, 150);
        Hippodrome hippodrome = new Hippodrome(List.
                of(horseWithMediumDistance, horseWithLargeDistance, horseWithZeroDistance, horseWithSmallDistance, horseWithAnotherLargeDistance));

        assertEquals(horseWithLargeDistance, hippodrome.getWinner());
    }

//    @Test
//    void getWinnerWhenThereIs2HorsesWithSameLargeDistance() {
//        Horse horse1 = new Horse("Horse1", 1, 50);
//        Horse horse2 = new Horse("Horse2", 1, 100);
//        Horse horse3 = new Horse("Horse3", 1, 200);
//        Horse horse4 = new Horse("Horse4", 1, 200);
//        Hippodrome hippodrome = new Hippodrome(List.of(horse1, horse2, horse3, horse4));
//
//        assertTrue(hippodrome.getWinner().equals(horse3) || hippodrome.getWinner().equals(horse4));
//        assertTrue(hippodrome.getWinner().equals(horse4) || hippodrome.getWinner().equals(horse3));
//    }
}