/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bb;

/**
 *
 * @author Abirami
 */
import java.time.LocalDate;

public class Days {

  public static void main(String[] args) {

      LocalDate currentDate = LocalDate.now();
      LocalDate currentDateMinus30Days = currentDate.minusDays(30);

      // 2021-03-26
      System.out.println("currentDate: " + currentDate);

      // 2020-09-26
      System.out.println("currentDateMinus30Days : " + currentDateMinus30Days);

     

  }
}
