package cn.violetkiss.mybatisplus.config;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * mybatis-plus 配置
 * </p>
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        // 攻击 SQL 阻断解析器、加入解析链,阻止恶意的全表更新删除
//        logger.debug("注册分页插件");
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();

        // 攻击 SQL 阻断解析器、加入解析链,阻止恶意的全表更新删除
        List<ISqlParser> sqlParserList = new ArrayList<>();
        sqlParserList.add(new BlockAttackSqlParser());
        paginationInterceptor.setSqlParserList(sqlParserList);


        return paginationInterceptor;
    }


}
