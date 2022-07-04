/*     */ package rs.etf.sab.tests;
import javax.validation.constraints.NotNull;
import rs.etf.sab.operations.AddressOperations;
/*     */ import rs.etf.sab.operations.CityOperations;
import rs.etf.sab.operations.CourierOperations;
/*     */ import rs.etf.sab.operations.CourierRequestOperation;
import rs.etf.sab.operations.DriveOperation;
import rs.etf.sab.operations.GeneralOperations;
/*     */ import rs.etf.sab.operations.PackageOperations;
import rs.etf.sab.operations.StockroomOperations;
import rs.etf.sab.operations.UserOperations;
import rs.etf.sab.operations.VehicleOperations;
/*     */ 
/*     */ public class TestHandler {
/*   8 */   private static TestHandler testHandler = null;
/*     */   
/*     */   private AddressOperations addressOperations;
/*     */   
/*     */   private CityOperations cityOperations;
/*     */   
/*     */   private CourierOperations courierOperations;
/*     */   
/*     */   private CourierRequestOperation courierRequestOperation;
/*     */   
/*     */   private DriveOperation driveOperation;
/*     */   
/*     */   private GeneralOperations generalOperations;
/*     */   
/*     */   private PackageOperations packageOperations;
/*     */   
/*     */   private StockroomOperations stockroomOperations;
/*     */   
/*     */   private UserOperations userOperations;
/*     */   
/*     */   private VehicleOperations vehicleOperations;
/*     */ 
/*     */   
/*     */   private TestHandler(@NotNull AddressOperations addressOperations, @NotNull CityOperations cityOperations, @NotNull CourierOperations courierOperations, @NotNull CourierRequestOperation courierRequestOperation, @NotNull DriveOperation driveOperation, @NotNull GeneralOperations generalOperations, @NotNull PackageOperations packageOperations, @NotNull StockroomOperations stockroomOperations, @NotNull UserOperations userOperations, @NotNull VehicleOperations vehicleOperations) {
/*  32 */     this.addressOperations = addressOperations;
/*  33 */     this.cityOperations = cityOperations;
/*  34 */     this.courierOperations = courierOperations;
/*  35 */     this.courierRequestOperation = courierRequestOperation;
/*  36 */     this.driveOperation = driveOperation;
/*  37 */     this.generalOperations = generalOperations;
/*  38 */     this.packageOperations = packageOperations;
/*  39 */     this.stockroomOperations = stockroomOperations;
/*  40 */     this.userOperations = userOperations;
/*  41 */     this.vehicleOperations = vehicleOperations;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void createInstance(@NotNull AddressOperations addressOperations, @NotNull CityOperations cityOperations, @NotNull CourierOperations courierOperations, @NotNull CourierRequestOperation courierRequestOperation, @NotNull DriveOperation driveOperation, @NotNull GeneralOperations generalOperations, @NotNull PackageOperations packageOperations, @NotNull StockroomOperations stockroomOperations, @NotNull UserOperations userOperations, @NotNull VehicleOperations vehicleOperations) {
/*  56 */     testHandler = new TestHandler(addressOperations, cityOperations, courierOperations, courierRequestOperation, driveOperation, generalOperations, packageOperations, stockroomOperations, userOperations, vehicleOperations);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static TestHandler getInstance() {
/*  62 */     return testHandler;
/*     */   }
/*     */   
/*     */   public AddressOperations getAddressOperations() {
/*  66 */     return this.addressOperations;
/*     */   }
/*     */   
/*     */   public CityOperations getCityOperations() {
/*  70 */     return this.cityOperations;
/*     */   }
/*     */   
/*     */   public CourierOperations getCourierOperations() {
/*  74 */     return this.courierOperations;
/*     */   }
/*     */   
/*     */   public CourierRequestOperation getCourierRequestOperation() {
/*  78 */     return this.courierRequestOperation;
/*     */   }
/*     */   
/*     */   public DriveOperation getDriveOperation() {
/*  82 */     return this.driveOperation;
/*     */   }
/*     */   
/*     */   public GeneralOperations getGeneralOperations() {
/*  86 */     return this.generalOperations;
/*     */   }
/*     */   
/*     */   public PackageOperations getPackageOperations() {
/*  90 */     return this.packageOperations;
/*     */   }
/*     */   
/*     */   public StockroomOperations getStockroomOperations() {
/*  94 */     return this.stockroomOperations;
/*     */   }
/*     */   
/*     */   public UserOperations getUserOperations() {
/*  98 */     return this.userOperations;
/*     */   }
/*     */   
/*     */   public VehicleOperations getVehicleOperations() {
/* 102 */     return this.vehicleOperations;
/*     */   }
/*     */ }


/* Location:              C:\Users\vulov\Downloads\SAB_projekat_2122\SAB_projekat_2122\SAB_projekat_2122.jar!\rs\etf\sab\tests\TestHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */