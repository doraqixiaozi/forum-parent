package util;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * CommonUtil
 *
 * @author 862965251@qq.com
 * @date 2020-04-08 9:58
 */
public class CommonUtil {
    /**
     * 将vue前端传来的日期转为日期对象
     * @param oldDate
     * @return
     * @throws ParseException
     */
    public static Date convertUtil(String oldDate) throws ParseException {
//        String date=oldDate.replace("Z"," UTC");
        Date parseDate = DateUtils.parseDate(oldDate, "yyyy-MM-dd HH:mm:ss");
        return parseDate;
    }
}
