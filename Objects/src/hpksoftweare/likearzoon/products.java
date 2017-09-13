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

public class products extends Activity implements B4AActivity{
	public static products mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "hpksoftweare.likearzoon", "hpksoftweare.likearzoon.products");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (products).");
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
		activityBA = new BA(this, layout, processBA, "hpksoftweare.likearzoon", "hpksoftweare.likearzoon.products");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "hpksoftweare.likearzoon.products", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (products) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (products) Resume **");
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
		return products.class;
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
        BA.LogInfo("** Activity (products) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (products) Resume **");
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
public static int _selected_index = 0;
public anywheresoftware.b4a.objects.collections.List _list_product = null;
public anywheresoftware.b4a.objects.PanelWrapper _p_group = null;
public de.amberhome.objects.FloatlabelEditTextWrapper _dsfloatlabeledittext8 = null;
public de.amberhome.objects.FloatlabelEditTextWrapper _dsfloatlabeledittext1 = null;
public de.amberhome.objects.FloatlabelEditTextWrapper _dsfloatlabeledittext2 = null;
public de.amberhome.objects.FloatlabelEditTextWrapper _dsfloatlabeledittext3 = null;
public de.amberhome.objects.FloatlabelEditTextWrapper _dsfloatlabeledittext4 = null;
public de.amberhome.objects.FloatlabelEditTextWrapper _dsfloatlabeledittext5 = null;
public de.amberhome.objects.FloatlabelEditTextWrapper _dsfloatlabeledittext6 = null;
public de.amberhome.objects.FloatlabelEditTextWrapper _dsfloatlabeledittext7 = null;
public de.amberhome.objects.appcompat.ACSwitchCompatWrapper _acswitch1 = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper _scrol = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spinner1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_reg = null;
public static int _height_creen = 0;
public b4a.example.frgfg.connector _connector = null;
public b4a.example.frgfg.db_mysql _db_mysql = null;
public b4a.example.get_json _get_json = null;
public b4a.example.json_connector _json_connector = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public hpksoftweare.likearzoon.main _main = null;
public hpksoftweare.likearzoon.starter _starter = null;
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

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _acswitch1_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 53;BA.debugLine="Sub ACSwitch1_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 55;BA.debugLine="End Sub";
return "";
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 33;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 34;BA.debugLine="height_creen=100%y";
_height_creen = anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA);
 //BA.debugLineNum = 35;BA.debugLine="scrol.Initialize(height_creen)";
mostCurrent._scrol.Initialize(mostCurrent.activityBA,_height_creen);
 //BA.debugLineNum = 36;BA.debugLine="Activity.AddView(scrol,0,0,100%x,100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._scrol.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 37;BA.debugLine="scrol.Panel.LoadLayout(\"edit_product\")";
mostCurrent._scrol.getPanel().LoadLayout("edit_product",mostCurrent.activityBA);
 //BA.debugLineNum = 38;BA.debugLine="p_group.Visible=False";
