/*     */ package rs.etf.sab.tests;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.junit.After;
/*     */ import org.junit.Assert;
/*     */ import org.junit.Before;
/*     */ import org.junit.Test;
/*     */ import rs.etf.sab.operations.AddressOperations;
/*     */ import rs.etf.sab.operations.CityOperations;
/*     */ import rs.etf.sab.operations.CourierOperations;
/*     */ import rs.etf.sab.operations.CourierRequestOperation;
/*     */ import rs.etf.sab.operations.DriveOperation;
/*     */ import rs.etf.sab.operations.GeneralOperations;
/*     */ import rs.etf.sab.operations.PackageOperations;
/*     */ import rs.etf.sab.operations.StockroomOperations;
/*     */ import rs.etf.sab.operations.UserOperations;
/*     */ import rs.etf.sab.operations.VehicleOperations;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PublicModuleTest
/*     */ {
/*     */   private GeneralOperations generalOperations;
/*     */   private CityOperations cityOperations;
/*     */   private AddressOperations addressOperations;
/*     */   private UserOperations userOperations;
/*     */   private CourierRequestOperation courierRequestOperation;
/*     */   
/*     */   @Before
/*     */   public void setUp() {
/*  33 */     this.testHandler = TestHandler.getInstance();
/*  34 */     Assert.assertNotNull(this.testHandler);
/*     */     
/*  36 */     this.cityOperations = this.testHandler.getCityOperations();
/*  37 */     Assert.assertNotNull(this.cityOperations);
/*     */     
/*  39 */     this.addressOperations = this.testHandler.getAddressOperations();
/*  40 */     Assert.assertNotNull(this.addressOperations);
/*     */     
/*  42 */     this.userOperations = this.testHandler.getUserOperations();
/*  43 */     Assert.assertNotNull(this.userOperations);
/*     */     
/*  45 */     this.courierRequestOperation = this.testHandler.getCourierRequestOperation();
/*  46 */     Assert.assertNotNull(this.courierRequestOperation);
/*     */     
/*  48 */     this.courierOperation = this.testHandler.getCourierOperations();
/*  49 */     Assert.assertNotNull(this.courierOperation);
/*     */     
/*  51 */     this.vehicleOperations = this.testHandler.getVehicleOperations();
/*  52 */     Assert.assertNotNull(this.vehicleOperations);
/*     */     
/*  54 */     this.stockroomOperations = this.testHandler.getStockroomOperations();
/*  55 */     Assert.assertNotNull(this.stockroomOperations);
/*     */     
/*  57 */     this.packageOperations = this.testHandler.getPackageOperations();
/*  58 */     Assert.assertNotNull(this.packageOperations);
/*     */     
/*  60 */     this.driveOperation = this.testHandler.getDriveOperation();
/*  61 */     Assert.assertNotNull(this.driveOperation);
/*     */     
/*  63 */     this.generalOperations = this.testHandler.getGeneralOperations();
/*  64 */     Assert.assertNotNull(this.generalOperations);
/*     */     
/*  66 */     this.generalOperations.eraseAll();
/*     */   }
/*     */   private VehicleOperations vehicleOperations; private CourierOperations courierOperation; private StockroomOperations stockroomOperations; private PackageOperations packageOperations; private DriveOperation driveOperation; private TestHandler testHandler;
/*     */   @After
/*     */   public void tearUp() {
/*  71 */     this.testHandler.getGeneralOperations().eraseAll();
/*     */   }
/*     */ 
/*     */   
/*     */   int insertCity(String name, String postalCode) {
/*  76 */     int idCity = this.cityOperations.insertCity(name, postalCode);
/*  77 */     Assert.assertNotEquals(-1L, idCity);
/*  78 */     Assert.assertTrue(this.cityOperations.getAllCities().contains(Integer.valueOf(idCity)));
/*  79 */     return idCity;
/*     */   }
/*     */   
/*  82 */   Map<Integer, Pair<Integer, Integer>> addressesCoords = new HashMap<>();
/*     */   
/*     */   int insertAddress(String street, int number, int idCity, int x, int y) {
/*  85 */     int idAddress = this.addressOperations.insertAddress(street, number, idCity, x, y);
/*  86 */     Assert.assertNotEquals(-1L, idAddress);
/*  87 */     Assert.assertTrue(this.addressOperations.getAllAddresses().contains(Integer.valueOf(idAddress)));
/*  88 */     this.addressesCoords.put(Integer.valueOf(idAddress), new Pair<>(Integer.valueOf(x), Integer.valueOf(y)));
/*  89 */     return idAddress;
/*     */   }
/*     */   
/*     */   String insertUser(String username, String firstName, String lastName, String password, int idAddress) {
/*  93 */     Assert.assertTrue(this.userOperations.insertUser(username, firstName, lastName, password, idAddress));
/*  94 */     Assert.assertTrue(this.userOperations.getAllUsers().contains(username));
/*  95 */     return username;
/*     */   }
/*     */   
/*     */   String insertCourier(String username, String firstName, String lastName, String password, int idAddress, String driverLicenceNumber) {
/*  99 */     insertUser(username, firstName, lastName, password, idAddress);
/* 100 */     Assert.assertTrue(this.courierOperation.insertCourier(username, driverLicenceNumber));
/* 101 */     return username;
/*     */   }
/*     */   
/*     */   public void insertAndParkVehicle(String licencePlateNumber, BigDecimal fuelConsumption, BigDecimal capacity, int fuelType, int idStockroom) {
/* 105 */     Assert.assertTrue(this.vehicleOperations.insertVehicle(licencePlateNumber, fuelType, fuelConsumption, capacity));
/* 106 */     Assert.assertTrue(this.vehicleOperations.getAllVehichles().contains(licencePlateNumber));
/* 107 */     Assert.assertTrue(this.vehicleOperations.parkVehicle(licencePlateNumber, idStockroom));
/*     */   }
/*     */   
/*     */   public int insertStockroom(int idAddress) {
/* 111 */     int stockroomId = this.stockroomOperations.insertStockroom(idAddress);
/* 112 */     Assert.assertNotEquals(-1L, stockroomId);
/* 113 */     Assert.assertTrue(this.stockroomOperations.getAllStockrooms().contains(Integer.valueOf(stockroomId)));
/* 114 */     return stockroomId;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 119 */   Map<Integer, BigDecimal> packagePrice = new HashMap<>();
/*     */   int insertAndAcceptPackage(int addressFrom, int addressTo, String userName, int packageType, BigDecimal weight) {
/* 121 */     int idPackage = this.packageOperations.insertPackage(addressFrom, addressTo, userName, packageType, weight);
/* 122 */     Assert.assertNotEquals(-1L, idPackage);
/* 123 */     Assert.assertTrue(this.packageOperations.acceptAnOffer(idPackage));
/* 124 */     Assert.assertTrue(this.packageOperations.getAllPackages().contains(Integer.valueOf(idPackage)));
/*     */     
/* 126 */     Assert.assertEquals(1L, this.packageOperations.getDeliveryStatus(idPackage));
/* 127 */     BigDecimal price = Util.getPackagePrice(packageType, weight, 
/* 128 */         Util.getDistance((Pair<Integer, Integer>[])new Pair[] { this.addressesCoords.get(Integer.valueOf(addressFrom)), this.addressesCoords.get(Integer.valueOf(addressTo)) }));
/*     */     
/* 130 */     Assert.assertTrue((this.packageOperations.getPriceOfDelivery(idPackage).compareTo(price.multiply(new BigDecimal(1.05D))) < 0));
/* 131 */     Assert.assertTrue((this.packageOperations.getPriceOfDelivery(idPackage).compareTo(price.multiply(new BigDecimal(0.95D))) > 0));
/*     */     
/* 133 */     this.packagePrice.put(Integer.valueOf(idPackage), price);
/*     */     
/* 135 */     return idPackage;
/*     */   }
/*     */   
/*     */   @Test
/*     */   public void publicOne() {
/* 140 */     int BG = insertCity("Belgrade", "11000");
/* 141 */     int KG = insertCity("Kragujevac", "550000");
/* 142 */     int VA = insertCity("Valjevo", "14000");
/* 143 */     int CA = insertCity("Cacak", "32000");
/*     */     
/* 145 */     int idAddressBG1 = insertAddress("Kraljice Natalije", 37, BG, 11, 15);
/* 146 */     int idAddressBG2 = insertAddress("Bulevar kralja Aleksandra", 73, BG, 10, 10);
/* 147 */     int idAddressBG3 = insertAddress("Vojvode Stepe", 39, BG, 1, -1);
/* 148 */     int idAddressBG4 = insertAddress("Takovska", 7, BG, 11, 12);
/* 149 */     int idAddressBG5 = insertAddress("Bulevar kralja Aleksandra", 37, BG, 12, 12);
/*     */     
/* 151 */     int idAddressKG1 = insertAddress("Daniciceva", 1, KG, 4, 310);
/* 152 */     int idAddressKG2 = insertAddress("Dure Pucara Starog", 2, KG, 11, 320);
/*     */     
/* 154 */     int idAddressVA1 = insertAddress("Cika Ljubina", 8, VA, 102, 101);
/* 155 */     int idAddressVA2 = insertAddress("Karadjordjeva", 122, VA, 104, 103);
/* 156 */     int idAddressVA3 = insertAddress("Milovana Glisica", 45, VA, 101, 101);
/*     */     
/* 158 */     int idAddressCA1 = insertAddress("Zupana Stracimira", 1, CA, 110, 309);
/* 159 */     int idAddressCA2 = insertAddress("Bulevar Vuka Karadzica", 1, CA, 111, 315);
/*     */     
/* 161 */     int idStockroomBG = insertStockroom(idAddressBG1);
/* 162 */     int idStockroomVA = insertStockroom(idAddressVA1);
/* 163 */     insertAndParkVehicle("BG1675DA", new BigDecimal(6.3D), new BigDecimal(1000.5D), 2, idStockroomBG);
/* 164 */     insertAndParkVehicle("VA1675DA", new BigDecimal(7.3D), new BigDecimal(500.5D), 1, idStockroomVA);
/*     */     
/* 166 */     String username = "crno.dete";
/* 167 */     insertUser(username, "Svetislav", "Kisprdilov", "Test_123", idAddressBG1);
/*     */     
/* 169 */     String courierUsernameBG = "postarBG";
/* 170 */     insertCourier(courierUsernameBG, "Pera", "Peric", "Postar_73", idAddressBG2, "654321");
/*     */     
/* 172 */     String courierUsernameVA = "postarVA";
/* 173 */     insertCourier(courierUsernameVA, "Pera", "Peric", "Postar_73", idAddressBG2, "123456");
/*     */     
/* 175 */     int type1 = 0;
/* 176 */     BigDecimal weight1 = new BigDecimal(2);
/* 177 */     int idPackage1 = insertAndAcceptPackage(idAddressBG2, idAddressCA1, username, type1, weight1);
/*     */     
/* 179 */     int type2 = 1;
/* 180 */     BigDecimal weight2 = new BigDecimal(4);
/* 181 */     int idPackage2 = insertAndAcceptPackage(idAddressBG3, idAddressVA1, username, type2, weight2);
/*     */     
/* 183 */     int type3 = 2;
/* 184 */     BigDecimal weight3 = new BigDecimal(5);
/* 185 */     int idPackage3 = insertAndAcceptPackage(idAddressBG4, idAddressKG1, username, type3, weight3);
/*     */     
/* 187 */     Assert.assertEquals(0L, this.courierOperation.getCouriersWithStatus(1).size());
/* 188 */     this.driveOperation.planingDrive(courierUsernameBG);
/* 189 */     Assert.assertTrue(this.courierOperation.getCouriersWithStatus(1).contains(courierUsernameBG));
/*     */     
/* 191 */     int type4 = 3;
/* 192 */     BigDecimal weight4 = new BigDecimal(2);
/* 193 */     int idPackage4 = insertAndAcceptPackage(idAddressBG2, idAddressKG2, username, type4, weight4);
/*     */     
/* 195 */     Assert.assertEquals(4L, this.packageOperations.getAllPackagesCurrentlyAtCity(BG).size());
/*     */     
/* 197 */     Assert.assertEquals(-2L, this.driveOperation.nextStop(courierUsernameBG));
/* 198 */     Assert.assertEquals(2L, this.packageOperations.getDeliveryStatus(idPackage1));
/* 199 */     Assert.assertEquals(1L, this.packageOperations.getDeliveryStatus(idPackage2));
/* 200 */     Assert.assertEquals(1L, this.packageOperations.getDeliveryStatus(idPackage3));
/* 201 */     Assert.assertEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage1));
/* 202 */     Assert.assertNotEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage2));
/* 203 */     Assert.assertNotEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage3));
/* 204 */     Assert.assertEquals(3L, this.packageOperations.getAllPackagesCurrentlyAtCity(BG).size());
/* 205 */     Assert.assertEquals(1L, this.driveOperation.getPackagesInVehicle(courierUsernameBG).size());
/*     */     
/* 207 */     Assert.assertEquals(-2L, this.driveOperation.nextStop(courierUsernameBG));
/* 208 */     Assert.assertEquals(2L, this.packageOperations.getDeliveryStatus(idPackage1));
/* 209 */     Assert.assertEquals(2L, this.packageOperations.getDeliveryStatus(idPackage2));
/* 210 */     Assert.assertEquals(1L, this.packageOperations.getDeliveryStatus(idPackage3));
/* 211 */     Assert.assertEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage1));
/* 212 */     Assert.assertEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage2));
/* 213 */     Assert.assertNotEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage3));
/* 214 */     Assert.assertEquals(2L, this.packageOperations.getAllPackagesCurrentlyAtCity(BG).size());
/* 215 */     Assert.assertEquals(2L, this.driveOperation.getPackagesInVehicle(courierUsernameBG).size());
/*     */     
/* 217 */     Assert.assertEquals(-2L, this.driveOperation.nextStop(courierUsernameBG));
/* 218 */     Assert.assertEquals(2L, this.packageOperations.getDeliveryStatus(idPackage1));
/* 219 */     Assert.assertEquals(2L, this.packageOperations.getDeliveryStatus(idPackage2));
/* 220 */     Assert.assertEquals(2L, this.packageOperations.getDeliveryStatus(idPackage3));
/* 221 */     Assert.assertEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage1));
/* 222 */     Assert.assertEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage2));
/* 223 */     Assert.assertEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage3));
/* 224 */     Assert.assertEquals(1L, this.packageOperations.getAllPackagesCurrentlyAtCity(BG).size());
/* 225 */     Assert.assertEquals(3L, this.driveOperation.getPackagesInVehicle(courierUsernameBG).size());
/*     */     
/* 227 */     Assert.assertEquals(idPackage2, this.driveOperation.nextStop(courierUsernameBG));
/* 228 */     Assert.assertEquals(2L, this.packageOperations.getDeliveryStatus(idPackage1));
/* 229 */     Assert.assertEquals(3L, this.packageOperations.getDeliveryStatus(idPackage2));
/* 230 */     Assert.assertEquals(2L, this.packageOperations.getDeliveryStatus(idPackage3));
/* 231 */     Assert.assertEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage1));
/* 232 */     Assert.assertNotEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage2));
/* 233 */     Assert.assertEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage3));
/* 234 */     Assert.assertEquals(1L, this.packageOperations.getAllPackagesCurrentlyAtCity(VA).size());
/* 235 */     Assert.assertEquals(2L, this.driveOperation.getPackagesInVehicle(courierUsernameBG).size());
/*     */     
/* 237 */     Assert.assertEquals(idPackage1, this.driveOperation.nextStop(courierUsernameBG));
/* 238 */     Assert.assertEquals(3L, this.packageOperations.getDeliveryStatus(idPackage1));
/* 239 */     Assert.assertEquals(3L, this.packageOperations.getDeliveryStatus(idPackage2));
/* 240 */     Assert.assertEquals(2L, this.packageOperations.getDeliveryStatus(idPackage3));
/* 241 */     Assert.assertNotEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage1));
/* 242 */     Assert.assertNotEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage2));
/* 243 */     Assert.assertEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage3));
/* 244 */     Assert.assertEquals(1L, this.packageOperations.getAllPackagesCurrentlyAtCity(CA).size());
/* 245 */     Assert.assertEquals(1L, this.driveOperation.getPackagesInVehicle(courierUsernameBG).size());
/*     */     
/* 247 */     Assert.assertEquals(idPackage3, this.driveOperation.nextStop(courierUsernameBG));
/* 248 */     Assert.assertEquals(3L, this.packageOperations.getDeliveryStatus(idPackage1));
/* 249 */     Assert.assertEquals(3L, this.packageOperations.getDeliveryStatus(idPackage2));
/* 250 */     Assert.assertEquals(3L, this.packageOperations.getDeliveryStatus(idPackage3));
/* 251 */     Assert.assertNotEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage1));
/* 252 */     Assert.assertNotEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage2));
/* 253 */     Assert.assertNotEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage3));
/* 254 */     Assert.assertEquals(1L, this.packageOperations.getAllPackagesCurrentlyAtCity(KG).size());
/* 255 */     Assert.assertEquals(0L, this.driveOperation.getPackagesInVehicle(courierUsernameBG).size());
/*     */     
/* 257 */     Assert.assertEquals(-1L, this.driveOperation.nextStop(courierUsernameBG));
/* 258 */     Assert.assertEquals(1L, this.packageOperations.getDeliveryStatus(idPackage4));
/* 259 */     Assert.assertEquals(1L, this.packageOperations.getAllUndeliveredPackages().size());
/* 260 */     Assert.assertTrue(this.packageOperations.getAllUndeliveredPackages().contains(Integer.valueOf(idPackage4)));
/*     */     
/* 262 */     Assert.assertEquals(2L, this.courierOperation.getCouriersWithStatus(0).size());
/*     */     
/* 264 */     double distance = Util.getDistance((Pair<Integer, Integer>[])new Pair[] { this.addressesCoords.get(Integer.valueOf(idAddressBG1)), this.addressesCoords.get(Integer.valueOf(idAddressBG2)), this.addressesCoords
/* 265 */           .get(Integer.valueOf(idAddressBG3)), this.addressesCoords.get(Integer.valueOf(idAddressBG4)), this.addressesCoords
/* 266 */           .get(Integer.valueOf(idAddressVA1)), this.addressesCoords.get(Integer.valueOf(idAddressCA1)), this.addressesCoords.get(Integer.valueOf(idAddressKG1)), this.addressesCoords
/* 267 */           .get(Integer.valueOf(idAddressBG1)) });
/* 268 */     BigDecimal profit = ((BigDecimal)this.packagePrice.get(Integer.valueOf(idPackage1))).add(this.packagePrice.get(Integer.valueOf(idPackage2))).add(this.packagePrice.get(Integer.valueOf(idPackage3)));
/* 269 */     profit = profit.subtract((new BigDecimal(36)).multiply(new BigDecimal(6.3D)).multiply(new BigDecimal(distance)));
/*     */     
/* 271 */     Assert.assertTrue((this.courierOperation.getAverageCourierProfit(3).compareTo(profit.multiply(new BigDecimal(1.05D))) < 0));
/* 272 */     Assert.assertTrue((this.courierOperation.getAverageCourierProfit(3).compareTo(profit.multiply(new BigDecimal(0.95D))) > 0));
/*     */   }
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void publicTwo() {
/* 278 */     int BG = insertCity("Belgrade", "11000");
/* 279 */     int KG = insertCity("Kragujevac", "550000");
/* 280 */     int VA = insertCity("Valjevo", "14000");
/* 281 */     int CA = insertCity("Cacak", "32000");
/*     */     
/* 283 */     int idAddressBG1 = insertAddress("Kraljice Natalije", 37, BG, 11, 15);
/* 284 */     int idAddressBG2 = insertAddress("Bulevar kralja Aleksandra", 73, BG, 10, 10);
/* 285 */     int idAddressBG3 = insertAddress("Vojvode Stepe", 39, BG, 1, -1);
/* 286 */     int idAddressBG4 = insertAddress("Takovska", 7, BG, 11, 12);
/* 287 */     int idAddressBG5 = insertAddress("Bulevar kralja Aleksandra", 37, BG, 12, 12);
/*     */     
/* 289 */     int idAddressKG1 = insertAddress("Daniciceva", 1, KG, 4, 310);
/* 290 */     int idAddressKG2 = insertAddress("Dure Pucara Starog", 2, KG, 11, 320);
/*     */     
/* 292 */     int idAddressVA1 = insertAddress("Cika Ljubina", 8, VA, 102, 101);
/* 293 */     int idAddressVA2 = insertAddress("Karadjordjeva", 122, VA, 104, 103);
/* 294 */     int idAddressVA3 = insertAddress("Milovana Glisica", 45, VA, 101, 101);
/*     */     
/* 296 */     int idAddressCA1 = insertAddress("Zupana Stracimira", 1, CA, 110, 309);
/* 297 */     int idAddressCA2 = insertAddress("Bulevar Vuka Karadzica", 1, CA, 111, 315);
/*     */     
/* 299 */     int idStockroomBG = insertStockroom(idAddressBG1);
/* 300 */     int idStockroomVA = insertStockroom(idAddressVA1);
/* 301 */     insertAndParkVehicle("BG1675DA", new BigDecimal(6.3D), new BigDecimal(1000.5D), 2, idStockroomBG);
/* 302 */     insertAndParkVehicle("VA1675DA", new BigDecimal(7.3D), new BigDecimal(500.5D), 1, idStockroomVA);
/*     */     
/* 304 */     String username = "crno.dete";
/* 305 */     insertUser(username, "Svetislav", "Kisprdilov", "Test_123", idAddressBG1);
/*     */     
/* 307 */     String courierUsernameBG = "postarBG";
/* 308 */     insertCourier(courierUsernameBG, "Pera", "Peric", "Postar_73", idAddressBG2, "654321");
/*     */     
/* 310 */     String courierUsernameVA = "postarVA";
/* 311 */     insertCourier(courierUsernameVA, "Pera", "Peric", "Postar_73", idAddressVA2, "123456");
/*     */     
/* 313 */     int type = 1;
/* 314 */     BigDecimal weight = new BigDecimal(4);
/*     */     
/* 316 */     int idPackage1 = insertAndAcceptPackage(idAddressBG2, idAddressKG1, username, type, weight);
/* 317 */     int idPackage2 = insertAndAcceptPackage(idAddressKG2, idAddressBG4, username, type, weight);
/*     */     
/* 319 */     int idPackage3 = insertAndAcceptPackage(idAddressVA2, idAddressCA1, username, type, weight);
/* 320 */     int idPackage4 = insertAndAcceptPackage(idAddressCA2, idAddressBG4, username, type, weight);
/*     */     
/* 322 */     Assert.assertEquals(0L, this.courierOperation.getCouriersWithStatus(1).size());
/* 323 */     this.driveOperation.planingDrive(courierUsernameBG);
/* 324 */     this.driveOperation.planingDrive(courierUsernameVA);
/* 325 */     Assert.assertEquals(2L, this.courierOperation.getCouriersWithStatus(1).size());
/*     */     
/* 327 */     Assert.assertEquals(-2L, this.driveOperation.nextStop(courierUsernameBG));
/* 328 */     Assert.assertEquals(idPackage1, this.driveOperation.nextStop(courierUsernameBG));
/* 329 */     Assert.assertEquals(3L, this.packageOperations.getDeliveryStatus(idPackage1));
/*     */     
/* 331 */     Assert.assertEquals(-2L, this.driveOperation.nextStop(courierUsernameVA));
/* 332 */     Assert.assertEquals(idPackage3, this.driveOperation.nextStop(courierUsernameVA));
/* 333 */     Assert.assertEquals(3L, this.packageOperations.getDeliveryStatus(idPackage3));
/*     */     
/* 335 */     Assert.assertEquals(-2L, this.driveOperation.nextStop(courierUsernameBG));
/* 336 */     Assert.assertEquals(2L, this.packageOperations.getDeliveryStatus(idPackage2));
/*     */     
/* 338 */     Assert.assertEquals(-2L, this.driveOperation.nextStop(courierUsernameVA));
/* 339 */     Assert.assertEquals(2L, this.packageOperations.getDeliveryStatus(idPackage4));
/*     */     
/* 341 */     Assert.assertEquals(-1L, this.driveOperation.nextStop(courierUsernameBG));
/* 342 */     Assert.assertEquals(2L, this.packageOperations.getDeliveryStatus(idPackage2));
/* 343 */     Assert.assertTrue(this.packageOperations.getAllPackagesCurrentlyAtCity(BG).contains(Integer.valueOf(idPackage2)));
/* 344 */     Assert.assertTrue(this.packageOperations.getAllPackagesCurrentlyAtCity(KG).contains(Integer.valueOf(idPackage1)));
/*     */     
/* 346 */     Assert.assertEquals(-1L, this.driveOperation.nextStop(courierUsernameVA));
/* 347 */     Assert.assertEquals(2L, this.packageOperations.getDeliveryStatus(idPackage4));
/* 348 */     Assert.assertTrue(this.packageOperations.getAllPackagesCurrentlyAtCity(VA).contains(Integer.valueOf(idPackage4)));
/* 349 */     Assert.assertTrue(this.packageOperations.getAllPackagesCurrentlyAtCity(CA).contains(Integer.valueOf(idPackage3)));
/*     */ 
/*     */ 
/*     */     
/* 353 */     int idPackage5 = insertAndAcceptPackage(idAddressVA2, idAddressCA1, username, type, weight);
/* 354 */     int idPackage6 = insertAndAcceptPackage(idAddressBG3, idAddressVA3, username, type, weight);
/*     */     
/* 356 */     this.driveOperation.planingDrive(courierUsernameBG);
/*     */     
/* 358 */     Assert.assertEquals(-2L, this.driveOperation.nextStop(courierUsernameBG));
/* 359 */     Assert.assertEquals(2L, this.packageOperations.getDeliveryStatus(idPackage6));
/* 360 */     Assert.assertTrue(this.packageOperations.getAllPackagesCurrentlyAtCity(BG).contains(Integer.valueOf(idPackage2)));
/* 361 */     Assert.assertFalse(this.packageOperations.getAllPackagesCurrentlyAtCity(BG).contains(Integer.valueOf(idPackage6)));
/*     */     
/* 363 */     Assert.assertEquals(-2L, this.driveOperation.nextStop(courierUsernameBG));
/* 364 */     Assert.assertEquals(2L, this.driveOperation.getPackagesInVehicle(courierUsernameBG).size());
/* 365 */     Assert.assertTrue(this.driveOperation.getPackagesInVehicle(courierUsernameBG).contains(Integer.valueOf(idPackage2)));
/* 366 */     Assert.assertTrue(this.driveOperation.getPackagesInVehicle(courierUsernameBG).contains(Integer.valueOf(idPackage6)));
/*     */     
/* 368 */     Assert.assertEquals(idPackage2, this.driveOperation.nextStop(courierUsernameBG));
/* 369 */     Assert.assertEquals(3L, this.packageOperations.getDeliveryStatus(idPackage2));
/* 370 */     Assert.assertEquals(idPackage6, this.driveOperation.nextStop(courierUsernameBG));
/* 371 */     Assert.assertEquals(3L, this.packageOperations.getDeliveryStatus(idPackage6));
/* 372 */     Assert.assertEquals(0L, this.driveOperation.getPackagesInVehicle(courierUsernameBG).size());
/*     */     
/* 374 */     Assert.assertEquals(-2L, this.driveOperation.nextStop(courierUsernameBG));
/* 375 */     Assert.assertEquals(2L, this.packageOperations.getDeliveryStatus(idPackage5));
/* 376 */     Assert.assertTrue(this.driveOperation.getPackagesInVehicle(courierUsernameBG).contains(Integer.valueOf(idPackage5)));
/*     */     
/* 378 */     Assert.assertEquals(-2L, this.driveOperation.nextStop(courierUsernameBG));
/* 379 */     Assert.assertEquals(2L, this.packageOperations.getDeliveryStatus(idPackage4));
/* 380 */     Assert.assertEquals(2L, this.driveOperation.getPackagesInVehicle(courierUsernameBG).size());
/* 381 */     Assert.assertTrue(this.driveOperation.getPackagesInVehicle(courierUsernameBG).contains(Integer.valueOf(idPackage4)));
/* 382 */     Assert.assertTrue(this.driveOperation.getPackagesInVehicle(courierUsernameBG).contains(Integer.valueOf(idPackage5)));
/* 383 */     Assert.assertEquals(1L, this.packageOperations.getAllPackagesCurrentlyAtCity(VA).size());
/* 384 */     Assert.assertTrue(this.packageOperations.getAllPackagesCurrentlyAtCity(VA).contains(Integer.valueOf(idPackage6)));
/*     */     
/* 386 */     Assert.assertEquals(-1L, this.driveOperation.nextStop(courierUsernameBG));
/* 387 */     Assert.assertEquals(0L, this.packageOperations.getAllUndeliveredPackagesFromCity(BG).size());
/* 388 */     Assert.assertEquals(3L, this.packageOperations.getAllPackagesCurrentlyAtCity(BG).size());
/* 389 */     Assert.assertTrue(this.packageOperations.getAllPackagesCurrentlyAtCity(BG).contains(Integer.valueOf(idPackage2)));
/* 390 */     Assert.assertTrue(this.packageOperations.getAllPackagesCurrentlyAtCity(BG).contains(Integer.valueOf(idPackage4)));
/* 391 */     Assert.assertTrue(this.packageOperations.getAllPackagesCurrentlyAtCity(BG).contains(Integer.valueOf(idPackage5)));
/*     */ 
/*     */     
/* 394 */     this.driveOperation.planingDrive(courierUsernameBG);
/* 395 */     Assert.assertEquals(0L, this.driveOperation.getPackagesInVehicle(courierUsernameBG).size());
/*     */     
/* 397 */     Assert.assertEquals(-2L, this.driveOperation.nextStop(courierUsernameBG));
/* 398 */     Assert.assertEquals(2L, this.driveOperation.getPackagesInVehicle(courierUsernameBG).size());
/* 399 */     Assert.assertTrue(this.driveOperation.getPackagesInVehicle(courierUsernameBG).contains(Integer.valueOf(idPackage4)));
/* 400 */     Assert.assertTrue(this.driveOperation.getPackagesInVehicle(courierUsernameBG).contains(Integer.valueOf(idPackage5)));
/*     */     
/* 402 */     Assert.assertEquals(idPackage4, this.driveOperation.nextStop(courierUsernameBG));
/* 403 */     Assert.assertEquals(3L, this.packageOperations.getDeliveryStatus(idPackage4));
/*     */     
/* 405 */     Assert.assertEquals(idPackage5, this.driveOperation.nextStop(courierUsernameBG));
/* 406 */     Assert.assertEquals(3L, this.packageOperations.getDeliveryStatus(idPackage5));
/*     */     
/* 408 */     Assert.assertEquals(-1L, this.driveOperation.nextStop(courierUsernameBG));
/*     */     
/* 410 */     Assert.assertEquals(0L, this.packageOperations.getAllUndeliveredPackages().size());
/*     */     
/* 412 */     Assert.assertEquals(2L, this.courierOperation.getCouriersWithStatus(0).size());
/*     */     
/* 414 */     Assert.assertTrue((this.courierOperation.getAverageCourierProfit(1).compareTo(new BigDecimal(0)) > 0));
/* 415 */     Assert.assertTrue((this.courierOperation.getAverageCourierProfit(5).compareTo(new BigDecimal(0)) > 0));
/*     */   }
/*     */ }


/* Location:              C:\Users\vulov\Downloads\SAB_projekat_2122\SAB_projekat_2122\SAB_projekat_2122.jar!\rs\etf\sab\tests\PublicModuleTest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */