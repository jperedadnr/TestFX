/*
 * Copyright 2013-2014 SmartBear Software
 * Copyright 2014-2018 The TestFX Contributors
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by the
 * European Commission - subsequent versions of the EUPL (the "Licence"); You may
 * not use this work except in compliance with the Licence.
 *
 * You may obtain a copy of the Licence at:
 * http://ec.europa.eu/idabc/eupl.html
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the Licence is distributed on an "AS IS" basis, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the Licence for the
 * specific language governing permissions and limitations under the Licence.
 */
package org.testfx.matcher.base;

import javafx.scene.paint.Color;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.testfx.api.FxRobot;
import org.testfx.service.support.impl.PixelMatcherRgb;

import static org.hamcrest.MatcherAssert.assertThat;

public class ColorMatchersTest extends FxRobot {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void isColor() {
        // expect:
        assertThat(Color.color(1, 0, 0), ColorMatchers.isColor(Color.RED));
    }

    @Test
    public void isColor_fails() {
        // expect:
        exception.expect(AssertionError.class);
        exception.expectMessage("Expected: Color has color \"BLACK\" (#000000)\n     " +
                "but: was \"RED\" (#ff0000)");

        assertThat(Color.color(1, 0, 0), ColorMatchers.isColor(Color.BLACK));
    }

    @Test
    public void isColor_colorMatcher() {
        // expect:
        assertThat(Color.color(0.9, 0, 0), ColorMatchers.isColor(Color.RED, new PixelMatcherRgb()));
    }

    @Test
    public void isColor_colorMatcher_fails() {
        // expect:
        exception.expect(AssertionError.class);
        exception.expectMessage("Expected: Color has color \"RED\" (#ff0000)\n     ");

        assertThat(Color.color(0.5, 0, 0), ColorMatchers.isColor(Color.RED, new PixelMatcherRgb(0.01, 0)));
    }

    @Test
    public void isNamedColor() {
        // expect:
        assertThat(Color.AQUAMARINE, ColorMatchers.isColor("AQUAMARINE"));
    }

    @Test
    public void isNamedColor_fails() {
        // expect:
        exception.expect(AssertionError.class);
        exception.expectMessage("Expected: Color is \"AQUAMARINE\"\n     " +
                "but: was \"ANTIQUEWHITE\" (#faebd7)");
        assertThat(Color.ANTIQUEWHITE, ColorMatchers.isColor("AQUAMARINE"));
    }

    @Test
    public void isNamedColor_non_named_color_fails() {
        // expect:
        exception.expect(AssertionError.class);
        exception.expectMessage("given color name: \"BAGEL\" is not a named color\n" +
                "See: https://docs.oracle.com/javase/9/docs/api/javafx/scene/doc-files/cssref.html#typecolor");
        assertThat(Color.web("#f3b2aa"), ColorMatchers.isColor("BAGEL"));
    }

    @Test
    public void hasClosestNamedColor_color() {
        // expect:
        assertThat(Color.color(0.8, 0.2, 0.1), ColorMatchers.hasClosestNamedColor(Color.FIREBRICK));
    }

    @Test
    public void hasClosestNamedColor_color_fails() {
        // expect:
        exception.expect(AssertionError.class);
        exception.expectMessage("Expected: Color has closest named color \"GAINSBORO\" (#dcdcdc)\n     " +
                "but: was \"#fbfbfb\" which has closest named color: \"SNOW\"");
        assertThat(Color.web("#fbfbfb"), ColorMatchers.hasClosestNamedColor(Color.GAINSBORO));
    }

    @Test
    public void hasClosestNamedColor_non_named_color_fails() {
        // expect:
        exception.expect(AssertionError.class);
        exception.expectMessage("given color: \"#acb2f1\" is not a named color\n" +
                "See: https://docs.oracle.com/javase/9/docs/api/javafx/scene/doc-files/cssref.html#typecolor");
        assertThat(Color.web("#dcdcdc"), ColorMatchers.hasClosestNamedColor(Color.web("#acb2f1")));
    }

    @Test
    public void hasClosestNamedColor_string() {
        // expect:
        assertThat(Color.color(0.8, 0.2, 0.1), ColorMatchers.hasClosestNamedColor("FIREBRICK"));
    }

    @Test
    public void hasClosestNamedColor_string_fails() {
        // expect:
        exception.expect(AssertionError.class);
        exception.expectMessage("Expected: Color has closest named color \"GAINSBORO\" (#dcdcdc)\n     " +
                "but: was \"#991a1a\" which has closest named color: \"BROWN\"");
        assertThat(Color.color(0.6, 0.1, 0.1), ColorMatchers.hasClosestNamedColor("GAINSBORO"));
    }

    @Test
    public void hasClosestNamedColor_non_named_color_string_fails() {
        // expect:
        exception.expect(AssertionError.class);
        exception.expectMessage("given color name: \"BETELGEUSE\" is not a named color\n" +
                "See: https://docs.oracle.com/javase/9/docs/api/javafx/scene/doc-files/cssref.html#typecolor");
        assertThat(Color.color(0.6, 0.1, 0.1), ColorMatchers.hasClosestNamedColor("BETELGEUSE"));
    }
}
