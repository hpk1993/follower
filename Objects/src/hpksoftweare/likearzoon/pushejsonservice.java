package hpksoftweare.likearzoon;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.ServiceHelper;
import anywheresoftware.b4a.debug.*;

public class pushejsonservice extends  android.app.Service{
	public static class pushejsonservice_BR extends android.content.BroadcastReceiver {

		@Override
		public void onReceive(android.content.Context context, android.content.Intent intent) {
			android.content.Intent in = new android.content.Intent(context, pushejsonservice.class);
			if (intent != null)
				in.putExtra("b4a_internal_intent", intent);
			context.startService(in);
		}

	}
    static pushejsonservice mostCurrent;
	public static BA processBA;
    private ServiceHelper _service;
    public static Class<?> getObject() {
		return pushejsonservice.class;
	}
	@Override
	public void onCreate() {
        super.onCreate();
        mostCurrent = this;
        if (processBA == null) {
		    processBA = new BA(this, null, null, "hpksoftweare.likearzoon", "hpksoftweare.likearzoon.pushejsonservice");
            if (BA.isShellModeRuntimeCheck(processBA)) {
                processBA.raiseEvent2(null, true, "SHELL", false);
		    }
            try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            processBA.loadHtSubs(this.getClass());
            ServiceHelper.init();
        }
        _service = new ServiceHelper(this);
        processBA.service = this;
        
        if (BA.isShellModeRuntimeCheck(processBA)) {
			processBA.raiseEvent2(null, true, "CREATE", true, "hpksoftweare.likearzoon.pushejsonservice", processBA, _service, anywheresoftware.b4a.keywords.Common.Density);
		}
        if (!false && ServiceHelper.StarterHelper.startFromServiceCreate(processBA, false) == false) {
				
		}
		else {
            processBA.setActivityPaused(false);
            BA.LogInfo("** Service (pushejsonservice) Create **");
            processBA.raiseEvent(null, "service_create");
        }
        processBA.runHook("oncreate", this, null);
        if (false) {
			if (ServiceHelper.StarterHelper.waitForLayout != null)
				BA.handler.post(ServiceHelper.StarterHelper.waitForLayout);
		}
    }
		@Override
	public void onStart(android.content.Intent intent, int startId) {
		onStartCommand(intent, 0, 0);
    }
    @Override
    public int onStartCommand(final android.content.Intent intent, int flags, int startId) {
    	if (ServiceHelper.StarterHelper.onStartCommand(processBA))
			handleStart(intent);
		else {
			ServiceHelper.StarterHelper.waitForLayout = new Runnable() {
				public void run() {
                    processBA.setActivityPaused(false);
                    BA.LogInfo("** Service (pushejsonservice) Create **");
                    processBA.raiseEvent(null, "service_create");
					handleStart(intent);
				}
			};
		}
        processBA.runHook("onstartcommand", this, new Object[] {intent, flags, startId});
		return android.app.Service.START_NOT_STICKY;
    }
    public void onTaskRemoved(android.content.Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        if (false)
            processBA.raiseEvent(null, "service_taskremoved");
            
    }
    private void handleStart(android.content.Intent intent) {
    	BA.LogInfo("** Service (pushejsonservice) Start **");
    	java.lang.reflect.Method startEvent = processBA.htSubs.get("service_start");
    	if (startEvent != null) {
    		if (startEvent.getParameterTypes().length > 0) {
    			anywheresoftware.b4a.objects.IntentWrapper iw = new anywheresoftware.b4a.objects.IntentWrapper();
    			if (intent != null) {
    				if (intent.hasExtra("b4a_internal_intent"))
    					iw.setObject((android.content.Intent) intent.getParcelableExtra("b4a_internal_intent"));
    				else
    					iw.setObject(intent);
    			}
    			processBA.raiseEvent(null, "service_start", iw);
    		}
    		else {
    			processBA.raiseEvent(null, "service_start");
    		}
    	}
    }
	
