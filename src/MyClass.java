import wiiusej.WiiUseApiManager;
import wiiusej.Wiimote;
import wiiusej.wiiusejevents.physicalevents.ExpansionEvent;
import wiiusej.wiiusejevents.physicalevents.IREvent;
import wiiusej.wiiusejevents.physicalevents.MotionSensingEvent;
import wiiusej.wiiusejevents.physicalevents.WiimoteButtonsEvent;
import wiiusej.wiiusejevents.utils.WiimoteListener;
import wiiusej.wiiusejevents.wiiuseapievents.ClassicControllerInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.ClassicControllerRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.DisconnectionEvent;
import wiiusej.wiiusejevents.wiiuseapievents.GuitarHeroInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.GuitarHeroRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.NunchukInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.NunchukRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.StatusEvent;

public class MyClass implements WiimoteListener{

	public void onButtonsEvent(WiimoteButtonsEvent arg0) {
		System.out.println(arg0);
		if (arg0.isButtonAPressed()){
			System.out.println("Je presse A");
			//WiiUseApiManager.shutdown();
		}
		if (arg0.isButtonBPressed())
			System.out.println("Je press B");
		if (arg0.isButtonPlusPressed())
			System.out.println("Je press +");
		if (arg0.isButtonUpPressed())
			System.out.println("Je press UP");
		if (arg0.isButtonDownPressed())
			System.out.println("Je press BAS");
		if (arg0.isButtonLeftPressed())
			System.out.println("Je press GAUCHE");
		if (arg0.isButtonRightPressed())
			System.out.println("Je press DROITE");
	
	}

	public void onIrEvent(IREvent arg0) {
		//System.out.println(arg0);
	}

	public void onMotionSensingEvent(MotionSensingEvent arg0) {
		/*System.out.println("X "+arg0.getRawAcceleration().getX()); 
		System.out.println("Y "+arg0.getRawAcceleration().getY()); 
		System.out.println("Z "+arg0.getRawAcceleration().getZ()); 

		System.out.println(arg0);*/
	}

	public void onExpansionEvent(ExpansionEvent arg0) {
		//System.out.println(arg0);
	}

	public void onStatusEvent(StatusEvent arg0) {
		//System.out.println(arg0);
	}

	public void onDisconnectionEvent(DisconnectionEvent arg0) {
		//System.out.println(arg0);
	}

	public void onNunchukInsertedEvent(NunchukInsertedEvent arg0) {
		//System.out.println(arg0);
	}

	public void onNunchukRemovedEvent(NunchukRemovedEvent arg0) {
		//System.out.println(arg0);
	}

	public static void main(String[] args) {
		System.out.println("Here");
		Wiimote[] wiimotes = WiiUseApiManager.getWiimotes(1, true);
		Wiimote wiimote = wiimotes[0];
		wiimote.activateIRTRacking();
		wiimote.activateMotionSensing();
		wiimote.addWiiMoteEventListeners(new MyClass());
	}

	@Override
	public void onClassicControllerInsertedEvent(ClassicControllerInsertedEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClassicControllerRemovedEvent(ClassicControllerRemovedEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGuitarHeroInsertedEvent(GuitarHeroInsertedEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGuitarHeroRemovedEvent(GuitarHeroRemovedEvent arg0) {
		// TODO Auto-generated method stub

	}
}
