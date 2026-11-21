package gp;

//import gp*;
public class Gp_test
{

    public static void main(String[] args)
    {
        final  int VENDOR_ID   = 0x0810;

        int i, result;
        Gp gamepad;
        Gp_values curr_v;


        gamepad = new Gp();

        result = gamepad.gp_open(VENDOR_ID, 0x0001);

        if(result == -1) System.out.println("gamepad not opened");

        for(;;)
        {
            // System.out.print("\033[H\033[2J");
            // System.out.flush();

            System.out.println("======================");

            curr_v = gamepad.get_values();

            for(i = 0; i < curr_v.buttons.length; i++)
            {
                switch(i)
                {
                    case 0:
                    {
                        if(curr_v.buttons[i]) System.out.println("TL");
                    }
                    break;

                    case 1:
                    {
                        if(curr_v.buttons[i]) System.out.println("TR");
                    }
                    break;

                    case 2:
                    {
                        if(curr_v.buttons[i]) System.out.println("ZL");
                    }
                    break;

                    case 3:
                    {
                        if(curr_v.buttons[i]) System.out.println("ZR");
                    }
                    break;

                    case 4:
                    {
                        if(curr_v.buttons[i]) System.out.println("SELECT");
                    }
                    break;

                    case 5:
                    {
                        if(curr_v.buttons[i]) System.out.println("START");
                    }
                    break;

                    case 6:
                    {
                        if(curr_v.buttons[i]) System.out.println("ANALOG");
                    }
                    break;

                    case 7:
                    {
                        if(curr_v.buttons[i]) System.out.println("1");
                    }
                    break;

                    case 8:
                    {
                        if(curr_v.buttons[i]) System.out.println("2");
                    }
                    break;

                    case 9:
                    {
                        if(curr_v.buttons[i]) System.out.println("3");
                    }
                    break;

                    case 10:
                    {
                        if(curr_v.buttons[i]) System.out.println("4");
                    }
                    break;

                    case 11:
                    {
                        if(curr_v.buttons[i]) System.out.println("N");
                    }
                    break;

                    case 12:
                    {
                        if(curr_v.buttons[i]) System.out.println("E");
                    }
                    break;

                    case 13:
                    {
                        if(curr_v.buttons[i]) System.out.println("S");
                    }
                    break;

                    case 14:
                    {
                        if(curr_v.buttons[i]) System.out.println("W");
                    }
                    break;

                    case 15:
                    {
                        if(curr_v.buttons[i]) System.out.println("L_PRESSED");
                    }
                    break;

                    case 16:
                    {
                        if(curr_v.buttons[i]) System.out.println("R_PRESSED");
                    }
                    break;
                }
            }

            if(curr_v.analog_mode)
            {
                System.out.println("LX:" + curr_v.LX);
                System.out.println("LY:" + curr_v.LY);
                System.out.println("RX:" + curr_v.RX);
                System.out.println("RY:" + curr_v.RY);
            }
        }
    }
}



