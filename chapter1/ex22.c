/* write a program to remove all comments from a C program.
 * Don't forget to handle quoted strings and character constants properly. */
#include <stdio.h>
int comment_remover();
int main() {
    comment_remover();
}
/* Removes all comments from stdin*/
int comment_remover() {

    int in_quote; /*inside a quote*/
    int in_comment = 0;

    int c;
    while ((c=getchar()) != EOF) {
        char d = c;
        /*inside string outside of a comment*/
        if ((c == '/') && in_quote && !in_comment) {
            putchar(c);
        }

        else if ((c=='/') && !in_quote) {
            in_comment = !in_comment;
        }

        else if ((c=='\"')) {
            if (!in_comment){
                putchar(c);
            }
            in_quote = !in_quote;
        }
        /*escape chars*/
        else if ((c=='\\')){
            c = getchar();
            if (c=='n')
                c = '\n';
            else if (c == 't')
                c = '\t';
            else if (c == '\\')
                c = '\\';
            if (!in_comment) {
                putchar(c);
            }
        }
         else if (!in_comment){
            putchar(c);
        }

    }

    return 0;
}