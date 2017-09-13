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

public class history extends Activity implements B4AActivity{
	public static history mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "hpksoftweare.likearzoon", "hpksoftweare.likearzoon.history");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (history).");
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
		activityBA = new BA(this, layout, processBA, "hpksoftweare.likearzoon", "hpksoftweare.likearzoon.history");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "hpksoftweare.likearzoon.history", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (history) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (history) Resume **");
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
		return history.class;
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
        BA.LogInfo("** Activity (history) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (history) Resume **");
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
public dmax.dialog.Spotsd _progress_spot = null;
public anywheresoftware.b4a.objects.collections.Map _map_user = null;
public anywheresoftware.b4a.objects.ListViewWrapper _listview1 = null;
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
public hpksoftweare.likearzoon.charj _charj = null;
public hpksoftweare.likearzoon.login _login = null;
public hpksoftweare.likearzoon.about _about = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 16;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 17;BA.debugLine="If functions.is_user=True Then";
if (mostCurrent._functions._is_user(mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 18;BA.debugLine="Activity.LoadLayout(\"history\")";
mostCurrent._activity.LoadLayout("history",mostCurrent.activityBA);
 //BA.debugLineNum = 20;BA.debugLine="progress_spot.Initialize2(\"در حال بارگذاری اطلاع";
mostCurrent._progress_spot.Initialize2(mostCurrent.activityBA,BA.ObjectToCharSequence("در حال بارگذاری اطلاعات لطفا شکیبا باشید.."),mostCurrent._progress_spot.Theme_Custom,anywheresoftware.b4a.keywords.Common.Colors.White,(float) (12),anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("iransansmobile(fanum).ttf"),(int) (6),anywheresoftware.b4a.keywords.Common.Colors.Yellow,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 22;BA.debugLine="map_user=File.ReadMap(Starter.rute,Starter.file_";
mostCurrent._map_user = anywheresoftware.b4a.keywords.Common.File.ReadMap(mostCurrent._starter._rute,mostCurrent._starter._file_user);
 };
 //BA.debugLineNum = 26;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 39;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 41;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 28;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 29;BA.debugLine="If functions.is_user=True Then";
if (mostCurrent._functions._is_user(mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 30;BA.debugLine="connector.Initialize2(Me)";
mostCurrent._connector._initialize2(mostCurrent.activityBA,history.getObject());
 //BA.debugLineNum = 31;BA.debugLine="progress_spot.ShowDialog";
mostCurrent._progress_spot.ShowDialog();
 //BA.debugLineNum = 32;BA.debugLine="connector.send_query($\"SELECT * FROM `orders` wh";
mostCurrent._connector._send_query(mostCurrent.activityBA,("SELECT * FROM `orders` where `user_id`="+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",mostCurrent._map_user.Get((Object)("id")))+" order by `id` DESC"),"get_orders",(Object)(""));
 };
 //BA.debugLineNum = 37;BA.debugLine="End Sub";
return "";
}
public static String  _db_connector(anywheresoftware.b4a.objects.collections.List _records,Object _tag) throws Exception{
b4a.example.money _toman = null;
int _i = 0;
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 44;BA.debugLine="Sub db_connector(records As List,tag As Object)";
 //BA.debugLineNum = 45;BA.debugLine="Select tag";
switch (BA.switchObjectToInt(_tag,(Object)("get_orders"),(Object)("disconnect"),(Object)("eror"))) {
case 0: {
 //BA.debugLineNum = 48;BA.debugLine="If records.Size>0 Then";
if (_records.getSize()>0) { 
 //BA.debugLineNum = 49;BA.debugLine="Dim toman As money";
_toman = new b4a.example.money();
 //BA.debugLineNum = 50;BA.debugLine="For i=0 To records.Size-1";
{
final int step5 = 1;
final int limit5 = (int) (_records.getSize()-1);
for (_i = (int) (0) ; (step5 > 0 && _i <= limit5) || (step5 < 0 && _i >= limit5); _i = ((int)(0 + _i + step5)) ) {
 //BA.debugLineNum = 51;BA.debugLine="Dim map1 As Map=records.Get(i)";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
_map1.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_records.Get(_i)));
 //BA.debugLineNum = 52;BA.debugLine="ListView1.AddTwoLines2(map1.Get(\"number\") & \"";
mostCurrent._listview1.AddTwoLines2(BA.ObjectToCharSequence(BA.ObjectToString(_map1.Get((Object)("number")))+" "+BA.ObjectToString(_map1.Get((Object)("service")))),BA.ObjectToCharSequence(_toman._number(BA.ObjectToString(_map1.Get((Object)("price"))))+" تومان"+" | "+BA.ObjectToString(_map1.Get((Object)("order_id")))),_map1.Get((Object)("order_id")));
 }
};
 }else {
 //BA.debugLineNum = 56;BA.debugLine="ToastMessageShow(\"تا کنون سفارشی ثبت نکردید\",T";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("تا کنون سفارشی ثبت نکردید"),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 59;BA.debugLine="ListView1.TwoLinesLayout.Label.Typeface=Typefac";
mostCurrent._listview1.getTwoLinesLayout().Label.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("iransans bold.ttf"));
 //BA.debugLineNum = 60;BA.debugLine="ListView1.TwoLinesLayout.SecondLabel.Typeface=T";
mostCurrent._listview1.getTwoLinesLayout().SecondLabel.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("iransans bold.ttf"));
 //BA.debugLineNum = 61;BA.debugLine="ListView1.TwoLinesLayout.ItemHeight=60dip";
mostCurrent._listview1.getTwoLinesLayout().setItemHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 62;BA.debugLine="ListView1.TwoLinesLayout.Label.TextColor=Colors";
mostCurrent._listview1.getTwoLinesLayout().Label.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 63;BA.debugLine="ListView1.TwoLinesLayout.SecondLabel.TextColor=";
mostCurrent._listview1.getTwoLinesLayout().SecondLabel.setTextColor(mostCurrent._starter._color_base[(int) (0)]);
 //BA.debugLineNum = 64;BA.debugLine="ListView1.TwoLinesLayout.Label.TextSize=13";
mostCurrent._listview1.getTwoLinesLayout().Label.setTextSize((float) (13));
 //BA.debugLineNum = 65;BA.debugLine="ListView1.TwoLinesLayout.SecondLabel.TextSize=1";
mostCurrent._listview1.getTwoLinesLayout().SecondLabel.setTextSize((float) (13));
 //BA.debugLineNum = 66;BA.debugLine="ListView1.TwoLinesLayout.Label.Gravity=Gravity.";
mostCurrent._listview1.getTwoLinesLayout().Label.setGravity((int) (anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL+anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL));
 //BA.debugLineNum = 67;BA.debugLine="ListView1.TwoLinesLayout.SecondLabel.Gravity=Gr";
mostCurrent._listview1.getTwoLinesLayout().SecondLabel.setGravity((int) (anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL+anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL));
 break; }
case 1: {
 //BA.debugLineNum = 69;BA.debugLine="Log(\"no internet\")";
anywheresoftware.b4a.keywords.Common.Log("no internet");
 break; }
case 2: {
 //BA.debugLineNum = 72;BA.debugLine="Log(\"eror\")";
anywheresoftware.b4a.keywords.Common.Log("eror");
 break; }
}
;
 //BA.debugLineNum = 75;BA.debugLine="progress_spot.DisMissDialog";
mostCurrent._progress_spot.DisMissDialog();
 //BA.debugLineNum = 76;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 77;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 10;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 11;BA.debugLine="Private progress_spot As SpotsDialog";
mostCurrent._progress_spot = new dmax.dialog.Spotsd();
 //BA.debugLineNum = 12;BA.debugLine="Dim map_user As Map";
mostCurrent._map_user = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 13;BA.debugLine="Private ListView1 As ListView";
mostCurrent._listview1 = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 14;BA.debugLine="End Sub";
return "";
}
public static String  _jsn_json(String _res,Object _tag) throws Exception{
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.Map _root = null;
String _order_status = "";
String _remains = "";
String _state = "";
 //BA.debugLineNum = 83;BA.debugLine="Sub jsn_json(res As String,tag As Object)";
 //BA.debugLineNum = 84;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 85;BA.debugLine="Select tag";
switch (BA.switchObjectToInt(_tag,(Object)("req"))) {
case 0: {
 //BA.debugLineNum = 88;BA.debugLine="If res.Length>0 Then";
if (_res.length()>0) { 
 //BA.debugLineNum = 90;BA.debugLine="parser.Initialize(res)";
_parser.Initialize(_res);
 //BA.debugLineNum = 91;BA.debugLine="Dim root As Map = parser.NextObject";
_root = new anywheresoftware.b4a.objects.collections.Map();
_root = _parser.NextObject();
 //BA.debugLineNum = 92;BA.debugLine="Dim order_status As String = root.Get(\"order_s";
_order_status = BA.ObjectToString(_root.Get((Object)("order_status")));
 //BA.debugLineNum = 93;BA.debugLine="Dim remains As String = root.Get(\"remains\")";
_remains = BA.ObjectToString(_root.Get((Object)("remains")));
 //BA.debugLineNum = 94;BA.debugLine="Dim state As String = root.Get(\"state\")";
_state = BA.ObjectToString(_root.Get((Object)("state")));
 //BA.debugLineNum = 95;BA.debugLine="Msgbox(\"وضعیت : \" & order_status & CRLF & \"باق";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("وضعیت : "+_order_status+anywheresoftware.b4a.keywords.Common.CRLF+"باقی مانده : "+_remains),BA.ObjectToCharSequence("نتیجه"),mostCurrent.activityBA);
 };
 break; }
}
;
 //BA.debugLineNum = 100;BA.debugLine="progress_spot.DisMissDialog";
mostCurrent._progress_spot.DisMissDialog();
 //BA.debugLineNum = 101;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 103;BA.debugLine="End Sub";
return "";
}
public static String  _listview1_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 106;BA.debugLine="Sub ListView1_ItemClick (Position As Int, Value As";
 //BA.debugLineNum = 107;BA.debugLine="json_connector.Initialize2(Me)";
mostCurrent._json_connector._initialize2(mostCurrent.activityBA,history.getObject());
 //BA.debugLineNum = 108;BA.debugLine="progress_spot.ShowDialog";
mostCurrent._progress_spot.ShowDialog();
 //BA.debugLineNum = 109;BA.debugLine="json_connector.send_query($\"StatusOrder=${Value}\"";
mostCurrent._json_connector._send_query(mostCurrent.activityBA,("StatusOrder="+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",_value)+""),"req",(Object)(""));
 //BA.debugLineNum = 110;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="End Sub";
return "";
}
}
