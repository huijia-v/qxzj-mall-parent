package com.huijia.qxzj.portal.web.util;

import com.octo.captcha.CaptchaFactory;
import com.octo.captcha.component.image.backgroundgenerator.UniColorBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomRangeColorGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.RandomTextPaster;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.word.FileDictionary;
import com.octo.captcha.component.word.wordgenerator.ComposeDictionaryWordGenerator;
import com.octo.captcha.engine.GenericCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;
import com.octo.captcha.service.image.ImageCaptchaService;
import com.octo.captcha.service.multitype.GenericManageableCaptchaService;


/**
 * @author huijia
 * @Email: 2921523918@qq.com
 * @date 2022/11/23 10:43 下午
 */
public class JcaptchaUtil {



    //做成单例
    private static final ImageCaptchaService service = imageCaptchaService();

    public static ImageCaptchaService getService() {
        return service;
    }

    private static ImageCaptchaService imageCaptchaService() {

        //背景
        UniColorBackgroundGenerator backgroundGenerator = new UniColorBackgroundGenerator(100, 50);
        RandomRangeColorGenerator textColor = new RandomRangeColorGenerator(new int[]{0, 100}, new int[]{0, 200}, new int[]{50, 250});

        RandomTextPaster randomTextPaster = new RandomTextPaster(4, 5, textColor);


        RandomFontGenerator randomFontGenerator = new RandomFontGenerator(20, 30);

        //组装图像
        ComposedWordToImage composedWordToImage = new ComposedWordToImage(randomFontGenerator, backgroundGenerator, randomTextPaster);

        //
        ComposeDictionaryWordGenerator cdwg = new ComposeDictionaryWordGenerator(new FileDictionary("toddlist"));

        GimpyFactory gf = new GimpyFactory(cdwg, composedWordToImage);

        GenericCaptchaEngine gce = new GenericCaptchaEngine(new CaptchaFactory[]{gf});

        return new GenericManageableCaptchaService(gce, 20, 2000, 2000);
    }
}
