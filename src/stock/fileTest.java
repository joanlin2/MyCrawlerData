package stock;

import com.github.abola.crawler.CrawlerPack;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Joan on 2017/9/5.
 */
public class fileTest {


    public static void main(String[] args) {
        String YYYY ="", MM ="" , sockId ="", uri = "", strYYYYMM="";
        String taskID = "dlytrans";
        String[] arrLine = null;  //股票清單陣列
        String strLine ="";   //單行股票info
        String data;
        String csvString = "" , csvTitle ="";
        FileWriter fw1 = null;
        csvTitle = "股票代號,日期,成交千股,成交千元,開盤,最高,最低,收盤,漲跌,筆數";
        String[] YYYYMMDD = null;
        Integer cnt =0, stockCnt =0;
        String PATH = "D:\\workspace\\output\\";
        String OUTPATH = PATH + taskID + "\\";
        File directory = new File(String.valueOf(OUTPATH));
        if (!directory.exists()) {
            directory.mkdir();
        }

        //起迄日期設定
        Calendar initDate = new GregorianCalendar(2014, Calendar.JANUARY, 1);
        Calendar startDate = null;
        Calendar endDate = null;  //不指定結束日期就由getMonthNum()抓系統日
//        Calendar endDate = new GregorianCalendar(2017, Calendar.FEBRUARY, 1); //指定結束日期
        int mons = getMonthNum(initDate,endDate); //total month
        System.out.println("total mons ="+ mons);

        try {
            //原始檔案編碼
            BufferedReader br1 = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(PATH + "stocklist.csv"), "Big5"));

            for(int k =0; k <= mons; k++) { //月份遞減
                //重設初始日期(為了利用k,每次新日期都要先讀初始日期)
                startDate = new GregorianCalendar(initDate.get(Calendar.YEAR), initDate.get(Calendar.MONTH), 1);
//                    System.out.println("initDate="+initDate.get(Calendar.YEAR)+"-"+ (initDate.get(Calendar.MONTH)+1));
                stockCnt = 0;
                startDate.add(GregorianCalendar.MONTH, k); //月份遞增
                YYYY = String.format("%04d",startDate.get(Calendar.YEAR));
                MM = String.format("%02d",(startDate.get(Calendar.MONTH)+1 ) );
                strYYYYMM = YYYY + MM ;//reset
                System.out.println("strYYYYMM="+strYYYYMM);

                //重讀股票檔
                br1 = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(PATH + "stocklist.csv"), "Big5"));
//                    System.out.println("br1.length="+ br1.);
                while ((data = br1.readLine()) != null) {//股票迴圈
                    strLine = new String(data.getBytes( "Big5"),"Big5"); //讀跟顯示時的編碼
                    arrLine = strLine.split(",");
                    sockId = arrLine[0] ;  //股票代號
//                        System.out.println("---------------------------");
//                        System.out.println("sockId="+ sockId);
                    stockCnt ++ ; //股票計次
                }//股票迴圈
                if(fw1 != null){
                    fw1.close();
                }
                System.out.println("stockCnt:" + stockCnt);
            }//月份迴圈
            br1.close();
            System.out.println("done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getMonthNum(Calendar initDate, Calendar endDate){
        /*
            計算參數日期與系統日期相隔幾個月
            EX: initDate: 2017/08/01 , sysdate: 2017/09/07 ==> 1
         */
        Calendar now;
        if (endDate == null){
            now = Calendar.getInstance();
        }else{
            now = endDate;
        }
        int year = now.get(Calendar.YEAR) - initDate.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) - initDate.get(Calendar.MONTH);
        if (month < 0) {
            month = 12 - initDate.get(Calendar.MONTH) + now.get(Calendar.MONTH);
            year -= 1;
        }
        return year*12 + month ;
    }


}
