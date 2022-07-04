/*     */ package rs.etf.sab.tests;
/*     */ 
/*     */ import org.junit.After;
/*     */ import org.junit.Assert;
/*     */ import org.junit.Before;
/*     */ import org.junit.Test;
/*     */ import rs.etf.sab.operations.AddressOperations;
/*     */ import rs.etf.sab.operations.CityOperations;
/*     */ import rs.etf.sab.operations.CourierRequestOperation;
/*     */ import rs.etf.sab.operations.GeneralOperations;
/*     */ import rs.etf.sab.operations.UserOperations;
/*     */ 
/*     */ 
/*     */ public class CourierRequestOperationTest
/*     */ {
/*     */   private GeneralOperations generalOperations;
/*     */   private CityOperations cityOperations;
/*     */   private AddressOperations addressOperations;
/*     */   
/*     */   @Before
/*     */   public void setUp() {
/*  22 */     this.testHandler = TestHandler.getInstance();
/*  23 */     Assert.assertNotNull(this.testHandler);
/*     */     
/*  25 */     this.cityOperations = this.testHandler.getCityOperations();
/*  26 */     Assert.assertNotNull(this.cityOperations);
/*     */     
/*  28 */     this.addressOperations = this.testHandler.getAddressOperations();
/*  29 */     Assert.assertNotNull(this.addressOperations);
/*     */     
/*  31 */     this.userOperations = this.testHandler.getUserOperations();
/*  32 */     Assert.assertNotNull(this.userOperations);
/*     */     
/*  34 */     this.courierRequestOperation = this.testHandler.getCourierRequestOperation();
/*  35 */     Assert.assertNotNull(this.courierRequestOperation);
/*     */     
/*  37 */     this.generalOperations = this.testHandler.getGeneralOperations();
/*  38 */     Assert.assertNotNull(this.generalOperations);
/*     */     
/*  40 */     this.generalOperations.eraseAll();
/*     */   }
/*     */   private UserOperations userOperations; private CourierRequestOperation courierRequestOperation; private TestHandler testHandler;
/*     */   @After
/*     */   public void tearDown() {
/*  45 */     this.generalOperations.eraseAll();
/*     */   }
/*     */   
/*     */   String insertUser() {
/*  49 */     String street = "Bulevar kralja Aleksandra";
/*  50 */     int number = 73;
/*     */     
/*  52 */     int idCity = this.cityOperations.insertCity("Belgrade", "11000");
/*  53 */     Assert.assertNotEquals(-1L, idCity);
/*     */     
/*  55 */     int idAddress = this.addressOperations.insertAddress(street, number, idCity, 10, 10);
/*  56 */     Assert.assertNotEquals(-1L, idAddress);
/*     */     
/*  58 */     String username = "crno.dete";
/*  59 */     String firstName = "Svetislav";
/*  60 */     String lastName = "Kisprdilov";
/*  61 */     String password = "Test_123";
/*  62 */     Assert.assertTrue(this.userOperations.insertUser(username, firstName, lastName, password, idAddress));
/*  63 */     Assert.assertTrue(this.userOperations.getAllUsers().contains(username));
/*     */     
/*  65 */     return username;
/*     */   }
/*     */   
/*     */   String insertUser2() {
/*  69 */     String street = "Vojvode Stepe";
/*  70 */     int number = 73;
/*     */     
/*  72 */     int idCity = this.cityOperations.insertCity("Nis", "70000");
/*  73 */     Assert.assertNotEquals(-1L, idCity);
/*     */     
/*  75 */     int idAddress = this.addressOperations.insertAddress(street, number, idCity, 100, 100);
/*  76 */     Assert.assertNotEquals(-1L, idAddress);
/*     */     
/*  78 */     String username = "crno.dete.2";
/*  79 */     String firstName = "Svetislav";
/*  80 */     String lastName = "Kisprdilov";
/*  81 */     String password = "Test_123";
/*  82 */     Assert.assertTrue(this.userOperations.insertUser(username, firstName, lastName, password, idAddress));
/*  83 */     Assert.assertTrue(this.userOperations.getAllUsers().contains(username));
/*     */     
/*  85 */     return username;
/*     */   }
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void insertCourierRequest() {
/*  91 */     String username = insertUser();
/*  92 */     String driverLicenceNumber = "1234567";
/*     */     
/*  94 */     Assert.assertTrue(this.courierRequestOperation.insertCourierRequest(username, driverLicenceNumber));
/*  95 */     Assert.assertEquals(1L, this.courierRequestOperation.getAllCourierRequests().size());
/*  96 */     Assert.assertTrue(this.courierRequestOperation.getAllCourierRequests().contains(username));
/*     */   }
/*     */   
/*     */   @Test
/*     */   public void insertCourierRequest_NoUser() {
/* 101 */     String username = "crno.dete";
/* 102 */     String driverLicenceNumber = "1234567";
/*     */     
/* 104 */     Assert.assertFalse(this.courierRequestOperation.insertCourierRequest(username, driverLicenceNumber));
/* 105 */     Assert.assertEquals(0L, this.courierRequestOperation.getAllCourierRequests().size());
/* 106 */     Assert.assertFalse(this.courierRequestOperation.getAllCourierRequests().contains(username));
/*     */   }
/*     */   
/*     */   @Test
/*     */   public void insertCourierRequest_RequestExists() {
/* 111 */     String username = insertUser();
/* 112 */     String driverLicenceNumber = "1234567";
/*     */     
/* 114 */     Assert.assertTrue(this.courierRequestOperation.insertCourierRequest(username, driverLicenceNumber));
/* 115 */     Assert.assertFalse(this.courierRequestOperation.insertCourierRequest(username, driverLicenceNumber));
/* 116 */     Assert.assertEquals(1L, this.courierRequestOperation.getAllCourierRequests().size());
/* 117 */     Assert.assertTrue(this.courierRequestOperation.getAllCourierRequests().contains(username));
/*     */   }
/*     */   
/*     */   @Test
/*     */   public void insertCourierRequest_AlreadyCourier() {
/* 122 */     String username = insertUser();
/* 123 */     String driverLicenceNumber = "1234567";
/*     */     
/* 125 */     Assert.assertTrue(this.courierRequestOperation.insertCourierRequest(username, driverLicenceNumber));
/* 126 */     Assert.assertTrue(this.courierRequestOperation.grantRequest(username));
/*     */     
/* 128 */     Assert.assertFalse(this.courierRequestOperation.insertCourierRequest(username, driverLicenceNumber));
/* 129 */     Assert.assertEquals(0L, this.courierRequestOperation.getAllCourierRequests().size());
/* 130 */     Assert.assertFalse(this.courierRequestOperation.getAllCourierRequests().contains(username));
/*     */   }
/*     */   
/*     */   @Test
/*     */   public void grantRequest() {
/* 135 */     String username = insertUser();
/* 136 */     String driverLicenceNumber = "1234567";
/*     */     
/* 138 */     Assert.assertTrue(this.courierRequestOperation.insertCourierRequest(username, driverLicenceNumber));
/* 139 */     Assert.assertTrue(this.courierRequestOperation.grantRequest(username));
/*     */     
/* 141 */     Assert.assertEquals(0L, this.courierRequestOperation.getAllCourierRequests().size());
/* 142 */     Assert.assertFalse(this.courierRequestOperation.getAllCourierRequests().contains(username));
/*     */   }
/*     */   
/*     */   @Test
/*     */   public void grantRequest_NoRequest() {
/* 147 */     String username = "crno.dete";
/* 148 */     String driverLicenceNumber = "1234567";
/*     */     
/* 150 */     Assert.assertFalse(this.courierRequestOperation.grantRequest(username));
/* 151 */     Assert.assertEquals(0L, this.courierRequestOperation.getAllCourierRequests().size());
/* 152 */     Assert.assertFalse(this.courierRequestOperation.getAllCourierRequests().contains(username));
/*     */   }
/*     */   
/*     */   @Test
/*     */   public void insertCourierRequest_multipleDifferentLicence() {
/* 157 */     String username = insertUser();
/* 158 */     String driverLicenceNumber = "1234567";
/* 159 */     String username2 = insertUser2();
/* 160 */     String driverLicenceNumber2 = "1234561";
/*     */     
/* 162 */     Assert.assertTrue(this.courierRequestOperation.insertCourierRequest(username, driverLicenceNumber));
/* 163 */     Assert.assertTrue(this.courierRequestOperation.insertCourierRequest(username2, driverLicenceNumber2));
/*     */     
/* 165 */     Assert.assertEquals(2L, this.courierRequestOperation.getAllCourierRequests().size());
/* 166 */     Assert.assertTrue(this.courierRequestOperation.getAllCourierRequests().contains(username));
/* 167 */     Assert.assertTrue(this.courierRequestOperation.getAllCourierRequests().contains(username2));
/*     */   }
/*     */   
/*     */   @Test
/*     */   public void insertCourierRequest_multipleSameLicence() {
/* 172 */     String username = insertUser();
/* 173 */     String driverLicenceNumber = "1234567";
/* 174 */     String username2 = insertUser2();
/*     */     
/* 176 */     Assert.assertTrue(this.courierRequestOperation.insertCourierRequest(username, driverLicenceNumber));
/* 177 */     Assert.assertFalse(this.courierRequestOperation.insertCourierRequest(username2, driverLicenceNumber));
/*     */     
/* 179 */     Assert.assertEquals(1L, this.courierRequestOperation.getAllCourierRequests().size());
/* 180 */     Assert.assertTrue(this.courierRequestOperation.getAllCourierRequests().contains(username));
/* 181 */     Assert.assertFalse(this.courierRequestOperation.getAllCourierRequests().contains(username2));
/*     */   }
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void deleteCourierRequest() {
/* 187 */     String username = insertUser();
/* 188 */     String driverLicenceNumber = "1234567";
/*     */     
/* 190 */     Assert.assertTrue(this.courierRequestOperation.insertCourierRequest(username, driverLicenceNumber));
/* 191 */     Assert.assertEquals(1L, this.courierRequestOperation.getAllCourierRequests().size());
/* 192 */     Assert.assertTrue(this.courierRequestOperation.getAllCourierRequests().contains(username));
/*     */     
/* 194 */     Assert.assertTrue(this.courierRequestOperation.deleteCourierRequest(username));
/* 195 */     Assert.assertEquals(0L, this.courierRequestOperation.getAllCourierRequests().size());
/* 196 */     Assert.assertFalse(this.courierRequestOperation.getAllCourierRequests().contains(username));
/*     */   }
/*     */   
/*     */   @Test
/*     */   public void deleteCourierRequest_NoRequest() {
/* 201 */     String username = insertUser();
/* 202 */     String driverLicenceNumber = "1234567";
/*     */     
/* 204 */     Assert.assertTrue(this.courierRequestOperation.insertCourierRequest(username, driverLicenceNumber));
/* 205 */     Assert.assertTrue(this.courierRequestOperation.deleteCourierRequest(username));
/* 206 */     Assert.assertFalse(this.courierRequestOperation.deleteCourierRequest(username));
/* 207 */     Assert.assertEquals(0L, this.courierRequestOperation.getAllCourierRequests().size());
/* 208 */     Assert.assertFalse(this.courierRequestOperation.getAllCourierRequests().contains(username));
/*     */   }
/*     */   
/*     */   @Test
/*     */   public void changeLicenceInCourierRequest() {
/* 213 */     String username = insertUser();
/* 214 */     String driverLicenceNumber = "1234567";
/* 215 */     String newDriverLicenceNumber = "1234567";
/*     */     
/* 217 */     Assert.assertTrue(this.courierRequestOperation.insertCourierRequest(username, driverLicenceNumber));
/*     */     
/* 219 */     Assert.assertTrue(this.courierRequestOperation.changeDriverLicenceNumberInCourierRequest(username, newDriverLicenceNumber));
/*     */   }
/*     */   @Test
/*     */   public void changeLicenceInCourierRequest_NoUser() {
/* 223 */     String username = insertUser();
/* 224 */     String driverLicenceNumber = "1234567";
/* 225 */     String newDriverLicenceNumber = "1234567";
/* 226 */     String username2 = "crno.dete.2";
/* 227 */     Assert.assertTrue(this.courierRequestOperation.insertCourierRequest(username, driverLicenceNumber));
/* 228 */     Assert.assertFalse(this.courierRequestOperation.changeDriverLicenceNumberInCourierRequest(username2, newDriverLicenceNumber));
/*     */   }
/*     */   
/*     */   @Test
/*     */   public void changeLicenceInCourierRequest_NoRequest() {
/* 233 */     String username = insertUser();
/* 234 */     String driverLicenceNumber = "1234567";
/* 235 */     String newDriverLicenceNumber = "1234567";
/*     */     
/* 237 */     Assert.assertTrue(this.courierRequestOperation.insertCourierRequest(username, driverLicenceNumber));
/* 238 */     Assert.assertTrue(this.courierRequestOperation.grantRequest(username));
/* 239 */     Assert.assertFalse(this.courierRequestOperation.changeDriverLicenceNumberInCourierRequest(username, newDriverLicenceNumber));
/*     */   }
/*     */ }


/* Location:              C:\Users\vulov\Downloads\SAB_projekat_2122\SAB_projekat_2122\SAB_projekat_2122.jar!\rs\etf\sab\tests\CourierRequestOperationTest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */