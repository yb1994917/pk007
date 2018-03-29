package zsly.xiangdian.cn.hookyueyuu;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.util.Log;
import android.view.View;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.findClass;
import static de.robv.android.xposed.XposedHelpers.findField;

/**
 * Created by XiangDian on 2018/3/20.
 */

public class HookTianHu  implements IXposedHookLoadPackage {
    private File file = new File(Environment.getExternalStorageDirectory()+"/urbox/urllist.txt");
    private File dir = new File(Environment.getExternalStorageDirectory()+"/urbox");
    private Class clazzCallingDatas;
    private Class ItemCourse;
    private Class courseDetail;
    private Class clazzCallingPresenter;
    private Class clazzSysPreferences;
    private Class<?> clazzCallingUI;
    private Class<?> loginBiz;
    private List<String> classes;


    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (lpparam.packageName.equals("com.baidu.mobads.demo.main")){
            hookBaidu(lpparam);
            return;
        }

        if (lpparam.packageName.equals("com.love.club.sv")){
            hooklove(lpparam);
            return;
        }

        if (lpparam.packageName.equals("com.esky.echat")){
            hookFuLiao(lpparam);
            return;
        }

        if (lpparam.packageName.equals("com.qtd.aacd")){
        if (!dir.exists()) dir.mkdir();
            if(!file.exists()){
                file.createNewFile();
            }
            hookURBOX(lpparam);
            return;
        }
        if (lpparam.packageName.equals("com.live.tianhu")){
            hookTianhu(lpparam);
            return;
        }
    }

    private void hooklove(final XC_LoadPackage.LoadPackageParam lpparam) throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                findAndHookMethod("com.love.club.sv.bean.PayResult", lpparam.classLoader, "getResultStatus", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                            param.setResult(9000);
                    }
                });
            }



        });
        findAndHookMethod("com.love.club.sv.base.ui.view.RechargeDialogActivity", lpparam.classLoader,"onCreate", Bundle.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Class<?> aClass = param.thisObject.getClass();
                Field u = findField(aClass, "u");
                u.setAccessible(true);
                u.set(param.thisObject,"5");
            }
        });   findAndHookMethod("com.love.club.sv.base.ui.view.RechargeDialogActivity", lpparam.classLoader,"onCreate", Bundle.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Class<?> aClass = param.thisObject.getClass();
                Field u = findField(aClass, "u");
                u.setAccessible(true);
                u.set(param.thisObject,"5");
            }
        });        findAndHookMethod("com.netease.nim.uikit.session.fragment.MessageFragment", lpparam.classLoader,"onCreate", Bundle.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Class<?> aClass = param.thisObject.getClass();
                Field u = findField(aClass, "u");
                u.setAccessible(true);
                u.set(param.thisObject,"5");
            }
        });  findAndHookMethod("com.netease.nim.uikit.bean.EnergyQMDBean", lpparam.classLoader,"getSweetLevel",  new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                param.setResult(8);
            }
        });findAndHookMethod("com.netease.nim.uikit.bean.EnergyQMDBean", lpparam.classLoader,"getCoin",  new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                param.setResult(20000);
            }
        });
    }
