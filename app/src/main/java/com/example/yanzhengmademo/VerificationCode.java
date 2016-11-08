package com.example.yanzhengmademo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by：赖上罗小贱 on 2016/11/8
 * 邮箱：ljq@luojunquan.top
 * 个人博客：www.luojunquan.top
 * CSDN:http://blog.csdn.net/u012990171
 */
public class VerificationCode {
    /*
     验证码的长度和多少条干扰线都是通过调用他的类传入的
      */
    private static int CODE_LENGTH;//验证码的长度
    private static int LINE_NUMBER;//多少条干扰线
    private static final int FONT_SIZE = 60;//字体大小
    private static final int BASE_PADDING_LEFT = 20; //左边距
    private static final int RANGE_PADDING_LEFT = 35;//左边距范围值
    private static final int BASE_PADDING_TOP = 42;//上边距
    private static final int RANGE_PADDING_TOP = 15;//上边距范围值
    private static final int DEFAULT_WIDTH = 300;//默认宽度.图片的总宽,当验证码过多时,默认宽度要改大
    private static final int DEFAULT_HEIGHT = 70;//默认高度.图片的总高
    private final int DEFAULT_COLOR = 0xdf;//默认背景颜色值
    private String code;//保存生成的验证码
    private int padding_left, padding_top;
    private Random random = new Random();
    /**
     * 所有产生验证码的字符
     */
    private static final char[] CHARS = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };
    /**
     * 单例模式返回一个验证码
     */
    private static VerificationCode bpUtil;

    private VerificationCode() {
    }

    //调用的时候传入验证码的长度和干扰线的多少
    public static VerificationCode getInstance(int codeLen, int lineNum) {
        VerificationCode.CODE_LENGTH = codeLen;
        VerificationCode.LINE_NUMBER = lineNum;
        if (bpUtil == null)
            bpUtil = new VerificationCode();
        return bpUtil;
    }


    /**
     * 创建一个公有的方法用于返回创建的bitmap
     */
    public Bitmap getBitmap() {
        return createBitmap();
    }

    /**
     * 创造一个bitmap
     */
    private Bitmap createBitmap() {
        padding_left = 0;
        //创建一个用来 显示验证码的BitMap
        Bitmap bitmap = Bitmap.createBitmap(DEFAULT_WIDTH, DEFAULT_HEIGHT, Bitmap.Config.ARGB_8888);
        //实例化一个画布对象,将bitmap传入
        Canvas canvas = new Canvas(bitmap);
        //画布颜色
        canvas.drawColor(Color.rgb(DEFAULT_COLOR, DEFAULT_COLOR, DEFAULT_COLOR));
        //调用随机得到验证码要显示的字符方法,得到要画到画布上的字符串
        code = createCode();
        //实例化画笔对象
        Paint paint = new Paint();
        //设置绘制字体的大小
        paint.setTextSize(FONT_SIZE);
        //根据字符串长度来绘制验证码
        for (int i = 0; i < code.length(); i++) {
            randomTextStyle(paint);//调用方法随机生成画笔的格式
            randomPadding();//调用方法产生随机的边距
            /*
            开始将字符串绘制成验证码
            参数1:要绘制的文本,参数2,3:X,Y坐标,参数3:画笔
             */
            canvas.drawText(code.charAt(i) + "", padding_left, padding_top, paint);
        }
        //根据传入的线条数画线条
        for (int i = 0; i < LINE_NUMBER; i++) {
            drawLine(canvas, paint);
        }
        canvas.save(Canvas.ALL_SAVE_FLAG);//保存
        canvas.restore();
        return bitmap;
    }
    /**
     * 得到验证码的方法,用于验证输入的对不对
     */
    public String getCode() {
        return code.toLowerCase();
    }
    /**
     * 随机得到验证码要显示的字符,个数为我们自己设置
     * @return 得到一个字符串
     */
    private String createCode() {
        StringBuilder buffer = new StringBuilder();
        //根据传入验证码的长度来随机生成数组里面的字符
        for (int i = 0; i < CODE_LENGTH; i++) {
            buffer.append(CHARS[random.nextInt(CHARS.length)]);
        }
        return buffer.toString();
    }

    /**
     * 画干扰线
     * @param canvas
     * @param paint
     */
    private void drawLine(Canvas canvas, Paint paint) {
        int color = randomColor(1);
        int startX = random.nextInt(DEFAULT_WIDTH);
        int startY = random.nextInt(DEFAULT_HEIGHT);
        int stopX = random.nextInt(DEFAULT_WIDTH);
        int stopY = random.nextInt(DEFAULT_HEIGHT);
        paint.setStrokeWidth(1);//设置线宽
        paint.setColor(color);//设置字体颜色
        canvas.drawLine(startX, startY, stopX, stopY, paint);//绘制线条
    }

    /**
     * 设置验证码的颜色
     */
    private int randomColor(int rate) {
        int red = random.nextInt(256) / rate;
        int green = random.nextInt(256) / rate;
        int blue = random.nextInt(256) / rate;
        return Color.rgb(red, green, blue);
    }

    /**
     * 设置验证码的字体格式
     * @param paint
     */
    private void randomTextStyle(Paint paint) {
        int color = randomColor(1);
        paint.setColor(color);
        paint.setFakeBoldText(random.nextBoolean());  //true为粗体，false为非粗体
        float skewX = random.nextInt(11) / 10;
        skewX = random.nextBoolean() ? skewX : -skewX;
        paint.setTextSkewX(skewX); //float类型参数，负数表示右斜，整数左斜
//		paint.setUnderlineText(true); //true为下划线，false为非下划线
//		paint.setStrikeThruText(true); //true为删除线，false为非删除线
    }

    /**
     * 随机产生验证码之间的间隔
     */
    private void randomPadding() {
        padding_left += BASE_PADDING_LEFT + random.nextInt(RANGE_PADDING_LEFT);
        padding_top = BASE_PADDING_TOP + random.nextInt(RANGE_PADDING_TOP);
    }
}
