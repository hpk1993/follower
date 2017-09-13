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

public class main extends android.support.v7.app.AppCompatActivity implements B4AActivity{
	public static main mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "hpksoftweare.likearzoon", "hpksoftweare.likearzoon.main");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (main).");
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
		activityBA = new BA(this, layout, processBA, "hpksoftweare.likearzoon", "hpksoftweare.likearzoon.main");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "hpksoftweare.likearzoon.main", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (main) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (main) Resume **");
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
		return main.class;
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
        BA.LogInfo("** Activity (main) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (main) Resume **");
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
public static int _admin_bool = 0;
public static int _my_id = 0;
public static co.ronash.pushe.wrapper.PusheWrapper _pushe = null;
public static anywheresoftware.b4a.ariagplib.ARIAlib _aria = null;
public static boolean _is_connection = false;
public de.amberhome.objects.appcompat.AppCompatBase _apc = null;
public anywheresoftware.b4a.objects.LabelWrapper _btn_order = null;
public anywheresoftware.b4a.objects.LabelWrapper _btn_pegiri = null;
public anywheresoftware.b4a.objects.LabelWrapper _btn_about = null;
public anywheresoftware.b4a.objects.LabelWrapper _btn_contact = null;
public anywheresoftware.b4a.objects.LabelWrapper _btn_account = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview1 = null;
public anywheresoftware.b4a.object.RippleViewWrapper[] _ripple = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_price = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel_base = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper _scrol = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_icon1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _btn_charj = null;
public anywheresoftware.b4a.objects.collections.Map _map_user = null;
public dmax.dialog.Spotsd _progress_spot = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_product = null;
public b4a.example.frgfg.connector _connector = null;
public b4a.example.frgfg.db_mysql _db_mysql = null;
public b4a.example.get_json _get_json = null;
public b4a.example.json_connector _json_connector = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
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
public hpksoftweare.likearzoon.history _history = null;
public static class _struct_req{
public boolean IsInitialized;
public int price_unit;
public int quantity;
public int price;
public int max_req;
public String alias;
public String description;
public int id;
public String title;
public int min_req;
public int show;
public void Initialize() {
IsInitialized = true;
price_unit = 0;
quantity = 0;
price = 0;
max_req = 0;
alias = "";
description = "";
id = 0;
title = "";
min_req = 0;
show = 0;
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
vis = vis | (products.mostCurrent != null);
vis = vis | (list_user.mostCurrent != null);
vis = vis | (list_history.mostCurrent != null);
vis = vis | (ticket.mostCurrent != null);
vis = vis | (order.mostCurrent != null);
vis = vis | (charj.mostCurrent != null);
vis = vis | (login.mostCurrent != null);
vis = vis | (about.mostCurrent != null);
vis = vis | (history.mostCurrent != null);
return vis;}
public static String  _activity_create(boolean _firsttime) throws Exception{
anywheresoftware.b4a.objects.drawable.ColorDrawable _bg1 = null;
 //BA.debugLineNum = 58;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 61;BA.debugLine="Activity.LoadLayout(\"layout\")";
mostCurrent._activity.LoadLayout("layout",mostCurrent.activityBA);
 //BA.debugLineNum = 62;BA.debugLine="scrol.Initialize(panel_base.Height)";
mostCurrent._scrol.Initialize(mostCurrent.activityBA,mostCurrent._panel_base.getHeight());
 //BA.debugLineNum = 63;BA.debugLine="Dim bg1 As ColorDrawable:bg1.Initialize(Starter.c";
_bg1 = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 63;BA.debugLine="Dim bg1 As ColorDrawable:bg1.Initialize(Starter.c";
_bg1.Initialize(mostCurrent._starter._color_base[(int) (1)],(int) (0));
 //BA.debugLineNum = 63;BA.debugLine="Dim bg1 As ColorDrawable:bg1.Initialize(Starter.c";
mostCurrent._panel1.setBackground((android.graphics.drawable.Drawable)(_bg1.getObject()));
 //BA.debugLineNum = 64;BA.debugLine="apc.SetElevation(Panel1,9dip)";
mostCurrent._apc.SetElevation((android.view.View)(mostCurrent._panel1.getObject()),(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (9))));
 //BA.debugLineNum = 65;BA.debugLine="panel_base.AddView(scrol,0,0,100%x,panel_base.Hei";
mostCurrent._panel_base.AddView((android.view.View)(mostCurrent._scrol.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),mostCurrent._panel_base.getHeight());
 //BA.debugLineNum = 66;BA.debugLine="scrol.Panel.LoadLayout(\"main\")";
mostCurrent._scrol.getPanel().LoadLayout("main",mostCurrent.activityBA);
 //BA.debugLineNum = 67;BA.debugLine="ImageView1.Top=scrol.Panel.Height-ImageView1.Heig";
mostCurrent._imageview1.setTop((int) (mostCurrent._scrol.getPanel().getHeight()-mostCurrent._imageview1.getHeight()));
 //BA.debugLineNum = 68;BA.debugLine="scrol.Panel.Height=ImageView1.Top + ImageView1.He";
mostCurrent._scrol.getPanel().setHeight((int) (mostCurrent._imageview1.getTop()+mostCurrent._imageview1.getHeight()));
 //BA.debugLineNum = 69;BA.debugLine="progress_spot.Initialize2(\"در حال بارگذاری اطلاعا";
mostCurrent._progress_spot.Initialize2(mostCurrent.activityBA,BA.ObjectToCharSequence("در حال بارگذاری اطلاعات لطفا شکیبا باشید.."),mostCurrent._progress_spot.Theme_Custom,anywheresoftware.b4a.keywords.Common.Colors.White,(float) (12),anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("iransansmobile(fanum).ttf"),(int) (6),anywheresoftware.b4a.keywords.Common.Colors.Yellow,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 71;BA.debugLine="Pushe.notificationOn";
_pushe.notificationOn(processBA);
 //BA.debugLineNum = 72;BA.debugLine="If aria.TestInterntConnection=True Then";
if (_aria.TestInterntConnection()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 74;BA.debugLine="connector.Initialize(Me,\"db\",Starter.Server_mysq";
mostCurrent._connector._initialize(mostCurrent.activityBA,main.getObject(),"db",mostCurrent._starter._server_mysql,mostCurrent._starter._db_name,mostCurrent._starter._db_user,mostCurrent._starter._db_pass);
 //BA.debugLineNum = 75;BA.debugLine="json_connector.Initialize(Me,\"jsn\",Starter.Serve";
mostCurrent._json_connector._initialize(mostCurrent.activityBA,main.getObject(),"jsn",mostCurrent._starter._server_json);
 //BA.debugLineNum = 76;BA.debugLine="is_connection=True";
_is_connection = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 77;BA.debugLine="Pushe.initialize()";
_pushe.initialize(processBA);
 }else {
 //BA.debugLineNum = 79;BA.debugLine="is_connection=False";
_is_connection = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 82;BA.debugLine="functions.SetRightDrawable(btn_order,\"ic_cart_whi";
mostCurrent._functions._setrightdrawable(mostCurrent.activityBA,mostCurrent._btn_order,"ic_cart_white_36dp");
 //BA.debugLineNum = 83;BA.debugLine="functions.SetRightDrawable(btn_pegiri,\"ic_downloa";
mostCurrent._functions._setrightdrawable(mostCurrent.activityBA,mostCurrent._btn_pegiri,"ic_download_white_36dp");
 //BA.debugLineNum = 84;BA.debugLine="functions.SetRightDrawable(btn_about,\"ic_help_cir";
mostCurrent._functions._setrightdrawable(mostCurrent.activityBA,mostCurrent._btn_about,"ic_help_circle_white_36dp");
 //BA.debugLineNum = 85;BA.debugLine="functions.SetRightDrawable(btn_contact,\"ic_telegr";
mostCurrent._functions._setrightdrawable(mostCurrent.activityBA,mostCurrent._btn_contact,"ic_telegram_white_36dp");
 //BA.debugLineNum = 86;BA.debugLine="functions.SetRightDrawable(btn_charj,\"ic_diamond_";
mostCurrent._functions._setrightdrawable(mostCurrent.activityBA,mostCurrent._btn_charj,"ic_diamond_white_36dp");
 //BA.debugLineNum = 92;BA.debugLine="btn_order.SetLayoutAnimated(3001,20%x,btn_order.T";
mostCurrent._btn_order.SetLayoutAnimated((int) (3001),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (20),mostCurrent.activityBA),mostCurrent._btn_order.getTop(),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA),mostCurrent._btn_order.getHeight());
 //BA.debugLineNum = 93;BA.debugLine="btn_charj.SetLayoutAnimated(3000,20%x,btn_charj.T";
mostCurrent._btn_charj.SetLayoutAnimated((int) (3000),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (20),mostCurrent.activityBA),mostCurrent._btn_charj.getTop(),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA),mostCurrent._btn_charj.getHeight());
 //BA.debugLineNum = 94;BA.debugLine="btn_pegiri.SetLayoutAnimated(3000,20%x,btn_pegiri";
