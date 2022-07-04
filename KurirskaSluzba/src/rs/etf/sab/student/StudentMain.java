package rs.etf.sab.student;

import java.math.BigDecimal;
import rs.etf.sab.operations.*;
import rs.etf.sab.tests.TestHandler;
import rs.etf.sab.tests.TestRunner;


public class StudentMain {
    
    // kurir - treba proveri getAverageCourierProfit
    // package - 
    // driver operation

    public static void main(String[] args) {
        AddressOperations addressOperations = new vv180421d_AdressOperations(); // Change this to your implementation.
        CityOperations cityOperations = new vv180421d_CityOperations(); // Do it for all classes.
        CourierOperations courierOperations = new vv180421d_CourierOperations(); // e.g. = new MyDistrictOperations();
        CourierRequestOperation courierRequestOperation = new vv180421d_CourierRequestOperation();
        DriveOperation driveOperation = new vv180421d_DriveOperation();
        GeneralOperations generalOperations = new vv180421d_GeneralOperations();
        PackageOperations packageOperations = new vv180421d_PackageOperations();
        StockroomOperations stockroomOperations = new vv180421d_StockroomOperations();
        UserOperations userOperations = new vv180421d_UserOperations();
        VehicleOperations vehicleOperations = new vv180421d_VehicleOperations();
        
        
  //      System.out.print(driveOperation.planingDrive("vlade"));
 // System.out.println(packageOperations.getAllPackagesCurrentlyAtCity(1));
 
        generalOperations.eraseAll();
        
   //     System.out.println(packageOperations.insertPackage(1 , 2 , "vlade", 0 , BigDecimal.valueOf(12)));
   
    //     System.out.println(packageOperations.getPriceOfDelivery(1));
    //     System.out.println(packageOperations.getPriceOfDelivery(2));
    //     System.out.println(packageOperations.getAllUndeliveredPackagesFromCity(1));

   /*     System.out.println(courierRequestOperation.insertCourierRequest("vlade", "123-345"));
        System.out.println(userOperations.insertUser("vlade1", "Vlade", "VUlovic", "123", 69));
        System.out.println(courierRequestOperation.insertCourierRequest("vlade1", "123-345"));
        System.out.println(cityOperations.insertCity("Grad2", "Postalcode 2"));
        
        System.out.println(cityOperations.deleteCity(1));
        System.out.println(cityOperations.deleteCity(2));
        System.out.println(cityOperations.getAllCities());
        
        System.out.println(cityOperations.deleteCity("Grad4", "Grad5"));
    
        cityOperations.insertCity("City1", "ps1");
        cityOperations.insertCity("City2", "ps1");
        cityOperations.insertCity("City3", "ps1");
        cityOperations.insertCity("City4", "ps1");
        
        cityOperations.deleteCity("City1", "City2", "City");*/
        
       TestHandler.createInstance(
                addressOperations,
                cityOperations,
                courierOperations,
                courierRequestOperation,
                driveOperation,
                generalOperations,
                packageOperations,
                stockroomOperations,
                userOperations,
                vehicleOperations);

        TestRunner.runTests();
    }
}