mostCurrent._p_group.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 39;BA.debugLine="scrol.Panel.Height=p_group.Top + p_group.Height +";
mostCurrent._scrol.getPanel().setHeight((int) (mostCurrent._p_group.getTop()+mostCurrent._p_group.getHeight()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 40;BA.debugLine="progress_spot.Initialize2(\"در حال بارگذاری اطلاعا";
mostCurrent._progress_spot.Initialize2(mostCurrent.activityBA,BA.ObjectToCharSequence("در حال بارگذاری اطلاعات لطفا شکیبا باشید.."),mostCurrent._progress_spot.Theme_Custom,anywheresoftware.b4a.keywords.Common.Colors.White,(float) (12),anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("iransansmobile(fanum).ttf"),(int) (6),anywheresoftware.b4a.keywords.Common.Colors.Yellow,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 43;BA.debugLine="connector.Initialize(Me,\"db\",Starter.Server_mysql";
mostCurrent._connector._initialize(mostCurrent.activityBA,products.getObject(),"db",mostCurrent._starter._server_mysql,mostCurrent._starter._db_name,mostCurrent._starter._db_user,mostCurrent._starter._db_pass);
 //BA.debugLineNum = 44;BA.debugLine="progress_spot.ShowDialog";
mostCurrent._progress_spot.ShowDialog();
 //BA.debugLineNum = 45;BA.debugLine="connector.send_query($\"SELECT * FROM `product`\"$,";
mostCurrent._connector._send_query(mostCurrent.activityBA,("SELECT * FROM `product`"),"get_service_db",(Object)(""));
 //BA.debugLineNum = 47;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 57;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 59;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 49;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 51;BA.debugLine="End Sub";
return "";
}
public static String  _btn_reg_click() throws Exception{
hpksoftweare.likearzoon.main._struct_req _struct1 = null;
 //BA.debugLineNum = 139;BA.debugLine="Sub btn_reg_Click";
 //BA.debugLineNum = 140;BA.debugLine="If DialogResponse.POSITIVE=Msgbox2(\"ایا مطمعن یه";
if (anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE==anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("ایا مطمعن یه ویرایش محصول هستید؟"),BA.ObjectToCharSequence(""),"بله","خیر","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA)) { 
 //BA.debugLineNum = 141;BA.debugLine="Log(\"selected_index: \" & selected_index)";
anywheresoftware.b4a.keywords.Common.Log("selected_index: "+BA.NumberToString(_selected_index));
 //BA.debugLineNum = 142;BA.debugLine="If selected_index > 0 Then";
if (_selected_index>0) { 
 //BA.debugLineNum = 143;BA.debugLine="Dim struct1 As struct_req";
_struct1 = new hpksoftweare.likearzoon.main._struct_req();
 //BA.debugLineNum = 144;BA.debugLine="struct1. title=DSFloatLabelEditText8.Text.Trim";
_struct1.title = mostCurrent._dsfloatlabeledittext8.getText().trim();
 //BA.debugLineNum = 145;BA.debugLine="struct1. id=DSFloatLabelEditText1.Text.Trim";
_struct1.id = (int)(Double.parseDouble(mostCurrent._dsfloatlabeledittext1.getText().trim()));
 //BA.debugLineNum = 146;BA.debugLine="struct1. quantity=DSFloatLabelEditText2.Text.Tr";
_struct1.quantity = (int)(Double.parseDouble(mostCurrent._dsfloatlabeledittext2.getText().trim()));
 //BA.debugLineNum = 147;BA.debugLine="struct1. price_unit=DSFloatLabelEditText3.Text.";
_struct1.price_unit = (int)(Double.parseDouble(mostCurrent._dsfloatlabeledittext3.getText().trim()));
 //BA.debugLineNum = 148;BA.debugLine="struct1. price=	DSFloatLabelEditText4.Text.Trim";
_struct1.price = (int)(Double.parseDouble(mostCurrent._dsfloatlabeledittext4.getText().trim()));
 //BA.debugLineNum = 149;BA.debugLine="struct1. max_req=DSFloatLabelEditText5.Text.Tri";
_struct1.max_req = (int)(Double.parseDouble(mostCurrent._dsfloatlabeledittext5.getText().trim()));
 //BA.debugLineNum = 150;BA.debugLine="struct1. min_req=DSFloatLabelEditText6.Text.Tri";
_struct1.min_req = (int)(Double.parseDouble(mostCurrent._dsfloatlabeledittext6.getText().trim()));
 //BA.debugLineNum = 151;BA.debugLine="struct1. alias=DSFloatLabelEditText7.Text.Trim";
_struct1.alias = mostCurrent._dsfloatlabeledittext7.getText().trim();
 //BA.debugLineNum = 152;BA.debugLine="struct1.description=\"\"";
_struct1.description = "";
 //BA.debugLineNum = 153;BA.debugLine="If ACSwitch1.Checked=True Then";
if (mostCurrent._acswitch1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 154;BA.debugLine="struct1.show=1";
_struct1.show = (int) (1);
 }else {
 //BA.debugLineNum = 156;BA.debugLine="struct1.show=0";
_struct1.show = (int) (0);
 };
 //BA.debugLineNum = 158;BA.debugLine="progress_spot.ShowDialog";
mostCurrent._progress_spot.ShowDialog();
 //BA.debugLineNum = 159;BA.debugLine="connector.send_query($\"Update `product` SET `pr";
mostCurrent._connector._send_query(mostCurrent.activityBA,("Update `product` SET `price_unit`="+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_struct1.price_unit))+",`quantity`="+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_struct1.quantity))+",`price`="+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_struct1.price))+",`max_req`="+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_struct1.max_req))+",`min_req`="+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_struct1.min_req))+",`alias`=N'"+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_struct1.alias))+"',`description`=N'"+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_struct1.description))+"',`title`=N'"+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_struct1.title))+"',`show`="+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_struct1.show))+" WHERE `id_service`="+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_struct1.id))+""),"update_service_db",(Object)(""));
 };
 };
 //BA.debugLineNum = 164;BA.debugLine="End Sub";
