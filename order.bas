Type=Activity
Version=6.8
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: False
	#IncludeTitle: True
	#Extends:android.support.v7.app.AppCompatActivity
#End Region

Sub Process_Globals
	
End Sub

Sub Globals
	Dim off_price As Int
	Dim map_user As Map
	Dim list_irfollower As List
	Dim list_irview As List
	Dim list_ircomment As List
	Dim list_otherlike As List
	Dim list_irlike As List
	Dim list_likealbum As List
	
	
	
	Dim map_min_max As Map
	
'	Private spin_service As ACSpinner
'	Private spin_quantity As ACSpinner
	Private txt_page As DSFloatLabelEditText
	Private txt_off As DSFloatLabelEditText
	Private btn_reg As Button
	Private txt_q As DSFloatLabelEditText
	Private lbl_pric_esum As Label
	Dim scrol As ScrollView
	Dim ime1 As IME
	Dim toman As money
	Private progress_spot As SpotsDialog
	Private spin_service,spin_quantity As Spinner
End Sub

Sub Activity_Create(FirstTime As Boolean)
	scrol.Initialize(100%y)
	Activity.AddView(scrol,0,0,100%x,100%y)
	scrol.Panel.LoadLayout("buy")
	scrol.Panel.Height=btn_reg.Top + btn_reg.Height + 3dip

	progress_spot.Initialize2("در حال بارگذاری اطلاعات لطفا شکیبا باشید..",progress_spot.Theme_Custom,Colors.White,12,Typeface.LoadFromAssets("iransansmobile(fanum).ttf"),6,Colors.Yellow,False)
	
	
'	Dim list1 As List
'	list1.Initialize
'	list1.Add("دسته بندی خود را انتخاب کنید")
''	list1.Add("فالو ایرانی اینستاگرام")
'	list1.Add("لایک ایرانی اینستاگرام")
'	list1.Add("لایک خارجی اینستاگرام")
'	list1.Add("کامنت رندوم اینستاگرام")
'	list1.Add("بازدید ویدیو اینستاگرام")
'	list1.Add("لایک آلبوم اینستاگرام")
'
'	spin_service.AddAll(list1)
	
	spin_service.DropdownTextColor=Colors.Black
	spin_quantity.DropdownTextColor=Colors.Black
	spin_quantity.TextSize=13
	
	spin_quantity.Add("بدون محصول")
'	connector.Initialize2(Me)
'	connector.send_query("select * from user","get_user","در حال ارسال")

	txt_q.InputType=txt_q.INPUT_TYPE_DECIMAL_NUMBERS
	

	ime1.Initialize("ime1")
	ime1.AddHeightChangedEvent

	
	json_connector.Initialize2(Me)
'	progress_spot.ShowDialog
	'	json_connector.send_query($"GetServices=true"$,"get_service","")

	connector.Initialize(Me,"db",Starter.Server_mysql,Starter.db_name,Starter.db_user,Starter.db_pass)
	progress_spot.ShowDialog
	connector.send_query($"SELECT * FROM `product` WHERE `show`=1 GROUP by `title` ORDER by `order_index`"$,"get_service_db","")
		
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub ime1_HeightChanged (NewHeight As Int, OldHeight As Int)
	scrol.Height=NewHeight
	Log("NewHeight =" & NewHeight)
End Sub




Sub btn_reg_Click
	Log(lbl_pric_esum.Tag)
	Log(map_min_max.Get("min"))
	Log(map_min_max.Get("max"))
	If functions.is_user=True Then
		If spin_service.SelectedIndex>0 And lbl_pric_esum.Tag<>"0"  Then
			Dim array1() As String=Regex.Split(";" , lbl_pric_esum.Tag	)
			Dim price As Int=array1(0)
			Dim service_id As Int=array1(1)
			Dim quantity As Int=array1(2)
		
		
			If txt_page.Text.Trim.Length > 3 Then
				If lbl_pric_esum.Tag <> "0" And txt_q.Text.Trim >= map_min_max.Get("min") And txt_q.Text.Trim <= map_min_max.Get("max") Then
					Log("$$$$" & off_price)
					If (off_price<=0) And (txt_off.Text.Trim.Length>0) Then
						ToastMessageShow("لطفا دکمه بررسی کد تخفیف را بزنید",False)
					Else
						Dim link_insta As String=""
						Dim user_insta As String=""
						If txt_page.Text.Trim.Contains("instagram.com")=True Then
							link_insta=txt_page.Text.Trim
						Else
							user_insta=txt_page.Text.Trim
						End If
						map_user=File.ReadMap(Starter.rute,Starter.file_user)
						Dim servis_name As String=spin_service.SelectedItem
						json_connector.Initialize2(Me)
						progress_spot.ShowDialog
						json_connector.send_query($"NewServices=true&ServiceID=${service_id}&quantity=${quantity}&InstagramUsername=${user_insta}&InstagramLink=${link_insta}&d=${Starter.db_name}&u=${Starter.db_user}&p=${Starter.db_pass}&price=${price}&userid=${map_user.Get("id")}&service=${servis_name}"$,"new_service","")
					
					End If
					
				Else
					ToastMessageShow("حداقل و حداکثر تعداد را رعایت کنید",False)
				End If
			Else
				ToastMessageShow("لینک صفحه خود را وارد کنید",False)
			End If
		Else
			ToastMessageShow("لطفا یک رویس را انتخاب کنید",False)
		End If
	Else
		ToastMessageShow("ابتدا وارد حساب کاربری خود شوید",False)
	End If
