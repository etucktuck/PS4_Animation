package animation;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.Random;

public class Animation
{
    /** Intervals between "scenes" */
    private final static int INTERVAL = 2500;

    /** Font size of the title */
    private final static int FONT_SIZE = 36;

    /** Colors for animations */
    public static final Color DIRT = new Color(153, 102, 0);
    public static final Color DAY_SKY = new Color(204, 255, 255);
    public static final Color SUN = new Color(255, 204, 0);
    public static final Color STEM = new Color(102, 255, 102);

    /** length of 1 rain drop */
    public static final int RAIN_LENGTH = 15;

    /** cloud size */
    public static final int CLOUD_SIZE = 75;

    /** base size of sun */
    public static final int SUN_SIZE = 100;

    /** base star size */
    public static final int STAR_SIZE = 10;

    /** Name to display in title bar */
    public final static String STUDENT_NAME = "Eric Tucker";  // PUT YOUR NAME HERE!!!

    /**
     * This method draws an animation representing the growth cycle of a flower. Rain, sun, steam growth and bud
     * blooming. Method is broken up into INTERVALS of 2500 msec multiples. The method returns true if the end of the
     * animation has been reached or false if the animation should continue.
     * 
     * @param g      Graphics object on which to draw
     * @param time   Number of milliseconds that have passed since animation started
     * @param height Current height of the frame
     * @param width  Current width of the frame
     */
    public static boolean paintFrame (Graphics2D g, int time, int height, int width)
    {
        drawSky(g, DAY_SKY, height, width);
        drawGround(g, DIRT);
        drawHole(g, DIRT.darker());

        // interval1: clouds moving
        if (time < INTERVAL)
        {
            drawCloud(g, time, (int) (-100 + time * 0.2), 200);
            drawCloud(g, time, (int) (time * 0.15), 100);
            drawCloud(g, time, (int) (width - time * 0.2), 250);
            drawBird(g, 100, 100, time);
        }
        // interval2: clouds moving & 1 cloud raining
        else if (time < INTERVAL * 2)
        {
            drawCloud(g, time, (int) (width - time * 0.2), 250);
            drawCloud(g, time, (int) (time * 0.15), 100);
            drawCloud(g, time, (int) (-100 + INTERVAL * 0.2), 200);
            drawRain(g, time, 400, 200);
            drawBird(g, 100, 100, time);
        }
        // interval3: rain stops & clouds clearing
        else if (time < INTERVAL * 3)
        {
            drawCloud(g, time, (int) (-100 + (time * 0.2) - (INTERVAL * 0.2)), 200);
            drawCloud(g, time, (int) (time * 0.15), 100);
            drawCloud(g, time, (int) (width - time * 0.2), 250);
            drawBird(g, 100, 100, time);
        }
        // interval4: sun rising and expanding. steam of flower growing
        else if (time < INTERVAL * 5)
        {
            int x = (int) ((time * .1) - (INTERVAL * 3 * .1));
            int y = 450 - (int) ((time * .08) - (INTERVAL * 3 * 0.08));

            drawSun(g, time - INTERVAL * 3, SUN_SIZE, x, y);
            drawStem(g, time - (INTERVAL * 3), 0);
            drawCloud(g, time, (int) (-100 + (time * 0.2) - (INTERVAL * 0.2)), 200);
            drawBird(g, 100, 100, time);
        }
        // interval5: sun setting, flow budding, sky darkening and stars appearing
        else if (time < INTERVAL * 7)
        {
            int angleX = (int) (Math.cos(Math.toRadians(45)) * time * .057) + (int) (.08 * (time - (INTERVAL * 5)));
            int angleY = (int) (Math.sin(Math.toRadians(45)) * time * .0057) + (int) (.08 * (time - (INTERVAL * 5)));
            drawSky(g, DAY_SKY, height, width, time);
            // draws 25 stars with random (x,y) center points - x range [10,width-10] y range [10,height -10]
            // 25 stars each have random size multiplier from [0.0 - 1.0)
            for (int i = 0; i < 25; i++)
            {
                int ranX = (int) (Math.random() * ((990 - 10) + 1)) + 10;
                int ranY = (int) (Math.random() * ((690 - 10) + 1)) + 10;
                Random r = new Random();
                double randomSize = r.nextDouble();
                drawStar(g, ranX, ranY, randomSize);
            }
            drawSun(g, INTERVAL * 2, SUN_SIZE, angleX, angleY);
            drawGround(g, DIRT);
            drawHole(g, DIRT.darker());
            drawStem(g, INTERVAL * 2, 0);
            drawFlower(g, 475, 50, time);
            drawBird(g, 100, 100, time);
        }
        // last interval: stars appearing, sky darkening, and sun completely set
        else
        {
            drawSky(g, DAY_SKY, height, width, time);
            for (int i = 0; i < 25; i++)
            {
                int ranX = (int) (Math.random() * ((990 - 10) + 1)) + 10;
                int ranY = (int) (Math.random() * ((690 - 10) + 1)) + 10;
                Random r = new Random();
                double randomSize = r.nextDouble();
                drawStar(g, ranX, ranY, randomSize);
            }
            drawGround(g, DIRT);
            drawHole(g, DIRT.darker());
            // drawSun(g, INTERVAL * 2, SUN_SIZE, angleX, angleY);
            drawStem(g, INTERVAL * 2, 0);
            drawFlower(g, 475, 50, INTERVAL * 7);
        }
        return time >= INTERVAL * 9;
    }

