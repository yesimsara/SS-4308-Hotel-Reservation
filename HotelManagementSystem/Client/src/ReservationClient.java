import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.util.*;
public class ReservationClient {
    public static void main(String args[]) throws RemoteException {

        //Our variables
        String id;
        String customer_name;
        int phone_no;
        String email;
        String room_type;
        String reservation_date;
        double payment_amount;
        String reservation_status;
        boolean loop = true;
        boolean loop1 = true;
        String reservation_id;
    
        // One scanner for integer and one scanner for string
        Scanner sc = new Scanner(System.in);
        Scanner sc2 = new Scanner(System.in);
        try{ 
            Registry reg = LocateRegistry.getRegistry("127.0.0.1",2345);
            ReservationInterface res = (ReservationInterface) reg.lookup("server");

            //Our fake database
            HashMap<String,List<Object>> cust = res.dummyDatabase();
            HashMap<String,List<Object>> pending = res.dummyDatabase();
            List<Object> details = new ArrayList<>();
            
            System.out.println("Access Type");
            System.out.println("1. Admin \n2. Customer");
            int admin = sc.nextInt();
            if (admin == 2){
                while (loop == true){
                // The menu
                System.out.println("Menu:");
                System.out.println("1. Add Reservation");
                System.out.println("2. Check Reservation Details");
                System.out.println("3. Cancel Reservation");
                System.out.println("4. Check Availability");
                System.out.println("5. Make Payment");
                System.out.println("Please choose an operation:");
                int option = sc.nextInt();
                switch (option){
                    case 1: // Add Reservation according to user input
                            System.out.println("You have selected 'Add Reservation'");
                            System.out.println("Enter name");
                            customer_name = sc2.nextLine();
                            System.out.println("Enter your phone number");
                            phone_no = sc.nextInt();
                            System.out.println("Enter your email");
                            email = sc2.nextLine();
                            System.out.println("Please enter your desired room type:");
                            System.out.println("1. Superior \n2. Deluxe \n3. Executive");
                            room_type = sc2.nextLine();
                            System.out.println("Please enter your desired date");
                            reservation_date = sc2.nextLine();
                            id = customer_name + " " + phone_no;
                            reservation_status = "Reservation Submitted";
                            details = res.addReservation(id,customer_name,phone_no,email,room_type, reservation_date,reservation_status,0.00);
                            // If we loop again into menu and check for detail, we can get check the added reservation 
                            // However, if program is quitted, changes will not be saved
                            cust.put(id, details);
                            System.out.println("Reservation request with " + id + " has been submitted successfully");
                            break;
                    case 2: // Get customer details from supposingly a fake database
                            System.out.println("You have selected 'Get Reservation Details'");
                            System.out.print("Enter your Reservation ID: ");
                            reservation_id = sc2.nextLine();
                            details = res.checkReservation(reservation_id,cust);
                            customer_name = details.get(0).toString();
                            phone_no = (int)details.get(1);
                            email = details.get(2).toString();
                            reservation_status = details.get(5).toString();
                            System.out.println("Reservation Details:\nName: "+customer_name+"\nPhone Number: "+phone_no+"\nEmail: " + email
                            + "\nReservation Status: "+reservation_status);
                            break;
                    case 3: // Cancell reservation, the reservation status is changed to 'cancelled'
                            // Display that it has been cancelled and removed
                            System.out.println("You have selected 'Cancel Reservation'");
                            System.out.print("Enter your Reservation ID: ");
                            reservation_id = sc2.nextLine();
                            details = res.removeReservation(reservation_id,cust);
                            System.out.println("Current status: " + details.get(5));
                            System.out.println("Removing reservation...");
                            cust.remove(reservation_id);
                            System.out.println("Cancellation of Reservation has been Successful!");
                            break;
                    case 4: // Check availability 
                            System.out.println("You have selected 'Check Availability'");
                            System.out.println("Please choose the room you are interested in:");
                            System.out.println("1. Superior");
                            System.out.println("2. Deluxe");
                            System.out.println("3. Executive");
                            System.out.print("Your chosen room type (1-3): ");
                            int selection = sc.nextInt();
                            System.out.println("The room are available on the dates below:");
                            if (selection == 1){
                                List<String> available_rooms = res.checkAvailability("Superior");
                                for(String room_no:available_rooms) {
                                    System.out.println(room_no);
                                }
                            }
                            else if(selection == 2){
                                List<String> available_rooms = res.checkAvailability("Deluxe");
                                for(String room_no:available_rooms) {
                                    System.out.println(room_no);
                                }
                            }
                            else if (selection == 3){
                                List<String> available_rooms = res.checkAvailability("Executive");
                                for(String room_no:available_rooms) {
                                    System.out.println(room_no);
                                }
                            }
                            else{
                                System.out.println("Please choose from available options only");
                            }
                            break;
                    case 5: //Make Payment
                            System.out.println("You have chosen 'Make Payment'");     
                    }
      
                    // To loop back, select 2
                    System.out.println("Do you want to go back to main menu?");
                    System.out.println("1. Yes");
                    System.out.println("2. No");
                    int choice = sc.nextInt();
                    if (choice == 1){
                        loop = true;
                    }
                    else if (choice == 2){
                        loop = false;
                    }             
            }
            }
      
            else if (admin == 1){
                while (loop1 == true){
                    System.out.println("You may now assign and confirm a reservation");
                    System.out.println("Below are list of reservations:");
                    Set<String> keys = pending.keySet();
                    //print all the keys 
                    for (String key : keys) {
                    System.out.println(key);
                    }
                    System.out.println("Select reservation id to confirm");
                    String id_to_confirm = sc2.nextLine();
                    details = res.checkReservation(id_to_confirm, pending);
                    room_type = details.get(3).toString();
                    reservation_date = details.get(4).toString();
                    String rs = res.matchAvailability(room_type, reservation_date).toString();
                    System.out.println("System confirmation: "+ rs);
                    System.out.println("Accept reservation?");
                    System.out.println("1. Yes \n2. No");
                    int selection = sc.nextInt();
                    if (selection == 1){
                        payment_amount = res.invoice(room_type);
                        details.set(6,payment_amount);
                        details.set(5,rs);
                        customer_name = details.get(0).toString();
                        phone_no = (int)details.get(1);
                        email = details.get(2).toString();
                        room_type = details.get(3).toString();
                        reservation_date = details.get(4).toString();
                        reservation_status = details.get(5).toString();
                        payment_amount = (double) details.get(6);

                        System.out.println("Updated Details:");
                        System.out.println("Reservation ID: " + id_to_confirm);
                        System.out.println("Customer Name: " + customer_name);
                        System.out.println("Room Type: " + room_type);
                        System.out.println("Reservation Date: " + reservation_date);
                        System.out.println("The Reservation is " + reservation_status);
                        System.out.println("Total Charge: " + payment_amount);
                        System.out.println("Emailing invoice with above details to " + email + "....."); 
                        pending.remove(id_to_confirm);
                        System.out.println("Action Successful!");
                    }
                    else if (selection ==2){
                        details.set(5,"Rejected");
                        customer_name = details.get(0).toString();
                        phone_no = (int)details.get(1);
                        email = details.get(2).toString();
                        room_type = details.get(3).toString();
                        reservation_date = details.get(4).toString();
                        reservation_status = details.get(5).toString();
                        payment_amount = (double) details.get(6);

                        System.out.println("Updated Details:");
                        System.out.println("Reservation ID: " + id_to_confirm);
                        System.out.println("Customer Name: " + customer_name);
                        System.out.println("Room Type: " + room_type);
                        System.out.println("Reservation Date: " + reservation_date);
                        System.out.println("The Reservation is " + reservation_status);
                        System.out.println("We will remove your reservation");
                        pending.remove(id_to_confirm);
                        cust.remove(id_to_confirm);
                        System.out.println("Action Successful!");
                    }

                    System.out.println("Do you want to resolve more reservation?");
                    System.out.println("1. Yes");
                    System.out.println("2. No");
                    int choice = sc.nextInt();
                    if (choice == 1){
                        loop1 = true;
                    }
                    else if (choice == 2){
                        loop1 = false;
                    } 
                }  
        }
        }
    catch(Exception e) {
        System.out.println(e.toString());
    }  
        sc2.close();
        sc.close();
  }
}
