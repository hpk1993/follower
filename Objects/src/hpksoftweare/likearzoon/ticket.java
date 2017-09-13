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

public class ticket extends android.support.v7.app.AppCompatActivity implements B4AActivity{
	public static ticket mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "hpksoftweare.likearzoon", "hpksoftweare.likearzoon.ticket");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (ticket).");
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
		activityBA = new BA(this, layout, processBA, "hpksoftweare.likearzoon", "hpksoftweare.likearzoon.ticket");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "hpksoftweare.likearzoon.ticket", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (ticket) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (ticket) Resume **");
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
		return ticket.class;
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
        BA.LogInfo("** Activity (ticket) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (ticket) Resume **");
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
public static int _id_user = 0;
public dmax.dialog.Spotsd _progress_spot = null;
public de.donmanfred.ChatViewWrapper _chat = null;
public de.donmanfred.MessageBuilderWrapper _builder = null;
public anywheresoftware.b4a.objects.collections.List _messages = null;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bmp_user = null;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bmp_admin = null;
public static String _title_msg = "";
public static String _last_text = "";
public anywheresoftware.b4a.objects.collections.Map _map_user = null;
public static int _admin_user = 0;
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
anywheresoftware.b4a.objects.IntentWrapper _intent1 = null;
String _notification_tag = "";
 //BA.debugLineNum = 28;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 29;BA.debugLine="If functions.is_user=True Then";
if (mostCurrent._functions._is_user(mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 30;BA.debugLine="Dim Intent1 As Intent";
_intent1 = new anywheresoftware.b4a.objects.IntentWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Intent1 = Activity.GetStartingIntent";
_intent1 = mostCurrent._activity.GetStartingIntent();
 //BA.debugLineNum = 32;BA.debugLine="If Intent1.HasExtra(\"Notification_Tag\") Then";
if (_intent1.HasExtra("Notification_Tag")) { 
 //BA.debugLineNum = 33;BA.debugLine="Try";
try { //BA.debugLineNum = 34;BA.debugLine="Dim Notification_Tag As String= Intent1.GetExt";
_notification_tag = BA.ObjectToString(_intent1.GetExtra("Notification_Tag"));
 //BA.debugLineNum = 35;BA.debugLine="id_user=Notification_Tag";
_id_user = (int)(Double.parseDouble(_notification_tag));
 } 
       catch (Exception e9) {
			processBA.setLastException(e9); };
 };
 //BA.debugLineNum = 41;BA.debugLine="chat.Initialize(\"chat\")";
mostCurrent._chat.Initialize(mostCurrent.activityBA,"chat");
 //BA.debugLineNum = 42;BA.debugLine="Activity.AddView(chat,0,0,100%x,100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._chat.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 43;BA.debugLine="progress_spot.Initialize2(\"در حال بارگذاری اطلاع";
mostCurrent._progress_spot.Initialize2(mostCurrent.activityBA,BA.ObjectToCharSequence("در حال بارگذاری اطلاعات لطفا شکیبا باشید.."),mostCurrent._progress_spot.Theme_Custom,anywheresoftware.b4a.keywords.Common.Colors.White,(float) (12),anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("iransansmobile(fanum).ttf"),(int) (6),anywheresoftware.b4a.keywords.Common.Colors.Yellow,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 45;BA.debugLine="map_user=File.ReadMap(Starter.rute,Starter.file_";
mostCurrent._map_user = anywheresoftware.b4a.keywords.Common.File.ReadMap(mostCurrent._starter._rute,mostCurrent._starter._file_user);
 //BA.debugLineNum = 47;BA.debugLine="admin_user=map_user.Get(\"admin\")";
_admin_user = (int)(BA.ObjectToNumber(mostCurrent._map_user.Get((Object)("admin"))));
 //BA.debugLineNum = 49;BA.debugLine="bmp_user.Initialize(File.DirAssets,\"user.png\")";
mostCurrent._bmp_user.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"user.png");
 //BA.debugLineNum = 50;BA.debugLine="bmp_admin.Initialize(File.DirAssets,\"admin.png\")";
mostCurrent._bmp_admin.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"admin.png");
 //BA.debugLineNum = 52;BA.debugLine="connector.Initialize(Me,\"db\",Starter.Server_mysq";
mostCurrent._connector._initialize(mostCurrent.activityBA,ticket.getObject(),"db",mostCurrent._starter._server_mysql,mostCurrent._starter._db_name,mostCurrent._starter._db_user,mostCurrent._starter._db_pass);
 //BA.debugLineNum = 53;BA.debugLine="progress_spot.ShowDialog";
mostCurrent._progress_spot.ShowDialog();
 //BA.debugLineNum = 54;BA.debugLine="connector.send_query($\"SELECT `ticket`.*,`users`";
mostCurrent._connector._send_query(mostCurrent.activityBA,("SELECT `ticket`.*,`users`.`name` FROM `ticket`\n"+"left join `users` ON `users`.`id`=`ticket`.`user_id`\n"+"Where `user_id`="+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_id_user))+" order by `id` ASC"),"get_msg",(Object)(""));
 };
 //BA.debugLineNum = 59;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 65;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 67;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 61;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 63;BA.debugLine="End Sub";
