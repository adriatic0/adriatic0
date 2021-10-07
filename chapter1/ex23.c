/* write a program to remove all comments from a C program.
 * Don't forget to handle quoted strings and character constants properly. */

#include <stdio.h>

void process(char c);
void comment(char c);
void escape_char(char c);
void double_quote(char c);
void single_quote(char c);
void open_bracket(char c);
void close_bracket(char c);
void check_eof();

char stack[10] = {0};
int pointer = -1; /*for stack*/

char status = 'b'; /* b = body,  q = quote, c = comment' */
int line_count = 1;
int last_bracket_line = -1;
char out_arr[2] = {0};


int main() {
    char c;

    while ((c = getchar()) != EOF) {
        out_arr[0] = 0;
        out_arr[1] = 0;
        process(c);

    }
    check_eof();

}
void process(char c) {

    /*comment*/
    if (c=='\n'){
        line_count++;
    }

    if (c=='/') {
      comment(c);
    }

    /*escape characters*/
    else if (c=='\\') {
       escape_char(c);
    }

    /*double quote*/
    else if (c == '\"') {
       double_quote(c);
    }

    /*single quote*/
    else if (c =='\'') {
       single_quote(c);
    }

    /*open bracket*/
    else if ((c == '{') || (c == '[') || (c =='(')) {
        open_bracket(c);
    }

    /*close bracket*/
    else if((c=='}') || (c==']') || (c==')')) {
        close_bracket(c);
    }

    else {
        out_arr[0] = c;
    }


    printf(out_arr);
}

void single_quote(char c){
    if (status != 'c') {
        int closing_quote = stack[pointer] == '\'';
        if (closing_quote) {
            stack[pointer] = 0;
            pointer--;
            status='b';
        } else {
            pointer++;
            stack[pointer] = '\'';
            status = 'q';
        }
        out_arr[0] = '\'';
    }
}


void double_quote(char c){
    if (status != 'c') {
        int closing_quote = stack[pointer] == '\"';
        if (closing_quote) {
            stack[pointer] = 0;
            pointer--;
            status = 'b';
        } else {
            pointer++;
            stack[pointer] = '\"';
            status = 'q';
        }
        out_arr[0] = '\"';
    }
};

void escape_char(char c){

    char out_arr[2] = {0};

    char next_char = getchar();
    if (next_char == 'n'){
        c = '\n';
    }

    if (next_char == 't') {
        c = '\t';
    }

    if (next_char == '\\'){
        c = '\\';
    }

    if (next_char == '\"') {
        c = '\"';
    }

    if (next_char == '\'') {
        c = '\'';
    }
    if (status == 'b') {
        out_arr[0] = c; /* the updated escape character */
    }

    if (status == 'q') {
        out_arr[0] = '\\';
        out_arr[1] = next_char; /* \ and the new char if in quote */
    }
};


void comment(char c){

    if (status=='c') {
        status = 'b';
    }

    else if (status == 'b') {
        status = 'c';
    }

    out_arr[0] = '/';

};

void open_bracket(char bracket){


    if (status == 'b') {
        pointer++;
        stack[pointer] = bracket;
        last_bracket_line = line_count;
    }

    if ((status == 'q') || (status == 'b')) {
        out_arr[0] = bracket;
    }
};


void close_bracket(char bracket){

    int correct_bracket = (((stack[pointer] == '{') && bracket == '}') ||
                          ((stack[pointer] == '[') && bracket == ']')  ||
                          ((stack[pointer] == '(') && bracket == ')'));

    if (status == 'b') {
        if (correct_bracket) {
            stack[pointer] = 0;
            pointer--;
        }
        else {
            printf("\n\n\n*************************************************\n");
            printf("Syntax Error: Missing closing bracket for %c on line %d\n", bracket, last_bracket_line);
            printf("*************************************************\n\n\n");

        }
    }

    if ((status == 'q') || (status == 'b')) {
        out_arr[0] = bracket;
    }
};



void check_eof() {
   if (pointer != -1) {
       printf("\n\n\n*************************************************\n");
       printf("Missing closing bracket for %c on line %d\n", stack[pointer], last_bracket_line);
       printf("*************************************************\n\n\n");

   }

   if (status == 'q') {
       printf("\n\n\n*************************************************\n");
       printf("Missing closing quote\n");
       printf("*************************************************\n\n\n");

   }

   if (status == 'c') {
       printf("\n\n\n*************************************************\n");
       printf("Missing closing comment /\n");
       printf("*************************************************\n\n\n");

   }
}
