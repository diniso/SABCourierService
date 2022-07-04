package rs.etf.sab.student;

import rs.etf.sab.operations.*;
import rs.etf.sab.tests.TestHandler;
import rs.etf.sab.tests.TestRunner;


public class StudentMain {

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