return "";
}
public static String  _callmsg(Object[] _arg0) throws Exception{
 //BA.debugLineNum = 166;BA.debugLine="public Sub callmsg(arg0() As Object)";
 //BA.debugLineNum = 167;BA.debugLine="If arg0(3)=0 Then";
if ((_arg0[(int) (3)]).equals((Object)(0))) { 
 //BA.debugLineNum = 168;BA.debugLine="initializeMsg(	arg0(0),arg0(1) ,arg0(2),1,	False";
_initializemsg(BA.ObjectToString(_arg0[(int) (0)]),BA.ObjectToString(_arg0[(int) (1)]),BA.ObjectToString(_arg0[(int) (2)]),(int) (1),anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 170;BA.debugLine="initializeMsg(	arg0(0),arg0(1) ,arg0(2),1,	True";
_initializemsg(BA.ObjectToString(_arg0[(int) (0)]),BA.ObjectToString(_arg0[(int) (1)]),BA.ObjectToString(_arg0[(int) (2)]),(int) (1),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 173;BA.debugLine="End Sub";
return "";
}
public static String  _chat_onsendclicked(String _text) throws Exception{
 //BA.debugLineNum = 69;BA.debugLine="Sub chat_onSendClicked(text As String)";
 //BA.debugLineNum = 70;BA.debugLine="Log(text)";
anywheresoftware.b4a.keywords.Common.Log(_text);
 //BA.debugLineNum = 71;BA.debugLine="If functions.is_user=True Then";
if (mostCurrent._functions._is_user(mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 72;BA.debugLine="If text.Trim.Length > 0 Then";
if (_text.trim().length()>0) { 
 //BA.debugLineNum = 73;BA.debugLine="map_user=File.ReadMap(Starter.rute,Starter.file";
mostCurrent._map_user = anywheresoftware.b4a.keywords.Common.File.ReadMap(mostCurrent._starter._rute,mostCurrent._starter._file_user);
 //BA.debugLineNum = 74;BA.debugLine="last_text=text";
mostCurrent._last_text = _text;
 //BA.debugLineNum = 75;BA.debugLine="connector.Initialize2(Me)";
mostCurrent._connector._initialize2(mostCurrent.activityBA,ticket.getObject());
 //BA.debugLineNum = 76;BA.debugLine="progress_spot.ShowDialog";
mostCurrent._progress_spot.ShowDialog();
 //BA.debugLineNum = 78;BA.debugLine="StartService(PusheJsonService)";
anywheresoftware.b4a.keywords.Common.StartService(mostCurrent.activityBA,(Object)(mostCurrent._pushejsonservice.getObject()));
 //BA.debugLineNum = 80;BA.debugLine="json_connector.Initialize(Me,\"jsn\",Starter.Serv";
mostCurrent._json_connector._initialize(mostCurrent.activityBA,ticket.getObject(),"jsn",mostCurrent._starter._server_push);
 //BA.debugLineNum = 81;BA.debugLine="Log($\"chat=true&admin_user=${admin_user}&text=$";
anywheresoftware.b4a.keywords.Common.Log(("chat=true&admin_user="+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_admin_user))+"&text="+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_text))+"&d="+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(mostCurrent._starter._db_name))+"&u="+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(mostCurrent._starter._db_user))+"&p="+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(mostCurrent._starter._db_pass))+"&uid="+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_id_user))+""));
 //BA.debugLineNum = 82;BA.debugLine="json_connector.send_query($\"chat=true&name=${ma";
