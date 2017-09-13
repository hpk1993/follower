Type=Service
Version=6.8
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region Module Attributes
	#StartAtBoot: true
	#ExcludeFromLibrary: True
#End Region

'Service module

Sub Process_Globals
Dim PusheUtil As PusheB4AUtil
End Sub
Sub Service_Create
	
End Sub

Sub Service_Start (StartingIntent As Intent)
	Select StartingIntent.Action
		Case "com.google.android.c2dm.intent.RECEIVE" 
			MessageArrived(StartingIntent)
	End Select
End Sub


Sub MessageArrived (pIntent As Intent)
	
	Dim JsonMsg As String
	JsonMsg = PusheUtil.getPusheJsonMsg(pIntent)
	LogColor(JsonMsg,Colors.Red)
	If JsonMsg <> "" Then
		Dim parser As JSONParser
		parser.Initialize(JsonMsg)
		Dim root As Map = parser.NextObject
		Dim action As String=root.Get("action")
		
		If action="Locked" Then
			File.WriteString(Starter.rute,"L4235","true")
			Dim nb As NotificationBuilder
			nb.Initialize
			nb.DefaultVibrate=True
			nb.DefaultSound=True
			nb.Autocancel=True
			nb.smallIcon="ico"
			nb.Ticker="پیام جدید "
			nb.ContentTitle="برنامه شما بدلیل برخی از مشکلات مسدود شد"
			nb.contentText=""
			nb.contentInfo="( " & Application.LabelName & " )"
			nb.Tag=""
				
			nb.setActivity(Main)
			nb.Notify(	1 )
		End If
		
		LogColor("Push: " & root.Get("text")	,Colors.Blue)
		LogColor("Push: " & root.Get("name")	,Colors.Blue)
		LogColor("Push: " & root.Get("date")	,Colors.Blue)
		LogColor("Push: " & root.Get("uid")	,Colors.Blue)
		LogColor("Push: " & root.Get("admin_user")	,Colors.Blue)
		
		Dim user_id As Int=root.Get("uid")
		Dim admin_user As Int=root.Get("admin_user")
		Dim text As String=root.Get("text")
		Dim date As String=root.Get("date")
		Dim name As String=root.Get("name")
		Dim arg0() As Object=Array As Object(name,text,date,admin_user)
		
		
		If user_id=Main.my_ID  Then 'myself  user
			Log("****user***")
			If IsPaused(ticket)=False Then
				CallSub2(ticket,"callmsg",arg0)
			Else
				Dim nb As NotificationBuilder
				nb.Initialize
				nb.DefaultVibrate=True
				nb.DefaultSound=True
				nb.Autocancel=True
				nb.smallIcon="admin"
				nb.Ticker="پاسخ تیکت "
				nb.ContentTitle="به تیکت شما پاسخ داده شد"
				nb.contentText=""
				nb.contentInfo="( " & Application.LabelName & " )"
				nb.Tag=user_id
				'			nb.setParentActivity(push_active)
				nb.setActivity(ticket)
				nb.Notify(	1 )
			End If
		End If
		
		
		If Main.admin_bool=1 Then 'myself  admin
			Log("****admin***")
			If IsPaused(ticket)=False Then
				CallSub2(ticket,"callmsg",arg0)
			Else
				Dim nb As NotificationBuilder
				nb.Initialize
				nb.DefaultVibrate=True
				nb.DefaultSound=True
				nb.Autocancel=True
				nb.smallIcon="admin"
				nb.Ticker="تیکت جدید "
				nb.ContentTitle="یک تیکت به ثبت رسید"
				nb.contentText=""
				nb.contentInfo="( " & Application.LabelName & " )"
				nb.Tag=user_id
				'			nb.setParentActivity(push_active)
				nb.setActivity(ticket)
				nb.Notify(	1 )
			End If
		End If
		
		
		
	End If '/not null
End Sub


Sub Service_Destroy

End Sub


