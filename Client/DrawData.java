import java.awt.Point;
import java.io.Serializable;

public class DrawData implements Serializable {
    private static final long serialVersionUID = 1L;
    private Point start;
    private Point end;

    public DrawData(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }
}
