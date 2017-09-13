Type=Activity
Version=6.8
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: False
	#IncludeTitle: True
#End Region

#Extends: android.support.v7.app.AppCompatActivity

Sub Process_Globals
	Public id_user As Int
	
End Sub

Sub Globals
	Private progress_spot As SpotsDialog
	Private chat As ChatView
'	Private msgview As MessageView
	Private builder As MessageBuilder
	
	Dim messages As List
	Dim bmp_user,bmp_admin As Bitmap
	Dim title_msg As String
	Dim last_text As String
	Dim map_user As Map
	
	Dim admin_user As Int
End Sub

Sub Activity_Create(FirstTime As Boolean)
	If functions.is_user=True Then
		Dim Intent1 As Intent
		Intent1 = Activity.GetStartingIntent
		If Intent1.HasExtra("Notification_Tag") Then
			Try
				Dim Notification_Tag As String= Intent1.GetExtra("Notification_Tag")
				id_user=Notification_Tag
			Catch
'				toast.Initialize(LastException.Message,toast.LENGTH_LONG,toast.WARNING)
			End Try
		End If
		
		chat.Initialize("chat")
		Activity.AddView(chat,0,0,100%x,100%y)
		progress_spot.Initialize2("در حال بارگذاری اطلاعات لطفا شکیبا باشید..",progress_spot.Theme_Custom,Colors.White,12,Typeface.LoadFromAssets("iransansmobile(fanum).ttf"),6,Colors.Yellow,False)
	
		map_user=File.ReadMap(Starter.rute,Starter.file_user)

		admin_user=map_user.Get("admin")
	
		bmp_user.Initialize(File.DirAssets,"user.png")
		bmp_admin.Initialize(File.DirAssets,"admin.png")
	
		connector.Initialize(Me,"db",Starter.Server_mysql,Starter.db_name,Starter.db_user,Starter.db_pass)
		progress_spot.ShowDialog
		connector.send_query($"SELECT `ticket`.*,`users`.`name` FROM `ticket`
left join `users` ON `users`.`id`=`ticket`.`user_id`
Where `user_id`=${id_user} order by `id` ASC"$,"get_msg","")
	
	End If
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub chat_onSendClicked(text As String)
	Log(text)
	If functions.is_user=True Then
		If text.Trim.Length > 0 Then
			map_user=File.ReadMap(Starter.rute,Starter.file_user)
			last_text=text
			connector.Initialize2(Me)
			progress_spot.ShowDialog
'			connector.send_query($"INSERT INTO `ticket`(`user_id`, `msg`, `title`, `date`, `sender_admin`) VALUES (${id_user},N'${text}',N'${""}',NOW() , ${admin_user})"$,"reg","")
			StartService(PusheJsonService)
			
			json_connector.Initialize(Me,"jsn",Starter.Server_push)
			Log($"chat=true&admin_user=${admin_user}&text=${text}&d=${Starter.db_name}&u=${Starter.db_user}&p=${Starter.db_pass}&uid=${id_user}"$)
			json_connector.send_query($"chat=true&name=${map_user.Get("name")}&admin_user=${admin_user}&text=${text}&d=${Starter.db_name}&u=${Starter.db_user}&p=${Starter.db_pass}&uid=${id_user}"$,"save_ticket","")
		End If
	Else
		ToastMessageShow("ابتدا وارد حساب کاربری خود شوید",True)
	End If


End Sub



Sub jsn_json(res As String,tag As Object)
	Dim parser As JSONParser
	Select tag

		Case "save_ticket"
			Log(res)
			ToastMessageShow("تیکت شما ثبت شد",True)
			chat.InputText=""
'			If admin_user=1 Then initializeMsg(map_user.Get("name"),last_text,"لحظاتی پیش",1,True)
'			If admin_user=0 Then initializeMsg(map_user.Get("name"),last_text,"لحظاتی پیش",1,False)
			
		Case "disconnect"	'تگ قطع ارتباط
			Log("no internet")

		Case "eror"	'تگ ارور
			Log("eror")

	End Select
	progress_spot.DisMissDialog
	ProgressDialogHide

End Sub







Sub db_connector(records As List,tag As Object)
	Select tag
		Case "reg"
			ToastMessageShow("تیکت شما ثبت شد",True)
			If admin_user=1 Then initializeMsg(map_user.Get("name"),last_text,"لحظاتی پیش",1,True)
			If admin_user=0 Then initializeMsg(map_user.Get("name"),last_text,"لحظاتی پیش",1,False)
			
			chat.InputText=""
			
		Case "get_msg"
			Log(records)
			If records.Size>0 Then
				For i=0 To records.Size-1
					Dim map1 As Map=records.Get(i)
					Dim sender_admin As Int=map1.Get("sender_admin")
					Dim user_id As Int=map1.Get("user_id")
					Dim msg_txt As String=map1.Get("msg")
					Dim name As String=map1.Get("name")
					Dim date As String=map1.Get("date")
					If i=0 Then title_msg=map1.Get("title")
					Dim is_admin As Boolean
					If sender_admin=1 Then
						is_admin=True
					Else
						is_admin=False
					End If
					
					initializeMsg(name,msg_txt,date,i,is_admin)
				Next
				
			End If
		
		Case "disconnect"	'تگ قطع ارتباط
			Log("no internet")
			
		Case "eror"	'تگ ارور
			Log("eror")
			
	End Select
	progress_spot.DisMissDialog
	ProgressDialogHide
End Sub


public Sub callmsg(arg0() As Object)
	If arg0(3)=0 Then
		initializeMsg(	arg0(0),arg0(1) ,arg0(2),1,	False	)
	Else
		initializeMsg(	arg0(0),arg0(1) ,arg0(2),1,	True	)
	End If
	
End Sub


 Sub initializeMsg(username As String,txtmsg As String,date As String,index As Int,is_admin As Boolean)
		
	
	
	messages.Initialize
	
	Dim msg As Message
	builder.Initialize("")
	
	If admin_user=1 Then
		If is_admin=True Then
			If index=0 Then
				msg = builder.setUserIcon(bmp_admin).setMessageText(txtmsg).setRightMessage(	True	).setUserName("مدیریت").build
			Else
				msg = builder.setUserIcon(bmp_admin).setMessageText(txtmsg).setRightMessage(	True	).setUserName("").build
			End If
		Else
			If index=0 Then
				msg = builder.setUserIcon(bmp_user).setMessageText(txtmsg).setRightMessage(	False	).setUserName(username).build
			Else
				msg = builder.setUserIcon(bmp_user).setMessageText(txtmsg).setRightMessage(	False	).setUserName("").build
			End If
		End If
		
	Else
		
		If is_admin=True Then
			If index=0 Then
				msg = builder.setUserIcon(bmp_admin).setMessageText(txtmsg).setRightMessage(	False	).setUserName("مدیریت").build
			Else
				msg = builder.setUserIcon(bmp_admin).setMessageText(txtmsg).setRightMessage(	False	).setUserName("").build
			End If
		Else
			If index=0 Then
				msg = builder.setUserIcon(bmp_user).setMessageText(txtmsg).setRightMessage(	True	).setUserName(username).build
			Else
				msg = builder.setUserIcon(bmp_user).setMessageText(txtmsg).setRightMessage(	True	).setUserName("").build
			End If
		End If
		
	End If
	
	
	
	If date.Contains("لحظاتی پیش")=False Then
		Dim date_str As String=date
		Dim date1() As String=Regex.Split("-", date_str.SubString2(0,11)	)
		Dim MP As ManamPersianDate
		msg.TimeText= MP.GregorianToPersian(date1(0) , date1(1) , date1(2)) & "  " & functions.ConvertNumbers2Persian(date_str.SubString2(12,date_str.Length)		)
	
	End If
	

	msg.DateCell=False
	msg.DateSeparateText=""
	messages.Add(msg)
	chat.send(msg)
	
	
	
	
	chat.AutoScroll = True
	'	chat.BackgroundColor = Colors.Magenta
	chat.DateSeparatorColor = Colors.Blue
	
	chat.SendTimeTextColor=Colors.Blue
	chat.UsernameTextColor=Colors.Blue
	'	chat.SendTimeTextColor=Colors.Blue
	
	chat.InputTextHint = "لطفا از ارسال پیام های رگباری خودداری کنید"
	'	chat.Color=Colors.Blue
	chat.SetBackgroundImage(LoadBitmap(File.DirAssets,"bg2.jpg"))
	chat.SendButtonColor=Colors.Blue

	chat.LeftBubbleColor = 0x96FFFFFF
	chat.RightBubbleColor = 0x96D510F7
	chat.RightMessageTextColor = 0xFF04030D
	chat.LeftMessageTextColor = 0xFF04030D
	
	'	chat.RightMessageTextColor = Colors.Magenta
End Sub