mostCurrent._btn_pegiri.SetLayoutAnimated((int) (3000),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (20),mostCurrent.activityBA),mostCurrent._btn_pegiri.getTop(),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA),mostCurrent._btn_pegiri.getHeight());
 //BA.debugLineNum = 95;BA.debugLine="btn_about.SetLayoutAnimated(3000,20%x,btn_about.T";
mostCurrent._btn_about.SetLayoutAnimated((int) (3000),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (20),mostCurrent.activityBA),mostCurrent._btn_about.getTop(),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA),mostCurrent._btn_about.getHeight());
 //BA.debugLineNum = 96;BA.debugLine="btn_contact.SetLayoutAnimated(3000,20%x,btn_conta";
mostCurrent._btn_contact.SetLayoutAnimated((int) (3000),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (20),mostCurrent.activityBA),mostCurrent._btn_contact.getTop(),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA),mostCurrent._btn_contact.getHeight());
 //BA.debugLineNum = 97;BA.debugLine="btn_account.SetLayoutAnimated(3000,20%x,btn_accou";
mostCurrent._btn_account.SetLayoutAnimated((int) (3000),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (20),mostCurrent.activityBA),mostCurrent._btn_account.getTop(),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA),mostCurrent._btn_account.getHeight());
 //BA.debugLineNum = 99;BA.debugLine="ImageView1.SetVisibleAnimated(4000,True)";
