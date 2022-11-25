package com.huijia.qxzj.portal.web.code;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import lombok.Data;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Random;

/**
 * @author huijia
 * @date 2022/11/23 5:16 下午
 */

@Data
public class ImageCode {

    //图形中的内容
    private String code;

    //图片
    private ByteArrayInputStream image;

    private int width = 400;

    private int height = 100;


    //单例
    public static ImageCode getInstance() throws IOException {
        return new ImageCode();
    }
    public ImageCode() throws IOException {
        //图形缓冲区
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        //画笔
        Graphics graphics = image.getGraphics();

        //用笔开始画图
        graphics.setColor(new Color(137, 142, 139));

        //画矩形
        graphics.fillRect(0, 0, width, height);

        //字体
        graphics.setFont(new Font("宋体", Font.PLAIN, 30));

        //随机数
        Random random = new Random();


        //随机生成6和数字
        this.code = "";
//        for (int i = 0; i < 6; i++) {
//            String s = String.valueOf(random.nextInt(10));
//            this.code += s;
//
//            graphics.setColor(new Color(21, 23, 22));
//            graphics.drawString(s, (width / 6) * i, 40);
//
//            //划线
//            graphics.setColor(new Color(155, 189, 71));
//
//            graphics.drawLine((width / 6) * i, 40, (width / 6) * i + 25, 40 - 10);
//
//        }


        //生成一个数学公式
        int num1 = random.nextInt(30);
        int num2 = random.nextInt(30);

        graphics.setColor(new Color(36, 38, 37));

        graphics.drawString(num1 + "", (width / 6) * 0 + 10, 60);
        graphics.drawString("+", (width / 6) * 1 + 10, 60);
        graphics.drawString(num2 + "", (width / 6) * 2 + 10, 60);
        graphics.drawString("=" + "", (width / 6) * 3 + 10, 60);
        graphics.drawString("?" + "", (width / 6) * 4 + 10, 60);


        this.code = num1 + num2 + "";

        graphics.setColor(new Color(155, 189, 71));
        for (int i = 0; i < 100; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int x1 = random.nextInt(20);
            int y1 = random.nextInt(30);
            graphics.drawLine(x, y, x + x1, y + y1);
        }



        //收笔
        graphics.dispose();


        ByteArrayInputStream inputStream = null;

        ByteOutputStream outputStream = new ByteOutputStream();

        try {
            //赋值给byteArrayInputStream
            ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(outputStream);
            ImageIO.write(image, "jpeg", imageOutputStream);

            inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        } catch (Exception e) {
            System.out.println("生成验证码失败，error");
        }
        this.image = inputStream;
    }
}
