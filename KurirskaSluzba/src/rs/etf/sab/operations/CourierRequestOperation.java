package rs.etf.sab.operations;

import java.util.List;
import javax.validation.constraints.NotNull;

public interface CourierRequestOperation {
  boolean insertCourierRequest(@NotNull String paramString1, @NotNull String paramString2);
  
  boolean deleteCourierRequest(@NotNull String paramString);
  
  boolean changeDriverLicenceNumberInCourierRequest(@NotNull String paramString1, @NotNull String paramString2);
  
  List<String> getAllCourierRequests();
  
  boolean grantRequest(@NotNull String paramString);
}


/* Location:              C:\Users\vulov\Downloads\SAB_projekat_2122\SAB_projekat_2122\SAB_projekat_2122.jar!\rs\etf\sab\operations\CourierRequestOperation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */