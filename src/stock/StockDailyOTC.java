package stock;

import com.github.abola.crawler.CrawlerPack;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 擷取證交所提供的盤後資訊
 * 
 * 重點
 * 1. Json取得上櫃每日交易資料
 * 
 * @author Joan lin
 * 2017/09/05
 *
 */
public class StockDailyOTC {

	public static void main(String[] args) {
		String YYYY ="";
		String MM ="";
		String DD ="";
		String sockId ="";
		String lastType =""; //上市/上櫃
		String lastSockId ="";
		String task = "dlytrans";
		String[] arrLine = null;  //股票清單陣列
		String strLine ="";   //單行股票info
		String data;
		String strdate = "";

		//

			String uri = "http://www.tpex.org.tw/web/stock/aftertrading"
					+ "/daily_trading_info/st43_result.php?l=zh-tw"
					+ "&d=106/09&stkno=1336";

		//起迄日期設定
		Calendar startDate = new GregorianCalendar(2017, Calendar.SEPTEMBER, 1);
		Date currentDate = new Date();
		GregorianCalendar endDate = new GregorianCalendar();
		endDate.setTime(currentDate);

		int days = (int)(endDate.getTimeInMillis()-startDate.getTimeInMillis())/ (1000 * 60 * 60 * 24); //total days
		System.out.println("total days ="+ (endDate.getTimeInMillis()-startDate.getTimeInMillis())/ (1000 * 60 * 60 * 24));


		try {
			//原始檔案編碼
			BufferedReader br1 = new BufferedReader(
					new InputStreamReader(
							new FileInputStream("D:\\workspace\\output\\stocklist.csv"), "Big5"));
			FileWriter fw1 = new FileWriter("D:\\workspace\\output\\message.txt", true);  //一直append於原檔



			while ((data = br1.readLine()) != null) {//股票迴圈
				strLine = new String(data.getBytes( "Big5"),"Big5"); //讀跟顯示時的編碼
				arrLine = strLine.split(",");
				sockId = arrLine[0] ;  //股票代號
				//arrLine[2] 上市/上櫃 lastType
				System.out.println(arrLine[0]);

				for(int k =0; k < days; k++) {
					startDate.add(GregorianCalendar.DATE, 1); //日期遞增
					strdate = ""; //reset

					 YYYY = String.format("%04d",startDate.get(Calendar.YEAR));
					 MM = String.format("%02d",(startDate.get(Calendar.MONTH) + 1) );
					 DD = String.format("%02d",startDate.get(Calendar.DATE));
					strdate = YYYY + MM + DD;



			//個股日成交資訊
					if (arrLine[2] == "上市"){

						fw1.close();
					}else{

					}

			 uri = "http://www.tpex.org.tw/web/stock/aftertrading"
					+ "/daily_trading_info/st43_result.php?l=zh-tw"
					+ "&d=106/09&stkno=1336";

			Document jsoupDoc = CrawlerPack.start()
					.setRemoteEncoding("big5")
					.getFromJson(uri);

			String stockID = jsoupDoc.select("stkno").text().toString();
			String csvString = "";

			String[] YYYYMMDD = {};


			for (Element elem : jsoupDoc.select("aadata")) {

				YYYYMMDD = elem.child(0).html().replace(",", "").split("/");
//			System.out.println((Integer.valueOf(YYYYMMDD[0])+1911)+YYYYMMDD[1]+YYYYMMDD[2]);

				csvString = stockID
						+ "," + (Integer.valueOf(YYYYMMDD[0]) + 1911) + YYYYMMDD[1] + YYYYMMDD[2]
						+ "," + elem.child(1).html().replace(",", "")
						+ "," + elem.child(2).html().replace(",", "")
						+ "," + elem.child(3).html().replace(",", "")
						+ "," + elem.child(4).html().replace(",", "")
						+ "," + elem.child(5).html().replace(",", "")
						+ "," + elem.child(6).html().replace(",", "")
						+ "," + elem.child(7).html().replace(",", "")
				;
				System.out.println(csvString);
			}

					strdate = arrLine[0] ; //放入歷史資料
				}



				br1.close();



				lastType = arrLine[2] ; //放入歷史資料

			}//股票迴圈


		} catch (IOException e) {
			e.printStackTrace();
//		} catch (ParseException e) {
//			e.printStackTrace();
		}




	}
}

