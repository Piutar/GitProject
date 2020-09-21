package TH;

class Shape {
    private String color = "green";
    private  boolean filled = true;

    public Shape() {

    }

    public Shape(String color, boolean filled) {
        this.color = color;
        this.filled = filled;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isFilled() {
        return filled;
    }

    public String toString() {
        return  "AShape with color of "
                + getColor()
                +" and "
                +(isFilled() ? "filled" : "not filled");
    }
}


class Circle extends Shape {
    private double radius = 1.0;

    public Circle() {
    }

    public Circle(double radius) {
        this.radius = radius;
    }

    public Circle(double radius, String color, boolean filled) {
        super(color, filled);
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getArea() {
        return radius * radius * Math.PI;
    }

    public double getPerimeter() {
        return 2 * radius * Math.PI;
    }

    @Override
    public String toString() {
        return "A Circle with radius=" + getRadius() + " , which is a subclass of " + super.toString();
    }

    public static class ShapeTest {
        public static void main(String[] args) {
            Shape shape = new Shape();
            System.out.println(shape);

            shape = new Shape("red", false);
            System.out.println(shape);

            Circle circle = new Circle();
            System.out.println(circle);

            circle = new Circle(3.5);
            System.out.println(circle);

            circle = new Circle(3.5, "indigo", false);
            System.out.println(circle);
        }
    }
}
