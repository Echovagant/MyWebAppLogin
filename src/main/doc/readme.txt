学生竞赛报名管理系统
1. 运行环境配置
组件	                 版本	          说明
JDK                   21	       兼容Jakarta EE 10，推荐使用
Tomcat	            10.1.46	       兼容Servlet 6.0 / JSP 3.1规范
IDE	IntelliJ IDEA   2025.2.5	   建议配置Maven或Gradle构建项目
数据库	          MySQL 8.0.43     数据库名称需设置为competition_db
                  （或更高版本）
JDBC驱动              MySQL         确保已添加到项目依赖中
                 Connector/J 8.x
2. 数据库配置和初始化
创建数据库competition_db。
执行competition_db_setup.sql文件完成表结构创建和初始数据导入。
重要提示：替换UserRepository.java和CompetitionRepository.java中的JDBC连接参数：USER和PASSWORD为您本地MySQL账户密码。

3. 用户登录信息
角色         	路径          	登录名（学号）	密码	    权限说明
管理员	/competition/login.jsp	   admin	123456	设置竞赛和查询所有报名记录
学生  	/competition/login.jsp	   20220001	123456	浏览竞赛和提交报名
注册 	/competition/register.jsp	   -       -	任何新学生都可以注册
