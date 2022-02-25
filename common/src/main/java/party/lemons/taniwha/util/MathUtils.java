package party.lemons.taniwha.util;

import net.minecraft.core.Direction;
import net.minecraft.util.Mth;

import java.util.Random;
import java.util.UUID;

public class MathUtils
{
    public static final Direction[] HORIZONTALS = new Direction[]{Direction.NORTH, Direction.EAST, Direction.WEST, Direction.SOUTH};

    public static float approachValue(float current, float target, float step)
    {
        if(current == target) return target;

        if(current < target)
        {
            return Math.min(current + step, target);
        }
        return Math.max(current - step, target);
    }

    public static double approachValue(double current, double target, double step)
    {
        if(current < target)
        {
            return Math.min(current + step, target);
        }
        return Math.max(current - step, target);
    }

    public static int colourBoost(int color, int rShift, int gShift, int bShift)
    {
        int r = color >> 16 & 0xFF;
        int g = color >> 8 & 0xFF;
        int b = color & 0xFF;

        color = (Math.max(0, Math.min(0xFF, r + rShift)) << 16) + Math.max(0, Math.min(0xFF, g + gShift) << 8) + Math.max(0, Math.min(0xFF, b + bShift));

        return color;
    }

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