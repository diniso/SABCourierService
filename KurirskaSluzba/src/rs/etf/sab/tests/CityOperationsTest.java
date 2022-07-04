/*     */ package rs.etf.sab.tests;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.junit.After;
/*     */ import org.junit.Assert;
/*     */ import org.junit.Before;
/*     */ import org.junit.Test;
/*     */ import rs.etf.sab.operations.CityOperations;
/*     */ import rs.etf.sab.operations.GeneralOperations;
/*     */ 
/*     */ 
/*     */ public class CityOperationsTest
/*     */ {
/*     */   private TestHandler testHandler;
/*     */   private GeneralOperations generalOperations;
/*     */   private CityOperations cityOperations;
/*     */   
/*     */   @Before
/*     */   public void setUp() {
/*  20 */     this.testHandler = TestHandler.getInstance();
/*  21 */     Assert.assertNotNull(this.testHandler);
/*     */     
/*  23 */     this.cityOperations = this.testHandler.getCityOperations();
/*  24 */     Assert.assertNotNull(this.cityOperations);
/*     */     
/*  26 */     this.generalOperations = this.testHandler.getGeneralOperations();
/*  27 */     Assert.assertNotNull(this.generalOperations);
/*     */     
/*  29 */     this.generalOperations.eraseAll();
/*     */   }
/*     */   
/*     */   @After
/*     */   public void tearDown() {
/*  34 */     this.generalOperations.eraseAll();
/*     */   }
/*     */   
/*     */   @Test
/*     */   public void insertCity_OnlyOne() {
/*  39 */     String name = "Tokyo";
/*  40 */     String postalCode = "100";
/*     */     
/*  42 */     int rowId = this.cityOperations.insertCity(name, postalCode);
/*     */     
/*  44 */     Assert.assertNotEquals(-1L, rowId);
/*  45 */     Assert.assertEquals(1L, this.cityOperations.getAllCities().size());
/*  46 */     Assert.assertTrue(this.cityOperations.getAllCities().contains(Integer.valueOf(rowId)));
/*     */   }
/*     */   
/*     */   @Test
/*     */   public void insertCity_TwoCities_SameBothNameAndPostalCode() {
/*  51 */     String name = "Tokyo";
/*  52 */     String postalCode = "100";
/*     */     
/*  54 */     int rowIdValid = this.cityOperations.insertCity(name, postalCode);
/*  55 */     int rowIdInvalid = this.cityOperations.insertCity(name, postalCode);
/*     */     
/*  57 */     Assert.assertEquals(-1L, rowIdInvalid);
/*  58 */     Assert.assertEquals(1L, this.cityOperations.getAllCities().size());
/*  59 */     Assert.assertTrue(this.cityOperations.getAllCities().contains(Integer.valueOf(rowIdValid)));
/*     */   }
/*     */   
/*     */   @Test
/*     */   public void insertCity_TwoCities_SameName() {
/*  64 */     String name = "Tokyo";
/*  65 */     String postalCode1 = "100";
/*  66 */     String postalCode2 = "1020";
/*     */     
/*  68 */     int rowId1 = this.cityOperations.insertCity(name, postalCode1);
/*  69 */     int rowId2 = this.cityOperations.insertCity(name, postalCode2);
/*     */     
/*  71 */     Assert.assertEquals(2L, this.cityOperations.getAllCities().size());
/*  72 */     Assert.assertTrue(this.cityOperations.getAllCities().contains(Integer.valueOf(rowId1)));
/*  73 */     Assert.assertTrue(this.cityOperations.getAllCities().contains(Integer.valueOf(rowId2)));
/*     */   }
/*     */   
/*     */   @Test
/*     */   public void insertCity_TwoCities_SamePostalCode() {
/*  78 */     String name1 = "Tokyo";
/*  79 */     String name2 = "Beijing";
/*  80 */     String postalCode = "100";
/*     */     
/*  82 */     int rowIdValid = this.cityOperations.insertCity(name1, postalCode);
/*  83 */     int rowIdInvalid = this.cityOperations.insertCity(name2, postalCode);
/*     */     
/*  85 */     Assert.assertEquals(-1L, rowIdInvalid);
/*  86 */     Assert.assertEquals(1L, this.cityOperations.getAllCities().size());
/*  87 */     Assert.assertTrue(this.cityOperations.getAllCities().contains(Integer.valueOf(rowIdValid)));
/*     */   }
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void insertCity_MultipleCities() {
/*  93 */     String name1 = "Tokyo";
/*  94 */     String name2 = "Beijing";
/*  95 */     String postalCode1 = "100";
/*  96 */     String postalCode2 = "065001";
/*     */     
/*  98 */     int rowId1 = this.cityOperations.insertCity(name1, postalCode1);
/*  99 */     int rowId2 = this.cityOperations.insertCity(name2, postalCode2);
/*     */     
/* 101 */     Assert.assertEquals(2L, this.cityOperations.getAllCities().size());
/* 102 */     Assert.assertTrue(this.cityOperations.getAllCities().contains(Integer.valueOf(rowId1)));
/* 103 */     Assert.assertTrue(this.cityOperations.getAllCities().contains(Integer.valueOf(rowId2)));
/*     */   }
/*     */   
/*     */   @Test
/*     */   public void deleteCity_WithId_OnlyOne() {
/* 108 */     String name = "Beijing";
/* 109 */     String postalCode = "065001";
/*     */     
/* 111 */     int rowId = this.cityOperations.insertCity(name, postalCode);
/*     */     
/* 113 */     Assert.assertNotEquals(-1L, rowId);
/*     */     
/* 115 */     Assert.assertTrue(this.cityOperations.deleteCity(rowId));
/* 116 */     Assert.assertEquals(0L, this.cityOperations.getAllCities().size());
/*     */   }
/*     */   
/*     */   @Test
/*     */   public void deleteCity_WithId_OnlyOne_NotExisting() {
/* 121 */     Random random = new Random();
/* 122 */     int rowId = random.nextInt();
/*     */     
/* 124 */     Assert.assertFalse(this.cityOperations.deleteCity(rowId));
/* 125 */     Assert.assertEquals(0L, this.cityOperations.getAllCities().size());
/*     */   }
/*     */   
/*     */   @Test
/*     */   public void deleteCity_WithName_One() {
/* 130 */     String name = "Beijing";
/* 131 */     String postalCode = "065001";
/*     */     
/* 133 */     int rowId = this.cityOperations.insertCity(name, postalCode);
/*     */     
/* 135 */     Assert.assertNotEquals(-1L, rowId);
/* 136 */     Assert.assertEquals(1L, this.cityOperations.deleteCity(new String[] { name }));
/* 137 */     Assert.assertEquals(0L, this.cityOperations.getAllCities().size());
/*     */   }
/*     */   
/*     */   @Test
/*     */   public void deleteCity_WithName_MultipleCities() {
/* 142 */     String name1 = "Tokyo";
/* 143 */     String name2 = "Beijing";
/* 144 */     String postalCode1 = "100";
/* 145 */     String postalCode2 = "065001";
/*     */     
/* 147 */     int rowId1 = this.cityOperations.insertCity(name1, postalCode1);
/* 148 */     int rowId2 = this.cityOperations.insertCity(name2, postalCode2);
/*     */     
/* 150 */     Assert.assertEquals(2L, this.cityOperations.getAllCities().size());
/* 151 */     Assert.assertEquals(2L, this.cityOperations.deleteCity(new String[] { name1, name2 }));
/*     */   }
/*     */   
/*     */   @Test
/*     */   public void deleteCity_WithName_OnlyOne_NotExisting() {
/* 156 */     String name = "Tokyo";
/*     */     
/* 158 */     Assert.assertEquals(0L, this.cityOperations.deleteCity(new String[] { name }));
/* 159 */     Assert.assertEquals(0L, this.cityOperations.getAllCities().size());
/*     */   }
/*     */ }


/* Location:              C:\Users\vulov\Downloads\SAB_projekat_2122\SAB_projekat_2122\SAB_projekat_2122.jar!\rs\etf\sab\tests\CityOperationsTest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */