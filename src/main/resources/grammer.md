## 数据
data: digit|string|bool|term

## 表达式
expression: data -> +|-|*|/|% -> data

many expression: (expression)-> +|-|*|/|% -> (expression)


## 声明变量与赋值
assign: label->=->label|data|express->;


## 函数调用

func_invoke: label->(->label|data|express->)->;












