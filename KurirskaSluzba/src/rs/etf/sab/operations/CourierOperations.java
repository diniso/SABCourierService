package rs.etf.sab.operations;

import java.math.BigDecimal;
import java.util.List;
import javax.validation.constraints.NotNull;

public interface CourierOperations {
  boolean insertCourier(@NotNull String paramString1, @NotNull String paramString2);
  
  boolean deleteCourier(@NotNull String paramString);
  
  List<String> getCouriersWithStatus(int paramInt);
  
  List<String> getAllCouriers();
  
  BigDecimal getAverageCourierProfit(int paramInt);
}


/* Location:              C:\Users\vulov\Downloads\SAB_projekat_2122\SAB_projekat_2122\SAB_projekat_2122.jar!\rs\etf\sab\operations\CourierOperations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */