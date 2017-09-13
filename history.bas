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
	Private progress_spot As SpotsDialog
	Dim map_user As Map
	Private ListView1 As ListView
End Sub

Sub Activity_Create(FirstTime As Boolean)
	If functions.is_user=True Then
		Activity.LoadLayout("history")
	
		progress_spot.Initialize2("در حال بارگذاری اطلاعات لطفا شکیبا باشید..",progress_spot.Theme_Custom,Colors.White,12,Typeface.LoadFromAssets("iransansmobile(fanum).ttf"),6,Colors.Yellow,False)
	
		map_user=File.ReadMap(Starter.rute,Starter.file_user)
	End If
	
	
End Sub

Sub Activity_Resume
	If functions.is_user=True Then
		connector.Initialize2(Me)
		progress_spot.ShowDialog
		connector.send_query($"SELECT * FROM `orders` where `user_id`=${map_user.Get("id")} order by `id` DESC"$,"get_orders","")
	End If
	
	
	
End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub


Sub db_connector(records As List,tag As Object)
	Select tag
		Case "get_orders"
			
			If records.Size>0 Then
				Dim toman As money
				For i=0 To records.Size-1
					Dim map1 As Map=records.Get(i)
					ListView1.AddTwoLines2(map1.Get("number") & " " & map1.Get("service") , toman.number(map1.Get("price")	) & " تومان" & " | " & map1.Get("order_id")	,map1.Get("order_id")	)
				Next
			
			Else
				ToastMessageShow("تا کنون سفارشی ثبت نکردید",True)
			End If
		
			ListView1.TwoLinesLayout.Label.Typeface=Typeface.LoadFromAssets("iransans bold.ttf")
			ListView1.TwoLinesLayout.SecondLabel.Typeface=Typeface.LoadFromAssets("iransans bold.ttf")
			ListView1.TwoLinesLayout.ItemHeight=60dip
			ListView1.TwoLinesLayout.Label.TextColor=Colors.Black
			ListView1.TwoLinesLayout.SecondLabel.TextColor=Starter.color_base(0)
			ListView1.TwoLinesLayout.Label.TextSize=13
			ListView1.TwoLinesLayout.SecondLabel.TextSize=13
			ListView1.TwoLinesLayout.Label.Gravity=Gravity.CENTER_HORIZONTAL + Gravity.CENTER_VERTICAL
			ListView1.TwoLinesLayout.SecondLabel.Gravity=Gravity.CENTER_HORIZONTAL + Gravity.CENTER_VERTICAL
		Case "disconnect"	'تگ قطع ارتباط
			Log("no internet")
			
		Case "eror"	'تگ ارور
			Log("eror")
			
	End Select
	progress_spot.DisMissDialog
	ProgressDialogHide
End Sub





Sub jsn_json(res As String,tag As Object)
	Dim parser As JSONParser
	Select tag

		Case "req"
			If res.Length>0 Then
				
				parser.Initialize(res)
				Dim root As Map = parser.NextObject
				Dim order_status As String = root.Get("order_status")
				Dim remains As String = root.Get("remains")
				Dim state As String = root.Get("state")
				Msgbox("وضعیت : " & order_status & CRLF & "باقی مانده : " & remains ,"نتیجه")
			End If
		
			
	End Select
	progress_spot.DisMissDialog
	ProgressDialogHide

End Sub


Sub ListView1_ItemClick (Position As Int, Value As Object)
	json_connector.Initialize2(Me)
	progress_spot.ShowDialog
	json_connector.send_query($"StatusOrder=${Value}"$,"req","")
End Sub