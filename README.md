# 一.注册广播有哪几种方式,有什么区别 #
	1.常驻型（静态注册）在清单文件中添加声明，程序关闭后如果有信息广播到来，程序会被系统调用自动运行。
		优点：无需担忧广播接收器是否被关闭，只要设备是开启状态就行。
	2.非常驻型（动态注册）代码中运行注册，广播跟随程序的生命周期
		优点：动态注册的优先级是要高于静态注册优先级的，因此在必要的情况下，我们是需要动态注册广播接收器的。
# 二.注册Service需要注意什么 #
	Service是运行在主线程中的，所以如果需要执行一些复杂的逻辑操作，最好在服务的内部手动创建子线程进行处理，否则会出现UI线程被阻塞的问题。
# 三.Service和Actvity怎么实现通信 #
	1.通过（Binder对象）
		1.添加一个集成Binder的内部类，并添加相关的逻辑方法
		2.重写Service的onBind方法，返回我们刚刚定义的那个内部类实例
		3.Actvity中创建一个ServiceConnection的匿名内部类，并重写里面的onServiceConnected方法和onServiceDisconnected方法，
		这两个方法分别会在活动与服务成功绑定以及解除绑定时调用，在onServiceConnected方法中，我们可以得到一个刚才那个Service的binder对象，
		通过这个binder对象向下转型，得到我门自定义的那个Binder实例，就可以通过这个实例里面的剧痛方法进行需要的操作了。
	2.通过（BroadCast广播）
		1.在我们的进度放生变化的时候我们发送一条广播，然后在Actvity中注册广播接收器，接收到广播更新视图。
#  四.介绍Handle的机制 #
	1.Handler通过send message方法把消息放在消息队列MessageQueue中，Looper负责把消息从消息队列中取出来，重新再发送给Handler进行处理，三者形成一个循环。
	2.通过构建一个消息队列，把所有的Message进行统一管理，当Message不用了，并不作为垃圾回收，
	而是放入消息队列中，供下次的Handler创建消息时使用，提高了消息对象的复用，减少系统垃圾回收的次数，
	每一个线程都单独对应一个looper，这个looper通过Thread Local来创建，保诚每个线程只能创建一个looper，
	looper初始化后会调用looper.loop创建一个Message Queue，这个方法在UI线程初始化的时候就会完成，不需要手动创建。
# 五.ListView卡顿的原因与优化 #
	1.复用convertView来减少不必要的view创建，另外Infalte操作会把xml文件实例化为相应的View实例，属于IO操作，耗时操作。
	2.减少findViewById()操作，将xml文件中的元素封装成viewholder静态类，通过convertview的setTag和getTag将view与相应的holder对象绑定在一起。
	3.避免在 getView 方法中做耗时操作，例如加载本地Image需要载入内存以及解析Bitmap，如果用户快速滑动Listview会使因getview逻辑过于复杂造成滑动卡顿的现象，用户滑动时候不要加载图片，待滑动完成再加载，可以使用这个第三方库glide。
	4.Item的布局层次结构精良简单，避免布局太深或不必要的重绘。
# 六.ListView与数据库绑定的实现 #
	1/将Sqlite数据库的内容查询出来并放入数组列表中，形成ListView的数据源。
	2/通过适配器绑定数据库，显示在Listview item中。
# 七.Android中动画有哪些，区别？ #
	1.帧动画（Drawable Animation）：接在一系列Drawable资源来创建动画，就是播放一系列的图标来实现动画效果，可以定义每张图片的持续时间。
	2.补间动画(Tween Animation)：Tween可以对View对象实现一系列简单的动画效果，但是并不会改变View属性的值，只改变绘制的位置，触发点击事件还是在原来的坐标。
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
		1.注意要复写AsyncTask的onCancel方法，把里面的socket，file等，该关掉的及时关掉
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
# 十八.怎么避免OOM问题的出现 #
	1.使用更加轻量级的数据结构
		例如我们可以考虑使用ArrayMap/SparseArray而不是HashMap等传统数据结构，SparseArray更加高效，在于它避免了对Key与Value的自动装箱。
	2.避免在Android中使用枚举
	3.Bitmap对象的内存占用和复用
		1）加载到内存之前先计算出一个合适的比例，避免不必要的大图载入
		2）编码格式
	4.使用更小的图片，资源图片是否有压缩的空间
	5.StringBuilder 有的时候，代码中会需要使用大量的字符串拼接，可以考虑使用StringBuilder来代替背反的 + 
	6.避免在onDraw()里面执行对象的创建，类似onDraw()等频繁调用的方法，一定要注意避免在这里做创建对象的操作，它会迅速增加内存的使用，容易引起频繁的CG，甚至内存抖动
