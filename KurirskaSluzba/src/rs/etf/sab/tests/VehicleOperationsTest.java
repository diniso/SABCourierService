/*     */ package rs.etf.sab.tests;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import java.util.Random;
/*     */ import org.junit.After;
/*     */ import org.junit.Assert;
/*     */ import org.junit.Before;
/*     */ import org.junit.Test;
/*     */ import rs.etf.sab.operations.AddressOperations;
/*     */ import rs.etf.sab.operations.CityOperations;
/*     */ import rs.etf.sab.operations.GeneralOperations;
/*     */ import rs.etf.sab.operations.StockroomOperations;
/*     */ import rs.etf.sab.operations.VehicleOperations;
/*     */ 
/*     */ public class VehicleOperationsTest
/*     */ {
/*     */   private GeneralOperations generalOperations;
/*     */   private AddressOperations addressOperations;
/*     */   private CityOperations cityOperations;
/*     */   
/*     */   @Before
/*     */   public void setUp() {
/*  23 */     this.testHandler = TestHandler.getInstance();
/*  24 */     Assert.assertNotNull(this.testHandler);
/*     */     
/*  26 */     this.cityOperations = this.testHandler.getCityOperations();
/*  27 */     Assert.assertNotNull(this.cityOperations);
/*     */     
/*  29 */     this.addressOperations = this.testHandler.getAddressOperations();
/*  30 */     Assert.assertNotNull(this.addressOperations);
/*     */     
/*  32 */     this.stockroomOperations = this.testHandler.getStockroomOperations();
/*  33 */     Assert.assertNotNull(this.stockroomOperations);
/*     */     
/*  35 */     this.vehicleOperations = this.testHandler.getVehicleOperations();
/*  36 */     Assert.assertNotNull(this.vehicleOperations);
/*     */     
/*  38 */     this.generalOperations = this.testHandler.getGeneralOperations();
/*  39 */     Assert.assertNotNull(this.generalOperations);
/*     */     
/*  41 */     this.generalOperations.eraseAll();
/*     */   }
/*     */   private StockroomOperations stockroomOperations; private VehicleOperations vehicleOperations; private TestHandler testHandler;
/*     */   @After
/*     */   public void tearDown() {
/*  46 */     this.generalOperations.eraseAll();
/*     */   }
/*     */   
/*     */   int insertStockroom() {
/*  50 */     String street = "Bulevar kralja Aleksandra";
/*  51 */     int number = 73;
/*     */     
/*  53 */     int idCity = this.cityOperations.insertCity("Belgrade", "11000");
/*  54 */     Assert.assertNotEquals(-1L, idCity);
/*     */     
/*  56 */     int idAddress = this.addressOperations.insertAddress(street, number, idCity, 10, 10);
/*  57 */     Assert.assertNotEquals(-1L, idAddress);
/*     */     
/*  59 */     int idStockroom = this.stockroomOperations.insertStockroom(idAddress);
/*  60 */     Assert.assertNotEquals(-1L, idStockroom);
/*  61 */     Assert.assertEquals(1L, this.stockroomOperations.getAllStockrooms().size());
/*     */     
/*  63 */     return idStockroom;
/*     */   }
/*     */   
/*     */   @Test
/*     */   public void insertVehicle() {
/*  68 */     String licencePlateNumber = "BG1675DA";
/*  69 */     BigDecimal fuelConsumption = new BigDecimal(6.3D);
/*  70 */     BigDecimal capacity = new BigDecimal(100.5D);
/*  71 */     int fuelType = 1;
/*     */     
/*  73 */     Assert.assertTrue(this.vehicleOperations.insertVehicle(licencePlateNumber, fuelType, fuelConsumption, capacity));
/*  74 */     Assert.assertEquals(1L, this.vehicleOperations.getAllVehichles().size());
/*  75 */     Assert.assertTrue(this.vehicleOperations.getAllVehichles().contains(licencePlateNumber));
/*     */   }
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void insertVehicle_UniqueLicencePlateNumber() {
/*  81 */     String licencePlateNumber = "BG1675DA";
/*  82 */     BigDecimal fuelConsumption = new BigDecimal(6.3D);
/*  83 */     BigDecimal capacity = new BigDecimal(100.5D);
/*  84 */     int fuelType = 1;
/*     */     
/*  86 */     Assert.assertTrue(this.vehicleOperations.insertVehicle(licencePlateNumber, fuelType, fuelConsumption, capacity));
/*  87 */     Assert.assertFalse(this.vehicleOperations.insertVehicle(licencePlateNumber, fuelType, fuelConsumption, capacity));
/*     */     
/*  89 */     Assert.assertEquals(1L, this.vehicleOperations.getAllVehichles().size());
/*  90 */     Assert.assertTrue(this.vehicleOperations.getAllVehichles().contains(licencePlateNumber));
/*     */   }
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void deleteVehicles() {
/*  96 */     String licencePlateNumber = "BG1675DA";
/*  97 */     BigDecimal fuelConsumption = new BigDecimal(6.3D);
/*  98 */     BigDecimal capacity = new BigDecimal(100.5D);
/*  99 */     int fuelType = 1;
/*     */     
/* 101 */     Assert.assertTrue(this.vehicleOperations.insertVehicle(licencePlateNumber, fuelType, fuelConsumption, capacity));
/* 102 */     Assert.assertEquals(1L, this.vehicleOperations.getAllVehichles().size());
/* 103 */     Assert.assertTrue(this.vehicleOperations.getAllVehichles().contains(licencePlateNumber));
/*     */     
/* 105 */     Assert.assertEquals(1L, this.vehicleOperations.deleteVehicles(new String[] { licencePlateNumber }));
/* 106 */     Assert.assertEquals(0L, this.vehicleOperations.getAllVehichles().size());
/* 107 */     Assert.assertFalse(this.vehicleOperations.getAllVehichles().contains(licencePlateNumber));
/*     */   }
/*     */   
/*     */   @Test
/*     */   public void parkVehicle() {
/* 112 */     String licencePlateNumber = "BG1675DA";
/* 113 */     BigDecimal fuelConsumption = new BigDecimal(6.3D);
/* 114 */     BigDecimal capacity = new BigDecimal(100.5D);
/* 115 */     int fuelType = 1;
/*     */     
/* 117 */     int idStockroom = insertStockroom();
/*     */     
/* 119 */     Assert.assertTrue(this.vehicleOperations.insertVehicle(licencePlateNumber, fuelType, fuelConsumption, capacity));
/* 120 */     Assert.assertEquals(1L, this.vehicleOperations.getAllVehichles().size());
/* 121 */     Assert.assertTrue(this.vehicleOperations.parkVehicle(licencePlateNumber, idStockroom));
/*     */   }
/*     */   
/*     */   @Test
/*     */   public void parkVehicle_NoVehicle() {
/* 126 */     String licencePlateNumber = "BG1675DA";
/*     */     
/* 128 */     int idStockroom = insertStockroom();
/* 129 */     Assert.assertFalse(this.vehicleOperations.parkVehicle(licencePlateNumber, idStockroom));
/*     */   }
/*     */   
/*     */   @Test
/*     */   public void parkVehicle_NoStockroom() {
/* 134 */     String licencePlateNumber = "BG1675DA";
/* 135 */     BigDecimal fuelConsumption = new BigDecimal(6.3D);
/* 136 */     BigDecimal capacity = new BigDecimal(100.5D);
/* 137 */     int fuelType = 1;
/*     */     
/* 139 */     Random random = new Random();
/* 140 */     int idStockroom = random.nextInt();
/*     */     
/* 142 */     Assert.assertTrue(this.vehicleOperations.insertVehicle(licencePlateNumber, fuelType, fuelConsumption, capacity));
/* 143 */     Assert.assertEquals(1L, this.vehicleOperations.getAllVehichles().size());
/* 144 */     Assert.assertFalse(this.vehicleOperations.parkVehicle(licencePlateNumber, idStockroom));
/*     */   }
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void changeFuelType() {
/* 150 */     String licencePlateNumber = "BG1675DA";
/* 151 */     BigDecimal fuelConsumption = new BigDecimal(6.3D);
/* 152 */     BigDecimal capacity = new BigDecimal(100.5D);
/* 153 */     int fuelType = 1;
/*     */     
/* 155 */     Assert.assertFalse(this.vehicleOperations.changeFuelType(licencePlateNumber, 2));
/*     */     
/* 157 */     Assert.assertTrue(this.vehicleOperations.insertVehicle(licencePlateNumber, fuelType, fuelConsumption, capacity));
/* 158 */     Assert.assertEquals(1L, this.vehicleOperations.getAllVehichles().size());
/*     */     
/* 160 */     Assert.assertFalse(this.vehicleOperations.changeFuelType(licencePlateNumber, 2));
/*     */     
/* 162 */     int idStockroom = insertStockroom();
/* 163 */     Assert.assertTrue(this.vehicleOperations.parkVehicle(licencePlateNumber, idStockroom));
/*     */     
/* 165 */     Assert.assertTrue(this.vehicleOperations.changeFuelType(licencePlateNumber, 2));
/*     */   }
/*     */   
/*     */   @Test
/*     */   public void changeConsumption() {
/* 170 */     String licencePlateNumber = "BG1675DA";
/* 171 */     BigDecimal fuelConsumption = new BigDecimal(6.3D);
/* 172 */     BigDecimal capacity = new BigDecimal(100.5D);
/* 173 */     int fuelType = 1;
/*     */     
/* 175 */     Assert.assertFalse(this.vehicleOperations.changeConsumption(licencePlateNumber, new BigDecimal(7.3D)));
/*     */     
/* 177 */     Assert.assertTrue(this.vehicleOperations.insertVehicle(licencePlateNumber, fuelType, fuelConsumption, capacity));
/* 178 */     Assert.assertEquals(1L, this.vehicleOperations.getAllVehichles().size());
/*     */     
/* 180 */     Assert.assertFalse(this.vehicleOperations.changeConsumption(licencePlateNumber, new BigDecimal(7.3D)));
/*     */     
/* 182 */     int idStockroom = insertStockroom();
/* 183 */     Assert.assertTrue(this.vehicleOperations.parkVehicle(licencePlateNumber, idStockroom));
/*     */     
/* 185 */     Assert.assertTrue(this.vehicleOperations.changeConsumption(licencePlateNumber, new BigDecimal(7.3D)));
/*     */   }
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void changeCapacity() {
/* 191 */     String licencePlateNumber = "BG1675DA";
/* 192 */     BigDecimal fuelConsumption = new BigDecimal(6.3D);
/* 193 */     BigDecimal capacity = new BigDecimal(100.5D);
/* 194 */     int fuelType = 1;
/*     */     
/* 196 */     Assert.assertFalse(this.vehicleOperations.changeCapacity(licencePlateNumber, new BigDecimal(107.3D)));
/*     */     
/* 198 */     Assert.assertTrue(this.vehicleOperations.insertVehicle(licencePlateNumber, fuelType, fuelConsumption, capacity));
/* 199 */     Assert.assertEquals(1L, this.vehicleOperations.getAllVehichles().size());
/*     */     
/* 201 */     Assert.assertFalse(this.vehicleOperations.changeCapacity(licencePlateNumber, new BigDecimal(107.3D)));
/*     */     
/* 203 */     int idStockroom = insertStockroom();
/* 204 */     Assert.assertTrue(this.vehicleOperations.parkVehicle(licencePlateNumber, idStockroom));
/*     */     
/* 206 */     Assert.assertTrue(this.vehicleOperations.changeCapacity(licencePlateNumber, new BigDecimal(107.3D)));
/*     */   }
/*     */ }


/* Location:              C:\Users\vulov\Downloads\SAB_projekat_2122\SAB_projekat_2122\SAB_projekat_2122.jar!\rs\etf\sab\tests\VehicleOperationsTest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */