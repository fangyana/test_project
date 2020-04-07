package com.f.pro.common.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author: FangYN
 * @Date: 2020/4/3
 * @Description: 阿里表格解析工具类
 */
public class WorkBookUtil {
    private static Logger log = LoggerFactory.getLogger(WorkBookUtil.class);

    private static Sheet initSheet;

    static {
        initSheet = new Sheet(1, 0);
        initSheet.setSheetName("sheet");
        //设置自适应宽度
        initSheet.setAutoWidth(Boolean.TRUE);
    }

    public static <T> List<T> getWorkbook(InputStream inStr, Class<T> cls) {
        List<T> listMap = EasyExcel.read(inStr).head(cls).sheet().doReadSync();
        return listMap;
    }

    public static OutputStream writeXls(OutputStream outstream, List<? extends BaseRowModel> list) {

        return outstream;
    }


    public static OutputStream writeSimpleBySheet(OutputStream outputStream, List<List<Object>> data, List<String> head, Sheet sheet) {
        sheet = (sheet != null) ? sheet : initSheet;

        if (head != null) {
            List<List<String>> list = new ArrayList<>();
            head.forEach(h -> list.add(Collections.singletonList(h)));
            sheet.setHead(list);
        }
        ExcelWriter writer = EasyExcelFactory.getWriter(outputStream);
        writer.write1(data, sheet);

        if (writer != null) {
            writer.finish();
        }

        return outputStream;

    }
}
