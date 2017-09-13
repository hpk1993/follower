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

public class order extends android.support.v7.app.AppCompatActivity implements B4AActivity{
	public static order mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "hpksoftweare.likearzoon", "hpksoftweare.likearzoon.order");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (order).");
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
		activityBA = new BA(this, layout, processBA, "hpksoftweare.likearzoon", "hpksoftweare.likearzoon.order");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "hpksoftweare.likearzoon.order", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (order) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (order) Resume **");
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
		return order.class;
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
        BA.LogInfo("** Activity (order) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (order) Resume **");
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
public static int _off_price = 0;
public anywheresoftware.b4a.objects.collections.Map _map_user = null;
public anywheresoftware.b4a.objects.collections.List _list_irfollower = null;
public anywheresoftware.b4a.objects.collections.List _list_irview = null;
public anywheresoftware.b4a.objects.collections.List _list_ircomment = null;
public anywheresoftware.b4a.objects.collections.List _list_otherlike = null;
public anywheresoftware.b4a.objects.collections.List _list_irlike = null;
public anywheresoftware.b4a.objects.collections.List _list_likealbum = null;
public anywheresoftware.b4a.objects.collections.Map _map_min_max = null;
public de.amberhome.objects.FloatlabelEditTextWrapper _txt_page = null;
public de.amberhome.objects.FloatlabelEditTextWrapper _txt_off = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_reg = null;
public de.amberhome.objects.FloatlabelEditTextWrapper _txt_q = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_pric_esum = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper _scrol = null;
public anywheresoftware.b4a.objects.IME _ime1 = null;
public b4a.example.money _toman = null;
public dmax.dialog.Spotsd _progress_spot = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spin_service = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spin_quantity = null;
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
 //BA.debugLineNum = 39;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 40;BA.debugLine="scrol.Initialize(100%y)";
mostCurrent._scrol.Initialize(mostCurrent.activityBA,anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 41;BA.debugLine="Activity.AddView(scrol,0,0,100%x,100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._scrol.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 42;BA.debugLine="scrol.Panel.LoadLayout(\"buy\")";
mostCurrent._scrol.getPanel().LoadLayout("buy",mostCurrent.activityBA);
 //BA.debugLineNum = 43;BA.debugLine="scrol.Panel.Height=btn_reg.Top + btn_reg.Height +";
mostCurrent._scrol.getPanel().setHeight((int) (mostCurrent._btn_reg.getTop()+mostCurrent._btn_reg.getHeight()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (3))));
 //BA.debugLineNum = 45;BA.debugLine="progress_spot.Initialize2(\"در حال بارگذاری اطلاعا";
mostCurrent._progress_spot.Initialize2(mostCurrent.activityBA,BA.ObjectToCharSequence("در حال بارگذاری اطلاعات لطفا شکیبا باشید.."),mostCurrent._progress_spot.Theme_Custom,anywheresoftware.b4a.keywords.Common.Colors.White,(float) (12),anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("iransansmobile(fanum).ttf"),(int) (6),anywheresoftware.b4a.keywords.Common.Colors.Yellow,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 60;BA.debugLine="spin_service.DropdownTextColor=Colors.Black";
mostCurrent._spin_service.setDropdownTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 61;BA.debugLine="spin_quantity.DropdownTextColor=Colors.Black";
mostCurrent._spin_quantity.setDropdownTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 62;BA.debugLine="spin_quantity.TextSize=13";
mostCurrent._spin_quantity.setTextSize((float) (13));
 //BA.debugLineNum = 64;BA.debugLine="spin_quantity.Add(\"بدون محصول\")";
mostCurrent._spin_quantity.Add("بدون محصول");
 //BA.debugLineNum = 68;BA.debugLine="txt_q.InputType=txt_q.INPUT_TYPE_DECIMAL_NUMBERS";
mostCurrent._txt_q.setInputType(mostCurrent._txt_q.INPUT_TYPE_DECIMAL_NUMBERS);
 //BA.debugLineNum = 71;BA.debugLine="ime1.Initialize(\"ime1\")";
mostCurrent._ime1.Initialize("ime1");
 //BA.debugLineNum = 72;BA.debugLine="ime1.AddHeightChangedEvent";
mostCurrent._ime1.AddHeightChangedEvent(mostCurrent.activityBA);
 //BA.debugLineNum = 75;BA.debugLine="json_connector.Initialize2(Me)";
mostCurrent._json_connector._initialize2(mostCurrent.activityBA,order.getObject());
 //BA.debugLineNum = 79;BA.debugLine="connector.Initialize(Me,\"db\",Starter.Server_mysql";
mostCurrent._connector._initialize(mostCurrent.activityBA,order.getObject(),"db",mostCurrent._starter._server_mysql,mostCurrent._starter._db_name,mostCurrent._starter._db_user,mostCurrent._starter._db_pass);
 //BA.debugLineNum = 80;BA.debugLine="progress_spot.ShowDialog";
mostCurrent._progress_spot.ShowDialog();
 //BA.debugLineNum = 81;BA.debugLine="connector.send_query($\"SELECT * FROM `product` WH";
mostCurrent._connector._send_query(mostCurrent.activityBA,("SELECT * FROM `product` WHERE `show`=1 GROUP by `title` ORDER by `order_index`"),"get_service_db",(Object)(""));
 //BA.debugLineNum = 83;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 89;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 91;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 85;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 87;BA.debugLine="End Sub";
return "";
}
public static String  _btn_off_click() throws Exception{
 //BA.debugLineNum = 388;BA.debugLine="Sub btn_off_Click";
 //BA.debugLineNum = 389;BA.debugLine="Log( off_price	)";
anywheresoftware.b4a.keywords.Common.Log(BA.NumberToString(_off_price));
 //BA.debugLineNum = 390;BA.debugLine="If txt_off.Text.Trim.Length > 0 Then";
if (mostCurrent._txt_off.getText().trim().length()>0) { 
 //BA.debugLineNum = 391;BA.debugLine="If off_price <=0  Then";
if (_off_price<=0) { 
 //BA.debugLineNum = 392;BA.debugLine="json_connector.Initialize2(Me)";
mostCurrent._json_connector._initialize2(mostCurrent.activityBA,order.getObject());
 //BA.debugLineNum = 393;BA.debugLine="progress_spot.ShowDialog";
mostCurrent._progress_spot.ShowDialog();
 //BA.debugLineNum = 394;BA.debugLine="json_connector.send_query($\"d=${Starter.db_name";
mostCurrent._json_connector._send_query(mostCurrent.activityBA,("d="+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(mostCurrent._starter._db_name))+"&u="+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(mostCurrent._starter._db_user))+"&p="+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(mostCurrent._starter._db_pass))+"&codeoff="+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(mostCurrent._txt_off.getText().trim()))+""),"get_off",(Object)(""));
 }else {
 //BA.debugLineNum = 396;BA.debugLine="ToastMessageShow(\"کد تخفیف از قبل اعمال شده است";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("کد تخفیف از قبل اعمال شده است"),anywheresoftware.b4a.keywords.Common.False);
 };
 };
 //BA.debugLineNum = 401;BA.debugLine="End Sub";
