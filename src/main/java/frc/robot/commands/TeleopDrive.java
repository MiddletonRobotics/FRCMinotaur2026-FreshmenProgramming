package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.subsystems.Drivetrain;

public class TeleopDrive extends Command {
    private Drivetrain drivetrain;
    private DoubleSupplier throttleSupplier;
    private DoubleSupplier rotationSupplier;

    public TeleopDrive(final Drivetrain drivetrain, DoubleSupplier throttleSupplier, DoubleSupplier rotationSupplier) { 
        this.drivetrain = drivetrain;
        this.throttleSupplier = throttleSupplier;
        this.rotationSupplier = rotationSupplier;

        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        drivetrain.arcadeDrive(throttleSupplier.getAsDouble(), rotationSupplier.getAsDouble());
    }
}