return "";
}
public static String  _db_connector(anywheresoftware.b4a.objects.collections.List _records,Object _tag) throws Exception{
anywheresoftware.b4a.objects.collections.Map _colservices = null;
hpksoftweare.likearzoon.main._struct_req _struct1 = null;
 //BA.debugLineNum = 65;BA.debugLine="Sub db_connector(records As List,tag As Object)";
 //BA.debugLineNum = 66;BA.debugLine="Select tag";
switch (BA.switchObjectToInt(_tag,(Object)("get_service_db"),(Object)("update_service_db"),(Object)("disconnect"),(Object)("eror"))) {
case 0: {
 //BA.debugLineNum = 68;BA.debugLine="Spinner1.Clear";
mostCurrent._spinner1.Clear();
 //BA.debugLineNum = 69;BA.debugLine="list_product.Initialize";
mostCurrent._list_product.Initialize();
 //BA.debugLineNum = 70;BA.debugLine="Spinner1.Add(\"یک محصول انتخاب کنید\")";
mostCurrent._spinner1.Add("یک محصول انتخاب کنید");
 //BA.debugLineNum = 71;BA.debugLine="For Each colservices As Map In records";
_colservices = new anywheresoftware.b4a.objects.collections.Map();
final anywheresoftware.b4a.BA.IterableList group6 = _records;
final int groupLen6 = group6.getSize();
for (int index6 = 0;index6 < groupLen6 ;index6++){
_colservices.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(group6.Get(index6)));
 //BA.debugLineNum = 72;BA.debugLine="Dim struct1 As struct_req";
_struct1 = new hpksoftweare.likearzoon.main._struct_req();
 //BA.debugLineNum = 73;BA.debugLine="struct1.Initialize";
_struct1.Initialize();
 //BA.debugLineNum = 75;BA.debugLine="struct1.price_unit = colservices.Get(\"price_un";
_struct1.price_unit = (int)(BA.ObjectToNumber(_colservices.Get((Object)("price_unit"))));
 //BA.debugLineNum = 76;BA.debugLine="struct1. quantity= colservices.Get(\"quantity\")";
_struct1.quantity = (int)(BA.ObjectToNumber(_colservices.Get((Object)("quantity"))));
 //BA.debugLineNum = 77;BA.debugLine="struct1. price = colservices.Get(\"price\")";
_struct1.price = (int)(BA.ObjectToNumber(_colservices.Get((Object)("price"))));
 //BA.debugLineNum = 78;BA.debugLine="struct1. max_req = colservices.Get(\"max_req\")";
_struct1.max_req = (int)(BA.ObjectToNumber(_colservices.Get((Object)("max_req"))));
 //BA.debugLineNum = 79;BA.debugLine="struct1. alias  = colservices.Get(\"alias\")";
_struct1.alias = BA.ObjectToString(_colservices.Get((Object)("alias")));
 //BA.debugLineNum = 80;BA.debugLine="struct1. description  = colservices.Get(\"descr";
_struct1.description = BA.ObjectToString(_colservices.Get((Object)("description")));
 //BA.debugLineNum = 81;BA.debugLine="struct1. id  = colservices.Get(\"id_service\")	'";
_struct1.id = (int)(BA.ObjectToNumber(_colservices.Get((Object)("id_service"))));
 //BA.debugLineNum = 83;BA.debugLine="struct1. title  = colservices.Get(\"title\")";
_struct1.title = BA.ObjectToString(_colservices.Get((Object)("title")));
 //BA.debugLineNum = 84;BA.debugLine="struct1. min_req = colservices.Get(\"min_req\")";
_struct1.min_req = (int)(BA.ObjectToNumber(_colservices.Get((Object)("min_req"))));
 //BA.debugLineNum = 85;BA.debugLine="struct1. show = colservices.Get(\"show\")";
_struct1.show = (int)(BA.ObjectToNumber(_colservices.Get((Object)("show"))));
 //BA.debugLineNum = 86;BA.debugLine="list_product.Add(struct1)";
mostCurrent._list_product.Add((Object)(_struct1));
 //BA.debugLineNum = 87;BA.debugLine="Spinner1.Add(struct1. title)";
mostCurrent._spinner1.Add(_struct1.title);
 }
;
 break; }
case 1: {
 //BA.debugLineNum = 93;BA.debugLine="Log(records)";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(_records));
 //BA.debugLineNum = 94;BA.debugLine="p_group.Visible=False";
mostCurrent._p_group.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 95;BA.debugLine="ToastMessageShow(\"محصول شما ویرایش شد\",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("محصول شما ویرایش شد"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 96;BA.debugLine="Spinner1.SelectedIndex=0";
mostCurrent._spinner1.setSelectedIndex((int) (0));
 //BA.debugLineNum = 97;BA.debugLine="scrol.ScrollPosition=0";
mostCurrent._scrol.setScrollPosition((int) (0));
 //BA.debugLineNum = 98;BA.debugLine="progress_spot.ShowDialog";
mostCurrent._progress_spot.ShowDialog();
 //BA.debugLineNum = 99;BA.debugLine="connector.send_query($\"SELECT * FROM `product`\"";
mostCurrent._connector._send_query(mostCurrent.activityBA,("SELECT * FROM `product`"),"get_service_db",(Object)(""));
 break; }
case 2: {
 //BA.debugLineNum = 102;BA.debugLine="Log(\"no internet\")";
anywheresoftware.b4a.keywords.Common.Log("no internet");
 break; }
case 3: {
 //BA.debugLineNum = 105;BA.debugLine="Log(\"eror\")";
anywheresoftware.b4a.keywords.Common.Log("eror");
 break; }
}
;
 //BA.debugLineNum = 108;BA.debugLine="progress_spot.DisMissDialog";
