package com.mygdx.game;

public class Point2D {
float x,y;
public Point2D (float x,float y)
{
this.x =x;
this.y=y;
}
public void translate(float dx,float dy)
{
this.x +=dx;
this.y +=dy;
}
public static Point2D translatePointByVector(Point2D p,Vector2D v)
{

return new Point2D(p.x+v.x,p.y+v.y);
}
public static Vector2D makeVector (Point2D v1,Point2D v2) {

return new Vector2D(v2.x-v1.x,v2.y-v1.y);
}
}
