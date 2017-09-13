Type=Activity
Version=6.8
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: False
	#IncludeTitle: True
#End Region

Sub Process_Globals
	
End Sub

Sub Globals
	Dim list1 As ListView
	
	Private progress_spot As SpotsDialog
End Sub

Sub Activity_Create(FirstTime As Boolean)
	progress_spot.Initialize2("در حال بارگذاری اطلاعات لطفا شکیبا باشید..",progress_spot.Theme_Custom,Colors.White,12,Typeface.LoadFromAssets("iransansmobile(fanum).ttf"),6,Colors.Yellow,False)
	
	
	If Main.admin_bool=1 Then
		list1.Initialize("list1")
		Activity.AddView(list1,0,0,100%x,100%y)
	
		list1.TwoLinesAndBitmap.ItemHeight=70dip
		list1.TwoLinesAndBitmap.Label.Height=70dip
		list1.TwoLinesAndBitmap.SecondLabel.Height=0
		list1.TwoLinesAndBitmap.Label.TextColor=Colors.Black
		list1.TwoLinesAndBitmap.Label.TextSize=14
		list1.TwoLinesAndBitmap.Label.Gravity=Gravity.CENTER_HORIZONTAL + Gravity.CENTER_VERTICAL
		list1.TwoLinesAndBitmap.Label.Typeface=Typeface.LoadFromAssets("iransans bold.ttf")
		
		
		connector.Initialize2(Me)
		progress_spot.ShowDialog
		connector.send_query($"SELECT `ticket`.*,`users`.`name` FROM `ticket`
left join `users` ON `users`.`id`=`ticket`.`user_id`
GROUP by `user_id` ORDER BY `id` DESC"$,"get_user","")
	End If
	
	
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub  list1_ItemClick (Position As Int, Value As Object)
	ticket.id_user=Value
	StartActivity(ticket)
End Sub

Sub  list1_ItemLongClick (Position As Int, Value As Object)
	If DialogResponse.POSITIVE=Msgbox2("آیا مطمعن هستید به حذف تیکت های این کاربر؟","توجه","بله","","خیر",Null) Then
		list1.RemoveAt(Position)
		connector.Initialize2(Me)
		progress_spot.ShowDialog
		connector.send_query($"DELETE FROM `ticket` WHERE `user_id`=${Value}"$,"delete_user","")
	End If
	
End Sub

Sub db_connector(records As List,tag As Object)
	Select tag
		
		Case "get_user"
			Log(records)
			If records.Size>0 Then
				For i=0 To records.Size-1
					Dim map1 As Map=records.Get(i)
					list1.AddTwoLinesAndBitmap2(map1.Get("name"),"",LoadBitmap(File.DirAssets,"user.png")	, map1.Get("user_id"))
					
				Next
			Else
				ToastMessageShow("هیچ کاربری تیکت ارسال نکرده است",True)	
			End If
			
			
		Case "delete_user"
			progress_spot.ShowDialog
			connector.send_query($"SELECT `ticket`.*,`users`.`name` FROM `ticket`
left join `users` ON `users`.`id`=`ticket`.`user_id`
GROUP by `user_id` ORDER BY `id` DESC"$,"get_user","")
			
		Case "disconnect"	'تگ قطع ارتباط
			Log("no internet")
			
		Case "eror"	'تگ ارور
			Log("eror")
			
	End Select
	progress_spot.DisMissDialog
	ProgressDialogHide
End Sub
