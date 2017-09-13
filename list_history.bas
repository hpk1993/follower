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
	Dim list_details As Boolean
	Private progress_spot As SpotsDialog
End Sub

Sub Activity_Create(FirstTime As Boolean)
	progress_spot.Initialize2("در حال بارگذاری اطلاعات لطفا شکیبا باشید..",progress_spot.Theme_Custom,Colors.White,12,Typeface.LoadFromAssets("iransansmobile(fanum).ttf"),6,Colors.Yellow,False)
	
	
	If Main.admin_bool=1 Then
		list1.Initialize("list1")
		Activity.AddView(list1,0,0,100%x,100%y)
	
		list1.TwoLinesAndBitmap.ItemHeight=70dip
		list1.TwoLinesAndBitmap.Label.Height=70dip
		list1.TwoLinesAndBitmap.SecondLabel.Height=0dip
		list1.TwoLinesAndBitmap.Label.TextColor=Colors.Black
		list1.TwoLinesAndBitmap.Label.TextSize=14
		list1.TwoLinesAndBitmap.Label.Gravity=Gravity.CENTER_HORIZONTAL + Gravity.CENTER_VERTICAL
		list1.TwoLinesAndBitmap.Label.Typeface=Typeface.LoadFromAssets("iransans bold.ttf")
	
		
		
	End If
	
	
End Sub

Sub Activity_Resume
	connector.Initialize2(Me)
	list_user_order
End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub


Sub  list1_ItemClick (Position As Int, Value As Object)
	Dim Link As String=Value
	If Link.Contains("http")=False And Link.Length>0 Then
		details(Value)
	Else
		Msgbox(Link,"")
	End If
End Sub

Sub list_user_order
	progress_spot.ShowDialog
	connector.send_query($"SELECT `orders`.*,`users`.`name` FROM `orders`
left join `users` ON `users`.`id`=`orders`.`user_id`
GROUP by `user_id` ORDER BY `id` DESC"$,"get_user","")
End Sub

Sub details(Value As Int)
	progress_spot.ShowDialog
	connector.send_query($"SELECT `orders`.*,`users`.`name` FROM `orders`
left join `users` ON `users`.`id`=`orders`.`user_id`
 WHERE `user_id`=${Value}
order BY `id` DESC"$,"details","")
End Sub

Sub db_connector(records As List,tag As Object)
	Select tag
		
		Case "get_user"
			Log(records)
			If records.Size>0 Then
				list_details=False
				list1.Clear
				For i=0 To records.Size-1
					Dim map1 As Map=records.Get(i)
					list1.AddTwoLinesAndBitmap2(map1.Get("name"),"مشاهده جزئیات خرید",LoadBitmap(File.DirAssets,"user.png")	, map1.Get("user_id"))
					
					
				
				Next
				
			End If
			list1.TwoLinesAndBitmap.ItemHeight=70dip
			list1.TwoLinesAndBitmap.Label.Height=70dip
			list1.TwoLinesAndBitmap.SecondLabel.Height=0dip
			list1.TwoLinesAndBitmap.Label.TextColor=Colors.Black
			list1.TwoLinesAndBitmap.Label.TextSize=14
			list1.TwoLinesAndBitmap.Label.Gravity=Gravity.CENTER_HORIZONTAL + Gravity.CENTER_VERTICAL
			list1.TwoLinesAndBitmap.Label.Typeface=Typeface.LoadFromAssets("iransans bold.ttf")
		
		Case "details"
			If records.Size>0 Then
				list_details=True
				list1.Clear
				list1.TwoLinesAndBitmap.ItemHeight=100dip
				list1.TwoLinesAndBitmap.Label.Height=50dip
				list1.TwoLinesAndBitmap.SecondLabel.Height=50dip
				list1.TwoLinesAndBitmap.Label.TextColor=Colors.Black
				list1.TwoLinesAndBitmap.Label.TextSize=14
				list1.TwoLinesAndBitmap.SecondLabel.TextSize=14
				list1.TwoLinesAndBitmap.Label.Gravity=Gravity.CENTER_HORIZONTAL + Gravity.CENTER_VERTICAL
				list1.TwoLinesAndBitmap.SecondLabel.Gravity=Gravity.CENTER_HORIZONTAL + Gravity.CENTER_VERTICAL
				list1.TwoLinesAndBitmap.Label.Typeface=Typeface.LoadFromAssets("iransans bold.ttf")
				list1.TwoLinesAndBitmap.SecondLabel.Typeface=Typeface.LoadFromAssets("iransans bold.ttf")
				For i=0 To records.Size-1
					Dim map1 As Map=records.Get(i)
					Dim toman As money
					Dim date_str As String=map1.Get("date")
					Dim date1() As String=Regex.Split("-", date_str.SubString2(0,11)	)
					Dim MP As ManamPersianDate
					Dim dateper As  String= MP.GregorianToPersian(date1(0) , date1(1) , date1(2))
					list1.AddTwoLinesAndBitmap2(map1.Get("number") & " " & map1.Get("service") & " (" & toman.number(map1.Get("price")) & " تومان"  & ")" _
					, CRLF &  dateper & "   |   " & map1.Get("order_id") ,Null	,map1.Get("page"))
					list1.TwoLinesAndBitmap.SecondLabel.Gravity=Gravity.CENTER_HORIZONTAL + Gravity.CENTER_VERTICAL
					
			
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
		
		
Sub Activity_KeyPress (KeyCode As Int) As Boolean 'Return True to consume the event
	
	If list_details = True Then
		list_user_order
		Return True
	Else
		Activity.Finish
	
	End If
End Sub
