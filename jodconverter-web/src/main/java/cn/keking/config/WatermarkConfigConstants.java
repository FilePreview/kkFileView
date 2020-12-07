package cn.keking.config;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author chenjh
 * @since 2020/5/13 17:44
 */
public class WatermarkConfigConstants {

    private static String watermarkTxt;
    private static String watermarkXSpace;
    private static String watermarkYSpace;
    private static String watermarkFont;
    private static String watermarkFontsize;
    private static String watermarkColor;
    private static String watermarkAlpha;
    private static String watermarkWidth;
    private static String watermarkHeight;
    private static String watermarkAngle;

    public static final String DEFAULT_WATERMARK_TXT = "";
    public static final String DEFAULT_WATERMARK_X_SPACE = "10";
    public static final String DEFAULT_WATERMARK_Y_SPACE = "10";
    public static final String DEFAULT_WATERMARK_FONT = "微软雅黑";
    public static final String DEFAULT_WATERMARK_FONTSIZE = "18px";
    public static final String DEFAULT_WATERMARK_COLOR = "black";
    public static final String DEFAULT_WATERMARK_ALPHA = "0.2";
    public static final String DEFAULT_WATERMARK_WIDTH = "240";
    public static final String DEFAULT_WATERMARK_HEIGHT = "80";
    public static final String DEFAULT_WATERMARK_ANGLE = "10";

    /**
     * Author：houzheng
     * Date：11-18
     * 获取水印文本
     *
     */

    public static String getWatermarkTxt() {
        return watermarkTxt;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置水印文本值
     *
     */

    public static void setWatermarkTxtValue(String watermarkTxt) {
        WatermarkConfigConstants.watermarkTxt = watermarkTxt;
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
        return watermarkXSpace;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置水印水平空间值
     *
     */

    public static void setWatermarkXSpaceValue(String watermarkXSpace) {
        WatermarkConfigConstants.watermarkXSpace = watermarkXSpace;
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
        return watermarkYSpace;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置水印竖直空间值
     *
     */

    public static void setWatermarkYSpaceValue(String watermarkYSpace) {
        WatermarkConfigConstants.watermarkYSpace = watermarkYSpace;
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
        return watermarkFont;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置水印字体值
     *
     */

    public static void setWatermarkFontValue(String watermarkFont) {
        WatermarkConfigConstants.watermarkFont = watermarkFont;
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
        return watermarkFontsize;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置水印字体大小值
     *
     */

    public static void setWatermarkFontsizeValue(String watermarkFontsize) {
        WatermarkConfigConstants.watermarkFontsize = watermarkFontsize;
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
        return watermarkColor;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置水印颜色值
     *
     */

    public static void setWatermarkColorValue(String watermarkColor) {
        WatermarkConfigConstants.watermarkColor = watermarkColor;
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
        return watermarkAlpha;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置水印Alpha值
     *
     */

    public static void setWatermarkAlphaValue(String watermarkAlpha) {
        WatermarkConfigConstants.watermarkAlpha = watermarkAlpha;
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
        return watermarkWidth;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置水印宽度值
     *
     */

    public static void setWatermarkWidthValue(String watermarkWidth) {
        WatermarkConfigConstants.watermarkWidth = watermarkWidth;
    }

    @Value("${watermark.width:240}")
    /**
     * Author：houzheng
     * Date：11-18
     * 设置水印宽度
     *
     */

    public static void setWatermarkWidth(String watermarkWidth) {
        WatermarkConfigConstants.watermarkWidth = watermarkWidth;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取水印高度
     *
     */

    public static String getWatermarkHeight() {
        return watermarkHeight;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置水影高度值
     *
     */

    public static void setWatermarkHeightValue(String watermarkHeight) {
        WatermarkConfigConstants.watermarkHeight = watermarkHeight;
    }

    @Value("${watermark.height:80}")
    /**
     * Author：houzheng
     * Date：11-18
     * 设置水印高度
     *
     */
    public static void setWatermarkHeight(String watermarkHeight) {
        WatermarkConfigConstants.watermarkHeight = watermarkHeight;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取水印角度
     *
     */

    public static String getWatermarkAngle() {
        return watermarkAngle;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置水印角度值
     *
     */

    public static void setWatermarkAngleValue(String watermarkAngle) {
        WatermarkConfigConstants.watermarkAngle = watermarkAngle;
    }

    /**
     * Author：houzheng
     * Date：11-18
     * 设置水印角度
     *
     */
    @Value("${watermark.angle:10}")
    public static void setWatermarkAngle(String watermarkAngle) {
        WatermarkConfigConstants.watermarkAngle = watermarkAngle;
    }


}