return "";
}
public static String  _btn_reg_click() throws Exception{
String[] _array1 = null;
int _price = 0;
int _service_id = 0;
int _quantity = 0;
String _link_insta = "";
String _user_insta = "";
String _servis_name = "";
 //BA.debugLineNum = 101;BA.debugLine="Sub btn_reg_Click";
 //BA.debugLineNum = 102;BA.debugLine="Log(lbl_pric_esum.Tag)";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(mostCurrent._lbl_pric_esum.getTag()));
 //BA.debugLineNum = 103;BA.debugLine="Log(map_min_max.Get(\"min\"))";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(mostCurrent._map_min_max.Get((Object)("min"))));
 //BA.debugLineNum = 104;BA.debugLine="Log(map_min_max.Get(\"max\"))";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(mostCurrent._map_min_max.Get((Object)("max"))));
 //BA.debugLineNum = 105;BA.debugLine="If functions.is_user=True Then";
if (mostCurrent._functions._is_user(mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 106;BA.debugLine="If spin_service.SelectedIndex>0 And lbl_pric_esu";
if (mostCurrent._spin_service.getSelectedIndex()>0 && (mostCurrent._lbl_pric_esum.getTag()).equals((Object)("0")) == false) { 
 //BA.debugLineNum = 107;BA.debugLine="Dim array1() As String=Regex.Split(\";\" , lbl_pr";
_array1 = anywheresoftware.b4a.keywords.Common.Regex.Split(";",BA.ObjectToString(mostCurrent._lbl_pric_esum.getTag()));
 //BA.debugLineNum = 108;BA.debugLine="Dim price As Int=array1(0)";
_price = (int)(Double.parseDouble(_array1[(int) (0)]));
 //BA.debugLineNum = 109;BA.debugLine="Dim service_id As Int=array1(1)";
_service_id = (int)(Double.parseDouble(_array1[(int) (1)]));
 //BA.debugLineNum = 110;BA.debugLine="Dim quantity As Int=array1(2)";
_quantity = (int)(Double.parseDouble(_array1[(int) (2)]));
 //BA.debugLineNum = 113;BA.debugLine="If txt_page.Text.Trim.Length > 3 Then";
if (mostCurrent._txt_page.getText().trim().length()>3) { 
 //BA.debugLineNum = 114;BA.debugLine="If lbl_pric_esum.Tag <> \"0\" And txt_q.Text.Tri";
if ((mostCurrent._lbl_pric_esum.getTag()).equals((Object)("0")) == false && (double)(Double.parseDouble(mostCurrent._txt_q.getText().trim()))>=(double)(BA.ObjectToNumber(mostCurrent._map_min_max.Get((Object)("min")))) && (double)(Double.parseDouble(mostCurrent._txt_q.getText().trim()))<=(double)(BA.ObjectToNumber(mostCurrent._map_min_max.Get((Object)("max"))))) { 
 //BA.debugLineNum = 115;BA.debugLine="Log(\"$$$$\" & off_price)";
anywheresoftware.b4a.keywords.Common.Log("$$$$"+BA.NumberToString(_off_price));
 //BA.debugLineNum = 116;BA.debugLine="If (off_price<=0) And (txt_off.Text.Trim.Leng";
if ((_off_price<=0) && (mostCurrent._txt_off.getText().trim().length()>0)) { 
 //BA.debugLineNum = 117;BA.debugLine="ToastMessageShow(\"لطفا دکمه بررسی کد تخفیف ر";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("لطفا دکمه بررسی کد تخفیف را بزنید"),anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 119;BA.debugLine="Dim link_insta As String=\"\"";
_link_insta = "";
 //BA.debugLineNum = 120;BA.debugLine="Dim user_insta As String=\"\"";
_user_insta = "";
 //BA.debugLineNum = 121;BA.debugLine="If txt_page.Text.Trim.Contains(\"instagram.co";
if (mostCurrent._txt_page.getText().trim().contains("instagram.com")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 122;BA.debugLine="link_insta=txt_page.Text.Trim";
_link_insta = mostCurrent._txt_page.getText().trim();
 }else {
 //BA.debugLineNum = 124;BA.debugLine="user_insta=txt_page.Text.Trim";
_user_insta = mostCurrent._txt_page.getText().trim();
 };
 //BA.debugLineNum = 126;BA.debugLine="map_user=File.ReadMap(Starter.rute,Starter.f";
mostCurrent._map_user = anywheresoftware.b4a.keywords.Common.File.ReadMap(mostCurrent._starter._rute,mostCurrent._starter._file_user);
 //BA.debugLineNum = 127;BA.debugLine="Dim servis_name As String=spin_service.Selec";
_servis_name = mostCurrent._spin_service.getSelectedItem();
 //BA.debugLineNum = 128;BA.debugLine="json_connector.Initialize2(Me)";
mostCurrent._json_connector._initialize2(mostCurrent.activityBA,order.getObject());
 //BA.debugLineNum = 129;BA.debugLine="progress_spot.ShowDialog";
mostCurrent._progress_spot.ShowDialog();
 //BA.debugLineNum = 130;BA.debugLine="json_connector.send_query($\"NewServices=true";
mostCurrent._json_connector._send_query(mostCurrent.activityBA,("NewServices=true&ServiceID="+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_service_id))+"&quantity="+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_quantity))+"&InstagramUsername="+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_user_insta))+"&InstagramLink="+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_link_insta))+"&d="+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(mostCurrent._starter._db_name))+"&u="+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(mostCurrent._starter._db_user))+"&p="+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(mostCurrent._starter._db_pass))+"&price="+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_price))+"&userid="+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",mostCurrent._map_user.Get((Object)("id")))+"&service="+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_servis_name))+""),"new_service",(Object)(""));
 };
 }else {
 //BA.debugLineNum = 135;BA.debugLine="ToastMessageShow(\"حداقل و حداکثر تعداد را رعا";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("حداقل و حداکثر تعداد را رعایت کنید"),anywheresoftware.b4a.keywords.Common.False);
 };
 }else {
 //BA.debugLineNum = 138;BA.debugLine="ToastMessageShow(\"لینک صفحه خود را وارد کنید\",";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("لینک صفحه خود را وارد کنید"),anywheresoftware.b4a.keywords.Common.False);
 };
 }else {
 //BA.debugLineNum = 141;BA.debugLine="ToastMessageShow(\"لطفا یک رویس را انتخاب کنید\",";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("لطفا یک رویس را انتخاب کنید"),anywheresoftware.b4a.keywords.Common.False);
 };
 }else {
 //BA.debugLineNum = 144;BA.debugLine="ToastMessageShow(\"ابتدا وارد حساب کاربری خود شوی";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("ابتدا وارد حساب کاربری خود شوید"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 146;BA.debugLine="End Sub";
return "";
}
public static String  _db_connector(anywheresoftware.b4a.objects.collections.List _records,Object _tag) throws Exception{
anywheresoftware.b4a.objects.collections.List _list1 = null;
anywheresoftware.b4a.objects.collections.Map _colservices = null;
hpksoftweare.likearzoon.main._struct_req _struct1 = null;
 //BA.debugLineNum = 405;BA.debugLine="Sub db_connector(records As List,tag As Object)";
 //BA.debugLineNum = 406;BA.debugLine="Select tag";
switch (BA.switchObjectToInt(_tag,(Object)("get_service_db"),(Object)("disconnect"),(Object)("eror"))) {
case 0: {
 //BA.debugLineNum = 408;BA.debugLine="list_irfollower.Initialize";
mostCurrent._list_irfollower.Initialize();
 //BA.debugLineNum = 409;BA.debugLine="list_irview.Initialize";
mostCurrent._list_irview.Initialize();
 //BA.debugLineNum = 410;BA.debugLine="list_ircomment.Initialize";
mostCurrent._list_ircomment.Initialize();
 //BA.debugLineNum = 411;BA.debugLine="list_otherlike.Initialize";
mostCurrent._list_otherlike.Initialize();
 //BA.debugLineNum = 412;BA.debugLine="list_irlike.Initialize";
mostCurrent._list_irlike.Initialize();
 //BA.debugLineNum = 413;BA.debugLine="list_likealbum.Initialize";
mostCurrent._list_likealbum.Initialize();
 //BA.debugLineNum = 414;BA.debugLine="spin_service.Clear";
mostCurrent._spin_service.Clear();
 //BA.debugLineNum = 415;BA.debugLine="Dim list1 As List";
_list1 = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 416;BA.debugLine="list1.Initialize";
_list1.Initialize();
 //BA.debugLineNum = 417;BA.debugLine="list1.Add(\"دسته بندی خود را انتخاب کنید\")";
_list1.Add((Object)("دسته بندی خود را انتخاب کنید"));
 //BA.debugLineNum = 419;BA.debugLine="For Each colservices As Map In records";
_colservices = new anywheresoftware.b4a.objects.collections.Map();
final anywheresoftware.b4a.BA.IterableList group13 = _records;
final int groupLen13 = group13.getSize();
for (int index13 = 0;index13 < groupLen13 ;index13++){
_colservices.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(group13.Get(index13)));
 //BA.debugLineNum = 420;BA.debugLine="Dim struct1 As struct_req";
_struct1 = new hpksoftweare.likearzoon.main._struct_req();
 //BA.debugLineNum = 421;BA.debugLine="struct1.Initialize";
_struct1.Initialize();
 //BA.debugLineNum = 423;BA.debugLine="struct1.price_unit = colservices.Get(\"price_un";
_struct1.price_unit = (int)(BA.ObjectToNumber(_colservices.Get((Object)("price_unit"))));
 //BA.debugLineNum = 424;BA.debugLine="struct1. quantity= colservices.Get(\"quantity\")";
_struct1.quantity = (int)(BA.ObjectToNumber(_colservices.Get((Object)("quantity"))));
 //BA.debugLineNum = 425;BA.debugLine="struct1. price = colservices.Get(\"price\")";
_struct1.price = (int)(BA.ObjectToNumber(_colservices.Get((Object)("price"))));
 //BA.debugLineNum = 426;BA.debugLine="struct1. max_req = colservices.Get(\"max_req\")";
_struct1.max_req = (int)(BA.ObjectToNumber(_colservices.Get((Object)("max_req"))));
 //BA.debugLineNum = 427;BA.debugLine="struct1. alias  = colservices.Get(\"alias\")";
_struct1.alias = BA.ObjectToString(_colservices.Get((Object)("alias")));
 //BA.debugLineNum = 428;BA.debugLine="struct1. description  = colservices.Get(\"descr";
_struct1.description = BA.ObjectToString(_colservices.Get((Object)("description")));
 //BA.debugLineNum = 429;BA.debugLine="struct1. id  = colservices.Get(\"id_service\")	'";
_struct1.id = (int)(BA.ObjectToNumber(_colservices.Get((Object)("id_service"))));
 //BA.debugLineNum = 431;BA.debugLine="struct1. title  = colservices.Get(\"title\")";
_struct1.title = BA.ObjectToString(_colservices.Get((Object)("title")));
 //BA.debugLineNum = 432;BA.debugLine="list1.Add(struct1. title)";
_list1.Add((Object)(_struct1.title));
 //BA.debugLineNum = 434;BA.debugLine="struct1. min_req = colservices.Get(\"min_req\")";
_struct1.min_req = (int)(BA.ObjectToNumber(_colservices.Get((Object)("min_req"))));
 //BA.debugLineNum = 435;BA.debugLine="map_min_max.Initialize";
mostCurrent._map_min_max.Initialize();
 //BA.debugLineNum = 436;BA.debugLine="map_min_max.Put(\"min\",0):map_min_max.Put(\"max\"";
mostCurrent._map_min_max.Put((Object)("min"),(Object)(0));
 //BA.debugLineNum = 436;BA.debugLine="map_min_max.Put(\"min\",0):map_min_max.Put(\"max\"";
mostCurrent._map_min_max.Put((Object)("max"),(Object)(0));
 //BA.debugLineNum = 437;BA.debugLine="Select struct1.alias";
switch (BA.switchObjectToInt(_struct1.alias,"irfollower","irview","ircomment","otherlike","irlike","likealbum")) {
case 0: {
 //BA.debugLineNum = 439;BA.debugLine="list_irfollower.Add(struct1)";
mostCurrent._list_irfollower.Add((Object)(_struct1));
 break; }
case 1: {
 //BA.debugLineNum = 442;BA.debugLine="list_irview.Add(struct1)";
mostCurrent._list_irview.Add((Object)(_struct1));
 break; }
case 2: {
 //BA.debugLineNum = 445;BA.debugLine="list_ircomment.Add(struct1)";
mostCurrent._list_ircomment.Add((Object)(_struct1));
 break; }
case 3: {
 //BA.debugLineNum = 448;BA.debugLine="list_otherlike.Add(struct1)";
mostCurrent._list_otherlike.Add((Object)(_struct1));
 break; }
case 4: {
 //BA.debugLineNum = 451;BA.debugLine="list_irlike.Add(struct1)";
mostCurrent._list_irlike.Add((Object)(_struct1));
 break; }
case 5: {
 //BA.debugLineNum = 454;BA.debugLine="list_likealbum.Add(struct1)";
mostCurrent._list_likealbum.Add((Object)(_struct1));
 break; }
}
;
 }
;
 //BA.debugLineNum = 459;BA.debugLine="spin_service.AddAll(list1)";
mostCurrent._spin_service.AddAll(_list1);
 break; }
case 1: {
 //BA.debugLineNum = 462;BA.debugLine="Log(\"no internet\")";
anywheresoftware.b4a.keywords.Common.Log("no internet");
 break; }
case 2: {
 //BA.debugLineNum = 465;BA.debugLine="Log(\"eror\")";
anywheresoftware.b4a.keywords.Common.Log("eror");
 break; }
}
;
 //BA.debugLineNum = 468;BA.debugLine="progress_spot.DisMissDialog";
