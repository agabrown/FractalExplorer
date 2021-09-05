# FractalExplorer

A fractal viewer implemented in Java. Written mainly for my own entertainment and inspired by the Fractile Plus app
[Fractile Plus app](https://fractilefractals.wordpress.com/) (with no pretence of getting even close to the performance and
smooth feel of Fractile Plus!).

## Build and dependencies

Build/execution is managed with Gradle.

## Documentation

TBD

## Running FractalExplorer

One can run the explorer as follows:

- Java: "java -jar folder-with-jar/FractalExplorer-version-jar-with-dependencies.jar"
- Bash: using the example bash script in the "scripts" folder.
- ... (your favoured method here)

## Limitations (or features, depending on your point of view) of FractalExplorer

Fractal sets are calculated and coloured at the pixel level, the "resolution" and "smoothness" are thus determined by the
screen properties. The size of the image is hard-coded for now.

The colour coding is done at the pixel level without using sophisticated algorithms. Together with the escape time algorithm
employed this leads to "bands" of colour. I actually like this but if you want to get rid of this see
[http://en.wikipedia.org/wiki/Mandelbrot_set](http://en.wikipedia.org/wiki/Mandelbrot_set) for suggestions.

For the moment the interface consists of a mixture of mouse-clicks and key-strokes. Stone age, I know! But that's how I like
my interfaces.

## To do

* Make the fractal set calculations multi-threaded.
* Showing the colour LUTs available and allowing the user to pick the one to use.
