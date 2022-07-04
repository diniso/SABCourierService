/*    */ package rs.etf.sab.tests;
/*    */ 
/*    */ import java.math.BigDecimal;
/*    */ 
/*    */ public class Util {
/*    */   static double euclidean(int x1, int y1, int x2, int y2) {
/*  7 */     return Math.sqrt(((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)));
/*    */   }
/*    */ 
/*    */   
/*    */   static BigDecimal getPackagePrice(int type, BigDecimal weight, double distance) {
/* 12 */     switch (type) {
/*    */       case 0:
/* 14 */         return new BigDecimal(115.0D * distance);
/*    */       case 1:
/* 16 */         return new BigDecimal((175.0D + weight.doubleValue() * 100.0D) * distance);
/*    */       case 2:
/* 18 */         return new BigDecimal((250.0D + weight.doubleValue() * 100.0D) * distance);
/*    */       case 3:
/* 20 */         return new BigDecimal((350.0D + weight.doubleValue() * 500.0D) * distance);
/*    */     } 
/* 22 */     return null;
/*    */   }
/*    */   
/*    */   static double getDistance(Pair<Integer, Integer>... addresses) {
/* 26 */     double distance = 0.0D;
/* 27 */     for (int i = 1; i < addresses.length; i++)
/* 28 */       distance += euclidean(((Integer)addresses[i - 1].getKey()).intValue(), ((Integer)addresses[i - 1].getValue()).intValue(), ((Integer)addresses[i]
/* 29 */           .getKey()).intValue(), ((Integer)addresses[i].getValue()).intValue()); 
/* 30 */     return distance;
/*    */   }
/*    */ }


/* Location:              C:\Users\vulov\Downloads\SAB_projekat_2122\SAB_projekat_2122\SAB_projekat_2122.jar!\rs\etf\sab\tests\Util.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */