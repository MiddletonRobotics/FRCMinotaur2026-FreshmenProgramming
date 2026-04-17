// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private final RobotContainer m_robotContainer;

  private WPI_TalonSRX frontLeft;
  private WPI_TalonSRX frontRight;
  private WPI_TalonSRX backLeft;
  private WPI_TalonSRX backRight; 

  private DifferentialDrive drivetrain;
  private XboxController driverController = new XboxController(0);

  private double speedMultiplier = 1.0;

  private double a = 1.0;
  private double b = 2.0;

  public Robot() {
    m_robotContainer = new RobotContainer();

    frontLeft = new WPI_TalonSRX(1);
    frontRight = new WPI_TalonSRX(2);
    backLeft = new WPI_TalonSRX(3);
    backRight = new WPI_TalonSRX(4);

    backLeft.setInverted(true);

    backLeft.follow(frontLeft);
    backRight.follow(frontRight);

    drivetrain = new DifferentialDrive(frontLeft, frontRight);
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void disabledExit() {}

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      CommandScheduler.getInstance().schedule(m_autonomousCommand);
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void autonomousExit() {}

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {
    if (driverController.getAButtonPressed()) {
      speedMultiplier = 0.5;
    } else if (driverController.getBButtonPressed()) {
      speedMultiplier = 1.0;
    }

    double magnitudeX = MathUtil.applyDeadband(driverController.getLeftY(), 0.1);
    double magnitudeY = MathUtil.applyDeadband(driverController.getRightX(), 0.1);

    //drivetrain.arcadeDrive(driverController.getLeftY() * speedMultiplier, driverController.getRightX() * speedMultiplier);
    arcadeDrive(magnitudeX, magnitudeY);
  }

  @Override
  public void teleopExit() {}

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void testExit() {}

  public void arcadeDrive(final double xSpeed, final double zSpeed) {
    drivetrain.arcadeDrive(xSpeed * speedMultiplier, zSpeed * speedMultiplier);
  }
}