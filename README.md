# XNoSuchElementException
Xposed模块，解除安卓（APP）对Appium自动化测试的限制：

1、安卓系统设置调试标识，DEBUG_ENABLE_DEBUGGER

2、设置弹窗PopupWindow可获取焦点，setFocusable(true)

3、解除界面无法截图的限制，setSecure(false)

4、允许获取WebView容器内的控件信息，setWebContentsDebuggingEnabled(true)，注意，获取控件前，要先打开appium侦听端，且要使用AndroidSDK自带的UiAutomatorViewer，不要使用GitHub上那个经过修改的Lvmama Ui Automator Viewer

5、\app\build\outputs\apk\debug\XNoEleEx.apk有生成好的apk，可以使用此apk进行验证，建议下载selendroid官网的selendroid-test-app.apk进行测试

6、在逍遥模拟器7.1.0（Android 5.1.1），Xposed 89版本测试通过
