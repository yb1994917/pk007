package zsly.xiangdian.cn.xposeddemo;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.findClass;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class Tutorial implements IXposedHookLoadPackage {

    private Class clazzCallingDatas;
    private Class clazzVideoCallInfo;
    private Class clazzCallingPresenter;
    private Class clazzSysPreferences;
    private Class<?> clazzCallingUI;
    private Class<?> loginBiz;


    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals("com.xkdandroid.p007"))
            return;
        XposedBridge.log("----------目前在包com.xkdandroid.p007中------------");
//        findAndHookMethod("com.xkdandroid.p005.calls.activity.CallingUI",lpparam.classLoader,"")
//        XposedBridge.log("set  has_hangUp true");

//        findAndHookMethod("com.xkdandroid.p007", lpparam.classLoader, "updateClock", new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                // this will be called before the clock was updated by the original method
//            }
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                // this will be called after the clock was updated by the original method
//            }
//    });

        try {
            XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    ClassLoader loader = ((Context) param.args[0]).getClassLoader();
                    try {
                        clazzVideoCallInfo = loader.loadClass("com.xkdandroid.p005.messages.api.model.VideoCallInfo");
                    } catch (Exception e) {
                        Log.i("yang", "load videoCallInfo err:" + Log.getStackTraceString(e));
                    }
                    if (clazzVideoCallInfo != null) {
                        XposedHelpers.findAndHookMethod(clazzVideoCallInfo, "getDuration", new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                super.afterHookedMethod(param);
                                Log.i("yang", "duration :" + param.getResult());
                                param.setResult(1);
                                XposedBridge.log("----------hook VideoCallInfo getDuration ------------");
                            }
                        });
                    }



                    XposedBridge.log("-------------------------------------------------------------------------");
                    try {
                        loginBiz = loader.loadClass("com.xkdandroid.p005.main.api.presenter.LoginPresenter$1");
                    } catch (Exception e) {
                        Log.i("yang", " TaUserInfoPresenter$1:" + Log.getStackTraceString(e));
                    }

                    if (loginBiz != null) {
                        XposedHelpers.findAndHookMethod(loginBiz, "success",String.class, new XC_MethodHook() {
                            @Override
                            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                                XposedBridge.log("hook      data----" +param.args[0]);
                                String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                                String data = "{\"items\":{\"uid\":1351237,\"nickname\":\"\\u5ba2\\u670d\\u4eba\\u5458\",\"token\":\"64fc22cea2dd52437a6bfa213bf58a1e\",\"gender\":1,\"head_url\":\"http:\\/\\/liaomeiapp.img-cn-shanghai.aliyuncs.com\\/image\\/d76965580253bc6bf32334cf8227d719.png\",\"birthday\":\"1991-01-01\",\"age\":28,\"about_me\":\"fffffffffffffffff\",\"province\":\"\",\"city\":\"\\u5317\\u4eac\",\"status\":0,\"is_phone_notice\":1,\"is_notice\":1,\"created\":\"2017-08-31 15:36:58\",\"last_login\":\"2018-01-31 19:58:41\",\"updated\":\""+format+"\",\"platform\":\"android\",\"level\":0,\"android_channel\":\"txyyb\",\"is_majia\":0,\"pass\":\"25%\",\"is_video_authentication\":0,\"is_identity_authentication\":0,\"is_job_authentication\":0,\"video_corn_price\":70,\"is_recommend\":0,\"is_info_authentication\":0,\"is_video\":0,\"tone\":\"0xff7bcdd5\",\"free\":\"0000-00-00\",\"scale\":\"\",\"qr_code\":\"\\/qrcodes\\/1351237_qrcode.png\",\"invite_uid\":0,\"client_version\":\"1.2.13\",\"mj_voice_base\":\"\",\"is_new\":1,\"recommend_time\":0,\"app_id\":\"com.xkdandroid.p007\",\"recommend_sum\":0,\"status_base\":0,\"top\":0,\"is_video_sort\":0,\"dinner\":{},\"is_dinner\":0,\"corn\":126,\"money\":{\"uid\":1351237,\"corn\":126,\"get_corn\":126,\"post_corn\":0,\"un_get_corn\":0,\"updated\":\"2017-09-16 12:11:36\"},\"video\":{\"id\":30823,\"uid\":1351237,\"url\":\"https:\\/\\/liaomeiapp.oss-cn-shanghai.aliyuncs.com\\/2018-01-17\\/578a3e56fa207de37acf8b81f0d86334.mp4\",\"status\":0,\"created\":\"2018-01-17 16:15:18\",\"updated\":\"2018-01-17 16:15:18\",\"pic\":\"\",\"video_pic_url\":\"\"},\"identity\":{},\"jobsAuthentication\":{},\"head_url_status\":0},\"err\":0,\"msg\":\"\",\"server_time\":\""+format+"\"}";
                                param.args[0] =data;
                            }
                        });
                    }

                    try {
                        clazzCallingDatas = loader.loadClass("com.xkdandroid.p005.calls.activity.CallingDatas");
                    } catch (Exception e) {
                        Log.i("yang", "load CallingDatas err:" + Log.getStackTraceString(e));
                    }

                    if (clazzCallingDatas != null) {

                        XposedHelpers.findAndHookMethod(clazzCallingDatas, "getFree_man_seconds", new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                super.afterHookedMethod(param);
                                Log.i("yang", "getFree_man_seconds :" + param.getResult());
                                param.setResult(1000000);
                                XposedBridge.log("----------hook getFree_man_seconds ------------");
                            }
                        });


                        XposedHelpers.findAndHookMethod(clazzCallingDatas, "getOffer_price", new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                super.afterHookedMethod(param);
                                param.setResult(1);
                                XposedBridge.log("----------getOffer_price 1------------");
                            }
                        });
                    }

                    findAndHookMethod("com.xkdandroid.p005.calls.activity.CallingUI", lpparam.classLoader, "hangUp", int.class,new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                            param.thisObject
                            Field has_hangUp = XposedHelpers.findField(param.thisObject.getClass(), "has_hangUp");
                            has_hangUp.setAccessible(true);
                            has_hangUp.set( param.thisObject,true);
                            XposedBridge.log("set  has_hangUp true");
                        }
                    });
                    findAndHookMethod("com.xkdandroid.p005.calls.activity.CallingUI", lpparam.classLoader, "getVideo_durations",new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            param.setResult(1);
                            XposedBridge.log("getVideo_durations   1...");
                        }
                    });
                    //  String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                    //{"items":{"uid":1351237,"nickname":"\u5ba2\u670d\u4eba\u5458","token":"576e33f02c5ab52396ca63a826b6af46","gender":1,"head_url":"http:\/\/liaomeiapp.img-cn-shanghai.aliyuncs.com\/image\/d76965580253bc6bf32334cf8227d719.png","birthday":"1991-01-01","age":28,"about_me":"fffffffffffffffff","province":"","city":"\u5317\u4eac","status":0,"is_phone_notice":1,"is_notice":1,"created":"2017-08-31 15:36:58","last_login":"2018-01-31 19:58:41","updated":"2018-02-01 10:54:40","platform":"android","level":0,"android_channel":"txyyb","is_majia":0,"pass":"25%","is_video_authentication":0,"is_identity_authentication":0,"is_job_authentication":0,"video_corn_price":70,"is_recommend":0,"is_info_authentication":0,"is_video":0,"tone":"0xff7bcdd5","free":"0000-00-00","scale":"","qr_code":"\/qrcodes\/1351237_qrcode.png","invite_uid":0,"client_version":"1.2.13","mj_voice_base":"","is_new":1,"recommend_time":0,"app_id":"com.xkdandroid.p007","recommend_sum":0,"status_base":0,"top":0,"is_video_sort":0,"dinner":{},"is_dinner":0,"corn":126,"money":{"uid":1351237,"corn":126,"get_corn":126,"post_corn":0,"un_get_corn":0,"updated":"2017-09-16 12:11:36"},"video":{"id":30823,"uid":1351237,"url":"https:\/\/liaomeiapp.oss-cn-shanghai.aliyuncs.com\/2018-01-17\/578a3e56fa207de37acf8b81f0d86334.mp4","status":0,"created":"2018-01-17 16:15:18","updated":"2018-01-17 16:15:18","pic":"","video_pic_url":""},"identity":{},"jobsAuthentication":{},"head_url_status":0},"err":0,"msg":"","server_time":"2018-02-01 10:54:40"}
                    findAndHookMethod("com.xkdandroid.p005.calls.api.presenter.CallingPresenter$2", lpparam.classLoader, "success",String.class,new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                           String result = param.args[0].toString();
//                           param.args[0]="{\"items\":{\"uid\":1477455,\"to_uid\":\"1259021\",\"corn_price\":500,\"status\":0,\"updated\":\"2018-01-29 20:42:03\",\"created\":\"2018-01-29 20:42:03\",\"id\":2232382}\n" +
//                                   ",\"err\":0,\"msg\":\"\",\"server_time\":\"2018-01-29 20:42:03\"}";
                            String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                            param.args[0]= "{\"items\":{\"uid\":1341114,\"to_uid\":\"1259021\",\"corn_price\":500,\"status\":0,\"updated\":\""+format+"\",\"created\":\""+format+"\",\"id\":2232382},\"err\":0,\"msg\":\"\",\"server_time\":\""+format+"\"}";
                            XposedBridge.log("success resp "+ param.args[0]);
                        }
                    });

                    findAndHookMethod("com.xkdandroid.p005.calls.CallingOutGoingAgent", lpparam.classLoader, "continueToDo",int.class,int.class,int.class,boolean.class,float.class,new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                                param.args[0]=999999;
                                param.args[1]=1;

                                XposedBridge.log("hook  continueToDo...");
                        }
                    });

                    findAndHookMethod("com.xkdandroid.p005.calls.CallingOutGoingAgent", lpparam.classLoader, "toVideo",boolean.class,int.class,int.class,float.class,new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            param.args[0]=false;   //isShowProgress
                            param.args[1]=1; //price
                                param.args[2]=999999; //free_man_seconds
                                param.args[3]=2.0f; //free_man_seconds
                                XposedBridge.log("hook  toVideo...");
                        }
                    });

