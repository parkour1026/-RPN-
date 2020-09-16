public static String[] Postfixnotation(String str)
{
        String s="";
        char a[]=new char[100];
        String converted[]=new String[100];
        int top=-1,j=0;
        for (int i=0; i<str.length(); i++)
        {
                //System.out.println(str.charAt(i));
                if ("0123456789".indexOf(str.charAt(i))>=0)//数字直接入库
                {
                        s="";
                        for (; i<str.length()&&"0123456789".indexOf(str.charAt(i))>=0; i++)
                        {
                                s=s+str.charAt(i);
                        }
                        i--;
                        converted[j]=s;
                        j++;
                }
                else if ("(".indexOf(str.charAt(i))>=0)//如果是（直接塞入栈
                {
                        top++;
                        a[top]=str.charAt(i);
                }
                else if (")".indexOf(str.charAt(i))>=0)//如果是）直到（为止 依次入库
                {
                        for (;;)
                        {
                                if (a[top]!='(')
                                {
                                        converted[j]=a[top]+"";
                                        j++;
                                        top--;
                                }
                                else
                                {
                                        top--;
                                        break;
                                }
                        }
                }
                else if("^".indexOf(str.charAt(i))>=0) {
                        top++;
                        a[top]=str.charAt(i);

                }
                else if ("*\\/".indexOf(str.charAt(i))>=0)//对比栈顶比自己低了话入栈，否则进库
                {
                        if (top==-1)
                        {
                                top++;
                                a[top]=str.charAt(i);
                        }
                        else
                        {
                                if ("*\\/".indexOf(a[top])>=0)
                                {
                                        converted[j]=a[top]+"";
                                        j++;
                                        a[top]=str.charAt(i);
                                }
                                else if ("(".indexOf(a[top])>=0)
                                {
                                        top++;
                                        a[top]=str.charAt(i);
                                }
                                else if ("+-".indexOf(a[top])>=0)
                                {
                                        top++;
                                        a[top]=str.charAt(i);
                                }
                        }
                }
                else if ("+-".indexOf(str.charAt(i))>=0)//只要不是（就从栈依次入库然后 把自己放入栈
                {
                        if (top==-1)
                        {
                                top++;
                                a[top]=str.charAt(i);
                        }
                        else
                        {

                                if ("(".indexOf(a[top])>=0)
                                {
                                        top++;
                                        a[top]=str.charAt(i);
                                }
                                else {
                                        for (;;)
                                        {
                                                if(top==-1) {
                                                        top++;
                                                        a[top]=str.charAt(i);
                                                        break;
                                                }
                                                else if (a[top]!='(')
                                                {
                                                        converted[j]=a[top]+"";
                                                        j++;
                                                        top--;
                                                }
                                                else
                                                {
                                                        top++;
                                                        a[top]=str.charAt(i);
                                                        break;
                                                }
                                        }
                                }
                        }
                }
        }
        for (; top!=-1;)
        {
                converted[j]=a[top]+"";
                j++;
                top--;
        }
        return converted;
}
public static String Result(String str[])
{
        String Result[]=new String[100];
        int Top=-1;
        for (int i=0; str[i]!=null; i++)
        {
                if ("+-*\\/^".indexOf(str[i])<0)
                {
                        Top++;
                        Result[Top]=str[i];
                }
                if ("+-*\\/^".indexOf(str[i])>=0)
                {
                        BigDecimal n=new BigDecimal("0");
                        BigDecimal x=new BigDecimal(Result[Top]);
                        Top--;
                        BigDecimal y=new BigDecimal(Result[Top]);
                        Top--;
                        if ("^".indexOf(str[i])>=0)
                        {
                                n=y.pow(x.intValue());
                                Top++;
                                Result[Top]=n.toPlainString();
                        }
                        if ("-".indexOf(str[i])>=0)
                        {
                                n=y.subtract(x);
                                Top++;
                                Result[Top]=n.toPlainString();
                        }
                        if ("+".indexOf(str[i])>=0)
                        {
                                n=y.add(x);
                                Top++;
                                Result[Top]=n.toPlainString();
                        }
                        if ("*".indexOf(str[i])>=0)
                        {
                                n=y.multiply(x);
                                Top++;
                                Result[Top]=n.toPlainString();
                        }
                        if ("\\/".indexOf(str[i])>=0)
                        {
                                if (x.compareTo(new BigDecimal("0"))==0)
                                {
                                        n=n.ZERO;
                                        Top++;
                                        Result[Top]=n.toPlainString();
                                }
                                else
                                {
                                        n=y.divide(x,10,BigDecimal.ROUND_HALF_UP);
                                        Top++;
                                        Result[Top]=n.toPlainString();
                                }
                        }
                }
        }
        return Result[Top];
}
}
