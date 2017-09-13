Type=Service
Version=6.8
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Service Attributes 
	#StartAtBoot: False
	#ExcludeFromLibrary: True
#End Region

Sub Process_Globals
	Public file_user As String="77545345"
	Public rute As String=File.DirDefaultExternal
	Public Server_json As String="http://shahabemadi.com/server_android/index.php"
	Public Server_mysql As String="http://shahabemadi.com/server_android/connector.php"
	Public Server_push As String="http://shahabemadi.com/server_android/pushe/index.php"


	Public color_base() As Int=Array As Int(0xff27CCF0,0xB927CCF0)
	
	
	Dim RSACode_bazaar As String ="MIHNMA0GCSqGSIb3DQEBAQUAA4G7ADCBtwKBrwDBqLRR0FZK5XjGRo9bSwLeNg6Cs6lC9Yr6slX7pK9oLMJsgawCTDbujisnwvnoPq6aRtTzTW6rILv1QZgHRke2xPXnQS+qmsnrQCSzTgWQU/QmM41VEMcoDEJ7fUo8shVD7e0hF53Ssc8BDZZ8C37/uVJKayi3V72X4kSVrhYpcKi2tdb6ehL0y0L0uLaN0CXPvV04zMgjsbYF4MBB9G5LZ4t7EpP42ZScVUrPabcCAwEAAQ=="
	
	
	Public db_name As String="pojenpar_ifollow"
	Public db_pass As String="112233445566"
	Public db_user As String="pojenpar_hpk"
End Sub

Sub Service_Create
	

End Sub

Sub Service_Start (StartingIntent As Intent)

End Sub

Sub Service_TaskRemoved
	'This event will be raised when the user removes the app from the recent apps list.
End Sub

'Return true to allow the OS default exceptions handler to handle the uncaught exception.
Sub Application_Error (Error As Exception, StackTrace As String) As Boolean
	Return True
End Sub

Sub Service_Destroy

End Sub
