# 基于互联网+的实验室设备云管理

将实验室设备上传云端并且实现客户端的管理与远程操作。

**V2**使用**kotlin**实现，感觉写起来非常舒服。以下主要写写**V2**中使用的**kotlin**的感受

## 对比Java

总体来说就是非常简洁，代码很干净

### Bean

在**Java**中要写很长，**kotlin**省去了一堆**set**和**get**，与其他需要覆写的操作

```kotlin
data class DataPoints(
        val at:String,
        val value:String
)
```

### 扩展方法

虽然是语法糖，但是也是非常实用，比如可以给**context**写个全局扩展

```kotlin
fun Context.getApiComponent() = KotlinApp.INSTANCE.apiComponent

fun Context.toast(mag:String,length:Int=Toast.LENGTH_SHORT){
    Toast.makeText(this,mag,length).show()
}
```

### Lambda

**kotlin**支持对象表达式，且可以把函数当做参数传递

```kotlin
override fun getDetil(deviceId: String) {
    model.getDevice(deviceId, LocalResponse(
            {t -> view.showDetil(t) },
            {exception -> view.showFail(exception) }
    ))
}
```

### No FindView

用了插件后可以直接使用**view**不用写一堆**find**了，直接导包后用**id**名

```kotlin
import kotlinx.android.synthetic.main.pop_list.view.*

val content:View=LayoutInflater.from(context).inflate(R.layout.pop_list,null)
content.pop_wifi.setOnClickListener { showSetWifi(context) }
```

## MVP+Dagger2

### MVP

用了官方最麻烦的写法但是非常清晰，再通过**Dagger**注入**Presenter**

```kotlin
interface DeviceListContract{
    interface View{
        fun setData(res:List<DeviceList>)
        fun failData(e:Exception)
        fun showsafe(map:Map<String,Int>)
    }

    interface Presenter{
        fun getData(page:Int?=0)
    }
}
```

```kotlin
class DeviceListPresent
 @Inject constructor(private val model:OneNetModel
                         , private val view:DeviceListContract.View)
    :DeviceListContract.Presenter {...}
```

### Dagger

**App**老大，用subComponent

```kotlin
@Component(modules = arrayOf(ApiModule::class))
interface ApiComponent{
    fun inject(app:KotlinApp)

    fun plus(module: DeviceListModule):DeviceListComponent
    fun plus(module: DeviceDetilModule):DeviceDetilComponent
}
```

#### **APP**

```kotlin
class KotlinApp : Application(){
    init {
        INSTANCE=this
    }
    @Inject lateinit var apiComponent:ApiComponent
    override fun onCreate() {
        super.onCreate()

        DaggerApiComponent.builder()
                .apiModule(ApiModule())
                .appModule(AppModule(this))
                .build()
                .inject(this)

    }
    companion object{
        lateinit var INSTANCE: KotlinApp
    }
}
```

#### Activity

```kotlin
@Subcomponent(modules = arrayOf(DeviceListModule::class))
interface DeviceListComponent{
    fun inject(activity:KDeviceListActivity)
}
```

**注入**

```kotlin
getApiComponent().plus(DeviceListModule(this)).inject(this)
```

## DataBinding

用了**BaseActivity**简单封装了下

```kotlin
abstract class KBaseActivity<B :ViewDataBinding> :AppCompatActivity(){

    lateinit var mBinding:B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding=createDataBinding(savedInstanceState)
        initView()
    }

    abstract fun initView()

    abstract fun  createDataBinding(savedInstanceState: Bundle?): B

}
```