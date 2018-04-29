package zsly.xiangdian.cn.xposeddemo;
import android.content.Context;
import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.Date;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class Tutorial implements IXposedHookLoadPackage {

    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals("com.xkdandroid.p007"))
            return;
        XposedBridge.log("----------目前在包com.xkdandroid.p007中------------");
        try {
            findAndHookMethod("com.xkdandroid.p005.calls.activity.CallingUI", lpparam.classLoader, "getVideo_durations",new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    param.setResult(1);
                    XposedBridge.log("getVideo_durations   1...");
                }
            });
            findAndHookMethod("com.xkdandroid.p005.calls.api.presenter.CallingPresenter$2", lpparam.classLoader, "success",String.class,new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    String result = param.args[0].toString();
                    String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                    param.args[0]= "{\"items\":{\"uid\":1341114,\"to_uid\":\"1259021\",\"corn_price\":500,\"status\":0,\"updated\":\""+format+"\",\"created\":\""+format+"\",\"id\":2232382},\"err\":0,\"msg\":\"\",\"server_time\":\""+format+"\"}";
                    XposedBridge.log("success resp "+ param.args[0]);
                }
            });
            findAndHookMethod("com.xkdandroid.p005.calls.CallingOutGoingAgent", lpparam.classLoader, "continueToDo",int.class,int.class,int.class,boolean.class,float.class,new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    param.args[0]=999999;
                    param.args[1]=500;
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
            try{
                Class<?> IResultCallback = XposedHelpers.findClass(" com.xkdandroid.p005.app.common.api.callback.IResultCallback", lpparam.classLoader);
                XposedHelpers.findAndHookMethod("com.xkdandroid.p005.messages.api.biz.impl.MessageBiz", lpparam.classLoader, "noticeMessageSendSuccess", Context.class,
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

                        XposedHelpers.findAndHookMethod("com.xkdandroid.p005.messages.api.model.VideoCallInfo", lpparam.classLoader,"getDuration", new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                super.afterHookedMethod(param);
                                Log.i("yang", "duration :" + param.getResult());
                                param.setResult(1);
                                XposedBridge.log("----------hook VideoCallInfo getDuration ------------");
                            }
                        });
                        XposedHelpers.findAndHookMethod("com.xkdandroid.p005.calls.activity.CallingDatas", lpparam.classLoader,"getFree_man_seconds", new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                super.afterHookedMethod(param);
                                Log.i("yang", "getFree_man_seconds :" + param.getResult());
                                param.setResult(1000000);
                                XposedBridge.log("----------hook getFree_man_seconds ------------");
                            }
                        });
                        XposedHelpers.findAndHookMethod("com.xkdandroid.p005.calls.activity.CallingDatas",lpparam.classLoader, "getOffer_price", new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                super.afterHookedMethod(param);
                                param.setResult(500);
                                XposedBridge.log("----------getOffer_price 1------------");
                            }
                        });
                        Class<?> aClass = XposedHelpers.findClass("com.xkdandroid.p005.calls.api.views.ICallingView", lpparam.classLoader);
                        XposedHelpers.findAndHookMethod("com.xkdandroid.p005.calls.api.presenter.CallingPresenter",lpparam.classLoader, "videoEnd", Context.class,long.class,int.class,aClass,new XC_MethodHook() {
                            @Override
                            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                                Log.d("yang", "come in videoEnd");
                                param.args[2] = 1;
                                XposedBridge.log("set  videoEnd 1");
                            }
                        });

//                        XposedHelpers.findAndHookMethod("com.xkdandroid.p005.app.common.config.preference.SysPreferences",lpparam.classLoader, "getisChargeFree", new XC_MethodHook() {
//                            @Override
//                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                                param.setResult(true);
//                                XposedBridge.log("getisChargeFree  true");
//                            }
//                        });
        } catch (Exception e) {
            Log.getStackTraceString(e);
        }
    }
}