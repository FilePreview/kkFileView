package cn.keking.config;

import io.netty.channel.nio.NioEventLoopGroup;
import org.redisson.client.codec.Codec;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ClassUtils;

/**
 * Created by kl on 2017/09/26.
 * redisson 客户端配置
 */
@ConditionalOnExpression("'${cache.type:default}'.equals('redis')")
@ConfigurationProperties(prefix = "spring.redisson")
@Configuration
/**
 * Author：houzheng
 * Date：11-18
 * 对redisson客户端进行配置
 *
 */
public class RedissonConfig {

    private String address;
    private int connectionMinimumIdleSize = 10;
    private int idleConnectionTimeout=10000;
    private int pingTimeout=1000;
    private int connectTimeout=10000;
    private int timeout=3000;
    private int retryAttempts=3;
    private int retryInterval=1500;
    private int reconnectionTimeout=3000;
    private int failedAttempts=3;
    private String password = null;
    private int subscriptionsPerConnection=5;
    private String clientName=null;
    private int subscriptionConnectionMinimumIdleSize = 1;
    private int subscriptionConnectionPoolSize = 50;
    private int connectionPoolSize = 64;
    private int database = 0;
    private boolean dnsMonitoring = false;
    private int dnsMonitoringInterval = 5000;

    private int thread; //当前处理核数量 * 2

    private String codec="org.redisson.codec.JsonJacksonCodec";

    @Bean
    Config config() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Config config = new Config();
        config.useSingleServer().setAddress(address)
                .setConnectionMinimumIdleSize(connectionMinimumIdleSize)
                .setConnectionPoolSize(connectionPoolSize)
                .setDatabase(database)
                .setDnsMonitoring(dnsMonitoring)
                .setDnsMonitoringInterval(dnsMonitoringInterval)
                .setSubscriptionConnectionMinimumIdleSize(subscriptionConnectionMinimumIdleSize)
                .setSubscriptionConnectionPoolSize(subscriptionConnectionPoolSize)
                .setSubscriptionsPerConnection(subscriptionsPerConnection)
                .setClientName(clientName)
                .setFailedAttempts(failedAttempts)
                .setRetryAttempts(retryAttempts)
                .setRetryInterval(retryInterval)
                .setReconnectionTimeout(reconnectionTimeout)
                .setTimeout(timeout)
                .setConnectTimeout(connectTimeout)
                .setIdleConnectionTimeout(idleConnectionTimeout)
                .setPingTimeout(pingTimeout)
                .setPassword(password);
        Codec instance=(Codec) ClassUtils.forName(getCodec(), ClassUtils.getDefaultClassLoader()).newInstance();
        config.setCodec(instance);
        config.setThreads(thread);
        config.setEventLoopGroup(new NioEventLoopGroup());
        config.setUseLinuxNativeEpoll(false);
        return config;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取线程
     *
     */
    public int getThread() {
        return thread;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置线程
     *
     */

    public void setThread(int thread) {
        this.thread = thread;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取地址
     *
     */

    public String getAddress() {
        return address;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置地址
     *
     */
    public void setAddress(String address) {
        this.address = address;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取空闲连接超时
     *
     */
    public int getIdleConnectionTimeout() {
        return idleConnectionTimeout;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置空闲连接超时
     *
     */

    public void setIdleConnectionTimeout(int idleConnectionTimeout) {
        this.idleConnectionTimeout = idleConnectionTimeout;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取ping超时
     *
     */

    public int getPingTimeout() {
        return pingTimeout;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置ping超时
     *
     */


    public void setPingTimeout(int pingTimeout) {
        this.pingTimeout = pingTimeout;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取连接超时
     *
     */

    public int getConnectTimeout() {
        return connectTimeout;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置连接超时
     *
     */


    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取超时
     *
     */


    public int getTimeout() {
        return timeout;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置超时
     *
     */

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取重试次数
     *
     */

    public int getRetryAttempts() {
        return retryAttempts;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置重试次数
     *
     */


    public void setRetryAttempts(int retryAttempts) {
        this.retryAttempts = retryAttempts;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取重试间隔
     *
     */

    public int getRetryInterval() {
        return retryInterval;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置重试间隔
     *
     */

    public void setRetryInterval(int retryInterval) {
        this.retryInterval = retryInterval;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取重新连接超时
     *
     */


    public int getReconnectionTimeout() {
        return reconnectionTimeout;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置重新连接超时
     *
     */

    public void setReconnectionTimeout(int reconnectionTimeout) {
        this.reconnectionTimeout = reconnectionTimeout;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取失败次数
     *
     */

    public int getFailedAttempts() {
        return failedAttempts;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置失败次数
     *
     */

    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取密码
     *
     */


    public String getPassword() {
        return password;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置密码
     *
     */

    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取每个连接的订阅数
     *
     */

    public int getSubscriptionsPerConnection() {
        return subscriptionsPerConnection;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置每个连接的订阅数
     *
     */

    public void setSubscriptionsPerConnection(int subscriptionsPerConnection) {
        this.subscriptionsPerConnection = subscriptionsPerConnection;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取客户姓名
     *
     */

    public String getClientName() {
        return clientName;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置用户姓名
     *
     */

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取订阅连接最小空闲大小
     *
     */

    public int getSubscriptionConnectionMinimumIdleSize() {
        return subscriptionConnectionMinimumIdleSize;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置订阅连接最小空闲大小
     *
     */

    public void setSubscriptionConnectionMinimumIdleSize(int subscriptionConnectionMinimumIdleSize) {
        this.subscriptionConnectionMinimumIdleSize = subscriptionConnectionMinimumIdleSize;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取订阅连接池大小
     *
     */

    public int getSubscriptionConnectionPoolSize() {
        return subscriptionConnectionPoolSize;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置订阅连接池大小
     *
     */

    public void setSubscriptionConnectionPoolSize(int subscriptionConnectionPoolSize) {
        this.subscriptionConnectionPoolSize = subscriptionConnectionPoolSize;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取连接最小空闲大小
     *
     */

    public int getConnectionMinimumIdleSize() {
        return connectionMinimumIdleSize;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置连接最小空闲大小
     *
     */

    public void setConnectionMinimumIdleSize(int connectionMinimumIdleSize) {
        this.connectionMinimumIdleSize = connectionMinimumIdleSize;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取连接池大小
     *
     */

    public int getConnectionPoolSize() {
        return connectionPoolSize;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置连接池大小
     *
     */

    public void setConnectionPoolSize(int connectionPoolSize) {
        this.connectionPoolSize = connectionPoolSize;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取数据
     *
     */

    public int getDatabase() {
        return database;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置数据
     *
     */

    public void setDatabase(int database) {
        this.database = database;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 判断dns监视
     *
     */

    public boolean isDnsMonitoring() {
        return dnsMonitoring;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置DNS监视
     *
     */

    public void setDnsMonitoring(boolean dnsMonitoring) {
        this.dnsMonitoring = dnsMonitoring;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取DNS监视间隔
     *
     */

    public int getDnsMonitoringInterval() {
        return dnsMonitoringInterval;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置DNS监视间隔
     *
     */

    public void setDnsMonitoringInterval(int dnsMonitoringInterval) {
        this.dnsMonitoringInterval = dnsMonitoringInterval;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取编解码器
     *
     */

    public String getCodec() {
        return codec;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置编解码器
     *
     */

    public void setCodec(String codec) {
        this.codec = codec;
    }
}