mostCurrent._progress_spot.DisMissDialog();
 //BA.debugLineNum = 469;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 470;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 11;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 12;BA.debugLine="Dim off_price As Int";
_off_price = 0;
 //BA.debugLineNum = 13;BA.debugLine="Dim map_user As Map";
mostCurrent._map_user = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 14;BA.debugLine="Dim list_irfollower As List";
mostCurrent._list_irfollower = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 15;BA.debugLine="Dim list_irview As List";
mostCurrent._list_irview = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 16;BA.debugLine="Dim list_ircomment As List";
mostCurrent._list_ircomment = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 17;BA.debugLine="Dim list_otherlike As List";
mostCurrent._list_otherlike = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 18;BA.debugLine="Dim list_irlike As List";
mostCurrent._list_irlike = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 19;BA.debugLine="Dim list_likealbum As List";
mostCurrent._list_likealbum = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 23;BA.debugLine="Dim map_min_max As Map";
mostCurrent._map_min_max = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 27;BA.debugLine="Private txt_page As DSFloatLabelEditText";
mostCurrent._txt_page = new de.amberhome.objects.FloatlabelEditTextWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private txt_off As DSFloatLabelEditText";
mostCurrent._txt_off = new de.amberhome.objects.FloatlabelEditTextWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private btn_reg As Button";
mostCurrent._btn_reg = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private txt_q As DSFloatLabelEditText";
mostCurrent._txt_q = new de.amberhome.objects.FloatlabelEditTextWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private lbl_pric_esum As Label";
mostCurrent._lbl_pric_esum = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Dim scrol As ScrollView";
mostCurrent._scrol = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Dim ime1 As IME";
mostCurrent._ime1 = new anywheresoftware.b4a.objects.IME();
 //BA.debugLineNum = 34;BA.debugLine="Dim toman As money";
