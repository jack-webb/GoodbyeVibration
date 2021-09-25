# GoodbyeVibration (Root)

**Forcefully disable vibration and haptic feedback for any Android app**

Quite a few apps on my Android 10 device have begun ignoring my haptic feedback and vibration 
preferences, most notably the YouTube app when seeking along a video's timeline and its chapters. 
This behaviour, along with most other Android permissions, can be controlled on a per-app basis with
`appops`. This was removed from user-accessible settings in 4.4.2, but is still available under the hood. 

GoodbyeVibration uses `appops` under the hood to remove a given app's `VIBRATE` permission. It 
also provides a worker to persist the change, to prevent our setting from being overwritten when the app is updated.
