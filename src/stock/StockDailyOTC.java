package stock;

import com.github.abola.crawler.CrawlerPack;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

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
		String uri = "http://www.tpex.org.tw/web/stock/aftertrading"
				+ "/daily_trading_info/st43_result.php?l=zh-tw"
				+ "&d=106/09&stkno=1336";




		Document jsoupDoc = CrawlerPack.start()
				.setRemoteEncoding("big5")
				.getFromJson(uri);

		String stockID =jsoupDoc.select("stkno").text().toString();
		String csvString ="";

		String[] YYYYMMDD ={};


		for( Element elem: jsoupDoc.select("aadata")){
			YYYYMMDD = elem.child(0).html().replace(",","").split("/") ;
//			System.out.println((Integer.valueOf(YYYYMMDD[0])+1911)+YYYYMMDD[1]+YYYYMMDD[2]);

			csvString = stockID
					+","+ (Integer.valueOf(YYYYMMDD[0])+1911)+YYYYMMDD[1]+YYYYMMDD[2]
					+","+elem.child(1).html().replace(",","")
					+","+elem.child(2).html().replace(",","")
					+","+elem.child(3).html().replace(",","")
					+","+elem.child(4).html().replace(",","")
					+","+elem.child(5).html().replace(",","")
					+","+elem.child(6).html().replace(",","")
					+","+elem.child(7).html().replace(",","")
					;
			System.out.println(csvString);
		}


	}
}
