package rs.etf.sab.operations;

import java.util.List;
import javax.validation.constraints.NotNull;

public interface CityOperations {
  int insertCity(@NotNull String paramString1, String paramString2);
  
  int deleteCity(@NotNull String... paramVarArgs);
  
  boolean deleteCity(int paramInt);
  
  List<Integer> getAllCities();
}


/* Location:              C:\Users\vulov\Downloads\SAB_projekat_2122\SAB_projekat_2122\SAB_projekat_2122.jar!\rs\etf\sab\operations\CityOperations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */