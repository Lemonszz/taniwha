package party.lemons.taniwha.util;

import com.google.common.collect.Lists;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class MathUtils
{
    //Non vertical directions
    public static final Direction[] HORIZONTALS = new Direction[]{Direction.NORTH, Direction.EAST, Direction.WEST, Direction.SOUTH};

    /*
        Return a random horizontal direction
     */
    public static Direction randomHorizontal(RandomSource randomSource)
    {
        return HORIZONTALS[randomSource.nextInt(HORIZONTALS.length)];
    }

    /*
        Gets a list of horizontal directions in a random order.
        Don't modify the list pls
     */
    public static List<Direction> randomOrderedHorizontals()
    {
        Collections.shuffle(randomHorizontals);
        return randomHorizontals;
    }
    private static final List<Direction> randomHorizontals = Lists.newArrayList(HORIZONTALS);

    /*
        returns if a direction next to the another
     */
    public static boolean isAdjacentDirection(Direction current, Direction check)
    {
        return check != current.getOpposite();
    }

    /*
        Returns the current value +/- the step, approaching the target
        clamps at target.
     */
    public static float approachValue(float current, float target, float step)
    {
        if(current == target) return target;

        if(current < target)
        {
            return Math.min(current + step, target);
        }
        return Math.max(current - step, target);
    }

    /*
        Returns the current value +/- the step, approaching the target
        clamps at target.
    */
    public static double approachValue(double current, double target, double step)
    {
        if(current < target)
        {
            return Math.min(current + step, target);
        }
        return Math.max(current - step, target);
    }

    /*
        Shifts a color
     */
    public static int colourBoost(int color, int rShift, int gShift, int bShift)
    {
        int r = color >> 16 & 0xFF;
        int g = color >> 8 & 0xFF;
        int b = color & 0xFF;

        color = (Math.max(0, Math.min(0xFF, r + rShift)) << 16) + Math.max(0, Math.min(0xFF, g + gShift) << 8) + Math.max(0, Math.min(0xFF, b + bShift));

        return color;
    }

    /*
        Rotates a degrees angle towards a point
     */
    public static float changeAngle(float from, float to, float max)
    {
        float f = Mth.wrapDegrees(to - from);
        if(f > max)
        {
            f = max;
        }

        if(f < -max)
        {
            f = -max;
        }

        float g = from + f;
        if(g < 0.0F)
        {
            g += 360.0F;
        }else if(g > 360.0F)
        {
            g -= 360.0F;
        }

        return g;
    }

    /*
        returns a UUID created from a string
     */
    public static UUID uuidFromString(String string)
    {
        Random random = new Random(string.hashCode());

        byte[] randomBytes = new byte[16];
        random.nextBytes(randomBytes);
        randomBytes[6] &= 0x0f;  /* clear version        */
        randomBytes[6] |= 0x40;  /* set to version 4     */
        randomBytes[8] &= 0x3f;  /* clear variant        */
        randomBytes[8] |= 0x80;  /* set to IETF variant  */

        return UUID.nameUUIDFromBytes(randomBytes);
    }
}