mostCurrent._toman = new b4a.example.money();
 //BA.debugLineNum = 35;BA.debugLine="Private progress_spot As SpotsDialog";
mostCurrent._progress_spot = new dmax.dialog.Spotsd();
 //BA.debugLineNum = 36;BA.debugLine="Private spin_service,spin_quantity As Spinner";
mostCurrent._spin_service = new anywheresoftware.b4a.objects.SpinnerWrapper();
mostCurrent._spin_quantity = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 37;BA.debugLine="End Sub";
return "";
}
public static String  _ime1_heightchanged(int _newheight,int _oldheight) throws Exception{
 //BA.debugLineNum = 93;BA.debugLine="Sub ime1_HeightChanged (NewHeight As Int, OldHeigh";
 //BA.debugLineNum = 94;BA.debugLine="scrol.Height=NewHeight";
mostCurrent._scrol.setHeight(_newheight);
 //BA.debugLineNum = 95;BA.debugLine="Log(\"NewHeight =\" & NewHeight)";
anywheresoftware.b4a.keywords.Common.Log("NewHeight ="+BA.NumberToString(_newheight));
 //BA.debugLineNum = 96;BA.debugLine="End Sub";
return "";
}
public static String  _jsn_json(String _res,Object _tag) throws Exception{
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.Map _root = null;
String _state = "";
String _order = "";
String[] _array1 = null;
int _price = 0;
 //BA.debugLineNum = 287;BA.debugLine="Sub jsn_json(res As String,tag As Object)";
 //BA.debugLineNum = 288;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 289;BA.debugLine="Select tag";
switch (BA.switchObjectToInt(_tag,(Object)("get_service"),(Object)("new_service"),(Object)("get_off"),(Object)("disconnect"),(Object)("eror"))) {
default: {
 break; }
case 0: {
 break; }
case 1: {
 //BA.debugLineNum = 297;BA.debugLine="Log(res)";
anywheresoftware.b4a.keywords.Common.Log(_res);
 //BA.debugLineNum = 298;BA.debugLine="If res.Length > 0 Then";
if (_res.length()>0) { 
 //BA.debugLineNum = 299;BA.debugLine="progress_spot.DisMissDialog";
mostCurrent._progress_spot.DisMissDialog();
 //BA.debugLineNum = 300;BA.debugLine="If res.Trim.Contains(\"موجودی\")=False Then";
if (_res.trim().contains("موجودی")==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 301;BA.debugLine="parser.Initialize(res)";
_parser.Initialize(_res);
 //BA.debugLineNum = 302;BA.debugLine="Dim root As Map = parser.NextObject";
_root = new anywheresoftware.b4a.objects.collections.Map();
_root = _parser.NextObject();
 //BA.debugLineNum = 303;BA.debugLine="Dim state As String = root.Get(\"state\")";
_state = BA.ObjectToString(_root.Get((Object)("state")));
 //BA.debugLineNum = 304;BA.debugLine="Dim order As String = root.Get(\"order\")";
_order = BA.ObjectToString(_root.Get((Object)("order")));
 //BA.debugLineNum = 306;BA.debugLine="Msgbox(CRLF & \"کد پیگیری: \" & order,\"وضعیت: \"";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.CRLF+"کد پیگیری: "+_order),BA.ObjectToCharSequence("وضعیت: "+_state),mostCurrent.activityBA);
 //BA.debugLineNum = 307;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 }else {
 //BA.debugLineNum = 309;BA.debugLine="Msgbox(CRLF & res,\"عدم ثبت سفارش\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.CRLF+_res),BA.ObjectToCharSequence("عدم ثبت سفارش"),mostCurrent.activityBA);
 };
 };
 break; }
case 2: {
 //BA.debugLineNum = 315;BA.debugLine="If res.Trim.Contains(\"not\")=False Then";
if (_res.trim().contains("not")==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 316;BA.debugLine="off_price=res";
_off_price = (int)(Double.parseDouble(_res));
 //BA.debugLineNum = 317;BA.debugLine="Dim array1() As String=Regex.Split(\";\" , lbl_p";
_array1 = anywheresoftware.b4a.keywords.Common.Regex.Split(";",BA.ObjectToString(mostCurrent._lbl_pric_esum.getTag()));
 //BA.debugLineNum = 318;BA.debugLine="Dim price As Int=array1(0)";
_price = (int)(Double.parseDouble(_array1[(int) (0)]));
 //BA.debugLineNum = 319;BA.debugLine="Log(price)";
anywheresoftware.b4a.keywords.Common.Log(BA.NumberToString(_price));
 //BA.debugLineNum = 320;BA.debugLine="lbl_pric_esum.Tag=pro_off_price(price,off_pric";
mostCurrent._lbl_pric_esum.setTag((Object)(BA.NumberToString(_pro_off_price(_price,_off_price))+";"+_array1[(int) (1)]+";"+mostCurrent._txt_q.getText().trim()));
 //BA.debugLineNum = 321;BA.debugLine="lbl_pric_esum.Text=text_show_off(price)";
mostCurrent._lbl_pric_esum.setText(BA.ObjectToCharSequence(_text_show_off(_price)));
 }else {
 //BA.debugLineNum = 323;BA.debugLine="txt_off.Text=\"\"";
mostCurrent._txt_off.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 324;BA.debugLine="off_price=0";
_off_price = (int) (0);
 //BA.debugLineNum = 325;BA.debugLine="ToastMessageShow(\"کد تخفیف شما نا معتبر است\",F";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("کد تخفیف شما نا معتبر است"),anywheresoftware.b4a.keywords.Common.False);
 };
 break; }
case 3: {
 //BA.debugLineNum = 330;BA.debugLine="Log(\"no internet\")";
anywheresoftware.b4a.keywords.Common.Log("no internet");
 break; }
case 4: {
 //BA.debugLineNum = 333;BA.debugLine="Log(\"eror\")";
anywheresoftware.b4a.keywords.Common.Log("eror");
 break; }
}
;
 //BA.debugLineNum = 336;BA.debugLine="progress_spot.DisMissDialog";
mostCurrent._progress_spot.DisMissDialog();
 //BA.debugLineNum = 337;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 339;BA.debugLine="End Sub";
return "";
}
public static int  _pro_off_price(int _price,int _percentage) throws Exception{
 //BA.debugLineNum = 355;BA.debugLine="Sub pro_off_price(price As Int,percentage As Int)A";
 //BA.debugLineNum = 356;BA.debugLine="Return (	price -	((price * percentage)/100)	 )";
if (true) return (int) ((_price-((_price*_percentage)/(double)100)));
 //BA.debugLineNum = 357;BA.debugLine="End Sub";
return 0;
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 7;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="End Sub";
return "";
}
public static String  _spin_quantity_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 161;BA.debugLine="Sub spin_quantity_ItemClick (Position As Int, Valu";
 //BA.debugLineNum = 177;BA.debugLine="End Sub";
