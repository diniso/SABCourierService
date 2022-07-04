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
/*     */ 
/*     */ public class AddressOperationsTest
/*     */ {
/*     */   private GeneralOperations generalOperations;
/*     */   private AddressOperations addressOperations;
/*     */   
/*     */   @Before
/*     */   public void setUp() {
/*  19 */     this.testHandler = TestHandler.getInstance();
/*  20 */     Assert.assertNotNull(this.testHandler);
/*     */     
/*  22 */     this.cityOperations = this.testHandler.getCityOperations();
/*  23 */     Assert.assertNotNull(this.cityOperations);
/*     */     
/*  25 */     this.addressOperations = this.testHandler.getAddressOperations();
/*  26 */     Assert.assertNotNull(this.addressOperations);
/*     */     
/*  28 */     this.generalOperations = this.testHandler.getGeneralOperations();
/*  29 */     Assert.assertNotNull(this.generalOperations);
/*     */     
/*  31 */     this.generalOperations.eraseAll();
/*     */   }
/*     */   private CityOperations cityOperations; private TestHandler testHandler;
/*     */   @After
/*     */   public void tearDown() {
/*  36 */     this.generalOperations.eraseAll();
/*     */   }
/*     */   
/*     */   @Test
/*     */   public void insertAddress_ExistingCity() {
/*  41 */     String streetOne = "Bulevar kralja Aleksandra";
/*  42 */     int numberOne = 73;
/*     */     
/*  44 */     int idCity = this.cityOperations.insertCity("Belgrade", "11000");
/*  45 */     Assert.assertNotEquals(-1L, idCity);
/*     */     
/*  47 */     int idAddress = this.addressOperations.insertAddress(streetOne, numberOne, idCity, 10, 10);
/*     */     
/*  49 */     Assert.assertNotEquals(-1L, idAddress);
/*  50 */     Assert.assertTrue(this.addressOperations.getAllAddresses().contains(Integer.valueOf(idAddress)));
/*     */   }
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void insertAddress_MissingCity() {
/*  56 */     String streetOne = "Bulevar kralja Aleksandra";
/*  57 */     int numberOne = 73;
/*     */     
/*  59 */     Random random = new Random();
/*  60 */     int idCity = random.nextInt();
/*     */     
/*  62 */     int idAddress = this.addressOperations.insertAddress(streetOne, numberOne, idCity, 10, 10);
/*     */     
/*  64 */     Assert.assertEquals(-1L, idAddress);
/*  65 */     Assert.assertEquals(0L, this.addressOperations.getAllAddresses().size());
/*     */   }
/*     */   
/*     */   @Test
/*     */   public void deleteAddress_existing() {
/*  70 */     String streetOne = "Bulevar kralja Aleksandra";
/*  71 */     int numberOne = 73;
/*     */     
/*  73 */     int idCity = this.cityOperations.insertCity("Belgrade", "11000");
/*  74 */     Assert.assertNotEquals(-1L, idCity);
/*     */     
/*  76 */     int idAddress = this.addressOperations.insertAddress(streetOne, numberOne, idCity, 10, 10);
/*     */     
/*  78 */     Assert.assertEquals(1L, this.addressOperations.getAllAddresses().size());
/*  79 */     Assert.assertTrue(this.addressOperations.deleteAdress(idAddress));
/*  80 */     Assert.assertEquals(0L, this.addressOperations.getAllAddresses().size());
/*     */   }
/*     */   
/*     */   @Test
/*     */   public void deleteAddress_missing() {
/*  85 */     int idCity = this.cityOperations.insertCity("Belgrade", "11000");
/*  86 */     Assert.assertNotEquals(-1L, idCity);
/*  87 */     Assert.assertTrue(this.cityOperations.getAllCities().contains(new Integer(idCity)));
/*     */     
/*  89 */     Random random = new Random();
/*  90 */     int idAddress = random.nextInt();
/*  91 */     Assert.assertFalse(this.addressOperations.deleteAdress(idAddress));
/*     */   }
/*     */   
/*     */   @Test
/*     */   public void deleteAddresses_multiple_existing() {
/*  96 */     String streetOne = "Bulevar kralja Aleksandra";
/*  97 */     int numberOne = 73;
/*  98 */     int idCity = this.cityOperations.insertCity("Belgrade", "11000");
/*  99 */     Assert.assertNotEquals(-1L, idCity);
/*     */     
/* 101 */     int idAddressOne = this.addressOperations.insertAddress(streetOne, numberOne, idCity, 10, 10);
/* 102 */     int idAddressTwo = this.addressOperations.insertAddress(streetOne, numberOne, idCity, 100, 100);
/*     */     
/* 104 */     Assert.assertEquals(2L, this.addressOperations.getAllAddresses().size());
/* 105 */     Assert.assertEquals(2L, this.addressOperations.deleteAddresses(streetOne, numberOne));
/* 106 */     Assert.assertEquals(0L, this.addressOperations.getAllAddresses().size());
/*     */   }
/*     */   
/*     */   @Test
/*     */   public void deleteAddresses_multiple_missing() {
/* 111 */     String streetOne = "Bulevar kralja Aleksandra";
/* 112 */     int numberOne = 73;
/*     */     
/* 114 */     Assert.assertEquals(0L, this.addressOperations.deleteAddresses(streetOne, numberOne));
/* 115 */     Assert.assertEquals(0L, this.addressOperations.getAllAddresses().size());
/*     */   }
/*     */   
/*     */   @Test
/*     */   public void getAllAddressesFromCity() {
/* 120 */     String streetOne = "Bulevar kralja Aleksandra";
/* 121 */     int numberOne = 73;
/* 122 */     String streetTwo = "Kraljice Natalije";
/* 123 */     int numberTwo = 37;
/*     */     
/* 125 */     int idCity = this.cityOperations.insertCity("Belgrade", "11000");
/* 126 */     Assert.assertNotEquals(-1L, idCity);
/*     */     
/* 128 */     int idAddressOne = this.addressOperations.insertAddress(streetOne, numberOne, idCity, 10, 10);
/* 129 */     int idAddressTwo = this.addressOperations.insertAddress(streetTwo, numberTwo, idCity, 100, 100);
/*     */     
/* 131 */     Assert.assertNotEquals(-1L, idAddressOne);
/* 132 */     Assert.assertNotEquals(-1L, idAddressTwo);
/*     */     
/* 134 */     Assert.assertEquals(2L, this.addressOperations.getAllAddressesFromCity(idCity).size());
/* 135 */     Assert.assertNull(this.addressOperations.getAllAddressesFromCity(idCity + 1));
/* 136 */     Assert.assertTrue(this.addressOperations.getAllAddressesFromCity(idCity).contains(Integer.valueOf(idAddressOne)));
/* 137 */     Assert.assertTrue(this.addressOperations.getAllAddressesFromCity(idCity).contains(Integer.valueOf(idAddressTwo)));
/*     */   }
/*     */   
/*     */   @Test
/*     */   public void deleteAllAddressesFromCity() {
/* 142 */     String streetOne = "Bulevar kralja Aleksandra";
/* 143 */     int numberOne = 73;
/* 144 */     String streetTwo = "Kraljice Natalije";
/* 145 */     int numberTwo = 37;
/*     */     
/* 147 */     int idCity = this.cityOperations.insertCity("Belgrade", "11000");
/* 148 */     Assert.assertNotEquals(-1L, idCity);
/*     */     
/* 150 */     int idAddressOne = this.addressOperations.insertAddress(streetOne, numberOne, idCity, 10, 10);
/* 151 */     int idAddressTwo = this.addressOperations.insertAddress(streetTwo, numberTwo, idCity, 100, 100);
/*     */     
/* 153 */     Assert.assertNotEquals(-1L, idAddressOne);
/* 154 */     Assert.assertNotEquals(-1L, idAddressTwo);
/*     */     
/* 156 */     Assert.assertEquals(0L, this.addressOperations.deleteAllAddressesFromCity(idCity + 1));
/* 157 */     Assert.assertEquals(2L, this.addressOperations.getAllAddresses().size());
/*     */     
/* 159 */     Assert.assertEquals(2L, this.addressOperations.deleteAllAddressesFromCity(idCity));
/* 160 */     Assert.assertEquals(0L, this.addressOperations.getAllAddresses().size());
/*     */   }
/*     */ }


/* Location:              C:\Users\vulov\Downloads\SAB_projekat_2122\SAB_projekat_2122\SAB_projekat_2122.jar!\rs\etf\sab\tests\AddressOperationsTest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */