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
        int eligibleCount = 0;
        System.out.println("---------- Eligible employees ----------\n");
        ArrayList<Employee> emList = new ArrayList<Employee>(this.empAtten.keySet()) ;
        System.out.format("%10s %20s %20s %20s %20s", "Employee ID", "Name", "Designation", "Department", "Salary");
        System.out.println();
        System.out.println("-------------------------------------------------------------------------------------------------");
        for (Employee emp : emList) {
            if(this.empAtten.get(emp) > 9){
                System.out.println(emp.toString());
                eligibleCount++;
            }
        }

        if(eligibleCount == 0){
            System.out.println("No employees eligible\n");
        }
    }

    public void FilterEmployeeList(){
        ArrayList<Employee> emList = new ArrayList<Employee>(empAtten.keySet());
        for (Employee emp : emList) {
            if(empAtten.get(emp) < 10){
                empAtten.remove(emp);
            }
        }
    }
}
