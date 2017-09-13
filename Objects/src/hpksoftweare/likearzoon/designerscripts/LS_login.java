package hpksoftweare.likearzoon.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_login{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("txt_pass").vw.setTop((int)((50d / 100 * height) - (views.get("txt_pass").vw.getHeight() / 2)));
views.get("txt_user").vw.setTop((int)((views.get("txt_pass").vw.getTop())-(2d / 100 * height) - (views.get("txt_user").vw.getHeight())));
views.get("label1").vw.setTop((int)((views.get("txt_user").vw.getTop())-(2d / 100 * height) - (views.get("label1").vw.getHeight())));
views.get("label1").vw.setLeft((int)((views.get("txt_user").vw.getLeft() + views.get("txt_user").vw.getWidth()) - (views.get("label1").vw.getWidth())));
views.get("label2").vw.setLeft((int)((views.get("txt_pass").vw.getLeft())));
views.get("label2").vw.setWidth((int)((views.get("label1").vw.getLeft())-(2d * scale) - ((views.get("txt_pass").vw.getLeft()))));
views.get("label2").vw.setTop((int)((views.get("label1").vw.getTop())));
views.get("btn_login").vw.setTop((int)((views.get("txt_pass").vw.getTop() + views.get("txt_pass").vw.getHeight())+(2d / 100 * height)));
views.get("lbl_forget").vw.setTop((int)((views.get("btn_login").vw.getTop() + views.get("btn_login").vw.getHeight())+(2d / 100 * height)));
views.get("lbl_reg").vw.setTop((int)((views.get("btn_login").vw.getTop() + views.get("btn_login").vw.getHeight())+(2d / 100 * height)));
views.get("lbl_reg").vw.setLeft((int)((views.get("btn_login").vw.getLeft())));
views.get("lbl_reg").vw.setWidth((int)((views.get("btn_login").vw.getLeft() + views.get("btn_login").vw.getWidth()/2)-(5d / 100 * width) - ((views.get("btn_login").vw.getLeft()))));
views.get("lbl_forget").vw.setLeft((int)((views.get("lbl_reg").vw.getLeft() + views.get("lbl_reg").vw.getWidth())+(1d / 100 * width)));
views.get("lbl_forget").vw.setWidth((int)((views.get("btn_login").vw.getLeft() + views.get("btn_login").vw.getWidth()) - ((views.get("lbl_reg").vw.getLeft() + views.get("lbl_reg").vw.getWidth())+(1d / 100 * width))));

}
}