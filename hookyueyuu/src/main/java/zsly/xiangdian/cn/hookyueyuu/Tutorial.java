package zsly.xiangdian.cn.hookyueyuu;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.findClass;

public class Tutorial implements IXposedHookLoadPackage {

    private Class clazzCallingDatas;
    private Class ItemCourse;
    private Class courseDetail;
    private Class clazzCallingPresenter;
    private Class clazzSysPreferences;
    private Class<?> clazzCallingUI;
    private Class<?> loginBiz;


    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
        if (lpparam.packageName.equals("com.ur.mh")){
            hookURBOX(lpparam);
            return;
        }
        if (!lpparam.packageName.equals("com.huahua.yueyu") && !lpparam.packageName.equals("com.easysay.poem") &&
                !lpparam.packageName.equals("com.easysay.korean") &&  !lpparam.packageName.equals("com.easysay.german") &&
                !lpparam.packageName.equals("com.easysay.japanese") &&   !lpparam.packageName.equals("com.easysay.learningpth")
                &&  !lpparam.packageName.equals("com.easysay.french") &&  !lpparam.packageName.equals("com.easysay.espanol")
                ){
            XposedBridge.log("----------now    in-----"+lpparam.packageName+"------------");
            return;
        }


        XposedBridge.log("----------now    in-----"+lpparam.packageName+"------------");
        Log.d("YUEYU","----------now    in-----"+lpparam.packageName+"------------");
        try {
            XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                     //Xposed 只要出现了  NoClassFoudException 代码就不会继续走了 catch也一样啊  不走
                    if (lpparam.packageName.equals("com.huahua.yueyu") || lpparam.packageName.equals("com.easysay.korean") || lpparam.packageName.equals("com.easysay.japanese") ){
                        try{
                            findAndHookMethod("com.hc.uschool.views.BuyVipActivity", lpparam.classLoader, "onActivityResult", int.class,int.class, Intent.class,new XC_MethodHook() {
                                @Override
                                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                         param.thisObject
                                    Intent intent = (Intent) param.args[2];
                                    String pay_result = intent.getExtras().getString("pay_result");
                                    XposedBridge.log("result"+pay_result);
                                    intent.putExtra("pay_result","success");
                                }
                            });

                            findAndHookMethod("com.hc.uschool.views.BuyPointActivity", lpparam.classLoader, "onActivityResult", int.class,int.class, Intent.class,new XC_MethodHook() {
                                @Override
                                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                            param.thisObject
                                    Intent intent = (Intent) param.args[2];
                                    String pay_result = intent.getExtras().getString("pay_result");
                                    XposedBridge.log("result"+pay_result);
                                    intent.putExtra("pay_result","success");
                                }
                            });
                        }catch (Exception e1){
                        }
                        ClassLoader loader = ((Context) param.args[0]).getClassLoader();
                        try {
                            ItemCourse = loader.loadClass("com.hc.uschool.databinding_bean.ItemCourse");
                            courseDetail = loader.loadClass("com.hc.uschool.model.bean.CourseDetail");
                        } catch (Exception e) {
                            Log.i("yang", "load videoCallInfo err:" + Log.getStackTraceString(e));
                        }
                        if (courseDetail!=null){
                            XposedHelpers.findAndHookMethod(courseDetail, "getIsUnlock", new XC_MethodHook() {
                                @Override
                                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                    super.afterHookedMethod(param);
                                    param.setResult(true);
                                    XposedBridge.log("----------hook ItemCourse getIsUnlock ------------");
                                }
                            });

                        }
                        if (ItemCourse != null) {
                            XposedHelpers.findAndHookMethod(ItemCourse, "getPrice", new XC_MethodHook() {
                                @Override
                                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                    super.afterHookedMethod(param);
                                    Log.i("yang", "duration :" + param.getResult());
                                    param.setResult(0);
                                    XposedBridge.log("----------hook ItemCourse getPrice ------------");
                                }
                            });
                            XposedHelpers.findAndHookMethod(ItemCourse, "setPrice",int.class, new XC_MethodHook() {
                                @Override
                                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                                    super.beforeHookedMethod(param);
                                    param.args[0]=0;
                                    XposedBridge.log("----------hook ItemCourse setPrice ------------");
                                }
                            });

                            XposedHelpers.findAndHookMethod(ItemCourse, "isUnlock", new XC_MethodHook() {
                                @Override
                                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                    super.afterHookedMethod(param);
                                    param.setResult(true);
                                    XposedBridge.log("----------hook ItemCourse setPrice ------------");
                                }
                            });
                        }


                    }else {
                        try {
                            findAndHookMethod("com.easysay.korean.BuyNewActivity", lpparam.classLoader, "onActivityResult", int.class,int.class, Intent.class,new XC_MethodHook() {
                                @Override
                                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                            param.thisObject
                                    XposedBridge.log("Resultcode:"+param.args[1]);
                                    Intent intent = (Intent) param.args[2];
                                    intent.putExtra("code",1);
                                    intent.putExtra("pay_result","success");
                                }
                            });
                        }catch (Exception e){
                        }

                        try {
                            findAndHookMethod("com.easysay.korean.BuyPointNewActivity", lpparam.classLoader, "onActivityResult", int.class,int.class, Intent.class,new XC_MethodHook() {
                                @Override
                                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                            param.thisObject
                                    XposedBridge.log("Resultcode:"+param.args[1]);
                                    Log.d("YUEYU","Resultcode:"+param.args[1]);
                                    Intent intent = (Intent) param.args[2];
                                    intent.putExtra("code",1);
                                    intent.putExtra("pay_result","success");
                                }
                            });
                        }catch (Exception e){
                            XposedBridge.log(e);
                            Log.e("YUEYU",Log.getStackTraceString(e));
                        }
                    }



                    //  String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                    //{"items":{"uid":1351237,"nickname":"\u5ba2\u670d\u4eba\u5458","token":"576e33f02c5ab52396ca63a826b6af46","gender":1,"head_url":"http:\/\/liaomeiapp.img-cn-shanghai.aliyuncs.com\/image\/d76965580253bc6bf32334cf8227d719.png","birthday":"1991-01-01","age":28,"about_me":"fffffffffffffffff","province":"","city":"\u5317\u4eac","status":0,"is_phone_notice":1,"is_notice":1,"created":"2017-08-31 15:36:58","last_login":"2018-01-31 19:58:41","updated":"2018-02-01 10:54:40","platform":"android","level":0,"android_channel":"txyyb","is_majia":0,"pass":"25%","is_video_authentication":0,"is_identity_authentication":0,"is_job_authentication":0,"video_corn_price":70,"is_recommend":0,"is_info_authentication":0,"is_video":0,"tone":"0xff7bcdd5","free":"0000-00-00","scale":"","qr_code":"\/qrcodes\/1351237_qrcode.png","invite_uid":0,"client_version":"1.2.13","mj_voice_base":"","is_new":1,"recommend_time":0,"app_id":"com.xkdandroid.p007","recommend_sum":0,"status_base":0,"top":0,"is_video_sort":0,"dinner":{},"is_dinner":0,"corn":126,"money":{"uid":1351237,"corn":126,"get_corn":126,"post_corn":0,"un_get_corn":0,"updated":"2017-09-16 12:11:36"},"video":{"id":30823,"uid":1351237,"url":"https:\/\/liaomeiapp.oss-cn-shanghai.aliyuncs.com\/2018-01-17\/578a3e56fa207de37acf8b81f0d86334.mp4","status":0,"created":"2018-01-17 16:15:18","updated":"2018-01-17 16:15:18","pic":"","video_pic_url":""},"identity":{},"jobsAuthentication":{},"head_url_status":0},"err":0,"msg":"","server_time":"2018-02-01 10:54:40"}

                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
        }
    }

    private void hookURBOX(final LoadPackageParam lpparam) {

        XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
            findAndHookMethod("tv.danmaku.ijk.exo.demo.UserInfo", lpparam.classLoader, "getIsvip", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    Log.d("TAG","tv.danmaku.ijk.exo.demo.UserInfo getIsvip ");
                    param.setResult(true);
                }
            });
            findAndHookMethod("tv.danmaku.ijk.exo.demo.UserInfo", lpparam.classLoader, "setIsvip", boolean.class,new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    Log.d("TAG","tv.danmaku.ijk.exo.demo.UserInfo setIsvip ");
                    param.setResult(true);
                }
            });
            findAndHookMethod("tv.danmaku.ijk.exo.demo.UserInfo", lpparam.classLoader, "setExpireTime", long.class,new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    long l = System.currentTimeMillis() + 365 * 24 * 60 * 60 * 1000;
                    Log.d("TAG","tv.danmaku.ijk.exo.demo.UserInfo setExpireTime ");
                    param.args[0]=l;
                }
            });

            };
        });

    }

    private void hookOther(LoadPackageParam lpparam) {


        try {
            findAndHookMethod("com.easysay.korean.BuyPointNewActivity", lpparam.classLoader, "onActivityResult", int.class,int.class, Intent.class,new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                            param.thisObject
                    XposedBridge.log("Resultcode:"+param.args[1]);
                    Log.d("YUEYU","Resultcode:"+param.args[1]);
                    Intent intent = (Intent) param.args[2];
                    intent.putExtra("code",1);
                    intent.putExtra("pay_result","success");
                }
            });
        }catch (Exception e){
            XposedBridge.log(e);
            Log.e("YUEYU",Log.getStackTraceString(e));
        }

