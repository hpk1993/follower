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

public class login extends android.support.v7.app.AppCompatActivity implements B4AActivity{
	public static login mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "hpksoftweare.likearzoon", "hpksoftweare.likearzoon.login");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (login).");
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
		activityBA = new BA(this, layout, processBA, "hpksoftweare.likearzoon", "hpksoftweare.likearzoon.login");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "hpksoftweare.likearzoon.login", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (login) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (login) Resume **");
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
		return login.class;
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
        BA.LogInfo("** Activity (login) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (login) Resume **");
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
public static String _type_page = "";
public dmax.dialog.Spotsd _progress_spot = null;
public de.amberhome.objects.FloatlabelEditTextWrapper _txt_user = null;
public de.amberhome.objects.FloatlabelEditTextWrapper _txt_pass = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_login = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_forget = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_reg = null;
public de.amberhome.objects.FloatlabelEditTextWrapper _txt_name = null;
public de.amberhome.objects.FloatlabelEditTextWrapper _txt_pass2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_reg = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_login = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper _scrol = null;
public anywheresoftware.b4a.objects.IME _ime1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_login_google = null;
public anywheresoftware.b4a.objects.collections.Map _map_user = null;
public anywheresoftware.b4a.objects.StringUtils _su = null;
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
 //BA.debugLineNum = 34;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 36;BA.debugLine="scrol.Initialize(100%y)";
mostCurrent._scrol.Initialize(mostCurrent.activityBA,anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 37;BA.debugLine="Activity.AddView(scrol,0,0,100%x,100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._scrol.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 38;BA.debugLine="progress_spot.Initialize2(\"در حال بارگذاری اطلاعا";
mostCurrent._progress_spot.Initialize2(mostCurrent.activityBA,BA.ObjectToCharSequence("در حال بارگذاری اطلاعات لطفا شکیبا باشید.."),mostCurrent._progress_spot.Theme_Custom,anywheresoftware.b4a.keywords.Common.Colors.White,(float) (12),anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("iransansmobile(fanum).ttf"),(int) (6),anywheresoftware.b4a.keywords.Common.Colors.Yellow,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 39;BA.debugLine="connector.Initialize2(Me)";
mostCurrent._connector._initialize2(mostCurrent.activityBA,login.getObject());
 //BA.debugLineNum = 45;BA.debugLine="initialize_layout(\"login\")";
_initialize_layout("login");
 //BA.debugLineNum = 48;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 91;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 93;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 87;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 89;BA.debugLine="End Sub";
return "";
}
public static String  _btn_login_click() throws Exception{
 //BA.debugLineNum = 232;BA.debugLine="Sub btn_login_Click";
 //BA.debugLineNum = 233;BA.debugLine="Log(functions.base64(txt_pass.Text.Trim))";
anywheresoftware.b4a.keywords.Common.Log(mostCurrent._functions._base64(mostCurrent.activityBA,mostCurrent._txt_pass.getText().trim()));
 //BA.debugLineNum = 234;BA.debugLine="progress_spot.ShowDialog";
mostCurrent._progress_spot.ShowDialog();
 //BA.debugLineNum = 235;BA.debugLine="connector.send_query($\"select * from `users` wher";
mostCurrent._connector._send_query(mostCurrent.activityBA,("select * from `users` where `pass`='"+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(mostCurrent._functions._base64(mostCurrent.activityBA,mostCurrent._txt_pass.getText().trim())))+"' And `mobile`='"+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(mostCurrent._txt_user.getText().trim()))+"' "),"login",(Object)(""));
 //BA.debugLineNum = 237;BA.debugLine="End Sub";
return "";
}
public static String  _btn_reg_click() throws Exception{
 //BA.debugLineNum = 130;BA.debugLine="Sub btn_reg_Click";
 //BA.debugLineNum = 131;BA.debugLine="If txt_name.ErrorText.Length<=0 And txt_pass.Erro";
if (mostCurrent._txt_name.getErrorText().length()<=0 && mostCurrent._txt_pass.getErrorText().length()<=0 && mostCurrent._txt_pass2.getErrorText().length()<=0 && mostCurrent._txt_user.getErrorText().length()<=0) { 
 //BA.debugLineNum = 134;BA.debugLine="connector.send_query($\"select * from `users` whe";
mostCurrent._connector._send_query(mostCurrent.activityBA,("select * from `users` where `mobile`='"+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(mostCurrent._txt_user.getText().trim()))+"' "),"check_login",(Object)(""));
 };
 //BA.debugLineNum = 139;BA.debugLine="End Sub";
