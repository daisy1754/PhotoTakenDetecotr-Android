This sample app demonstrates how you can detect newly taken photo on Android.


This app trys two options:

1. Using BroadcastReceiver to receive NEW_PICTURE broadcast
2. Using ContentObserver to detect change in file


Problem for approach#1 is that third-party camera apps may not send NEW_PICTURE intent.  
Problem for approach#2 is that an instance of ContentObserver is a part of app, so OS or users may kill them
