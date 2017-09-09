package stock;

import com.github.abola.crawler.CrawlerPack;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * 擷取證交所提供的盤後資訊
 *
 * 重點
 * 1. Json取得上市/上櫃每月可轉債資料
 * 2. 依照日期&產csv檔
 *
 * @author Joan lin
 * 2017/09/08
 *
 */
public class Convertiblebond_ByDate {

    public static void main(String[] args) {
        String YYYY ="", MM ="", DD =""  , sockId ="", uri = "", strYYYYMM="";
        String taskID = "tcb";
        String fileType = "";
        String stockFileSource = "stocklist.csv";
        String[] arrLine = null;  //股票清單陣列
        String csvString = "" , csvTitle ="";
        FileWriter fw1 = null;
        Integer cnt =0, stockCnt =0 ;

        java.util.Date starttime = new java.util.Date();
        long longToday = starttime.getTime();

        //起迄日期設定
        Calendar initDate = new GregorianCalendar(2014, Calendar.JANUARY, 1);
        Calendar startDate = null;
        Calendar endDate = null;  //不指定結束日期就由getMonthNum()抓系統日
//        Calendar endDate = new GregorianCalendar(2017, Calendar.SEPTEMBER, 1); //指定結束日期
        int mons = getMonthNum(initDate,endDate); //total month
        System.out.println("time ="+ starttime);


        String PATH = "D:\\workspace\\output\\";
        String OUTPATH = PATH + taskID + "\\";

        File directory = new File(String.valueOf(OUTPATH));
        if (!directory.exists()) {
            directory.mkdir();
        }
        try {
            //觀測站只提供上個月的, 故mon-1
            for(int k =0; k <= mons -1; k++) { //月份遞減
                //重設初始日期(為了利用k,每次新日期都要先讀初始日期)
                startDate = new GregorianCalendar(initDate.get(Calendar.YEAR), initDate.get(Calendar.MONTH), 1);
//                System.out.println("initDate="+initDate.get(Calendar.YEAR)+"-"+ (initDate.get(Calendar.MONTH)+1));

                startDate.add(GregorianCalendar.MONTH, k); //月份遞增
                YYYY = String.format("%04d",startDate.get(Calendar.YEAR));
                MM = String.format("%02d",(startDate.get(Calendar.MONTH)+1 ) );
                DD = String.format("%02d",startDate.get(Calendar.DATE));
                strYYYYMM = YYYY + MM ;//reset
                System.out.println("strYYYYMM="+strYYYYMM);
                File file;

                uri = "http://mops.twse.com.tw/nas/t120/CBTRN"+ strYYYYMM +".htm" ;
//                System.out.println(uri);
                Document jsoupDoc = CrawlerPack.start()
                        .setRemoteEncoding("big5")
                        .getFromHtml(uri);

                //標題
                cnt = jsoupDoc.select("table tr").size();
                if (cnt>0){
                    stockCnt = 0;
                    //產生檔案
                    file = new File(OUTPATH + taskID +"-"+ strYYYYMM + ".csv");
                    if (file.exists()) {
                        file.delete();  //重新執行時要殺舊檔
                    }
                    fw1 = new FileWriter(file,true);
                    for (Element elem : jsoupDoc.select("table tr")) {
                        csvString = elem.text().replace(",", "").replace(" ", ",");
                        fw1.write (csvString+"\r\n");
                        fw1.flush();
                        stockCnt++;
                        if((stockCnt % 100) == 1 ){
                            Thread.sleep(301); //抓資料不能太快, 因公開觀測站有擋大量爬蟲行為
                        }
                    }
                }
                if(fw1 != null){
                    fw1.close();
                }
                System.out.println("stockCnt:" + stockCnt);
                Thread.sleep(301); //抓資料不能太快, 因公開觀測站有擋大量爬蟲行為
                }//資料迴圈
                System.out.println("done");
            }//月份迴圈
        catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            System.out.println("stockCnt:" + stockCnt);
            java.util.Date endtime = new java.util.Date();
            System.out.println("time ="+ endtime);
            System.out.println("total spend time ="+ ((endtime.getTime() - longToday)/(1000  ) + " secs"));
            System.out.println("total spend time ="+ ((endtime.getTime() - longToday)/(1000 * 60 ) + " mins"));
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

