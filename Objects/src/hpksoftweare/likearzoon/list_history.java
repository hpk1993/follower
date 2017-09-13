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

public class list_history extends Activity implements B4AActivity{
	public static list_history mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "hpksoftweare.likearzoon", "hpksoftweare.likearzoon.list_history");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (list_history).");
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
		activityBA = new BA(this, layout, processBA, "hpksoftweare.likearzoon", "hpksoftweare.likearzoon.list_history");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "hpksoftweare.likearzoon.list_history", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (list_history) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (list_history) Resume **");
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
		return list_history.class;
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
        BA.LogInfo("** Activity (list_history) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (list_history) Resume **");
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
public anywheresoftware.b4a.objects.ListViewWrapper _list1 = null;
public static boolean _list_details = false;
public dmax.dialog.Spotsd _progress_spot = null;
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
public hpksoftweare.likearzoon.ticket _ticket = null;
public hpksoftweare.likearzoon.pushejsonservice _pushejsonservice = null;
public hpksoftweare.likearzoon.order _order = null;
public hpksoftweare.likearzoon.charj _charj = null;
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
 //BA.debugLineNum = 17;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 18;BA.debugLine="progress_spot.Initialize2(\"در حال بارگذاری اطلاعا";
mostCurrent._progress_spot.Initialize2(mostCurrent.activityBA,BA.ObjectToCharSequence("در حال بارگذاری اطلاعات لطفا شکیبا باشید.."),mostCurrent._progress_spot.Theme_Custom,anywheresoftware.b4a.keywords.Common.Colors.White,(float) (12),anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("iransansmobile(fanum).ttf"),(int) (6),anywheresoftware.b4a.keywords.Common.Colors.Yellow,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 21;BA.debugLine="If Main.admin_bool=1 Then";
if (mostCurrent._main._admin_bool==1) { 
 //BA.debugLineNum = 22;BA.debugLine="list1.Initialize(\"list1\")";
mostCurrent._list1.Initialize(mostCurrent.activityBA,"list1");
 //BA.debugLineNum = 23;BA.debugLine="Activity.AddView(list1,0,0,100%x,100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._list1.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 25;BA.debugLine="list1.TwoLinesAndBitmap.ItemHeight=70dip";
mostCurrent._list1.getTwoLinesAndBitmap().setItemHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (70)));
 //BA.debugLineNum = 26;BA.debugLine="list1.TwoLinesAndBitmap.Label.Height=70dip";
mostCurrent._list1.getTwoLinesAndBitmap().Label.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (70)));
 //BA.debugLineNum = 27;BA.debugLine="list1.TwoLinesAndBitmap.SecondLabel.Height=0dip";
mostCurrent._list1.getTwoLinesAndBitmap().SecondLabel.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)));
 //BA.debugLineNum = 28;BA.debugLine="list1.TwoLinesAndBitmap.Label.TextColor=Colors.B";
mostCurrent._list1.getTwoLinesAndBitmap().Label.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 29;BA.debugLine="list1.TwoLinesAndBitmap.Label.TextSize=14";
mostCurrent._list1.getTwoLinesAndBitmap().Label.setTextSize((float) (14));
 //BA.debugLineNum = 30;BA.debugLine="list1.TwoLinesAndBitmap.Label.Gravity=Gravity.CE";
mostCurrent._list1.getTwoLinesAndBitmap().Label.setGravity((int) (anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL+anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL));
 //BA.debugLineNum = 31;BA.debugLine="list1.TwoLinesAndBitmap.Label.Typeface=Typeface.";
mostCurrent._list1.getTwoLinesAndBitmap().Label.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("iransans bold.ttf"));
 };
 //BA.debugLineNum = 38;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 140;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 142;BA.debugLine="If list_details = True Then";