return "";
}
public static String  _ckeck_validation() throws Exception{
 //BA.debugLineNum = 254;BA.debugLine="Sub ckeck_validation";
 //BA.debugLineNum = 255;BA.debugLine="Try";
try { //BA.debugLineNum = 259;BA.debugLine="If txt_name.Text.Trim.Length < 5 Then";
if (mostCurrent._txt_name.getText().trim().length()<5) { 
 //BA.debugLineNum = 260;BA.debugLine="txt_name.ErrorText=\"نام شما باید بیشتر از 4 کار";
mostCurrent._txt_name.setErrorText(BA.ObjectToCharSequence("نام شما باید بیشتر از 4 کاراکتر باشد"));
 }else {
 //BA.debugLineNum = 262;BA.debugLine="txt_name.ErrorText=\"\"";
mostCurrent._txt_name.setErrorText(BA.ObjectToCharSequence(""));
 };
 //BA.debugLineNum = 266;BA.debugLine="If txt_pass.Text.Trim.Length >= 6 Then";
if (mostCurrent._txt_pass.getText().trim().length()>=6) { 
 //BA.debugLineNum = 267;BA.debugLine="txt_pass.ErrorText=\"\"";
mostCurrent._txt_pass.setErrorText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 268;BA.debugLine="If txt_pass.Text.Trim=txt_pass2.Text.Trim Then";
if ((mostCurrent._txt_pass.getText().trim()).equals(mostCurrent._txt_pass2.getText().trim())) { 
 //BA.debugLineNum = 269;BA.debugLine="txt_pass2.ErrorText=\"\"";
mostCurrent._txt_pass2.setErrorText(BA.ObjectToCharSequence(""));
 };
 //BA.debugLineNum = 271;BA.debugLine="If txt_pass.Text.Trim<>txt_pass2.Text.Trim Then";
if ((mostCurrent._txt_pass.getText().trim()).equals(mostCurrent._txt_pass2.getText().trim()) == false) { 
 //BA.debugLineNum = 272;BA.debugLine="txt_pass2.ErrorText=\"فیلد های گذرواژه با هم مط";
mostCurrent._txt_pass2.setErrorText(BA.ObjectToCharSequence("فیلد های گذرواژه با هم مطابقت ندارند"));
 };
 }else {
 //BA.debugLineNum = 275;BA.debugLine="txt_pass.ErrorText=\"گذرواژه باید بیشتر از 5 کار";
mostCurrent._txt_pass.setErrorText(BA.ObjectToCharSequence("گذرواژه باید بیشتر از 5 کاراکتر باشد"));
 };
 //BA.debugLineNum = 278;BA.debugLine="If txt_user.Text.Trim.Length = 11 Then";
if (mostCurrent._txt_user.getText().trim().length()==11) { 
 //BA.debugLineNum = 282;BA.debugLine="txt_user.ErrorText=\"\"";
mostCurrent._txt_user.setErrorText(BA.ObjectToCharSequence(""));
 }else {
 //BA.debugLineNum = 285;BA.debugLine="txt_user.ErrorText=\"موبایل نا معتبر است  مثال:ش";
mostCurrent._txt_user.setErrorText(BA.ObjectToCharSequence("موبایل نا معتبر است  مثال:شماره را به صورت 091123456789 وارد کنید"));
 };
 } 
       catch (Exception e24) {
			processBA.setLastException(e24); //BA.debugLineNum = 291;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)));
 };
 //BA.debugLineNum = 293;BA.debugLine="End Sub";
return "";
}
public static String  _db_connector(anywheresoftware.b4a.objects.collections.List _records,Object _tag) throws Exception{
anywheresoftware.b4a.objects.collections.Map _root = null;
anywheresoftware.b4a.agraham.byteconverter.ByteConverter _byte1 = null;
 //BA.debugLineNum = 177;BA.debugLine="Sub db_connector(records As List,tag As Object)";
 //BA.debugLineNum = 178;BA.debugLine="Select tag";
switch (BA.switchObjectToInt(_tag,(Object)("check_login"),(Object)("reg"),(Object)("login"),(Object)("disconnect"),(Object)("eror"))) {
case 0: {
 //BA.debugLineNum = 180;BA.debugLine="If records.Size<=0 Then";
if (_records.getSize()<=0) { 
 //BA.debugLineNum = 181;BA.debugLine="progress_spot.ShowDialog";
mostCurrent._progress_spot.ShowDialog();
 //BA.debugLineNum = 182;BA.debugLine="connector.send_query($\"Insert into `users`(`na";
mostCurrent._connector._send_query(mostCurrent.activityBA,("Insert into `users`(`name`,`mobile`,`pass`) Values(N'"+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(mostCurrent._txt_name.getText().trim()))+"','"+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(mostCurrent._txt_user.getText().trim()))+"','"+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(mostCurrent._functions._base64(mostCurrent.activityBA,mostCurrent._txt_pass.getText().trim())))+"')"),"reg",(Object)(""));
 }else {
 //BA.debugLineNum = 184;BA.debugLine="ToastMessageShow(\"تلفن شما از قبل ثبت شده\",Tru";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("تلفن شما از قبل ثبت شده"),anywheresoftware.b4a.keywords.Common.True);
 };
 break; }
