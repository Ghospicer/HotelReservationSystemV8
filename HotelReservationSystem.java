import java.util.*;

public class HotelReservationSystem {

    public static void main(String[] args) throws InputMismatchException, RoomTypeException {
    	List<Services> service = new ArrayList<Services>();
    	List<Reservation> reservation = new ArrayList<Reservation>();
    	List<Employees> employees = new ArrayList<Employees>();
        List<Bills> bills = new ArrayList<Bills>();
    	String resCity;
    	String roomType = null;
    	String[] roomList = {"Single", "Double", "Family", "FamilywithView", "Club", "Suite"};
        int id = 0;
        int choice = 0;
        int serviceChoice;
        int reservationStart = 0;
        int reservationEnd = 0;
        int[] choices = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        Scanner sc = new Scanner(System.in);

        while(true) {       		
        	
        	for(MenuOptions rm: EnumSet.range(MenuOptions.FIRST, MenuOptions.EXIT)) {
        		System.out.printf("%-100s\n", rm.menuOptions());
        		System.out.printf("\n");
        	}        	
        	
        	try {
        		choice = sc.nextInt();
        		int a = choices[choice-1];
        		a=a+1;
                System.out.printf("\n");
        	}catch(ArrayIndexOutOfBoundsException e) {
        		System.out.println("You entered an invalid menu option. Enter again.\n");
        		sc.nextLine();
        		for(MenuOptions rm: EnumSet.range(MenuOptions.FIRST, MenuOptions.EXIT)) {
            		System.out.printf("%-100s\n", rm.menuOptions());
            		System.out.printf("\n");
            	}
        		choice = sc.nextInt();
                System.out.printf("\n");
        		
        	}

            if (choice==1) {
            	
            	//Create new Reservation with Room Type
            	System.out.println("ROOM INFOS: \n");
            	for(MenuOptions rm: EnumSet.range(MenuOptions.SINGLE, MenuOptions.SUITE)) {
            		System.out.printf("%-100s\n", rm.menuOptions());
            	} 
            	System.out.printf("\n");
            	System.out.printf("%s\n", "Hotel Name: ");
            	String hotelName = sc.next() + " " + sc.next();
            	try {
            		System.out.printf("%s\n", "Room Type: ");
                    roomType = sc.next();
                    for(int r=0;r<6;r++) {
                    	if(roomType.equalsIgnoreCase(roomList[r])) {
                    		
                    	}
                    	else {
                    		throw new RoomTypeException("Room Type is not valid!\n");
                    	}
                    }
            	}catch(RoomTypeException e) {
            		sc.nextLine();
            		System.out.printf("%s\n", "Room Type: ");
                    roomType = sc.next();
            		
            	}
                System.out.printf("%s\n", "Reservation Month: ");
                String reservationMonth = sc.next();
                try {
                	System.out.printf("%s\n", "Reservation Start: ");
                	reservationStart = sc.nextInt();
                }catch(InputMismatchException e){
                	System.out.println("Reservation Start must be a numeric value!\n");
                	sc.nextLine();
                	System.out.printf("%s\n", "Reservation Start: ");
                	reservationStart = sc.nextInt();
                }
                try {
                	System.out.printf("%s\n", "Reservation End: ");
                    reservationEnd = sc.nextInt();
                }catch(InputMismatchException e) {
                	System.out.println("Reservation End must be a numeric value!");
                	sc.nextLine();
                	System.out.printf("%s\n", "Reservation End: ");
                    reservationEnd = sc.nextInt();
                }
                id++;
                Room rm = new Room(roomType);
                Reservation reserv = new Reservation(reservationEnd, reservationStart, reservationMonth, hotelName, rm, id);
                reservation.add(reserv);
                service.add(reserv);
                System.out.printf("Reservation ID: %s created!\n", id);
                System.out.printf("\n");
                
            }
            else if (choice==2) {
            	
            	//Display all Reservations
            	Iterator<Services> svitr = service.iterator();
            	while(svitr.hasNext()) {
            		Services svr = svitr.next();
                    if (svr instanceof Reservation) {
                        Reservation reservationch2 = (Reservation) svr;
                        reservationch2.displayInfo();
                    }
            	}
            }
            
            else if (choice==3) {
            	
            	//List the reservations for a specific city
            	System.out.println("Type a city name for a reservation search: \n");
            	resCity=sc.next();
            	Iterator<Services> cLitr = service.iterator();
            	while (cLitr.hasNext()) {
            		Reservation reservationch3 = (Reservation)cLitr.next();
            		if(reservationch3.getHotelName().contains(resCity)) { 
            			System.out.printf("%s \n", reservationch3.getHotelName());
            			
            		}
            	}
            	System.out.printf("\n");
            }
            else if (choice==4) {
                
            	//Add extra services to a reservation
            	System.out.println("Please select one of the extra services from below: \n");
            	System.out.println("1. Laundry Service");
            	System.out.println("2. Spa Service");
            	serviceChoice=sc.nextInt();
            	
            	if (serviceChoice==1) {
            		System.out.println("Type the reservation ID to credit this service: \n");
            		int CustomerID=sc.nextInt();
            		try {
            			System.out.println("How many pieces of clothing?");
                		int clothingPieces=sc.nextInt();
                		Laundry lndry = new Laundry(CustomerID, clothingPieces);
                		service.add(lndry);
            		}catch(InputMismatchException e) {
            			System.out.println("Clothing count must be a numeric value!\n");
            			sc.nextLine();
            			System.out.println("How many pieces of clothing?");
                		int clothingPieces=sc.nextInt();
                		Laundry lndry = new Laundry(CustomerID, clothingPieces);
                		service.add(lndry);
            		}
            		
            		
            	}
            	else if (serviceChoice==2) {
            		System.out.println("Type the reservation ID to credit this service: \n");
            		int CustomerID=sc.nextInt();
            		try {
            			System.out.println("How many days?");
                		int days=sc.nextInt();
                		Spa spa = new Spa(CustomerID, days);
                		service.add(spa);
            		}catch(InputMismatchException e) {
            			System.out.println("Day count must be a numeric value!\n");
            			sc.nextLine();
            			System.out.println("How many days?");
                		int days=sc.nextInt();
                		Spa spa = new Spa(CustomerID, days);
                		service.add(spa);
            		}
            		
            	}
            	
            }
            else if (choice==5) {
            	
            	//Calculate total cost for each service
            	Iterator<Services> ch5 = service.iterator();
            	while(ch5.hasNext()) {
            		Services svc = ch5.next();
            		if(svc instanceof Reservation) {
            			Reservation resev =(Reservation)svc;
            			int reservCost=resev.checker();
            			System.out.printf("The cost for the Room booking service of reservation ID %d: %d\n", resev.getCustomerID(), reservCost); 
            		}
            		else if (svc instanceof Spa) {
            			Spa spa =(Spa)svc;
            			double spaCost=spa.calculateService();
            			System.out.printf("The cost for the Room booking service of reservation ID %d: %f\n", spa.getCustomerID(), spaCost);
            		}
            		else  {
            			Laundry lndry = (Laundry)svc;
            			double laundryCost=lndry.calculateService();
            			System.out.printf("The cost for the Room booking service of reservation ID %d: %f\n", lndry.getCustomerID(), laundryCost);
            		}
            	}
            	System.out.println("\n");
            }
            else if (choice==6) {
            	
            	//Display the total cost of every customer
            	double total = 0;
            	for(int a=1;a<=id;a++) {
            		Iterator<Services> ch6 = service.iterator();
            		while(ch6.hasNext()) {
            			Services c6srv = ch6.next();
            				if(c6srv.getCustomerID()==a) {
            					if(c6srv instanceof Reservation) {
            						Reservation resev = (Reservation)c6srv;
            						total =  resev.checker();
            					}
            					else if(c6srv instanceof Spa) {
            						Spa spa = (Spa)c6srv;
                					total+=spa.calculateService();
            					}
            					else if(c6srv instanceof Laundry) {
            						Laundry lndry = (Laundry)c6srv;
                					total += lndry.calculateService();
            					}
            					System.out.printf("The total cost of all services of the reservation with ID: %d is %f\n", a, total);
            			}
            		}
            	}
            }
            else if (choice==7) {
            	
            	//Add an Employee
            	double monthlypayment=0;
            	System.out.printf("Name: \n");
            	String empName=sc.next();
            	System.out.printf("Surname: \n");
            	String empSname=sc.next();
            	System.out.printf("ID: \n");
            	int empID=sc.nextInt();
            	try {
            		System.out.printf("Monthly Payment: \n");
                	monthlypayment=sc.nextDouble();
            	}catch(InputMismatchException e) {
            		System.out.println("Monthly Payment must be a numeric value!\n");
            		sc.nextLine();
            		System.out.printf("Monthly Payment: \n");
                	monthlypayment=sc.nextDouble();
            	}
            	Employees emp = new Employees(empName, empSname, empID, monthlypayment);
            	employees.add(emp);
            	
            }
            else if (choice==8) {
            	
            	//Add a Bill
            	System.out.printf("Type: \n");
            	String bllType=sc.next();
            	double bllAmount=0;
            	try {
            		System.out.printf("Amount: \n");
                	bllAmount=sc.nextDouble();
            	}catch(InputMismatchException e) {
            		System.out.println("Bill Amount must be a numeric value!\n");
            		sc.nextLine();
            		System.out.printf("Amount: \n");
                	bllAmount=sc.nextDouble();
            	}
            	System.out.printf("Month: \n");
            	String bllMonth=sc.next();
            	Bills bill = new Bills(bllType, bllAmount, bllMonth);
            	bills.add(bill);
            	
            }
            else if (choice==9) {
            	
            	//Get Monthly Balance
            	System.out.printf("Enter Month: \n");
            	String monthBalance=sc.next();
            	double income = 0;
            	double tIncome = 0; 
            	double empExpense = 0;
            	double billExpense = 0;
            	double tExpense = 0;
            	double balance = 0;
            	for(int a=1;a<=id;a++) {
            		Iterator<Services> ch9 = service.iterator();
            		while(ch9.hasNext()) {
            			Services ch9srv = ch9.next();
            			if(ch9srv.getCustomerID()==a) {
            				if(ch9srv instanceof Reservation) {
            					Reservation resev = (Reservation)ch9srv;
            					if (resev.getReservationMonth().equals(monthBalance)){
            						income =  resev.checker();
            						tIncome = income;
            						System.out.printf("For reservation ID: %s, Service type: Room booking, Service Cost: %f\n", a, income);
            					}
            				}
            				else if(ch9srv instanceof Spa) {            					
            					Spa spa = (Spa)ch9srv;
            					if(spa.getCustomerID()==a) {
            						income =spa.calculateService();
            						tIncome += income;
            						System.out.printf("For reservation ID: %s, Service type: Spa, Service Cost: %f\n", a, income);
            					}
                			}
            				else if(ch9srv instanceof Laundry) {
            					Laundry lndry = (Laundry)ch9srv;
            					if(lndry.getCustomerID()==a) {
            						income = lndry.calculateService();
            						tIncome += income;
            						System.out.printf("For reservation ID: %s, Service type: Laundry, Service Cost: %f\n", a, income);
            					}
            				}           				
            			}
            		}
            		Iterator<Bills> ch9bill = bills.iterator();
            		while(ch9bill.hasNext()) {
            			Bills ch9bll = ch9bill.next();
            			Bills bill = (Bills)ch9bll;
            			if(bill.month.equals(monthBalance)) {
            				billExpense = bill.getCost();
            				tExpense += billExpense;
            			}
            		}
            		Iterator<Employees> ch9emps = employees.iterator();
            		while(ch9emps.hasNext()) {
            			Employees ch9emp = ch9emps.next();
            			Employees emp = (Employees)ch9emp;
            			empExpense = emp.getCost();
            			tExpense += empExpense;
            		}
            	
            	System.out.printf("Total monthly income: %f\n", tIncome);
				System.out.printf("Total monthly bills due: %f\n", billExpense);		
				System.out.printf("Total monthly employee cost: %f\n", empExpense);
				balance = (income - tExpense);
				System.out.printf("End of month balance: %f\n", balance);
				System.out.printf("\n");
				balance=0;
				tExpense=0;
				billExpense=0;
				empExpense=0;
				income=0;
				tIncome=0;
				
            	}
            }
            else if (choice==10) {
            	
            	//List all Services sorted based on cost
            	Collections.sort(service, new CostComparator());
            	for(Services s: service) {
            		System.out.printf("Customer ID: %d, Service Type: %s, Cost: %f\n", s.getCustomerID(), s.getServiceType(), s.getCost());
            	}
            	System.out.printf("\n");
            	
            }
            else if (choice==11) {
            	
            	//List all Reservations sorted based on hotel names
            	Collections.sort(reservation);
            	Collections.reverse(reservation);
            	for(Reservation r: reservation) {
            		System.out.printf("%s\n", Services.displayServiceInfo(r));
            	}	
            	System.out.printf("\n");
            	
            	
            }
            else if (choice==12) {
            	
            	//Exit
            	System.out.println("Exiting, Goodbye!");
                break;
            }
            else {
                System.out.println("Wrong choice!");
            }
        
        
        }

        sc.close();
    }

}
