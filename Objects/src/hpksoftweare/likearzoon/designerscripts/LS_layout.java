package hpksoftweare.likearzoon.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_layout{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("panel1").vw.setLeft((int)(0d));
views.get("panel1").vw.setWidth((int)((100d / 100 * width) - (0d)));
views.get("panel1").vw.setTop((int)(0d));
views.get("panel1").vw.setHeight((int)((47d * scale) - (0d)));
if ((anywheresoftware.b4a.keywords.LayoutBuilder.getScreenSize()>6.5d)) { 
;
views.get("panel1").vw.setHeight((int)((64d * scale)));
;}else{ 
;
if ((BA.ObjectToBoolean( String.valueOf(anywheresoftware.b4a.keywords.LayoutBuilder.isPortrait())))) { 
;
views.get("panel1").vw.setHeight((int)((56d * scale)));
;}else{ 
;
views.get("panel1").vw.setHeight((int)((48d * scale)));
;};
//BA.debugLineNum = 15;BA.debugLine="End If"[layout/General script]
;};
//BA.debugLineNum = 17;BA.debugLine="btn_product.Height=Panel1.Height"[layout/General script]
views.get("btn_product").vw.setHeight((int)((views.get("panel1").vw.getHeight())));
//BA.debugLineNum = 18;BA.debugLine="btn_product.Width=Panel1.Height"[layout/General script]
views.get("btn_product").vw.setWidth((int)((views.get("panel1").vw.getHeight())));
//BA.debugLineNum = 19;BA.debugLine="btn_product.Left=1dip"[layout/General script]
views.get("btn_product").vw.setLeft((int)((1d * scale)));
//BA.debugLineNum = 21;BA.debugLine="lbl_icon1.Width=Panel1.Height-2dip"[layout/General script]
views.get("lbl_icon1").vw.setWidth((int)((views.get("panel1").vw.getHeight())-(2d * scale)));
//BA.debugLineNum = 22;BA.debugLine="lbl_icon1.Height=Panel1.Height-2dip"[layout/General script]
views.get("lbl_icon1").vw.setHeight((int)((views.get("panel1").vw.getHeight())-(2d * scale)));
//BA.debugLineNum = 23;BA.debugLine="lbl_icon1.VerticalCenter=Panel1.VerticalCenter"[layout/General script]
views.get("lbl_icon1").vw.setTop((int)((views.get("panel1").vw.getTop() + views.get("panel1").vw.getHeight()/2) - (views.get("lbl_icon1").vw.getHeight() / 2)));
//BA.debugLineNum = 24;BA.debugLine="lbl_icon1.Right=98%x"[layout/General script]
views.get("lbl_icon1").vw.setLeft((int)((98d / 100 * width) - (views.get("lbl_icon1").vw.getWidth())));
//BA.debugLineNum = 26;BA.debugLine="lbl_price.SetLeftAndRight(40%x,lbl_icon1.Left)"[layout/General script]
views.get("lbl_price").vw.setLeft((int)((40d / 100 * width)));
views.get("lbl_price").vw.setWidth((int)((views.get("lbl_icon1").vw.getLeft()) - ((40d / 100 * width))));
//BA.debugLineNum = 27;BA.debugLine="lbl_price.Height=Panel1.Height-4dip"[layout/General script]
views.get("lbl_price").vw.setHeight((int)((views.get("panel1").vw.getHeight())-(4d * scale)));
//BA.debugLineNum = 28;BA.debugLine="lbl_price.VerticalCenter=Panel1.VerticalCenter"[layout/General script]
views.get("lbl_price").vw.setTop((int)((views.get("panel1").vw.getTop() + views.get("panel1").vw.getHeight()/2) - (views.get("lbl_price").vw.getHeight() / 2)));
//BA.debugLineNum = 30;BA.debugLine="panel_base.SetLeftAndRight(0,100%x)"[layout/General script]
views.get("panel_base").vw.setLeft((int)(0d));
views.get("panel_base").vw.setWidth((int)((100d / 100 * width) - (0d)));
//BA.debugLineNum = 31;BA.debugLine="panel_base.SetTopAndBottom(Panel1.Height,100%y)"[layout/General script]
views.get("panel_base").vw.setTop((int)((views.get("panel1").vw.getHeight())));
views.get("panel_base").vw.setHeight((int)((100d / 100 * height) - ((views.get("panel1").vw.getHeight()))));

}
}