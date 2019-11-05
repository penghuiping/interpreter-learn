## 符号说明

1. 星号*

表示0个或者多个重复

2. 小括号()

匹配小括号内的字符串，可以是一个，也可以是多个，常跟“|”（或）符号搭配使用，是多选结构的

## 语法规则

```
factor0               -> integer|string
factor                -> factor0 | variable| LeftParenthesis expr RightParenthesis
unary_factor          -> ((PLUS | MINUS) unary_factor) | factor
term                  -> unary_factor ((MUL | DIV | MOD) unary_factor)*
expr                  -> term ((PLUS | MINUS) term)*

variable              -> identifier
variable_declare      -> (var|let) variable(,variable)*

function_name         -> function variable
function_params       -> LeftParenthesis variable? (,variable)* RightParenthesis
function_body         -> {statement_list (return variable)?}
function_declare      -> function_name function_params function_body
function_invoke       -> variable function_params 
            
assign_statement      -> (variable_declare|variable) assign (expr|function_invoke)
statement_list        -> ((assign_statement|function_invoke|function_declare) SEMI)+ 
program               -> statement_list

```







