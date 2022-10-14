import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        String userIn = in.nextLine().trim();
        System.out.println(calc(userIn));
        in.close();
    }

    public static String calc(String input) {
        input=input.replaceAll(" +","");
        input=input.toUpperCase();
        String[] userSplitted = input.split("[+-/*/]");
        boolean operandIsUnic= input.matches("[^+-/*/][+-/*/][^+-/*/]");

        if (userSplitted.length == 1)
            throw new RuntimeException("//т.к. строка не является математической операцией");
        if (userSplitted.length > 2||!operandIsUnic)
            throw new RuntimeException("//т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");

        boolean aIsRoman = userSplitted[0].matches("[IVX]+");
        boolean bIsRoman = userSplitted[1].matches("[IVX]+");


        if ((aIsRoman&& !bIsRoman)||(!aIsRoman&& bIsRoman) )
            throw new RuntimeException("// т.к. используются одновременно разные системы счисления");

        char operand=input.charAt(userSplitted[0].length());
        int result;
        int a, b;
        if (!aIsRoman){
            try {
                a = Integer.parseInt(userSplitted[0]);
            }catch(Exception e){
                throw new RuntimeException("Введено некорректное число");
            }
            try{
                b = Integer.parseInt(userSplitted[1]);
            }catch(Exception e){
                throw new RuntimeException("Введено некорректное число");
            }

        }else{
            a=RomanToInt(userSplitted[0]);
            b=RomanToInt(userSplitted[1]);
        }
        if (!(a>0&&a<=10)||!(b>0&&b<=10))
            throw new RuntimeException("//т.к. калькудятор принимает на вход числа от 1 до 10 включительно");
        switch (operand) {
            case '+' -> result = (a + b);
            case '-' -> result = (a - b);
            case '/' -> result = (a / b);
            case '*' -> result = (a * b);
            default -> throw new RuntimeException("//т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }


        if (!aIsRoman)
            return Integer.toString(result);
        else
            if (result>0)
                return IntToRoman(result);
            else
                throw new RuntimeException("//т.к. в римской системе нет отрицательных чисел");
    }


    public static int RomanToInt(String val) {

        if (val.endsWith("IV")) return 4 + RomanToInt(val.substring(0, val.length() - 2));
        if (val.endsWith("IX")) return 9 + RomanToInt(val.substring(0, val.length() - 2));
        if (val.endsWith("VX")) throw new NumberFormatException("Введено некорректное число");
        if (val.endsWith("X")) return 10 + RomanToInt(val.substring(0, val.length() - 1));
        if (val.endsWith("V")) return 5 + RomanToInt(val.substring(0, val.length() - 1));
        if (val.endsWith("I")) return 1 + RomanToInt(val.substring(0, val.length() - 1));
        if(val.isEmpty())return 0;
        throw new NumberFormatException("Введено некорректное число");
    }

    public static String IntToRoman(int val) {
        String result="";
        result+=Roman.C.RepeatName(val / 100);val%=100;
        result+=Roman.XC.RepeatName(val / 90);val%=90;
        result+=Roman.L.RepeatName(val / 50);val%=50;
        result+=Roman.XL.RepeatName(val / 40);val%=40;
        result+=Roman.X.RepeatName(val / 10);val%=10;
        result+=Roman.IX.RepeatName(val / 9);val%=9;
        result+=Roman.V.RepeatName(val / 5);val%=5;
        result+=Roman.IV.RepeatName(val / 4);val%=4;
        result+= Roman.I.RepeatName(val);
        return result;
    }
}
