import java.util.*;

public class TestEmployee{
    public static void main(String[] args) {
        ArrayList<Employee> employees = new ArrayList<Employee>();
        int choice = 1, idToFind = 0;
        boolean invalidInput = false, isFiltered = false;
        HashMap<Employee,Integer> empHash = new HashMap<Employee, Integer>();
        HashMap<Integer, Employee> employeeIdMapping = new HashMap<Integer, Employee>();
        
        MasterData mData = new MasterData(employees);
        Scanner scin = new Scanner(System.in);
        
        while(true){
            Employee employee = new Employee();
            do{
                invalidInput = false; 
                try{
                    System.out.print("---- Menu ----\n1.Add employees\n2.Display Employees list\n3.Add all employee attendance\n4.Update Attendance for an Employee ID\n5.Show Eligible Employees\n6.Filter Employees\n7.sort\n8.Provide salary\n9.Exit\nEnter choice: ");
                    choice = Integer.parseInt(scin.nextLine());
                }
                catch(NumberFormatException e){
                    System.out.println("Enter valid menu option (only numbers)\n");
                    invalidInput = true;
                }
            }while(invalidInput); // proper choice at this point
            // choice = getAnInteger(1, 1, 0);

            if(choice == 1){      // when adding employee
                employee.setName(""); //to validate name
                employee.setDesg(""); //to validate department
                employee.setDept(""); //to validate designation
                employee.setSal(0);  //to set the correct salary
                employeeIdMapping.put(employee.getEmpID(), employee);
                employees.add(employee);
                System.out.println();
            }
            else if(choice == 2){
                mData.displayEmployees();    
            }

            else if(choice == 3){
                ArrayList<Employee> empList = mData.getEmpList();
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
                            if((attendance<0 || attendance > 30) && !invalidInput){
                                System.out.println("Enter attendance for 1 month\n");
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

            else if(choice == 4){
                do{
                    invalidInput = false;
                    if(employees.size() == 0){
                        System.out.println("No employee to update attendance to");
                        invalidInput = true;
                        break;
                    }
                    else{
                        try{
                            System.out.print("Enter employee id to update attendance: ");
                            idToFind = Integer.parseInt(scin.nextLine());
                        }
                        catch(NumberFormatException e){
                            System.out.println("Enter valid Id number");
                            invalidInput = true;
                        }
                        if(!(!invalidInput && (idToFind>1000 && (idToFind<=employees.size()+1000)))){
                            System.out.println("Enter an available employee ID\n");
                            invalidInput = true;
                        }    
                    }
                }while(invalidInput);
                if(empHash.get(employeeIdMapping.get(idToFind)) == null){
                    System.out.println("\nOld attendence: 0 days");
                }
                else{
                    System.out.println("Old attendence: " + empHash.get(employeeIdMapping.get(idToFind)));
                }
                // proper employee id at this point
                int attendance = 0;
                do{
                    invalidInput = false;
                    try{
                        System.out.print("New attendance for Employee " + idToFind + " :  ");
                        attendance = Integer.parseInt(scin.nextLine());
                    }
                    catch(NumberFormatException e){
                        System.out.println("Enter attendance as a number\n");
                        invalidInput = true;
                    }
                    if((attendance<0 || attendance > 30) && !invalidInput){
                        System.out.println("Enter positive value for attendance\n");
                        invalidInput = true;
                    }
                }while(invalidInput);                
                empHash.put(employeeIdMapping.get(idToFind), attendance);
                isFiltered = false;
            }

            else if(choice == 5){
                if(empHash.size() > 0){
                    AttendanceMaster am = new AttendanceMaster(empHash);
                    am.showEligibleList();
                }
                else{
                    System.out.println("Attendance not entered for any employees\n");
                }
            }
            else if(choice == 6){
                AttendanceMaster am = new AttendanceMaster(empHash);
                if(am.getEmpAtten().size()!=0){
                    am.FilterEmployeeList();
                    isFiltered = true;
                    System.out.println("---- Eligible employees after filtering ----");
                    System.out.format("%10s %20s %20s %20s %20s", "Employee ID", "Name", "Designation", "Department", "Salary");
                    System.out.println();
                    System.out.println("-------------------------------------------------------------------------------------------------");
                    for (Employee emp : am.getEmpAtten().keySet()) {
                        System.out.println(emp.toString());
                    }
                }
                else{
                    System.out.println("Filter employees after providing attendance ..... choose menu choice 2 to add attendance\n");
                }
            }

            else if(choice == 7){
                int sortChoice = 0;
                if(employees.size()!=0){
                    while(true){
                        do{
                            invalidInput = false; 
                            try{
                                System.out.println("Sorting Menu\n1.Sort by ID Acsending\n2.Sort by ID Descending\n3.Sort by Name Ascending\n4.Sort by Name Descending\n5.Sort by Designation Ascending\n6.Sort by Designation Descending\n7.Sort by Department Ascending\n8.Sort by Department Descending\n9.Exit sorting\nEnter choice: ");
                                sortChoice = Integer.parseInt(scin.nextLine());
                            }
                            catch(NumberFormatException e){
                                System.out.println("Enter valid menu option (only numbers)\n");
                                invalidInput = true;
                            }
                            if((sortChoice<1 || sortChoice>9) && !invalidInput){
                                System.out.println("Enter valid menu option (1-9)");
                                invalidInput = true;
                            }
                        }while(invalidInput); // proper choice at this point

                        if(sortChoice == 9){
                            break;
                        }
                        else{
                            Quicksort(employees, 0, employees.size()-1, sortChoice);
                            for (Employee emp : employees) {
                                System.out.println(emp.toString());
                            }
                        }
                    }    
                }
                else{
                    System.out.println("No employees available to sort");
                }
            }

            else if(choice == 8){
                if(isFiltered){
                    SalCalculator salCalc = new SalCalculator();
                    salCalc.CalculateSalary(empHash);
                }
                else{
                    System.out.println("Filter employees before providing salary ..... 6 to filter employees");
                }
            }

            else if(choice == 9){
                System.out.println("Bye Bye");
                System.exit(0);
            }

            else{
                System.out.println("Enter valid menu option");
            }
        }
    }


    public static int Partition(ArrayList<Employee> employees, int start, int end, int sortChoice){
        String pivot = "";
        String val = "";
        int pIndex = start;

        for (int i = start; i < end; i++) {
            if(sortChoice == 1 || sortChoice == 2){
                val = ""+employees.get(i).getEmpID();
                pivot = ""+employees.get(end).getEmpID();
            }
            else if(sortChoice == 3 || sortChoice == 4){
                val = employees.get(i).getName();
                pivot = employees.get(end).getName();
            }
            else if(sortChoice == 5 || sortChoice == 6){
                val = employees.get(i).getDesg();
                pivot = employees.get(end).getDesg();
            }
            else if(sortChoice == 7 || sortChoice == 8){
                val = employees.get(i).getDept();
                pivot = employees.get(end).getDept();
            }

            if(sortChoice%2 == 1){
                if(val.compareTo(pivot) < 0){
                    Employee temp = employees.get(i);
                    employees.set(i, employees.get(pIndex));
                    employees.set(pIndex, temp);
                    pIndex++;
                }    
            }
            else{
                if(val.compareTo(pivot) > 0){
                    Employee temp = employees.get(i);
                    employees.set(i, employees.get(pIndex));
                    employees.set(pIndex, temp);
                    pIndex++;
                }
            }
        }

        Employee temp = employees.get(end);
        employees.set(end, employees.get(pIndex));
        employees.set(pIndex, temp);

        return pIndex;
    }

    public static void Quicksort(ArrayList<Employee> employees, int start, int end, int sortChoice){
        if(start<end){
            int p = Partition(employees, start, end, sortChoice);
            Quicksort(employees, start, (p-1), sortChoice);
            Quicksort(employees, (p+1), end, sortChoice);
        }
    }
}