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
            String menuOptions = "---- Menu ----\n1.Add employees\n2.Display Employees list\n3.Add all employee attendance\n4.Update Attendance for an Employee ID\n5.Show Eligible Employees\n6.Filter Employees\n7.sort\n8.Provide salary\n9.Exit\nEnter choice: ";
            choice = ValidMenuOption(menuOptions, 1, 9);
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
                mData.displayEmployees(employees);    
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
                    System.out.println("Eligible employees after filtering\n");
                    ArrayList<Employee> temp = new ArrayList<Employee>(am.getEmpAtten().keySet());
                    mData.displayEmployees(temp);
                }
                else{
                    System.out.println("Filter employees after providing attendance ..... choose menu choice 3 to add attendance\n");
                }
            }

            else if(choice == 7){
                int sortChoice = 0;
                if(employees.size()!=0){
                    while(true){
                        menuOptions = "Sorting Menu\n1.Sort by Name Ascending\n2.Sort by Name Descending\n3.Sort by Designation Ascending\n4.Sort by Designation Descending\n5.Sort by Department Ascending\n6.Sort by Department Descending\n7.Exit sorting\nEnter choice: ";
                        sortChoice = ValidMenuOption(menuOptions, 1, 7);
                        if(sortChoice == 7){
                            break;
                        }
                        else{
                            if(sortChoice == 1){
                                Collections.sort(employees, new NameSorting());
                            }
                            else if(sortChoice == 2){
                                Collections.sort(employees, new NameSorting().reversed());
                            }
                            else if(sortChoice == 3){
                                Collections.sort(employees, new DesignationSorting());
                            }
                            else if(sortChoice == 4){
                                Collections.sort(employees, new DesignationSorting().reversed());
                            }
                            else if(sortChoice == 5){
                                Collections.sort(employees, new DepartmentSorting());
                            }
                            else if(sortChoice == 6){
                                Collections.sort(employees, new DepartmentSorting().reversed());
                            }

                            mData.displayEmployees(employees);
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