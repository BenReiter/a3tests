package a3test;

import a3.*;

import static org.junit.Test.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;

import org.junit.Test;

public class A3AdeptTests {
	// Initialize different pixel amounts.
	Pixel red = new ColorPixel(1, 0, 0);
	Pixel green = new ColorPixel(0, 1, 0);
	Pixel blue = new ColorPixel(0, 0, 1);
	Pixel orange = new ColorPixel(0.9, 0.6, 0.1);
	Pixel yellow = new ColorPixel(0.9, 1, 0.1);
	Pixel randomColor = new ColorPixel(0.545, 0.65, 0.332);

	// Valid Pixel 2d Arrays
	Pixel[][] rgbPicture = { { red, red, red }, { green, green, green }, { blue, blue, blue } };
	Pixel[][] randomPicture = { { blue, randomColor, randomColor, yellow }, { green, red, green, randomColor } };
	// Invalid Pixel 2d Arrays
	Pixel[][] noWidthPicture = { {}, {} };
	Pixel[][] noHeightPicture = { {} };
	Pixel[][] differentHeightPicture = { { red, blue, green }, { red, green, blue }, { red, green } };
	Pixel[][] includesNullRowsPicture = { { red, red }, null, { blue, blue } };
	Pixel[][] includesNullPixelsPicture = { { randomColor, blue, red, blue }, { green, green, randomColor, blue },
			{ blue, blue, randomColor, null } };

	// Valid Immutable Pixel Array Picture initializations.
	Picture redImmutablePicture = new ImmutablePixelArrayPicture(4, 4, red);
	Picture blueImmutablePicture = new ImmutablePixelArrayPicture(3, 3, blue);
	Picture randomColorImmutablePicture = new ImmutablePixelArrayPicture(2, 2, randomColor);
	Picture rgbImmutablePicture = new ImmutablePixelArrayPicture(rgbPicture);
	Picture randomImmutablePicture = new ImmutablePixelArrayPicture(randomPicture);

	// Valid Gradient Picture initializations.
	Picture yellowGradientPicture = new GradientPicture(2, 2, yellow, yellow, yellow, yellow);
	Picture randomGradientPicture = new GradientPicture(3, 4, randomColor, blue, green, red);
	Picture blueYellowGradientPicture = new GradientPicture(2, 10, blue, yellow, blue, yellow);
	Picture miamiGradientPicture = new GradientPicture(5, 100, orange, green, green, orange);
	Picture redGradientPicture = new GradientPicture(3, 7, red, red, red, red);

	// Valid Horizontal Stack Picture initializations.
	Picture yellowGradientHorizontalStackPicture = new HorizontalStackPicture(yellowGradientPicture,
			yellowGradientPicture);
	Picture mixedComboHorizontalStackPicture = new HorizontalStackPicture(redImmutablePicture, randomGradientPicture);
	Picture mixedRandomHorizontalStackPicture = new HorizontalStackPicture(redImmutablePicture, randomImmutablePicture);

	// Valid Vertical Stack Picture initializations.
	Picture yellowGradientVerticalStackPicture = new VerticalStackPicture(yellowGradientPicture, yellowGradientPicture);
	Picture mixedComboVerticalStackPicture = new VerticalStackPicture(rgbImmutablePicture, blueImmutablePicture);
	Picture mixedRandomVerticalStackPicture = new VerticalStackPicture(yellowGradientPicture,
			blueYellowGradientPicture);

