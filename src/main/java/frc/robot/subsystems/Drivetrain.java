// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {
  public static final class Config {
    public static final int kRightMotorPrimary = 0;
    public static final int kRightMotorSecondary = 0;
    public static final int kLeftMotorPrimary = 0;
    public static final int kLeftMotorSecondary = 0;

    public static final double kWheelDiameter = 6;
    public static final double kTicksPerRotation = 4096;
  }

  private WPI_TalonFX m_rightMotorPrimary = new WPI_TalonFX(Config.kRightMotorPrimary);
  private WPI_TalonFX m_rightMotorSecondary = new WPI_TalonFX(Config.kRightMotorSecondary);
  private WPI_TalonFX m_leftMotorPrimary = new WPI_TalonFX(Config.kLeftMotorPrimary);
  private WPI_TalonFX m_leftMotorSecondary = new WPI_TalonFX(Config.kLeftMotorSecondary);

  private DifferentialDrive m_drive = new DifferentialDrive(m_rightMotorPrimary, m_leftMotorPrimary);

  /** Creates a new Drivetrain. */
  public Drivetrain() {
    m_rightMotorSecondary.follow(m_rightMotorPrimary);
    m_leftMotorSecondary.follow(m_leftMotorPrimary);

    m_leftMotorPrimary.setInverted(true);
    m_leftMotorSecondary.setInverted(true);
  }

  public DifferentialDrive getDrive() {
    return m_drive;
  }

  /**
   * @return Distance travelled in feet.
   */
  public double getDistance() {
    return (m_rightMotorPrimary.getSelectedSensorPosition() * Config.kWheelDiameter * Math.PI)
        / Config.kTicksPerRotation;
  }

  public void resetEncoder() {
    m_rightMotorPrimary.setSelectedSensorPosition(0.0);
  }

  public void setIdleMode(NeutralMode n) {
    m_rightMotorPrimary.setNeutralMode(n);
    m_rightMotorSecondary.setNeutralMode(n);
    m_leftMotorPrimary.setNeutralMode(n);
    m_leftMotorSecondary.setNeutralMode(n);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Drivetrain/encoder", m_rightMotorPrimary.getSelectedSensorPosition());
    SmartDashboard.putNumber("Drivetrain/distance", getDistance());
  }
}
