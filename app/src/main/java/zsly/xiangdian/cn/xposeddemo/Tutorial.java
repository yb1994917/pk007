package zsly.xiangdian.cn.xposeddemo;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.lang.reflect.Field;

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
//                        XposedHelpers.findAndHookMethod(clazzCallingPresenter, "videoEnd", Context.class,long.class,int.class,aClass,new XC_MethodHook() {
//                            @Override
//                            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                                Log.d("yang", "come in videoEnd");
//                                param.args[2] = 1;
//                                XposedBridge.log("set  videoEnd 1");
//                            }
//                        });

                        XposedHelpers.findAndHookMethod(clazzCallingPresenter, "videoEnd", Context.class, long.class, int.class, aClass, new XC_MethodReplacement() {
                            @Override
                            protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                                return null;
                            }
                        });
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