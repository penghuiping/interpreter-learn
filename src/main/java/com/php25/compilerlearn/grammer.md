## 数据
data: digit|string|bool

## 表达式
express: label|data -> +|-|*|/|% -> label|data 


## 声明变量与赋值
assign: label->=->label|data|express->;


## 函数调用

func_invoke: label->(->label|data|express->)->;








a=2;
println(a>0);



