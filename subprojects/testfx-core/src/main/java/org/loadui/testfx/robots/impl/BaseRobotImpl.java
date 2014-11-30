/*
 * Copyright 2013-2014 SmartBear Software
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by the European
 * Commission - subsequent versions of the EUPL (the "Licence"); You may not use this work
 * except in compliance with the Licence.
 *
 * You may obtain a copy of the Licence at:
 * http://ec.europa.eu/idabc/eupl
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * Licence is distributed on an "AS IS" basis, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the Licence for the specific language governing permissions
 * and limitations under the Licence.
 */
package org.loadui.testfx.robots.impl;

import java.util.Objects;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

import org.loadui.testfx.robots.BaseRobot;
import org.loadui.testfx.service.adapter.RobotAdapter;
import org.loadui.testfx.service.adapter.impl.AwtRobotAdapter;
import org.loadui.testfx.service.adapter.impl.GlassRobotAdapter;

public class BaseRobotImpl implements BaseRobot {

    //---------------------------------------------------------------------------------------------
    // CONSTANTS.
    //---------------------------------------------------------------------------------------------

    private static final String PROPERTY_TESTFX_ROBOT = "testfx.robot";
    private static final String PROPERTY_TESTFX_ROBOT_AWT = "awt";
    private static final String PROPERTY_TESTFX_ROBOT_GLASS = "glass";

    //---------------------------------------------------------------------------------------------
    // PRIVATE FIELDS.
    //---------------------------------------------------------------------------------------------

    private final String robotAdapterName = System.getProperty(
        PROPERTY_TESTFX_ROBOT, PROPERTY_TESTFX_ROBOT_AWT
    );

    private final RobotAdapter robotAdapter;

    //---------------------------------------------------------------------------------------------
    // CONSTRUCTORS.
    //---------------------------------------------------------------------------------------------

    public BaseRobotImpl() {
        if (isAwtRobotAdapter(robotAdapterName)) {
            robotAdapter = new AwtRobotAdapter();
        }
        else if (isGlassRobotAdapter(robotAdapterName)) {
            robotAdapter = new GlassRobotAdapter();
        }
        else {
            throw new IllegalStateException("Unknown robot adapter " +
                "'" + PROPERTY_TESTFX_ROBOT + "=" + robotAdapterName + "'");
        }
    }

    //---------------------------------------------------------------------------------------------
    // METHODS.
    //---------------------------------------------------------------------------------------------

    @Override
    public void pressKeyboard(KeyCode key) {
        robotAdapter.keyPress(key);
    }

    @Override
    public void releaseKeyboard(KeyCode key) {
        robotAdapter.keyRelease(key);
    }

    @Override
    public Point2D retrieveMouse() {
        return robotAdapter.getMouseLocation();
    }

    @Override
    public void moveMouse(Point2D point) {
        robotAdapter.mouseMove(point);
    }

    @Override
    public void scrollMouse(int amount) {
        robotAdapter.mouseWheel(amount);
    }

    @Override
    public void pressMouse(MouseButton button) {
        robotAdapter.mousePress(button);
    }

    @Override
    public void releaseMouse(MouseButton button) {
        robotAdapter.mouseRelease(button);
    }

    @Override
    public Image captureRegion(Rectangle2D region) {
        return robotAdapter.getCaptureRegion(region);
    }

    @Override
    public void awaitEvents() {
        robotAdapter.timerWaitForIdle();
    }

    //---------------------------------------------------------------------------------------------
    // PRIVATE METHODS.
    //---------------------------------------------------------------------------------------------

    private boolean isAwtRobotAdapter(String robotAdapterName) {
        return Objects.equals(robotAdapterName, PROPERTY_TESTFX_ROBOT_AWT);
    }

    private boolean isGlassRobotAdapter(String robotAdapterName) {
        return Objects.equals(robotAdapterName, PROPERTY_TESTFX_ROBOT_GLASS);
    }

}