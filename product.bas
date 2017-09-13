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
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.

End Sub

Sub Globals
	Private progress_spot As SpotsDialog

	Dim list_product As List
	Private p_group As Panel
	Private DSFloatLabelEditText8 As DSFloatLabelEditText
	Private DSFloatLabelEditText1 As DSFloatLabelEditText
	Private DSFloatLabelEditText2 As DSFloatLabelEditText
	Private DSFloatLabelEditText3 As DSFloatLabelEditText
	Private DSFloatLabelEditText4 As DSFloatLabelEditText
	Private DSFloatLabelEditText5 As DSFloatLabelEditText
	Private DSFloatLabelEditText6 As DSFloatLabelEditText
	Private DSFloatLabelEditText7 As DSFloatLabelEditText
	Private ACSwitch1 As ACSwitch
	Dim scrol As ScrollView
	Private Spinner1 As Spinner
End Sub

Sub Activity_Create(FirstTime As Boolean)
	
	scrol.Initialize(100%y)
	Activity.AddView(scrol,0,0,100%x,100%y)
	scrol.Panel.LoadLayout("edit_product")
	p_group.Visible=False
	
	progress_spot.Initialize2("در حال بارگذاری اطلاعات لطفا شکیبا باشید..",progress_spot.Theme_Custom,Colors.White,12,Typeface.LoadFromAssets("iransansmobile(fanum).ttf"),6,Colors.Yellow,False)
	
	
	connector.Initialize(Me,"db",Starter.Server_mysql,Starter.db_name,Starter.db_user,Starter.db_pass)
	progress_spot.ShowDialog
	connector.send_query($"SELECT * FROM `product`"$,"get_service_db","")

End Sub

Sub Activity_Resume

End Sub

Sub ACSwitch1_CheckedChange(Checked As Boolean)
	
End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub





Sub db_connector(records As List,tag As Object)
	Select tag
		Case "get_service_db"
			
			list_product.Initialize
			 
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
				struct1. min_req = colservices.Get("min_req")
				struct1. show = colservices.Get("show")
				list_product.Add(struct1)
				Spinner1.Add(struct1. title)
				
			Next
			
			
		Case "disconnect"	'تگ قطع ارتباط
			Log("no internet")
			
		Case "eror"	'تگ ارور
			Log("eror")
			
	End Select
	progress_spot.DisMissDialog
	ProgressDialogHide
End Sub



Sub Spinner1_ItemClick (Position As Int, Value As Object)
	If Position > 0 Then
		p_group.Visible=True
		Dim struct1 As struct_req=list_product.Get(Position)
		DSFloatLabelEditText8.Text=struct1. title
		DSFloatLabelEditText1.Text=struct1. id
		DSFloatLabelEditText2.Text=struct1. quantity
		DSFloatLabelEditText3.Text=struct1. price_unit
		DSFloatLabelEditText4.Text=struct1. price
		DSFloatLabelEditText5.Text=struct1. max_req
		DSFloatLabelEditText6.Text=struct1. min_req
		DSFloatLabelEditText7.Text=struct1. alias
		If struct1.show=0 Then
			ACSwitch1.Checked=False
		Else
			ACSwitch1.Checked=True
		End If
	Else
		p_group.Visible=False
	End If
End Sub