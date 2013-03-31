/**
 * Provides classes for visually exploring Fractal sets (such as the Mandelbrot
 * set).
 * 
 * For now only the Mandelbrot set is implemented and it is visualized using the
 * 'escape time' algorithm. For background see this <a
 * href="http://en.wikipedia.org/wiki/Mandelbrot_set">wiki page</a>.
 * 
 * @version 0.4.0
 * @version 31.03.2013
 * @author Anthony Brown
 * @author agabrown@xs4all.nl
 * 
 *         <h2>History</h2>
 * 
 *         <p>
 *         <b>March 2013, version 0.4.0</b>
 *         <ul>
 *         <li>Full screen is used now either in "full screen mode" or by
 *         instantiating a window that takes up the full screen.</li>
 *         </ul>
 *         </p>
 * 
 *         <p>
 *         <b>March 2013, version 0.3.2</b>
 *         <ul>
 *         <li>Updated dependencies and build file.</li>
 *         </ul>
 *         </p>
 * 
 *         <p>
 *         <b>August 2012, version 0.3.1</b>
 *         <ul>
 *         <li>Cleaner implementation of info layer by adding informative
 *         strings to the {@link agabrown.fractalexplorer.sets.FractalSet}
 *         interface.</li>
 *         </ul>
 *         </p>
 * 
 *         <p>
 *         <b>July 2012, version 0.3.0</b>
 *         <ul>
 *         <li>Refactoring and clean-up of code by introducing the
 *         {@link agabrown.fractalexplorer.dm.ComplexPlaneView} data type.</li>
 *         </ul>
 *         </p>
 * 
 *         <p>
 *         <b>July 2012, version 0.2.0</b>
 *         <ul>
 *         <li>Added 'press enter' for selecting complex number to centre on.</li>
 *         <li>Changed behaviour while help screen is visible (only the 'h' key
 *         is then processed).</li>
 *         <li>Added Julia set and created FractalSet interface.</li>
 *         </ul>
 *         </p>
 * 
 *         <p>
 *         <b>July 2012, version 0.1.1</b>
 *         <ul>
 *         <li>Moved splash screen resources into package tree for easier access
 *         from both Eclipse and the jar file.</li>
 *         <li>Small bug-fix in the
 *         {@link agabrown.fractalexplorer.colours.ImageScaling} enum, which did
 *         not take into account the possibility that an image consists of
 *         pixels all at same value.</li>
 *         </ul>
 *         </p>
 * 
 *         <p>
 *         <b>July 2012, version 0.1.0</b>
 *         <ul>
 *         <li>First completely functional version of the Fractal Explorer.</li>
 *         </ul>
 *         </p>
 */
package agabrown.fractalexplorer;

