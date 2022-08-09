/**
 * Description: This program simulates the motion of a number of bodies on a plane.
 * The bodies (show as planets) are affected by the gravitational forces of
 * other planets. The information for these bodies' mass, images, velocities,
 * and positions are found in the text file: planets.txt. There are two command-
 * line arguments to be inputted: when to stop the movement and the change in time
 * with each loop. The change in time is incremented after each loop.
 */
public class NBody {
    public static void main(String[] args) {
        // Step 1. Parse command-line arguments.
        double stoppingTime = Double.parseDouble(args[0]);
        double changeInTime = Double.parseDouble(args[1]);

        // Step 2. Read universe from standard input.
        int n = StdIn.readInt();
        double radius = StdIn.readDouble();
        double[] px = new double[n];
        double[] py = new double[n];
        double[] vx = new double[n];
        double[] vy = new double[n];
        double[] mass = new double[n];
        String[] image = new String[n];

        for (int i = 0; i < n; i++) {
            px[i] = StdIn.readDouble();
            py[i] = StdIn.readDouble();
            vx[i] = StdIn.readDouble();
            vy[i] = StdIn.readDouble();
            mass[i] = StdIn.readDouble();
            image[i] = StdIn.readString();
        }
        // Step 3. Initialize standard drawing.
        // Makes the scale the size of the diameter of the universe to make the
        // animation show up
        StdDraw.setXscale(-radius, radius);
        StdDraw.setYscale(-radius, radius);
        StdDraw.enableDoubleBuffering();

        // Step 4. Play music on standard audio.
        StdAudio.play("2001.wav");

        // Step 5. Simulate the universe. The change in time is incremented
        // and the loop stops when it hits the stopping time inputted into
        // the command-line.
        for (double t = 0.0; t < stoppingTime; t += changeInTime) {
            // StdOut.println("t = " + t);

            double[] fx = new double[n];
            double[] fy = new double[n];

            // Step 5A. Calculate net forces.
            // First calculate the force using F = (m1*m2)/r^2
            // Then use the force to find force in the x and y directions
            // Finally, add the forces found to fx[i] and fy[i] so that net forces
            // are calculated.
            for (int i = 0; i < n; i++) {
                fx[i] = 0.0;
                fy[i] = 0.0;
                for (int j = 0; j < n; j++) {
                    if (j != i) {
                        double GRAVITATIONAL_CONSTANT = 6.67e-11;
                        double num = ((GRAVITATIONAL_CONSTANT) * mass[i] * mass[j]);
                        int POWER = 2;
                        double changeOfX = px[j] - px[i];
                        double changeOfY = py[j] - py[i];
                        double powerOfX = Math.pow(changeOfX, POWER);
                        double powerOfY = Math.pow(changeOfY, POWER);
                        double distance = powerOfX + powerOfY;
                        double r = Math.sqrt(distance);
                        double denominator = Math.pow(r, POWER);
                        double force = num / denominator;
                        double fx1 = (force * changeOfX) / r;
                        double fy1 = (force * changeOfY) / r;
                        fx[i] += fx1;
                        fy[i] += fy1;
                    }
                }
            }
            // Step 5B. Update velocities and positions
            for (int k = 0; k < n; k++) {
                double ax = fx[k] / mass[k];
                double ay = fy[k] / mass[k];
                vx[k] = vx[k] + (ax * changeInTime);
                vy[k] = vy[k] + (ay * changeInTime);
                px[k] = px[k] + (vx[k] * changeInTime);
                py[k] = py[k] + (vy[k] * changeInTime);
            }


            // Step 5C. Draw universe to standard drawing.
            StdDraw.picture(0, 0, "starfield.jpg");

            for (int k = 0; k < n; k++) {
                StdDraw.picture(px[k], py[k], image[k]);
            }
            StdDraw.show();
            StdDraw.pause(40);
        }

        // Step 6. Print universe to standard output.
        StdOut.printf("%d\n", n);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < n; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                          px[i], py[i], vx[i], vy[i], mass[i], image[i]);

        }
    }
}

