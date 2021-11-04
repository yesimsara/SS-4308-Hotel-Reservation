import java.util.*;
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
public class Reservation extends UnicastRemoteObject implements ReservationInterface{

    String id;
    String customer_name;
    int phone_number;
    String email;
    int room_number;
    String room_type;
    String reservation_date;
    String reservation_status;
    double payment_amount;
    
    public HashMap<String,List<Object>> dummyDatabase() throws RemoteException{
        HashMap<String,List<Object>> cust = new HashMap<>();
        List<Object> test = new ArrayList<Object>();
        test.add(new String("Test"));
        test.add(123);
        test.add(new String("test@mail.com"));
        test.add(new String("Deluxe"));
        test.add(new String("31/10/2021"));
        test.add(new String("Reservation Submitted"));
        test.add(0.00);
        cust.put("test 123",test);
        List<Object> sara = new ArrayList<Object>();
        sara.add(new String("sara"));
        sara.add(123);
        sara.add(new String("sara@mail.com"));
        sara.add(new String("Executive"));
        sara.add(new String("31/10/2021"));
        sara.add(new String("Reservation Submitted"));
        sara.add(0.00);
        cust.put("sara 123",sara);
        List<Object> bong = new ArrayList<Object>();
        bong.add(new String("Bong"));
        bong.add(123);
        bong.add(new String("bong@mail.com"));
        bong.add(new String("Superior"));
        bong.add(new String("31/10/2021"));
        bong.add(new String("Reservation Submitted"));
        bong.add(0.00);
        cust.put("bong 123",bong);
        return cust;
    }

    // Empty constructor
    public Reservation() throws RemoteException{
    }

    // Add reservation
    public List<Object>  addReservation(String id, String customer_name, int phone_number, String email,String room_type, String reservation_date,String reservation_status, double payment_amount) throws RemoteException {
        List<Object> details = new ArrayList<Object>();
        details.add(customer_name);
        details.add(phone_number);
        details.add(email);
        details.add(room_type);
        details.add(reservation_date);
        details.add(reservation_status);
        details.add(payment_amount);
        return details;
    }

    // Remove reservation
    // Change reservation status to cancelled
    public List<Object> removeReservation(String id,HashMap<String,List<Object>> cust) throws RemoteException{
        List<Object> details = checkReservation(id, cust);
        details.set(5,"Cancelled");
        return details;
    }

    public List<Object> checkReservation(String id,HashMap<String,List<Object>> cust) throws RemoteException{
        List<Object> details = cust.get(id);
        return details;

    }

    // Since we are not making connection to database
    // Hash table of available dates for certain room have been created
    // When this method is called, room type is supplied to it
    // This function return the available dates for the room type
    public List<String> checkAvailability(String room_type) throws RemoteException{
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
    public double invoice(String room_type) throws RemoteException{
        HashMap <String, Double> pricing = new HashMap <> ();
        pricing.put("Superior", 122.99);
        pricing.put("Deluxe",150.00);
        pricing.put("Executive",170.00);

        return pricing.get(room_type);   
    }

    // This function find out if the date selected by customer is available or not
    // If the date is available and the room type is available, the reservation status is changed to 'Confirmed'
    // Else reservation status is changed to 'rejected'
    // This function is only available in admin mode
    // I might change this later to be implemented within customer mode instead for better execution
    public String matchAvailability(String room_type, String reservation_date) throws RemoteException{
        List <String> available_dates = checkAvailability(room_type);
        if (available_dates.contains(reservation_date)) {
            reservation_status = "Accepted";
        } else {
            reservation_status = "Rejected";
        }

        return reservation_status;

    }

    public List<Object> makePayment(String id, HashMap<String,List<Object>> cust,int card_no, String bank_name, int Cvv_no) throws RemoteException {
        //add other details in the parameter
        List<Object> details = new ArrayList<Object>();
        details = cust.get(id);
        double payment_amount = (double)details.get(6);
        details.add(payment_amount);
        details.add(card_no);
        details.add(bank_name);
        details.add(Cvv_no);
        //details.add(); keep adding other details
        return details;
    }



public static void main(String[] args) throws RemoteException {
    try {
        Registry reg = LocateRegistry.createRegistry(2345);
        reg.rebind("server", new Reservation());
        System.out.println("Server Started");
    }
    
    catch(Exception e) {
        System.out.println(e.toString());
    }    
}
}

