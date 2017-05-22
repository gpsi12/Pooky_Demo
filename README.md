# 一.注册广播有哪几种方式,有什么区别 #
	1.常驻型（静态注册）在清单文件中添加声明，程序关闭后如果有信息广播到来，程序会被系统调用自动运行。
		优点：无需担忧广播接收器是否被关闭，只要设备是开启状态就行。
	2.非常驻型（动态注册）代码中运行注册，广播跟随程序的生命周期
		优点：动态注册的优先级是要高于静态注册优先级的，因此在必要的情况下，我们是需要动态注册广播接收器的。
# 二.注册Service需要注意什么 #
	Service是熏香在主线程中的，所以如果需要执行一些复杂的逻辑操作，最好在服务的内部手动创建子线程进行处理，否则会出现UI线程被阻塞的问题。
# 三.Service和Actvity怎么实现通信 #
	1.通过（Binder对象）
		1.添加一个集成Binder的内部类，并添加想关的逻辑方法
		2.重写Service的onBind方法，返回我们刚刚定义的那个内部类实例
		3.Actvity中创建一个ServiceConnection的匿名内部类，并重写里面的onServiceConnected方法和onServiceDisconnected方法，
		这两个方法分别会在活动与服务成功绑定以及解除绑定时调用，在onServiceConnected方法中，我们可以得到一个刚才那个Service的binder对象，
		通过这个binder对象向下转型，得到我门自定义的那个Binder实例，就可以通过这个实例里面的剧痛方法进行需要的操作了。
	2.通过（BroadCast广播）
		1.在我们的进度放生变化的时候我们发送一条广播，然后在Actvity中注册广播接收器，接收到广播更新视图。
#  四.介绍Handle的机制 #
	1.Handler通过send message方法吧消息放在消息队列MessageQueue中，Looper负责吧消息从消息队列中取出来，重新再发送给Handler进行处理，三者形成一个循环。
	2.通过构建一个消息队列，把所有的Message进行统一管理，当Message不用了，并不作为垃圾回收，
	而是放入消息队列中，供下次的Handler创建消息时使用，提高了许欸西对象的复用，减少系统垃圾回收的次数，
	每一个线程都单独对应一个looper，这个looper通过Thread Local来创建，保诚每个线程只能创建一个looper，
	looper初始化后会调用looper.loop创建一个Message Queue，这个方法在UI线程初始化的时候就会完成，不需要手动创建。
# 五.ListView卡顿的原因与优化 #
	1.复用converView来减少不必要的view创建，另外Infalte操作会把xml文件实例化为相应的View实例，属于IO操作，耗时操作。
	2.减少findViewById()操作，将xml文件中的元素封装成viewholder静态类，通过converview的setTag和getTag将view与相应的holder对象绑定在一起。
	3.避免在 getView 方法中做耗时操作，例如加载本地Image需要载入内存以及解析Bitmap，如果用户快速滑动Listview会是因getview逻辑过于复杂造成滑动卡顿的现象，用户滑动时候不要加载图片，待滑动完成再加载，可以使用这个第三方库glide。
	4.Item的布局层次结构精良简单，避免布局太深或不必要的重绘。
# 六.ListView玉数据库绑定的实现 #
	1/将Sqlite数据库的内容查询出来并放入数组列表中，形成ListView的数据源。
	2/通过适配器绑定数据库，显示在Listview item中。
# 七.Android中动画有哪些，区别？ #
	1.帧动画（Drawable Animation）：接在一系列Drawable资源来创建动画，就是播放一系列的图标来实现动画效果，可以定义每张图片的持续时间。
	2.补间动画(Tween Animation)：Tween可以对View对象实现一系列简单的动画效果，但是并不会该表View属性的值，只改变绘制的位置，触发点击事件还是在原来的坐标。
	3.属性动画(Property Animation)：动画的对象除了传统的View，还可以是Object对象，动画结束后，Object对象的属性值是被改变了。
# 八.显式Intent和隐式Intent的区别 #
	1.对明确指出目标组件名称的Intent，我们称之为“显式Intent”，而没有明确指出的称之为“隐式Intent”。
	2.对于隐式意图，在定义Activity时，指定一个intent-filter，当一个隐式意图对象被一个意图郭璐琦进行匹配时，将有三个方面会被考虑到：
		1/动作（Action）
		2/类别（Category）
		3/数据（Data）
# 九.View的绘制流程 #
	measure()方法，layout()，draw()三个方法主要存放了一些标识符，来判断每个View是否需要再重新测量，布局或者绘制，主要的绘制过程还是在onMeasure，onLayout，onDraw这个三个方法中。
		1.onMeasure():为整个View树对象计算实际大小，高宽每个View的控件的实际宽高都由父视图和本身视图决定的。
		2.onlayout():为将确定参数大小的View树放在合适的位置上。
		3.onDeaw():来时绘制图像，流程：
			1/首先绘制该View的背景
			2/调用onDeaw()方法绘制视图本身(View Group不要需要实现该方法)
			3/如果该View是View Group，则调用dispatchDeaw()方法绘制子视图
			4/绘制滚动条
# 十.数据持久化的四种方式 #
	