case 1: {
 //BA.debugLineNum = 188;BA.debugLine="Log(records)";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(_records));
 //BA.debugLineNum = 189;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 190;BA.debugLine="ToastMessageShow(\"ثبت نام شدید.میتوانید با دادن";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("ثبت نام شدید.میتوانید با دادن نام کاربری و گذرواژه وارد شوید "),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 191;BA.debugLine="StartActivity(Me)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,login.getObject());
 break; }
case 2: {
 //BA.debugLineNum = 194;BA.debugLine="If records.Size>0 Then";
if (_records.getSize()>0) { 
 //BA.debugLineNum = 196;BA.debugLine="map_user.Initialize";
mostCurrent._map_user.Initialize();
 //BA.debugLineNum = 197;BA.debugLine="Dim root As Map = records.Get(0)";
_root = new anywheresoftware.b4a.objects.collections.Map();
_root.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_records.Get((int) (0))));
 //BA.debugLineNum = 198;BA.debugLine="Dim byte1 As ByteConverter";
_byte1 = new anywheresoftware.b4a.agraham.byteconverter.ByteConverter();
 //BA.debugLineNum = 200;BA.debugLine="If byte1.StringFromBytes(	su.DecodeBase64(root";
if ((_byte1.StringFromBytes(mostCurrent._su.DecodeBase64(BA.ObjectToString(_root.Get((Object)("pass")))),"UTF-8")).equals(mostCurrent._txt_pass.getText().trim())) { 
 //BA.debugLineNum = 202;BA.debugLine="map_user.Put(\"id\",root.Get(\"id\")	)";
mostCurrent._map_user.Put((Object)("id"),_root.Get((Object)("id")));
 //BA.debugLineNum = 203;BA.debugLine="map_user.Put(\"mobile\",root.Get(\"mobile\")	)";
mostCurrent._map_user.Put((Object)("mobile"),_root.Get((Object)("mobile")));
 //BA.debugLineNum = 204;BA.debugLine="map_user.Put(\"name\",root.Get(\"name\")	)";
mostCurrent._map_user.Put((Object)("name"),_root.Get((Object)("name")));
 //BA.debugLineNum = 205;BA.debugLine="map_user.Put(\"pass\",root.Get(\"pass\")	)";
mostCurrent._map_user.Put((Object)("pass"),_root.Get((Object)("pass")));
 //BA.debugLineNum = 206;BA.debugLine="map_user.Put(\"admin\",root.Get(\"admin\")	)";
mostCurrent._map_user.Put((Object)("admin"),_root.Get((Object)("admin")));
 //BA.debugLineNum = 207;BA.debugLine="File.WriteMap(Starter.rute,Starter.file_user,";
anywheresoftware.b4a.keywords.Common.File.WriteMap(mostCurrent._starter._rute,mostCurrent._starter._file_user,mostCurrent._map_user);
 //BA.debugLineNum = 208;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 209;BA.debugLine="ToastMessageShow(map_user.Get(\"name\") & \" \" &";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(BA.ObjectToString(mostCurrent._map_user.Get((Object)("name")))+" "+"خوش آمدید "),anywheresoftware.b4a.keywords.Common.False);
 };
 }else {
 //BA.debugLineNum = 215;BA.debugLine="ToastMessageShow(\"تلفن یا گذرواژه شما نادرست ا";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("تلفن یا گذرواژه شما نادرست است"),anywheresoftware.b4a.keywords.Common.True);
 };
 break; }
