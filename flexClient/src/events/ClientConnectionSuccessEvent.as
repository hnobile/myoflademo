package events
{
	import flash.events.Event;
	
	public class ClientConnectionSuccessEvent extends Event
	{
		private var clientId:String;
		
		public function ClientConnectionSuccessEvent(clientId:String):void
		{
			super("ClientConnectionSuccessEvent");
			this.clientId = clientId;
		}
		
		public function ClientId():String{
			return this.clientId;
		}
	}
}