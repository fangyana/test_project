package com.f.pro.common.util;

import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.FileOutputStream;

/**
 * @Description 加水印 TODO  待验证
 * @Author FangYN
 * @Date 2020/4/13
 **/
public class ImageWaterMark {
    public static void main(String[] args) throws Exception {
        String path = "";
        String outPath = "";
        String waterImgPath = "";
        ImageWaterMark.addPdfImageWatermark(path, outPath, waterImgPath);
    }

    /**
     * 给pdf文件添加水印
     *
     * @param inPath       pdf文件全路径
     * @param outPath      生成的带水印的pdf文件全路径
     * @param waterImgPath 水印图片全路径
     * @throws Exception
     */
    public static void addPdfImageWatermark(String inPath, String outPath, String waterImgPath) throws Exception {
        PdfReader reader = new PdfReader(inPath, "PDF".getBytes());
        int pageCount = reader.getNumberOfPages();
        PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(outPath));

        // 插入水印
        Image img = Image.getInstance(waterImgPath);
        img.setAbsolutePosition(150, 100);
        img.setAlignment(Image.DEFAULT);

        // 设置透明度为0.5
        PdfGState gs = new PdfGState();
        gs.setFillOpacity(0.5f);

        for (int i = 1; i <= pageCount; i++) {
            //PdfContentByte under = stamp.getUnderContent(i);
            PdfContentByte under = stamp.getOverContent(i);
            //注意：应先设透明度，再添加图片，否则无效
            under.setGState(gs);
            under.addImage(img);
        }
        stamp.close();
    }
}