//                    try {
//                        clazzCallingUI = loader.loadClass("com.xkdandroid.p005.calls.activity.CallingUI");
//                    } catch (Exception e) {
//                        XposedBridge.log(e);
//                    }
//
//                    if (clazzCallingUI != null) {
//                        XposedBridge.log("----------hook CallingUI ------------");
//                        Field has_hangUp = XposedHelpers.findField(clazzCallingUI, "has_hangUp");
//                        XposedBridge.log("has_hangUp  type:"+has_hangUp.getType().getName());
//                        has_hangUp.setAccessible(true);
//                        has_hangUp.set(clazzCallingUI, true);
//                        Log.i("yang", "set(clazzCallingUI,true);.....");
//                        XposedHelpers.findAndHookMethod(clazzCallingDatas, "getFree_man_seconds", new XC_MethodHook() {
//                            @Override
//                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                                super.afterHookedMethod(param);
//                                Log.i("yang", "duration :" + param.getResult());
//                                param.setResult(1000000);
//                            }
//                        });
//                    }

                    try {
                        clazzCallingPresenter = loader.loadClass("com.xkdandroid.p005.calls.api.presenter.CallingPresenter");
                    } catch (Exception e) {
                        Log.i("yang", "load clazzCallingPresenter err:" + Log.getStackTraceString(e));
                    }
                    if (clazzCallingPresenter != null) {
                        Class<?> aClass = XposedHelpers.findClass("com.xkdandroid.p005.calls.api.views.ICallingView", loader);
                        XposedHelpers.findAndHookMethod(clazzCallingPresenter, "videoEnd", Context.class,long.class,int.class,aClass,new XC_MethodHook() {
                            @Override
                            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                                Log.d("yang", "come in videoEnd");
                                param.args[2] = 1;
                                XposedBridge.log("set  videoEnd 1");
                            }
                        });