    /**
     * draws a star consisting of 6 lines extending from mid point (x,y) length of each line = STAR_SIZE * sizeMult
     */
    public static void drawStar (Graphics2D g, int x, int y, double sizeMult)
    {
        for (int i = 0; i <= 360; i += 60)
        {
            g.setColor(Color.YELLOW);
            int x2 = x + (int) (Math.cos(Math.toRadians(i)) * STAR_SIZE * sizeMult);
            int y2 = y + (int) (Math.sin(Math.toRadians(i)) * STAR_SIZE * sizeMult);
            g.drawLine(x, y, x2, y2);
        }
    }

    /**
     * draws a bird in the shape of a V. "flaps" wingers every quarter of a second. alternating betwene 45 and 180
     * degree angle (x,y) are the center point of the V shape.
     */
    public static void drawBird (Graphics2D g, int x, int y, int time)
    {
        g.setColor(Color.black);
        double angleRadians = 0;
        x += (int) ((time - INTERVAL) * .08);
        // draws the V wing shape
        if ((time / 250) % 2 == 0)
        {
            angleRadians = Math.toRadians(45);
            int wingLength = 40;
            int x1 = x + (int) (Math.cos(angleRadians) * wingLength);
            int y1 = y - (int) (Math.sin(angleRadians) * wingLength);
            int x2 = x - (int) (Math.cos(-angleRadians) * wingLength);
            int y2 = y - (int) (Math.sin(angleRadians) * wingLength);
            g.drawLine(x, y, x1, y1);
            g.drawLine(x, y, x2, y2);
        }
        // draws the flat wing shape
        else
        {
            angleRadians = Math.toRadians(180);
            int wingLength = 40;
            int x1 = x + (int) (Math.cos(angleRadians) * wingLength);
            int y1 = y;
            int x2 = x - (int) (Math.cos(-angleRadians) * wingLength);
            int y2 = y;
            g.drawLine(x, y, x1, y1);
            g.drawLine(x, y, x2, y2);
        }

    }

    /*
     * draws the bud of a flower. consists of 2 concentric circle. outer = random color. inner = black size of flower is
     * dependent on 'time'
     */
    public static void drawFlower (Graphics2D g, int x1, int y1, int time)
    {
        int stemTop = 500 - (int) (INTERVAL * 2 * 0.017);

        // random color for outer flower
        Random rand = new Random();
        float red = rand.nextFloat();
        float green = rand.nextFloat();
        float blue = rand.nextFloat();
        Color random = new Color(red, green, blue);
        g.setColor(random);

        // draw outer flower
        int size = (int) (time * 0.025) - 312;
        g.fillOval(x1 - size / 2, stemTop - size / 2, size, size);

        // draw inner flower - set color to black
        int x2 = x1 + size / 4;
        int y2 = stemTop + size / 4;
        g.setColor(Color.BLACK);
        g.fillOval(x2 - size / 2, y2 - size / 2, size / 2, size / 2);
    }

    /**
     * draws a hole set in the middle of the center dirt with given color
     */
    public static void drawHole (Graphics2D g, Color color)
    {
        g.setColor(color);
        g.fillOval(450, 475, 50, 50);
    }

    /**
     * draws the ground with given color
     */
    public static void drawGround (Graphics2D g, Color color)
    {
        g.setColor(color);
        g.fillRect(0, 450, 1000, 250);
    }

    /**
     * draws the sky with given color
     */
    public static void drawSky (Graphics2D g, Color color, int height, int width)
    {
        g.setColor(color);
        g.fillRect(0, 0, width, height);
    }

