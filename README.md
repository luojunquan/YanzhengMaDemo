产生原理
自定义一组字符数组,随机在里面挑选出自己想要产生的验证码个数的字符,用画笔画入自己定义的BitMap中,然后在随机画入干扰线条,当然绘制时的一些参数都是随机产生的,比如:颜色,字体格式,字体之间的间隔,线条的格式等等都是随机的,下面来看看具体的实现步骤;
具体过程
定义一组字符数组
定义一组字符数组,用于随机产生我们想要的字符,然后绘制成验证码
随机生成字符
随机生成几个字符,字符的数量CODE_LENGTH是我们自己传入的
创造画布
既然是要绘制验证码,比不可少的一个就是画布Canvars了,先创建一个验证码要显示的BitMap,然后把BitMap变成画布,
画布的颜色是自己设置的
创建画笔
画笔也是绘制必不可少的一个
http://blog.csdn.net/dl10210950/article/details/52421473#comments