if (_list_details==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 143;BA.debugLine="list_user_order";
_list_user_order();
 //BA.debugLineNum = 144;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 146;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
 //BA.debugLineNum = 149;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 45;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 47;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 40;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 41;BA.debugLine="connector.Initialize2(Me)";
mostCurrent._connector._initialize2(mostCurrent.activityBA,list_history.getObject());
 //BA.debugLineNum = 42;BA.debugLine="list_user_order";
_list_user_order();
 //BA.debugLineNum = 43;BA.debugLine="End Sub";
return "";
}
public static String  _db_connector(anywheresoftware.b4a.objects.collections.List _records,Object _tag) throws Exception{
int _i = 0;
anywheresoftware.b4a.objects.collections.Map _map1 = null;
b4a.example.money _toman = null;
String _date_str = "";
String[] _date1 = null;
com.b4a.manamsoftware.PersianDate.ManamPersianDate _mp = null;
String _dateper = "";
 //BA.debugLineNum = 74;BA.debugLine="Sub db_connector(records As List,tag As Object)";
 //BA.debugLineNum = 75;BA.debugLine="Select tag";
switch (BA.switchObjectToInt(_tag,(Object)("get_user"),(Object)("details"),(Object)("disconnect"),(Object)("eror"))) {
case 0: {
 //BA.debugLineNum = 78;BA.debugLine="Log(records)";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(_records));
 //BA.debugLineNum = 79;BA.debugLine="If records.Size>0 Then";
if (_records.getSize()>0) { 
 //BA.debugLineNum = 80;BA.debugLine="list_details=False";
_list_details = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 81;BA.debugLine="list1.Clear";
mostCurrent._list1.Clear();
 //BA.debugLineNum = 82;BA.debugLine="For i=0 To records.Size-1";
{
final int step7 = 1;
final int limit7 = (int) (_records.getSize()-1);
for (_i = (int) (0) ; (step7 > 0 && _i <= limit7) || (step7 < 0 && _i >= limit7); _i = ((int)(0 + _i + step7)) ) {
 //BA.debugLineNum = 83;BA.debugLine="Dim map1 As Map=records.Get(i)";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
_map1.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_records.Get(_i)));
 //BA.debugLineNum = 84;BA.debugLine="list1.AddTwoLinesAndBitmap2(map1.Get(\"name\"),";
mostCurrent._list1.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence(_map1.Get((Object)("name"))),BA.ObjectToCharSequence("مشاهده جزئیات خرید"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"user.png").getObject()),_map1.Get((Object)("user_id")));
 }
};
 };
 //BA.debugLineNum = 91;BA.debugLine="list1.TwoLinesAndBitmap.ItemHeight=70dip";
mostCurrent._list1.getTwoLinesAndBitmap().setItemHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (70)));
 //BA.debugLineNum = 92;BA.debugLine="list1.TwoLinesAndBitmap.Label.Height=70dip";
mostCurrent._list1.getTwoLinesAndBitmap().Label.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (70)));
 //BA.debugLineNum = 93;BA.debugLine="list1.TwoLinesAndBitmap.SecondLabel.Height=0dip";
mostCurrent._list1.getTwoLinesAndBitmap().SecondLabel.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)));
 //BA.debugLineNum = 94;BA.debugLine="list1.TwoLinesAndBitmap.Label.TextColor=Colors.";
mostCurrent._list1.getTwoLinesAndBitmap().Label.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 95;BA.debugLine="list1.TwoLinesAndBitmap.Label.TextSize=14";
mostCurrent._list1.getTwoLinesAndBitmap().Label.setTextSize((float) (14));
 //BA.debugLineNum = 96;BA.debugLine="list1.TwoLinesAndBitmap.Label.Gravity=Gravity.C";
mostCurrent._list1.getTwoLinesAndBitmap().Label.setGravity((int) (anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL+anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL));
 //BA.debugLineNum = 97;BA.debugLine="list1.TwoLinesAndBitmap.Label.Typeface=Typeface";
mostCurrent._list1.getTwoLinesAndBitmap().Label.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("iransans bold.ttf"));
 break; }
case 1: {
 //BA.debugLineNum = 100;BA.debugLine="If records.Size>0 Then";
if (_records.getSize()>0) { 
 //BA.debugLineNum = 101;BA.debugLine="list_details=True";
_list_details = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 102;BA.debugLine="list1.Clear";
mostCurrent._list1.Clear();
 //BA.debugLineNum = 103;BA.debugLine="list1.TwoLinesAndBitmap.ItemHeight=100dip";
mostCurrent._list1.getTwoLinesAndBitmap().setItemHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100)));
 //BA.debugLineNum = 104;BA.debugLine="list1.TwoLinesAndBitmap.Label.Height=50dip";
