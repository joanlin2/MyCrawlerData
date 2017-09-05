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

//		Calendar startDate = new GregorianCalendar(2017, Calendar.SEPTEMBER, 1);
//		Date currentDate = new Date();
//		GregorianCalendar endDate = new GregorianCalendar();
//		endDate.setTime(currentDate);
//		int days = (int)(endDate.getTimeInMillis()-startDate.getTimeInMillis())/ (1000 * 60 * 60 * 24);
//
//		System.out.println("compareTo="+ (endDate.getTimeInMillis()-startDate.getTimeInMillis())/ (1000 * 60 * 60 * 24));


		try {
//			System.out.println("getEncoding="+new FileReader("D:\\workspace\\output\\stocklist.csv").getEncoding());
//			BufferedReader br1 = new BufferedReader(new FileReader("D:\\workspace\\output\\stocklist.csv"));
			BufferedReader br1 = new BufferedReader(
					new InputStreamReader(
							new FileInputStream("D:\\workspace\\output\\stocklist.csv"), "UTF8"));

			FileWriter fw1 = new FileWriter("D:\\workspace\\output\\message.txt", true);  //一直append於原檔


			String data;
			int i =1;
			while ((data = br1.readLine()) != null) {

				System.out.println(data);
				i++;
				if(i==10) {break;}
			}

//			for(int i =0; i < days; i++) {
//				startDate.add(GregorianCalendar.DATE, 1); //日期遞增
//
//
//
//
//
//				System.out.println(startDate.get(Calendar.YEAR) + "/" + (startDate.get(Calendar.MONTH) + 1) + "/" + startDate.get(Calendar.DATE));
//			}



			br1.close();
			fw1.close();

//
//			String uri = "http://www.tpex.org.tw/web/stock/aftertrading"
//					+ "/daily_trading_info/st43_result.php?l=zh-tw"
//					+ "&d=106/09&stkno=1336";
//
//			Document jsoupDoc = CrawlerPack.start()
//					.setRemoteEncoding("big5")
//					.getFromJson(uri);
//
//			String stockID = jsoupDoc.select("stkno").text().toString();
//			String csvString = "";
//
//			String[] YYYYMMDD = {};
//
//
//			for (Element elem : jsoupDoc.select("aadata")) {
//
//				YYYYMMDD = elem.child(0).html().replace(",", "").split("/");
////			System.out.println((Integer.valueOf(YYYYMMDD[0])+1911)+YYYYMMDD[1]+YYYYMMDD[2]);
//
//				csvString = stockID
//						+ "," + (Integer.valueOf(YYYYMMDD[0]) + 1911) + YYYYMMDD[1] + YYYYMMDD[2]
//						+ "," + elem.child(1).html().replace(",", "")
//						+ "," + elem.child(2).html().replace(",", "")
//						+ "," + elem.child(3).html().replace(",", "")
//						+ "," + elem.child(4).html().replace(",", "")
//						+ "," + elem.child(5).html().replace(",", "")
//						+ "," + elem.child(6).html().replace(",", "")
//						+ "," + elem.child(7).html().replace(",", "")
//				;
//				System.out.println(csvString);
//			}

		} catch (IOException e) {
			e.printStackTrace();
//		} catch (ParseException e) {
//			e.printStackTrace();
		}




	}
}

