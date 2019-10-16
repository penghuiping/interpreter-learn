## 符号说明

1. 星号*

表示0个或者多个重复

2. 小括号()

匹配小括号内的字符串，可以是一个，也可以是多个，常跟“|”（或）符号搭配使用，是多选结构的

## 语法规则


1. factor

```
factor : INTEGER | LeftBracket expr RightBracket
```

2. term

```
term : factor ((MUL | DIV) factor)*
```

3. expr

```
 expr : term ((PLUS | MINUS) term)*
```











