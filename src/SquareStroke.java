import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;

import static java.lang.Math.*;

public class SquareStroke implements Stroke {
    BasicStroke stroke;
    public SquareStroke(int width){
        stroke = new BasicStroke(width);
    }
    float[] prevCoords = new float[2];
    @Override
    public Shape createStrokedShape(Shape shape) {
        GeneralPath path = new GeneralPath();
        float[] coords = new float[2];
        for(PathIterator pi = shape.getPathIterator(null); !pi.isDone(); pi.next()){
            int type = pi.currentSegment(coords);
            switch (type){
                case PathIterator.SEG_MOVETO:
                    path.moveTo(coords[0], coords[1]);
                    prevCoords = coords;
                    break;
                case PathIterator.SEG_LINETO:
                     drawRoute(path,coords);
                    break;
                case PathIterator.SEG_CLOSE:
                    break;
                default:
                    System.err.println("Unsupported iterator");
                    break;
            }
            prevCoords = coords.clone();
        }
        return stroke.createStrokedShape(path);
    }

    private void drawRoute(GeneralPath path, float[] coords){
        float x1 = prevCoords[0], y1 = prevCoords[1];
        float x2 = coords[0], y2 = coords[1];
        float v11 = -(x2-x1)/3.f, v12 = -(y2-y1)/3.f;
        float v21 = -v12, v22 = v11;
        float c1 = x1, c2 = y1;

        path.moveTo(c1,c2);

        c1+=v11+v21; c2+=v12+v22;
        path.lineTo(c1,c2);

        c1+=v21; c2+=v22;
        path.lineTo(c1,c2);

        c1+=v11; c2+=v12;
        path.lineTo(c1,c2);

        c1-=v21; c2-=v22;
        path.lineTo(c1,c2);

        c1=c1+v11-v21; c2=c2+v12-v22;
        path.lineTo(c1,c2);

    }


}