mostCurrent._list1.getTwoLinesAndBitmap().Label.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 //BA.debugLineNum = 105;BA.debugLine="list1.TwoLinesAndBitmap.SecondLabel.Height=50d";
mostCurrent._list1.getTwoLinesAndBitmap().SecondLabel.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 //BA.debugLineNum = 106;BA.debugLine="list1.TwoLinesAndBitmap.Label.TextColor=Colors";
mostCurrent._list1.getTwoLinesAndBitmap().Label.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 107;BA.debugLine="list1.TwoLinesAndBitmap.Label.TextSize=14";
mostCurrent._list1.getTwoLinesAndBitmap().Label.setTextSize((float) (14));
 //BA.debugLineNum = 108;BA.debugLine="list1.TwoLinesAndBitmap.SecondLabel.TextSize=1";
mostCurrent._list1.getTwoLinesAndBitmap().SecondLabel.setTextSize((float) (14));
 //BA.debugLineNum = 109;BA.debugLine="list1.TwoLinesAndBitmap.Label.Gravity=Gravity.";
mostCurrent._list1.getTwoLinesAndBitmap().Label.setGravity((int) (anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL+anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL));
 //BA.debugLineNum = 110;BA.debugLine="list1.TwoLinesAndBitmap.SecondLabel.Gravity=Gr";
mostCurrent._list1.getTwoLinesAndBitmap().SecondLabel.setGravity((int) (anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL+anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL));
 //BA.debugLineNum = 111;BA.debugLine="list1.TwoLinesAndBitmap.Label.Typeface=Typefac";
mostCurrent._list1.getTwoLinesAndBitmap().Label.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("iransans bold.ttf"));
 //BA.debugLineNum = 112;BA.debugLine="list1.TwoLinesAndBitmap.SecondLabel.Typeface=T";
mostCurrent._list1.getTwoLinesAndBitmap().SecondLabel.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("iransans bold.ttf"));
 //BA.debugLineNum = 113;BA.debugLine="For i=0 To records.Size-1";
{
final int step33 = 1;
final int limit33 = (int) (_records.getSize()-1);
for (_i = (int) (0) ; (step33 > 0 && _i <= limit33) || (step33 < 0 && _i >= limit33); _i = ((int)(0 + _i + step33)) ) {
 //BA.debugLineNum = 114;BA.debugLine="Dim map1 As Map=records.Get(i)";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
_map1.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_records.Get(_i)));
 //BA.debugLineNum = 115;BA.debugLine="Dim toman As money";
_toman = new b4a.example.money();
 //BA.debugLineNum = 116;BA.debugLine="Dim date_str As String=map1.Get(\"date\")";
_date_str = BA.ObjectToString(_map1.Get((Object)("date")));
 //BA.debugLineNum = 117;BA.debugLine="Dim date1() As String=Regex.Split(\"-\", date_s";
_date1 = anywheresoftware.b4a.keywords.Common.Regex.Split("-",_date_str.substring((int) (0),(int) (11)));
 //BA.debugLineNum = 118;BA.debugLine="Dim MP As ManamPersianDate";
_mp = new com.b4a.manamsoftware.PersianDate.ManamPersianDate();
 //BA.debugLineNum = 119;BA.debugLine="Dim dateper As  String= MP.GregorianToPersian";
_dateper = _mp.GregorianToPersian((int)(Double.parseDouble(_date1[(int) (0)])),(int)(Double.parseDouble(_date1[(int) (1)])),(int)(Double.parseDouble(_date1[(int) (2)])));
 //BA.debugLineNum = 120;BA.debugLine="list1.AddTwoLinesAndBitmap2(map1.Get(\"number\"";
