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

	Private lbl_version As Label
	Private Label4 As Label
	Private Label1 As Label
	
	Dim ht As Float
	Dim stringut As StringUtils
	Dim panelheight As Int
	Dim text As String
	Dim myfont As Typeface
End Sub

Sub Activity_Create(FirstTime As Boolean)
	
	Activity.LoadLayout("abount")
	lbl_version.Text=Application.VersionName
	
	Label4.Visible=False
	myfont=Typeface.LoadFromAssets("iransans bold.ttf")
	text=Label4.Text
	Dim pnl1 As Panel
	pnl1.Initialize("")
	Activity.AddView(pnl1,Label4.Left,Label4.Top,Label4.Width,Label4.Height)
	Dim sct As Scrol_Text_hpk
	sct.Initialize
	sct.scrollText(pnl1,Colors.Black,text,myfont,14,2,Gravity.RIGHT,15dip)
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub


Sub Label1_Click
	Dim i As Intent
	i.Initialize(i.ACTION_VIEW,"https://t.me/" & Label1.Tag)
	StartActivity(i)
End Sub

Sub Label2_Click
	Dim i As Intent
	i.Initialize(i.ACTION_VIEW,Label1.Text)
	StartActivity(i)
End Sub

Sub ImageView1_Click
	Msgbox(" حسن پیکرستان" & CRLF & " 09118771505" ," برناه نویس")
End Sub