return "";
}
public static String  _spin_service_itemclick(int _position,Object _value) throws Exception{
anywheresoftware.b4a.objects.collections.List _list1 = null;
int _i = 0;
hpksoftweare.likearzoon.main._struct_req _struct1 = null;
 //BA.debugLineNum = 180;BA.debugLine="Sub spin_service_ItemClick (Position As Int, Value";
 //BA.debugLineNum = 181;BA.debugLine="Try";
try { //BA.debugLineNum = 182;BA.debugLine="spin_quantity.Clear";
mostCurrent._spin_quantity.Clear();
 //BA.debugLineNum = 183;BA.debugLine="Select spin_service.SelectedItem 	'(Position)";
switch (BA.switchObjectToInt(mostCurrent._spin_service.getSelectedItem(),"دسته بندی خود را انتخاب کنید",BA.NumberToString(11),"لایک ایرانی","لایک خارجی","Instagram Comment(IRAN)","Instagram View(IRAN)","لایک آلبوم")) {
case 0: {
 //BA.debugLineNum = 185;BA.debugLine="spin_quantity.AddAll(Array As Object(\"بدون محص";
mostCurrent._spin_quantity.AddAll(anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)("بدون محصول")}));
 //BA.debugLineNum = 186;BA.debugLine="txt_q.Text=0";
mostCurrent._txt_q.setText(BA.ObjectToCharSequence(0));
 //BA.debugLineNum = 187;BA.debugLine="lbl_pric_esum.Text=\"مجموع سفارش: \" & 0 & \" توم";
mostCurrent._lbl_pric_esum.setText(BA.ObjectToCharSequence("مجموع سفارش: "+BA.NumberToString(0)+" تومان"));
 //BA.debugLineNum = 188;BA.debugLine="txt_q.Enabled=False";
mostCurrent._txt_q.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 189;BA.debugLine="lbl_pric_esum.Tag=0";
mostCurrent._lbl_pric_esum.setTag((Object)(0));
 break; }
case 1: {
 break; }