case 3: {
 //BA.debugLineNum = 219;BA.debugLine="Log(\"no internet\")";
anywheresoftware.b4a.keywords.Common.Log("no internet");
 break; }
case 4: {
 //BA.debugLineNum = 222;BA.debugLine="Log(\"eror\")";
anywheresoftware.b4a.keywords.Common.Log("eror");
 break; }
}
;
 //BA.debugLineNum = 225;BA.debugLine="progress_spot.DisMissDialog";
mostCurrent._progress_spot.DisMissDialog();
 //BA.debugLineNum = 226;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 227;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 14;BA.debugLine="Private progress_spot As SpotsDialog";
mostCurrent._progress_spot = new dmax.dialog.Spotsd();
 //BA.debugLineNum = 15;BA.debugLine="Private txt_user As DSFloatLabelEditText";
mostCurrent._txt_user = new de.amberhome.objects.FloatlabelEditTextWrapper();
 //BA.debugLineNum = 16;BA.debugLine="Private txt_pass As DSFloatLabelEditText";
mostCurrent._txt_pass = new de.amberhome.objects.FloatlabelEditTextWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private btn_login As Button";
mostCurrent._btn_login = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private lbl_forget As Label";
mostCurrent._lbl_forget = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private lbl_reg As Label";
mostCurrent._lbl_reg = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private txt_name As DSFloatLabelEditText";
mostCurrent._txt_name = new de.amberhome.objects.FloatlabelEditTextWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private txt_pass2 As DSFloatLabelEditText";
mostCurrent._txt_pass2 = new de.amberhome.objects.FloatlabelEditTextWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private btn_reg As Button";
mostCurrent._btn_reg = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private lbl_login As Label";
mostCurrent._lbl_login = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Dim scrol As ScrollView";
mostCurrent._scrol = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Dim ime1 As IME";
mostCurrent._ime1 = new anywheresoftware.b4a.objects.IME();
 //BA.debugLineNum = 28;BA.debugLine="Private btn_login_google As Button";
mostCurrent._btn_login_google = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Dim map_user As Map";
mostCurrent._map_user = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 31;BA.debugLine="Dim su As StringUtils";
mostCurrent._su = new anywheresoftware.b4a.objects.StringUtils();
 //BA.debugLineNum = 32;BA.debugLine="End Sub";