mostCurrent._json_connector._send_query(mostCurrent.activityBA,("chat=true&name="+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",mostCurrent._map_user.Get((Object)("name")))+"&admin_user="+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_admin_user))+"&text="+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_text))+"&d="+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(mostCurrent._starter._db_name))+"&u="+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(mostCurrent._starter._db_user))+"&p="+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(mostCurrent._starter._db_pass))+"&uid="+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_id_user))+""),"save_ticket",(Object)(""));
 };
 }else {
 //BA.debugLineNum = 85;BA.debugLine="ToastMessageShow(\"ابتدا وارد حساب کاربری خود شوی";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("ابتدا وارد حساب کاربری خود شوید"),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 89;BA.debugLine="End Sub";
return "";
}
public static String  _db_connector(anywheresoftware.b4a.objects.collections.List _records,Object _tag) throws Exception{
int _i = 0;
anywheresoftware.b4a.objects.collections.Map _map1 = null;
int _sender_admin = 0;
int _user_id = 0;
String _msg_txt = "";
String _name = "";
String _date = "";
boolean _is_admin = false;
 //BA.debugLineNum = 122;BA.debugLine="Sub db_connector(records As List,tag As Object)";
 //BA.debugLineNum = 123;BA.debugLine="Select tag";
switch (BA.switchObjectToInt(_tag,(Object)("reg"),(Object)("get_msg"),(Object)("disconnect"),(Object)("eror"))) {
case 0: {
 //BA.debugLineNum = 125;BA.debugLine="ToastMessageShow(\"تیکت شما ثبت شد\",True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("تیکت شما ثبت شد"),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 126;BA.debugLine="If admin_user=1 Then initializeMsg(map_user.Get";
if (_admin_user==1) { 
_initializemsg(BA.ObjectToString(mostCurrent._map_user.Get((Object)("name"))),mostCurrent._last_text,"لحظاتی پیش",(int) (1),anywheresoftware.b4a.keywords.Common.True);};
 //BA.debugLineNum = 127;BA.debugLine="If admin_user=0 Then initializeMsg(map_user.Get";
if (_admin_user==0) { 
_initializemsg(BA.ObjectToString(mostCurrent._map_user.Get((Object)("name"))),mostCurrent._last_text,"لحظاتی پیش",(int) (1),anywheresoftware.b4a.keywords.Common.False);};
 //BA.debugLineNum = 129;BA.debugLine="chat.InputText=\"\"";
mostCurrent._chat.setInputText("");
 break; }
case 1: {
 //BA.debugLineNum = 132;BA.debugLine="Log(records)";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(_records));
 //BA.debugLineNum = 133;BA.debugLine="If records.Size>0 Then";
if (_records.getSize()>0) { 
 //BA.debugLineNum = 134;BA.debugLine="For i=0 To records.Size-1";
{
final int step10 = 1;
final int limit10 = (int) (_records.getSize()-1);
for (_i = (int) (0) ; (step10 > 0 && _i <= limit10) || (step10 < 0 && _i >= limit10); _i = ((int)(0 + _i + step10)) ) {
 //BA.debugLineNum = 135;BA.debugLine="Dim map1 As Map=records.Get(i)";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
_map1.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_records.Get(_i)));
 //BA.debugLineNum = 136;BA.debugLine="Dim sender_admin As Int=map1.Get(\"sender_admi";
_sender_admin = (int)(BA.ObjectToNumber(_map1.Get((Object)("sender_admin"))));
 //BA.debugLineNum = 137;BA.debugLine="Dim user_id As Int=map1.Get(\"user_id\")";
_user_id = (int)(BA.ObjectToNumber(_map1.Get((Object)("user_id"))));
 //BA.debugLineNum = 138;BA.debugLine="Dim msg_txt As String=map1.Get(\"msg\")";
_msg_txt = BA.ObjectToString(_map1.Get((Object)("msg")));
 //BA.debugLineNum = 139;BA.debugLine="Dim name As String=map1.Get(\"name\")";
_name = BA.ObjectToString(_map1.Get((Object)("name")));
 //BA.debugLineNum = 140;BA.debugLine="Dim date As String=map1.Get(\"date\")";
_date = BA.ObjectToString(_map1.Get((Object)("date")));
 //BA.debugLineNum = 141;BA.debugLine="If i=0 Then title_msg=map1.Get(\"title\")";
if (_i==0) { 
mostCurrent._title_msg = BA.ObjectToString(_map1.Get((Object)("title")));};
 //BA.debugLineNum = 142;BA.debugLine="Dim is_admin As Boolean";
_is_admin = false;
 //BA.debugLineNum = 143;BA.debugLine="If sender_admin=1 Then";
if (_sender_admin==1) { 
 //BA.debugLineNum = 144;BA.debugLine="is_admin=True";
_is_admin = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 146;BA.debugLine="is_admin=False";
_is_admin = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 149;BA.debugLine="initializeMsg(name,msg_txt,date,i,is_admin)";
_initializemsg(_name,_msg_txt,_date,_i,_is_admin);
 }
};
 };
 break; }
case 2: {
 //BA.debugLineNum = 155;BA.debugLine="Log(\"no internet\")";
anywheresoftware.b4a.keywords.Common.Log("no internet");
 break; }
case 3: {
 //BA.debugLineNum = 158;BA.debugLine="Log(\"eror\")";
anywheresoftware.b4a.keywords.Common.Log("eror");
 break; }
}
;
 //BA.debugLineNum = 161;BA.debugLine="progress_spot.DisMissDialog";
