#include <stdio.h>

int main()
{
    int c, total;
    total = 0;

    while ((c = getchar()) != EOF) 
        if (c == '\b')
            ++total;
        if (c == '\n')
            ++total;
        if (c == '\t')
            ++total;
    return total;

    }
}