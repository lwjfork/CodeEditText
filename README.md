# CodeEditText
验证码，密码输入框，文本支持明文和密码及透明三种形式展示，背景支持下划线，填充色块，边框，透明四种


##  引入

```
  implementation 'io.github.lwjfork:CodeEditText:1.0.5'
  
<<<<<<< HEAD
=======

>>>>>>> 4ebe4af283c3e57d3b746e6a0f575a5edaece24d
```


## 原理
>> 继承EditText，测量宽高后，根据每块间距、EditText的宽度以及可输入最大长度计算出每块区域的left top right bottom

>> 将EditText的背景取消、字体颜色和光标透明不显示，监听输入并根据计算的每块的left top right bottom 自行绘制文本和背景

## 注意事项(优点)
1. 此EditText 在输入文本方面没有对除最大长度以外做任何限制，即需要自己控制输入数字、汉字、空格等内容限制
2. 此EditText 键盘的控制需要自行控制，可以使用系统键盘，也可以自定义键盘


# 属性讲解
## 背景样式设置

属性名 | 属性说明
:---:|:---:
blockNormalColor |  正常状态下的颜色 默认为字体颜色
blockFocusColor |   待输入且EditText获取了焦点时颜色  默认值为blockNormalColor
blockErrorColor |   错误状态下颜色  默认值为blockNormalColor
blockLineWidth |   绘制边框或者下划线的线的宽度 默认1dp
blockSpace |   块与块之间的间距 默认0
blockShape |   块的样式暂时支持四种  none solid stroke underline 默认值为none


blockShape 属性值 | 属性说明
:---:|:---:
none |  什么都不绘制，空白
solid |   填充色块
stroke |   绘制边框
underline |   下划线

## 文本样式设置


属性名 | 属性说明
:---:|:---:
codeTextColor |  文本或者密码圆点颜色
codeTextSize |   文本尺寸，当 codeInputType 为 text 时生效。 默认为12sp
maxCodeLength |   输入的最大长度 默认为6
dotRadius |    密码圆点半径，当codeInputType为 password 生效。默认为5dp
codeInputType |   password （密码圆点展示）text 明文展示。 默认text

codeInputType 属性值 | 属性说明
:---:|:---:
none |  什么都不绘制，空白
text |   明文
password |   密码圆点展示

## 代码混淆

```
  -keep class com.lwjfork.code.** { *;}
  -dontwarn com.lwjfork.code.**
```

## 效果图


<img src='https://github.com/lwjfork/CodeEditText/blob/master/example.jpg' height='600'/>

## Change list

## 1.0.4
1. fix 当code长度超过6位时，绘制错误


## 1.0.2
1. 支持自定义样式，具体参考demo里的 CustomStyleActivity
2. 添加addCharSequence 方法

## 1.0.1 
1. 添加 delete 和 addChar 方法 方便自定义键盘


