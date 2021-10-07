#include <stdio.h>
int main()
{
    int c;
    for (c = getchar(); c != EOF; c++)
        if (c == '\t')
            putchar('>');
            putchar('\b');
            putchar('-');
        if (c == '\b') 
            putchar('<');
            putchar('\b');
            putchar('-');
        
        if (c != '\b')
            if (c != '\t')
                putchar(c);

}
