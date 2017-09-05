package stock;

import com.github.abola.crawler.CrawlerPack;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * 練習擷取證交所提供的盤後資訊
 * 
 * 重點
 * 1. Json取得每日交易資料
 * 
 * @author Joan lin
 *
 */
public class StockDailyOTC {
	
	public static void main(String[] args) {
		String csvString ="";
		String uri = "http://www.tpex.org.tw/web/stock/aftertrading"
				+ "/daily_trading_info/st43_result.php?l=zh-tw"
				+ "&d=106/09&stkno=1336";




//		System.out.println(
//			CrawlerPack.start()
//			//	.setRemoteEncoding("big5")
//					.getFromJson(uri)
//				.getElementsByTag("aadata")
//				//.select("td:matchesOwn(^[\\+\\-]?([0-9]{1,3},)*[0-9]{1,3}(\\.[0-9]+)*$)")
////				//.select("script")
//				.toString()
//		);

		Document jsoupDoc = CrawlerPack.start()
				.setRemoteEncoding("big5")
				.getFromJson(uri);

		// 印出整份 XML 資料
//		System.out.println(jsoupDoc.toString());
//		System.out.println("--------------");

		csvString = jsoupDoc.select("stkno").text().toString();
		System.out.println(jsoupDoc.select("aadata").size() );
		for( Element elem: jsoupDoc.select("aadata") ){
			System.out.print(csvString);
			System.out.print("," + elem.getElementsByIndexLessThan(8).text().replace(",","") );
//			for( Element arr: jsoupDoc.select("array") ) {
////				csvString = csvString+"," + arr.text().replace(",","");
//				System.out.print("," + arr.text().replace(",",""));
//			}
			System.out.println();
		}


	}
}
