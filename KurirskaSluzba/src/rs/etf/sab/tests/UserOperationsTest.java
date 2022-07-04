/*     */ package rs.etf.sab.tests;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.junit.After;
/*     */ import org.junit.Assert;
/*     */ import org.junit.Before;
/*     */ import org.junit.Test;
/*     */ import rs.etf.sab.operations.AddressOperations;
/*     */ import rs.etf.sab.operations.CityOperations;
/*     */ import rs.etf.sab.operations.GeneralOperations;
/*     */ import rs.etf.sab.operations.UserOperations;
/*     */ 
/*     */ public class UserOperationsTest
/*     */ {
/*     */   private GeneralOperations generalOperations;
/*     */   private AddressOperations addressOperations;
/*     */   
/*     */   @Before
/*     */   public void setUp() {
/*  20 */     this.testHandler = TestHandler.getInstance();
/*  21 */     Assert.assertNotNull(this.testHandler);
/*     */     
/*  23 */     this.cityOperations = this.testHandler.getCityOperations();
/*  24 */     Assert.assertNotNull(this.cityOperations);
/*     */     
/*  26 */     this.addressOperations = this.testHandler.getAddressOperations();
/*  27 */     Assert.assertNotNull(this.addressOperations);
/*     */     
/*  29 */     this.userOperations = this.testHandler.getUserOperations();
/*  30 */     Assert.assertNotNull(this.userOperations);
/*     */     
/*  32 */     this.generalOperations = this.testHandler.getGeneralOperations();
/*  33 */     Assert.assertNotNull(this.generalOperations);
/*     */     
/*  35 */     this.generalOperations.eraseAll();
/*     */   }
/*     */   private CityOperations cityOperations; private UserOperations userOperations; private TestHandler testHandler;
/*     */   @After
/*     */   public void tearDown() {
/*  40 */     this.generalOperations.eraseAll();
/*     */   }
/*     */   
/*     */   int insertAddress() {
/*  44 */     String street = "Bulevar kralja Aleksandra";
/*  45 */     int number = 73;
/*     */     
/*  47 */     int idCity = this.cityOperations.insertCity("Belgrade", "11000");
/*  48 */     Assert.assertNotEquals(-1L, idCity);
/*     */     
/*  50 */     int idAddress = this.addressOperations.insertAddress(street, number, idCity, 10, 10);
/*  51 */     Assert.assertNotEquals(-1L, idAddress);
/*  52 */     Assert.assertEquals(1L, this.addressOperations.getAllAddresses().size());
/*     */     
/*  54 */     return idAddress;
/*     */   }
/*     */   
/*     */   @Test
/*     */   public void insertUser_Good() {
/*  59 */     int idAddress = insertAddress();
/*     */     
/*  61 */     String username = "crno.dete";
/*  62 */     String firstName = "Svetislav";
/*  63 */     String lastName = "Kisprdilov";
/*  64 */     String password = "Test_123";
/*     */     
/*  66 */     Assert.assertTrue(this.userOperations.insertUser(username, firstName, lastName, password, idAddress));
/*  67 */     Assert.assertTrue(this.userOperations.getAllUsers().contains(username));
/*  68 */     Assert.assertEquals(1L, this.userOperations.getAllUsers().size());
/*     */   }
/*     */   
/*     */   @Test
/*     */   public void insertUser_UniqueUsername() {
/*  73 */     int idAddress = insertAddress();
/*     */     
/*  75 */     String username = "crno.dete";
/*  76 */     String firstName = "Svetislav";
/*  77 */     String lastName = "Kisprdilov";
/*  78 */     String password = "Test_123";
/*     */     
/*  80 */     Assert.assertTrue(this.userOperations.insertUser(username, firstName, lastName, password, idAddress));
/*  81 */     Assert.assertFalse(this.userOperations.insertUser(username, firstName, lastName, password, idAddress));
/*  82 */     Assert.assertTrue(this.userOperations.getAllUsers().contains(username));
/*  83 */     Assert.assertEquals(1L, this.userOperations.getAllUsers().size());
/*     */   }
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void insertUser_BadFirstname() {
/*  89 */     int idAddress = insertAddress();
/*     */     
/*  91 */     String username = "crno.dete";
/*  92 */     String firstName = "svetislav";
/*  93 */     String lastName = "Kisprdilov";
/*  94 */     String password = "Test_123";
/*     */     
/*  96 */     Assert.assertFalse(this.userOperations.insertUser(username, firstName, lastName, password, idAddress));
/*  97 */     Assert.assertFalse(this.userOperations.getAllUsers().contains(username));
/*  98 */     Assert.assertEquals(0L, this.userOperations.getAllUsers().size());
/*     */   }
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void insertUser_BadLastName() {
/* 104 */     int idAddress = insertAddress();
/*     */     
/* 106 */     String username = "crno.dete";
/* 107 */     String firstName = "Svetislav";
/* 108 */     String lastName = "kisprdilov";
/* 109 */     String password = "Test_123";
/*     */     
/* 111 */     Assert.assertFalse(this.userOperations.insertUser(username, firstName, lastName, password, idAddress));
/* 112 */     Assert.assertFalse(this.userOperations.getAllUsers().contains(username));
/* 113 */     Assert.assertEquals(0L, this.userOperations.getAllUsers().size());
/*     */   }
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void insertUser_BadAddress() {
/* 119 */     Random random = new Random();
/* 120 */     int idAddress = random.nextInt();
/*     */     
/* 122 */     String username = "crno.dete";
/* 123 */     String firstName = "Svetislav";
/* 124 */     String lastName = "Kisprdilov";
/* 125 */     String password = "Test_123";
/*     */     
/* 127 */     Assert.assertFalse(this.userOperations.insertUser(username, firstName, lastName, password, idAddress));
/* 128 */     Assert.assertFalse(this.userOperations.getAllUsers().contains(username));
/* 129 */     Assert.assertEquals(0L, this.userOperations.getAllUsers().size());
/*     */   }
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void insertUser_BadPassword() {
/* 135 */     int idAddress = insertAddress();
/*     */     
/* 137 */     String username = "crno.dete";
/* 138 */     String firstName = "Svetislav";
/* 139 */     String lastName = "Kisprdilov";
/*     */     
/* 141 */     String password1 = "test_123";
/* 142 */     String password2 = "Test123";
/* 143 */     String password3 = "Test_test";
/* 144 */     String password4 = "TEST_123";
/* 145 */     String password5 = "Test_1";
/*     */     
/* 147 */     Assert.assertFalse(this.userOperations.insertUser(username, firstName, lastName, password1, idAddress));
/* 148 */     Assert.assertFalse(this.userOperations.getAllUsers().contains(username));
/* 149 */     Assert.assertEquals(0L, this.userOperations.getAllUsers().size());
/*     */     
/* 151 */     Assert.assertFalse(this.userOperations.insertUser(username, firstName, lastName, password2, idAddress));
/* 152 */     Assert.assertFalse(this.userOperations.getAllUsers().contains(username));
/* 153 */     Assert.assertEquals(0L, this.userOperations.getAllUsers().size());
/*     */     
/* 155 */     Assert.assertFalse(this.userOperations.insertUser(username, firstName, lastName, password3, idAddress));
/* 156 */     Assert.assertFalse(this.userOperations.getAllUsers().contains(username));
/* 157 */     Assert.assertEquals(0L, this.userOperations.getAllUsers().size());
/*     */     
/* 159 */     Assert.assertFalse(this.userOperations.insertUser(username, firstName, lastName, password4, idAddress));
/* 160 */     Assert.assertFalse(this.userOperations.getAllUsers().contains(username));
/* 161 */     Assert.assertEquals(0L, this.userOperations.getAllUsers().size());
/*     */     
/* 163 */     Assert.assertFalse(this.userOperations.insertUser(username, firstName, lastName, password5, idAddress));
/* 164 */     Assert.assertFalse(this.userOperations.getAllUsers().contains(username));
/* 165 */     Assert.assertEquals(0L, this.userOperations.getAllUsers().size());
/*     */   }
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void declareAdmin() {
/* 171 */     int idAddress = insertAddress();
/*     */     
/* 173 */     String username = "crno.dete";
/* 174 */     String firstName = "Svetislav";
/* 175 */     String lastName = "Kisprdilov";
/* 176 */     String password = "Test_123";
/*     */     
/* 178 */     Assert.assertTrue(this.userOperations.insertUser(username, firstName, lastName, password, idAddress));
/* 179 */     Assert.assertTrue(this.userOperations.declareAdmin(username));
/*     */   }
/*     */   
/*     */   @Test
/*     */   public void declareAdmin_AlreadyAdmin() {
/* 184 */     int idAddress = insertAddress();
/*     */     
/* 186 */     String username = "crno.dete";
/* 187 */     String firstName = "Svetislav";
/* 188 */     String lastName = "Kisprdilov";
/* 189 */     String password = "Test_123";
/*     */     
/* 191 */     Assert.assertTrue(this.userOperations.insertUser(username, firstName, lastName, password, idAddress));
/* 192 */     Assert.assertTrue(this.userOperations.declareAdmin(username));
/* 193 */     Assert.assertFalse(this.userOperations.declareAdmin(username));
/*     */   }
/*     */   
/*     */   @Test
/*     */   public void declareAdmin_NoSuchUser() {
/* 198 */     int idAddress = insertAddress();
/*     */     
/* 200 */     String username = "crno.dete";
/* 201 */     String firstName = "Svetislav";
/* 202 */     String lastName = "Kisprdilov";
/* 203 */     String password = "Test_123";
/*     */     
/* 205 */     Assert.assertTrue(this.userOperations.insertUser(username, firstName, lastName, password, idAddress));
/*     */     
/* 207 */     String username2 = "crno.dete.2";
/* 208 */     Assert.assertFalse(this.userOperations.declareAdmin(username2));
/*     */   }
/*     */   
/*     */   @Test
/*     */   public void getSentPackages_userExisting() {
/* 213 */     int idAddress = insertAddress();
/*     */     
/* 215 */     String username = "crno.dete";
/* 216 */     String firstName = "Svetislav";
/* 217 */     String lastName = "Kisprdilov";
/* 218 */     String password = "Test_123";
/*     */     
/* 220 */     Assert.assertTrue(this.userOperations.insertUser(username, firstName, lastName, password, idAddress));
/* 221 */     Assert.assertEquals(0L, this.userOperations.getSentPackages(new String[] { username }));
/*     */   }
/*     */   
/*     */   @Test
/*     */   public void getSentPackages_userNotExisting() {
/* 226 */     String username = "crno.dete";
/* 227 */     Assert.assertEquals(-1L, this.userOperations.getSentPackages(new String[] { username }));
/*     */   }
/*     */   
/*     */   @Test
/*     */   public void deleteUsers() {
/* 232 */     int idAddress = insertAddress();
/*     */     
/* 234 */     String username1 = "crno.dete1";
/* 235 */     String username2 = "crno.dete2";
/* 236 */     String username3 = "crno.dete3";
/* 237 */     String firstName = "Svetislav";
/* 238 */     String lastName = "Kisprdilov";
/* 239 */     String password = "Test_123";
/*     */     
/* 241 */     Assert.assertTrue(this.userOperations.insertUser(username1, firstName, lastName, password, idAddress));
/* 242 */     Assert.assertTrue(this.userOperations.insertUser(username2, firstName, lastName, password, idAddress));
/* 243 */     Assert.assertTrue(this.userOperations.insertUser(username3, firstName, lastName, password, idAddress));
/*     */     
/* 245 */     Assert.assertEquals(2L, this.userOperations.deleteUsers(new String[] { username1, username2 }));
/*     */     
/* 247 */     Assert.assertEquals(1L, this.userOperations.getAllUsers().size());
/* 248 */     Assert.assertFalse(this.userOperations.getAllUsers().contains(username1));
/* 249 */     Assert.assertFalse(this.userOperations.getAllUsers().contains(username2));
/* 250 */     Assert.assertTrue(this.userOperations.getAllUsers().contains(username3));
/*     */   }
/*     */   
/*     */   @Test
/*     */   public void getAllUsers() {
/* 255 */     int idAddress = insertAddress();
/*     */     
/* 257 */     String username1 = "crno.dete1";
/* 258 */     String username2 = "crno.dete2";
/* 259 */     String firstName = "Svetislav";
/* 260 */     String lastName = "Kisprdilov";
/* 261 */     String password = "Test_123";
/*     */     
/* 263 */     Assert.assertTrue(this.userOperations.insertUser(username1, firstName, lastName, password, idAddress));
/* 264 */     Assert.assertTrue(this.userOperations.insertUser(username2, firstName, lastName, password, idAddress));
/*     */     
/* 266 */     Assert.assertEquals(2L, this.userOperations.getAllUsers().size());
/* 267 */     Assert.assertTrue(this.userOperations.getAllUsers().contains(username1));
/* 268 */     Assert.assertTrue(this.userOperations.getAllUsers().contains(username2));
/*     */   }
/*     */ }


/* Location:              C:\Users\vulov\Downloads\SAB_projekat_2122\SAB_projekat_2122\SAB_projekat_2122.jar!\rs\etf\sab\tests\UserOperationsTest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */