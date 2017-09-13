package hpksoftweare.likearzoon.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_buy{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("btn_off").vw.setLeft((int)((views.get("btn_off").vw.getLeft())));
views.get("btn_off").vw.setWidth((int)((views.get("txt_off").vw.getLeft()) - ((views.get("btn_off").vw.getLeft()))));
//BA.debugLineNum = 6;BA.debugLine="btn_off.SetTopAndBottom(txt_off.Top,txt_off.Bottom)"[buy/General script]
views.get("btn_off").vw.setTop((int)((views.get("txt_off").vw.getTop())));
views.get("btn_off").vw.setHeight((int)((views.get("txt_off").vw.getTop() + views.get("txt_off").vw.getHeight()) - ((views.get("txt_off").vw.getTop()))));
//BA.debugLineNum = 8;BA.debugLine="btn_off.VerticalCenter=txt_off.VerticalCenter"[buy/General script]
views.get("btn_off").vw.setTop((int)((views.get("txt_off").vw.getTop() + views.get("txt_off").vw.getHeight()/2) - (views.get("btn_off").vw.getHeight() / 2)));

}
}