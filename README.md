#开发生成器 mybatis-generator 自动生成dao接口、entity实体、对应xml
http://mybatis.org/generator/running/runningWithMaven.html
    1.连接数据库
    2.获取表结构
    3.生成文件
#操作
    1.导入依赖
        <plugin>
          <groupId>org.mybatis.generator</groupId>
          <artifactId>mybatis-generator-maven-plugin</artifactId>
          <version>1.4.0</version>
        </plugin>
    2.在resources目录下配置generatorConfig.xml文件
    3.执行语句
        mvn mybatis-generator:generate

