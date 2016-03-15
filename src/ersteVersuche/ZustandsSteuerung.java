package ersteVersuche;

import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;

public class ZustandsSteuerung {
	public static int zustandDesEinparkens = -1;
	public static EV3UltrasonicSensor ultraschallSensorVorne = new EV3UltrasonicSensor(SensorPort.S1);
	// public static EV3UltrasonicSensor ultraschallSensorHinten = new
	// EV3UltrasonicSensor(SensorPort.S2);
	public static SampleProvider seitenAbstandNachRechts = ultraschallSensorVorne.getDistanceMode();
	// public static SampleProvider abstandNachHinten =
	// ultraschallSensorHinten.getDistanceMode();

	public static EV3TouchSensor touchSensorOben = new EV3TouchSensor(SensorPort.S3);
	public static SampleProvider einparkVorgangAusloeser = touchSensorOben.getTouchMode();

	public static RegulatedMotor motorLenkung = new EV3MediumRegulatedMotor(MotorPort.A);
	public static RegulatedMotor motorAntrieb = new EV3LargeRegulatedMotor(MotorPort.B);

	static float parklueckenBeginn = 0;
	static float parklueckenEnde = 0;

	static float[] touchSensorObenMesswerte = new float[einparkVorgangAusloeser.sampleSize()];
	static float[] seitenAbstandNachRechtsMesswerte = new float[seitenAbstandNachRechts.sampleSize()];
	
	static float abstandNachRechtsZuBeginnDesEinparkens = 0;	
	static double breiteDesAutos = 0.195;
	
	static double streckeBiszurHaelfteInDerParkluecke;
	static double laengeDesAutos = 0.225;
	
	static double streckeProReifenUmdrehung = 0.1728;

	public static void main(String[] args) {

		while (Button.ESCAPE.isUp()) {
			fahreVorwaertsUndWarteAufEinparksignal();
			if(zustandDesEinparkens != -1){
				motorAntrieb.flt();
				parkeEin();
			}
		}
	}

	private static void parkeEin() {

		berechneEinparkLaenge();
		System.out.println("in einparken");
		motorAntrieb.setSpeed(100);
		motorAntrieb.setAcceleration(150);
		// hier Beberechnungen anhand des Abstands anpassen!
		int gradFuerMotor = (int) (streckeBiszurHaelfteInDerParkluecke/streckeProReifenUmdrehung)*360;
		motorAntrieb.rotate(-450);
		motorLenkung.rotate(130);
		motorAntrieb.rotate(gradFuerMotor);

		motorLenkung.rotate(-260);
		motorAntrieb.rotate(gradFuerMotor);

		motorLenkung.rotate(130);

		System.exit(0);
	}

	private static void wechselZustand(int zustand, float a) {

		zustandDesEinparkens = zustand;
		parklueckenBeginn = a;
	}

	private static void fahreVorwaertsUndWarteAufEinparksignal() {
		motorAntrieb.setAcceleration(150);
		motorAntrieb.setSpeed(200);
		motorAntrieb.backward();

		einparkVorgangAusloeser.fetchSample(touchSensorObenMesswerte, 0);
		if (touchSensorObenMesswerte[0] == 1 && zustandDesEinparkens == -1) {
			wechselZustand(0, -motorAntrieb.getTachoCount());
			Button.LEDPattern(5);
			sucheParkluecke();
		}
	}
	
	private static void berechneEinparkLaenge (){
		
		streckeBiszurHaelfteInDerParkluecke = (Math.asin(((breiteDesAutos+abstandNachRechtsZuBeginnDesEinparkens)/2)*(Math.cos(45)/laengeDesAutos) - 1) + (Math.PI/2))*laengeDesAutos/Math.cos(45);
	}

	private static void sucheParkluecke() {
		while (zustandDesEinparkens != 3) {

			if (zustandDesEinparkens == 0) {
				
				seitenAbstandNachRechts.fetchSample(seitenAbstandNachRechtsMesswerte, 0);

				if (seitenAbstandNachRechtsMesswerte[0] < 0.15 && seitenAbstandNachRechtsMesswerte[0] != Float.NaN) {
					wechselZustand(1, -motorAntrieb.getTachoCount());
				}
				if (seitenAbstandNachRechtsMesswerte[0] > 0.15 | seitenAbstandNachRechtsMesswerte[0] == Float.NaN) {
					wechselZustand(2, -motorAntrieb.getTachoCount());

				}

			}
			if (zustandDesEinparkens == 1) {
				seitenAbstandNachRechts.fetchSample(seitenAbstandNachRechtsMesswerte, 0);

				if (seitenAbstandNachRechtsMesswerte[0] > 0.15 | seitenAbstandNachRechtsMesswerte[0] == Float.NaN) {
					wechselZustand(2, -motorAntrieb.getTachoCount());

				}
			}
			if (zustandDesEinparkens == 2) {
				seitenAbstandNachRechts.fetchSample(seitenAbstandNachRechtsMesswerte, 0);

				if (seitenAbstandNachRechtsMesswerte[0] < 0.15 && seitenAbstandNachRechtsMesswerte[0] != Float.NaN) {
					parklueckenEnde = -motorAntrieb.getTachoCount();

					if ((parklueckenEnde - parklueckenBeginn) > 540) {
						wechselZustand(3, -motorAntrieb.getTachoCount());
					} else {
						wechselZustand(1, -motorAntrieb.getTachoCount());
					}
				}
			}
			if (zustandDesEinparkens == 3) {
				seitenAbstandNachRechts.fetchSample(seitenAbstandNachRechtsMesswerte, 0);
				motorAntrieb.setAcceleration(6000);
				abstandNachRechtsZuBeginnDesEinparkens = seitenAbstandNachRechtsMesswerte[0];

			}
		}
	}

}
