import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.sharetop.max7219.Led;

/**
 * 微雪的RPi LED Matrix
 * 
 * @author yancheng
 * 
 */

public class MainDemo {

	public static void main(String[] args) throws InterruptedException, IOException {

		long displaytime = 60000;
		long printtime = new Date().getTime();
		DateFormat df = new SimpleDateFormat("mmss");
		// 有两块(8*8)的屏幕
		Led c = new Led((short) 6);

		// 打开设备
		c.open();

		// 旋转270度，缺省两个屏幕是上下排列，我需要的是左右排
		c.orientation(90);

		// DEMO1: 输出两个字母

		while (true) {

			// c.letter((short) 0, (short) '1');
			// c.letter((short) 1, (short) '2');
			// c.letter((short) 2, (short) '3');
			// c.letter((short) 3, (short) '4');
			// c.letter((short) 4, (short) '5');
			// c.letter((short) 5, (short) '6');

			print4(df.format(new Date()), c);
			c.flush();
			Thread.sleep(1000);

			if (new Date().getTime() - printtime > 60000) {
				
				printtime = new Date().getTime();
				String value = readTmp("/sensor/dht22");
				c.clear();
				c.showMessage(value);
				c.showMessage(value);
			}

		}

		// DEMO2: 输出两个汉字，比较CHOU
		// c.letter((short)0, (short)0,Font.CHN_FONT,false);
		// c.letter((short)1, (short)1,Font.CHN_FONT,false);
		// c.flush();

		// DEMO3: 输出一串字母
		// while (true) {
		//
		// c.showMessage("20:23");
		// c.showMessage("20 23");
		// }

		/*
		 * try { System.in.read(); c.close();
		 * 
		 * } catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 * 
		 */
	}

	/**
	 * ใช้สำหรับ 4 port
	 * 
	 * @param s
	 */
	static void print4(String s, Led c) {

		c.letter((short) 0, (short) s.charAt(0));
		c.letter((short) 1, (short) s.charAt(1));
		c.letter((short) 2, (short) s.charAt(2));
		c.letter((short) 3, (short) s.charAt(3));
	}

	void print(String s, Led c) {

		for (int i = 0; i < s.length(); i++) {
			c.letter((short) i, (short) s.charAt(i));
		}

	}

	static String readTmp(String pathtosensor) throws IOException {

		FileInputStream fi = new FileInputStream(new File(pathtosensor));
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		byte[] buff = new byte[2024];
		int read = 0;

		while ((read = fi.read(buff)) != -1) {
			buf.write(buff, 0, read);
		}

		String r = new String(buf.toByteArray());
		buf.close();
		fi.close();

		return r;

	}
}
