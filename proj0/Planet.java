public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    public static final double G =  6.67 * 1e-11;

    public Planet(double xP, double yP, double xV, double yV,
                   double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass  = p.mass;
        imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet p) {
        double dx = p.xxPos - xxPos;
        double dy = p.yyPos - yyPos;
        return Math.sqrt(dx * dx + dy * dy);
    }
    public double calcForceExertedBy(Planet p) {
        double r = this.calcDistance(p);
        return (G * mass * p.mass) / (r * r);
    }

    public double calcForceExertedByX(Planet p) {
        double r = this.calcDistance(p);
        double net = this.calcForceExertedBy(p);
        double dx = p.xxPos - xxPos;
        return (net) * (dx / r);
    }

    public double calcForceExertedByY(Planet p) {
        double r = this.calcDistance(p);
        double net = this.calcForceExertedBy(p);
        double dy = p.yyPos - yyPos;
        return (net) * (dy / r);
    }

    public double calcNetForceExertedByX(Planet[] p) {
        double res = 0;
        for (Planet x : p) {
            if (x == this) {
                continue;
            }
            res += this.calcForceExertedByX(x);
        }
        return res;
    }

    public double calcNetForceExertedByY(Planet[] p) {
        double res = 0;
        for (Planet x: p) {
            if (x == this) {
                continue;
            }
            res += this.calcForceExertedByY(x);
        }
        return res;
    }

    public void update(double seconds, double x_force, double y_force) {
        double x_acc = x_force / mass;
        double y_acc = y_force / mass;
        xxVel = xxVel + x_acc * seconds;
        yyVel = yyVel + y_acc * seconds;
        xxPos = xxPos + xxVel * seconds;
        yyPos = yyPos + yyVel * seconds;
    }

    public void draw() {
        StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
    }
}
