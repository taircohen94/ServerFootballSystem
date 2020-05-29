package AssociationAssets;

import org.junit.Before;
import org.junit.Test;

import java.sql.Time;
import java.util.Date;

import static org.junit.Assert.*;

public class EventTest {

    Event e;

    @Before
    public void setUp() throws Exception {

        Date date= new Date(2020,12,12);
        Time time= new Time(19,21,0);
        EEventType eEventType= EEventType.FOUL;
        e= new Event(date,time,eEventType,"Messi NOOO!!!");
    }

    @Test
    public void getDate() {
        Date date= new Date(2020,12,12);
        assertEquals(e.date.getDate(),date.getDate());
    }

    @Test
    public void setDate() {
        Date date= new Date(2030,13,13);
        e.setDate(date);
        assertEquals(e.date.getDate(),date.getDate());
    }

    @Test
    public void getTime() {
        Time time= new Time(19,21,0);
        assertEquals(e.time.getTime(),time.getTime());
    }

    @Test
    public void setTime() {
        Time time= new Time(18,28,0);
        e.setTime(time);
        assertEquals(e.time.getTime(),time.getTime());
    }

    @Test
    public void getEventType() {
        assertEquals(e.getEventType(),EEventType.FOUL);
    }

    @Test
    public void setEventType() {
        e.setEventType(EEventType.GOALHOST);
        assertEquals(e.getEventType(),EEventType.GOALHOST);
    }

    @Test
    public void getDescription() {
        assertEquals(e.getDescription(),"Messi NOOO!!!");
    }

    @Test
    public void setDescription() {
        e.setDescription("Messi YESSS!!!");
        assertEquals(e.getDescription(),"Messi YESSS!!!");

    }
}