mostCurrent._progress_spot.DisMissDialog();
 //BA.debugLineNum = 109;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 110;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 13;BA.debugLine="Private progress_spot As SpotsDialog";
mostCurrent._progress_spot = new dmax.dialog.Spotsd();
 //BA.debugLineNum = 14;BA.debugLine="Dim selected_index As Int";
_selected_index = 0;
 //BA.debugLineNum = 15;BA.debugLine="Dim list_product As List";
mostCurrent._list_product = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 16;BA.debugLine="Private p_group As Panel";
mostCurrent._p_group = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private DSFloatLabelEditText8 As DSFloatLabelEdit";
mostCurrent._dsfloatlabeledittext8 = new de.amberhome.objects.FloatlabelEditTextWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private DSFloatLabelEditText1 As DSFloatLabelEdit";
mostCurrent._dsfloatlabeledittext1 = new de.amberhome.objects.FloatlabelEditTextWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private DSFloatLabelEditText2 As DSFloatLabelEdit";
mostCurrent._dsfloatlabeledittext2 = new de.amberhome.objects.FloatlabelEditTextWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private DSFloatLabelEditText3 As DSFloatLabelEdit";
mostCurrent._dsfloatlabeledittext3 = new de.amberhome.objects.FloatlabelEditTextWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private DSFloatLabelEditText4 As DSFloatLabelEdit";
mostCurrent._dsfloatlabeledittext4 = new de.amberhome.objects.FloatlabelEditTextWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private DSFloatLabelEditText5 As DSFloatLabelEdit";
mostCurrent._dsfloatlabeledittext5 = new de.amberhome.objects.FloatlabelEditTextWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private DSFloatLabelEditText6 As DSFloatLabelEdit";
mostCurrent._dsfloatlabeledittext6 = new de.amberhome.objects.FloatlabelEditTextWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private DSFloatLabelEditText7 As DSFloatLabelEdit";
mostCurrent._dsfloatlabeledittext7 = new de.amberhome.objects.FloatlabelEditTextWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private ACSwitch1 As ACSwitch";
mostCurrent._acswitch1 = new de.amberhome.objects.appcompat.ACSwitchCompatWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Dim scrol As ScrollView";
mostCurrent._scrol = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private Spinner1 As Spinner";
mostCurrent._spinner1 = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private btn_reg As Button";
mostCurrent._btn_reg = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Dim height_creen As Int";
_height_creen = 0;
 //BA.debugLineNum = 31;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
