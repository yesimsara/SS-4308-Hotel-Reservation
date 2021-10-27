import java.util.*;
public class Reservation {

    String id;
    String customer_name;
    int phone_number;
    String email;
    int room_number;
    String room_type;
    String reservation_date;
    String reservation_status;
    double payment_amount;

    // Empty constructor
    public Reservation() {
    }

    // Add reservation
    public void addReservation(String id, String customer_name, int phone_number, String email,String room_type, String reservation_date,String reservation_status, double payment_amount) {
        this.id = id;
        this.customer_name = customer_name;
        this.phone_number = phone_number;
        this.email = email;
        this.room_type = room_type;
        this.reservation_date = reservation_date;
        this.reservation_status =reservation_status;
        this.payment_amount = payment_amount;
        
    }

    // Remove reservation
    // Change reservation status to cancelled
    public void removeReservation(Reservation r){
        r.reservation_status = "cancelled";
    }

    // Since we are not making connection to database
    // Hash table of available dates for certain room have been created
    // When this method is called, room type is supplied to it
    // This function return the available dates for the room type
    public List<String> checkAvailability(String room_type){
        HashMap <String, List<String>> available_room = new HashMap <> ();
        List<String> superior = new ArrayList<String>();
        superior.add("21/10/2021");
        superior.add("31/10/2021");
        superior.add("1/11/2021");
        superior.add("13/11/2021");
        superior.add("31/12/2021");
        List<String> deluxe = new ArrayList<String>();
        deluxe.add("31/10/2021");
        deluxe.add("1/11/2021");
        deluxe.add("7/12/2021");
        List<String> executive = new ArrayList<String>();
        executive.add("31/10/2021");
        executive.add("1/11/2021");
        available_room.put("Superior",superior);
        available_room.put("Deluxe",superior);
        available_room.put("Executive",superior);
        
        return available_room.get(room_type);
    }

    // This function simply invoice the amount to be paid shall the reservation is confirmed
    public double invoice(String room_type){
        HashMap <String, Double> pricing = new HashMap <> ();
        pricing.put("Superior", 122.99);
        pricing.put("Deluxe",150.00);
        pricing.put("Executive",170.00);

        return pricing.get(room_type);   
    }

    public void makePayment(){

    }

    // This function find out if the date selected by customer is available or not
    // If the date is available and the room type is available, the reservation status is changed to 'Confirmed'
    // Else reservation status is changed to 'rejected'
    // This function is only available in admin mode
    // I might change this later to be implemented within customer mode instead for better execution
    public String matchAvailability(String room_type, String reservation_date){
        List <String> available_dates = checkAvailability(room_type);
        if (available_dates.contains(reservation_date)) {
            reservation_status = "Confirmed";
        } else {
            reservation_status = "Rejected";
        }

        return reservation_status;

    }



}