	@Override
	public void onDestroy() {
        super.onDestroy();
        BA.LogInfo("** Service (pushejsonservice) Destroy **");
		processBA.raiseEvent(null, "service_destroy");
        processBA.service = null;
		mostCurrent = null;
		processBA.setActivityPaused(true);
        processBA.runHook("ondestroy", this, null);
	}

@Override
	public android.os.IBinder onBind(android.content.Intent intent) {
		return null;
	}public anywheresoftware.b4a.keywords.Common __c = null;
public static co.ronash.pushe.wrapper.PusheWrapper.PusheB4AUtil _pusheutil = null;
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
public hpksoftweare.likearzoon.order _order = null;
public hpksoftweare.likearzoon.charj _charj = null;
public hpksoftweare.likearzoon.login _login = null;
public hpksoftweare.likearzoon.about _about = null;
public hpksoftweare.likearzoon.history _history = null;
public static String  _messagearrived(anywheresoftware.b4a.objects.IntentWrapper _pintent) throws Exception{
String _jsonmsg = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.Map _root = null;
String _action = "";
barxdroid.NotificationBuilder.NotificationBuilder _nb = null;
int _user_id = 0;
int _admin_user = 0;
String _text = "";
String _date = "";
String _name = "";
Object[] _arg0 = null;
 //BA.debugLineNum = 23;BA.debugLine="Sub MessageArrived (pIntent As Intent)";
 //BA.debugLineNum = 25;BA.debugLine="Dim JsonMsg As String";
_jsonmsg = "";
 //BA.debugLineNum = 26;BA.debugLine="JsonMsg = PusheUtil.getPusheJsonMsg(pIntent)";
_jsonmsg = _pusheutil.getPusheJsonMsg((android.content.Intent)(_pintent.getObject()));
 //BA.debugLineNum = 27;BA.debugLine="LogColor(JsonMsg,Colors.Red)";
anywheresoftware.b4a.keywords.Common.LogColor(_jsonmsg,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 28;BA.debugLine="If JsonMsg <> \"\" Then";
if ((_jsonmsg).equals("") == false) { 
 //BA.debugLineNum = 29;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 30;BA.debugLine="parser.Initialize(JsonMsg)";
_parser.Initialize(_jsonmsg);
 //BA.debugLineNum = 31;BA.debugLine="Dim root As Map = parser.NextObject";
_root = new anywheresoftware.b4a.objects.collections.Map();
_root = _parser.NextObject();
 //BA.debugLineNum = 32;BA.debugLine="Dim action As String=root.Get(\"action\")";
_action = BA.ObjectToString(_root.Get((Object)("action")));
 //BA.debugLineNum = 34;BA.debugLine="If action=\"Locked\" Then";
if ((_action).equals("Locked")) { 
 //BA.debugLineNum = 35;BA.debugLine="File.WriteString(Starter.rute,\"L4235\",\"true\")";
anywheresoftware.b4a.keywords.Common.File.WriteString(mostCurrent._starter._rute,"L4235","true");
 //BA.debugLineNum = 36;BA.debugLine="Dim nb As NotificationBuilder";
_nb = new barxdroid.NotificationBuilder.NotificationBuilder();
 //BA.debugLineNum = 37;BA.debugLine="nb.Initialize";
_nb.Initialize(processBA);
 //BA.debugLineNum = 38;BA.debugLine="nb.DefaultVibrate=True";
_nb.setDefaultVibrate(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 39;BA.debugLine="nb.DefaultSound=True";
_nb.setDefaultSound(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 40;BA.debugLine="nb.Autocancel=True";
_nb.setAutoCancel(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 41;BA.debugLine="nb.smallIcon=\"ico\"";
_nb.setSmallIcon("ico");
 //BA.debugLineNum = 42;BA.debugLine="nb.Ticker=\"پیام جدید \"";
_nb.setTicker("پیام جدید ");
 //BA.debugLineNum = 43;BA.debugLine="nb.ContentTitle=\"برنامه شما بدلیل برخی از مشکلا";
_nb.setContentTitle("برنامه شما بدلیل برخی از مشکلات مسدود شد");
 //BA.debugLineNum = 44;BA.debugLine="nb.contentText=\"\"";
_nb.setContentText("");
 //BA.debugLineNum = 45;BA.debugLine="nb.contentInfo=\"( \" & Application.LabelName & \"";
_nb.setContentInfo("( "+anywheresoftware.b4a.keywords.Common.Application.getLabelName()+" )");
 //BA.debugLineNum = 46;BA.debugLine="nb.Tag=\"\"";
_nb.setTag("");
 //BA.debugLineNum = 48;BA.debugLine="nb.setActivity(Main)";
_nb.setActivity(processBA,(Object)(mostCurrent._main.getObject()));
 //BA.debugLineNum = 49;BA.debugLine="nb.Notify(	1 )";
_nb.Notify(processBA,(int) (1));
 };
 //BA.debugLineNum = 52;BA.debugLine="LogColor(\"Push: \" & root.Get(\"text\")	,Colors.Blu";
anywheresoftware.b4a.keywords.Common.LogColor("Push: "+BA.ObjectToString(_root.Get((Object)("text"))),anywheresoftware.b4a.keywords.Common.Colors.Blue);
 //BA.debugLineNum = 53;BA.debugLine="LogColor(\"Push: \" & root.Get(\"name\")	,Colors.Blu";
anywheresoftware.b4a.keywords.Common.LogColor("Push: "+BA.ObjectToString(_root.Get((Object)("name"))),anywheresoftware.b4a.keywords.Common.Colors.Blue);
 //BA.debugLineNum = 54;BA.debugLine="LogColor(\"Push: \" & root.Get(\"date\")	,Colors.Blu";
anywheresoftware.b4a.keywords.Common.LogColor("Push: "+BA.ObjectToString(_root.Get((Object)("date"))),anywheresoftware.b4a.keywords.Common.Colors.Blue);
 //BA.debugLineNum = 55;BA.debugLine="LogColor(\"Push: \" & root.Get(\"uid\")	,Colors.Blue";
anywheresoftware.b4a.keywords.Common.LogColor("Push: "+BA.ObjectToString(_root.Get((Object)("uid"))),anywheresoftware.b4a.keywords.Common.Colors.Blue);
 //BA.debugLineNum = 56;BA.debugLine="LogColor(\"Push: \" & root.Get(\"admin_user\")	,Colo";
anywheresoftware.b4a.keywords.Common.LogColor("Push: "+BA.ObjectToString(_root.Get((Object)("admin_user"))),anywheresoftware.b4a.keywords.Common.Colors.Blue);
 //BA.debugLineNum = 58;BA.debugLine="Dim user_id As Int=root.Get(\"uid\")";
_user_id = (int)(BA.ObjectToNumber(_root.Get((Object)("uid"))));
 //BA.debugLineNum = 59;BA.debugLine="Dim admin_user As Int=root.Get(\"admin_user\")";
_admin_user = (int)(BA.ObjectToNumber(_root.Get((Object)("admin_user"))));
 //BA.debugLineNum = 60;BA.debugLine="Dim text As String=root.Get(\"text\")";
_text = BA.ObjectToString(_root.Get((Object)("text")));
 //BA.debugLineNum = 61;BA.debugLine="Dim date As String=root.Get(\"date\")";
_date = BA.ObjectToString(_root.Get((Object)("date")));
 //BA.debugLineNum = 62;BA.debugLine="Dim name As String=root.Get(\"name\")";
_name = BA.ObjectToString(_root.Get((Object)("name")));
 //BA.debugLineNum = 63;BA.debugLine="Dim arg0() As Object=Array As Object(name,text,d";
_arg0 = new Object[]{(Object)(_name),(Object)(_text),(Object)(_date),(Object)(_admin_user)};
 //BA.debugLineNum = 66;BA.debugLine="If user_id=Main.my_ID  Then 'myself  user";
if (_user_id==mostCurrent._main._my_id) { 
 //BA.debugLineNum = 67;BA.debugLine="Log(\"****user***\")";
anywheresoftware.b4a.keywords.Common.Log("****user***");
 //BA.debugLineNum = 68;BA.debugLine="If IsPaused(ticket)=False Then";
if (anywheresoftware.b4a.keywords.Common.IsPaused(processBA,(Object)(mostCurrent._ticket.getObject()))==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 69;BA.debugLine="CallSub2(ticket,\"callmsg\",arg0)";
anywheresoftware.b4a.keywords.Common.CallSubNew2(processBA,(Object)(mostCurrent._ticket.getObject()),"callmsg",(Object)(_arg0));
 }else {
 //BA.debugLineNum = 71;BA.debugLine="Dim nb As NotificationBuilder";
_nb = new barxdroid.NotificationBuilder.NotificationBuilder();
 //BA.debugLineNum = 72;BA.debugLine="nb.Initialize";
_nb.Initialize(processBA);
 //BA.debugLineNum = 73;BA.debugLine="nb.DefaultVibrate=True";
_nb.setDefaultVibrate(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 74;BA.debugLine="nb.DefaultSound=True";
_nb.setDefaultSound(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 75;BA.debugLine="nb.Autocancel=True";
_nb.setAutoCancel(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 76;BA.debugLine="nb.smallIcon=\"admin\"";
_nb.setSmallIcon("admin");
 //BA.debugLineNum = 77;BA.debugLine="nb.Ticker=\"پاسخ تیکت \"";
_nb.setTicker("پاسخ تیکت ");
 //BA.debugLineNum = 78;BA.debugLine="nb.ContentTitle=\"به تیکت شما پاسخ داده شد\"";
_nb.setContentTitle("به تیکت شما پاسخ داده شد");
 //BA.debugLineNum = 79;BA.debugLine="nb.contentText=\"\"";
_nb.setContentText("");
 //BA.debugLineNum = 80;BA.debugLine="nb.contentInfo=\"( \" & Application.LabelName &";
_nb.setContentInfo("( "+anywheresoftware.b4a.keywords.Common.Application.getLabelName()+" )");
 //BA.debugLineNum = 81;BA.debugLine="nb.Tag=user_id";
_nb.setTag(BA.NumberToString(_user_id));
 //BA.debugLineNum = 83;BA.debugLine="nb.setActivity(ticket)";
_nb.setActivity(processBA,(Object)(mostCurrent._ticket.getObject()));
 //BA.debugLineNum = 84;BA.debugLine="nb.Notify(	1 )";
_nb.Notify(processBA,(int) (1));
 };
 };
 //BA.debugLineNum = 89;BA.debugLine="If Main.admin_bool=1 Then 'myself  admin";
if (mostCurrent._main._admin_bool==1) { 
 //BA.debugLineNum = 90;BA.debugLine="Log(\"****admin***\")";
anywheresoftware.b4a.keywords.Common.Log("****admin***");
 //BA.debugLineNum = 91;BA.debugLine="If IsPaused(ticket)=False Then";
if (anywheresoftware.b4a.keywords.Common.IsPaused(processBA,(Object)(mostCurrent._ticket.getObject()))==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 92;BA.debugLine="CallSub2(ticket,\"callmsg\",arg0)";
anywheresoftware.b4a.keywords.Common.CallSubNew2(processBA,(Object)(mostCurrent._ticket.getObject()),"callmsg",(Object)(_arg0));
 }else {
 //BA.debugLineNum = 94;BA.debugLine="Dim nb As NotificationBuilder";
_nb = new barxdroid.NotificationBuilder.NotificationBuilder();
 //BA.debugLineNum = 95;BA.debugLine="nb.Initialize";
_nb.Initialize(processBA);
 //BA.debugLineNum = 96;BA.debugLine="nb.DefaultVibrate=True";
_nb.setDefaultVibrate(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 97;BA.debugLine="nb.DefaultSound=True";
_nb.setDefaultSound(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 98;BA.debugLine="nb.Autocancel=True";
_nb.setAutoCancel(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 99;BA.debugLine="nb.smallIcon=\"admin\"";
_nb.setSmallIcon("admin");
 //BA.debugLineNum = 100;BA.debugLine="nb.Ticker=\"تیکت جدید \"";
_nb.setTicker("تیکت جدید ");
 //BA.debugLineNum = 101;BA.debugLine="nb.ContentTitle=\"یک تیکت به ثبت رسید\"";
_nb.setContentTitle("یک تیکت به ثبت رسید");
 //BA.debugLineNum = 102;BA.debugLine="nb.contentText=\"\"";
_nb.setContentText("");
 //BA.debugLineNum = 103;BA.debugLine="nb.contentInfo=\"( \" & Application.LabelName &";
_nb.setContentInfo("( "+anywheresoftware.b4a.keywords.Common.Application.getLabelName()+" )");
 //BA.debugLineNum = 104;BA.debugLine="nb.Tag=user_id";
_nb.setTag(BA.NumberToString(_user_id));
 //BA.debugLineNum = 106;BA.debugLine="nb.setActivity(ticket)";
_nb.setActivity(processBA,(Object)(mostCurrent._ticket.getObject()));
 //BA.debugLineNum = 107;BA.debugLine="nb.Notify(	1 )";
_nb.Notify(processBA,(int) (1));
 };
 };
 };
 //BA.debugLineNum = 114;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 8;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim PusheUtil As PusheB4AUtil";
_pusheutil = new co.ronash.pushe.wrapper.PusheWrapper.PusheB4AUtil();
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
public static String  _service_create() throws Exception{
 //BA.debugLineNum = 11;BA.debugLine="Sub Service_Create";
 //BA.debugLineNum = 13;BA.debugLine="End Sub";
return "";
}
public static String  _service_destroy() throws Exception{
 //BA.debugLineNum = 117;BA.debugLine="Sub Service_Destroy";
 //BA.debugLineNum = 119;BA.debugLine="End Sub";
return "";
}
public static String  _service_start(anywheresoftware.b4a.objects.IntentWrapper _startingintent) throws Exception{
 //BA.debugLineNum = 15;BA.debugLine="Sub Service_Start (StartingIntent As Intent)";
 //BA.debugLineNum = 16;BA.debugLine="Select StartingIntent.Action";
switch (BA.switchObjectToInt(_startingintent.getAction(),"com.google.android.c2dm.intent.RECEIVE")) {
case 0: {
 //BA.debugLineNum = 18;BA.debugLine="MessageArrived(StartingIntent)";
_messagearrived(_startingintent);
 break; }
}
;
 //BA.debugLineNum = 20;BA.debugLine="End Sub";
return "";
}
}
