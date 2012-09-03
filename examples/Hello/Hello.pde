import net.hellonico.ocr.*;

OCRLibrary ocr;

void setup() {
  size(1200,800);
  smooth();
  background(0);
  fill(255);
  PFont font = createFont("",9);
  textFont(font);
  noLoop();
  
  ocr = new OCRLibrary(this);
  text(ocr.scan(dataPath("sample.jpg")), 0, 0);
}

void draw() {
  
}