class HotelManagementSystem{
    // Main Method
    public static void main(String[] args) {

      //Our variables
      String id;
      String customer_name;
      int phone_no;
      String email;
      String room_type;
      String reservation_date;
      //int room_number;
      double payment_amount;
      String reservation_status;
      boolean loop = true;
      boolean loop1 = true;
      String reservation_id;
    
      // One scanner for integer and one scanner for string
      Scanner sc = new Scanner(System.in);
      Scanner sc2 = new Scanner(System.in);

      // Since we are not connecting to database, we just create hash table here
      // Using hash table make it easy to access object using its id
      HashMap <String,Reservation> reservation = new HashMap<>();
      Reservation r2 = new Reservation();
      r2.addReservation("sara 123", "sara", 8123123, "sara123@mail.com", "Executive", "31/10/2021","Reservation Submitted",0.00);
      reservation.put(r2.id,r2);
      Reservation r3 = new Reservation();
      r3.addReservation("bong 123", "bong", 8321123, "bong123@mail.com", "Executive", "31/10/2021","Reservation Submitted",0.00);
      reservation.put(r3.id,r3);
      Reservation r4 = new Reservation();
      r4.addReservation("test 123", "test", 123, "123@mail.com", "Deluxe", "31/10/2021","Test",0.00);
      reservation.put(r4.id,r4);
      System.out.print(reservation.toString());
    
      // Prompt to get into admin mode or customer mode
      // I might create credential if I choose to do this
      System.out.println("Are you an admin?");
      System.out.println("1. Yes \n2. No");
      int admin = sc.nextInt();

      // Below will be executed if in customer mode
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
            
            // Create instance of reservation
            Reservation r = new Reservation();

            switch (option){
                case 1: 
                        // Add Reservation according to user input
                        System.out.println("You have selected 'Add Reservation'");
                        System.out.println("Enter name");
                        customer_name = sc2.nextLine();
                        System.out.println("Enter your phone number");
                        phone_no = sc.nextInt();
                        System.out.println("Enter your email");
                        System.out.println("Please enter your desired room type:");
                        System.out.println("1. Superior \n 2. Deluxe \n 3. Executive");
                        room_type = sc2.nextLine();
                        System.out.println("Please enter your desired date");
                        reservation_date = sc2.nextLine();
                        email = sc2.nextLine();
                        id = customer_name + " " + phone_no;
                        reservation_status = "Reservation Submitted";
                        r.addReservation(id,customer_name,phone_no,email,room_type, reservation_date,reservation_status,0.00);
                        // If we loop again into menu and check for detail, we can get check the added reservation 
                        // However, if program is quitted, changes will not be saved
                        reservation.put(r.id, r);
                        System.out.print("Reservation request with " + r.id + " has been submitted");
                        break;
                case 2: // Get customer details from supposingly a fake database
                        System.out.println("You have selected 'Get Reservation Details'");
                        System.out.print("Enter your Reservation ID: ");
                        reservation_id = sc2.nextLine();
                        r = reservation.get(reservation_id);
                        if (r != null){
                            System.out.println("Details are as below:");
                            System.out.println("Customer Name: " + r.customer_name);
                            System.out.println("Customer Reservation Status: " + r.reservation_status);
                        }
                        else{
                            System.out.println("No reservation with id " + reservation_id);
                        }
                        break;
                case 3: // Cancell reservation, seems like it is complex to remove an instance, therefore,
                        // the reservation status is changed to 'cancelled instead'
                        System.out.println("You have selected 'Cancel Reservation'");
                        System.out.print("Enter your Reservation ID: ");
                        reservation_id = sc2.nextLine();
                        r = reservation.get(reservation_id);
                        r.removeReservation(r);
                        r = null;
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
                            List<String> available_rooms = r.checkAvailability("Superior");
                            for(String room_no:available_rooms) {
                                System.out.println(room_no);
                            }
                        }
                        else if(selection == 2){
                            List<String> available_rooms = r.checkAvailability("Deluxe");
                            for(String room_no:available_rooms) {
                                System.out.println(room_no);
                            }
                        }
                        else{
                            List<String> available_rooms = r.checkAvailability("Executive");
                            for(String room_no:available_rooms) {
                                System.out.println(room_no);
                            }
                        }
                        break;
                case 5: //Make Payment
                        System.out.println("You have chosen 'Make Payment'");     
                }

                // To loop back, select 2
                System.out.println("Do you want to exit?");
                System.out.println("1. Yes");
                System.out.println("2. No");
                int choice = sc.nextInt();
                if (choice == 1){
                    loop = false;
                }
                else if (choice == 2){
                    loop = true;
                }             
        }
        }

        else if (admin == 1){
            while (loop1 == true){
                System.out.println("You may now assign and confirm a reservation");
                System.out.println("Below are list of reservations:");
                Set<String> keys = reservation.keySet();
                //print all the keys 
                for (String key : keys) {
                System.out.println(key);
                }
                System.out.println("Select reservation id to confirm");
                String id_to_confirm = sc2.nextLine();
                Reservation res = reservation.get(id_to_confirm);
                System.out.println(res.toString());
                reservation_status = res.matchAvailability(res.room_type, res.reservation_date);
                res.reservation_status = reservation_status;
                if (reservation_status == "Confirmed"){
                    payment_amount = res.invoice(res.room_type);
                    res.payment_amount = payment_amount;
                }
                else{
                    System.out.println("No available room");
                    res.removeReservation(res);
                }    
                System.out.println("Updated Information:");
                System.out.println("Reservation ID: " + res.id);
                System.out.println("Customer Name: " + res.customer_name);
                System.out.println("Room Type: " + res.room_type);
                System.out.println("Reservation Date: " + res.reservation_date);
                System.out.println("The Reservation is " + reservation_status);
                System.out.println("Total Charge: " + res.payment_amount);
                System.out.println("Emailing invoice to " + res.email + ".....");

                System.out.println("Do you want to exit?");
                System.out.println("1. Yes");
                System.out.println("2. No");
                int choice = sc.nextInt();
                if (choice == 1){
                    loop1 = false;
                }
                else if (choice == 2){
                    loop1 = true;
                } 
            }  
    }
      sc2.close();
      sc.close();
    }
}