case 2: {
 //BA.debugLineNum = 205;BA.debugLine="Dim list1 As List";
_list1 = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 206;BA.debugLine="list1.Initialize";
_list1.Initialize();
 //BA.debugLineNum = 207;BA.debugLine="For i=0 To list_irlike.Size-1";
{
final int step14 = 1;
final int limit14 = (int) (mostCurrent._list_irlike.getSize()-1);
for (_i = (int) (0) ; (step14 > 0 && _i <= limit14) || (step14 < 0 && _i >= limit14); _i = ((int)(0 + _i + step14)) ) {
 //BA.debugLineNum = 208;BA.debugLine="Dim struct1 As struct_req=list_irlike.Get(i)";
_struct1 = (hpksoftweare.likearzoon.main._struct_req)(mostCurrent._list_irlike.Get(_i));
 //BA.debugLineNum = 209;BA.debugLine="list1.Add(\"حداقل تعداد: \" & struct1.min_req &";
_list1.Add((Object)("حداقل تعداد: "+BA.NumberToString(_struct1.min_req)+"  |  حداکثر: "+BA.NumberToString(_struct1.max_req)));
 //BA.debugLineNum = 210;BA.debugLine="map_min_max.Put(\"min\",struct1.min_req):map_mi";
mostCurrent._map_min_max.Put((Object)("min"),(Object)(_struct1.min_req));
 //BA.debugLineNum = 210;BA.debugLine="map_min_max.Put(\"min\",struct1.min_req):map_mi";
mostCurrent._map_min_max.Put((Object)("max"),(Object)(_struct1.max_req));
 }
};
 //BA.debugLineNum = 212;BA.debugLine="spin_quantity.AddAll(list1)";
mostCurrent._spin_quantity.AddAll(_list1);
 //BA.debugLineNum = 213;BA.debugLine="txt_q.Text=struct1.min_req";
mostCurrent._txt_q.setText(BA.ObjectToCharSequence(_struct1.min_req));
 //BA.debugLineNum = 214;BA.debugLine="txt_q.Enabled=True";
mostCurrent._txt_q.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 215;BA.debugLine="lbl_pric_esum.Tag=pro_off_price((struct1.price";
mostCurrent._lbl_pric_esum.setTag((Object)(BA.NumberToString(_pro_off_price((int) ((_struct1.price_unit*(double)(Double.parseDouble(mostCurrent._txt_q.getText().trim())))),_off_price))+";"+BA.NumberToString(_struct1.id)+";"+mostCurrent._txt_q.getText().trim()));
 //BA.debugLineNum = 216;BA.debugLine="LogColor(list_irlike,Colors.Blue)";
anywheresoftware.b4a.keywords.Common.LogColor(BA.ObjectToString(mostCurrent._list_irlike),anywheresoftware.b4a.keywords.Common.Colors.Blue);
 //BA.debugLineNum = 217;BA.debugLine="LogColor((struct1.price_unit*txt_q.Text.Trim),";
anywheresoftware.b4a.keywords.Common.LogColor(BA.NumberToString((_struct1.price_unit*(double)(Double.parseDouble(mostCurrent._txt_q.getText().trim())))),anywheresoftware.b4a.keywords.Common.Colors.Blue);
 //BA.debugLineNum = 218;BA.debugLine="LogColor(text_show_off(struct1.price),Colors.B";
anywheresoftware.b4a.keywords.Common.LogColor(_text_show_off(_struct1.price),anywheresoftware.b4a.keywords.Common.Colors.Blue);
 //BA.debugLineNum = 219;BA.debugLine="LogColor(off_price,Colors.Blue)";
anywheresoftware.b4a.keywords.Common.LogColor(BA.NumberToString(_off_price),anywheresoftware.b4a.keywords.Common.Colors.Blue);
 //BA.debugLineNum = 220;BA.debugLine="lbl_pric_esum.Text=text_show_off((struct1.pric";
mostCurrent._lbl_pric_esum.setText(BA.ObjectToCharSequence(_text_show_off((int) ((_struct1.price_unit*(double)(Double.parseDouble(mostCurrent._txt_q.getText().trim())))))));
 break; }
case 3: {
 //BA.debugLineNum = 223;BA.debugLine="Dim list1 As List";
_list1 = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 224;BA.debugLine="list1.Initialize";
_list1.Initialize();
 //BA.debugLineNum = 225;BA.debugLine="For i=0 To list_otherlike.Size-1";
{
final int step32 = 1;
final int limit32 = (int) (mostCurrent._list_otherlike.getSize()-1);
for (_i = (int) (0) ; (step32 > 0 && _i <= limit32) || (step32 < 0 && _i >= limit32); _i = ((int)(0 + _i + step32)) ) {
 //BA.debugLineNum = 226;BA.debugLine="Dim struct1 As struct_req=list_otherlike.Get(";
_struct1 = (hpksoftweare.likearzoon.main._struct_req)(mostCurrent._list_otherlike.Get(_i));
 //BA.debugLineNum = 227;BA.debugLine="list1.Add(\"حداقل تعداد: \" & struct1.min_req &";
_list1.Add((Object)("حداقل تعداد: "+BA.NumberToString(_struct1.min_req)+"  |  حداکثر: "+BA.NumberToString(_struct1.max_req)));
 //BA.debugLineNum = 228;BA.debugLine="map_min_max.Put(\"min\",struct1.min_req):map_mi";
mostCurrent._map_min_max.Put((Object)("min"),(Object)(_struct1.min_req));
 //BA.debugLineNum = 228;BA.debugLine="map_min_max.Put(\"min\",struct1.min_req):map_mi";
mostCurrent._map_min_max.Put((Object)("max"),(Object)(_struct1.max_req));
 }
};
 //BA.debugLineNum = 230;BA.debugLine="spin_quantity.AddAll(list1)";
mostCurrent._spin_quantity.AddAll(_list1);
 //BA.debugLineNum = 231;BA.debugLine="txt_q.Text=struct1.min_req";
mostCurrent._txt_q.setText(BA.ObjectToCharSequence(_struct1.min_req));
 //BA.debugLineNum = 232;BA.debugLine="txt_q.Enabled=True";
mostCurrent._txt_q.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 233;BA.debugLine="lbl_pric_esum.Tag=pro_off_price((struct1.price";
mostCurrent._lbl_pric_esum.setTag((Object)(BA.NumberToString(_pro_off_price((int) ((_struct1.price_unit*(double)(Double.parseDouble(mostCurrent._txt_q.getText().trim())))),_off_price))+";"+BA.NumberToString(_struct1.id)+";"+mostCurrent._txt_q.getText().trim()));
 //BA.debugLineNum = 234;BA.debugLine="lbl_pric_esum.Text=text_show_off((struct1.pric";
mostCurrent._lbl_pric_esum.setText(BA.ObjectToCharSequence(_text_show_off((int) ((_struct1.price_unit*(double)(Double.parseDouble(mostCurrent._txt_q.getText().trim())))))));
 break; }