mostCurrent._imageview1.SetVisibleAnimated((int) (4000),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 101;BA.debugLine="ripple(0).Initialize(btn_order,Colors.White,500,F";
mostCurrent._ripple[(int) (0)].Initialize(mostCurrent.activityBA,(android.view.View)(mostCurrent._btn_order.getObject()),anywheresoftware.b4a.keywords.Common.Colors.White,(int) (500),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 102;BA.debugLine="ripple(1).Initialize(btn_pegiri,Colors.White,500,";
mostCurrent._ripple[(int) (1)].Initialize(mostCurrent.activityBA,(android.view.View)(mostCurrent._btn_pegiri.getObject()),anywheresoftware.b4a.keywords.Common.Colors.White,(int) (500),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 103;BA.debugLine="ripple(2).Initialize(btn_about,Colors.White,500,F";
mostCurrent._ripple[(int) (2)].Initialize(mostCurrent.activityBA,(android.view.View)(mostCurrent._btn_about.getObject()),anywheresoftware.b4a.keywords.Common.Colors.White,(int) (500),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 104;BA.debugLine="ripple(3).Initialize(btn_contact,Colors.White,500";
mostCurrent._ripple[(int) (3)].Initialize(mostCurrent.activityBA,(android.view.View)(mostCurrent._btn_contact.getObject()),anywheresoftware.b4a.keywords.Common.Colors.White,(int) (500),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 105;BA.debugLine="ripple(4).Initialize(btn_account,Colors.White,500";
mostCurrent._ripple[(int) (4)].Initialize(mostCurrent.activityBA,(android.view.View)(mostCurrent._btn_account.getObject()),anywheresoftware.b4a.keywords.Common.Colors.White,(int) (500),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 106;BA.debugLine="ripple(5).Initialize(btn_charj,Colors.White,500,F";
mostCurrent._ripple[(int) (5)].Initialize(mostCurrent.activityBA,(android.view.View)(mostCurrent._btn_charj.getObject()),anywheresoftware.b4a.keywords.Common.Colors.White,(int) (500),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 107;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 148;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 150;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 109;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 110;BA.debugLine="refresh";
_refresh();
 //BA.debugLineNum = 111;BA.debugLine="End Sub";
return "";
}
public static String  _btn_about_click() throws Exception{
 //BA.debugLineNum = 196;BA.debugLine="Sub btn_about_Click";
 //BA.debugLineNum = 197;BA.debugLine="StartActivity(about)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._about.getObject()));
 //BA.debugLineNum = 198;BA.debugLine="End Sub";
return "";
}
public static String  _btn_account_click() throws Exception{
 //BA.debugLineNum = 166;BA.debugLine="Sub btn_account_Click";
 //BA.debugLineNum = 167;BA.debugLine="If functions.is_user=True Then";
if (mostCurrent._functions._is_user(mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 168;BA.debugLine="If Msgbox2(\"آیا میخواهید از حساب کاربری خود خارج";
if (anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("آیا میخواهید از حساب کاربری خود خارج شوید؟"),BA.ObjectToCharSequence("توجه"),"بله","خیر","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 169;BA.debugLine="File.Delete(Starter.rute,Starter.file_user)";
anywheresoftware.b4a.keywords.Common.File.Delete(mostCurrent._starter._rute,mostCurrent._starter._file_user);
 //BA.debugLineNum = 170;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
 }else {
 //BA.debugLineNum = 174;BA.debugLine="StartActivity(login)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._login.getObject()));
 };
 //BA.debugLineNum = 176;BA.debugLine="End Sub";
return "";
}
public static String  _btn_charj_click() throws Exception{
 //BA.debugLineNum = 218;BA.debugLine="Sub btn_charj_Click";
 //BA.debugLineNum = 219;BA.debugLine="If is_connection=True Then";
if (_is_connection==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 220;BA.debugLine="StartActivity(charj)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._charj.getObject()));
 }else {
 //BA.debugLineNum = 222;BA.debugLine="ToastMessageShow(\"اتصال اینترنت ندارید\",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("اتصال اینترنت ندارید"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 224;BA.debugLine="End Sub";
return "";
}
public static String  _btn_contact_click() throws Exception{
 //BA.debugLineNum = 178;BA.debugLine="Sub btn_contact_Click";
 //BA.debugLineNum = 179;BA.debugLine="Log(admin_bool)";
anywheresoftware.b4a.keywords.Common.Log(BA.NumberToString(_admin_bool));
 //BA.debugLineNum = 180;BA.debugLine="If is_connection=True Then";
if (_is_connection==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 181;BA.debugLine="If functions.is_user=True Then";
if (mostCurrent._functions._is_user(mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 182;BA.debugLine="If admin_bool=1 Then";
if (_admin_bool==1) { 
 //BA.debugLineNum = 183;BA.debugLine="StartActivity(list_user)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._list_user.getObject()));
 }else {
 //BA.debugLineNum = 185;BA.debugLine="ticket.id_user=map_user.Get(\"id\")";
mostCurrent._ticket._id_user = (int)(BA.ObjectToNumber(mostCurrent._map_user.Get((Object)("id"))));
 //BA.debugLineNum = 186;BA.debugLine="StartActivity(ticket)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._ticket.getObject()));
 };
 }else {
 //BA.debugLineNum = 189;BA.debugLine="ToastMessageShow(\"وارد حساب کاربری خود شوید\",Fa";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("وارد حساب کاربری خود شوید"),anywheresoftware.b4a.keywords.Common.False);
 };
 }else {
 //BA.debugLineNum = 192;BA.debugLine="ToastMessageShow(\"اتصال اینترنت ندارید\",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("اتصال اینترنت ندارید"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 194;BA.debugLine="End Sub";
return "";
}
public static String  _btn_order_click() throws Exception{
 //BA.debugLineNum = 156;BA.debugLine="Sub btn_order_Click";
 //BA.debugLineNum = 157;BA.debugLine="If is_connection=True Then";
if (_is_connection==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 158;BA.debugLine="StartActivity(order)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._order.getObject()));
 }else {
 //BA.debugLineNum = 160;BA.debugLine="ToastMessageShow(\"اتصال اینترنت ندارید\",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("اتصال اینترنت ندارید"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 163;BA.debugLine="End Sub";
return "";
}
public static String  _btn_pegiri_click() throws Exception{
 //BA.debugLineNum = 200;BA.debugLine="Sub btn_pegiri_Click";
 //BA.debugLineNum = 201;BA.debugLine="If is_connection=True Then";
if (_is_connection==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 202;BA.debugLine="If functions.is_user=True Then";
if (mostCurrent._functions._is_user(mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 203;BA.debugLine="If admin_bool=1 Then";
if (_admin_bool==1) { 
 //BA.debugLineNum = 204;BA.debugLine="StartActivity(list_history)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._list_history.getObject()));
 }else {
 //BA.debugLineNum = 206;BA.debugLine="StartActivity(history)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._history.getObject()));
 };
 }else {
 //BA.debugLineNum = 210;BA.debugLine="ToastMessageShow(\"وارد حساب کاربری خود شوید\",Fa";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("وارد حساب کاربری خود شوید"),anywheresoftware.b4a.keywords.Common.False);
 };
 }else {
 //BA.debugLineNum = 213;BA.debugLine="ToastMessageShow(\"اتصال اینترنت ندارید\",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("اتصال اینترنت ندارید"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 215;BA.debugLine="End Sub";
return "";
}
public static String  _btn_product_click() throws Exception{
 //BA.debugLineNum = 261;BA.debugLine="Sub btn_product_Click";
 //BA.debugLineNum = 262;BA.debugLine="StartActivity(products)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._products.getObject()));
 //BA.debugLineNum = 263;BA.debugLine="End Sub";
return "";
}
public static String  _db_connector(anywheresoftware.b4a.objects.collections.List _records,Object _tag) throws Exception{
anywheresoftware.b4a.objects.collections.Map _map1 = null;
b4a.example.money _toman = null;
 //BA.debugLineNum = 229;BA.debugLine="Sub db_connector(records As List,tag As Object)";
 //BA.debugLineNum = 230;BA.debugLine="Select tag";
switch (BA.switchObjectToInt(_tag,(Object)("get_coin"),(Object)("disconnect"),(Object)("eror"))) {
case 0: {
 //BA.debugLineNum = 232;BA.debugLine="Log(\"hhhhhhhhhhh\")";
anywheresoftware.b4a.keywords.Common.Log("hhhhhhhhhhh");
 //BA.debugLineNum = 233;BA.debugLine="If records.Size>0 Then";
if (_records.getSize()>0) { 
 //BA.debugLineNum = 234;BA.debugLine="Dim map1 As Map=records.Get(0)";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
_map1.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_records.Get((int) (0))));
 //BA.debugLineNum = 235;BA.debugLine="Dim toman As money";
_toman = new b4a.example.money();
 //BA.debugLineNum = 236;BA.debugLine="lbl_price.Text=\"موجودی: \" & toman.number(map1.";
mostCurrent._lbl_price.setText(BA.ObjectToCharSequence("موجودی: "+_toman._number(BA.ObjectToString(_map1.Get((Object)("wallet"))))+" "+"تومان"));
 //BA.debugLineNum = 237;BA.debugLine="admin_bool=map1.Get(\"admin\")";
_admin_bool = (int)(BA.ObjectToNumber(_map1.Get((Object)("admin"))));
 //BA.debugLineNum = 238;BA.debugLine="my_ID=map1.Get(\"id\")";
_my_id = (int)(BA.ObjectToNumber(_map1.Get((Object)("id"))));
 //BA.debugLineNum = 239;BA.debugLine="If admin_bool=1 Then";
if (_admin_bool==1) { 
 //BA.debugLineNum = 240;BA.debugLine="btn_contact.Text=\"مشاهده پیام کاربران\"";
mostCurrent._btn_contact.setText(BA.ObjectToCharSequence("مشاهده پیام کاربران"));
 //BA.debugLineNum = 241;BA.debugLine="btn_pegiri.Text=\"مشاهده گزارشات خرید\"";
mostCurrent._btn_pegiri.setText(BA.ObjectToCharSequence("مشاهده گزارشات خرید"));
 //BA.debugLineNum = 242;BA.debugLine="btn_product.Visible=True";
mostCurrent._btn_product.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 244;BA.debugLine="btn_product.Visible=False";
mostCurrent._btn_product.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 }else {
 };
 break; }
case 1: {
 //BA.debugLineNum = 251;BA.debugLine="Log(\"no internet\")";
anywheresoftware.b4a.keywords.Common.Log("no internet");
 break; }
case 2: {
 //BA.debugLineNum = 254;BA.debugLine="Log(\"eror\")";
anywheresoftware.b4a.keywords.Common.Log("eror");
 break; }
}
;
 //BA.debugLineNum = 257;BA.debugLine="progress_spot.DisMissDialog";
mostCurrent._progress_spot.DisMissDialog();
 //BA.debugLineNum = 258;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 259;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 33;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 34;BA.debugLine="Type struct_req(price_unit As Int,quantity As Int";
;
 //BA.debugLineNum = 36;BA.debugLine="Dim apc As AppCompat";
mostCurrent._apc = new de.amberhome.objects.appcompat.AppCompatBase();
 //BA.debugLineNum = 37;BA.debugLine="Private btn_order As Label";
mostCurrent._btn_order = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Private btn_pegiri As Label";
mostCurrent._btn_pegiri = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Private btn_about As Label";
mostCurrent._btn_about = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Private btn_contact As Label";
mostCurrent._btn_contact = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Private btn_account As Label";
mostCurrent._btn_account = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Private ImageView1 As ImageView";
mostCurrent._imageview1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Dim ripple(6) As RippleView";
mostCurrent._ripple = new anywheresoftware.b4a.object.RippleViewWrapper[(int) (6)];
{
int d0 = mostCurrent._ripple.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._ripple[i0] = new anywheresoftware.b4a.object.RippleViewWrapper();
}
}
;
 //BA.debugLineNum = 46;BA.debugLine="Private lbl_price As Label";
mostCurrent._lbl_price = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Private panel_base As Panel";
mostCurrent._panel_base = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 48;BA.debugLine="Dim scrol As ScrollView";
mostCurrent._scrol = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 49;BA.debugLine="Private Panel1 As Panel";
mostCurrent._panel1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 50;BA.debugLine="Private lbl_icon1 As Label";
mostCurrent._lbl_icon1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 51;BA.debugLine="Private btn_charj As Label";
mostCurrent._btn_charj = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 52;BA.debugLine="Dim map_user As Map";
mostCurrent._map_user = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 53;BA.debugLine="Dim progress_spot As SpotsDialog";
mostCurrent._progress_spot = new dmax.dialog.Spotsd();
 //BA.debugLineNum = 54;BA.debugLine="Private btn_product As Button";
mostCurrent._btn_product = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 56;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        b4a.example.frgfg.connector._process_globals();
b4a.example.frgfg.db_mysql._process_globals();
b4a.example.get_json._process_globals();
b4a.example.json_connector._process_globals();
anywheresoftware.b4a.samples.httputils2.httputils2service._process_globals();
main._process_globals();
starter._process_globals();
products._process_globals();
functions._process_globals();
list_user._process_globals();
list_history._process_globals();
ticket._process_globals();
pushejsonservice._process_globals();
order._process_globals();
charj._process_globals();
login._process_globals();
about._process_globals();
history._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 24;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 25;BA.debugLine="Public admin_bool As Int";
_admin_bool = 0;
 //BA.debugLineNum = 26;BA.debugLine="Public my_ID As Int";
_my_id = 0;
 //BA.debugLineNum = 27;BA.debugLine="Dim Pushe As Pushe";
_pushe = new co.ronash.pushe.wrapper.PusheWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Dim aria As AriaLib";
_aria = new anywheresoftware.b4a.ariagplib.ARIAlib();
 //BA.debugLineNum = 30;BA.debugLine="Dim is_connection As Boolean";
_is_connection = false;
 //BA.debugLineNum = 31;BA.debugLine="End Sub";
return "";
}
public static String  _refresh() throws Exception{
anywheresoftware.b4a.objects.drawable.ColorDrawable _bg2 = null;
 //BA.debugLineNum = 113;BA.debugLine="Sub refresh";
 //BA.debugLineNum = 114;BA.debugLine="Dim bg2 As ColorDrawable";
_bg2 = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 115;BA.debugLine="If functions.is_user=False Then";
if (mostCurrent._functions._is_user(mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 116;BA.debugLine="functions.SetRightDrawable(btn_account,\"ic_accou";
mostCurrent._functions._setrightdrawable(mostCurrent.activityBA,mostCurrent._btn_account,"ic_account_white_36dp");
 //BA.debugLineNum = 117;BA.debugLine="btn_charj.Enabled=False";
mostCurrent._btn_charj.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 118;BA.debugLine="btn_pegiri.Enabled=False";
mostCurrent._btn_pegiri.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 119;BA.debugLine="bg2.Initialize(0xB4B5AFB5,10dip)";
_bg2.Initialize((int) (0xb4b5afb5),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)));
 //BA.debugLineNum = 120;BA.debugLine="btn_charj.Background=bg2";
mostCurrent._btn_charj.setBackground((android.graphics.drawable.Drawable)(_bg2.getObject()));
 //BA.debugLineNum = 121;BA.debugLine="btn_contact.Background=bg2";
mostCurrent._btn_contact.setBackground((android.graphics.drawable.Drawable)(_bg2.getObject()));
 //BA.debugLineNum = 122;BA.debugLine="btn_pegiri.Background=bg2";
mostCurrent._btn_pegiri.setBackground((android.graphics.drawable.Drawable)(_bg2.getObject()));
 //BA.debugLineNum = 123;BA.debugLine="lbl_icon1.Visible=False";
mostCurrent._lbl_icon1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 124;BA.debugLine="lbl_price.Visible=False";
mostCurrent._lbl_price.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 125;BA.debugLine="btn_contact.Enabled=False";
mostCurrent._btn_contact.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 127;BA.debugLine="btn_account.Text=\"خروج از حساب کاربری\"";
mostCurrent._btn_account.setText(BA.ObjectToCharSequence("خروج از حساب کاربری"));
 //BA.debugLineNum = 128;BA.debugLine="functions.SetRightDrawable(btn_account,\"ic_accou";
mostCurrent._functions._setrightdrawable(mostCurrent.activityBA,mostCurrent._btn_account,"ic_account_off_white_36dp");
 //BA.debugLineNum = 129;BA.debugLine="btn_charj.Enabled=True";
mostCurrent._btn_charj.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 130;BA.debugLine="btn_contact.Enabled=True";
mostCurrent._btn_contact.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 131;BA.debugLine="btn_pegiri.Enabled=True";
mostCurrent._btn_pegiri.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 132;BA.debugLine="bg2.Initialize(0xB4228BD2,10dip)";
_bg2.Initialize((int) (0xb4228bd2),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)));
 //BA.debugLineNum = 133;BA.debugLine="btn_charj.Background=bg2";
mostCurrent._btn_charj.setBackground((android.graphics.drawable.Drawable)(_bg2.getObject()));
 //BA.debugLineNum = 134;BA.debugLine="btn_contact.Background=bg2";
mostCurrent._btn_contact.setBackground((android.graphics.drawable.Drawable)(_bg2.getObject()));
 //BA.debugLineNum = 135;BA.debugLine="btn_pegiri.Background=bg2";
mostCurrent._btn_pegiri.setBackground((android.graphics.drawable.Drawable)(_bg2.getObject()));
 //BA.debugLineNum = 136;BA.debugLine="lbl_icon1.Visible=True";
mostCurrent._lbl_icon1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 137;BA.debugLine="lbl_price.Visible=True";
mostCurrent._lbl_price.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 138;BA.debugLine="map_user=File.ReadMap(Starter.rute,Starter.file_";
mostCurrent._map_user = anywheresoftware.b4a.keywords.Common.File.ReadMap(mostCurrent._starter._rute,mostCurrent._starter._file_user);
 //BA.debugLineNum = 140;BA.debugLine="If is_connection=True Then";
if (_is_connection==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 141;BA.debugLine="progress_spot.ShowDialog";
mostCurrent._progress_spot.ShowDialog();
 //BA.debugLineNum = 142;BA.debugLine="connector.Initialize2(Me)";
mostCurrent._connector._initialize2(mostCurrent.activityBA,main.getObject());
 //BA.debugLineNum = 143;BA.debugLine="connector.send_query($\"select * from `users` wh";
mostCurrent._connector._send_query(mostCurrent.activityBA,("select * from `users` where `id`="+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",mostCurrent._map_user.Get((Object)("id")))+" "),"get_coin",(Object)(""));
 };
 };
 //BA.debugLineNum = 147;BA.debugLine="End Sub";
return "";
}
}
