# yjhServiceTest
android: 
1. Service的基本用法。
2. Activity和Service进行通信: 比如在activity中可以开始下载和查看进度，使用Binder解决。 
3. 使用前台Service。 
4. 使用IntentService：Service默认主线程，所以要用到Android多线程技术。法1：具体在Service的每个方法里开启子线程。法2：使用IntentService
