package hpksoftweare.likearzoon;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.ServiceHelper;
import anywheresoftware.b4a.debug.*;

public class starter extends  android.app.Service{
	public static class starter_BR extends android.content.BroadcastReceiver {

		@Override
		public void onReceive(android.content.Context context, android.content.Intent intent) {
			android.content.Intent in = new android.content.Intent(context, starter.class);
			if (intent != null)
				in.putExtra("b4a_internal_intent", intent);
			context.startService(in);
		}

	}
    static starter mostCurrent;
	public static BA processBA;
    private ServiceHelper _service;
    public static Class<?> getObject() {
		return starter.class;
	}
	@Override
	public void onCreate() {
        super.onCreate();
        mostCurrent = this;
        if (processBA == null) {
		    processBA = new BA(this, null, null, "hpksoftweare.likearzoon", "hpksoftweare.likearzoon.starter");
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
			processBA.raiseEvent2(null, true, "CREATE", true, "hpksoftweare.likearzoon.starter", processBA, _service, anywheresoftware.b4a.keywords.Common.Density);
		}
        if (!true && ServiceHelper.StarterHelper.startFromServiceCreate(processBA, false) == false) {
				
		}
		else {
            processBA.setActivityPaused(false);
            BA.LogInfo("** Service (starter) Create **");
            processBA.raiseEvent(null, "service_create");
        }
        processBA.runHook("oncreate", this, null);
        if (true) {
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
                    BA.LogInfo("** Service (starter) Create **");
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
        if (true)
            processBA.raiseEvent(null, "service_taskremoved");
            
    }
    private void handleStart(android.content.Intent intent) {
    	BA.LogInfo("** Service (starter) Start **");
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
        BA.LogInfo("** Service (starter) Destroy **");
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
public static String _file_user = "";
public static String _rute = "";
public static String _server_json = "";
public static String _server_mysql = "";
public static String _server_push = "";
public static int[] _color_base = null;
public static String _rsacode_bazaar = "";
public static String _db_name = "";
public static String _db_pass = "";
public static String _db_user = "";
public b4a.example.frgfg.connector _connector = null;
public b4a.example.frgfg.db_mysql _db_mysql = null;
public b4a.example.get_json _get_json = null;
public b4a.example.json_connector _json_connector = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public hpksoftweare.likearzoon.main _main = null;
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
public static boolean  _application_error(anywheresoftware.b4a.objects.B4AException _error,String _stacktrace) throws Exception{
 //BA.debugLineNum = 39;BA.debugLine="Sub Application_Error (Error As Exception, StackTr";
 //BA.debugLineNum = 40;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 41;BA.debugLine="End Sub";
return false;
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 7;BA.debugLine="Public file_user As String=\"77545345\"";
_file_user = "77545345";
 //BA.debugLineNum = 8;BA.debugLine="Public rute As String=File.DirDefaultExternal";
_rute = anywheresoftware.b4a.keywords.Common.File.getDirDefaultExternal();
 //BA.debugLineNum = 9;BA.debugLine="Public Server_json As String=\"http://shahabemadi.";
_server_json = "http://shahabemadi.com/server_android/index.php";
 //BA.debugLineNum = 10;BA.debugLine="Public Server_mysql As String=\"http://shahabemadi";
_server_mysql = "http://shahabemadi.com/server_android/connector.php";
 //BA.debugLineNum = 11;BA.debugLine="Public Server_push As String=\"http://shahabemadi.";
_server_push = "http://shahabemadi.com/server_android/pushe/index.php";
 //BA.debugLineNum = 14;BA.debugLine="Public color_base() As Int=Array As Int(0xff27CCF";
_color_base = new int[]{(int) (0xff27ccf0),(int) (0xb927ccf0)};
 //BA.debugLineNum = 17;BA.debugLine="Dim RSACode_bazaar As String =\"MIHNMA0GCSqGSIb3DQ";
_rsacode_bazaar = "MIHNMA0GCSqGSIb3DQEBAQUAA4G7ADCBtwKBrwDBqLRR0FZK5XjGRo9bSwLeNg6Cs6lC9Yr6slX7pK9oLMJsgawCTDbujisnwvnoPq6aRtTzTW6rILv1QZgHRke2xPXnQS+qmsnrQCSzTgWQU/QmM41VEMcoDEJ7fUo8shVD7e0hF53Ssc8BDZZ8C37/uVJKayi3V72X4kSVrhYpcKi2tdb6ehL0y0L0uLaN0CXPvV04zMgjsbYF4MBB9G5LZ4t7EpP42ZScVUrPabcCAwEAAQ==";
 //BA.debugLineNum = 20;BA.debugLine="Public db_name As String=\"pojenpar_ifollow\"";
_db_name = "pojenpar_ifollow";
 //BA.debugLineNum = 21;BA.debugLine="Public db_pass As String=\"112233445566\"";
_db_pass = "112233445566";
 //BA.debugLineNum = 22;BA.debugLine="Public db_user As String=\"pojenpar_hpk\"";
_db_user = "pojenpar_hpk";
 //BA.debugLineNum = 23;BA.debugLine="End Sub";
return "";
}
public static String  _service_create() throws Exception{
 //BA.debugLineNum = 25;BA.debugLine="Sub Service_Create";
 //BA.debugLineNum = 28;BA.debugLine="End Sub";
return "";
}
public static String  _service_destroy() throws Exception{
 //BA.debugLineNum = 43;BA.debugLine="Sub Service_Destroy";
 //BA.debugLineNum = 45;BA.debugLine="End Sub";
return "";
}
public static String  _service_start(anywheresoftware.b4a.objects.IntentWrapper _startingintent) throws Exception{
 //BA.debugLineNum = 30;BA.debugLine="Sub Service_Start (StartingIntent As Intent)";
 //BA.debugLineNum = 32;BA.debugLine="End Sub";
return "";
}
public static String  _service_taskremoved() throws Exception{
 //BA.debugLineNum = 34;BA.debugLine="Sub Service_TaskRemoved";
 //BA.debugLineNum = 36;BA.debugLine="End Sub";
return "";
}
}
