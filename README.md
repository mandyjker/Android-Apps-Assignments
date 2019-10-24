# Android-Apps-Assignments
These are 3 apps created as an assignment @ my university

## App 1
The 1st app has 2 activities. On the Main Activity it requests a user ID, latitude and longitude and it saves it on the database when the button "Save is pressed".
By pressing the button "List" the ListAllActivity shows all the entries on the database. 

## App 2
The 2nd app uses a Broadcast Receiver to start a Service that requests location updates every 3 seconds and saves them to the database of the 1st app. 
The service starts and stops when the airplane mode is enabled or disabled.

## App 3
The 3rd app requests from the user to input a user ID and when they press the button "Proceed" it takes them to a MapActivity,
where it shows all the entries from the database of the 1st app with this specific user ID as Markers on the map.
