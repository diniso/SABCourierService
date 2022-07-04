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
/*     */ import rs.etf.sab.operations.StockroomOperations;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StockroomOperationsTest
/*     */ {
/*     */   private TestHandler testHandler;
/*     */   private GeneralOperations generalOperations;
/*     */   private CityOperations cityOperations;
/*     */   private AddressOperations addressOperations;
/*     */   private StockroomOperations stockroomOperations;
/*     */   
/*     */   @Before
/*     */   public void setUp() {
/*  25 */     this.testHandler = TestHandler.getInstance();
/*  26 */     Assert.assertNotNull(this.testHandler);
/*     */     
/*  28 */     this.cityOperations = this.testHandler.getCityOperations();
/*  29 */     Assert.assertNotNull(this.cityOperations);
/*     */     
/*  31 */     this.addressOperations = this.testHandler.getAddressOperations();
/*  32 */     Assert.assertNotNull(this.addressOperations);
/*     */     
/*  34 */     this.stockroomOperations = this.testHandler.getStockroomOperations();
/*  35 */     Assert.assertNotNull(this.stockroomOperations);
/*     */     
/*  37 */     this.generalOperations = this.testHandler.getGeneralOperations();
/*  38 */     Assert.assertNotNull(this.generalOperations);
/*     */     
/*  40 */     this.generalOperations.eraseAll();
/*     */   }
/*     */   
/*     */   @After
/*     */   public void tearDown() {
/*  45 */     this.generalOperations.eraseAll();
/*     */   }
/*     */   
/*     */   int insertAddress() {
/*  49 */     String street = "Bulevar kralja Aleksandra";
/*  50 */     int number = 73;
/*     */     
/*  52 */     int idCity = this.cityOperations.insertCity("Belgrade", "11000");
/*  53 */     Assert.assertNotEquals(-1L, idCity);
/*     */     
/*  55 */     int idAddress = this.addressOperations.insertAddress(street, number, idCity, 10, 10);
/*  56 */     Assert.assertNotEquals(-1L, idAddress);
/*  57 */     Assert.assertEquals(1L, this.addressOperations.getAllAddresses().size());
/*     */     
/*  59 */     return idAddress;
/*     */   }
/*     */   
/*     */   int insertAddress_SameCity() {
/*  63 */     String street = "Kraljice Natalije";
/*  64 */     int number = 37;
/*     */     
/*  66 */     Assert.assertEquals(1L, this.cityOperations.getAllCities().size());
/*     */     
/*  68 */     int idCity = ((Integer)this.cityOperations.getAllCities().get(0)).intValue();
/*  69 */     Assert.assertNotEquals(-1L, idCity);
/*     */     
/*  71 */     int idAddress = this.addressOperations.insertAddress(street, number, idCity, 30, 30);
/*  72 */     Assert.assertNotEquals(-1L, idAddress);
/*     */     
/*  74 */     return idAddress;
/*     */   }
/*     */   
/*     */   int insertAddress_DifferentCity() {
/*  78 */     String street = "Vojvode Stepe";
/*  79 */     int number = 73;
/*     */     
/*  81 */     int idCity = this.cityOperations.insertCity("Nis", "700000");
/*  82 */     Assert.assertNotEquals(-1L, idCity);
/*     */     
/*  84 */     int idAddress = this.addressOperations.insertAddress(street, number, idCity, 100, 100);
/*  85 */     Assert.assertNotEquals(-1L, idAddress);
/*     */     
/*  87 */     return idAddress;
/*     */   }
/*     */   
/*     */   @Test
/*     */   public void insertStockroom_OnlyOne() {
/*  92 */     int idAddress = insertAddress();
/*     */     
/*  94 */     int rowId = this.stockroomOperations.insertStockroom(idAddress);
/*     */     
/*  96 */     Assert.assertNotEquals(-1L, rowId);
/*  97 */     Assert.assertEquals(1L, this.stockroomOperations.getAllStockrooms().size());
/*  98 */     Assert.assertTrue(this.stockroomOperations.getAllStockrooms().contains(Integer.valueOf(rowId)));
/*     */   }
/*     */   
/*     */   @Test
/*     */   public void insertStockrooms_SameCity() {
/* 103 */     int idAddress = insertAddress();
/* 104 */     int idAddress2 = insertAddress_SameCity();
/*     */     
/* 106 */     int rowId = this.stockroomOperations.insertStockroom(idAddress);
/* 107 */     Assert.assertNotEquals(-1L, rowId);
/* 108 */     Assert.assertEquals(-1L, this.stockroomOperations.insertStockroom(idAddress2));
/*     */     
/* 110 */     Assert.assertEquals(1L, this.stockroomOperations.getAllStockrooms().size());
/* 111 */     Assert.assertTrue(this.stockroomOperations.getAllStockrooms().contains(Integer.valueOf(rowId)));
/*     */   }
/*     */   
/*     */   @Test
/*     */   public void insertStockrooms_DifferentCity() {
/* 116 */     int idAddress = insertAddress();
/* 117 */     int idAddress2 = insertAddress_DifferentCity();
/*     */     
/* 119 */     int rowId = this.stockroomOperations.insertStockroom(idAddress);
/* 120 */     Assert.assertNotEquals(-1L, rowId);
/* 121 */     int rowId2 = this.stockroomOperations.insertStockroom(idAddress2);
/* 122 */     Assert.assertNotEquals(-1L, rowId);
/*     */     
/* 124 */     Assert.assertEquals(2L, this.stockroomOperations.getAllStockrooms().size());
/* 125 */     Assert.assertTrue(this.stockroomOperations.getAllStockrooms().contains(Integer.valueOf(rowId)));
/* 126 */     Assert.assertTrue(this.stockroomOperations.getAllStockrooms().contains(Integer.valueOf(rowId2)));
/*     */   }
/*     */   
/*     */   @Test
/*     */   public void deleteStockroom() {
/* 131 */     int idAddress = insertAddress();
/*     */     
/* 133 */     int rowId = this.stockroomOperations.insertStockroom(idAddress);
/*     */     
/* 135 */     Assert.assertNotEquals(-1L, rowId);
/* 136 */     Assert.assertEquals(1L, this.stockroomOperations.getAllStockrooms().size());
/* 137 */     Assert.assertTrue(this.stockroomOperations.getAllStockrooms().contains(Integer.valueOf(rowId)));
/*     */     
/* 139 */     Assert.assertTrue(this.stockroomOperations.deleteStockroom(rowId));
/* 140 */     Assert.assertEquals(0L, this.stockroomOperations.getAllStockrooms().size());
/* 141 */     Assert.assertFalse(this.stockroomOperations.getAllStockrooms().contains(Integer.valueOf(rowId)));
/*     */   }
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void deleteStockroom_NoStockroom() {
/* 147 */     Random random = new Random();
/* 148 */     int rowId = random.nextInt();
/*     */     
/* 150 */     Assert.assertFalse(this.stockroomOperations.deleteStockroom(rowId));
/* 151 */     Assert.assertEquals(0L, this.stockroomOperations.getAllStockrooms().size());
/*     */   }
/*     */   
/*     */   @Test
/*     */   public void deleteStockroomFromCity() {
/* 156 */     int idAddress = insertAddress();
/*     */     
/* 158 */     int rowId = this.stockroomOperations.insertStockroom(idAddress);
/* 159 */     Assert.assertNotEquals(-1L, rowId);
/* 160 */     Assert.assertEquals(1L, this.stockroomOperations.getAllStockrooms().size());
/* 161 */     Assert.assertTrue(this.stockroomOperations.getAllStockrooms().contains(Integer.valueOf(rowId)));
/*     */     
/* 163 */     Assert.assertEquals(1L, this.cityOperations.getAllCities().size());
/* 164 */     int idCity = ((Integer)this.cityOperations.getAllCities().get(0)).intValue();
/*     */     
/* 166 */     Assert.assertEquals(rowId, this.stockroomOperations.deleteStockroomFromCity(idCity));
/* 167 */     Assert.assertEquals(0L, this.stockroomOperations.getAllStockrooms().size());
/* 168 */     Assert.assertFalse(this.stockroomOperations.getAllStockrooms().contains(Integer.valueOf(rowId)));
/*     */   }
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void deleteStockroomFromCity_NoCity() {
/* 174 */     Random random = new Random();
/* 175 */     int rowId = random.nextInt();
/*     */     
/* 177 */     Assert.assertEquals(-1L, this.stockroomOperations.deleteStockroomFromCity(rowId));
/* 178 */     Assert.assertEquals(0L, this.stockroomOperations.getAllStockrooms().size());
/*     */   }
/*     */   
/*     */   @Test
/*     */   public void deleteStockroomFromCity_NoStockroom() {
/* 183 */     int idCity = this.cityOperations.insertCity("Belgrade", "11000");
/* 184 */     Assert.assertNotEquals(-1L, idCity);
/*     */     
/* 186 */     Assert.assertEquals(-1L, this.stockroomOperations.deleteStockroomFromCity(idCity));
/* 187 */     Assert.assertEquals(0L, this.stockroomOperations.getAllStockrooms().size());
/*     */   }
/*     */ }


/* Location:              C:\Users\vulov\Downloads\SAB_projekat_2122\SAB_projekat_2122\SAB_projekat_2122.jar!\rs\etf\sab\tests\StockroomOperationsTest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */