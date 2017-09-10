package stock;

import com.github.abola.crawler.CrawlerPack;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * 產銷組合
 *
 * 重點
 * 1. 來源: 至中國信託證券
 * 2. 依照股票代號csv檔
 *
 * @author Joan lin
 * 2017/09/09
 *
 */
public class companyPrdMatrix {

    public static void main(String[] args) {
        String YYYY ="" , lastYYYY ="", MM ="", DD =""  , sockId ="", uri = "", strYYYYMM="";
        String taskID = "prdmatrix";
        String stockFileSource = "stocklist.csv";
        String[] arrLine = null ;  //股票清單陣列
        String[] arrPrdmtx = null;  //產銷組合
        String strLine ="";   //單行股票info
        String data;
        String csvString = "" , csvTitle ="";
        FileWriter fw1 = null;
        csvTitle = "";
        String[] YYYYMM = null;
        String nowYYYY = "";
        Integer cnt =0, docCnt=0 , docCnt2=0, fileNum = 1 ;
        FileWriter fw2 = null;
        String uri2 = "";

        java.util.Date starttime = new java.util.Date();
        long longToday = starttime.getTime();

        //起迄日期設定
        Calendar initDate = Calendar.getInstance();
        File file , file2;

        String PATH = "D:\\workspace\\output\\";
        String OUTPATH = PATH + taskID + "\\";
        File directory = new File(String.valueOf(OUTPATH));
        if (!directory.exists()) {
            directory.mkdir();
        }
        try {
            //原始檔案編碼
            BufferedReader br1 = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(PATH + stockFileSource), "Big5"));

                //初始日期
                System.out.println("initDate="+initDate.get(Calendar.YEAR)+"-"+ (initDate.get(Calendar.MONTH)+1));
                YYYY = String.format("%04d",initDate.get(Calendar.YEAR));
                lastYYYY = String.format("%04d",initDate.get(Calendar.YEAR)-1);
                MM = String.format("%02d",(initDate.get(Calendar.MONTH)+1 ) );
                DD = String.format("%02d",initDate.get(Calendar.DATE));
                strYYYYMM = YYYY + MM ;//reset
                System.out.println("strYYYYMM="+strYYYYMM);

                //重讀股票檔
                br1 = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(PATH + stockFileSource), "Big5"));
                while ((data = br1.readLine()) != null) {//股票迴圈
                    strLine = new String(data.getBytes( "Big5"),"Big5"); //讀跟顯示時的編碼
                    arrLine = strLine.split(",");
                    sockId = arrLine[0] ;  //股票代號
//                        System.out.println("---------------------------");
//                        System.out.println("sockId="+ sockId);

                    //產銷組合-今年度
                    uri = "http://jow.win168.com.tw/z/zc/zc2/zc2_"+sockId + ".djhtm";
//                    System.out.println(uri);
                    Document jsoupDoc = CrawlerPack.start()
                            .getFromHtml(uri);
                    cnt = jsoupDoc.select("tr:gt(1)").size();

                    if (cnt>2){ //查無資料時cnt=2, 必須>2才算有資料
                        //產生檔案
                        file = new File(OUTPATH + taskID +"-"+ YYYY  + "-" + fileNum + ".csv"); //今年度
                        file2 = new File(OUTPATH + taskID +"-"+ lastYYYY + "-" + fileNum + ".csv");  //去年度
                        if (docCnt == 0 && file.exists()) {
                            file.delete();  //重新執行時要殺舊檔
                            file2.delete();  //重新執行時要殺舊檔
                        }
                        fw1 = new FileWriter(file,true);
                        fw2 = new FileWriter(file2,true);

                        //標題
                        if(docCnt == 0){
                            csvTitle = "股票代號" ;
                            for (Element elem : jsoupDoc.select(".t2")) {
                                csvTitle =  csvTitle + "," + elem.text().replace(",", "").replace(" ", ",");
                            }
                            fw1.write (csvTitle+"\r\n");
                            fw1.flush();
                            fw2.write (csvTitle+"\r\n");
                            fw2.flush();
                        }

                        //內容
                        for (Element elem : jsoupDoc.select("tr:gt(1)")) {
                            csvString = elem.text().replace(",", "").replace(" ", ",");
                            if(csvString.equals("")) continue;
                            arrPrdmtx = csvString.split(",");
                            if(arrPrdmtx[0].equals("--")){
                                csvString = sockId + "," + csvString.replace("--",nowYYYY);
                            }else{
                                YYYYMM = arrPrdmtx[0].split("/");
                                nowYYYY = String.valueOf(Integer.valueOf(YYYYMM[0]) + 1911) + YYYYMM[1] ;
                                csvString = sockId + "," + csvString.replace(arrPrdmtx[0],nowYYYY);
                            }

                            fw1.write (csvString+"\r\n");
                            fw1.flush();
                            docCnt++;
                            if((docCnt % 100) == 1 ){
                                Thread.sleep(301); //抓資料不能太快, 因公開觀測站有擋大量爬蟲行為
                            }
                        }
                    }

                //-------------------------------------------------------------------------
                    //產銷組合-去年度
                    uri2 = "http://jow.win168.com.tw/z/zc/zc2/zc2_"+sockId + "_2.djhtm";
//                    System.out.println(uri2);
                    Document jsoupDoc2 = CrawlerPack.start()
                            .getFromHtml(uri2);
                    cnt = jsoupDoc2.select("tr:gt(1)").size();

                    if (cnt>2){ //查無資料時cnt=2, 必須>2才算有資料

                        //內容
                        for (Element elem : jsoupDoc2.select("tr:gt(1)")) {
                            csvString = elem.text().replace(",", "").replace(" ", ",");
                            if(csvString.equals("")) continue;
                            arrPrdmtx = csvString.split(",");
                            if(arrPrdmtx[0].equals("--")){
                                csvString = sockId + "," + csvString.replace("--",nowYYYY);
                            }else{
                                YYYYMM = arrPrdmtx[0].split("/");
                                nowYYYY = String.valueOf(Integer.valueOf(YYYYMM[0]) + 1911) + YYYYMM[1] ;
                                csvString = sockId + "," + csvString.replace(arrPrdmtx[0],nowYYYY);
                            }
                            fw2.write (csvString+"\r\n");
                            fw2.flush();
                            docCnt2 ++;
                            if((docCnt2 % 100) == 1 ){
                                Thread.sleep(301); //抓資料不能太快, 因公開觀測站有擋大量爬蟲行為
                            }
                        }
                    }

                    if((docCnt % 900) == 1 ){
                        if(fw1 != null  ){
                            fw1.close();
                            fw2.close();
                            docCnt =0;
                            docCnt2 =0;
                            fileNum++;
                        }
                    }else if((docCnt % 300) == 1){
                        System.out.println("docCnt:" + docCnt);
                    }else if((docCnt % 300) == 1){
                        Thread.sleep(301); //抓資料不能太快, 因公開觀測站有擋大量爬蟲行為
                    }

                }//股票迴圈
                if(fw1 != null){
                    fw1.close();
                    fw2.close();
                }
                System.out.println("docCnt:" + docCnt);
            br1.close();
            System.out.println("done");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
//            System.out.println("sockId="+ sockId);
            System.out.println("docCnt:" + docCnt);
            java.util.Date endtime = new java.util.Date();
            System.out.println("time ="+ endtime);
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

