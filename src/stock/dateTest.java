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
        java.util.Date da = new java.util.Date();
        long longToday = da.getTime();
        java.sql.Date ds2 = new java.sql.Date(longToday);
        System.out.println("longToday=" + longToday);
        System.out.println("ds2=" + ds2);



        //test timezon
        //        ts=int(datetime.datetime.strptime(clst[0],"%Y-%m-%d %H:%M:%S.%f").timestamp())

//        SimpleDateFormat formatter;
//        formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        Calendar ca = new GregorianCalendar(2016, 9,16,17,56,03);
        int ts = (int) ca.getTimeInMillis() / (1000 );

//        System.out.println("ctime=" + ctime);




    }
}