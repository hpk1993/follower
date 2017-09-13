Type=Activity
Version=6.8
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Extends:android.support.v7.app.AppCompatActivity

#Region  Activity Attributes 
	#FullScreen: False
	#IncludeTitle: True
#End Region

Sub Process_Globals
	Public type_page As String="login"
End Sub

Sub Globals
	
	Private progress_spot As SpotsDialog
	Private txt_user As DSFloatLabelEditText
	Private txt_pass As DSFloatLabelEditText
	Private btn_login As Button
	Private lbl_forget As Label
	Private lbl_reg As Label
	Private txt_name As DSFloatLabelEditText
	Private txt_pass2 As DSFloatLabelEditText
	Private btn_reg As Button
	Private lbl_login As Label
	
	Dim scrol As ScrollView
	Dim ime1 As IME

	Private btn_login_google As Button
	
	Dim map_user As Map
	Dim su As StringUtils
End Sub

Sub Activity_Create(FirstTime As Boolean)
	
	scrol.Initialize(100%y)
	Activity.AddView(scrol,0,0,100%x,100%y)
	progress_spot.Initialize2("در حال بارگذاری اطلاعات لطفا شکیبا باشید..",progress_spot.Theme_Custom,Colors.White,12,Typeface.LoadFromAssets("iransansmobile(fanum).ttf"),6,Colors.Yellow,False)
	connector.Initialize2(Me)
	
	
	
	
	
	initialize_layout("login")
	

End Sub

Sub ime1_HeightChanged (NewHeight As Int, OldHeight As Int)
	scrol.Height=NewHeight
	Log("NewHeight =" & NewHeight)
End Sub


Sub initialize_layout(typee As String)
	Dim xmlb As XmlLayoutBuilder
	
	If typee="login" Then
		scrol.Panel.RemoveAllViews
		scrol.Panel.LoadLayout("login")
		scrol.Panel.Height=lbl_reg.Top + lbl_reg.Height + 3dip
		txt_pass.PasswordMode=True
		txt_user.InputType=txt_user.INPUT_TYPE_PHONE
		'		txt_pass.PasswordVisibilityToggleEnabled=True
		'		txt_pass.PasswordVisibilityToggleDrawable=xmlb.GetDrawable("ic_star_black_24dp")
		'
		
	Else if typee="reg" Then
		scrol.Panel.RemoveAllViews
		scrol.Panel.LoadLayout("register")
		scrol.Panel.Height=lbl_login.Top + lbl_login.Height + 3dip
		txt_pass.PasswordMode=True
		txt_pass2.PasswordMode=True
		txt_name.Visible=False
	End If
	
	ime1.Initialize("ime1")
	ime1.AddHeightChangedEvent
	ime1.ShowKeyboard(txt_pass)
	
	

End Sub


Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub




Sub lbl_reg_Click
	Log(type_page)
	If Not(type_page="reg") Then
		type_page="reg"
		initialize_layout(type_page)
	End If
End Sub

Sub lbl_forget_Click
	If txt_user.Text.Trim.Length=11 Then
		progress_spot.ShowDialog
		lbl_forget.Visible=False
		json_connector.Initialize2(Me)
		json_connector.send_query($"forgetpassword_me=${txt_user.Text.Trim}&d=${Starter.db_name}&u=${Starter.db_user}&p=${Starter.db_pass}"$,"forget","")
		
	Else
		ToastMessageShow("لطفا شماره تلفن را به درستی وارد کنید",False)
	End If
End Sub


Sub lbl_login_Click
	If Not(type_page="login") Then
		type_page="login"
		initialize_layout(type_page)
	End If
	
End Sub




Sub btn_reg_Click
	If txt_name.ErrorText.Length<=0 And txt_pass.ErrorText.Length<=0 And txt_pass2.ErrorText.Length<=0 And txt_user.ErrorText.Length<=0 Then
'		json_connector.send_query($"name=${txt_name.Text.Trim}&email=${txt_user.Text.Trim}&password=${txt_pass.Text.Trim}&register=true"$,"reg","")
		'	progress_spot.ShowDialog
		connector.send_query($"select * from `users` where `mobile`='${txt_user.Text.Trim}' "$,"check_login","")
	End If
	
	
	
End Sub





