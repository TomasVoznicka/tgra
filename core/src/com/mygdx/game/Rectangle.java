package com.mygdx.game;


public class Rectangle {
	Point2D p1;
	Point2D p2;
	Point2D p3;
	Point2D p4;

    public Rectangle(Point2D p1,Point2D p2)
    {
        this.p1 = new Point2D(p1.x,p1.y);
        this.p3 = new Point2D(p2.x,p2.y);
        
        
        this.p2 = new Point2D(p1.x,p2.y);
        this.p4 = new Point2D(p2.x,p1.y);

    }

    public float width()
    {
        return p3.x - p1.x;
    }

    public float height()
    {
    	return p3.y - p1.y;
    }

    public Point2D center()
    {
        return new Point2D(((p4.x-p2.x)/2)+p2.x,((p4.y-p2.y)/2)+p2.y);
    }
}
