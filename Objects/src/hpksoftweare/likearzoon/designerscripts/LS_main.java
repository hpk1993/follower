package hpksoftweare.likearzoon.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_main{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("btn_account").vw.setLeft((int)((20d / 100 * width)));
views.get("btn_account").vw.setWidth((int)((80d / 100 * width) - ((20d / 100 * width))));
views.get("btn_contact").vw.setLeft((int)((20d / 100 * width)));
views.get("btn_contact").vw.setWidth((int)((80d / 100 * width) - ((20d / 100 * width))));
views.get("btn_about").vw.setLeft((int)((20d / 100 * width)));
views.get("btn_about").vw.setWidth((int)((80d / 100 * width) - ((20d / 100 * width))));
views.get("btn_pegiri").vw.setLeft((int)((20d / 100 * width)));
views.get("btn_pegiri").vw.setWidth((int)((80d / 100 * width) - ((20d / 100 * width))));
views.get("btn_charj").vw.setLeft((int)((20d / 100 * width)));
views.get("btn_charj").vw.setWidth((int)((80d / 100 * width) - ((20d / 100 * width))));
views.get("btn_order").vw.setLeft((int)((20d / 100 * width)));
views.get("btn_order").vw.setWidth((int)((80d / 100 * width) - ((20d / 100 * width))));
if ((anywheresoftware.b4a.keywords.LayoutBuilder.getScreenSize()>=3.5d)) { 
;
views.get("btn_account").vw.setTop((int)((views.get("imageview1").vw.getTop())-(5d / 100 * height) - (views.get("btn_account").vw.getHeight())));
;}else{ 
;
views.get("btn_account").vw.setTop((int)((views.get("imageview1").vw.getTop())-(2.3d / 100 * height) - (views.get("btn_account").vw.getHeight())));
;};
views.get("btn_order").vw.setTop((int)((10d * scale)));
views.get("btn_charj").vw.setTop((int)((views.get("btn_order").vw.getTop() + views.get("btn_order").vw.getHeight())+(3d / 100 * height)));
views.get("btn_pegiri").vw.setTop((int)((views.get("btn_charj").vw.getTop() + views.get("btn_charj").vw.getHeight())+(3d / 100 * height)));
views.get("btn_about").vw.setTop((int)((views.get("btn_pegiri").vw.getTop() + views.get("btn_pegiri").vw.getHeight())+(3d / 100 * height)));
views.get("btn_contact").vw.setTop((int)((views.get("btn_about").vw.getTop() + views.get("btn_about").vw.getHeight())+(3d / 100 * height)));
views.get("btn_account").vw.setTop((int)((views.get("btn_contact").vw.getTop() + views.get("btn_contact").vw.getHeight())+(3d / 100 * height)));
views.get("imageview1").vw.setTop((int)((views.get("btn_account").vw.getTop() + views.get("btn_account").vw.getHeight())+(1.2d / 100 * height)));
views.get("btn_order").vw.setLeft((int)((101d / 100 * width)));
views.get("btn_charj").vw.setLeft((int)((110d / 100 * width)));
views.get("btn_pegiri").vw.setLeft((int)((120d / 100 * width)));
views.get("btn_about").vw.setLeft((int)((130d / 100 * width)));
//BA.debugLineNum = 31;BA.debugLine="btn_contact.Left =140%x"[main/General script]
views.get("btn_contact").vw.setLeft((int)((140d / 100 * width)));
//BA.debugLineNum = 32;BA.debugLine="btn_account.Left =150%x"[main/General script]
views.get("btn_account").vw.setLeft((int)((150d / 100 * width)));

}
}