# 十九.Handler内存泄露 #
	1.Handler作为内部类存在于Activity中，但是Handler的生命周期与Activity的生命周期往往不相同，比如当Handler对象有Message在排队，则无法释放，进而造成本该释放的Activity也无法释放
	2.解决方法：handler为static类，这样内部类就不在只有外部类的引用了，就不会阻塞Activity的释放，如果内部类实在需要用到外部类的对象，可在其内部类声明一个弱引用的外部类，在Activity onStop或者onDestroy的时候，取消该Handler对象的Message和Runnable
	3.一些不良的代码习惯：有些代码并不会造成内存泄露，但是他们的资源没有得到重用，频繁的申请和销毁内存，消耗Cpu资源的同时引起内存抖动
	解决办法：如果需要频繁的申请内存对象和释放对象，可以考虑使用对象池来增加对象的复用，例如ListView变采用这种思想，通过复用Converevview来避免频繁的CG
# 二十.Android中的几种布局 #
	1.FrameLayout(框架布局)：
		五种布局中最简单的布局，ANdroid中并没有对chid view的布局进行控制，布局中的所有控件对默认出现在师徒的左上角
	2.LinearLayout(线性布局)：
		一行活一列以控制一个控件的线性布局，所以当有很多控件需要在一个界面中列出时，可以在LinearLayout布局，需要注意的属性，orientation
	3.AbsoluteLayout(绝对布局)
		可以放置多个控件，并且可以自定义控件的xy位置
	4.RelativeLayout(相对布局)
		这个也是相对自由的布局，Android对改布局的child view的水平lauout-垂直layout做了解析，由此我们可以在FrameLayout的基础上使用标签或者Java代码对方向，布局中的views进行任意的控制
	5.TableLayout(表格布局)
		将子元素的位置分配到行或列中，一个TableLayout由许多的TableRow组成
# 二十一.Activity的生命周期 #
	1.启动Activity：onCreate() --> onStart() --> onResume(),Activity进入运行状态
	2.Activity退居后台：当前Activity转到新的Activity界面或者按下Home健回到主屏：onPause() --> onStop()，进入停止状态
	3.Activity返回前台：onRestart() --> onStart() --> onResume(),再次回到运行状态
	4.Activity退居后，且系统内存不足，系统会杀死这个后台状态的Activity(此时这个Activity引用仍然处在任务栈中，只有这个时候u引用指向的对象已经为null)，若再次回到这个Activity，则会重新走一次Activity的初始化生命周期
	5.锁屏：onPause() --> onStop()
	6.解锁：onStart() --> onResume()
