package com.mygdx.game;


public class Rectangle {
	Point2D p1;
	Point2D p2;
	Point2D p3;
	Point2D p4;

	float maxX, maxY, minX, minY;
    public Rectangle(Point2D p1,Point2D p2)
    {
    	maxX = Math.max(p1.x, p2.x);
		minX = Math.min(p1.x, p2.x);
		maxY = Math.max(p1.y, p2.y);
		minY = Math.min(p1.y, p2.y);
		
        this.p1 = new Point2D(minX,maxY);
        this.p3 = new Point2D(maxX,minY);
        
        
        this.p2 = new Point2D(minX,minY);
        this.p4 = new Point2D(maxX,maxY);

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
