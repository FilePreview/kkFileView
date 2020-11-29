package cn.keking.config;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author chenjh
 * @since 2020/5/13 17:44
 */
public class WatermarkConfigConstants {

    private static String WATERMARK_TXT;
    private static String WATERMARK_X_SPACE;
    private static String WATERMARK_Y_SPACE;
    private static String WATERMARK_FONT;
    private static String WATERMARK_FONTSIZE;
    private static String WATERMARK_COLOR;
    private static String WATERMARK_ALPHA;
    private static String WATERMARK_WIDTH;
    private static String WATERMARK_HEIGHT;
    private static String WATERMARK_ANGLE;

    public static String DEFAULT_WATERMARK_TXT = "";
    public static String DEFAULT_WATERMARK_X_SPACE = "10";
    public static String DEFAULT_WATERMARK_Y_SPACE = "10";
    public static String DEFAULT_WATERMARK_FONT = "微软雅黑";
    public static String DEFAULT_WATERMARK_FONTSIZE = "18px";
    public static String DEFAULT_WATERMARK_COLOR = "black";
    public static String DEFAULT_WATERMARK_ALPHA = "0.2";
    public static String DEFAULT_WATERMARK_WIDTH = "240";
    public static String DEFAULT_WATERMARK_HEIGHT = "80";
    public static String DEFAULT_WATERMARK_ANGLE = "10";

    /**
     * Author：houzheng
     * Date：11-18
     * 获取水印文本
     *
     */

    public static String getWatermarkTxt() {
        return WATERMARK_TXT;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置水印文本值
     *
     */

    public static void setWatermarkTxtValue(String watermarkTxt) {
        WATERMARK_TXT = watermarkTxt;
    }

    @Value("${watermark.txt:}")
    /**
     * Author：houzheng
     * Date：11-18
     * 设置水印文本
     *
     */

    public void setWatermarkTxt(String watermarkTxt) {
        setWatermarkTxtValue(watermarkTxt);
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取水印水平空间
     *
     */

    public static String getWatermarkXSpace() {
        return WATERMARK_X_SPACE;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置水印水平空间值
     *
     */

    public static void setWatermarkXSpaceValue(String watermarkXSpace) {
        WATERMARK_X_SPACE = watermarkXSpace;
    }

    @Value("${watermark.x.space:10}")
    /**
     * Author：houzheng
     * Date：11-18
     * 设置水印水平空间
     *
     */

    public void setWatermarkXSpace(String watermarkXSpace) {
        setWatermarkXSpaceValue(watermarkXSpace);
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取水印竖直空间
     *
     */

    public static String getWatermarkYSpace() {
        return WATERMARK_Y_SPACE;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置水印竖直空间值
     *
     */

    public static void setWatermarkYSpaceValue(String watermarkYSpace) {
        WATERMARK_Y_SPACE = watermarkYSpace;
    }

    @Value("${watermark.y.space:10}")
    /**
     * Author：houzheng
     * Date：11-18
     * 设置水印竖直空间
     *
     */

    public void setWatermarkYSpace(String watermarkYSpace) {
        setWatermarkYSpaceValue(watermarkYSpace);
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取水印字体
     *
     */

    public static String getWatermarkFont() {
        return WATERMARK_FONT;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置水印字体值
     *
     */

    public static void setWatermarkFontValue(String watermarkFont) {
        WATERMARK_FONT = watermarkFont;
    }

    @Value("${watermark.font:微软雅黑}")
    /**
     * Author：houzheng
     * Date：11-18
     * 设置水印字体
     *
     */

    public void setWatermarkFont(String watermarkFont) {
        setWatermarkFontValue(watermarkFont);
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取水印字体大小
     *
     */

    public static String getWatermarkFontsize() {
        return WATERMARK_FONTSIZE;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置水印字体大小值
     *
     */

    public static void setWatermarkFontsizeValue(String watermarkFontsize) {
        WATERMARK_FONTSIZE = watermarkFontsize;
    }

    @Value("${watermark.fontsize:18px}")
    /**
     * Author：houzheng
     * Date：11-18
     * 设置水印字体大小
     *
     */

    public void setWatermarkFontsize(String watermarkFontsize) {
        setWatermarkFontsizeValue(watermarkFontsize);
    }
    /**
     * Author：houzheng
     * Date：11-18
     *获取水印颜色
     *
     */

    public static String getWatermarkColor() {
        return WATERMARK_COLOR;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置水印颜色值
     *
     */

    public static void setWatermarkColorValue(String watermarkColor) {
        WATERMARK_COLOR = watermarkColor;
    }

    @Value("${watermark.color:black}")
    /**
     * Author：houzheng
     * Date：11-18
     * 设置水印颜色
     *
     */

    public void setWatermarkColor(String watermarkColor) {
        setWatermarkColorValue(watermarkColor);
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取水印Alpha
     *
     */

    public static String getWatermarkAlpha() {
        return WATERMARK_ALPHA;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置水印Alpha值
     *
     */

    public static void setWatermarkAlphaValue(String watermarkAlpha) {
        WATERMARK_ALPHA = watermarkAlpha;
    }

    @Value("${watermark.alpha:0.2}")
    /**
     * Author：houzheng
     * Date：11-18
     * 设置水印Alpha
     *
     */

    public void setWatermarkAlpha(String watermarkAlpha) {
        setWatermarkAlphaValue(watermarkAlpha);
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取水印宽度
     *
     */

    public static String getWatermarkWidth() {
        return WATERMARK_WIDTH;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置水印宽度值
     *
     */

    public static void setWatermarkWidthValue(String watermarkWidth) {
        WATERMARK_WIDTH = watermarkWidth;
    }

    @Value("${watermark.width:240}")
    /**
     * Author：houzheng
     * Date：11-18
     * 设置水印宽度
     *
     */

    public void setWatermarkWidth(String watermarkWidth) {
        WATERMARK_WIDTH = watermarkWidth;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取水印高度
     *
     */

    public static String getWatermarkHeight() {
        return WATERMARK_HEIGHT;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置水影高度值
     *
     */

    public static void setWatermarkHeightValue(String watermarkHeight) {
        WATERMARK_HEIGHT = watermarkHeight;
    }

    @Value("${watermark.height:80}")
    /**
     * Author：houzheng
     * Date：11-18
     * 设置水印高度
     *
     */
    public void setWatermarkHeight(String watermarkHeight) {
        WATERMARK_HEIGHT = watermarkHeight;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取水印角度
     *
     */

    public static String getWatermarkAngle() {
        return WATERMARK_ANGLE;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置水印角度值
     *
     */

    public static void setWatermarkAngleValue(String watermarkAngle) {
        WATERMARK_ANGLE = watermarkAngle;
    }

    @Value("${watermark.angle:10}")
    /**
     * Author：houzheng
     * Date：11-18
     * 设置水印角度
     *
     */

    public void setWatermarkAngle(String watermarkAngle) {
        WATERMARK_ANGLE = watermarkAngle;
    }


}
