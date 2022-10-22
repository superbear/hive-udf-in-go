package go;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ClientTest {
    @Test
    @DisplayName("test Add")
    public void testAdd() {
        assertEquals(Client.Add(0, 0), 0);
        assertEquals(Client.Add(1, 1), 2);
        assertEquals(Client.Add(-1, 1), 0);
    }

    @Test
    @DisplayName("test ToUpper")
    public void testToUpper() {
        assertEquals(Client.ToUpper(""), "");
        assertEquals(Client.ToUpper("1"), "1");
        assertEquals(Client.ToUpper(" hello, world!"), " HELLO, WORLD!");
        assertEquals(Client.ToUpper("HELLO, world!"), "HELLO, WORLD!");
        assertEquals(Client.ToUpper("helLO, world!"), "HELLO, WORLD!");
    }

    @Test
    @DisplayName("test Atoi")
    public void testAtoi() {
        assertEquals(Client.Atoi(""), null);
        assertEquals(Client.Atoi("1"), 1L);
        assertEquals(Client.Atoi("-2"), -2L);
        assertEquals(Client.Atoi("HELLO, world!"), null);
        assertEquals(Client.Atoi("3.14"), null);
        assertEquals(Client.Atoi("0x7"), null);
    }
}
