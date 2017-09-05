package com.mygdx.game;

public class Vector2D {
	float x,y;
public Vector2D (float x,float y)
{
this.x =x;
this.y=y;
}
public  Vector2D getUnit () {

return new Vector2D(-y,x);
}
public static Vector2D scale (Vector2D v,float ds) {

return new Vector2D(v.x*ds,v.y*ds);
}
public static Vector2D addTwo (Vector2D v1,Vector2D v2) {

return new Vector2D(v1.x+v2.x,v1.y+v2.y);
}
public static Vector2D subbTwo (Vector2D v1,Vector2D v2) {

return new Vector2D(v1.x-v2.x,v1.y-v2.y);
}
public static float dot (Vector2D v1,Vector2D v2) {

return (v1.x*v2.x)+(v1.y*v2.y);
}
}
