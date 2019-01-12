# CodeEditText
验证码，密码输入框，文本支持明文和密码及透明三种形式展示，背景支持下划线，填充色块，边框，透明四种

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
blockNormalColor |  正常状态下的颜色
blockFocusColor |   待输入且EditText获取了焦点时颜色
blockErrorColor |   错误状态下颜色
blockLineWidth |   绘制边框或者下划线的线的宽度
blockSpace |   块与块之间的间距
blockShape |   块的样式暂时支持四种  none solid stroke underline


blockShape 属性值 | 属性说明
:---:|:---:
none |  什么都不绘制
solid |   填充色块
stroke |   绘制边框
underline |   下划线

## 文本样式设置


属性名 | 属性说明
:---:|:---:
codeTextColor |  文本或者密码圆点颜色
codeTextSize |   文本尺寸，当 codeInputType 为 text 时生效
maxCodeLength |   输入的最大长度
dotRadius |    密码圆点半径，当codeInputType为 password 生效
codeInputType |   password （密码圆点展示）text 明文展示

## 效果图