End Sub






Sub txt_off_TextChanged (Old As String, New As String)
	
End Sub

Sub txt_page_TextChanged (Old As String, New As String)
	
End Sub

Sub spin_quantity_ItemClick (Position As Int, Value As Object)
'	Try
'		If spin_service.SelectedIndex=1 Then
'			Dim struct1 As struct_req=list_irfollower.Get(Position-1)
'			txt_q.Text=struct1.quantity
'			txt_q.Enabled=False
'			map_min_max.put("min",0)
'			map_min_max.put("max",txt_q.Text.Trim)
'			lbl_pric_esum.Tag=pro_off_price(struct1.price,off_price) & ";" & struct1.id &  ";" & txt_q.Text.Trim
'			lbl_pric_esum.Text=text_show_off(struct1.price)
'		End If
'	Catch
'		Log(LastException)
'	End Try
	
	
End Sub


Sub spin_service_ItemClick (Position As Int, Value As Object)
	Try
		spin_quantity.Clear
		Select spin_service.SelectedItem 	'(Position)
			Case "دسته بندی خود را انتخاب کنید"
				spin_quantity.AddAll(Array As Object("بدون محصول"))
				txt_q.Text=0
				lbl_pric_esum.Text="مجموع سفارش: " & 0 & " تومان"
				txt_q.Enabled=False
				lbl_pric_esum.Tag=0
			Case 11
