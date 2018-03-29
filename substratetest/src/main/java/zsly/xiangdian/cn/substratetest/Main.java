package zsly.xiangdian.cn.substratetest;

import android.util.Log;

import com.saurik.substrate.MS;

import java.lang.reflect.Method;

/**
 * Created by XiangDian on 2018/1/18.
 */

public class Main {

    static void initialize() {

        MS.hookClassLoad("android.telephony.TelephonyManager", new MS.ClassLoadHook() {


            @Override
            public void classLoaded(Class<?> aClass) {
                Method getDeviceID;
                try {
                    getDeviceID = aClass.getMethod("getDeviceID");

                } catch (Exception e) {
                    getDeviceID = null;
                }

                if (getDeviceID != null) {
                    final MS.MethodPointer old = new MS.MethodPointer();
                    MS.hookMethod(aClass, getDeviceID, new MS.MethodHook() {


                        @Override
                        public Object invoked(Object o, Object... objects) throws Throwable {
                            Object result = null;
                            try {
                                Log.i("yang", "hook imei start...");
                                result = old.invoke(o, objects);
                                Log.i("yang", "before hook  reult:" + result);
                                result = "fuour brother";
                            } catch (Exception e) {
                                Log.i("yang", "hook imei err:" + Log.getStackTraceString(e));
                            }
                            return result;
                        }
                    }, old);

                } else {
                    Log.i("yang", "getDeviceID!=null");
                }

            }
        });

        MS.hookClassLoad("android.content.res.Resources", new MS.ClassLoadHook() {


            @Override
            public void classLoaded(Class<?> aClass) {
                Method getColor;
                try {
                    getColor = aClass.getMethod("getColor", Integer.TYPE);
                } catch (Exception e) {
                    getColor = null;
                }

                if (getColor != null) {

                    final MS.MethodPointer old = new MS.MethodPointer<>();
                    MS.hookMethod(aClass, getColor, new MS.MethodHook() {


                        @Override
                        public Object invoked(Object o, Object... objects) throws Throwable {
                            try {
                                int color = (Integer) old.invoke(o, objects);
                                return color & ~0x0000ff00 | 0x00ff0000;

                            } catch (Exception e) {
                                Log.i("yang", "hook getColor err:" + Log.getStackTraceString(e));
                            }

                            return 0xffffffff;
                        }
                    }, old);
                } else {
                    Log.i("yang", "getColor!=null");
                }
            }
        });

    }
}
