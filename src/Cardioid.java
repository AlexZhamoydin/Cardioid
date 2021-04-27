import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;


import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.PI;


public class Cardioid implements Shape {
    double x,y;     //углубление в сердечке
    double r = 1;

    public Cardioid(){
        this(0,0,0);
    }
    public Cardioid(double x, double y, double r){
        this.x = x;
        this.y = y;
        this.r = r;
    }


    @Override
    public Rectangle getBounds() {
        return null;
    }

    @Override
    public Rectangle2D getBounds2D() {
        return null;
    }

    @Override
    public boolean contains(double x, double y) {
        return false;
    }

    @Override
    public boolean contains(Point2D p) {
        return false;
    }

    @Override
    public boolean intersects(double x, double y, double w, double h) {
        return false;
    }

    @Override
    public boolean intersects(Rectangle2D r) {
        return false;
    }

    @Override
    public boolean contains(double x, double y, double w, double h) {
        return false;
    }

    @Override
    public boolean contains(Rectangle2D r) {
        return false;
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at) {
        return new CardioidIterator(at);
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at, double flatness) {
        return new CardioidIterator(at,flatness);
    }
    private class CardioidIterator implements PathIterator{
        AffineTransform transform;
        private double flatness;
        private int index = 0;
        private final int accuracy = 64*5;
        private double angle;
        /* подсчет х и у для паситератора */
        private double calculateX(){
            return -(1-sin(angle))*cos(angle);
        }
        private double calculateY(){
            return -(1-sin(angle))*sin(angle);
        }

        private CardioidIterator(AffineTransform transform, double flatness){
            this.transform = transform;
            this.flatness = flatness;
        }
        private CardioidIterator(AffineTransform transform){
            this.transform = transform;
        }

        @Override
        public int getWindingRule() {
            return WIND_NON_ZERO;
        }

        @Override
        public boolean isDone() {
            return index>accuracy/2+1;
        }

        @Override
        public void next() {
           index++;
           angle=4*index*PI/accuracy;
        }

        @Override
        public int currentSegment(float[] coords) {
            coords[0] = (float)(x + (calculateX())*r);
            coords[1] = (float)(y + (calculateY())*r);

            if (transform != null)
                transform.transform(coords, 0, coords, 0,1);
            if(index == 0)
                return SEG_MOVETO;
            if(index==accuracy/2+1)
                return SEG_CLOSE;
            return SEG_LINETO;
        }
        @Override
        public int currentSegment(double[] coords) {
            coords[0] = x + calculateX()*r;
            coords[1] = y + calculateY()*r;

            if (transform != null)
                transform.transform(coords, 0, coords, 0,1);
            if(index==accuracy/2)
                return SEG_CLOSE;
            return index == 0 ? SEG_MOVETO : SEG_LINETO;
        }
    }
}