return "";
}
public static String  _ime1_heightchanged(int _newheight,int _oldheight) throws Exception{
 //BA.debugLineNum = 50;BA.debugLine="Sub ime1_HeightChanged (NewHeight As Int, OldHeigh";
 //BA.debugLineNum = 51;BA.debugLine="scrol.Height=NewHeight";
mostCurrent._scrol.setHeight(_newheight);
 //BA.debugLineNum = 52;BA.debugLine="Log(\"NewHeight =\" & NewHeight)";
anywheresoftware.b4a.keywords.Common.Log("NewHeight ="+BA.NumberToString(_newheight));
 //BA.debugLineNum = 53;BA.debugLine="End Sub";
return "";
}
public static String  _initialize_layout(String _typee) throws Exception{
anywheresoftware.b4a.object.XmlLayoutBuilder _xmlb = null;
 //BA.debugLineNum = 56;BA.debugLine="Sub initialize_layout(typee As String)";
 //BA.debugLineNum = 57;BA.debugLine="Dim xmlb As XmlLayoutBuilder";
_xmlb = new anywheresoftware.b4a.object.XmlLayoutBuilder();
 //BA.debugLineNum = 59;BA.debugLine="If typee=\"login\" Then";
if ((_typee).equals("login")) { 
 //BA.debugLineNum = 60;BA.debugLine="scrol.Panel.RemoveAllViews";
mostCurrent._scrol.getPanel().RemoveAllViews();
 //BA.debugLineNum = 61;BA.debugLine="scrol.Panel.LoadLayout(\"login\")";
mostCurrent._scrol.getPanel().LoadLayout("login",mostCurrent.activityBA);
 //BA.debugLineNum = 62;BA.debugLine="scrol.Panel.Height=lbl_reg.Top + lbl_reg.Height";
mostCurrent._scrol.getPanel().setHeight((int) (mostCurrent._lbl_reg.getTop()+mostCurrent._lbl_reg.getHeight()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (3))));
 //BA.debugLineNum = 63;BA.debugLine="txt_pass.PasswordMode=True";
mostCurrent._txt_pass.setPasswordMode(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 64;BA.debugLine="txt_user.InputType=txt_user.INPUT_TYPE_PHONE";
mostCurrent._txt_user.setInputType(mostCurrent._txt_user.INPUT_TYPE_PHONE);
 }else if((_typee).equals("reg")) { 
 //BA.debugLineNum = 70;BA.debugLine="scrol.Panel.RemoveAllViews";
mostCurrent._scrol.getPanel().RemoveAllViews();
 //BA.debugLineNum = 71;BA.debugLine="scrol.Panel.LoadLayout(\"register\")";
mostCurrent._scrol.getPanel().LoadLayout("register",mostCurrent.activityBA);
 //BA.debugLineNum = 72;BA.debugLine="scrol.Panel.Height=lbl_login.Top + lbl_login.Hei";
mostCurrent._scrol.getPanel().setHeight((int) (mostCurrent._lbl_login.getTop()+mostCurrent._lbl_login.getHeight()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (3))));
 //BA.debugLineNum = 73;BA.debugLine="txt_pass.PasswordMode=True";
mostCurrent._txt_pass.setPasswordMode(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 74;BA.debugLine="txt_pass2.PasswordMode=True";
mostCurrent._txt_pass2.setPasswordMode(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 75;BA.debugLine="txt_name.Visible=False";
mostCurrent._txt_name.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 78;BA.debugLine="ime1.Initialize(\"ime1\")";
mostCurrent._ime1.Initialize("ime1");
 //BA.debugLineNum = 79;BA.debugLine="ime1.AddHeightChangedEvent";
mostCurrent._ime1.AddHeightChangedEvent(mostCurrent.activityBA);
 //BA.debugLineNum = 80;BA.debugLine="ime1.ShowKeyboard(txt_pass)";
mostCurrent._ime1.ShowKeyboard((android.view.View)(mostCurrent._txt_pass.getObject()));
 //BA.debugLineNum = 84;BA.debugLine="End Sub";
return "";
}
public static String  _jsn_json(String _res,Object _tag) throws Exception{
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
 //BA.debugLineNum = 145;BA.debugLine="Sub jsn_json(res As String,tag As Object)";
 //BA.debugLineNum = 146;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 147;BA.debugLine="Select tag";
switch (BA.switchObjectToInt(_tag,(Object)("forget"),(Object)("disconnect"),(Object)("eror"))) {
default: {
 break; }
case 0: {
 //BA.debugLineNum = 150;BA.debugLine="Log(\"res : \" & res)";
anywheresoftware.b4a.keywords.Common.Log("res : "+_res);
 //BA.debugLineNum = 151;BA.debugLine="ToastMessageShow(res,True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(_res),anywheresoftware.b4a.keywords.Common.True);
 break; }
case 1: {
 //BA.debugLineNum = 153;BA.debugLine="Log(\"no internet\")";
anywheresoftware.b4a.keywords.Common.Log("no internet");
 break; }
case 2: {
 //BA.debugLineNum = 156;BA.debugLine="Log(\"eror\")";
anywheresoftware.b4a.keywords.Common.Log("eror");
 break; }
}
;
 //BA.debugLineNum = 159;BA.debugLine="progress_spot.DisMissDialog";
mostCurrent._progress_spot.DisMissDialog();
 //BA.debugLineNum = 160;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 162;BA.debugLine="End Sub";
return "";
}
public static String  _lbl_forget_click() throws Exception{
 //BA.debugLineNum = 106;BA.debugLine="Sub lbl_forget_Click";
 //BA.debugLineNum = 107;BA.debugLine="If txt_user.Text.Trim.Length=11 Then";
if (mostCurrent._txt_user.getText().trim().length()==11) { 
 //BA.debugLineNum = 108;BA.debugLine="progress_spot.ShowDialog";
mostCurrent._progress_spot.ShowDialog();
 //BA.debugLineNum = 109;BA.debugLine="lbl_forget.Visible=False";
mostCurrent._lbl_forget.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 110;BA.debugLine="json_connector.Initialize2(Me)";
mostCurrent._json_connector._initialize2(mostCurrent.activityBA,login.getObject());
 //BA.debugLineNum = 111;BA.debugLine="json_connector.send_query($\"forgetpassword_me=${";
mostCurrent._json_connector._send_query(mostCurrent.activityBA,("forgetpassword_me="+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(mostCurrent._txt_user.getText().trim()))+"&d="+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(mostCurrent._starter._db_name))+"&u="+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(mostCurrent._starter._db_user))+"&p="+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(mostCurrent._starter._db_pass))+""),"forget",(Object)(""));
 }else {
 //BA.debugLineNum = 114;BA.debugLine="ToastMessageShow(\"لطفا شماره تلفن را به درستی وا";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("لطفا شماره تلفن را به درستی وارد کنید"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 116;BA.debugLine="End Sub";
return "";
}
public static String  _lbl_login_click() throws Exception{
 //BA.debugLineNum = 119;BA.debugLine="Sub lbl_login_Click";
 //BA.debugLineNum = 120;BA.debugLine="If Not(type_page=\"login\") Then";
if (anywheresoftware.b4a.keywords.Common.Not((_type_page).equals("login"))) { 
 //BA.debugLineNum = 121;BA.debugLine="type_page=\"login\"";
_type_page = "login";
 //BA.debugLineNum = 122;BA.debugLine="initialize_layout(type_page)";
_initialize_layout(_type_page);
 };
 //BA.debugLineNum = 125;BA.debugLine="End Sub";
return "";
}
public static String  _lbl_reg_click() throws Exception{
 //BA.debugLineNum = 98;BA.debugLine="Sub lbl_reg_Click";
 //BA.debugLineNum = 99;BA.debugLine="Log(type_page)";
anywheresoftware.b4a.keywords.Common.Log(_type_page);
 //BA.debugLineNum = 100;BA.debugLine="If Not(type_page=\"reg\") Then";
if (anywheresoftware.b4a.keywords.Common.Not((_type_page).equals("reg"))) { 
 //BA.debugLineNum = 101;BA.debugLine="type_page=\"reg\"";
_type_page = "reg";
 //BA.debugLineNum = 102;BA.debugLine="initialize_layout(type_page)";
_initialize_layout(_type_page);
 };
 //BA.debugLineNum = 104;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 8;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Public type_page As String=\"login\"";
_type_page = "login";
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
public static String  _txt_name_textchanged(String _old,String _new) throws Exception{
 //BA.debugLineNum = 250;BA.debugLine="Sub txt_name_TextChanged (Old As String, New As St";
 //BA.debugLineNum = 251;BA.debugLine="ckeck_validation";
_ckeck_validation();
 //BA.debugLineNum = 252;BA.debugLine="End Sub";
return "";
}
public static String  _txt_pass_textchanged(String _old,String _new) throws Exception{
 //BA.debugLineNum = 241;BA.debugLine="Sub txt_pass_TextChanged (Old As String, New As St";
 //BA.debugLineNum = 243;BA.debugLine="ckeck_validation";
_ckeck_validation();
 //BA.debugLineNum = 244;BA.debugLine="End Sub";
return "";
}
public static String  _txt_pass2_textchanged(String _old,String _new) throws Exception{
 //BA.debugLineNum = 295;BA.debugLine="Sub txt_pass2_TextChanged (Old As String, New As S";
 //BA.debugLineNum = 296;BA.debugLine="ckeck_validation";
_ckeck_validation();
 //BA.debugLineNum = 297;BA.debugLine="End Sub";
return "";
}
public static String  _txt_user_textchanged(String _old,String _new) throws Exception{
 //BA.debugLineNum = 246;BA.debugLine="Sub txt_user_TextChanged (Old As String, New As St";
 //BA.debugLineNum = 247;BA.debugLine="ckeck_validation";
_ckeck_validation();
 //BA.debugLineNum = 248;BA.debugLine="End Sub";
return "";
}
public static String  _userlogin(anywheresoftware.b4a.objects.collections.Map _data,boolean _login) throws Exception{
 //BA.debugLineNum = 166;BA.debugLine="Sub UserLogin(Data As Map,login As Boolean)";
 //BA.debugLineNum = 167;BA.debugLine="progress_spot.DisMissDialog";
mostCurrent._progress_spot.DisMissDialog();
 //BA.debugLineNum = 173;BA.debugLine="json_connector.send_query($\"name=${Data.Get(\"name";
mostCurrent._json_connector._send_query(mostCurrent.activityBA,("name="+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",_data.Get((Object)("name")))+"&email="+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",_data.Get((Object)("email")))+"&google=true"),"check_google",(Object)(""));
 //BA.debugLineNum = 174;BA.debugLine="End Sub";
return "";
}
}
