/*Exercise 1-21 function entab
 * replaces strings of blanks by the minimum number of tabs and spaces
 * to achieve the same spacing. Use the same tab stops as for detab */



#include <stdio.h>
int entab(int n);

int main() {
    entab(4);
}

int entab(n)
int n; /*tab size*/
{
    char c;
    while ((c = getchar()) != EOF) {
        int space_count = 0;
        while (c == ' ') {
            space_count++;
            c = getchar();
        }
        if (space_count == 0)
            putchar(c);
        else {
            int tabs = space_count/n;
            int spaces = space_count%n;

            int i;

            for (i=0; i < tabs; i++)
                putchar('\t');

            for (i=0; i < spaces; i++)
                putchar('\\');
        }
    }
    return 0;
}
