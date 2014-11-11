package events
{
	import flash.events.Event;

	public class NewStreamInRoomEvent extends Event
	{
		
		public var streamName:String;
		
		public function NewStreamInRoomEvent(streamName:String):void
		{
			super("NewStreamInRoomEvent");
			this.streamName = streamName;
		}
	}
}