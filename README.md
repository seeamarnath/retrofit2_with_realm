# retrofit2_with_realm
A simple Networking example with caching date using Realm library.

Libraries used:
	Networking : Retrofit2
	Database : Realm

Release build apk is available in root folder of main app, named "release" 

Signing certificates are available in root folder of the project, named "certificate"     

Reason for 7MB size:
	Since we are using Realm library, it adds all architecture with apk. By using apk splits we can optimize it.
	https://academy.realm.io/posts/reducing-apk-size-native-libraries/	
	
For Google map API key:

	To get one, follow this link, follow the directions and press "Create" at the end:

    https://console.developers.google.com/flows/enableapi?apiid=maps_android_backend&keyType=CLIENT_SIDE_ANDROID&r=BD:44:D4:E7:76:52:35:C8:70:36:1C:B7:B5:52:BC:2F:3C:DF:14:19%3Bcom.amarnath.deliverytest

    You can also add your credentials to an existing key, using these values:

    Package name:
    com.amarnath.deliverytest

    SHA-1 certificate fingerprint:
	    For DEV build: BD:44:D4:E7:76:52:35:C8:70:36:1C:B7:B5:52:BC:2F:3C:DF:14:19
		For PROD build: AD:D7:88:72:BC:AB:F7:98:60:2C:0C:32:1C:5B:86:F7:60:1E:88:BB
	
    Alternatively, follow the directions here:
    https://developers.google.com/maps/documentation/android/start#get-key

    Once you have your key, replace the "google_maps_key" string in this corresponding debug/release folder. 
    


