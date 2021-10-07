#include <stdio.h>
#define N 30

int fold_ka(int n);

int main() {
    fold_ka(N);
}

fold_ka(n)
int n;
{
int col_count = 0;
char c;
while ((c = getchar()) != EOF) {
    int space_count = 0;
    while (c == ' '){
        space_count++;
        c = getchar();
    }
    int next_col = col_count + space_count;

    if ((next_col) >= n) {
        putchar('\n');
        col_count = 0;
    } else {
        while (space_count > 0) {
            putchar(' ');
            col_count++;
            space_count--;
        }
        putchar(c);
        col_count++;
    }
    }
}