mostCurrent._progress_spot.DisMissDialog();
 //BA.debugLineNum = 162;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 163;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 13;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 14;BA.debugLine="Private progress_spot As SpotsDialog";
mostCurrent._progress_spot = new dmax.dialog.Spotsd();
 //BA.debugLineNum = 15;BA.debugLine="Private chat As ChatView";
mostCurrent._chat = new de.donmanfred.ChatViewWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private builder As MessageBuilder";
mostCurrent._builder = new de.donmanfred.MessageBuilderWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Dim messages As List";
mostCurrent._messages = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 20;BA.debugLine="Dim bmp_user,bmp_admin As Bitmap";
mostCurrent._bmp_user = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
mostCurrent._bmp_admin = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Dim title_msg As String";
mostCurrent._title_msg = "";
 //BA.debugLineNum = 22;BA.debugLine="Dim last_text As String";
mostCurrent._last_text = "";
 //BA.debugLineNum = 23;BA.debugLine="Dim map_user As Map";
mostCurrent._map_user = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 25;BA.debugLine="Dim admin_user As Int";
_admin_user = 0;
 //BA.debugLineNum = 26;BA.debugLine="End Sub";
return "";
}
public static String  _initializemsg(String _username,String _txtmsg,String _date,int _index,boolean _is_admin) throws Exception{
de.donmanfred.MessageWrapper _msg = null;
String _date_str = "";
String[] _date1 = null;
com.b4a.manamsoftware.PersianDate.ManamPersianDate _mp = null;
 //BA.debugLineNum = 176;BA.debugLine="Sub initializeMsg(username As String,txtmsg As St";
 //BA.debugLineNum = 180;BA.debugLine="messages.Initialize";
mostCurrent._messages.Initialize();
 //BA.debugLineNum = 182;BA.debugLine="Dim msg As Message";
_msg = new de.donmanfred.MessageWrapper();
 //BA.debugLineNum = 183;BA.debugLine="builder.Initialize(\"\")";
mostCurrent._builder.Initialize(processBA,"");
 //BA.debugLineNum = 185;BA.debugLine="If admin_user=1 Then";
if (_admin_user==1) { 
 //BA.debugLineNum = 186;BA.debugLine="If is_admin=True Then";
if (_is_admin==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 187;BA.debugLine="If index=0 Then";
if (_index==0) { 
 //BA.debugLineNum = 188;BA.debugLine="msg = builder.setUserIcon(bmp_admin).setMessag";
_msg.setObject((jp.bassaer.chatmessageview.models.Message)(mostCurrent._builder.setUserIcon((android.graphics.Bitmap)(mostCurrent._bmp_admin.getObject())).setMessageText(_txtmsg).setRightMessage(anywheresoftware.b4a.keywords.Common.True).setUserName("مدیریت").build()));
 }else {
 //BA.debugLineNum = 190;BA.debugLine="msg = builder.setUserIcon(bmp_admin).setMessag";
_msg.setObject((jp.bassaer.chatmessageview.models.Message)(mostCurrent._builder.setUserIcon((android.graphics.Bitmap)(mostCurrent._bmp_admin.getObject())).setMessageText(_txtmsg).setRightMessage(anywheresoftware.b4a.keywords.Common.True).setUserName("").build()));
 };
 }else {
 //BA.debugLineNum = 193;BA.debugLine="If index=0 Then";
if (_index==0) { 
 //BA.debugLineNum = 194;BA.debugLine="msg = builder.setUserIcon(bmp_user).setMessage";
_msg.setObject((jp.bassaer.chatmessageview.models.Message)(mostCurrent._builder.setUserIcon((android.graphics.Bitmap)(mostCurrent._bmp_user.getObject())).setMessageText(_txtmsg).setRightMessage(anywheresoftware.b4a.keywords.Common.False).setUserName(_username).build()));
 }else {
 //BA.debugLineNum = 196;BA.debugLine="msg = builder.setUserIcon(bmp_user).setMessage";
_msg.setObject((jp.bassaer.chatmessageview.models.Message)(mostCurrent._builder.setUserIcon((android.graphics.Bitmap)(mostCurrent._bmp_user.getObject())).setMessageText(_txtmsg).setRightMessage(anywheresoftware.b4a.keywords.Common.False).setUserName("").build()));
 };
 };
 }else {
 //BA.debugLineNum = 202;BA.debugLine="If is_admin=True Then";
if (_is_admin==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 203;BA.debugLine="If index=0 Then";
if (_index==0) { 
 //BA.debugLineNum = 204;BA.debugLine="msg = builder.setUserIcon(bmp_admin).setMessag";
_msg.setObject((jp.bassaer.chatmessageview.models.Message)(mostCurrent._builder.setUserIcon((android.graphics.Bitmap)(mostCurrent._bmp_admin.getObject())).setMessageText(_txtmsg).setRightMessage(anywheresoftware.b4a.keywords.Common.False).setUserName("مدیریت").build()));
 }else {
 //BA.debugLineNum = 206;BA.debugLine="msg = builder.setUserIcon(bmp_admin).setMessag";
_msg.setObject((jp.bassaer.chatmessageview.models.Message)(mostCurrent._builder.setUserIcon((android.graphics.Bitmap)(mostCurrent._bmp_admin.getObject())).setMessageText(_txtmsg).setRightMessage(anywheresoftware.b4a.keywords.Common.False).setUserName("").build()));
 };
 }else {
 //BA.debugLineNum = 209;BA.debugLine="If index=0 Then";
if (_index==0) { 
 //BA.debugLineNum = 210;BA.debugLine="msg = builder.setUserIcon(bmp_user).setMessage";
_msg.setObject((jp.bassaer.chatmessageview.models.Message)(mostCurrent._builder.setUserIcon((android.graphics.Bitmap)(mostCurrent._bmp_user.getObject())).setMessageText(_txtmsg).setRightMessage(anywheresoftware.b4a.keywords.Common.True).setUserName(_username).build()));
 }else {
 //BA.debugLineNum = 212;BA.debugLine="msg = builder.setUserIcon(bmp_user).setMessage";
_msg.setObject((jp.bassaer.chatmessageview.models.Message)(mostCurrent._builder.setUserIcon((android.graphics.Bitmap)(mostCurrent._bmp_user.getObject())).setMessageText(_txtmsg).setRightMessage(anywheresoftware.b4a.keywords.Common.True).setUserName("").build()));
 };
 };
 };
 //BA.debugLineNum = 220;BA.debugLine="If date.Contains(\"لحظاتی پیش\")=False Then";
if (_date.contains("لحظاتی پیش")==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 221;BA.debugLine="Dim date_str As String=date";
_date_str = _date;
 //BA.debugLineNum = 222;BA.debugLine="Dim date1() As String=Regex.Split(\"-\", date_str.";
_date1 = anywheresoftware.b4a.keywords.Common.Regex.Split("-",_date_str.substring((int) (0),(int) (11)));
 //BA.debugLineNum = 223;BA.debugLine="Dim MP As ManamPersianDate";
_mp = new com.b4a.manamsoftware.PersianDate.ManamPersianDate();
 //BA.debugLineNum = 224;BA.debugLine="msg.TimeText= MP.GregorianToPersian(date1(0) , d";
_msg.setTimeText(_mp.GregorianToPersian((int)(Double.parseDouble(_date1[(int) (0)])),(int)(Double.parseDouble(_date1[(int) (1)])),(int)(Double.parseDouble(_date1[(int) (2)])))+"  "+mostCurrent._functions._convertnumbers2persian(mostCurrent.activityBA,_date_str.substring((int) (12),_date_str.length())));
 };
 //BA.debugLineNum = 229;BA.debugLine="msg.DateCell=False";
_msg.setDateCell(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 230;BA.debugLine="msg.DateSeparateText=\"\"";
_msg.setDateSeparateText("");
 //BA.debugLineNum = 231;BA.debugLine="messages.Add(msg)";
mostCurrent._messages.Add((Object)(_msg.getObject()));
 //BA.debugLineNum = 232;BA.debugLine="chat.send(msg)";
mostCurrent._chat.send((jp.bassaer.chatmessageview.models.Message)(_msg.getObject()));
 //BA.debugLineNum = 237;BA.debugLine="chat.AutoScroll = True";
mostCurrent._chat.setAutoScroll(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 239;BA.debugLine="chat.DateSeparatorColor = Colors.Blue";
mostCurrent._chat.setDateSeparatorColor(anywheresoftware.b4a.keywords.Common.Colors.Blue);
 //BA.debugLineNum = 241;BA.debugLine="chat.SendTimeTextColor=Colors.Blue";
mostCurrent._chat.setSendTimeTextColor(anywheresoftware.b4a.keywords.Common.Colors.Blue);
 //BA.debugLineNum = 242;BA.debugLine="chat.UsernameTextColor=Colors.Blue";
mostCurrent._chat.setUsernameTextColor(anywheresoftware.b4a.keywords.Common.Colors.Blue);
 //BA.debugLineNum = 245;BA.debugLine="chat.InputTextHint = \"لطفا از ارسال پیام های رگبا";
mostCurrent._chat.setInputTextHint("لطفا از ارسال پیام های رگباری خودداری کنید");
 //BA.debugLineNum = 247;BA.debugLine="chat.SetBackgroundImage(LoadBitmap(File.DirAssets";
mostCurrent._chat.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"bg2.jpg").getObject()));
 //BA.debugLineNum = 248;BA.debugLine="chat.SendButtonColor=Colors.Blue";
