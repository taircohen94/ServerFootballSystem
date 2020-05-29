package Budget;

import Users.Fan;
import Users.Coach;
import Users.ECoachRole;
import Users.ETraining;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class AssociationBudgetTest {
    AssociationBudget associationBudget;
    HashMap<Fan, Double> salaries;
    Fan user;
    @Before
    public void setUp(){
        salaries= new HashMap<>();
        user = new Coach("DaniLevi","Dani","Levi", ETraining.CDiploma, ECoachRole.AssistantCoach);
        associationBudget = new AssociationBudget();
    }

    @Test
    public void addSalary() {
        salaries.put(user,1000.0);
        assertTrue(salaries.containsKey(user));
    }

    @Test
    public void setRegistrationFee() {
        associationBudget.setRegistrationFee(1000);
        assertEquals(associationBudget.getRegistrationFee(), 1000,0);
    }

    @Test
    public void setTutuIntakes() {
        associationBudget.setTutuIntakes(1000);
        assertEquals(associationBudget.getTutuIntakes(),1000,0);
    }

    @Test
    public void getRegistrationFee() {
        associationBudget.setRegistrationFee(5);
        assertEquals(associationBudget.getRegistrationFee(),5,0);
    }

    @Test
    public void getTutuIntakes() {
        associationBudget.setTutuIntakes(10);
        assertEquals(associationBudget.getTutuIntakes(),10,0);
    }
    @Test
    public void userSalaryIsExist(){
        associationBudget.addSalary(user,1000.0);
        assertTrue(associationBudget.userSalaryIsExist(user));
    }

}