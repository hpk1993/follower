Type=StaticCode
Version=6.8
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
'Code module
'Subs in this code module will be accessible from all modules.
Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.

End Sub



Public Sub SetRightDrawable(l As Label,d As String)
	Dim jo As JavaObject=l
	jo.RunMethod("setCompoundDrawablesWithIntrinsicBounds",Array(0,0,GetId(d),0))
End Sub
Public Sub SetLeftDrawable(l As Label,d As String)
	Dim jo As JavaObject=l
	jo.RunMethod("setCompoundDrawablesWithIntrinsicBounds",Array(GetId(d),0,0,0))
End Sub
Public Sub SetLeftAndRightDrawable(l As Label,dr As String,dl As String)
	Dim jo As JavaObject=l
	jo.RunMethod("setCompoundDrawablesWithIntrinsicBounds",Array(GetId(dl),0,GetId(dr),0))
End Sub
Private Sub GetId(name As String ) As Int
	Dim xml As XmlLayoutBuilder
	Return xml.GetResourceId("drawable",name)
End Sub
Public Sub Ripple(v As View)
	Dim javao As JavaObject
	javao.InitializeContext
	javao.RunMethodJO("setRipple",Array(v))
End Sub

Sub is_user()As Boolean
	Return File.Exists(Starter.rute,Starter.file_user)
End Sub

#region base64
public Sub base64(txt As String)As String
	Dim converter As ByteConverter
	Dim byte1() As Byte
	byte1=converter.StringToBytes(txt,"UTF-8")
	Dim st As StringUtils
	Return st.EncodeBase64	(byte1)
End Sub
#end region



Sub ConvertNumbers2Persian(sNumber As String) As String
	Dim sNumbers(10) As String
	Dim res As String
	Dim j As Int
	res = sNumber
	sNumbers(0) = "٠"
	sNumbers(1) = "١"
	sNumbers(2) = "٢"
	sNumbers(3) = "٣"
	sNumbers(4) = "٤"
	sNumbers(5) = "٥"
	sNumbers(6) = "٦"
	sNumbers(7) = "٧"
	sNumbers(8) = "٨"
	sNumbers(9) = "٩"

	For i =0 To sNumber.Length - 1
		If IsNumber(sNumber.SubString2(i,i+1))=True Then
			j = sNumber.SubString2(i,i+1)
			res = res.Replace(sNumber.CharAt(i),sNumbers(j))
		End If
		
	Next
	Return res
End Sub

