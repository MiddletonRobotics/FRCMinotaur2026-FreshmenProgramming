package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.SensorVelocityMeasPeriod;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {
    private WPI_TalonSRX[] leftMotors;
    private WPI_TalonSRX[] rightMotors;

    private DifferentialDrive drivetrain;

    public Drivetrain() {
        leftMotors = new WPI_TalonSRX[2];
        leftMotors[0] = new WPI_TalonSRX(1);
        leftMotors[1] = new WPI_TalonSRX(2);

        rightMotors = new WPI_TalonSRX[2];
        rightMotors[0] = new WPI_TalonSRX(3);
        rightMotors[1] = new WPI_TalonSRX(4);

        for (int i = 0; i < 2; i++) {
            leftMotors[i].setInverted(true);
            leftMotors[i].setNeutralMode(NeutralMode.Coast);
            leftMotors[i].configSelectedFeedbackSensor(FeedbackDevice.None);
            leftMotors[i].setSelectedSensorPosition(0);
            leftMotors[i].configVelocityMeasurementWindow(1);
            leftMotors[i].configVelocityMeasurementPeriod(SensorVelocityMeasPeriod.Period_1Ms);

            rightMotors[i].setInverted(false);
            rightMotors[i].setNeutralMode(NeutralMode.Coast);
            rightMotors[i].configSelectedFeedbackSensor(FeedbackDevice.None);
            rightMotors[i].setSelectedSensorPosition(0);
            rightMotors[i].configVelocityMeasurementWindow(1);
            rightMotors[i].configVelocityMeasurementPeriod(SensorVelocityMeasPeriod.Period_1Ms);

            if (i != 0) {
                leftMotors[i].follow(leftMotors[0]);
                rightMotors[i].follow(rightMotors[0]);
            }
        }

        drivetrain = new DifferentialDrive(leftMotors[0], rightMotors[0]);
        drivetrain.setSafetyEnabled(true);
    }

    @Override
    public void periodic() {

    }

    public void arcadeDrive(double linearSpeed, double rotationalSpeed) {
        double linearMagnitude = MathUtil.applyDeadband(linearSpeed, 0.1);
        double thetaMagnitude = MathUtil.applyDeadband(rotationalSpeed, 0.1);

        drivetrain.arcadeDrive(linearMagnitude, thetaMagnitude);
    }

    public void stopDrive() {
        drivetrain.stopMotor();
    }

    public WPI_TalonSRX getLeftLeader() {
        return leftMotors[0];
    }

    public WPI_TalonSRX getRightLeader() {
        return rightMotors[0];
    }
}
