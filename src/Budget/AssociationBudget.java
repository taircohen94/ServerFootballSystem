package Budget;

import java.util.HashMap;
import Users.Fan;

public class AssociationBudget {
    private static double  registrationFee; //all teams have the same registration Fee
    private static double TutuIntakes;
    private static HashMap<Fan, Double> Salaries;

    /**
     * Constructor
     */
    public AssociationBudget()
    {
        Salaries=  new HashMap<Fan, Double>();
    }

    /**
     * add salary for user
     * @param user
     * @param salary
     */
    public void addSalary(Fan user,Double salary) {
        if(user == null || salary < 0){
            return;
        }
        Salaries.put(user,salary);
    }

    //region Setters: setRegistrationFee , setTotoIntakes
    public void setRegistrationFee(double registrationFee) {
        if(!(registrationFee< 0) ){
            this.registrationFee = registrationFee;
        }
    }

    public void setTutuIntakes(double tutuIntakes)
    {
        if(!(tutuIntakes < 0)) {
            TutuIntakes = tutuIntakes;
        }
    }
     public boolean userSalaryIsExist(Fan fan){
        return this.Salaries.containsKey(fan);
     }

    //region Getters: getRegistrationFee, getTutuIntakes
    public double getRegistrationFee() {
        return registrationFee;
    }

    public double getTutuIntakes() {
        return TutuIntakes;
    }
    //endregion

}



