import java.util.*;

public class TestEmployee{
    public static void main(String[] args) {
        ArrayList<Employee> employees = new ArrayList<Employee>();
        int choice = 1, idToFind = 0;
        boolean isFiltered = false;
        HashMap<Employee,Integer> empHash = new HashMap<Employee, Integer>();
        HashMap<Integer, Employee> employeeIdMapping = new HashMap<Integer, Employee>();
        ArrayList<Employee> employeesToAddAttendance = new ArrayList<Employee>();
        MasterData mData = new MasterData(employees);
        AttendanceMaster am = new AttendanceMaster(empHash);
        Scanner scin = new Scanner(System.in);
        
        while(true){
            Employee employee = new Employee();
            String menuOptions = "\n---- Menu ----\n1.Add employees\n2.Display Employees list\n3.Add attendance to newly inserted employees\n4.Update Attendance for an Employee ID\n5.Filter and show Eligible employees\n6.sort\n7.Provide salary\n8.Exit\nEnter choice: ";
            choice = ValidMenuOption(menuOptions, 1, 9);
            if(choice == 1){      // when adding employee
                employee.setName(""); //to validate name
                employee.setDesg(""); //to validate department
                employee.setDept(""); //to validate designation
                employee.setSal(0);  //to set the correct salary
                employeeIdMapping.put(employee.getEmpID(), employee);
                employees.add(employee);
                employeesToAddAttendance.add(employee);
                isFiltered = false;
                System.out.println();
            }
            else if(choice == 2){
                mData.displayEmployees();    
            }
            
            else if(choice == 3){
                if(employeesToAddAttendance.size()>0){
                    for (Employee emp : employeesToAddAttendance){
                        empHash.put(emp, GetAttendanceAndID(emp.getEmpID(), 1, 30));  //valid attendance inserted to hashmap
                    }
                    employeesToAddAttendance.clear();
                }
                else{
                    System.out.println("No new employees to add attendance to");
                }
            }

            else if(choice == 4){
                if(employees.size() == 0){
                    System.out.println("No employees available to update attendance to");
                }
                else{
                    idToFind = GetAttendanceAndID(0, 1001, employees.size()+1000);
                    employeesToAddAttendance.remove(employeeIdMapping.get(idToFind));
                    empHash.put(employeeIdMapping.get(idToFind), GetAttendanceAndID(idToFind, 1, 30));
                    isFiltered = false;
                }
            }

            else if(choice == 5){
                if(employees.size()!=0){
                    if(employeesToAddAttendance.size()==0){
                        am.FilterEmployeeList();
                        isFiltered = true;
                        System.out.println("Filtered Employees list\n");
                        am.showEligibleList();  
                    }
                    else{
                        System.out.println("Provide attendance for all available employees ..... choose menu choice 3 to add attendance\n");
                    }  
                }
                else{
                    System.out.println("Add employees before filtering ... choice 1 to add");
                }
            }

            else if(choice == 6){
                int sortChoice = 0;
                if(employees.size()!=0){
                    while(true){
                        menuOptions = "\n---- Sorting Menu ----\n1.Sort by Name Ascending\n2.Sort by Name Descending\n3.Sort by Designation Ascending\n4.Sort by Designation Descending\n5.Sort by Department Ascending\n6.Sort by Department Descending\n7.Exit sorting\nEnter choice: ";
                        sortChoice = ValidMenuOption(menuOptions, 1, 7);
                        if(sortChoice == 7){
                            break;
                        }
                        else{
                            if(sortChoice == 1){
                                System.out.println("\nSort by Name (Ascending)");
                                Collections.sort(employees, new NameSorting());
                            }
                            else if(sortChoice == 2){
                                System.out.println("\nSort by Name (Descending)");
                                Collections.sort(employees, new NameSorting().reversed());
                            }
                            else if(sortChoice == 3){
                                System.out.println("\nSort by Designation (Ascending)");
                                Collections.sort(employees, new DesignationSorting());
                            }
                            else if(sortChoice == 4){
                                System.out.println("\nSort by Designation (Descending)");
                                Collections.sort(employees, new DesignationSorting().reversed());
                            }
                            else if(sortChoice == 5){
                                System.out.println("\nSort by Department (Ascending)");
                                Collections.sort(employees, new DepartmentSorting());
                            }
                            else if(sortChoice == 6){
                                System.out.println("\nSort by Department (Descending)");
                                Collections.sort(employees, new DepartmentSorting().reversed());
                            }
                            mData.displayEmployees();
                        }
                    }    
                }
                else{
                    System.out.println("No employees available to sort");
                }
            }

            else if(choice == 7){
                if(employees.size()!=0){
                    if(isFiltered){
                        SalCalculator salCalc = new SalCalculator();
                        salCalc.CalculateSalary(empHash);
                    }
                    else{
                        System.out.println("Filter employees before providing salary ..... 5 to filter employees");
                    }
                }
                else{
                    System.out.println("Enter atleast 1 employee to add salary\n");
                }
            }

            else if(choice == 8){
                System.out.println("Bye Bye");
                scin.close();
                System.exit(0);
            }
        }
    }

    public static int ValidMenuOption(String menu, int start, int end){
        int val = 0;
        boolean invalidInput;
        Scanner scin = new Scanner(System.in);
        do{
            invalidInput = false; 
            try{
                System.out.print(menu);
                val = Integer.parseInt(scin.nextLine());
            }
            catch(NumberFormatException e){
                System.out.println("Enter valid menu option (only numbers)\n");
                invalidInput = true;
            }
            if((val<start || val>end) && !invalidInput){
                System.out.println("Enter valid menu option ("+start+"-"+end+")");
                invalidInput = true;
            }
        }while(invalidInput); // proper choice at this point
        return val;
    }
    
    public static int GetAttendanceAndID(int idToFind, int start, int end){
        int value = 0;
        boolean invalidInput;
        Scanner scin1 = new Scanner(System.in);
        do{
            invalidInput = false;
            try{
                if(end == 30){
                    System.out.print("Enter attendance for the employee " + idToFind +":  ");
                }
                else{
                    System.out.print("Enter employee id to update attendance: ");
                }          
                value = Integer.parseInt(scin1.nextLine());
            }
            catch(NumberFormatException e){
                System.out.println("Enter numerical value only\n");
                invalidInput = true;
            }
            if((value<start || value > end) && !invalidInput){
                if (end == 30){
                    System.out.println("Enter value between " + start + " and " + end +" \n");
                }
                else{
                    System.out.println("Enter an available ID to update");
                }
                invalidInput = true;
            }
        }while(invalidInput);                
        return value;
    }
}

// Overriding Comparators to sort various features
class NameSorting implements Comparator<Employee>{
    @Override public int compare(Employee emp1, Employee emp2){
        return emp1.getName().compareTo(emp2.getName());
    }
}
class DesignationSorting implements Comparator<Employee>{
    @Override public int compare(Employee emp1, Employee emp2){
        return emp1.getDesg().compareTo(emp2.getDesg());
    }
}

class DepartmentSorting implements Comparator<Employee>{
    @Override public int compare(Employee emp1, Employee emp2){
        return emp1.getDept().compareTo(emp2.getDept());
    }
}
