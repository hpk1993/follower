package hpksoftweare.likearzoon.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_edit_product{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("p_group").vw.setLeft((int)((2d / 100 * width)));
views.get("p_group").vw.setWidth((int)((98d / 100 * width) - ((2d / 100 * width))));
views.get("dsfloatlabeledittext1").vw.setLeft((int)((2d / 100 * width)));
views.get("dsfloatlabeledittext1").vw.setWidth((int)((views.get("p_group").vw.getWidth())-(2d / 100 * width) - ((2d / 100 * width))));
//BA.debugLineNum = 5;BA.debugLine="DSFloatLabelEditText2.SetLeftAndRight(2%x,p_group.Width-2%x)"[edit_product/General script]
views.get("dsfloatlabeledittext2").vw.setLeft((int)((2d / 100 * width)));
views.get("dsfloatlabeledittext2").vw.setWidth((int)((views.get("p_group").vw.getWidth())-(2d / 100 * width) - ((2d / 100 * width))));
//BA.debugLineNum = 6;BA.debugLine="DSFloatLabelEditText3.SetLeftAndRight(2%x,p_group.Width-2%x)"[edit_product/General script]
views.get("dsfloatlabeledittext3").vw.setLeft((int)((2d / 100 * width)));
views.get("dsfloatlabeledittext3").vw.setWidth((int)((views.get("p_group").vw.getWidth())-(2d / 100 * width) - ((2d / 100 * width))));
//BA.debugLineNum = 7;BA.debugLine="DSFloatLabelEditText4.SetLeftAndRight(2%x,p_group.Width-2%x)"[edit_product/General script]
views.get("dsfloatlabeledittext4").vw.setLeft((int)((2d / 100 * width)));
views.get("dsfloatlabeledittext4").vw.setWidth((int)((views.get("p_group").vw.getWidth())-(2d / 100 * width) - ((2d / 100 * width))));
//BA.debugLineNum = 8;BA.debugLine="DSFloatLabelEditText5.SetLeftAndRight(2%x,p_group.Width-2%x)"[edit_product/General script]
views.get("dsfloatlabeledittext5").vw.setLeft((int)((2d / 100 * width)));
views.get("dsfloatlabeledittext5").vw.setWidth((int)((views.get("p_group").vw.getWidth())-(2d / 100 * width) - ((2d / 100 * width))));
//BA.debugLineNum = 9;BA.debugLine="DSFloatLabelEditText6.SetLeftAndRight(2%x,p_group.Width-2%x)"[edit_product/General script]
views.get("dsfloatlabeledittext6").vw.setLeft((int)((2d / 100 * width)));
views.get("dsfloatlabeledittext6").vw.setWidth((int)((views.get("p_group").vw.getWidth())-(2d / 100 * width) - ((2d / 100 * width))));
//BA.debugLineNum = 10;BA.debugLine="DSFloatLabelEditText7.SetLeftAndRight(2%x,p_group.Width-2%x)"[edit_product/General script]
views.get("dsfloatlabeledittext7").vw.setLeft((int)((2d / 100 * width)));
views.get("dsfloatlabeledittext7").vw.setWidth((int)((views.get("p_group").vw.getWidth())-(2d / 100 * width) - ((2d / 100 * width))));
//BA.debugLineNum = 11;BA.debugLine="DSFloatLabelEditText8.SetLeftAndRight(2%x,p_group.Width-2%x)"[edit_product/General script]
views.get("dsfloatlabeledittext8").vw.setLeft((int)((2d / 100 * width)));
views.get("dsfloatlabeledittext8").vw.setWidth((int)((views.get("p_group").vw.getWidth())-(2d / 100 * width) - ((2d / 100 * width))));
//BA.debugLineNum = 12;BA.debugLine="ACSwitch1.SetLeftAndRight(2%x,p_group.Width-2%x)"[edit_product/General script]
views.get("acswitch1").vw.setLeft((int)((2d / 100 * width)));
views.get("acswitch1").vw.setWidth((int)((views.get("p_group").vw.getWidth())-(2d / 100 * width) - ((2d / 100 * width))));
//BA.debugLineNum = 13;BA.debugLine="btn_reg.SetLeftAndRight(2%x,p_group.Width-2%x)"[edit_product/General script]
views.get("btn_reg").vw.setLeft((int)((2d / 100 * width)));
views.get("btn_reg").vw.setWidth((int)((views.get("p_group").vw.getWidth())-(2d / 100 * width) - ((2d / 100 * width))));

}
}