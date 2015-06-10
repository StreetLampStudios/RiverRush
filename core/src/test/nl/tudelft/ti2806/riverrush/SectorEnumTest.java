package nl.tudelft.ti2806.riverrush;

import nl.tudelft.ti2806.riverrush.domain.entity.Sector;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SectorEnumTest {

    @Test
    public void testGetNext() {
        Sector s = Sector.FRONT.getNext();
        assertEquals(Sector.FRONT_MIDDLE, s);
        s = s.getNext();
        assertEquals(Sector.MIDDLE, s);
        s = s.getNext();
        assertEquals(Sector.BACK_MIDDLE, s);
        s = s.getNext();
        assertEquals(Sector.BACK, s);
        s = s.getNext();
        assertEquals(Sector.FRONT, s);
    }
}
