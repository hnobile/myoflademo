/myoflademo: 
Contains the compiled web app. Copy the folder to red5.
The important thing are the configuration files in the WEB-INF folder

/flexClient:
Contains the flex application.
BEFORE USE, you shoud change the connection url in gateways.RTCGateway.as, line 21:
this.connect("rtmp://red5.local/myOflaDemo");

Once you have compiled and copied de app to red5, you should enter to http://[your.host]:5080/myoflademo/MiniClient.html

/server:
contains the server.
it imports the red5-server.jar from /server/lib. this is the jar that comes within Red 1.0.3 last release.