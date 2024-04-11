package nine.math;

public class Intersection
{
    public static boolean circleAndCircle(Circle a, Circle b)
    {
        return a.center.sub(b.center).length() <= (a.radius + b.radius);
    }
    public static boolean circleAndLine(Circle a, Line b)
    {
        Vector2f ab = b.b.sub(b.a);
        Vector2f ao = a.center.sub(b.a);
        float aoM = ao.length();
        float acM = ao.dot(ab) * aoM;
        float oc = (float)Math.sqrt(aoM * aoM - acM * acM);
        return oc <= a.radius;
    }
}