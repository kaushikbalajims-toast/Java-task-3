import java.util.*;


public class MasterData {
    private ArrayList<Employee> empList = new ArrayList<Employee>();

    MasterData(ArrayList<Employee> emp){
        this.empList = emp;
    }

    // public void displayEmployees(){
    //     if(this.empList.size()==0){
    //         System.out.println("No employees to display\n");
    //     }
    //     else{
    //         for (Employee emp : this.empList) {
    //             System.out.println("-----------------------------------");
    //             System.out.println(emp.toString());
    //         }
    //     }
    // }

    public ArrayList<Employee> getEmpList(){
        return this.empList;
    }

    public void setEmpList(ArrayList<Employee> employeeList){
        this.empList = employeeList;
    }

}