Sub jsn_json(res As String,tag As Object)
	Dim parser As JSONParser
	Select tag
		Log(res)
		Case "forget"
			Log("res : " & res)
				ToastMessageShow(res,True)
		Case "disconnect"	'تگ قطع ارتباط
			Log("no internet")

		Case "eror"	'تگ ارور
			Log("eror")

	End Select
	progress_spot.DisMissDialog
	ProgressDialogHide

End Sub



Sub UserLogin(Data As Map,login As Boolean)
	progress_spot.DisMissDialog
	'	Log(Data)
	'	Log(Data.Get("name"))
	'
	'

	json_connector.send_query($"name=${Data.Get("name")}&email=${Data.Get("email")}&google=true"$,"check_google","")
End Sub


Sub db_connector(records As List,tag As Object)
	Select tag
		Case "check_login"
			If records.Size<=0 Then
				progress_spot.ShowDialog
				connector.send_query($"Insert into `users`(`name`,`mobile`,`pass`) Values(N'${txt_name.Text.Trim}','${txt_user.Text.Trim}','${functions.base64(txt_pass.Text.Trim)}')"$,"reg","")
			Else
				ToastMessageShow("تلفن شما از قبل ثبت شده",True)
			End If
		
		Case "reg"
			Log(records)
			Activity.Finish
			ToastMessageShow("ثبت نام شدید.میتوانید با دادن نام کاربری و گذرواژه وارد شوید ",False)
			StartActivity(Me)
		
		Case "login"
			If records.Size>0 Then
				
				map_user.Initialize
				Dim root As Map = records.Get(0)
				Dim byte1 As ByteConverter
				
				If byte1.StringFromBytes(	su.DecodeBase64(root.Get("pass"))	,"UTF-8") =txt_pass.Text.Trim Then
					
					map_user.Put("id",root.Get("id")	)
					map_user.Put("mobile",root.Get("mobile")	)
					map_user.Put("name",root.Get("name")	)
					map_user.Put("pass",root.Get("pass")	)
					map_user.Put("admin",root.Get("admin")	)
					File.WriteMap(Starter.rute,Starter.file_user,map_user)
					Activity.Finish
					ToastMessageShow(map_user.Get("name") & " " & "خوش آمدید ",False)
				End If
				
			
		
			Else
				ToastMessageShow("تلفن یا گذرواژه شما نادرست است",True)
			End If
				
		Case "disconnect"	'تگ قطع ارتباط
			Log("no internet")
			
		Case "eror"	'تگ ارور
			Log("eror")
			
	End Select
	progress_spot.DisMissDialog
	ProgressDialogHide
End Sub




Sub btn_login_Click
	Log(functions.base64(txt_pass.Text.Trim))
	progress_spot.ShowDialog
	connector.send_query($"select * from `users` where `pass`='${functions.base64(txt_pass.Text.Trim)}' And `mobile`='${txt_user.Text.Trim}' "$,"login","")
	
End Sub



Sub txt_pass_TextChanged (Old As String, New As String)
	
	ckeck_validation
End Sub

Sub txt_user_TextChanged (Old As String, New As String)
	ckeck_validation
End Sub

Sub txt_name_TextChanged (Old As String, New As String)
	ckeck_validation
End Sub

Sub ckeck_validation
	Try
		
	
		
		If txt_name.Text.Trim.Length < 5 Then
			txt_name.ErrorText="نام شما باید بیشتر از 4 کاراکتر باشد"
		Else
			txt_name.ErrorText=""
		End If
		
		
		If txt_pass.Text.Trim.Length >= 6 Then
			txt_pass.ErrorText=""
			If txt_pass.Text.Trim=txt_pass2.Text.Trim Then
				txt_pass2.ErrorText=""
			End If
			If txt_pass.Text.Trim<>txt_pass2.Text.Trim Then
				txt_pass2.ErrorText="فیلد های گذرواژه با هم مطابقت ندارند"
			End If
		Else
			txt_pass.ErrorText="گذرواژه باید بیشتر از 5 کاراکتر باشد"
		End If
		
		If txt_user.Text.Trim.Length = 11 Then
'			If txt_user.Text.Contains("@")=False Then
'				txt_user.ErrorText="ایمیل نا معتبر است"
'			Else
			txt_user.ErrorText=""
'			End If
		Else
			txt_user.ErrorText="موبایل نا معتبر است  مثال:شماره را به صورت 091123456789 وارد کنید"
		End If
		
		
		
	Catch
		Log(LastException)
	End Try
End Sub

Sub txt_pass2_TextChanged (Old As String, New As String)
	ckeck_validation
End Sub