//    http://xposed.appkg.com/wordpress/down.php?id=2799    Virtual Xposed
    private void hookFuLiao(final XC_LoadPackage.LoadPackageParam lpparam) {
        XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                findAndHookMethod("com.esky.echat.common.data.entity.PayInfoEntity", lpparam.classLoader, "getEnergyUnit", new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                            Log.d("FULIAO","getEnergyUnit :"+param.getResult());
                            param.setResult(1);
                    }
                });


                findAndHookMethod("com.esky.echat.common.data.entity.PayInfoEntity", lpparam.classLoader, "getIntegralUnit",new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                            Log.d("FULIAO","getIntegralUnit :"+param.getResult());
                            param.setResult(1);
                    }
                });

                findAndHookMethod("com.esky.echat.common.data.entity.PayForImageEntity", lpparam.classLoader, "getEnergy",new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                            Log.d("FULIAO","getIntegralUnit :"+param.getResult());
                            param.setResult(1);
                    }
                });  findAndHookMethod("com.esky.echat.common.data.entity.PreVideoEntity", lpparam.classLoader, "getEnergyUnit",new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                            Log.d("FULIAO","getEnergyUnit :"+param.getResult());
                            param.setResult(1);
                    }
                }); findAndHookMethod("com.esky.echat.common.data.entity.PreVideoEntity", lpparam.classLoader, "getIntegralUnit",new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                            Log.d("FULIAO","getIntegralUnit :"+param.getResult());
                            param.setResult(1);
                    }
                });findAndHookMethod("com.esky.echat.common.data.entity.NoPayDirectionEntity", lpparam.classLoader, "getEnergycounts",new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                            Log.d("FULIAO","getEnergycounts :"+param.getResult());
                            param.setResult(100000);
                    }
                });findAndHookMethod("com.esky.echat.common.data.entity.NoPayDirectionEntity", lpparam.classLoader, "getIsgold",new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                            Log.d("FULIAO","getEnergycounts :"+param.getResult());
                            param.setResult(1);
                    }
                });findAndHookMethod("com.esky.echat.common.data.entity.NoPayDirectionEntity", lpparam.classLoader, "getIssuper",new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                            Log.d("FULIAO","getIssuper :"+param.getResult());
                            param.setResult(1);
                    }
                });findAndHookMethod("com.esky.echat.common.data.entity.NoPayDirectionEntity", lpparam.classLoader, "getViplevel",new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                            Log.d("FULIAO","getIssuper :"+param.getResult());
                            param.setResult(30);
                    }
                });findAndHookMethod("com.esky.echat.common.data.entity.LiveVideoGiftEntity", lpparam.classLoader, "getEnergy_consume",new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                            Log.d("FULIAO","getIssuper :"+param.getResult());
                            param.setResult(1);
                    }
                });
            };
        });



    }

    private void hookBaidu(final XC_LoadPackage.LoadPackageParam lpparam) {
        classes = new ArrayList<>();
        classes.add("com.baidu.mobads.container.g.b");

        findAndHookMethod(ClassLoader.class, "loadClass", String.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                if (param.hasThrowable()) return;
                Class<?> cls = (Class<?>) param.getResult();
                String name = cls.getName();
                if (classes.contains(name)) {
                    // 所有的类都是通过loadClass方法加载的
                    // 所以这里通过判断全限定类名，查找到目标类
                    // 第二步：Hook目标方法
                    findAndHookMethod(cls, "a", new XC_MethodHook() {

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                               Log.d("TAG","json : "+param.getResult().toString());
                        }
                    });
                }
            }
        });