mostCurrent._list1.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence(BA.ObjectToString(_map1.Get((Object)("number")))+" "+BA.ObjectToString(_map1.Get((Object)("service")))+" ("+_toman._number(BA.ObjectToString(_map1.Get((Object)("price"))))+" تومان"+")"),BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.CRLF+_dateper+"   |   "+BA.ObjectToString(_map1.Get((Object)("order_id")))),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),_map1.Get((Object)("page")));
 //BA.debugLineNum = 122;BA.debugLine="list1.TwoLinesAndBitmap.SecondLabel.Gravity=G";
mostCurrent._list1.getTwoLinesAndBitmap().SecondLabel.setGravity((int) (anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL+anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL));
 }
};
 };
 break; }
case 2: {
 //BA.debugLineNum = 129;BA.debugLine="Log(\"no internet\")";
anywheresoftware.b4a.keywords.Common.Log("no internet");
 break; }
case 3: {
 //BA.debugLineNum = 132;BA.debugLine="Log(\"eror\")";
anywheresoftware.b4a.keywords.Common.Log("eror");
 break; }
}
;
 //BA.debugLineNum = 135;BA.debugLine="progress_spot.DisMissDialog";
mostCurrent._progress_spot.DisMissDialog();
 //BA.debugLineNum = 136;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 137;BA.debugLine="End Sub";
return "";
}
public static String  _details(int _value) throws Exception{
 //BA.debugLineNum = 66;BA.debugLine="Sub details(Value As Int)";
 //BA.debugLineNum = 67;BA.debugLine="progress_spot.ShowDialog";
mostCurrent._progress_spot.ShowDialog();
 //BA.debugLineNum = 68;BA.debugLine="connector.send_query($\"SELECT `orders`.*,`users`.";
mostCurrent._connector._send_query(mostCurrent.activityBA,("SELECT `orders`.*,`users`.`name` FROM `orders`\n"+"left join `users` ON `users`.`id`=`orders`.`user_id`\n"+" WHERE `user_id`="+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_value))+"\n"+"order BY `id` DESC"),"details",(Object)(""));
 //BA.debugLineNum = 72;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 11;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 12;BA.debugLine="Dim list1 As ListView";
mostCurrent._list1 = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 13;BA.debugLine="Dim list_details As Boolean";
_list_details = false;
 //BA.debugLineNum = 14;BA.debugLine="Private progress_spot As SpotsDialog";
mostCurrent._progress_spot = new dmax.dialog.Spotsd();
 //BA.debugLineNum = 15;BA.debugLine="End Sub";
return "";
}
public static String  _list_user_order() throws Exception{
 //BA.debugLineNum = 59;BA.debugLine="Sub list_user_order";
 //BA.debugLineNum = 60;BA.debugLine="progress_spot.ShowDialog";
mostCurrent._progress_spot.ShowDialog();
 //BA.debugLineNum = 61;BA.debugLine="connector.send_query($\"SELECT `orders`.*,`users`.";
mostCurrent._connector._send_query(mostCurrent.activityBA,("SELECT `orders`.*,`users`.`name` FROM `orders`\n"+"left join `users` ON `users`.`id`=`orders`.`user_id`\n"+"GROUP by `user_id` ORDER BY `id` DESC"),"get_user",(Object)(""));
 //BA.debugLineNum = 64;BA.debugLine="End Sub";
return "";
}
public static String  _list1_itemclick(int _position,Object _value) throws Exception{
String _link = "";
 //BA.debugLineNum = 50;BA.debugLine="Sub  list1_ItemClick (Position As Int, Value As Ob";
 //BA.debugLineNum = 51;BA.debugLine="Dim Link As String=Value";
_link = BA.ObjectToString(_value);
 //BA.debugLineNum = 52;BA.debugLine="If Link.Contains(\"http\")=False And Link.Length>0";
if (_link.contains("http")==anywheresoftware.b4a.keywords.Common.False && _link.length()>0) { 
 //BA.debugLineNum = 53;BA.debugLine="details(Value)";
_details((int)(BA.ObjectToNumber(_value)));
 }else {
 //BA.debugLineNum = 55;BA.debugLine="Msgbox(Link,\"\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence(_link),BA.ObjectToCharSequence(""),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 57;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="End Sub";
return "";
}
}
