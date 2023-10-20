public class NBody {
    public static double readRadius(String filepath) {
        In in = new In(filepath);
        if (!in.isEmpty()) {
            int total = in.readInt();
            double r = in.readDouble();
            return r;
        }
        return 0;
    }

    public static Planet[] readPlanets(String filepath) {
        In in = new In(filepath);
        if (in.isEmpty()) {
            return  null;
        }
        int total_line = in.readInt();
        double r = in.readDouble();
        Planet[] res = new Planet[total_line];
        int index = 0;
        while (index < total_line) {
            double xxPos = in.readDouble();
            double yyPos = in.readDouble();
            double xxVel = in.readDouble();
            double yyVel = in.readDouble();
            double mass = in.readDouble();
            String img = in.readString();
            res[index] = new Planet(xxPos, yyPos, xxVel, yyVel, mass, img);
            index += 1;
        }
        return res;
    }

    public static void main(String[] args) {
        //enable double buffer.
        StdDraw.enableDoubleBuffering();

        //read parameters and information.
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double r = readRadius(filename);
        Planet[] ArrayPlants = readPlanets(filename);
        double time = 0;

        double[] xForces = new double[ArrayPlants.length];
        double[] yForces = new double[ArrayPlants.length];
        StdAudio.play("audio/2001.mid");
        while (time < T) {
            //init universe
            StdDraw.setScale(-r, r);
            StdDraw.clear();
            StdDraw.picture(0, 0, "images/starfield.jpg");

            //draw plants_old and update plants_new
            int index = 0;
            for (Planet p: ArrayPlants) {
                p.draw();
                xForces[index] = p.calcNetForceExertedByX(ArrayPlants);
                yForces[index] = p.calcNetForceExertedByY(ArrayPlants);
                index += 1;
            }

            StdDraw.show();
            StdDraw.pause(10);
            time += dt;
            index = 0;
            //update planet
            for (Planet p:ArrayPlants) {
                p.update(dt, xForces[index], yForces[index]);
                index += 1;
            }
        }
        StdOut.printf("%d\n", ArrayPlants.length);
        StdOut.printf("%.2e\n", r);
        for (int i = 0; i < ArrayPlants.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    ArrayPlants[i].xxPos, ArrayPlants[i].yyPos, ArrayPlants[i].xxVel,
                    ArrayPlants[i].yyVel, ArrayPlants[i].mass, ArrayPlants[i].imgFileName);
        }
        return;
    }
}
