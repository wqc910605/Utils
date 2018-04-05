package demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
/**
 * 屏幕适配, 自定义尺寸的工具类
 * @author wwf
 *
 */
public class MakeXml {
	private final static String rootPath = "C:/Users/wwf/Desktop/values/values-{0}x{1}";

	private final static float dw = 480f;// 320 1单位长度=1px 640px ,1单位长度=2px
	private final static float dh = 800f;
	private final static String WTemplate = "<dimen name=\"w{0}\">{1}px</dimen>\n";
	private final static String HTemplate = "<dimen name=\"h{0}\">{1}px</dimen>\n";

	public static void main(String[] args) {
		makeString(720, 1280);
		makeString(1080, 1920);
		makeString(1080, 2160);
		makeString(1440, 2560);
	}

	private static void makeString(int w, int h) {
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		sb.append("<resources>");
		float cellw = w / dw;
		for (int i = 1; i < dw; i++) {
			sb.append(WTemplate.replace("{0}", i + "").replace("{1}",
					change(cellw * i) + ""));
		}
		sb.append(WTemplate.replace("{0}", (int)dw+"").replace("{1}", w + ""));
		sb.append("</resources>");

		StringBuffer sb2 = new StringBuffer();
		sb2.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		sb2.append("<resources>");
		float cellh = h / dh;
		for (int i = 1; i < dh; i++) {
			sb2.append(HTemplate.replace("{0}", i + "").replace("{1}",
					change(cellh * i) + ""));
		}
		sb2.append(HTemplate.replace("{0}", (int)dh+"").replace("{1}", h + ""));
		sb2.append("</resources>");

		String path = rootPath.replace("{0}", h + "").replace("{1}", w + "");
		File rootFile = new File(path);
		if (!rootFile.exists()) {
			rootFile.mkdirs();
		}

		File layxFile = new File(path + "/width.xml");
		File layyFile = new File(path + "/height.xml");
		PrintWriter pw;
		try {
			pw = new PrintWriter(new FileOutputStream(layxFile));
			pw.print(sb.toString());
			pw.close();
			pw = new PrintWriter(new FileOutputStream(layyFile));
			pw.print(sb2.toString());
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static String change(float f) {
		float b = (float) (Math.round(f * 100))/100;// 100;(这里的100就是2位小数点,如果要其它位,如4位,这里两个100改成10000)
		return b+"";
	}
}
