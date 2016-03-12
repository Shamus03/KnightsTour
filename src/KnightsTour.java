import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class KnightsTour
{
    public static final int GRID_SIZE = 50;
    public static final int LINE_SIZE = 5;

    public static void main(String[] args)
    {
        int size;
        boolean cycle;

        try
        {
            size = Integer.parseInt(args[0]);
        }
        catch (Exception e)
        {
            size = 8;
        }

        cycle = size < 0;

        size = Math.abs(size);

        KnightBoard board = new KnightBoard(size, cycle);

        ArrayList<GridNode<Point>> tour = board.findTour(0, 0);

        createImage(tour, size, cycle ? "cycle" : "path");
    }

    public static void createImage(ArrayList<GridNode<Point>> tour, int size,
        String suffix)
    {
        int imageSize = GRID_SIZE * size + LINE_SIZE * (size + 1);
    
        BufferedImage bi = new BufferedImage(imageSize, imageSize,
            BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = bi.createGraphics();

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, imageSize, imageSize);

        g.setColor(Color.WHITE);
        int i;
        int j;
        for (i = 0; i < size; i++)
        {
            for (j = 0; j < size; j++)
            {
                g.fillRect(LINE_SIZE * (i + 1) + GRID_SIZE * i,
                           LINE_SIZE * (j + 1) + GRID_SIZE * j,
                           GRID_SIZE,
                           GRID_SIZE);
            }
        }
        
        g.setColor(Color.RED);
        Point p;
        int xPosLast = LINE_SIZE + GRID_SIZE / 2;
        int yPosLast = LINE_SIZE + GRID_SIZE / 2;
        int xPos;
        int yPos;
        int x;
        int y;

        boolean first = true;
        for (GridNode<Point> node : tour)
        {
            if (first)
            {
                first = false;
                continue;
            }

            p = node.getElement();

            x = (int) p.getX();
            y = (int) p.getY();

            xPos = GRID_SIZE / 2 + LINE_SIZE * (x + 1) + GRID_SIZE * x;
            yPos = GRID_SIZE / 2 + LINE_SIZE * (y + 1) + GRID_SIZE * y;

            g.drawLine(xPosLast, yPosLast, xPos, yPos);

            xPosLast = xPos;
            yPosLast = yPos;
        }

        try 
        {
            File folder = new File("image");
            if (folder.exists() || !folder.exists() && folder.mkdir())
            {
                ImageIO.write(bi, "PNG", new File("image/Tour" + size
                    + "x" + size + "_" + suffix + ".png"));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