![](http://thumbnail0.baidupcs.com/thumbnail/64237df36a973c99a027cf9e6471c7e2?fid=3542265707-250528-60324785275619&time=1495702800&rt=sh&sign=FDTAER-DCb740ccc5511e5e8fedcff06b081203-%2FoWnVtgp43aYbRS9Xu%2B8vvZHojk%3D&expires=8h&chkv=0&chkbd=0&chkpc=&dp-logid=3356234180327777362&dp-callid=0&size=c710_u400&quality=100)
# 二十二.怎么保证Service不被杀死 #
	1.Service设置成START_STICKY
		kill后会被重启（等待5秒左右），重传Intent，保持与重启前一样
	2.提升service优先级
		在AndroidManifest.xml文件中对于intent-filter可以通过Android：priority=“1000”这个属性提高优先级，1000是最高值，数字越小优先级越低，适用于广播（可能无效）
	3.提升service进程优先级
		1）Android中进程是托管的，当系统进程控件紧张的时候，会一招优先级自动进行进程的回收
		2）当service运行在低内存的环境是，将会kill掉一些存在的进程，因此进程的优先级将会很重要，可以在startForeground()使用startForeground()将service放到前台状态。会降低被kill的几率（内存极低的压力下，service还是会被kill掉，并且不一定会restart）
	4.onDestroy()里重启service
		1）service+broadcast方式，就是当service走onDestory()的时候，发送一个自定义的广播，当收到广播的时候，重新启动service
		2）也可以在onDestroy()里startService
		3）总结：当时用类似xx管家等第三方应用或者在setting里-应用-强制停止时，App进程可能就直接被干掉了，onDestroy方法都进不来，所以无法保证
	5.监听广播判断Service状态
		1）通过系统的一些广播，比如-收起重启、界面唤醒、应用状态改变等等监听并捕捉到，判断我们的Service是否还存活，别忘记加权限
		2）总结：算是一种措施，感觉监听多了会导致Service很混乱，带来诸多不便
	6.在JNI层用C代码fork一个进程出来
		1）这样产生的进程，会被系统任务是两个不同的进程，5.0后可能不行
	7.Root之后放到system/app变成系统级应用
	8.放一个像素在前台。。。
# 二十三.ANdroid的几种进程 #
	1.前台进程：即于用户正在交易的Activity或者Activity用到的Service等，如果系统内存不足时前台进程是最后被杀死的
	2.可见进程：可以是处于暂停状态(onPause)的Activity或者绑定在其上的Service，即被用户可见，但由于失去了焦点而不能与用户交互
	3.服务进程：其中运行这使用startService方法启动的Service，虽然不被用户可见，但是确实用户关心的，例如用户正在非音乐见面听的音乐或者正在非下载页面自己下载的文件等，当系统要空间运行前前两者进程时才会被终止
	4.后台进程：其中运行着执行onStop方法而停止的程序，但是却不是用户当前关心的，例如后台的qq,这样的进程在系统一旦没有内存就首先被杀死
	5.空进程  ：不包含人户应用程序的进程，这样的进程系统一般不会让他存在的
	6.如何避免后台进程被杀死
		1）调用startForegound，让你的Service所在的线程成为前台进程
		2）Service的onStartCommond返回START_STICKY或START_REDELIVER_INTENT
		3）Service的onDestroy里面重新启动自己
# 二十四.进程间通信的方式 #
	1.通过Intent在Activity、Service或BroadcastReceiver间进行进程间通讯，可通过Intent传递数据
	2.AIDL的方式
	3.Messenger的方式
	4.利用ContentProvider
	5.Socket
	6.基于文件共享的方式
# 二十五.RecyclerView和ListView的异同 #
	1.ViewHolder是用来保存视图引用的类，无论是ListView或是RecyclerView，只不过在ListView中，ViewHolder需要自己来定义，并且这只是一种推荐的使用方式，不适用也可以，只不过不适用ViewHolder的话，ListView每次getView的时候会调用findViewByid(int)，这将导致ListView性能战士迟缓，而在RecyclerView中使用RecyclerView.ViewHolder则变成了必须，尽管实现起来稍显复杂，但它却解决了ListView面临的上述不适用自定义ViewHolder所面临的问题
	2.我们都知道ListView只能在垂直方向上滚动，Android API没有提供ListView在水平方向上面滚动的支持，或许有多种方式实现水平滚动，但ListView并不是设计来做这件事情的，但是RecyclerView相较于listView在滚动上面的功能扩展了许多，它可以支持多种类型列表的展示要求：
		1）LinearLayoutManager:可以支持水平和树枝方向上滚动的列表
		2）StaggeredGridLayoutManager:可以支持交叉网络风格的列表，类似与瀑布流或者Pinterest
		3)GridLayoutManager:支持网络展示，可以水平火竖直滚动，如展示图片的画廊
	3.列表动画是一个全新的、拥有无限可能的维度，起初的Android API中，删除或添加item时，item是无法产生动画效果的，后面随着Android的进化，Google的Char Hasse推荐使用ViewPropertyAnimator属性动画来实现上述需求，相比较于ListView，RecyclerView.ItemAnimatro则被提供用于在RecyclerView添加、删除或移动item时处理动画效果，同时，如果你比较懒，不想自定义ItemAnimatoe，你还可以使用DefaultItemAnimator。
	4.ListView的Adapter中，getView是最重要的方法，它将视图跟position绑定起来，是所有神奇的事情发生的地方。同时我们也能通过registerDataObserver在Adapter中注册一个观察者，List View有三个Adapter的默认实现，分别是ArrayAdapter、CursorAdapter和SimpleCursorAdapter。然而RecyclerView的Adapter则拥有除了内置的DB游标和ArrayList的支持之外的所有功能，RecyclerView.Adapter的实现的，我们必须采用措施将数据提供给Adapter，比如BaseAdapter对ListView所做的那样
	5.在ListView中如果我们想要在item之间添加间隔符，我们只需要在布局文件中对ListView添加如下属性：
		android:divider="@android:color/transparent"
		android:dividerHerght="6dp"
	6.Listview通过AdapterView.OnItemClickListener接口来探测点击事件，二RecyclerView则通过RecyclerView.RecyclerView.OnItemTouchListener接口来探测触摸事件，它虽然增加了实现的难度，单是他却给予开发人员拦截触摸事件更多的控制权限。
	7.ListView可以设置选择模式，并添加MultiChoiceModeListener，如下：
		ListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
# 二十六.Context与ApplicationContext的区别，分别用在什么情况下 #
	1.AppLication的Context是一个全局静态变量，SDK的说明是只有当你引用这个context的生命周期超过了当前activity的生命周期，而和整个应用的生命周期挂钩时，才会使用这个AppLication的context。
	2.在Android中context可以作很多操作，但是最主要的功能是加载和访问资源，在Android中有两种context，一种是application context，一种是activity context，通常我们在各种类的方法间传递的是activity context。
# 二十七.Activity中如何动态的添加Fragment #
	1.获取FragmentManager
	2.动态添加Fragment
	3.通过beginTransaction()获取fragmenTransaction
	4.向view中添加指定的Fragment
	5.更新完成后记得commit()
# 二十八.Socket编程的步骤 #
	1.什么是Socket
		1）所谓Socket通常也称作为“套接字”，用于描述IP地址和端口
		2）应用程序通常通过“套接字”向网络发送请求或者应答网络请求
	2.应用程序 -> “套接字” -> TCP - IP - 传递通道 - IP -> 传递通道 - IP -> UDP -> “套接字” - 应用程序，就是客户端与服务端的输入输出。
	3.  