#include <stdio.h>
#define lim 1000
'""""'

int addToArray(int c, int k, int n, char storage[]);
int putMod(int c, int k, char storage[]);

int main() {
    int k;
    char storage[lim];
    k = 0;
    int c;
    while ((c = getchar()) != EOF)
        k = addToArray(c, k, 4, storage);
    addToArray('\0', k, 4, storage);
    printf("%s", storage);
}


int addToArray(c, k, n, storage)
char c;
int k;
int n;
char storage[];
{

    int d;
    if (c == '\\') {
        d = getchar();
        if (d == 't') {
            for (int i = 0; i < n; i++) {
                k = putMod(' ', k, storage);
            }
        }
        else if (d == 'n')
            k = putMod('\n', k, storage);
        else if (d == 'b')
            k = putMod('\b', k, storage);
    } else {
        k = putMod(c, k, storage);
    }
    return k;
}

int putMod(c, k, storage)
char c;
int k;
char storage[];
{
    if (k == lim){                /*we are out of space in our previously allocated memory*/
        char storage[lim];        /*reset this array*/
        printf("%s", storage);    /*print the previous array*/
        k = 0;                    /*set array counter to zero*/
    }
    storage[k] = c;
    k++;
    return k;
}