public static String  _spinner1_itemclick(int _position,Object _value) throws Exception{
hpksoftweare.likearzoon.main._struct_req _struct1 = null;
 //BA.debugLineNum = 114;BA.debugLine="Sub Spinner1_ItemClick (Position As Int, Value As";
 //BA.debugLineNum = 115;BA.debugLine="If Position > 0 Then";
if (_position>0) { 
 //BA.debugLineNum = 116;BA.debugLine="selected_index=Position";
_selected_index = _position;
 //BA.debugLineNum = 117;BA.debugLine="p_group.Visible=True";
mostCurrent._p_group.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 118;BA.debugLine="Dim struct1 As struct_req=list_product.Get(selec";
_struct1 = (hpksoftweare.likearzoon.main._struct_req)(mostCurrent._list_product.Get((int) (_selected_index-1)));
 //BA.debugLineNum = 119;BA.debugLine="DSFloatLabelEditText8.Text=struct1. title";
mostCurrent._dsfloatlabeledittext8.setText(BA.ObjectToCharSequence(_struct1.title));
 //BA.debugLineNum = 120;BA.debugLine="DSFloatLabelEditText1.Text=struct1. id";
mostCurrent._dsfloatlabeledittext1.setText(BA.ObjectToCharSequence(_struct1.id));
 //BA.debugLineNum = 121;BA.debugLine="DSFloatLabelEditText2.Text=struct1. quantity";
mostCurrent._dsfloatlabeledittext2.setText(BA.ObjectToCharSequence(_struct1.quantity));
 //BA.debugLineNum = 122;BA.debugLine="DSFloatLabelEditText3.Text=struct1. price_unit";
mostCurrent._dsfloatlabeledittext3.setText(BA.ObjectToCharSequence(_struct1.price_unit));
 //BA.debugLineNum = 123;BA.debugLine="DSFloatLabelEditText4.Text=struct1. price";
mostCurrent._dsfloatlabeledittext4.setText(BA.ObjectToCharSequence(_struct1.price));
 //BA.debugLineNum = 124;BA.debugLine="DSFloatLabelEditText5.Text=struct1. max_req";
mostCurrent._dsfloatlabeledittext5.setText(BA.ObjectToCharSequence(_struct1.max_req));
 //BA.debugLineNum = 125;BA.debugLine="DSFloatLabelEditText6.Text=struct1. min_req";
mostCurrent._dsfloatlabeledittext6.setText(BA.ObjectToCharSequence(_struct1.min_req));
 //BA.debugLineNum = 126;BA.debugLine="DSFloatLabelEditText7.Text=struct1. alias";
mostCurrent._dsfloatlabeledittext7.setText(BA.ObjectToCharSequence(_struct1.alias));
 //BA.debugLineNum = 127;BA.debugLine="If struct1.show=0 Then";
if (_struct1.show==0) { 
 //BA.debugLineNum = 128;BA.debugLine="ACSwitch1.Checked=False";
mostCurrent._acswitch1.setChecked(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 130;BA.debugLine="ACSwitch1.Checked=True";
mostCurrent._acswitch1.setChecked(anywheresoftware.b4a.keywords.Common.True);
 };
 }else {
 //BA.debugLineNum = 133;BA.debugLine="selected_index=0";
_selected_index = (int) (0);
 //BA.debugLineNum = 134;BA.debugLine="p_group.Visible=False";
mostCurrent._p_group.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 135;BA.debugLine="scrol.Panel.Height=scrol.Height";
mostCurrent._scrol.getPanel().setHeight(mostCurrent._scrol.getHeight());
 };
 //BA.debugLineNum = 137;BA.debugLine="End Sub";
return "";
}
}
