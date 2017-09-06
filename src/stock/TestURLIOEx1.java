package stock;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;

public class TestURLIOEx1 {

	private String urlstr = "https://tw.youcard.yahoo.com/cardstack/36e2bb10-3fa2-11e7-806f-7d1db5514465/%E5%A4%B1%E6%88%80%E8%A6%81%E5%90%83%E9%A6%99%E8%95%89%E7%9A%AE%EF%BC%9F%E9%80%9910%E7%A8%AE%E5%BF%AB%E6%A8%82%E9%A3%9F%E7%89%A9%E5%BF%AB%E8%A9%A6%E8%A9%A6";
	private URL url;
	
	public TestURLIOEx1() throws Exception{  //用建構子把IO exception獨立出來
		url = new URL(urlstr);
	}

	public void parseUrlContent() {
		
			//使用自動關閉, 用try catch包起這2行
		try (BufferedReader br1 = new BufferedReader(new InputStreamReader(url.openStream()));
			 BufferedWriter bw1 = 
				        new BufferedWriter(new OutputStreamWriter(new FileOutputStream("D:\\workspace\\output\\page.html")))) {

			String data;
			while ((data = br1.readLine()) != null) {  //讀進來
				System.out.println(data);
				bw1.write(data);//寫出去
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		TestURLIOEx1 test1 = new TestURLIOEx1();
		test1.parseUrlContent();
	}

}