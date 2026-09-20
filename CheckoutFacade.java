package lab_2;

 class InventoryService{
   
    public  boolean isAvailable(String item,int qty){
        System.out.println(item +"is available");
        return true;
    }
}

 class PaymentService{
    public boolean charge(String account,double amount){
        System.out.println("account is valide");
        return true;
    }

}

class ShippingService{
    public boolean schedule(String  item , String adress){
        System.out.println(item +"we be shipped to"+adress);
        return true;
    }
}

class NotificationService{
    public boolean sendConfirmation(String email){
        System.out.println("sending confirmation to "+email);
        return  true;
    }
}




public class CheckoutFacade {
    InventoryService inventory=new InventoryService();
     PaymentService pay= new PaymentService();
     ShippingService ship= new ShippingService();
     NotificationService not = new NotificationService();

    public boolean placeOrder(String item, int qty, String account,
        double amount,String adress,String email){
            if (!inventory.isAvailable(item, qty)){
                return false;
            }
            if (!pay.charge(account, amount)){
                return  false;
            }
            ship.schedule(item, adress);
            not.sendConfirmation(email);
            return  true;
        }
    
}