case 4: {
 //BA.debugLineNum = 237;BA.debugLine="Dim list1 As List";
_list1 = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 238;BA.debugLine="list1.Initialize";
_list1.Initialize();
 //BA.debugLineNum = 239;BA.debugLine="For i=0 To list_ircomment.Size-1";
{
final int step46 = 1;
final int limit46 = (int) (mostCurrent._list_ircomment.getSize()-1);
for (_i = (int) (0) ; (step46 > 0 && _i <= limit46) || (step46 < 0 && _i >= limit46); _i = ((int)(0 + _i + step46)) ) {
 //BA.debugLineNum = 240;BA.debugLine="Dim struct1 As struct_req=list_ircomment.Get(";
_struct1 = (hpksoftweare.likearzoon.main._struct_req)(mostCurrent._list_ircomment.Get(_i));
 //BA.debugLineNum = 241;BA.debugLine="list1.Add(\"حداقل تعداد: \" & struct1.min_req &";
_list1.Add((Object)("حداقل تعداد: "+BA.NumberToString(_struct1.min_req)+"  |  حداکثر: "+BA.NumberToString(_struct1.max_req)));
 //BA.debugLineNum = 242;BA.debugLine="map_min_max.Put(\"min\",struct1.min_req):map_mi";
mostCurrent._map_min_max.Put((Object)("min"),(Object)(_struct1.min_req));
 //BA.debugLineNum = 242;BA.debugLine="map_min_max.Put(\"min\",struct1.min_req):map_mi";
mostCurrent._map_min_max.Put((Object)("max"),(Object)(_struct1.max_req));
 }
};
 //BA.debugLineNum = 244;BA.debugLine="spin_quantity.AddAll(list1)";
mostCurrent._spin_quantity.AddAll(_list1);
 //BA.debugLineNum = 245;BA.debugLine="txt_q.Text=struct1.min_req";
mostCurrent._txt_q.setText(BA.ObjectToCharSequence(_struct1.min_req));
 //BA.debugLineNum = 246;BA.debugLine="txt_q.Enabled=True";
mostCurrent._txt_q.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 247;BA.debugLine="lbl_pric_esum.Tag=pro_off_price((struct1.price";
mostCurrent._lbl_pric_esum.setTag((Object)(BA.NumberToString(_pro_off_price((int) ((_struct1.price_unit*(double)(Double.parseDouble(mostCurrent._txt_q.getText().trim())))),_off_price))+";"+BA.NumberToString(_struct1.id)+";"+mostCurrent._txt_q.getText().trim()));
 //BA.debugLineNum = 248;BA.debugLine="lbl_pric_esum.Text=text_show_off((struct1.pric";
mostCurrent._lbl_pric_esum.setText(BA.ObjectToCharSequence(_text_show_off((int) ((_struct1.price_unit*(double)(Double.parseDouble(mostCurrent._txt_q.getText().trim())))))));
 break; }
case 5: {
 //BA.debugLineNum = 251;BA.debugLine="Dim list1 As List";
_list1 = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 252;BA.debugLine="list1.Initialize";
_list1.Initialize();
 //BA.debugLineNum = 253;BA.debugLine="For i=0 To list_irview.Size-1";
{
final int step60 = 1;
final int limit60 = (int) (mostCurrent._list_irview.getSize()-1);
for (_i = (int) (0) ; (step60 > 0 && _i <= limit60) || (step60 < 0 && _i >= limit60); _i = ((int)(0 + _i + step60)) ) {
 //BA.debugLineNum = 254;BA.debugLine="Dim struct1 As struct_req=list_irview.Get(i)";
_struct1 = (hpksoftweare.likearzoon.main._struct_req)(mostCurrent._list_irview.Get(_i));
 //BA.debugLineNum = 255;BA.debugLine="list1.Add(\"حداقل تعداد: \" & struct1.min_req &";
_list1.Add((Object)("حداقل تعداد: "+BA.NumberToString(_struct1.min_req)+"  |  حداکثر: "+BA.NumberToString(_struct1.max_req)));
 //BA.debugLineNum = 256;BA.debugLine="map_min_max.Put(\"min\",struct1.min_req):map_mi";
mostCurrent._map_min_max.Put((Object)("min"),(Object)(_struct1.min_req));
 //BA.debugLineNum = 256;BA.debugLine="map_min_max.Put(\"min\",struct1.min_req):map_mi";
mostCurrent._map_min_max.Put((Object)("max"),(Object)(_struct1.max_req));
 }
};
 //BA.debugLineNum = 258;BA.debugLine="spin_quantity.AddAll(list1)";
mostCurrent._spin_quantity.AddAll(_list1);
 //BA.debugLineNum = 259;BA.debugLine="txt_q.Text=struct1.min_req";
mostCurrent._txt_q.setText(BA.ObjectToCharSequence(_struct1.min_req));
 //BA.debugLineNum = 260;BA.debugLine="txt_q.Enabled=True";
mostCurrent._txt_q.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 261;BA.debugLine="lbl_pric_esum.Tag=pro_off_price((struct1.price";
mostCurrent._lbl_pric_esum.setTag((Object)(BA.NumberToString(_pro_off_price((int) ((_struct1.price_unit*(double)(Double.parseDouble(mostCurrent._txt_q.getText().trim())))),_off_price))+";"+BA.NumberToString(_struct1.id)+";"+mostCurrent._txt_q.getText().trim()));
 //BA.debugLineNum = 262;BA.debugLine="lbl_pric_esum.Text=text_show_off((struct1.pric";
mostCurrent._lbl_pric_esum.setText(BA.ObjectToCharSequence(_text_show_off((int) ((_struct1.price_unit*(double)(Double.parseDouble(mostCurrent._txt_q.getText().trim())))))));
 break; }
case 6: {
 //BA.debugLineNum = 265;BA.debugLine="Dim list1 As List";
_list1 = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 266;BA.debugLine="list1.Initialize";
_list1.Initialize();
 //BA.debugLineNum = 267;BA.debugLine="For i=0 To list_likealbum.Size-1";
{
final int step74 = 1;
final int limit74 = (int) (mostCurrent._list_likealbum.getSize()-1);
for (_i = (int) (0) ; (step74 > 0 && _i <= limit74) || (step74 < 0 && _i >= limit74); _i = ((int)(0 + _i + step74)) ) {
 //BA.debugLineNum = 268;BA.debugLine="Dim struct1 As struct_req=list_likealbum.Get(";
_struct1 = (hpksoftweare.likearzoon.main._struct_req)(mostCurrent._list_likealbum.Get(_i));
 //BA.debugLineNum = 269;BA.debugLine="list1.Add(\"حداقل تعداد: \" & struct1.min_req &";
_list1.Add((Object)("حداقل تعداد: "+BA.NumberToString(_struct1.min_req)+"  |  حداکثر: "+BA.NumberToString(_struct1.max_req)));
 //BA.debugLineNum = 270;BA.debugLine="map_min_max.Put(\"min\",struct1.min_req):map_mi";
mostCurrent._map_min_max.Put((Object)("min"),(Object)(_struct1.min_req));
 //BA.debugLineNum = 270;BA.debugLine="map_min_max.Put(\"min\",struct1.min_req):map_mi";
mostCurrent._map_min_max.Put((Object)("max"),(Object)(_struct1.max_req));
 }
};
 //BA.debugLineNum = 272;BA.debugLine="spin_quantity.AddAll(list1)";
mostCurrent._spin_quantity.AddAll(_list1);
 //BA.debugLineNum = 273;BA.debugLine="txt_q.Text=struct1.min_req";
mostCurrent._txt_q.setText(BA.ObjectToCharSequence(_struct1.min_req));
 //BA.debugLineNum = 274;BA.debugLine="txt_q.Enabled=True";
mostCurrent._txt_q.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 275;BA.debugLine="lbl_pric_esum.Tag=pro_off_price((struct1.price";
mostCurrent._lbl_pric_esum.setTag((Object)(BA.NumberToString(_pro_off_price((int) ((_struct1.price_unit*(double)(Double.parseDouble(mostCurrent._txt_q.getText().trim())))),_off_price))+";"+BA.NumberToString(_struct1.id)+";"+mostCurrent._txt_q.getText().trim()));
 //BA.debugLineNum = 276;BA.debugLine="lbl_pric_esum.Text=text_show_off((struct1.pric";
mostCurrent._lbl_pric_esum.setText(BA.ObjectToCharSequence(_text_show_off((int) ((_struct1.price_unit*(double)(Double.parseDouble(mostCurrent._txt_q.getText().trim())))))));
 break; }
}
;
 } 
       catch (Exception e87) {
			processBA.setLastException(e87); //BA.debugLineNum = 279;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)));
 };
 //BA.debugLineNum = 282;BA.debugLine="End Sub";
