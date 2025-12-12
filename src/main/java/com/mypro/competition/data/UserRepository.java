package com.mypro.competition.data;

import com.mypro.competition.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;

public class UserRepository {

    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    // 数据库连接配置
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/competition_db?useSSL=false&serverTimezone=UTC&characterEncoding=utf8";
    private static final String JDBC_USER = "root"; // 请根据您的数据库配置修改
    private static final String JDBC_PASSWORD = "123456"; // 请根据您的数据库配置修改

    // 获取数据库连接
    private Connection getConnection() throws SQLException {
        logger.info("尝试获取数据库连接...");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            logger.info("MySQL JDBC驱动加载成功");
        } catch (ClassNotFoundException e) {
            logger.error("MySQL JDBC驱动加载失败", e);
            throw new SQLException("MySQL JDBC Driver not found", e);
        }
        logger.debug("数据库URL: {}, 用户: {}", JDBC_URL, JDBC_USER);
        Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        logger.info("数据库连接成功");
        return conn;
    }

    /**
     * 根据用户名和密码验证用户。
     * @param username 学号/工号
     * @param password 密码
     * @return 匹配的 User 对象，如果没有找到则返回 null
     */
    public User findByUsernameAndPassword(String username, String password) {
        logger.info("开始登录验证，用户名: {}", username);

        String sql = "SELECT id, username, password, role, name, student_id, major FROM users WHERE username = ? AND password = ?";
        logger.debug("执行查询SQL: {}", sql);

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password); // 注意：生产环境应使用哈希密码
            logger.debug("SQL参数设置完成：username={}, password={}", username, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    logger.info("找到匹配的用户记录，用户ID: {}", rs.getInt("id"));
                    logger.debug("用户名: {}, 角色: {}, 姓名: {}", 
                            rs.getString("username"), rs.getString("role"), rs.getString("name"));

                    User user = new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("name"),
                            rs.getString("role"),
                            rs.getString("student_id"),
                            rs.getString("major")
                    );
                    logger.info("登录验证成功");
                    return user;
                } else {
                    logger.info("没有找到匹配的用户记录");
                }
            }
        } catch (SQLException e) {
            logger.error("登录验证异常", e);
        }

        logger.info("登录验证失败");
        return null;
    }

    /**
     * 根据用户ID查询用户。
     * @param userId 用户ID
     * @return 匹配的 User 对象，如果没有找到则返回 null
     */
    public User findById(int userId) {
        logger.info("开始根据ID查询用户，用户ID: {}", userId);

        String sql = "SELECT id, username, password, role, name, student_id, major FROM users WHERE id = ?";
        logger.debug("执行查询SQL: {}", sql);

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            logger.debug("SQL参数设置完成：userId={}", userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    logger.info("找到匹配的用户记录，用户ID: {}", rs.getInt("id"));
                    logger.debug("用户名: {}, 角色: {}, 姓名: {}", 
                            rs.getString("username"), rs.getString("role"), rs.getString("name"));

                    User user = new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("name"),
                            rs.getString("role"),
                            rs.getString("student_id"),
                            rs.getString("major")
                    );
                    logger.info("根据ID查询用户成功");
                    return user;
                } else {
                    logger.info("没有找到匹配的用户记录");
                }
            }
        } catch (SQLException e) {
            logger.error("根据ID查询用户异常", e);
        }

        logger.info("根据ID查询用户失败");
        return null;
    }

    /**
     * 注册新的学生用户。
     * @param user 待注册的用户对象
     * @return 注册成功返回 true，否则 false
     */
    public boolean save(User user) {
        String sql = "INSERT INTO users (username, password, role, name, student_id, major) VALUES (?, ?, 'student', ?, ?, ?)";
        logger.debug("执行注册SQL: {}", sql);

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword()); // 注意：生产环境应存储哈希值
            ps.setString(3, user.getName());
            ps.setString(4, user.getUsername()); // 使用用户名作为学号
            ps.setString(5, "未指定"); // 默认专业

            int rowsInserted = ps.executeUpdate();
            boolean success = rowsInserted > 0;
            logger.info("用户注册{}", success ? "成功" : "失败");
            return success;
        } catch (SQLException e) {
            logger.error("用户注册异常", e);
            return false;
        }
    }
}