    /**
     * alternate drawSky method for a darkening sky based on time, going down to 0,0,0 for black
     */
    public static void drawSky (Graphics2D g, Color color, int height, int width, int time)
    {
        int timeReset = (int) (time - (INTERVAL * 5));
        timeReset *= 0.04;
        int r = Math.max(0, color.getRed() - timeReset);
        int gr = Math.max(0, color.getGreen() - timeReset);
        int b = Math.max(0, color.getBlue() - timeReset);
        Color colorNew = new Color(r, gr, b);
        g.setColor(colorNew);
        g.fillRect(0, 0, width, height);
    }

    /**
     * draws a dynamically moving and sized sun (oval and sun beams) sun is dynamic width and height. calculated by
     * taking the static sun size multiplied by time in msec mulitplied by a dappening factor sun beams. 24 lines drawn
     * from 0-360 degrees. each line originates at center of sun oval and dynamic length set to above sun size
     * calculation
     */
    public static void drawSun (Graphics2D g, int time, int size, int x, int y)
    {
        // dynamically calculated sunSize
        int sunSize = (int) (size * time * .0005);

        // initial point of sunbeams are at the center point of the sun's oval
        int x1 = x + sunSize / 2;
        int y1 = y + sunSize / 2;
        int beamLength = sunSize;
        // boolean for alternating beam color. yellow and orange
        boolean beamColor = false;

        // draws 12 sunbeams with size proportional to sun oval, ranging 0-360 degrees
        for (int i = 0; i <= 360; i += 15)
        {
            if (!beamColor)
            {
                g.setColor(SUN);
            }
            else
            {
                g.setColor(Color.ORANGE.darker());
            }
            int x2 = x1 + (int) (Math.cos(Math.toRadians(i)) * beamLength);
            int y2 = y1 + (int) (Math.sin(Math.toRadians(i)) * beamLength);
            g.drawLine(x1, y1, x2, y2);
            beamColor = !beamColor;
        }
        // sun oval drawn after beams so it 'sits' above them
        g.setColor(SUN);
        g.fillOval(x, y, sunSize, sunSize);
    }

    /**
     * draws a dynamically growing stem using the parameter time. stem needs to grow in the negative y direction
     */
    public static void drawStem (Graphics2D g, int time, int size)
    {
        g.setColor(STEM);
        g.fillRect(470, (int) (500 - time * .017), 10, (int) (time * .017));
    }

    /**
     * draws a dynamically moving cloud with respect to the x coordinate method assuming 'time' is passed through the
     * method call
     */
    public static void drawCloud (Graphics2D g, int time, int x, int y)
    {
        g.setColor(Color.WHITE);
        // draws bottom half of cloud
        for (int i = 0; i < 3; i++)
        {
            int cloudX = x + (i * 50);
            g.fillOval(cloudX, y, CLOUD_SIZE, (int) (CLOUD_SIZE * 0.75));
        }
        // draws top half of cloud
        g.fillOval(x + 25, y - 40, CLOUD_SIZE, (int) (CLOUD_SIZE * 0.75));
        g.fillOval(x + 75, y - 40, CLOUD_SIZE, (int) (CLOUD_SIZE * 0.75));
    }

    /**
     * draws rain starting at given x,y position. uses random number generator to call drawRainDrop method 20 times
     */
    public static void drawRain (Graphics2D g, int time, int x, int y)
    {
        Random rand = new Random();
        int random = rand.nextInt(6);
        for (int i = 1; i <= 10; i++)
        {
            int rainPosition = y + (random * i) * (RAIN_LENGTH + 10);
            // System.out.println(rainPosition);
            drawRainDrop(g, x, rainPosition);
            drawRainDrop(g, x + 45, rainPosition + 15);
        }
    }

    /**
     * draws 2 rain drops off-set by 1 pixel in the x direction. creates a 'thicker' rain drop only draws rain if the y
     * value is <= 460 (position of the hole in the dirt)
     */
    public static void drawRainDrop (Graphics2D g, int x, int y)
    {
        g.setColor(Color.BLUE);
        // starting point of rain drops
        int x1 = x + 60;
        int y2 = y + CLOUD_SIZE;

        if (y2 <= 460)
        {
            g.drawLine(x1, y2, x1, y2 + RAIN_LENGTH);
            g.drawLine(x1 + 1, y2, x1 + 1, y2 + RAIN_LENGTH);
        }
    }

    /**
     * Draws the title in the upper-left corner
     */
    public static void drawTitle (Graphics2D g, String title)
    {
        // Use a larger than default font for the title
        Font f = g.getFont();
        g.setFont(new Font(f.getName(), f.getStyle(), FONT_SIZE));
        g.drawString(title, FONT_SIZE, FONT_SIZE);
    }

}
