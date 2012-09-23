package agabrown.fractalexplorer.colours;

import java.awt.Color;
import java.util.EnumSet;

/**
 * Enum that provides colour lookup tables for converting mapped image values (i.e. mapped to the
 * range [0,1]) to java.awt.Colour instances.
 * 
 * <pre>
 * Code cribbed from {@code agabrown.agabplot} package.
 * </pre>
 * 
 * @author agabrown 18 Jul 2012
 * 
 */
public enum ColourLuts {

  /**
   * Simple Gray scale, black to white
   */
  GREYSCALE("Black to white colour table") {
    @Override
    public Color getColour(final double val) {
      return new Color((float) val, (float) val, (float) val);
    }
  },
  /**
   * IDL's Rainbow colour look-up table
   */
  IDL_RAINBOW("IDL's Rainbow colour table") {
    @Override
    public Color getColour(final double val) {
      float r, g, b;

      if (val <= 0.11) {
        r = (float) (0.486 - val / 0.11 * 0.486);
      } else if (val <= 0.56) {
        r = 0.0f;
      } else if (val <= 0.78) {
        r = (float) ((val - 0.56) / (0.78 - 0.56));
      } else {
        r = 1.0f;
      }

      if (val <= 0.11) {
        g = 0.0f;
      } else if (val <= 0.34) {
        g = (float) ((val - 0.11) / (0.34 - 0.11));
      } else if (val <= 0.78) {
        g = 1.0f;
      } else {
        g = (float) (1.0 - (val - 0.78) / (1.0 - 0.78));
      }

      if (val <= 0.33) {
        b = 1.0f;
      } else if (val <= 0.56) {
        b = (float) (1.0 - (val - 0.33) / (0.56 - 0.33));
      } else {
        b = 0.0f;
      }

      return new Color(r, g, b);
    }
  },
  /**
   * Colour look-up table used in Hipparcos Catalogue
   */
  HIPPARCOS("Colour look-up table used in Hipparcos Catalogue") {
    @Override
    public Color getColour(final double val) {
      Color c;
      if (val <= 0.1) {
        c = Color.getHSBColor(2.0f / 3.0f, 1.0f, (float) (0.5 * (1.0 + val / 0.1)));
      } else {
        c = Color.getHSBColor((float) (2.0 / 3.0 * (1.0 - val) / 0.9), 1.0f, 1.0f);
      }
      return c;
    }
  },
  /**
   * Colour look-up table used in Hipparcos Catalogue: top colour is white
   */
  HIPPARCOS_TOP_WHITE("Colour look-up table used in Hipparcos Catalogue: top colour is white") {
    @Override
    public Color getColour(final double val) {
      Color c;
      if (val <= 0.1) {
        c = Color.getHSBColor(2.0f / 3.0f, 1.0f, (float) (0.5 * (1.0 + val / 0.1)));
      } else if (val == 1.0) {
        c = Color.white;
      } else {
        c = Color.getHSBColor((float) (2.0 / 3.0 * (1.0 - val) / 0.9), 1.0f, 1.0f);
      }
      return c;
    }
  },
  /**
   * IDL's prism white colour look-up table
   */
  PRISM_WHITE("Variation on IDL's PRISM colour look-up table") {
    @Override
    public Color getColour(final double val) {
      Color c;
      if (val <= 0.25) {
        c = new Color((float) (val / 0.25), 0.0f, 0.0f);
      } else if (val > 0.25 && val <= 0.50) {
        c = new Color((float) ((0.5 - val) / 0.25), (float) (1.0 - (0.5 - val) / 0.25), 0.0f);
      } else if (val > 0.50 && val <= 0.75) {
        c = new Color(0.0f, (float) ((0.75 - val) / 0.25), (float) (1.0 - (0.75 - val) / 0.25));
      } else {
        c = new Color((float) (1.0 - (1.0 - val) / 0.25), (float) (1.0 - (1.0 - val) / 0.25), 1.0f);
      }
      return c;
    }
  },
  /**
   * Rainbow scale in steps
   */
  RAINBOW_HSB("HSB-interpolated rainbow colour table") {
    @Override
    public Color getColour(final double val) {
      return Color.getHSBColor((float) (5.0 / 6.0 * (1.0 - val)), 1.0f, 1.0f);
    }
  },
  /**
   * HSB defined rainbow scale in steps
   */
  RAINBOW_HSB_STEP("HSB-interpolated rainbow colour table in steps") {
    @Override
    public Color getColour(final double val) {
      return Color.getHSBColor((float) (5.0 / 6.0 * Math.floor((1.0 - val) * 8.0) / 7.0), 1.0f, 1.0f);
    }
  },
  /**
   * IDL's BLUE/WHITE lut
   */
  IDL_BLUE_WHITE("IDL's BLUE/WHITE colour table") {
    @Override
    public Color getColour(final double val) {
      float r, g, b;
      final double RKNOT = 192.0 / 255.0;
      final double BKNOT = 188.0 / 255.0;
      final double GKNOT = 96.0 / 255.0;
      if (val <= RKNOT) {
        r = 0.0f;
      } else {
        r = (float) ((val - RKNOT) / (1.0 - RKNOT));
      }
      g = (float) val;
      if (val <= GKNOT) {
        g = 0.0f;
      } else {
        g = (float) ((val - GKNOT) / (1.0 - GKNOT));
      }
      if (val <= BKNOT) {
        b = (float) (val / BKNOT);
      } else {
        b = 1.0f;
      }
      return new Color(r, g, b);
    }
  },
  /**
   * IDL's GRN-RED-BLU-WHT colour table
   */
  IDL_GRN_RED_BLU_WHT("IDL's GRN-RED-BLU-WHT colour table") {
    @Override
    public Color getColour(final double val) {
      float r, g, b;

      if (val < 0.113725) {
        r = 0.0f;
      } else if (val >= 0.113725 && val < 0.305882) {
        r = (float) ((val - 0.113725) / (0.305882 - 0.113725) * 250.0 / 255.0);
      } else if (val >= 0.305882 && val < 0.862745) {
        r = (float) (250.0 / 255.0 - ((val - 0.305882) / (0.862745 - 0.305882) * (250.0 - 148.0) / 255.0));
      } else {
        r = (float) (148.0 / 255.0 + ((val - 0.862745) / (1.0 - 0.862745) * (255.0 - 148.0) / 255.0));
      }

      if (val < 0.109804) {
        g = (float) (val / 0.109804 * 252.0 / 255.0);
      } else if (val >= 0.109804 && val < 0.282353) {
        g = (float) (252.0 / 255.0 * (1.0 - (val - 0.109804) / (0.282353 - 0.109804)));
      } else if (val > 0.282353 && val <= 0.862745) {
        g = 0.0f;
      } else {
        g = (float) ((val - 0.862745) / (1.0 - 0.862745));
      }

      if (val < 0.294118) {
        b = 0.0f;
      } else if (val >= 0.294118 && val < 0.784314) {
        b = (float) ((val - 0.294118) / (0.784314 - 0.294118));
      } else {
        b = 1.0f;
      }

      return new Color(r, g, b);
    }
  },
  /**
   * Variation of IDL's GRN-RED-BLU-WHT colour table
   */
  GRN_YEL_RED_BLU_WHT("Variation of IDL's GRN-RED-BLU-WHT colour table") {
    @Override
    public Color getColour(final double val) {
      float r, g, b;

      if (val < 0.113725) {
        r = 0.0f;
      } else if (val >= 0.113725 && val < 0.305882) {
        r = (float) ((val - 0.113725) / (0.305882 - 0.113725) * 250.0 / 255.0);
      } else if (val >= 0.305882 && val < 0.862745) {
        r = (float) (250.0 / 255.0 - ((val - 0.305882) / (0.862745 - 0.305882) * (250.0 - 148.0) / 255.0));
      } else {
        r = (float) (148.0 / 255.0 + ((val - 0.862745) / (1.0 - 0.862745) * (255.0 - 148.0) / 255.0));
      }

      if (val < 0.109804) {
        g = (float) (val / 0.109804 * 252.0 / 255.0);
      } else if (val >= 0.109804 && val < 0.282353) {
        g = (float) (252.0 / 255.0 * (val - 0.109804) / (0.282353 - 0.109804));
      } else if (val > 0.282353 && val <= 0.862745) {
        g = 0.0f;
      } else {
        g = (float) ((val - 0.862745) / (1.0 - 0.862745));
      }

      if (val < 0.294118) {
        b = 0.0f;
      } else if (val >= 0.294118 && val < 0.784314) {
        b = (float) ((val - 0.294118) / (0.784314 - 0.294118));
      } else {
        b = 1.0f;
      }

      return new Color(r, g, b);
    }
  },
  /**
   * For entertainment
   */
  ROOD_WIT_BLAUW("Red-White-Blue") {
    @Override
    public Color getColour(final double val) {
      float r, g, b;
      if (val <= 0.5) {
        r = 1.0f;
        g = (float) (val / 0.5);
        b = (float) (val / 0.5);
      } else {
        r = (float) (2.0 - 2.0 * val);
        g = (float) (2.0 - 2.0 * val);
        b = 1.0f;
      }
      return new Color(r, g, b);
    }
  },
  /**
   * IDL's RED TEMPERATURE colour table
   */
  IDL_RED_TEMPERATURE("IDL's RED TEMPERATURE colour table") {
    @Override
    public Color getColour(final double val) {
      float r, g, b;
      final double RKNOT = 176.0 / 255.0;
      final double GKNOT = 120.0 / 255.0;
      final double BKNOT = 190.0 / 255.0;
      if (val < RKNOT) {
        r = (float) (val / RKNOT);
      } else {
        r = 1.0f;
      }
      if (val < GKNOT) {
        g = 0.0f;
      } else {
        g = (float) ((val - GKNOT) / (1.0 - GKNOT));
      }
      if (val < BKNOT) {
        b = 0.0f;
      } else {
        b = (float) ((val - BKNOT) / (1.0 - BKNOT));
      }
      return new Color(r, g, b);
    }
  },
  /**
   * IDL's Blue-Red colour table
   */
  IDL_BLUE_RED("IDL's Blue-Red colour table") {
    float r, g, b;

    final double[] RKNOTS = new double[] { 98.0 / 255.0, 162.0 / 255.0, 226.0 / 255.0 };

    final double[] GKNOTS = new double[] { 33.0 / 255.0, 97.0 / 255.0, 162.0 / 255.0, 226.0 / 255.0 };

    final double[] BKNOTS = new double[] { 32.0 / 255.0, 98.0 / 255.0, 162.0 / 255.0 };

    final double ROFFSET = 131.0 / 255.0;

    final double BOFFSET = 131.0 / 255.0;

    @Override
    public Color getColour(final double val) {
      if (val <= RKNOTS[0]) {
        r = 0.0f;
      } else if (val > RKNOTS[0] && val < RKNOTS[1]) {
        r = (float) ((val - RKNOTS[0]) / (RKNOTS[1] - RKNOTS[0]));
      } else if (val > RKNOTS[1] && val < RKNOTS[2]) {
        r = 1.0f;
      } else {
        r = (float) (ROFFSET + (1.0 - ROFFSET) * (1.0 - val) / (1.0 - RKNOTS[2]));
      }
      if (val <= GKNOTS[0]) {
        g = 0.0f;
      } else if (val > GKNOTS[0] && val < GKNOTS[1]) {
        g = (float) ((val - GKNOTS[0]) / (GKNOTS[1] - GKNOTS[0]));
      } else if (val > GKNOTS[1] && val < GKNOTS[2]) {
        g = 1.0f;
      } else if (val > GKNOTS[2] && val < GKNOTS[3]) {
        g = (float) ((GKNOTS[3] - val) / (GKNOTS[3] - GKNOTS[2]));
      } else {
        g = 0.0f;
      }
      if (val <= BKNOTS[0]) {
        b = (float) (BOFFSET + (1.0 - BOFFSET) * val / (BKNOTS[0]));
      } else if (val > BKNOTS[0] && val < BKNOTS[1]) {
        b = 1.0f;
      } else if (val > BKNOTS[1] && val < BKNOTS[2]) {
        b = (float) ((BKNOTS[2] - val) / (BKNOTS[2] - BKNOTS[1]));
      } else {
        b = 0.0f;
      }
      return new Color(r, g, b);
    }
  },
  /**
   * IDL's BLUE/GREEN/RED/YELLOW
   */
  IDL_BLUE_GREEN_RED_YELLOW("IDL's BLUE/GREEN/RED/YELLOW colour table") {
    @Override
    public Color getColour(final double val) {
      float r, g, b;
      final double[] RKNOTS = new double[] { 112.0 / 255.0, 144.0 / 255.0 };
      final double[] GKNOTS = new double[] { 32.0 / 255.0, 80.0 / 255.0, 144.0 / 255.0 };
      final double[] BKNOTS = new double[] { 48.0 / 255.0, 80.0 / 255.0, 112.0 / 255.0 };
      final double RKNEEVAL = 200.0 / 255.0;
      final double BMAX = 100.0 / 255.0;
      final double GVAL = 150.0 / 255.0;
      final double GHELPVAL = (GKNOTS[2] - GKNOTS[1]) * (GKNOTS[2] - GKNOTS[1]);
      if (val <= RKNOTS[0]) {
        r = 0.0f;
      } else if (val > RKNOTS[0] && val <= RKNOTS[1]) {
        r = (float) (RKNEEVAL * (val - RKNOTS[0]) / (RKNOTS[1] - RKNOTS[0]));
      } else {
        r = (float) (RKNEEVAL + (1.0 - RKNEEVAL) * (val - RKNOTS[1]) / (1.0 - RKNOTS[1]));
      }
      if (val <= GKNOTS[0]) {
        g = 0.0f;
      } else if (val > GKNOTS[0] && val < GKNOTS[1]) {
        g = (float) (GVAL * (val - GKNOTS[0]) / (GKNOTS[1] - GKNOTS[0]));
      } else if (val >= GKNOTS[1] && val <= GKNOTS[2]) {
        g = (float) (GVAL * (1.0 - (val - GKNOTS[1]) * (val - GKNOTS[1]) / GHELPVAL));
      } else {
        g = (float) ((val - GKNOTS[2]) / (1.0 - GKNOTS[2]));
      }
      if (val < BKNOTS[0]) {
        b = (float) (BMAX * val / BKNOTS[0]);
      } else if (val >= BKNOTS[0] && val <= BKNOTS[1]) {
        b = (float) BMAX;
      } else if (val > BKNOTS[1] && val < BKNOTS[2]) {
        b = (float) (BMAX * (BKNOTS[2] - val) / (BKNOTS[2] - BKNOTS[1]));
      } else {
        b = 0.0f;
      }
      return new Color(r, g, b);
    }
  },
  /**
   * IDL's STERN SPECIAL
   */
  IDL_STERN_SPECIAL("IDL's STERN SPECIAL colour table") {
    @Override
    public Color getColour(final double val) {
      float r, g, b;
      final double[] RKNOTS = new double[] { 14.0 / 255.0, 63.0 / 255.0, 64.0 / 255.0 };
      final double[] BKNOTS = new double[] { 128.0 / 255.0, 188.0 / 255.0 };
      if (val <= RKNOTS[0]) {
        r = (float) (val / RKNOTS[0]);
      } else if (val > RKNOTS[0] && val <= RKNOTS[1]) {
        r = (float) ((RKNOTS[1] - val) / (RKNOTS[1] - RKNOTS[0]));
      } else if (val > RKNOTS[1] && val <= RKNOTS[2]) {
        r = (float) (RKNOTS[2] * (val - RKNOTS[1]) / (RKNOTS[2] - RKNOTS[1]));
      } else {
        r = (float) val;
      }
      g = (float) val;
      if (val <= BKNOTS[0]) {
        b = (float) (val / BKNOTS[0]);
      } else if (val > BKNOTS[0] && val <= BKNOTS[1]) {
        b = (float) ((BKNOTS[1] - val) / (BKNOTS[1] - BKNOTS[0]));
      } else {
        b = (float) ((val - BKNOTS[1]) / (1.0 - BKNOTS[1]));
      }
      return new Color(r, g, b);
    }
  },
  /**
   * The 6 primary RGB colours plus black and white
   */
  PRIMARY_STEPS("The 6 primary RGB colours plus black and white") {
    @Override
    public Color getColour(final double val) {
      Color col;
      if (val <= 0.125) {
        col = Color.black;
      } else if (val > 0.125 && val <= 0.25) {
        col = Color.blue;
      } else if (val > 0.25 && val <= 0.375) {
        col = Color.cyan;
      } else if (val > 0.375 && val <= 0.5) {
        col = Color.red;
      } else if (val > 0.5 && val <= 0.625) {
        col = Color.yellow;
      } else if (val > 0.625 && val <= 0.75) {
        col = Color.magenta;
      } else if (val > 0.75 && val <= 0.875) {
        col = Color.green;
      } else {
        col = Color.white;
      }
      return col;
    }
  },
  /**
   * IDL's GREEN/WHITE LINEAR colour table
   */
  IDL_GREEN_WHITE("IDL's GREEN/WHITE LINEAR colour table") {
    @Override
    public Color getColour(final double val) {
      float r, g, b;
      final double RKNOT = 97.0 / 255.0;
      final double BKNOT = 181.0 / 255.0;
      if (val <= RKNOT) {
        r = 0.0f;
      } else {
        r = (float) ((val - RKNOT) / (1.0 - RKNOT));
      }
      g = (float) val;
      if (val <= BKNOT) {
        b = 0.0f;
      } else {
        b = (float) ((val - BKNOT) / (1.0 - BKNOT));
      }
      return new Color(r, g, b);
    }
  },
  /**
   * IDL's BLUE-RED
   */
  BLK_CYA_BLU_MAG_RED("IDL's BLUE-RED colour table (different from 'Blue-Red')") {
    @Override
    public Color getColour(final double val) {
      float r, g, b;
      final double[] RKNOTS = new double[] { 128.0 / 255.0, 191.0 / 255.0 };
      final double[] GKNOTS = new double[] { 64.0 / 255.0, 128.0 / 255.0 };
      final double[] BKNOTS = new double[] { 64.0 / 255.0, 191.0 / 255.0 };
      if (val < RKNOTS[0]) {
        r = 0.0f;
      } else if (val >= RKNOTS[0] && val <= RKNOTS[1]) {
        r = (float) ((val - RKNOTS[0]) / (RKNOTS[1] - RKNOTS[0]));
      } else {
        r = 1.0f;
      }
      if (val < GKNOTS[0]) {
        g = (float) (val / GKNOTS[0]);
      } else if (val >= GKNOTS[0] && val <= GKNOTS[1]) {
        g = (float) ((GKNOTS[1] - val) / (GKNOTS[1] - GKNOTS[0]));
      } else {
        g = 0.0f;
      }
      if (val < BKNOTS[0]) {
        b = (float) (val / BKNOTS[0]);
      } else if (val >= BKNOTS[0] && val <= BKNOTS[1]) {
        b = 1.0f;
      } else {
        b = (float) ((1.0 - val) / (1.0 - BKNOTS[1]));
      }
      return new Color(r, g, b);
    }
  },
  ;

