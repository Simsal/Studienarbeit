package ersteVersuche;

import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class Tests {
//	public static EV3UltrasonicSensor ultrasonicsensor_vorne = new EV3UltrasonicSensor(SensorPort.S1);
//	public static EV3UltrasonicSensor ultrasonicsensor_hinten = new EV3UltrasonicSensor(SensorPort.S2);
//	public static SampleProvider distanceMode = ultrasonicsensor_vorne.getDistanceMode();
//	public static SampleProvider distanceMode_hinten = ultrasonicsensor_hinten.getDistanceMode();

	public static RegulatedMotor motorLenkung = new EV3MediumRegulatedMotor(MotorPort.A);
	public static RegulatedMotor motorAntrieb = new EV3LargeRegulatedMotor(MotorPort.B);
	
	public static int counter = 0;

	public static void main(String[] args) throws InterruptedException {

		
		while (Button.ESCAPE.isUp()) {
			motorLenkung.rotate(130);

			motorLenkung.rotate(-200);

			motorLenkung.rotate(100);
			
			

//			if(Button.DOWN.isDown()){
//				parkeEin();
//			}
			//68, 66,5
////
//			float[] sample = new float[distanceMode.sampleSize()];
//			distanceMode.fetchSample(sample, 0);
//			System.out.println(sample[0]);
//			
//			if (sample[0] < 0.5 && sample[0] != Float.NaN) {
//				System.out.println("Sensor erkennt");
//				counter = bewerteParkluecke(motorAntrieb.getTachoCount());
//				if(counter == 2){
//					
//					parkeEin();
//				}
//			}
		}
	}

	private static void normaleFahrt() {

		motorAntrieb.backward();
	}

//	public static void parkeEin() {
//		//
////		motorAntrieb.rotate(-420);
////		Delay.msDelay(200);
//		// motorAntrieb.setSpeed(10);
//		motorAntrieb.forward();
//		motorLenkung.rotate(100);
//		Delay.msDelay(1563);
//		motorLenkung.rotate(-200);
//		Delay.msDelay(1563);
//		motorLenkung.rotate(100);
//		motorAntrieb.flt();
////		motorAntrieb.close();
////		System.exit(0);
//	}

//	public static int bewerteParkluecke(int parklueckenStart) {
//		System.out.println("parklueckenstart"+parklueckenStart);
//		float[] sample = new float[distanceMode.sampleSize()];
//		distanceMode.fetchSample(sample, 0);
//		
//		if (sample[0] < 0.5 && sample[0] != Float.NaN){
//			int parklueckenEnde = motorAntrieb.getTachoCount();
//			System.out.println("parklueckenEnde"+parklueckenEnde);
//			if((parklueckenEnde-parklueckenStart)>200){
//				return 2;
//			}
//			else {
//				return 1;
//			}
//		}
//		return 1;
//	}
//
}

// if(Button.LEFT.isDown()){
// float[] sample = new float[distanceMode.sampleSize()];
// distanceMode.fetchSample(sample, 0);
// System.out.println(sample[0]);
// }
//
// if(Button.RIGHT.isDown()){
// float[] sample = new float[distanceMode_hinten.sampleSize()];
// distanceMode_hinten.fetchSample(sample, 0);
// System.out.println(sample[0]);
// }

// Delay.msDelay(5000);
// motorLenkung.rotate(100);
// Delay.msDelay(470);
// motorLenkung.rotate(-200);
// Delay.msDelay(470);
// motorLenkung.rotate(100);
// Delay.msDelay(5000);
// System.out.println(motorAntrieb.getRotationSpeed());
// motorAntrieb.flt();
// float[] sample = new float[distanceMode.sampleSize()];
// distanceMode.fetchSample(sample, 0);
// System.out.printf("%.5f m\n", sample[0]);
// LCD.drawString("Plugin Test", 0, 4);
// Delay.msDelay(5000);
