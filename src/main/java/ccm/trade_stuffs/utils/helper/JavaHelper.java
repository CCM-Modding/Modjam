/**
 * CCM Modding, ModJam
 */
package ccm.trade_stuffs.utils.helper;

/**
 * JavaHelper
 * <p>
 * 
 * @author Captain_Shadows
 */
public final class JavaHelper
{
    public static final String s = " ";

    public static String tileCase(final String input)
    {
        if (input.contains(s))
        {
            final StringBuilder finished = new StringBuilder();
            int lastSpace = -1;
            String currentString = input;
            for (int i = 0; i < input.length(); i++)
            {
                final int first = lastSpace + 1;
                final int last = lastSpace - 1;

                finished.append(currentString.substring(first, 1).toUpperCase());
                lastSpace = currentString.indexOf(s);
                finished.append(currentString.substring(1, lastSpace - 1));
                finished.append(s);
                if (!(last <= currentString.length()))
                {
                    currentString = currentString.substring(last, currentString.length());
                }
                else
                {
                    break;
                }
            }
            return finished.toString();
        }
        else
        {
            return input.substring(0, 1).toUpperCase() + input.substring(1);
        }
    }
}