//                        XposedHelpers.findAndHookMethod(clazzCallingPresenter, "videoEnd", Context.class, long.class, int.class, aClass, new XC_MethodReplacement() {
//                            @Override
//                            protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
//                                return null;
//                            }
//                        });
                    }

                    try{
                        Class<?> IResultCallback = XposedHelpers.findClass(" com.xkdandroid.p005.app.common.api.callback.IResultCallback", loader);
                        XposedHelpers.findAndHookMethod("com.xkdandroid.p005.messages.api.biz.impl.MessageBiz", loader, "noticeMessageSendSuccess", Context.class,
                                String.class, String.class, IResultCallback, new XC_MethodReplacement() {
                                    @Override
                                    protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                                        XposedBridge.log("noticeMessageSendSuccess hook ..");
                                        return null;
                                    }
                                });
                    }catch (Exception e){
                        XposedBridge.log(e);
                    }

                    try {
                        clazzSysPreferences = loader.loadClass("com.xkdandroid.p005.app.common.config.preference.SysPreferences");
                    } catch (Exception e) {
                        Log.i("yang", "load SysPreferences err:" + Log.getStackTraceString(e));
                    }

                    if (clazzSysPreferences != null) {

                        XposedHelpers.findAndHookMethod(clazzSysPreferences, "getisChargeFree", new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                param.setResult(true);
                                XposedBridge.log("getisChargeFree  true");
                            }
                        });
                    }
                }
            });
        } catch (Exception e) {

            Log.getStackTraceString(e);
        }
    }


}