	@Test
	public void testImmutableArrayPictureConstructor2dPixelArray() {
		try {
			Picture invalidNullPixelArray = new ImmutablePixelArrayPicture(null);
			fail("Pixel 2d array cannot be null");
		} catch (IllegalArgumentException e) {
		}

		try {
			Picture noWidth2dPixelsArray = new ImmutablePixelArrayPicture(noWidthPicture);
			fail("Pixel array has illegal geometry.");
		} catch (IllegalArgumentException e) {
		}

		try {
			Picture noHeight2dPixelsArray = new ImmutablePixelArrayPicture(noHeightPicture);
			fail("Pixel array has illegal geometry.");
		} catch (IllegalArgumentException e) {
		}
		try {
			Picture differentHeight2dPixelsArray = new ImmutablePixelArrayPicture(differentHeightPicture);
			fail("Columns in picture are not all the same height.");
		} catch (IllegalArgumentException e) {
		}
		try {
			Picture invalidNullRow2dPixelArray = new ImmutablePixelArrayPicture(includesNullRowsPicture);
			fail("Pixel array includes null rows.");
		} catch (IllegalArgumentException e) {
		}
		try {
			Picture invalidNullPixels2dPixelArray = new ImmutablePixelArrayPicture(includesNullPixelsPicture);
			fail("Pixel array includes null pixels.");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void testImmutableArrayPictureConstructorGeometry() {
		try {
			Picture invalidImmutablePictureWidth = new ImmutablePixelArrayPicture(0, 2, red);
			fail("Width is illegal.");
		} catch (IllegalArgumentException e) {
		}
		try {
			Picture invalidImmutablePictureHeight = new ImmutablePixelArrayPicture(2, 0, red);
			fail("Height is illegal.");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void testImmutableArrayPictureGetters() {
		assertEquals(4, redImmutablePicture.getWidth());
		assertEquals(4, redImmutablePicture.getHeight());
		assertEquals(red, redImmutablePicture.getPixel(2, 3));

		assertEquals(3, blueImmutablePicture.getWidth());
		assertEquals(3, blueImmutablePicture.getHeight());
		assertEquals(blue, blueImmutablePicture.getPixel(0, 2));

		assertEquals(2, randomColorImmutablePicture.getWidth());
		assertEquals(2, randomColorImmutablePicture.getHeight());
		assertEquals(randomColor, randomColorImmutablePicture.getPixel(0, 1));

		assertEquals(3, rgbImmutablePicture.getWidth());
		assertEquals(3, rgbImmutablePicture.getHeight());
		assertEquals(green, rgbImmutablePicture.getPixel(1, 1));

		assertEquals(2, randomImmutablePicture.getWidth());
		assertEquals(4, randomImmutablePicture.getHeight());
		assertEquals(randomColor, randomImmutablePicture.getPixel(1, 3));
	}

	@Test
	public void testImmutableArrayPicturePaintMethod() {
		try {
			redImmutablePicture.paint(1, 1, null, 1.0);
			fail("Null pixel.");
		} catch (IllegalArgumentException e) {
		}
		try {
			redImmutablePicture.paint(-1, 1, red, 1.0);
			fail("Illegal x.");
		} catch (IllegalArgumentException e) {
		}
		try {
			redImmutablePicture.paint(redImmutablePicture.getWidth(), 1, red, 0.5);
			fail("Illegal x.");
		} catch (IllegalArgumentException e) {
		}
		try {
			blueImmutablePicture.paint(1, -1, blue, 0.5);
			fail("Illegal y.");
		} catch (IllegalArgumentException e) {
		}
		try {
			blueImmutablePicture.paint(1, blueImmutablePicture.getHeight(), blue, 0.5);
			fail("Illegal y.");
		} catch (IllegalArgumentException e) {
		}
		try {
			rgbImmutablePicture.paint(1, 1, green, 1.5);
			fail("Illegal factor.");
		} catch (IllegalArgumentException e) {
		}
		try {
			rgbImmutablePicture.paint(1, 1, green, -1);
			fail("Illegal factor.");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void testGradientPictureConstructor() {
		try {
			Picture invalidGradientPictureWidth = new GradientPicture(0, 2, yellow, red, blue, green);
			fail("Width is illegal.");
		} catch (IllegalArgumentException e) {
		}
		try {
			Picture invalidGradientPictureHeight = new GradientPicture(3, 0, yellow, red, blue, green);
			fail("Height is illegal.");
		} catch (IllegalArgumentException e) {
		}
		try {
			Picture nullPixelsGradientPicture = new GradientPicture(3, 3, null, null, null, null);
			fail("Null value for at least one pixel.");
		} catch (IllegalArgumentException e) {
		}
		try {
			Picture lowerRightNullPixelGradientPicture = new GradientPicture(3, 3, red, green, blue, null);
			fail("Null value for at least one pixel.");
		} catch (IllegalArgumentException e) {
		}
		try {
			Picture twoNullPixelsGradientPicture = new GradientPicture(3, 3, null, green, green, null);
			fail("Null value for at least one pixel.");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void testGradientPictureGetters() {
		assertEquals(2, yellowGradientPicture.getWidth());
		assertEquals(2, yellowGradientPicture.getHeight());

		assertEquals(2, blueYellowGradientPicture.getWidth());
		assertEquals(10, blueYellowGradientPicture.getHeight());

		assertEquals(3, randomGradientPicture.getWidth());
		assertEquals(4, randomGradientPicture.getHeight());

		assertEquals(3, redGradientPicture.getWidth());
		assertEquals(7, redGradientPicture.getHeight());

		assertEquals(5, miamiGradientPicture.getWidth());
		assertEquals(100, miamiGradientPicture.getHeight());
	}

	@Test
	public void testGradientPicturePaintMethod() {
		try {
			redGradientPicture.paint(1, 1, null, 1.0);
			fail("Null pixel.");
		} catch (IllegalArgumentException e) {
		}
		try {
			redGradientPicture.paint(-1, 1, red, 1.0);
			fail("Illegal x.");
		} catch (IllegalArgumentException e) {
		}
		try {
			redGradientPicture.paint(redGradientPicture.getWidth(), 1, red, 0.5);
			fail("Illegal x.");
		} catch (IllegalArgumentException e) {
		}
		try {
			blueYellowGradientPicture.paint(1, -1, blue, 0.5);
			fail("Illegal y.");
		} catch (IllegalArgumentException e) {
		}
		try {
			blueYellowGradientPicture.paint(1, blueYellowGradientPicture.getHeight(), blue, 0.5);
			fail("Illegal y.");
		} catch (IllegalArgumentException e) {
		}
		try {
			miamiGradientPicture.paint(1, 1, green, 1.5);
			fail("Illegal factor.");
		} catch (IllegalArgumentException e) {
		}
		try {
			miamiGradientPicture.paint(1, 1, green, -1);
			fail("Illegal factor.");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void testHorizontalStackPictureConstructor() {
		try {
			Picture invalidHorizontalStackPictureNullValues = new HorizontalStackPicture(null, null);
			fail("Left or right is null.");
		} catch (IllegalArgumentException e) {
		}
		try {
			Picture invalidHorizontalStackPictureNullValueLeft = new HorizontalStackPicture(null,
					yellowGradientPicture);
			fail("Left or right is null.");
		} catch (IllegalArgumentException e) {
		}
		try {
			Picture invalidHorizontalStackPictureNullValueRight = new HorizontalStackPicture(redImmutablePicture, null);
			fail("Left or right is null.");
		} catch (IllegalArgumentException e) {
		}
		try {
			Picture invalidHorizontalStackPictureNullValueRight = new HorizontalStackPicture(redImmutablePicture,
					yellowGradientPicture);
			fail("Left and right are not the same height.");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void testHorizontalStackPictureGetters() {
		assertEquals(yellowGradientPicture.getWidth() * 2, yellowGradientHorizontalStackPicture.getWidth());
		assertEquals(yellowGradientPicture.getHeight(), yellowGradientHorizontalStackPicture.getHeight());

		assertEquals(redImmutablePicture.getWidth() + randomGradientPicture.getWidth(),
				mixedComboHorizontalStackPicture.getWidth());
		assertEquals(redImmutablePicture.getHeight(), mixedComboHorizontalStackPicture.getHeight());

		assertEquals(redImmutablePicture.getWidth() + randomImmutablePicture.getWidth(),
				mixedRandomHorizontalStackPicture.getWidth());
		assertEquals(randomImmutablePicture.getHeight(), mixedRandomHorizontalStackPicture.getHeight());
	}

	@Test
	public void testHorizontalStackPicturePaintMethod() {
		try {
			yellowGradientHorizontalStackPicture.paint(1, 1, null, 1.0);
			fail("Null pixel.");
		} catch (IllegalArgumentException e) {
		}
		try {
			yellowGradientHorizontalStackPicture.paint(-1, 1, red, 1.0);
			fail("Illegal x.");
		} catch (IllegalArgumentException e) {
		}
		try {
			mixedComboHorizontalStackPicture.paint(mixedComboHorizontalStackPicture.getWidth(), 1, red, 0.5);
			fail("Illegal x.");
		} catch (IllegalArgumentException e) {
		}
		try {
			mixedComboHorizontalStackPicture.paint(1, -1, blue, 0.5);
			fail("Illegal y.");
		} catch (IllegalArgumentException e) {
		}
		try {
			mixedComboHorizontalStackPicture.paint(1, blueYellowGradientPicture.getHeight(), blue, 0.5);
			fail("Illegal y.");
		} catch (IllegalArgumentException e) {
		}
		try {
			mixedRandomHorizontalStackPicture.paint(1, 1, green, 1.5);
			fail("Illegal factor.");
		} catch (IllegalArgumentException e) {
		}
		try {
			mixedRandomHorizontalStackPicture.paint(1, 1, green, -1);
			fail("Illegal factor.");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void testVerticalStackPictureConstructor() {
		try {
			Picture invalidVerticalStackPictureNullValues = new VerticalStackPicture(null, null);
			fail("Left or right is null.");
		} catch (IllegalArgumentException e) {
		}
		try {
			Picture invalidVerticalStackPictureNullValueLeft = new VerticalStackPicture(null, yellowGradientPicture);
			fail("Left or right is null.");
		} catch (IllegalArgumentException e) {
		}
		try {
			Picture invalidVerticalStackPictureNullValueRight = new VerticalStackPicture(redImmutablePicture, null);
			fail("Left or right is null.");
		} catch (IllegalArgumentException e) {
		}
		try {
			Picture invalidVerticalStackPictureNullValueRight = new VerticalStackPicture(redImmutablePicture,
					yellowGradientPicture);
			fail("Left and right are not the same height.");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void testVerticalStackPictureGetters() {
		assertEquals(yellowGradientPicture.getWidth(), yellowGradientVerticalStackPicture.getWidth());
		assertEquals(yellowGradientPicture.getHeight() * 2, yellowGradientVerticalStackPicture.getHeight());

		assertEquals(rgbImmutablePicture.getWidth(), mixedComboVerticalStackPicture.getWidth());
		assertEquals(rgbImmutablePicture.getHeight() + blueImmutablePicture.getHeight(),
				mixedComboVerticalStackPicture.getHeight());

		assertEquals(yellowGradientPicture.getWidth(), mixedRandomVerticalStackPicture.getWidth());
		assertEquals(yellowGradientPicture.getHeight() + blueYellowGradientPicture.getHeight(),
				mixedRandomVerticalStackPicture.getHeight());
	}

	@Test
	public void testVerticalStackPicturePaintMethod() {
		try {
			yellowGradientVerticalStackPicture.paint(1, 1, null, 1.0);
			fail("Null pixel.");
		} catch (IllegalArgumentException e) {
		}
		try {
			yellowGradientVerticalStackPicture.paint(-1, 1, red, 1.0);
			fail("Illegal x.");
		} catch (IllegalArgumentException e) {
		}
		try {
			mixedComboVerticalStackPicture.paint(mixedComboVerticalStackPicture.getWidth(), 1, red, 0.5);
			fail("Illegal x.");
		} catch (IllegalArgumentException e) {
		}
		try {
			mixedComboVerticalStackPicture.paint(1, -1, blue, 0.5);
			fail("Illegal y.");
		} catch (IllegalArgumentException e) {
		}
		try {
			mixedComboVerticalStackPicture.paint(1, blueYellowGradientPicture.getHeight(), blue, 0.5);
			fail("Illegal y.");
		} catch (IllegalArgumentException e) {
		}
		try {
			mixedRandomVerticalStackPicture.paint(1, 1, green, 1.5);
			fail("Illegal factor.");
		} catch (IllegalArgumentException e) {
		}
		try {
			mixedRandomVerticalStackPicture.paint(1, 1, green, -1);
			fail("Illegal factor.");
		} catch (IllegalArgumentException e) {
		}
	}
}
