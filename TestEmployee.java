import java.util.*;

public class TestEmployee{
    public static void main(String[] args) {
        ArrayList<Employee> employees = new ArrayList<Employee>();
        int choice = 1;
        double salary = 0;
        Scanner scin = new Scanner(System.in);
        boolean invalidInput = false;
        HashMap<Employee,Integer> empHash = new HashMap<Employee, Integer>();

        while(choice == 1){
            Employee employee = new Employee();
            do{
                invalidInput = false;
                try{
                    System.out.print("---- Menu ----\n1.Add employees\nAny other number to display and move to next menu\nEnter choice: ");
                    choice = Integer.parseInt(scin.nextLine());
                }
                catch(NumberFormatException e){
                    System.out.println("Enter valid menu option (only numbers)\n");
                    invalidInput = true;
                }
            }while(invalidInput); // proper choice at this point

            // choice = getAnInteger(1, 1, 0);

            if(choice == 1){      // when adding employee
                System.out.print("Enter name: ");
                employee.setName(scin.nextLine()); //to validate name
                System.out.print("\nDepartment choices \n1.R & D\n2.IT\n3.Admin\n4.HR\n5.Support\nEnter department choice:");
                employee.setDept(scin.nextLine()); //to validate department
                System.out.print("Designation choice\n1.Manager\n2.Senior developer\n3.Software developer\n4.Intern\nEnter designation choice: ");
                employee.setDesg(scin.nextLine()); //to validate designation
                do{
                    invalidInput = false;
                    try{
                        System.out.print("Enter Salary: ");
                        salary = Double.parseDouble(scin.nextLine());
                    }
                    catch(NumberFormatException e){
                        System.out.println("Enter valid salary");
                        invalidInput = true;
                        System.out.println();
                    }
                    if(salary < 10000 && invalidInput!=true){
                        System.out.println("Enter more than 4 figure salary");
                        invalidInput = true;
                        System.out.println();
                    }
                }while(invalidInput);
                employee.setSal(salary);  //to set the correct salary
                
                employees.add(employee);
                System.out.println();
            }
        }

        MasterData mData = new MasterData(employees);
        // md.displayEmployees();
        ArrayList<Employee> empList = mData.getEmpList();

        // to display the employees when pressed anyother number
        if(empList.size()==0){
            System.out.println("No employees to display ... Bye bye\n");
            System.exit(0);
        }
        else{
            for (Employee emp : empList) {
                emp.SetAllowance();
                System.out.println(emp.toString());
            }
        }

        choice = 1; // choice for attendance menu
        while(true){
            do{
                invalidInput = false;
                try{
                    System.out.print("\n1.Add attendance\n2.Display Eligiblity list\nAny other number to exit\nEnter choice: ");
                    choice = Integer.parseInt(scin.nextLine());
                }
                catch(NumberFormatException e){
                    System.out.println("Enter valid menu option (only numbers)\n");
                    invalidInput = true;
                }
            }while(invalidInput);  //proper choice for attendance menu at this point

            if(choice == 1){
                if(empList.size()>0){
                    for (Employee emp : empList){
                        int attendance = 0;
                        invalidInput = false;
                        do{
                            invalidInput = false;
                            try{
                                System.out.print("\nEnter attendance for Employee "+emp.getEmpID() + " " + emp.getName() + ": ");
                                attendance = Integer.parseInt(scin.nextLine());
                            }
                            catch(NumberFormatException e){
                                System.out.println("Enter attendance as a number\n");
                                invalidInput = true;
                            }
                            if(attendance<0 && !invalidInput){
                                System.out.println("Enter positive value for attendance\n");
                                invalidInput = true;
                            }
                        }while(invalidInput);   
                        empHash.put(emp, attendance);  //valid attendance inserted to hashmap
                    }
                }
                else{
                    System.out.println("No employees to add attendance to");
                }
            }

            else if(choice == 2){  //to display the eligible list of employees
                if(empHash.size() > 0){
                    AttendanceMaster am = new AttendanceMaster(empHash);
                    am.showEligibleList();
                }
                else{
                    System.out.println("Attendance not entered for any employees\n");
                }
            }
            else{
                System.out.println("Bye Bye");
                scin.close();
                System.exit(0);
            }
        }
    }
}