'				Dim list1 As List
'				list1.Initialize
'				list1.Add("یک محصول را انتخاب کنید")
'				For i=0 To list_irfollower.Size-1
'					Dim struct1 As struct_req=list_irfollower.Get(i)
'					list1.Add(struct1.title)
'				Next
'				spin_quantity.AddAll(list1)
'				txt_q.Text=0
'				lbl_pric_esum.Text="مجموع سفارش: " & 0 & " تومان"
'				txt_q.Enabled=False
'				map_min_max.put("min",0)
'				map_min_max.put("max",0)
			Case "لایک ایرانی"
				Dim list1 As List
				list1.Initialize
				For i=0 To list_irlike.Size-1
					Dim struct1 As struct_req=list_irlike.Get(i)
					list1.Add("حداقل تعداد: " & struct1.min_req & "  |  حداکثر: " & struct1.max_req)
					map_min_max.Put("min",struct1.min_req):map_min_max.Put("max",struct1.max_req)
				Next
				spin_quantity.AddAll(list1)
				txt_q.Text=struct1.min_req
				txt_q.Enabled=True
				lbl_pric_esum.Tag=pro_off_price((struct1.price_unit*txt_q.Text.Trim),off_price) & ";" & struct1.id &  ";" & txt_q.Text.Trim
				LogColor(list_irlike,Colors.Blue)
				LogColor((struct1.price_unit*txt_q.Text.Trim),Colors.Blue)
				LogColor(text_show_off(struct1.price),Colors.Blue)
				LogColor(off_price,Colors.Blue)
				lbl_pric_esum.Text=text_show_off((struct1.price_unit*txt_q.Text.Trim))
				
			Case "لایک خارجی"
				Dim list1 As List
				list1.Initialize
				For i=0 To list_otherlike.Size-1
					Dim struct1 As struct_req=list_otherlike.Get(i)
					list1.Add("حداقل تعداد: " & struct1.min_req & "  |  حداکثر: " & struct1.max_req)
					map_min_max.Put("min",struct1.min_req):map_min_max.Put("max",struct1.max_req)
				Next
				spin_quantity.AddAll(list1)
				txt_q.Text=struct1.min_req
				txt_q.Enabled=True
				lbl_pric_esum.Tag=pro_off_price((struct1.price_unit*txt_q.Text.Trim),off_price) & ";" & struct1.id &  ";" & txt_q.Text.Trim
				lbl_pric_esum.Text=text_show_off((struct1.price_unit*txt_q.Text.Trim))
				
			Case "Instagram Comment(IRAN)"
				Dim list1 As List
				list1.Initialize
				For i=0 To list_ircomment.Size-1
					Dim struct1 As struct_req=list_ircomment.Get(i)
					list1.Add("حداقل تعداد: " & struct1.min_req & "  |  حداکثر: " & struct1.max_req)
					map_min_max.Put("min",struct1.min_req):map_min_max.Put("max",struct1.max_req)
				Next
				spin_quantity.AddAll(list1)
				txt_q.Text=struct1.min_req
				txt_q.Enabled=True
				lbl_pric_esum.Tag=pro_off_price((struct1.price_unit*txt_q.Text.Trim),off_price) & ";" & struct1.id &  ";" & txt_q.Text.Trim
				lbl_pric_esum.Text=text_show_off((struct1.price_unit*txt_q.Text.Trim))
				
			Case "Instagram View(IRAN)"
				Dim list1 As List
				list1.Initialize
				For i=0 To list_irview.Size-1
					Dim struct1 As struct_req=list_irview.Get(i)
					list1.Add("حداقل تعداد: " & struct1.min_req & "  |  حداکثر: " & struct1.max_req)
					map_min_max.Put("min",struct1.min_req):map_min_max.Put("max",struct1.max_req)
				Next
				spin_quantity.AddAll(list1)
				txt_q.Text=struct1.min_req
				txt_q.Enabled=True
				lbl_pric_esum.Tag=pro_off_price((struct1.price_unit*txt_q.Text.Trim),off_price) & ";" & struct1.id &  ";" & txt_q.Text.Trim
				lbl_pric_esum.Text=text_show_off((struct1.price_unit*txt_q.Text.Trim))
				
			Case "لایک آلبوم"
				Dim list1 As List
				list1.Initialize
				For i=0 To list_likealbum.Size-1
					Dim struct1 As struct_req=list_likealbum.Get(i)
					list1.Add("حداقل تعداد: " & struct1.min_req & "  |  حداکثر: " & struct1.max_req)
					map_min_max.Put("min",struct1.min_req):map_min_max.Put("max",struct1.max_req)
				Next
				spin_quantity.AddAll(list1)
				txt_q.Text=struct1.min_req
				txt_q.Enabled=True
				lbl_pric_esum.Tag=pro_off_price((struct1.price_unit*txt_q.Text.Trim),off_price) & ";" & struct1.id &  ";" & txt_q.Text.Trim
				lbl_pric_esum.Text=text_show_off((struct1.price_unit*txt_q.Text.Trim))
		End Select
	Catch
		Log(LastException)
	End Try
	
End Sub




Sub jsn_json(res As String,tag As Object)
	Dim parser As JSONParser
	Select tag
		Log(res)
		Case "get_service"
			
			
		
			
		Case "new_service"
			Log(res)
			If res.Length > 0 Then
				progress_spot.DisMissDialog
				If res.Trim.Contains("موجودی")=False Then
					parser.Initialize(res)
					Dim root As Map = parser.NextObject
					Dim state As String = root.Get("state")
					Dim order As String = root.Get("order")
				
					Msgbox(CRLF & "کد پیگیری: " & order,"وضعیت: " & state)
					Activity.Finish
				Else
					Msgbox(CRLF & res,"عدم ثبت سفارش")
				End If
				
			End If
		
		Case "get_off"
			If res.Trim.Contains("not")=False Then
				off_price=res
				Dim array1() As String=Regex.Split(";" , lbl_pric_esum.Tag	)
				Dim price As Int=array1(0)
				Log(price)
				lbl_pric_esum.Tag=pro_off_price(price,off_price) & ";" & array1(1) &  ";" & txt_q.Text.Trim
				lbl_pric_esum.Text=text_show_off(price)
			Else
				txt_off.Text=""
				off_price=0
				ToastMessageShow("کد تخفیف شما نا معتبر است",False)
			End If
			
				
		Case "disconnect"	'تگ قطع ارتباط
			Log("no internet")

		Case "eror"	'تگ ارور
			Log("eror")

	End Select
	progress_spot.DisMissDialog
	ProgressDialogHide