return "";
}
public static String  _text_show_off(int _price) throws Exception{
 //BA.debugLineNum = 345;BA.debugLine="Sub text_show_off(price As Int)As String";
 //BA.debugLineNum = 346;BA.debugLine="If off_price > 0 Then";
if (_off_price>0) { 
 //BA.debugLineNum = 347;BA.debugLine="Return \"مجموع سفارش: \" & toman.number(price) & \"";
if (true) return "مجموع سفارش: "+mostCurrent._toman._number(BA.NumberToString(_price))+" تومان"+" با تخفیف "+mostCurrent._toman._number(BA.NumberToString(_pro_off_price(_price,_off_price)))+" تومان";
 }else {
 //BA.debugLineNum = 349;BA.debugLine="Return \"مجموع سفارش: \" & toman.number(price) & \"";
if (true) return "مجموع سفارش: "+mostCurrent._toman._number(BA.NumberToString(_price))+" تومان";
 };
 //BA.debugLineNum = 352;BA.debugLine="End Sub";
return "";
}
public static String  _txt_off_focuschanged(boolean _hasfocus) throws Exception{
 //BA.debugLineNum = 384;BA.debugLine="Sub txt_off_FocusChanged (HasFocus As Boolean)";
 //BA.debugLineNum = 386;BA.debugLine="End Sub";
return "";
}
public static String  _txt_off_textchanged(String _old,String _new) throws Exception{
 //BA.debugLineNum = 153;BA.debugLine="Sub txt_off_TextChanged (Old As String, New As Str";
 //BA.debugLineNum = 155;BA.debugLine="End Sub";
return "";
}
public static String  _txt_page_textchanged(String _old,String _new) throws Exception{
 //BA.debugLineNum = 157;BA.debugLine="Sub txt_page_TextChanged (Old As String, New As St";
 //BA.debugLineNum = 159;BA.debugLine="End Sub";
return "";
}
public static String  _txt_q_textchanged(String _old,String _new) throws Exception{
hpksoftweare.likearzoon.main._struct_req _struct1 = null;
 //BA.debugLineNum = 359;BA.debugLine="Sub txt_q_TextChanged (Old As String, New As Strin";
 //BA.debugLineNum = 360;BA.debugLine="If spin_service.SelectedIndex> 0 Then";
if (mostCurrent._spin_service.getSelectedIndex()>0) { 
 //BA.debugLineNum = 361;BA.debugLine="Select spin_service.SelectedItem";
switch (BA.switchObjectToInt(mostCurrent._spin_service.getSelectedItem(),"لایک ایرانی","لایک خارجی","Instagram Comment(IRAN)","Instagram View(IRAN)","لایک آلبوم")) {
case 0: {
 //BA.debugLineNum = 363;BA.debugLine="Dim struct1 As struct_req=list_irlike.Get(0)";
_struct1 = (hpksoftweare.likearzoon.main._struct_req)(mostCurrent._list_irlike.Get((int) (0)));
 break; }
case 1: {
 //BA.debugLineNum = 365;BA.debugLine="Dim struct1 As struct_req=list_otherlike.Get(0";
_struct1 = (hpksoftweare.likearzoon.main._struct_req)(mostCurrent._list_otherlike.Get((int) (0)));
 break; }
case 2: {
 //BA.debugLineNum = 367;BA.debugLine="Dim struct1 As struct_req=list_ircomment.Get(0";
_struct1 = (hpksoftweare.likearzoon.main._struct_req)(mostCurrent._list_ircomment.Get((int) (0)));
 break; }
case 3: {
 //BA.debugLineNum = 369;BA.debugLine="Dim struct1 As struct_req=list_irview.Get(0)";
_struct1 = (hpksoftweare.likearzoon.main._struct_req)(mostCurrent._list_irview.Get((int) (0)));
 break; }
case 4: {
 //BA.debugLineNum = 371;BA.debugLine="Dim struct1 As struct_req=list_likealbum.Get(0";
_struct1 = (hpksoftweare.likearzoon.main._struct_req)(mostCurrent._list_likealbum.Get((int) (0)));
 break; }
}
;
 //BA.debugLineNum = 373;BA.debugLine="map_min_max.Put(\"min\",struct1.min_req):map_min_m";
mostCurrent._map_min_max.Put((Object)("min"),(Object)(_struct1.min_req));
 //BA.debugLineNum = 373;BA.debugLine="map_min_max.Put(\"min\",struct1.min_req):map_min_m";
mostCurrent._map_min_max.Put((Object)("max"),(Object)(_struct1.max_req));
 //BA.debugLineNum = 374;BA.debugLine="If txt_q.Text.Trim<>\"\" Then";
if ((mostCurrent._txt_q.getText().trim()).equals("") == false) { 
 //BA.debugLineNum = 376;BA.debugLine="lbl_pric_esum.Tag=pro_off_price((struct1.price_";
mostCurrent._lbl_pric_esum.setTag((Object)(BA.NumberToString(_pro_off_price((int) ((_struct1.price_unit*(double)(Double.parseDouble(mostCurrent._txt_q.getText().trim())))),_off_price))+";"+BA.NumberToString(_struct1.id)+";"+mostCurrent._txt_q.getText().trim()));
 //BA.debugLineNum = 377;BA.debugLine="lbl_pric_esum.Text=text_show_off(	(struct1.pric";
mostCurrent._lbl_pric_esum.setText(BA.ObjectToCharSequence(_text_show_off((int) ((_struct1.price_unit*(double)(Double.parseDouble(mostCurrent._txt_q.getText().trim())))))));
 };
 };
 //BA.debugLineNum = 382;BA.debugLine="End Sub";
return "";
}
}
