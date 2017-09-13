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
	
End Sub

Sub Globals
	
	Dim rsender As ACRadioButton
	Dim PhoneId As String
	Dim bazzar As BillingManager3
	Dim Pardakht As Boolean
	Dim RSACode As String =Starter.RSACode_bazaar
	
	Dim wallet As Int
	Dim add_coins As Int
	
	
	Dim toman As money
	Dim progress_spot As SpotsDialog
	Dim scrol As ScrollView
	Private r1 As ACRadioButton
	Private r2 As ACRadioButton
	Private r3 As ACRadioButton
	Private r6 As ACRadioButton
	Private r4 As ACRadioButton
	Private r5 As ACRadioButton
	Private r9 As ACRadioButton
	Private r7 As ACRadioButton
	Private r8 As ACRadioButton
	Dim map_user As Map
	Private btn_charj As Button
End Sub

Sub Activity_Create(FirstTime As Boolean)
	If functions.is_user=True Then
		scrol.Initialize(100%y)
		Activity.AddView(scrol,0,0,100%x,100%y)
		scrol.Panel.LoadLayout("list_coins")
	
		scrol.Panel.Height=btn_charj.Top+btn_charj.Height+2dip
		r1.Checked=True
		map_user=File.ReadMap(Starter.rute,Starter.file_user)
		Try
			bazzar.Initialize("bazzar",RSACode)
			'			bazzar.DebugLogging=True
			'			bazzar.GetOwnedProducts

		Catch
			ToastMessageShow("Not Bazaar Install in the phone",True)
			'		Activity.Finish
	
		End Try
		progress_spot.Initialize2("در حال بارگذاری اطلاعات لطفا شکیبا باشید..",progress_spot.Theme_Custom,Colors.White,12,Typeface.LoadFromAssets("iransansmobile(fanum).ttf"),6,Colors.Yellow,False)
		connector.Initialize2(Me)
		
	Else
		Activity.Finish
	End If
	
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub


Sub radioselect_CheckedChange(Checked As Boolean)
	 rsender=Sender
	If functions.is_user=True Then
		If Msgbox2("از شارژ کیف پول با این مبلغ مطمعن هستم؟",toman.number(rsender.Tag) & " تومان","بله","خیر","",Null)=DialogResponse.POSITIVE Then
			If Pardakht = False Then
				Log("Error purches")
				Msgbox("خطایی رخ داد","خطا")
				Return
			Else
'				map_user=File.ReadMap(Starter.rute,Starter.file_user)
				bazzar.RequestPayment($"product_${rsender.Tag}"$,"inapp",rsender.Tag)
			End If
		End If
	Else
		ToastMessageShow("لطفا ابتدا وارد حساب کاربری خود شوید",False)
	End If
	
End Sub











Sub bazzar_BillingSupported (Supported As Boolean, Message As String)
	'	Log("Supported " & Supported & " | " & Message)
	Pardakht=Supported
	'	Log("1")
End Sub

Sub bazzar_OwnedProducts (Success As Boolean, Purchases As Map)
	
End Sub
Sub bazzar_ProductConsumed (Success As Boolean, product As Purchase)
'	LogColor(product.DeveloperPayload , Colors.Green)
	connector.Initialize2(Me)
	progress_spot.ShowDialog
	connector.send_query($"CALL `up_wallet`(${product.DeveloperPayload}, ${map_user.Get("id")}, @p2);"$,"wallet","")
	
End Sub
Sub bazzar_PurchaseCompleted (Success As Boolean, product As Purchase)
	If Success Then
		
		
		'		File.WriteString(File.DirInternal,"Success-pay.txt","paysuccess")
		'Consume
		bazzar.ConsumeProduct(product)
		
	Else
		ToastMessageShow("متاسفانه خرید ناموفق بود",False)
	End If
End Sub






Sub db_connector(records As List,tag As Object)
	Select tag
		Case "wallet"
'			If records.Size>0 Then
				ToastMessageShow("کیف پول شما شارژ شد",True)
				Activity.Finish
'			Else
'				
'			End If
		
		Case "disconnect"	'تگ قطع ارتباط
			Log("no internet")
			
		Case "eror"	'تگ ارور
			Log("eror")
			
	End Select
	progress_spot.DisMissDialog
	ProgressDialogHide
End Sub


Sub btn_charj_Click
	If r1.Checked=True Then
		rsender=r1
	End If
	If functions.is_user=True Then
		If Msgbox2("از شارژ کیف پول با این مبلغ مطمعن هستم؟",toman.number(rsender.Tag) & " تومان","بله","خیر","",Null)=DialogResponse.POSITIVE Then
			If Pardakht = False Then
				Log("Error purches")
				Msgbox("خطایی رخ داد","خطا")
				Return
			Else
				'				map_user=File.ReadMap(Starter.rute,Starter.file_user)
				bazzar.RequestPayment($"product_${rsender.Tag}"$,"inapp",rsender.Tag)
			End If
		End If
	Else
		ToastMessageShow("لطفا ابتدا وارد حساب کاربری خود شوید",False)
	End If
End Sub