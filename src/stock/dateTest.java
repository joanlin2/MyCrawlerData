package stock;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Joan on 2017/9/5.
 */
public class dateTest {

    public static void main(String[] args) {
        Calendar startDate = new GregorianCalendar(2017, Calendar.SEPTEMBER, 1);
        Date currentDate = new Date();
        GregorianCalendar endDate = new GregorianCalendar();
        endDate.setTime(currentDate);
        int days = (int)(endDate.getTimeInMillis()-startDate.getTimeInMillis())/ (1000 * 60 * 60 * 24);

        System.out.println("compareTo="+ (endDate.getTimeInMillis()-startDate.getTimeInMillis())/ (1000 * 60 * 60 * 24));
        for(int i =0; i < days; i++){
            startDate.add(GregorianCalendar.DATE, 1); //日期遞增

            System.out.println(startDate.get(Calendar.YEAR) + "/"+ (startDate.get(Calendar.MONTH )+1)+ "/"+startDate.get(Calendar.DATE));
//            System.out.println(endDate.get(Calendar.MONTH )+1);
//            System.out.println(endDate.get(Calendar.DATE));


        }
    }
}