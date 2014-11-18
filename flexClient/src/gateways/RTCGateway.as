package gateways
{
	import flash.events.Event;
	import flash.events.NetStatusEvent;
	import flash.net.NetConnection;
	
	import events.NewStreamInRoomEvent;
	
	
	public class RTCGateway extends NetConnection
	{
		public function RTCGateway()
		{
		}
		
		public function connectToServer():void{
			
			if(this.hasEventListener(NetStatusEvent.NET_STATUS))
				this.removeEventListener(NetStatusEvent.NET_STATUS, onNetStatus);
			this.addEventListener(NetStatusEvent.NET_STATUS, onNetStatus);
			this.connect("rtmp://test.webconf.me/myOflaDemo"); // Change the Red5 URL
		}
		
		protected function onNetStatus(e:NetStatusEvent):void
		{
			trace("Net Satus: "+ e.info.code);
			
			switch (e.info.code)
			{
				case "NetConnection.Connect.Success":
					this.dispatchEvent(new Event("ConnectionSuccess"));
					break;
				case "NetConnection.Connect.Rejected":
				case "NetConnection.Connect.Failed":
					
					break;
				case "NetConnection.Connect.Closed":
					
					break;
				case "NetConnection.Call.Failed":
					
					break;
				default : {
					break;
				}
			}
		}
		
		
		public function newStreamInRoom(params:Object = null):void 
		{
			this.dispatchEvent(new NewStreamInRoomEvent(params as String));
		}
		
		
		
	}
}