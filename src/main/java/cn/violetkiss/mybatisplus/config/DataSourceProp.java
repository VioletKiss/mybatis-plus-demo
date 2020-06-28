package cn.violetkiss.mybatisplus.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.jfinal.kit.Prop;

/**
 * @author wrb
 * @date 2020/6/10 14:18
 */
public class DataSourceProp {

    private String config = "db.properties";
    private Prop prop;

    private String url;
    private String username;
    private String password;
    private String driverName;
    private DbType dbType;

    public DataSourceProp() {
        prop = new Prop(config);
        this.url = prop.get("db.url");
        this.username = prop.get("db.username");
        this.password = prop.get("db.password");
        this.driverName = prop.get("db.driver-class-name");
        //todo 此处处理数据库类型,可能会出错
        String[] split = url.split(":", 3);
        dbType = DbType.getDbType(split[1]);
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDriverName() {
        return driverName;
    }

    public DbType getDbType() {
        return dbType;
    }
}
