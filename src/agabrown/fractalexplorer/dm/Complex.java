package agabrown.fractalexplorer.dm;

/**
 * Provides the complex number data type and methods for operating on complex numbers.
 * 
 * @author agabrown 18 Jul 2012
 * 
 */
public final class Complex {

  /**
   * Real part of complex number.
   */
  private final double real;

  /**
   * Imaginary part of complex number;
   */
  private final double imaginary;

  /**
   * The norm of the complex number.
   */
  private final double nrm;

  /**
   * Argument of complex number in polar representation.
   */
  private final double argument;

  /**
   * The square of the norm of the complex number.
   */
  private final double nrmSquared;

  /**
   * Constructor.
   * 
   * x - Real part of complex number. y - Imaginary part of complex number.
   */
  public Complex(final double x, final double y) {
    this.real = x;
    this.imaginary = y;
    nrmSquared = real * real + imaginary * imaginary;
    nrm = Math.sqrt(nrmSquared);
    argument = Math.atan2(imaginary, real);
  }

  /**
   * Return the real part of this complex number.
   */
  public double getReal() {
    return real;
  }

  /**
   * Return the imaginary part of this complex number.
   */
  public double getImaginary() {
    return imaginary;
  }

  /**
   * Return the squared of the norm of this complex number.
   */
  public double getNormSquared() {
    return nrmSquared;
  }

  /**
   * Return the norm of this complex number.
   */
  public double getNorm() {
    return nrm;
  }

  /**
   * Return a new complex number which is the sum of this and the other complex number.
   * 
   * other - Complex number to add to this number.
   */
  public Complex add(final Complex other) {
    return new Complex(this.real + other.getReal(), this.imaginary + other.getImaginary());
  }

  /**
   * Return a new complex number which is the difference of this and the other complex number.
   * 
   * other - Complex number to subtract from this number.
   */
  public Complex subtract(final Complex other) {
    return new Complex(this.real - other.getReal(), this.imaginary - other.getImaginary());
  }

  /**
   * Return a new complex number which is the multiplication of this and the other complex number.
   * 
   * other - Complex number to multiply with this number.
   */
  public Complex multiply(final Complex other) {
    return new Complex(this.real * other.getReal() - this.imaginary * other.getImaginary(), this.real
        * other.getImaginary() + this.imaginary * other.getReal());
  }

  /**
   * Return a new complex number which the multiplication of this complex number with a real number.
   * 
   * factor - Real number with which to multiply this complex number.
   */
  public Complex multiply(final double factor) {
    return new Complex(real * factor, imaginary * factor);
  }

  /**
   * Return a new complex number which the division of this complex number by another complex
   * number.
   * 
   * factor - Complex number by which to divide this complex number.
   */
  public Complex divide(final Complex other) {
    return multiply(other.reciprocal());
  }

  /**
   * Return the conjugate of this complex number.
   */
  public Complex conjugate() {
    return new Complex(real, -imaginary);
  }

  /**
   * Returns the reciprocal of this complex number.
   */
  public Complex reciprocal() {
    return conjugate().multiply(1.0 / nrmSquared);
  }

  /**
   * Return string representation of this complex number.
   */
  @Override
  public String toString() {
    final String sign = Math.signum(imaginary) < 0.0 ? "" : "+";
    return Double.toString(real) + sign + Double.toString(imaginary) + "*i";
  }

}