//        XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                findAndHookMethod("com.baidu.mobads.utils.t", lpparam.classLoader, "isAdViewTooSmall", View.class,new XC_MethodHook() {
//                    @Override
//                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                        super.afterHookedMethod(param);
//                        if ( param.args[0]==null){
//                            Log.d("TAG","view == null");
//                        }else {
//                            View arg = (View) param.args[0];
//                            Log.d("TAG","width"+arg.getWidth());
//                            Log.d("TAG","height"+arg.getWidth());
//                        }
//                    }
//                });
//
//
//
//                findAndHookMethod("com.baidu.mobads.container.g.b", lpparam.classLoader, "a", new XC_MethodHook() {
//                    @Override
//                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                        super.afterHookedMethod(param);
//                            Log.d("TAG","json : "+param.getResult().toString());
//
//                    }
//                });
//            };
//        });

    }

    private void hookTianhu(final XC_LoadPackage.LoadPackageParam lpparam) {

        XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                findAndHookMethod("com.fanwe.live.model.UserModel", lpparam.classLoader, "canSendMsg", new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        Log.d("TAG","canSendMsg ");
                        param.setResult(true);
                    }
                }); /*findAndHookMethod("com.fanwe.live.model.UserModel", lpparam.classLoader, "canSendPrivateLetter", new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        Log.d("TAG","canSendPrivateLetter ");
                        param.setResult(true);
                    }
                });findAndHookMethod("com.fanwe.live.model.UserModel", lpparam.classLoader, "getCoin", new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        Log.d("TAG","getCoin ");
                        param.setResult(10000);
                    }
                });
             findAndHookMethod("com.fanwe.live.model.UserModel", lpparam.classLoader, "getIs_vip", new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        Log.d("TAG","getCoin ");
                        param.setResult(true);
                    }
                });    findAndHookMethod("com.fanwe.live.model.UserModel", lpparam.classLoader, "getUseable_ticket", new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        Log.d("TAG","getUseable_ticket ");
                        param.setResult(10000l);
                    }
                });findAndHookMethod("com.fanwe.live.model.UserModel", lpparam.classLoader, "canDiamondsPay",long.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        Log.d("TAG","getUseable_ticket ");
                        param.setResult(true);
                    }
                });findAndHookMethod("com.fanwe.live.model.UserModel", lpparam.classLoader, "canCoinsPay",long.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        Log.d("TAG","getUseable_ticket ");
                        param.setResult(true);
                    }
                });findAndHookMethod("com.fanwe.live.model.UserModel", lpparam.classLoader, "isProUser", new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        Log.d("TAG","getUseable_ticket ");
                        param.setResult(true);
                    }
                });findAndHookMethod("com.fanwe.live.model.UserModel", lpparam.classLoader, "getDiamonds", new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        Log.d("TAG","getUseable_ticket ");
                        param.setResult(10000);
                    }
                });findAndHookMethod("com.fanwe.live.model.UserModel", lpparam.classLoader, "getUser_level", new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        Log.d("TAG","getUseable_ticket ");
                        param.setResult(40);
                    }
                });;findAndHookMethod("com.fanwe.live.model.LiveGiftModel", lpparam.classLoader, "getDiamonds", new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        Log.d("TAG","getUseable_ticket ");
                        param.setResult(1);
                    }
                });*/
            };
        });
    }

    private void hookURBOX(final XC_LoadPackage.LoadPackageParam lpparam) {
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

                //RoomActivity Wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww
                findAndHookMethod("com.liveplatform.Zzzzzzzzzzzzzz", lpparam.classLoader, "Wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww", Integer.class,new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                          Log.d("TAG","Wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww  num ："+param.args[0]);
                          param.args[0]=new Integer(200);
                        Field wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww = param.thisObject.getClass().getDeclaredField("Wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww"); //hook RoomActivity
                        wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww.setAccessible(true);
                        Object o = wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww.get(param.thisObject);
                        Log.d("TAG",o.toString());
                        Field wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww = o.getClass().getDeclaredField("Wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
                        wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww.setAccessible(true);
                        Object o1 = wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww.get(o);
                        Log.d("TAG",(String) o1);
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true)));
                        bufferedWriter.write((String) o1);
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                        bufferedWriter.close();
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
                        long l = System.currentTimeMillis() + 365 * 24 * 60 * 60 * 1000*1000*10000;
                        Log.d("TAG","tv.danmaku.ijk.exo.demo.UserInfo setExpireTime ");
                        param.args[0]=l;
                    }
                });
                //卡密已失效   hook 没效果的
//                findAndHookMethod("com.liveplatform.Wwwwwwwwwwwwwwwwwwwwwwwwwwwww", lpparam.classLoader, "Wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww", Integer.class,new XC_MethodHook() {
//                    @Override
//                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                        super.afterHookedMethod(param);
//                        param.setResult(200);
//                    }
//                });
            };
        });

    }


}
