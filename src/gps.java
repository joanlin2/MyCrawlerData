import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Student on 2017/9/9.
 */
public class gps {
    public static void main(String[] args) {
        String outfile = "datagps";
        String mystr;
        String[] clst = null;
        String strLine ="";
        Boolean first_record = true;
        float clat = 0.0f ;
        float clong = 0.0f ;
        float cspeed = 0.0f ;
        float lastlat = 0.0f ;
        float lastlong = 0.0f ;
        float lastspeed = 0.0f ;
        int ts = 0;




        if(args.length!= 2){
            System.out.println("Need two args: Usage & filename.");
            return;
        }

        try {
            BufferedReader br1 = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream("/2016-09-16 17-56-03_0000.log"), "Big5"));
            System.out.println("starting");
            while ((mystr = br1.readLine()) != null) {//迴圈
                //set previous lat, long and speed
                lastlat=clat;
                lastlong=clong;
                lastspeed=cspeed;

                //split data into list
                clst = mystr.split(",");

                //# set current lat, logn and speed
                clat = Float.parseFloat(clst[0]);
                clong = Float.parseFloat(clst[1]);
                cspeed = Float.parseFloat(clst[2]);

                //do not process first record
                if (first_record) {
                    first_record=false;
                    continue;
                }

                //convert datetime to timestamp
                //ts=int(datetime.datetime.strptime(clst[0],"%Y-%m-%d %H:%M:%S.%f").timestamp())
               // ts= new Timestamp(int year, int month, int date, int hour, int minute, int second, int nano);
//                Calendar ca = new GregorianCalendar(2016, 9,16,17,56,03);
                Integer YYYY = Integer.valueOf(clst[0].substring(0,5));
                Integer MM = Integer.valueOf(clst[0].substring(0,5));
                Integer DD = Integer.valueOf(clst[0].substring(0,5));
                Integer HH = Integer.valueOf(clst[0].substring(0,5));
                Integer MM = Integer.valueOf(clst[0].substring(0,5));
                Calendar ca = new GregorianCalendar(, 9,16,17,56,03);
                 ts = (int) ca.getTimeInMillis() / (1000 );
//                2016-09-16 17:56:03.006

            }



        /*

   # do not process first record
   if (first_record):
      first_record=False
      continue

   # convert datetime to timestamp
   ts=int(datetime.datetime.strptime(clst[0],"%Y-%m-%d %H:%M:%S.%f").timestamp())

   str2=""
   for i in range(100):
      sT=ts*100+i
      sLat=(clat-lastlat)*0.01*i+lastlat
      sLong=(clong-lastlong)*0.01*i+lastlong
      sSpeed=(cspeed-lastspeed)*0.001*60*60*0.01*i+(lastspeed*0.001*60*60)

      str2+="{0:d}\n{1:.20f}\n{2:.20f}\n{3:.20f}\n".format(sT,sLat,sLong,sSpeed)
#      str3="{0:d}\n{1:.20f}\n{2:.20f}\n{3:.20f}\n".format(sT,sLat,sLong,sSpeed)
#      str2+=str3
   fout.write(str2)
   print(".",end='')

print("finished")
         */



        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
