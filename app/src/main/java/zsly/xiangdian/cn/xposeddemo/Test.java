package zsly.xiangdian.cn.xposeddemo;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by XiangDian on 2018/1/29.
 */

public class Test {
        public  static  void  main(String[] args) throws JSONException {
//          String json = "{\"items\":{\"uid\":1477455,\"to_uid\":\"1259021\",\"corn_price\":500,\"status\":0,\"updated\":\"2018-01-29 20:42:03\",\"created\":\"2018-01-29 20:42:03\",\"id\":2232382},\"err\":0,\"msg\":\"\",\"server_time\":\"2018-01-29 20:42:03\"}";
//            String s ="11";
            String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            System.out.println(format);
        }
}