  /**
   * Descriptive string of colour table.
   */
  private String label;

  /**
   * Constructor.
   * 
   * @param lab
   *          Descriptive string of colour table.
   */
  ColourLuts(final String lab) {
    label = lab;
  }

  /**
   * Override the default toString() method for enum types so that a more readable string is
   * returned whenever the values are to be converted to strings.
   * 
   * @return label
   */
  @Override
  public String toString() {
    return label;
  }

  /**
   * Given a pixel value scaled between zero and one return the colour from the lookup table
   * selected when constructing the instance of this class.
   * 
   * @param pixValue
   *          Image pixel value scaled between 0 and 1.
   * @return The colour from the LUT.
   */
  public abstract Color getColour(double pixValue);

  /**
   * Obtain the colour corresponding to the reversed LUT.
   * 
   * @param pixValue
   *          Image pixel value scaled between 0 and 1.
   * @return The colour from the reverse LUT.
   */
  public Color getReverseColour(final double pixValue) {
    return getColour(1.0 - pixValue);
  }

  /**
   * Return all the LUTs defined in this Enum as an array.
   * 
   * @return Array of all look-up tables.
   */
  public static ColourLuts[] toArray() {
    return EnumSet.allOf(ColourLuts.class).toArray(new ColourLuts[0]);
  }

}
