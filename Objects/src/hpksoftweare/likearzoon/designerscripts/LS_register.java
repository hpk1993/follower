package hpksoftweare.likearzoon.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_register{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("txt_pass").vw.setTop((int)((55d / 100 * height) - (views.get("txt_pass").vw.getHeight() / 2)));
views.get("txt_user").vw.setTop((int)((views.get("txt_pass").vw.getTop())-(2d / 100 * height) - (views.get("txt_user").vw.getHeight())));
views.get("label1").vw.setTop((int)((views.get("txt_user").vw.getTop())-(4d / 100 * height) - (views.get("label1").vw.getHeight())));
views.get("label1").vw.setLeft((int)((views.get("txt_user").vw.getLeft() + views.get("txt_user").vw.getWidth()) - (views.get("label1").vw.getWidth())));
views.get("label2").vw.setLeft((int)((views.get("txt_pass").vw.getLeft())));
views.get("label2").vw.setWidth((int)((views.get("label1").vw.getLeft())-(2d * scale) - ((views.get("txt_pass").vw.getLeft()))));
views.get("label2").vw.setTop((int)((views.get("label1").vw.getTop())));
views.get("txt_pass2").vw.setTop((int)((views.get("txt_pass").vw.getTop() + views.get("txt_pass").vw.getHeight())+(2d / 100 * height)));
views.get("btn_reg").vw.setTop((int)((views.get("txt_pass2").vw.getTop() + views.get("txt_pass2").vw.getHeight())+(1d / 100 * height)));
views.get("lbl_login").vw.setTop((int)((views.get("btn_reg").vw.getTop() + views.get("btn_reg").vw.getHeight())+(2d / 100 * height)));
views.get("lbl_login").vw.setLeft((int)((views.get("btn_reg").vw.getLeft() + views.get("btn_reg").vw.getWidth()/2)));
views.get("lbl_login").vw.setWidth((int)((views.get("btn_reg").vw.getLeft() + views.get("btn_reg").vw.getWidth()) - ((views.get("btn_reg").vw.getLeft() + views.get("btn_reg").vw.getWidth()/2))));

}
}