//        try {
//            findAndHookMethod("com.easysay.korean.BuyActivity", lpparam.classLoader, "onActivityResult", int.class,int.class, Intent.class,new XC_MethodHook() {
//                @Override
//                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
////                            param.thisObject
//                XposedBridge.log("Resultcode:"+param.args[1]);
//                    Intent intent = (Intent) param.args[2];
//                        intent.putExtra("code",1);
//                }
//            });
//        }catch (Exception e){
//                XposedBridge.log(e);
//        }
     try {
            findAndHookMethod("com.easysay.korean.BuyNewActivity", lpparam.classLoader, "onActivityResult", int.class,int.class, Intent.class,new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                            param.thisObject
                    XposedBridge.log("Resultcode:"+param.args[1]);
                    Intent intent = (Intent) param.args[2];
                        intent.putExtra("code",1);
                    intent.putExtra("pay_result","success");
                }
            });
        }catch (Exception e){
         XposedBridge.log(e);
        }

        try {
            findAndHookMethod("com.easysay.korean.BuyPointActivity", lpparam.classLoader, "onActivityResult", int.class,int.class, Intent.class,new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                            param.thisObject
                    XposedBridge.log("Resultcode:"+param.args[1]);
                    Intent intent = (Intent) param.args[2];
                    intent.putExtra("code",1);
                    intent.putExtra("pay_result","success");
                }
            });
        }catch (Exception e){
            XposedBridge.log(e);
        }

        try {
            findAndHookMethod("com.easysay.korean.BuyPointNewActivity", lpparam.classLoader, "onActivityResult", int.class,int.class, Intent.class,new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                            param.thisObject
                    XposedBridge.log("Resultcode:"+param.args[1]);
                    Intent intent = (Intent) param.args[2];
                    intent.putExtra("code",1);
                    intent.putExtra("pay_result","success");
                }
            });
        }catch (Exception e){
            XposedBridge.log(e);
        }
    }


}