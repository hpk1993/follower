package hpksoftweare.likearzoon;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class charj extends android.support.v7.app.AppCompatActivity implements B4AActivity{
	public static charj mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = false;
	public static final boolean includeTitle = true;
    public static WeakReference<Activity> previousOne;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isFirst) {
			processBA = new BA(this.getApplicationContext(), null, null, "hpksoftweare.likearzoon", "hpksoftweare.likearzoon.charj");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (charj).");
				p.finish();
			}
		}
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		mostCurrent = this;
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(processBA, wl, false))
		    BA.handler.postDelayed(wl, 5);

	}
	static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "hpksoftweare.likearzoon", "hpksoftweare.likearzoon.charj");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "hpksoftweare.likearzoon.charj", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (charj) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (charj) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEventFromUI(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return charj.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeydown", this, new Object[] {keyCode, event}))
            return true;
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeyup", this, new Object[] {keyCode, event}))
            return true;
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null) //workaround for emulator bug (Issue 2423)
            return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        BA.LogInfo("** Activity (charj) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        processBA.setActivityPaused(true);
        mostCurrent = null;
        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
			if (mostCurrent == null || mostCurrent != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (charj) Resume **");
		    processBA.raiseEvent(mostCurrent._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
        for (int i = 0;i < permissions.length;i++) {
            Object[] o = new Object[] {permissions[i], grantResults[i] == 0};
            processBA.raiseEventFromDifferentThread(null,null, 0, "activity_permissionresult", true, o);
        }
            
    }

public anywheresoftware.b4a.keywords.Common __c = null;
public de.amberhome.objects.appcompat.ACRadioButtonWrapper _rsender = null;
public static String _phoneid = "";
public anywheresoftware.b4a.inappbilling3.BillingManager3 _bazzar = null;
public static boolean _pardakht = false;
public static String _rsacode = "";
public static int _wallet = 0;
public static int _add_coins = 0;
public b4a.example.money _toman = null;
public dmax.dialog.Spotsd _progress_spot = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper _scrol = null;
public de.amberhome.objects.appcompat.ACRadioButtonWrapper _r1 = null;
public de.amberhome.objects.appcompat.ACRadioButtonWrapper _r2 = null;
public de.amberhome.objects.appcompat.ACRadioButtonWrapper _r3 = null;
public de.amberhome.objects.appcompat.ACRadioButtonWrapper _r6 = null;
public de.amberhome.objects.appcompat.ACRadioButtonWrapper _r4 = null;
public de.amberhome.objects.appcompat.ACRadioButtonWrapper _r5 = null;
public de.amberhome.objects.appcompat.ACRadioButtonWrapper _r9 = null;
public de.amberhome.objects.appcompat.ACRadioButtonWrapper _r7 = null;
public de.amberhome.objects.appcompat.ACRadioButtonWrapper _r8 = null;
public anywheresoftware.b4a.objects.collections.Map _map_user = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_charj = null;
public b4a.example.frgfg.connector _connector = null;
public b4a.example.frgfg.db_mysql _db_mysql = null;
public b4a.example.get_json _get_json = null;
public b4a.example.json_connector _json_connector = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public hpksoftweare.likearzoon.main _main = null;
public hpksoftweare.likearzoon.starter _starter = null;
public hpksoftweare.likearzoon.products _products = null;
public hpksoftweare.likearzoon.functions _functions = null;
public hpksoftweare.likearzoon.list_user _list_user = null;
public hpksoftweare.likearzoon.list_history _list_history = null;
public hpksoftweare.likearzoon.ticket _ticket = null;
public hpksoftweare.likearzoon.pushejsonservice _pushejsonservice = null;
public hpksoftweare.likearzoon.order _order = null;
public hpksoftweare.likearzoon.login _login = null;
public hpksoftweare.likearzoon.about _about = null;
public hpksoftweare.likearzoon.history _history = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 39;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 40;BA.debugLine="If functions.is_user=True Then";
if (mostCurrent._functions._is_user(mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 41;BA.debugLine="scrol.Initialize(100%y)";
mostCurrent._scrol.Initialize(mostCurrent.activityBA,anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 42;BA.debugLine="Activity.AddView(scrol,0,0,100%x,100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._scrol.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 43;BA.debugLine="scrol.Panel.LoadLayout(\"list_coins\")";
mostCurrent._scrol.getPanel().LoadLayout("list_coins",mostCurrent.activityBA);
 //BA.debugLineNum = 45;BA.debugLine="scrol.Panel.Height=btn_charj.Top+btn_charj.Heigh";
mostCurrent._scrol.getPanel().setHeight((int) (mostCurrent._btn_charj.getTop()+mostCurrent._btn_charj.getHeight()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))));
 //BA.debugLineNum = 46;BA.debugLine="r1.Checked=True";
mostCurrent._r1.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 47;BA.debugLine="map_user=File.ReadMap(Starter.rute,Starter.file_";
mostCurrent._map_user = anywheresoftware.b4a.keywords.Common.File.ReadMap(mostCurrent._starter._rute,mostCurrent._starter._file_user);
 //BA.debugLineNum = 48;BA.debugLine="Try";
try { //BA.debugLineNum = 49;BA.debugLine="bazzar.Initialize(\"bazzar\",RSACode)";
mostCurrent._bazzar.Initialize(processBA,"bazzar",mostCurrent._rsacode);
 } 
       catch (Exception e11) {
			processBA.setLastException(e11); //BA.debugLineNum = 54;BA.debugLine="ToastMessageShow(\"Not Bazaar Install in the pho";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Not Bazaar Install in the phone"),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 58;BA.debugLine="progress_spot.Initialize2(\"در حال بارگذاری اطلاع";
mostCurrent._progress_spot.Initialize2(mostCurrent.activityBA,BA.ObjectToCharSequence("در حال بارگذاری اطلاعات لطفا شکیبا باشید.."),mostCurrent._progress_spot.Theme_Custom,anywheresoftware.b4a.keywords.Common.Colors.White,(float) (12),anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("iransansmobile(fanum).ttf"),(int) (6),anywheresoftware.b4a.keywords.Common.Colors.Yellow,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 59;BA.debugLine="connector.Initialize2(Me)";
mostCurrent._connector._initialize2(mostCurrent.activityBA,charj.getObject());
 }else {
 //BA.debugLineNum = 62;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
 //BA.debugLineNum = 65;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 71;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 73;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 67;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 69;BA.debugLine="End Sub";
return "";
}
public static String  _bazzar_billingsupported(boolean _supported,String _message) throws Exception{
 //BA.debugLineNum = 105;BA.debugLine="Sub bazzar_BillingSupported (Supported As Boolean,";
 //BA.debugLineNum = 107;BA.debugLine="Pardakht=Supported";
_pardakht = _supported;
 //BA.debugLineNum = 109;BA.debugLine="End Sub";
return "";
}
public static String  _bazzar_ownedproducts(boolean _success,anywheresoftware.b4a.objects.collections.Map _purchases) throws Exception{
 //BA.debugLineNum = 111;BA.debugLine="Sub bazzar_OwnedProducts (Success As Boolean, Purc";
 //BA.debugLineNum = 113;BA.debugLine="End Sub";
return "";
}
public static String  _bazzar_productconsumed(boolean _success,anywheresoftware.b4a.inappbilling3.BillingManager3.Prchase _product) throws Exception{
 //BA.debugLineNum = 114;BA.debugLine="Sub bazzar_ProductConsumed (Success As Boolean, pr";
 //BA.debugLineNum = 116;BA.debugLine="connector.Initialize2(Me)";
mostCurrent._connector._initialize2(mostCurrent.activityBA,charj.getObject());
 //BA.debugLineNum = 117;BA.debugLine="progress_spot.ShowDialog";
mostCurrent._progress_spot.ShowDialog();
 //BA.debugLineNum = 118;BA.debugLine="connector.send_query($\"CALL `up_wallet`(${product";
mostCurrent._connector._send_query(mostCurrent.activityBA,("CALL `up_wallet`("+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_product.getDeveloperPayload()))+", "+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",mostCurrent._map_user.Get((Object)("id")))+", @p2);"),"wallet",(Object)(""));
 //BA.debugLineNum = 120;BA.debugLine="End Sub";
return "";
}
public static String  _bazzar_purchasecompleted(boolean _success,anywheresoftware.b4a.inappbilling3.BillingManager3.Prchase _product) throws Exception{
 //BA.debugLineNum = 121;BA.debugLine="Sub bazzar_PurchaseCompleted (Success As Boolean,";
 //BA.debugLineNum = 122;BA.debugLine="If Success Then";
if (_success) { 
 //BA.debugLineNum = 127;BA.debugLine="bazzar.ConsumeProduct(product)";
mostCurrent._bazzar.ConsumeProduct(processBA,_product);
 }else {
 //BA.debugLineNum = 130;BA.debugLine="ToastMessageShow(\"متاسفانه خرید ناموفق بود\",Fals";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("متاسفانه خرید ناموفق بود"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 132;BA.debugLine="End Sub";
return "";
}
public static String  _btn_charj_click() throws Exception{
 //BA.debugLineNum = 161;BA.debugLine="Sub btn_charj_Click";
 //BA.debugLineNum = 162;BA.debugLine="If r1.Checked=True Then";
if (mostCurrent._r1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 163;BA.debugLine="rsender=r1";
mostCurrent._rsender = mostCurrent._r1;
 };
 //BA.debugLineNum = 165;BA.debugLine="If functions.is_user=True Then";
if (mostCurrent._functions._is_user(mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 166;BA.debugLine="If Msgbox2(\"از شارژ کیف پول با این مبلغ مطمعن هس";
if (anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("از شارژ کیف پول با این مبلغ مطمعن هستم؟"),BA.ObjectToCharSequence(mostCurrent._toman._number(BA.ObjectToString(mostCurrent._rsender.getTag()))+" تومان"),"بله","خیر","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 167;BA.debugLine="If Pardakht = False Then";
if (_pardakht==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 168;BA.debugLine="Log(\"Error purches\")";
anywheresoftware.b4a.keywords.Common.Log("Error purches");
 //BA.debugLineNum = 169;BA.debugLine="Msgbox(\"خطایی رخ داد\",\"خطا\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("خطایی رخ داد"),BA.ObjectToCharSequence("خطا"),mostCurrent.activityBA);
 //BA.debugLineNum = 170;BA.debugLine="Return";
if (true) return "";
 }else {
 //BA.debugLineNum = 173;BA.debugLine="bazzar.RequestPayment($\"product_${rsender.Tag}";
mostCurrent._bazzar.RequestPayment(processBA,("product_"+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",mostCurrent._rsender.getTag())+""),"inapp",BA.ObjectToString(mostCurrent._rsender.getTag()));
 };
 };
 }else {
 //BA.debugLineNum = 177;BA.debugLine="ToastMessageShow(\"لطفا ابتدا وارد حساب کاربری خو";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("لطفا ابتدا وارد حساب کاربری خود شوید"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 179;BA.debugLine="End Sub";
return "";
}
public static String  _db_connector(anywheresoftware.b4a.objects.collections.List _records,Object _tag) throws Exception{
 //BA.debugLineNum = 139;BA.debugLine="Sub db_connector(records As List,tag As Object)";
 //BA.debugLineNum = 140;BA.debugLine="Select tag";
switch (BA.switchObjectToInt(_tag,(Object)("wallet"),(Object)("disconnect"),(Object)("eror"))) {
case 0: {
 //BA.debugLineNum = 143;BA.debugLine="ToastMessageShow(\"کیف پول شما شارژ شد\",True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("کیف پول شما شارژ شد"),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 144;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 break; }
case 1: {
 //BA.debugLineNum = 150;BA.debugLine="Log(\"no internet\")";
anywheresoftware.b4a.keywords.Common.Log("no internet");
 break; }
case 2: {
 //BA.debugLineNum = 153;BA.debugLine="Log(\"eror\")";
anywheresoftware.b4a.keywords.Common.Log("eror");
 break; }
}
;
 //BA.debugLineNum = 156;BA.debugLine="progress_spot.DisMissDialog";
mostCurrent._progress_spot.DisMissDialog();
 //BA.debugLineNum = 157;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 158;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 11;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 13;BA.debugLine="Dim rsender As ACRadioButton";
mostCurrent._rsender = new de.amberhome.objects.appcompat.ACRadioButtonWrapper();
 //BA.debugLineNum = 14;BA.debugLine="Dim PhoneId As String";
mostCurrent._phoneid = "";
 //BA.debugLineNum = 15;BA.debugLine="Dim bazzar As BillingManager3";
mostCurrent._bazzar = new anywheresoftware.b4a.inappbilling3.BillingManager3();
 //BA.debugLineNum = 16;BA.debugLine="Dim Pardakht As Boolean";
_pardakht = false;
 //BA.debugLineNum = 17;BA.debugLine="Dim RSACode As String =Starter.RSACode_bazaar";
mostCurrent._rsacode = mostCurrent._starter._rsacode_bazaar;
 //BA.debugLineNum = 19;BA.debugLine="Dim wallet As Int";
_wallet = 0;
 //BA.debugLineNum = 20;BA.debugLine="Dim add_coins As Int";
_add_coins = 0;
 //BA.debugLineNum = 23;BA.debugLine="Dim toman As money";
mostCurrent._toman = new b4a.example.money();
 //BA.debugLineNum = 24;BA.debugLine="Dim progress_spot As SpotsDialog";
mostCurrent._progress_spot = new dmax.dialog.Spotsd();
 //BA.debugLineNum = 25;BA.debugLine="Dim scrol As ScrollView";
mostCurrent._scrol = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private r1 As ACRadioButton";
mostCurrent._r1 = new de.amberhome.objects.appcompat.ACRadioButtonWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private r2 As ACRadioButton";
mostCurrent._r2 = new de.amberhome.objects.appcompat.ACRadioButtonWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private r3 As ACRadioButton";
mostCurrent._r3 = new de.amberhome.objects.appcompat.ACRadioButtonWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private r6 As ACRadioButton";
mostCurrent._r6 = new de.amberhome.objects.appcompat.ACRadioButtonWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private r4 As ACRadioButton";
mostCurrent._r4 = new de.amberhome.objects.appcompat.ACRadioButtonWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private r5 As ACRadioButton";
mostCurrent._r5 = new de.amberhome.objects.appcompat.ACRadioButtonWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private r9 As ACRadioButton";
mostCurrent._r9 = new de.amberhome.objects.appcompat.ACRadioButtonWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private r7 As ACRadioButton";
mostCurrent._r7 = new de.amberhome.objects.appcompat.ACRadioButtonWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private r8 As ACRadioButton";
mostCurrent._r8 = new de.amberhome.objects.appcompat.ACRadioButtonWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Dim map_user As Map";
mostCurrent._map_user = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 36;BA.debugLine="Private btn_charj As Button";
mostCurrent._btn_charj = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 37;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 7;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="End Sub";
return "";
}
public static String  _radioselect_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 76;BA.debugLine="Sub radioselect_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 77;BA.debugLine="rsender=Sender";
mostCurrent._rsender.setObject((android.widget.RadioButton)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 78;BA.debugLine="If functions.is_user=True Then";
if (mostCurrent._functions._is_user(mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 79;BA.debugLine="If Msgbox2(\"از شارژ کیف پول با این مبلغ مطمعن هس";
if (anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("از شارژ کیف پول با این مبلغ مطمعن هستم؟"),BA.ObjectToCharSequence(mostCurrent._toman._number(BA.ObjectToString(mostCurrent._rsender.getTag()))+" تومان"),"بله","خیر","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 80;BA.debugLine="If Pardakht = False Then";
if (_pardakht==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 81;BA.debugLine="Log(\"Error purches\")";
anywheresoftware.b4a.keywords.Common.Log("Error purches");
 //BA.debugLineNum = 82;BA.debugLine="Msgbox(\"خطایی رخ داد\",\"خطا\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("خطایی رخ داد"),BA.ObjectToCharSequence("خطا"),mostCurrent.activityBA);
 //BA.debugLineNum = 83;BA.debugLine="Return";
if (true) return "";
 }else {
 //BA.debugLineNum = 86;BA.debugLine="bazzar.RequestPayment($\"product_${rsender.Tag}";
mostCurrent._bazzar.RequestPayment(processBA,("product_"+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",mostCurrent._rsender.getTag())+""),"inapp",BA.ObjectToString(mostCurrent._rsender.getTag()));
 };
 };
 }else {
 //BA.debugLineNum = 90;BA.debugLine="ToastMessageShow(\"لطفا ابتدا وارد حساب کاربری خو";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("لطفا ابتدا وارد حساب کاربری خود شوید"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 93;BA.debugLine="End Sub";
return "";
}
}
