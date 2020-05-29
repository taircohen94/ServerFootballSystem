package AssociationAssets;

import org.junit.Before;
import org.junit.Test;

import java.sql.Time;
import java.util.Date;

import static org.junit.Assert.*;

public class FieldTest {

    Field field;
    @Before
    public void setUp() throws Exception {

        field= new Field("Teddi", "Beer Sheva", 800);

    }



    @Test
    public void getName() {
        assertEquals(field.getName(),"Teddi");
    }

    @Test
    public void setName() {
        field.setName("Sami Ofer");
        assertNotEquals(field.getName(),"Teddi");

    }

    @Test
    public void getCity() {
        assertEquals(field.getCity(),"Beer Sheva");
    }

    @Test
    public void setCity() {
        field.setCity("London");
        assertEquals(field.getCity(),"London");
    }

    @Test
    public void getCapacity() {
        assertEquals(field.getCapacity(),800);
    }

    @Test
    public void setCapacity() {
        field.setCapacity(100);
        assertEquals(field.getCapacity(),100);
    }
}