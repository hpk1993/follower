package hpksoftweare.likearzoon;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class functions {
private static functions mostCurrent = new functions();
public static Object getObject() {
    throw new RuntimeException("Code module does not support this method.");
}
 public anywheresoftware.b4a.keywords.Common __c = null;
public b4a.example.frgfg.connector _connector = null;
public b4a.example.frgfg.db_mysql _db_mysql = null;
public b4a.example.get_json _get_json = null;
public b4a.example.json_connector _json_connector = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public hpksoftweare.likearzoon.main _main = null;
public hpksoftweare.likearzoon.starter _starter = null;
public hpksoftweare.likearzoon.products _products = null;
public hpksoftweare.likearzoon.list_user _list_user = null;
public hpksoftweare.likearzoon.list_history _list_history = null;
public hpksoftweare.likearzoon.ticket _ticket = null;
public hpksoftweare.likearzoon.pushejsonservice _pushejsonservice = null;
public hpksoftweare.likearzoon.order _order = null;
public hpksoftweare.likearzoon.charj _charj = null;
public hpksoftweare.likearzoon.login _login = null;
public hpksoftweare.likearzoon.about _about = null;
public hpksoftweare.likearzoon.history _history = null;
public static String  _base64(anywheresoftware.b4a.BA _ba,String _txt) throws Exception{
anywheresoftware.b4a.agraham.byteconverter.ByteConverter _converter = null;
byte[] _byte1 = null;
anywheresoftware.b4a.objects.StringUtils _st = null;
 //BA.debugLineNum = 38;BA.debugLine="public Sub base64(txt As String)As String";
 //BA.debugLineNum = 39;BA.debugLine="Dim converter As ByteConverter";
_converter = new anywheresoftware.b4a.agraham.byteconverter.ByteConverter();
 //BA.debugLineNum = 40;BA.debugLine="Dim byte1() As Byte";
_byte1 = new byte[(int) (0)];
;
 //BA.debugLineNum = 41;BA.debugLine="byte1=converter.StringToBytes(txt,\"UTF-8\")";
_byte1 = _converter.StringToBytes(_txt,"UTF-8");
 //BA.debugLineNum = 42;BA.debugLine="Dim st As StringUtils";
_st = new anywheresoftware.b4a.objects.StringUtils();
 //BA.debugLineNum = 43;BA.debugLine="Return st.EncodeBase64	(byte1)";
if (true) return _st.EncodeBase64(_byte1);
 //BA.debugLineNum = 44;BA.debugLine="End Sub";
return "";
}
public static String  _convertnumbers2persian(anywheresoftware.b4a.BA _ba,String _snumber) throws Exception{
String[] _snumbers = null;
String _res = "";
int _j = 0;
int _i = 0;
 //BA.debugLineNum = 49;BA.debugLine="Sub ConvertNumbers2Persian(sNumber As String) As S";
 //BA.debugLineNum = 50;BA.debugLine="Dim sNumbers(10) As String";
_snumbers = new String[(int) (10)];
java.util.Arrays.fill(_snumbers,"");
 //BA.debugLineNum = 51;BA.debugLine="Dim res As String";
_res = "";
 //BA.debugLineNum = 52;BA.debugLine="Dim j As Int";
_j = 0;
 //BA.debugLineNum = 53;BA.debugLine="res = sNumber";
_res = _snumber;
 //BA.debugLineNum = 54;BA.debugLine="sNumbers(0) = \"٠\"";
_snumbers[(int) (0)] = "٠";
 //BA.debugLineNum = 55;BA.debugLine="sNumbers(1) = \"١\"";
_snumbers[(int) (1)] = "١";
 //BA.debugLineNum = 56;BA.debugLine="sNumbers(2) = \"٢\"";
_snumbers[(int) (2)] = "٢";
 //BA.debugLineNum = 57;BA.debugLine="sNumbers(3) = \"٣\"";
_snumbers[(int) (3)] = "٣";
 //BA.debugLineNum = 58;BA.debugLine="sNumbers(4) = \"٤\"";
_snumbers[(int) (4)] = "٤";
 //BA.debugLineNum = 59;BA.debugLine="sNumbers(5) = \"٥\"";
_snumbers[(int) (5)] = "٥";
 //BA.debugLineNum = 60;BA.debugLine="sNumbers(6) = \"٦\"";
_snumbers[(int) (6)] = "٦";
 //BA.debugLineNum = 61;BA.debugLine="sNumbers(7) = \"٧\"";
_snumbers[(int) (7)] = "٧";
 //BA.debugLineNum = 62;BA.debugLine="sNumbers(8) = \"٨\"";
_snumbers[(int) (8)] = "٨";
 //BA.debugLineNum = 63;BA.debugLine="sNumbers(9) = \"٩\"";
_snumbers[(int) (9)] = "٩";
 //BA.debugLineNum = 65;BA.debugLine="For i =0 To sNumber.Length - 1";
{
final int step15 = 1;
final int limit15 = (int) (_snumber.length()-1);
for (_i = (int) (0) ; (step15 > 0 && _i <= limit15) || (step15 < 0 && _i >= limit15); _i = ((int)(0 + _i + step15)) ) {
 //BA.debugLineNum = 66;BA.debugLine="If IsNumber(sNumber.SubString2(i,i+1))=True Then";
if (anywheresoftware.b4a.keywords.Common.IsNumber(_snumber.substring(_i,(int) (_i+1)))==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 67;BA.debugLine="j = sNumber.SubString2(i,i+1)";
_j = (int)(Double.parseDouble(_snumber.substring(_i,(int) (_i+1))));
 //BA.debugLineNum = 68;BA.debugLine="res = res.Replace(sNumber.CharAt(i),sNumbers(j)";
_res = _res.replace(BA.ObjectToString(_snumber.charAt(_i)),_snumbers[_j]);
 };
 }
};
 //BA.debugLineNum = 72;BA.debugLine="Return res";
if (true) return _res;
 //BA.debugLineNum = 73;BA.debugLine="End Sub";
return "";
}
public static int  _getid(anywheresoftware.b4a.BA _ba,String _name) throws Exception{
anywheresoftware.b4a.object.XmlLayoutBuilder _xml = null;
 //BA.debugLineNum = 23;BA.debugLine="Private Sub GetId(name As String ) As Int";
 //BA.debugLineNum = 24;BA.debugLine="Dim xml As XmlLayoutBuilder";
_xml = new anywheresoftware.b4a.object.XmlLayoutBuilder();
 //BA.debugLineNum = 25;BA.debugLine="Return xml.GetResourceId(\"drawable\",name)";
if (true) return _xml.GetResourceId("drawable",_name);
 //BA.debugLineNum = 26;BA.debugLine="End Sub";
return 0;
}
public static boolean  _is_user(anywheresoftware.b4a.BA _ba) throws Exception{
 //BA.debugLineNum = 33;BA.debugLine="Sub is_user()As Boolean";
 //BA.debugLineNum = 34;BA.debugLine="Return File.Exists(Starter.rute,Starter.file_user";
if (true) return anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._starter._rute,mostCurrent._starter._file_user);
 //BA.debugLineNum = 35;BA.debugLine="End Sub";
return false;
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 3;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 7;BA.debugLine="End Sub";
return "";
}
public static String  _ripple(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.ConcreteViewWrapper _v) throws Exception{
anywheresoftware.b4j.object.JavaObject _javao = null;
 //BA.debugLineNum = 27;BA.debugLine="Public Sub Ripple(v As View)";
 //BA.debugLineNum = 28;BA.debugLine="Dim javao As JavaObject";
_javao = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 29;BA.debugLine="javao.InitializeContext";
_javao.InitializeContext((_ba.processBA == null ? _ba : _ba.processBA));
 //BA.debugLineNum = 30;BA.debugLine="javao.RunMethodJO(\"setRipple\",Array(v))";
_javao.RunMethodJO("setRipple",new Object[]{(Object)(_v.getObject())});
 //BA.debugLineNum = 31;BA.debugLine="End Sub";
return "";
}
public static String  _setleftandrightdrawable(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.LabelWrapper _l,String _dr,String _dl) throws Exception{
anywheresoftware.b4j.object.JavaObject _jo = null;
 //BA.debugLineNum = 19;BA.debugLine="Public Sub SetLeftAndRightDrawable(l As Label,dr A";
 //BA.debugLineNum = 20;BA.debugLine="Dim jo As JavaObject=l";
_jo = new anywheresoftware.b4j.object.JavaObject();
_jo.setObject((java.lang.Object)(_l.getObject()));
 //BA.debugLineNum = 21;BA.debugLine="jo.RunMethod(\"setCompoundDrawablesWithIntrinsicBo";
_jo.RunMethod("setCompoundDrawablesWithIntrinsicBounds",new Object[]{(Object)(_getid(_ba,_dl)),(Object)(0),(Object)(_getid(_ba,_dr)),(Object)(0)});
 //BA.debugLineNum = 22;BA.debugLine="End Sub";
return "";
}
public static String  _setleftdrawable(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.LabelWrapper _l,String _d) throws Exception{
anywheresoftware.b4j.object.JavaObject _jo = null;
 //BA.debugLineNum = 15;BA.debugLine="Public Sub SetLeftDrawable(l As Label,d As String)";
 //BA.debugLineNum = 16;BA.debugLine="Dim jo As JavaObject=l";
_jo = new anywheresoftware.b4j.object.JavaObject();
_jo.setObject((java.lang.Object)(_l.getObject()));
 //BA.debugLineNum = 17;BA.debugLine="jo.RunMethod(\"setCompoundDrawablesWithIntrinsicBo";
_jo.RunMethod("setCompoundDrawablesWithIntrinsicBounds",new Object[]{(Object)(_getid(_ba,_d)),(Object)(0),(Object)(0),(Object)(0)});
 //BA.debugLineNum = 18;BA.debugLine="End Sub";
return "";
}
public static String  _setrightdrawable(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.LabelWrapper _l,String _d) throws Exception{
anywheresoftware.b4j.object.JavaObject _jo = null;
 //BA.debugLineNum = 11;BA.debugLine="Public Sub SetRightDrawable(l As Label,d As String";
 //BA.debugLineNum = 12;BA.debugLine="Dim jo As JavaObject=l";
_jo = new anywheresoftware.b4j.object.JavaObject();
_jo.setObject((java.lang.Object)(_l.getObject()));
 //BA.debugLineNum = 13;BA.debugLine="jo.RunMethod(\"setCompoundDrawablesWithIntrinsicBo";
_jo.RunMethod("setCompoundDrawablesWithIntrinsicBounds",new Object[]{(Object)(0),(Object)(0),(Object)(_getid(_ba,_d)),(Object)(0)});
 //BA.debugLineNum = 14;BA.debugLine="End Sub";
return "";
}
}
