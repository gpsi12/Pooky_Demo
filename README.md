# 一.注册广播有哪几种方式,有什么区别 #
	1.常驻型（静态注册）在清单文件中添加声明，程序关闭后如果有信息广播到来，程序会被系统调用自动运行。
		优点：无需担忧广播接收器是否被关闭，只要设备是开启状态就行。
	2.非常驻型（动态注册）代码中运行注册，广播跟随程序的生命周期
		优点：动态注册的优先级是要高于静态注册优先级的，因此在必要的情况下，我们是需要动态注册广播接收器的。
# 二.注册Service需要注意什么 #
	Service是运行在主线程中的，所以如果需要执行一些复杂的逻辑操作，最好在服务的内部手动创建子线程进行处理，否则会出现UI线程被阻塞的问题。
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
# 六.ListView与数据库绑定的实现 #
	1/将Sqlite数据库的内容查询出来并放入数组列表中，形成ListView的数据源。
	2/通过适配器绑定数据库，显示在Listview item中。
# 七.Android中动画有哪些，区别？ #
	1.帧动画（Drawable Animation）：接在一系列Drawable资源来创建动画，就是播放一系列的图标来实现动画效果，可以定义每张图片的持续时间。
	2.补间动画(Tween Animation)：Tween可以对View对象实现一系列简单的动画效果，但是并不会该表View属性的值，只改变绘制的位置，触发点击事件还是在原来的坐标。
	3.属性动画(Property Animation)：动画的对象除了传统的View，还可以是Object对象，动画结束后，Object对象的属性值是被改变了。
# 八.显式Intent和隐式Intent的区别 #
	1.对明确指出目标组件名称的Intent，我们称之为“显式Intent”，而没有明确指出的称之为“隐式Intent”。
	2.对于隐式意图，在定义Activity时，指定一个intent-filter，当一个隐式意图对象被一个意图过滤器进行匹配时，将有三个方面会被考虑到：
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
	1.文件储存：通过java.io.FileInputStream和java.io.FileOutputStream这两个类来实现对文件的对象。
	2.SharedPreferences：是一种轻量级的数据存储机制，他将一些简单的数据类型的数据，包括boolean类型，int类型，float类型，long类型以及String类型的数据，以键值对的形式存储在应用程序的私有Preferences目录（/data/data/<包名>/shared_prefs/）中，这种Preferences机制广泛应用于存储应用程序中的配置信息。
	3.SQLite数据库：当应用程序需要处理的数据量比较大时，为了更加合理的处理、管理、查询数据，我们往往使用关系数据库储存数据。Android中如联系人】、通话记录、短信等，都是存储在SQLite数据库中。
	4.ContentProvider：主要用于在不同的应用程序之间实现数据共享的功能，不同于SP和文件存储中的两种全局可读写的操作模式，内容提供其可以选择之对那一部分数据进行共享，而办证我们程序中的隐私数据不会被泄露。
# 十一.Async task系列 #
	十二.为什么要设计为只能够一次任务？
		最核心的还是线程安全的问题，多个子线程同事运行，会产生状态不一致的问题，所以要务必保证只能运行一次。
	十三.AsyncTask造成的内存泄露问题怎么解决？
		比如非静态内部类AsyncTask会隐式地持有外部类的引用，如果气生命周期大于外部activity的生命周期，就会出现内存泄露。
		1.注意要复写AsyncTask的onCancel方法，吧里面的socket，file等，该关掉的及时关掉
		2.在Activity的onDestory()方法中调用Asynctask.cancel方法。
		3.Asynctask内部使用弱引用的方式来持有Activity。
	十四.若Activity已被销毁，此时AsyncTask执行完毕并返回结果，会报异常么？
		答：当一个App旋转时，整个Activity会被销毁和重建，当Activity重启时，AsyncTask中对该Activity的引用是无效的，因此onPostExecute()就不会起作用，若这是正在执行就回报出view not attached to window manage异常，同样也是生命周期的问题，在Activity的onDestory()中调用Asynctask.cancel方法，让二者的生命周期同步。
	十五.Activity销毁但Task如果没有销毁掉，当Activity重启时这个AsyncTask该如何解决？
		在重建Activity的时候，会回调Activity.onRetainNonConfigurationInstance()重新创建一个新的对象给AsyncTask，完成引用的更新。
# 十六.ANR系列 #
	1.什么是ANR？
		ANR全称是Application Not Responding，意为程序未响应，程序无法响应用户的输入，系统会弹出一个ANR对话框，用户选择继续等待还是停止当前程序。
		1）应用在5s内未相应用户的输入事件
		2）BroadcastReceiver未在10s内完成相应的处理。
	2.怎么引起的？
		1）主线程中存在耗时操作
		2）主线程中错误的操作，例如Thread.wait或Thread.sleepp等
	3.如何避免ANR为题？
		基本思路就是把一些耗时操作放在子线程中处理
		1）使用Async Task处理耗时IO操作
		2）降低子线程优先级使用Thread火HandlerThread，调用Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND)设置优先级，否则仍然会降低程序响应，因为默认Thread的优先级和主线程相同。
		3）使用Handler处理子线程结果，而不是Thread.wait()或者Thread.sleep来
		4）Activity的onCreate和onResume回调中尽量避免耗时操作的代码
		5）BroadcastReceiver中onReceive代码也要尽量避免耗时操作，建议使用IntentService处理，IS是一个异步的，会自动停止服务，很好的解决了传统的Service中处理耗时操作忘记停止并销毁Service的问题
# 十七.如何使用JNI #
	1.在JAVA中生命native 方法如private native String printJNI(String inputStr);
	2.使用javah工具生成.h头文件，文件中就会自动生成对应的函数JNIEXPORT jstring JNICALLJava_com_wenming_HelloWorld_printJNI 
	3.实现JNI原生函数源文件，新建HelloWord.c文件，对刚才自动生成的函数进行具体的逻辑书写，例如返回一个Java叫做HelloWord的字符串，
	4.编译生成动态链接so文件
	5.Java中调用System.load方法吧方才的so库加载过来，就可以调用native方法了
	例子：如果通过JNI船体String对象？
		Java的String和C++的String是不能对等起来的，所以我们拿到.h文件下面的jstring对象，会做一次转换，我们吧jstring转换为C下面的char+类型获取值
		constchar*str;
		str = env->GetStringUTFChars(prompt,false);
		赋予值
		char*tmpstr = "return string succeeded";
		jstring resrt = env->NewStringUTF(tmpstr);
十八.