mostCurrent._chat.setSendButtonColor(anywheresoftware.b4a.keywords.Common.Colors.Blue);
 //BA.debugLineNum = 250;BA.debugLine="chat.LeftBubbleColor = 0x96FFFFFF";
mostCurrent._chat.setLeftBubbleColor((int) (0x96ffffff));
 //BA.debugLineNum = 251;BA.debugLine="chat.RightBubbleColor = 0x96D510F7";
mostCurrent._chat.setRightBubbleColor((int) (0x96d510f7));
 //BA.debugLineNum = 252;BA.debugLine="chat.RightMessageTextColor = 0xFF04030D";
mostCurrent._chat.setRightMessageTextColor((int) (0xff04030d));
 //BA.debugLineNum = 253;BA.debugLine="chat.LeftMessageTextColor = 0xFF04030D";
mostCurrent._chat.setLeftMessageTextColor((int) (0xff04030d));
 //BA.debugLineNum = 256;BA.debugLine="End Sub";
return "";
}
public static String  _jsn_json(String _res,Object _tag) throws Exception{
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
 //BA.debugLineNum = 93;BA.debugLine="Sub jsn_json(res As String,tag As Object)";
 //BA.debugLineNum = 94;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 95;BA.debugLine="Select tag";
switch (BA.switchObjectToInt(_tag,(Object)("save_ticket"),(Object)("disconnect"),(Object)("eror"))) {
case 0: {
 //BA.debugLineNum = 98;BA.debugLine="Log(res)";
anywheresoftware.b4a.keywords.Common.Log(_res);
 //BA.debugLineNum = 99;BA.debugLine="ToastMessageShow(\"تیکت شما ثبت شد\",True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("تیکت شما ثبت شد"),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 100;BA.debugLine="chat.InputText=\"\"";
mostCurrent._chat.setInputText("");
 break; }
case 1: {
 //BA.debugLineNum = 105;BA.debugLine="Log(\"no internet\")";
anywheresoftware.b4a.keywords.Common.Log("no internet");
 break; }
case 2: {
 //BA.debugLineNum = 108;BA.debugLine="Log(\"eror\")";
anywheresoftware.b4a.keywords.Common.Log("eror");
 break; }
}
;
 //BA.debugLineNum = 111;BA.debugLine="progress_spot.DisMissDialog";
mostCurrent._progress_spot.DisMissDialog();
 //BA.debugLineNum = 112;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 114;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 8;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Public id_user As Int";
_id_user = 0;
 //BA.debugLineNum = 11;BA.debugLine="End Sub";
return "";
}
}
