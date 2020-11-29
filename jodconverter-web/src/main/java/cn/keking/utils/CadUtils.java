package cn.keking.utils;

import com.aspose.cad.Color;
import com.aspose.cad.fileformats.cad.CadDrawTypeMode;
import com.aspose.cad.imageoptions.CadRasterizationOptions;
import com.aspose.cad.imageoptions.PdfOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

/*
 *Author:FanPan Date:2020-11-17
 *预览Cad文件工具类
 */
/**
 * @author chenjhc
 * @since 2019/11/21 14:34
 */
@Component
public class CadUtils {

    /*
     *Author:FanPan Date:2020-11-17
     *日志logger类打印日志信息
     */
    private final Logger logger = LoggerFactory.getLogger(CadUtils.class);


    /**
     * @Author FanPan
     * @param inputFilePath 文件输入路径
     * @param outputFilePath 文件输出路径
     * @return 文件是否转换成功
     * @see com.aspose.cad.Image
     * 这个方法实现了cad文件到pdf文件的转换
     */
    public boolean cadToPdf(String inputFilePath, String outputFilePath)  {
        /*
         *Author:FanPan Date:2020-11-17
         *Aspose.CAD是用于.Net和Java平台的独立软件库，可读取CAD文件（例如DWG，DXF，DNG，IFC，STL文件）并将其内容导出为PDF文件和光栅图像。
         */
        com.aspose.cad.Image cadImage = com.aspose.cad.Image.load(inputFilePath);

        /*
         *Author:FanPan Date:2020-11-17
         *CadRasterizationOptions指定应如何渲染CAD图像-宽度，高度（以像素为单位），CAD内容应居中，对象的背景颜色和替代颜色等。
         */
        CadRasterizationOptions cadRasterizationOptions = new CadRasterizationOptions();

        /*
         *Author:FanPan Date:2020-11-17
         *获取或设置要导出到AutoCAD文档的布局名称。
         */
        cadRasterizationOptions.setLayouts(new String[]{"Model"});

        /*
         *Author:FanPan Date:2020-11-17
         *定义是否缩放CAD内容以匹配输出图像。 这将被AutomaticLayoutScaling覆盖-如果将其设置为true，则NoScaling将无效。
         */
        cadRasterizationOptions.setNoScaling(true);
        cadRasterizationOptions.setBackgroundColor(Color.getWhite());
        cadRasterizationOptions.setPageWidth(cadImage.getWidth());
        cadRasterizationOptions.setPageHeight(cadImage.getHeight());
        cadRasterizationOptions.setPdfProductLocation("center");
        /*
         *Author:FanPan Date:2020-11-17
         *如果设置为true，则缩放CAD文档内容以输出图像大小，默认为true。
         */
        cadRasterizationOptions.setAutomaticLayoutsScaling(true);
        /*
         *Author:FanPan Date:2020-11-17
         *指示是否使用文档中指定的颜色渲染实体，或使用DrawColor。 默认值为UseDrawColor。
         */
        cadRasterizationOptions.setDrawType(CadDrawTypeMode.UseObjectColor);

        /*
         *Author:FanPan Date:2020-11-17
         *创建了一个PdfOptions实例，该实例显然将输出设置为PDF文件格式
         */
        PdfOptions pdfOptions = new PdfOptions();
        /*
         *Author:FanPan Date:2020-11-17
         *将pdf的渲染方式指定为上面创建的方式
         */
        pdfOptions.setVectorRasterizationOptions(cadRasterizationOptions);

        /*
         *Author:FanPan Date:2020-11-17
         *保存转换后的文件
         */
        File outputFile = new File(outputFilePath);
        OutputStream stream;
        try {
            stream = new FileOutputStream(outputFile);
            cadImage.save(stream, pdfOptions);
            cadImage.close();
            return true;
        } catch (FileNotFoundException e) {
            logger.error("PDFFileNotFoundException，inputFilePath：{}", inputFilePath, e);
            return false;
        }
    }
}
