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
 * 1. Json取得上櫃每日交易資料
 * 2. 依照日期&產csv檔
 *
 * @author Joan lin
 * 2017/09/05
 *
 */
public class StockDailyOTC_ByDate {

    public static void main(String[] args) {
        String YYYY ="", MM ="", DD ="" , sockId ="", uri = "", strYYYYMM="";
        String taskID = "dlytrans";
        String fileType = "OTC";
        String stockFileSource = "stocklist.csv";
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
        Calendar initDate = new GregorianCalendar(2016, Calendar.JANUARY, 1);
        Calendar startDate = null;
//        Calendar endDate = null;  //不指定結束日期就由getMonthNum()抓系統日
        Calendar endDate = new GregorianCalendar(2016, Calendar.DECEMBER, 1); //指定結束日期
        int mons = getMonthNum(initDate,endDate); //total month
        System.out.println("total mons ="+ mons);

        try {
            //原始檔案編碼
            BufferedReader br1 = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(PATH + stockFileSource ), "Big5"));

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
                                    new FileInputStream(PATH + stockFileSource), "Big5"));
//                    System.out.println("br1.length="+ br1.);
                    while ((data = br1.readLine()) != null) {//股票迴圈
                        strLine = new String(data.getBytes( "Big5"),"Big5"); //讀跟顯示時的編碼
                        arrLine = strLine.split(",");
                        sockId = arrLine[0] ;  //股票代號
//                        System.out.println("---------------------------");
//                        System.out.println("sockId="+ sockId);
                        stockCnt ++ ; //股票計次
                        //個股日成交資訊
                        if (arrLine[2].equals("上櫃") ){
                            uri = "http://www.tpex.org.tw/web/stock/aftertrading/daily_trading_info/st43_result.php?l=zh-tw&d="
                                    + (Integer.valueOf(YYYY) - 1911 )+"/"+ MM +"&stkno="+sockId;

    //                        System.out.println(uri);
                            Document jsoupDoc = CrawlerPack.start()
                                    .setRemoteEncoding("big5")
                                    .getFromJson(uri);
                            csvString = csvTitle;
                            cnt = jsoupDoc.select("aadata").size();
                            if (cnt>0){
//                                stockCnt ++ ; //股票計次
                                csvString = csvTitle;
                            }
                            for (Element elem : jsoupDoc.select("aadata")) {
                                File file = new File(OUTPATH + fileType + "-" + taskID +"-"+ strYYYYMM + ".csv");
                                if (file.exists()) {
                                    file.delete();  //重新執行時要殺舊檔
                                }
                                fw1 = new FileWriter(file,true);
                                if(file.length()==0){
                                    fw1.write (csvString+"\r\n");  //標題
                                }
                                YYYYMMDD = elem.child(0).html().replace(",", "").split("/");
                                csvString = sockId
                                        + "," + (Integer.valueOf(YYYYMMDD[0]) + 1911) + YYYYMMDD[1] + YYYYMMDD[2]
                                        + "," + elem.child(1).html().replace(",", "")
                                        + "," + elem.child(2).html().replace(",", "")
                                        + "," + elem.child(3).html().replace(",", "")
                                        + "," + elem.child(4).html().replace(",", "")
                                        + "," + elem.child(5).html().replace(",", "")
                                        + "," + elem.child(6).html().replace(",", "")
                                        + "," + elem.child(7).html().replace(",", "")
                                        + "," + elem.child(8).html().replace(",", "")
                                ;
                                fw1.write (csvString+"\r\n");
                                fw1.flush();
    //                          System.out.println(csvString);
                            }
//                            System.out.println("done:" + cnt);
                        }
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