End Sub





Sub text_show_off(price As Int)As String
	If off_price > 0 Then
		Return "مجموع سفارش: " & toman.number(price) & " تومان" & " با تخفیف " & toman.number(pro_off_price(price,off_price)) & " تومان"
	Else
		Return "مجموع سفارش: " & toman.number(price) & " تومان"
	End If
	
End Sub


Sub pro_off_price(price As Int,percentage As Int)As Int
	Return (	price -	((price * percentage)/100)	 )
End Sub

Sub txt_q_TextChanged (Old As String, New As String)
	If spin_service.SelectedIndex> 0 Then
		Select spin_service.SelectedItem
			Case "لایک ایرانی"
				Dim struct1 As struct_req=list_irlike.Get(0)
			Case "لایک خارجی"
				Dim struct1 As struct_req=list_otherlike.Get(0)
			Case "Instagram Comment(IRAN)"
				Dim struct1 As struct_req=list_ircomment.Get(0)
			Case "Instagram View(IRAN)"
				Dim struct1 As struct_req=list_irview.Get(0)
			Case "لایک آلبوم"
				Dim struct1 As struct_req=list_likealbum.Get(0)
		End Select
		map_min_max.Put("min",struct1.min_req):map_min_max.Put("max",struct1.max_req)
		If txt_q.Text.Trim<>"" Then
		
			lbl_pric_esum.Tag=pro_off_price((struct1.price_unit*txt_q.Text.Trim),off_price) & ";" & struct1.id &  ";" & txt_q.Text.Trim
			lbl_pric_esum.Text=text_show_off(	(struct1.price_unit*txt_q.Text.Trim)	)
		End If
	End If
	

End Sub

Sub txt_off_FocusChanged (HasFocus As Boolean)
	
End Sub

Sub btn_off_Click
	Log( off_price	)
	If txt_off.Text.Trim.Length > 0 Then
		If off_price <=0  Then
			json_connector.Initialize2(Me)
			progress_spot.ShowDialog
			json_connector.send_query($"d=${Starter.db_name}&u=${Starter.db_user}&p=${Starter.db_pass}&codeoff=${txt_off.Text.Trim}"$,"get_off","")
		Else
			ToastMessageShow("کد تخفیف از قبل اعمال شده است",False)
		End If
	
	End If
	
End Sub



Sub db_connector(records As List,tag As Object)
	Select tag
		Case "get_service_db"
			list_irfollower.Initialize
			list_irview.Initialize
			list_ircomment.Initialize
			list_otherlike.Initialize
			list_irlike.Initialize
			list_likealbum.Initialize
			spin_service.Clear
			Dim list1 As List
			list1.Initialize
			list1.Add("دسته بندی خود را انتخاب کنید")
			
			For Each colservices As Map In records
				Dim struct1 As struct_req
				struct1.Initialize
				
				struct1.price_unit = colservices.Get("price_unit")
				struct1. quantity= colservices.Get("quantity")
				struct1. price = colservices.Get("price")
				struct1. max_req = colservices.Get("max_req")
				struct1. alias  = colservices.Get("alias")
				struct1. description  = colservices.Get("description")
				struct1. id  = colservices.Get("id_service")	'id

				struct1. title  = colservices.Get("title")
				list1.Add(struct1. title)
				
				struct1. min_req = colservices.Get("min_req")
				map_min_max.Initialize
				map_min_max.Put("min",0):map_min_max.Put("max",0)
				Select struct1.alias
					Case "irfollower"
						list_irfollower.Add(struct1)
						
					Case "irview"
						list_irview.Add(struct1)
						
					Case "ircomment"
						list_ircomment.Add(struct1)
					
					Case "otherlike"
						list_otherlike.Add(struct1)
						
					Case "irlike"
						list_irlike.Add(struct1)
					
					Case "likealbum"
						list_likealbum.Add(struct1)
						
				End Select
				
			Next
			spin_service.AddAll(list1)
			
		Case "disconnect"	'تگ قطع ارتباط
			Log("no internet")
			
		Case "eror"	'تگ ارور
			Log("eror")
			
	End Select
	progress_spot.DisMissDialog
	ProgressDialogHide
End Sub