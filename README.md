## SDK 开发

#### 1、使用spring boot 初始化项目
组件：

​	a. spring-boot-configuration-processor （在yml/yaml中输入前缀提示工具）

​	b. lombok （@Data，set，get方法）

#### 2、pom 修改：去除打包代码（build标签，）

#### 3、创建：src/main/resources/META-INF/spring.factories，springboot启动时会加载该文件的内容

​	spring.factories编写内容（指定自启动类）：

```java
org.springframework.boot.autoconfigure.EnableAutoConfiguration=com.liu.liuapiclientsdk.LiuApiClientConfig
```

#### 4、编写加载的类以及相关的方法

```java
@Configuration // 配置类，可以用来定义和注册Spring容器中的bean。
@ConfigurationProperties("liuapi.client") // 读取配置文件，同时给accessKey和secretKey赋值
@ComponentScan
@Data
public class LiuApiClientConfig {

    private String accessKey;

    private String secretKey;

    @Bean
    public LiuApiClient liuApiClient() {
        return new LiuApiClient(accessKey,secretKey);
    }
}
```

LiuApiClient类：

```java
public class LiuApiClient {

    private String accessKey;

    private String secretKey;

    public LiuApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }
    private Map<String, String> getHeaderMap(String body) {
        Map<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("accessKey", accessKey);
        hashMap.put("nonce", RandomUtil.randomNumbers(4));
        hashMap.put("body", body);
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        hashMap.put("sign", genSign(body, secretKey));
        return hashMap;
    }
    ......
}
```

genSign方法的类：

```java
/**
 * 签名工具
 */
public class SignUtils {
    /**
     * 生成签名
     * @param body
     * @param secretKey
     * @return
     */
    public static String genSign(String body, String secretKey) {
        Digester md5 = new Digester(DigestAlgorithm.SHA256);
        String content = body + "." + secretKey;
        return md5.digestHex(content);
    }
}
```

User类：

```java
@Data
public class User {
    private String username;
}
```

