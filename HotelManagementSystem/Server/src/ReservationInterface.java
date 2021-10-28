import java.rmi.*;
import java.util.HashMap;
import java.util.List;

public interface ReservationInterface extends Remote{
	public List<Object>  addReservation(String id, String customer_name, int phone_number, String email,String room_type, String reservation_date,String reservation_status, double payment_amount) throws RemoteException;
    public List<Object> checkReservation(String id,HashMap<String,List<Object>> cust) throws RemoteException;
    public List<Object> removeReservation(String id,HashMap<String,List<Object>> cust) throws RemoteException;
    public List<String> checkAvailability(String room_type) throws RemoteException;
    public double invoice(String room_type) throws RemoteException;
    public void makePayment() throws RemoteException;
    public String matchAvailability(String room_type, String reservation_date) throws RemoteException;
    public HashMap<String,List<Object>> dummyDatabase() throws RemoteException;
}