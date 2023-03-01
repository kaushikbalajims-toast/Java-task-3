import java.util.*;

class SalCalculator{
    SalCalculator(){

    }

    public void CalculateSalary(HashMap<Employee, Integer> empAttendance){
        if(empAttendance.keySet().size()!=0){
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.format("%10s %20s %20s %20s %20s %20s %20s %20s %20s", "Employee ID", "Name", "Designation", "Department", "Salary", "Allowance", "PF", "Gross Salary", "Net Salary");
            System.out.println();
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            for(Employee emp: empAttendance.keySet()){
                double pf, sal;
                double allowance = 0, grossSalary = 0, netSalary = 0;
                if(emp.getDesg().compareTo("Manager") == 0){
                    sal = (emp.getSal()*10)/12;
                    allowance = sal*20/100;
                    pf = allowance/2;
                }
                else{
                    sal = (emp.getSal()*10)/11;
                    allowance = sal*10/100;
                    pf = allowance;
                }
                grossSalary = emp.getSal() + allowance;
                netSalary = grossSalary - pf;
                System.out.format("%10s %20s %20s %20s %20s %20s %20s %20s %20s", emp.getEmpID(),emp.getName(), emp.getDesg(), emp.getDept(), sal, allowance,pf, grossSalary, netSalary);
                System.out.println();
            }

        }
    }
}

