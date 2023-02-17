import java.util.*;

public class AttendanceMaster {
    private HashMap<Employee, Integer> empAtten = new HashMap<Employee,Integer>();

    AttendanceMaster( HashMap<Employee, Integer> emphash){
        this.empAtten = emphash;
    }
    public HashMap<Employee,Integer> getEmpAtten(){
        return this.empAtten;
    }

    public void showEligibleList(){
        System.out.println("---------- Eligible employees ----------\n");
        ArrayList<Employee> emList = new ArrayList<Employee>(this.empAtten.keySet()) ;
        for (Employee emp : emList) {
            if(this.empAtten.get(emp) > 9){
                System.out.println(emp.toString());
            }
        }
    }
}
