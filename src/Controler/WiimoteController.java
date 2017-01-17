package Controler;
import Main.GlitchesBattle;
import wiiusej.WiiUseApiManager;
import wiiusej.Wiimote;
import wiiusej.wiiusejevents.physicalevents.NunchukButtonsEvent;
import wiiusej.wiiusejevents.physicalevents.ExpansionEvent;
import wiiusej.wiiusejevents.physicalevents.IREvent;
import wiiusej.wiiusejevents.physicalevents.MotionSensingEvent;
import wiiusej.wiiusejevents.physicalevents.WiimoteButtonsEvent;
import wiiusej.wiiusejevents.utils.WiimoteListener;
import wiiusej.wiiusejevents.utils.WiiUseApiListener;
import wiiusej.wiiusejevents.wiiuseapievents.ClassicControllerInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.ClassicControllerRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.DisconnectionEvent;
import wiiusej.wiiusejevents.wiiuseapievents.GuitarHeroInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.GuitarHeroRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.NunchukInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.NunchukRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.StatusEvent;

public class WiimoteController implements WiimoteListener{
	private GlitchesBattle my_parent;
	private boolean animationHitFinished;

	public WiimoteController( GlitchesBattle p) {
		my_parent = p;
		animationHitFinished = true;
	}

	public void onButtonsEvent(WiimoteButtonsEvent arg0) {
		if (arg0.isButtonAJustPressed())
			my_parent.isButtonAPressed(arg0.getWiimoteId());

		/*if (arg0.isButtonBPressed())
			System.out.println("Je press B");*/
		if (arg0.isButtonPlusPressed())
		{
			my_parent.setState(GlitchesBattle.PAUSE);
			my_parent.getGameControler().setPauseState(true);			
		}

		if (arg0.isButtonUpJustPressed())
			my_parent.isButtonUpPressed();

		if (arg0.isButtonDownJustPressed())
			my_parent.isButtonDownPressed();

		if (arg0.isButtonLeftJustPressed())
			my_parent.isButtonLeftPressed();

		if (arg0.isButtonRightJustPressed())
			my_parent.isButtonRightPressed();

	}

	public void onIrEvent(IREvent arg0) {}

	public void onMotionSensingEvent(MotionSensingEvent arg0) {
		int x = arg0.getRawAcceleration().getX();
		int y = arg0.getRawAcceleration().getY();
		int z = arg0.getRawAcceleration().getZ();

		//System.out.println(animationHitFinished);
		if(!animationHitFinished)
			return;
		if(my_parent.getState() == GlitchesBattle.IN_GAME && my_parent.getGameControler()!=null && !my_parent.getGameControler().isDecompting() && (90<x && x<100 || 90<y && y<100 || 90<z && z<100) ){
			my_parent.hit();
		}
	}

	public void onExpansionEvent(ExpansionEvent arg0) {
		//System.out.println(arg0);
		try {
			String line_angle = arg0.toString().split("\n")[7];
			String value = line_angle.split(" ")[3];
			
			String line_magnitude = arg0.toString().split("\n")[8];
			String value_m = line_magnitude.split(" ")[3];
			//System.out.println(value);
			if (value.equals("NaN"))
				value = "0.0";
			if (value_m.equals("NaN"))
				value = "0.0";
			my_parent.setAngle_and_Maginitude(Double.parseDouble(value), Double.parseDouble(value_m));
		} catch (Exception e) {}
	}

	public void onStatusEvent(StatusEvent arg0) {
		try {
			System.out.println(arg0);
			String line_state = arg0.toString().split("\n")[1];
			String value = line_state.split(" : ")[1];
			
			System.out.println("----- "+value);
			
			if(value.equals("false"))
			{
				if(my_parent.getState() == GlitchesBattle.IN_GAME)
					my_parent.getGameControler().setPauseState(true);
				else
					my_parent.initAll();
			}
		} catch (Exception e) {}
	}

	public void onDisconnectionEvent(DisconnectionEvent arg0) {
		System.out.println("banana");
	}

	public void onNunchukInsertedEvent(NunchukInsertedEvent arg0) {}

	public void onNunchukRemovedEvent(NunchukRemovedEvent arg0) {}

	@Override
	public void onClassicControllerInsertedEvent(ClassicControllerInsertedEvent arg0) {}

	@Override
	public void onClassicControllerRemovedEvent(ClassicControllerRemovedEvent arg0) {}

	@Override
	public void onGuitarHeroInsertedEvent(GuitarHeroInsertedEvent arg0) {}

	@Override
	public void onGuitarHeroRemovedEvent(GuitarHeroRemovedEvent arg0) {}

	public void setAnimationHitState(boolean b) {
		animationHitFinished = b;

	}
}
