package com.mypro.competition.data;

import com.mypro.competition.model.Competition;
import com.mypro.competition.model.Registration;
import com.mypro.competition.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompetitionRepository {

    private static final Logger logger = LoggerFactory.getLogger(CompetitionRepository.class);
    private final UserRepository userRepository = new UserRepository();

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
        logger.debug("数据库URL: {}", JDBC_URL);
        Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        logger.info("数据库连接成功");
        return conn;
    }

    /**
     * 查询所有竞赛信息。
     * @return 所有竞赛的列表
     */
    public List<Competition> findAllCompetitions() {
        List<Competition> competitions = new ArrayList<>();
        String sql = "SELECT id, name, level, form_type, start_date, end_date FROM competitions";
        logger.debug("执行查询SQL: {}", sql);

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String level = rs.getString("level");
                String formType = rs.getString("form_type");
                Date startDate = rs.getDate("start_date");
                Date endDate = rs.getDate("end_date");

                logger.debug("竞赛ID: {}, 名称: {}, 级别: {}, 形式: {}", id, name, level, formType);

                competitions.add(new Competition(
                        id,
                        name,
                        level,
                        formType,
                        startDate,
                        endDate,
                        formType // 使用form_type作为type
                ));
            }
        } catch (SQLException e) {
            logger.error("查询竞赛信息异常", e);
        }

        logger.info("返回 {} 个竞赛", competitions.size());
        return competitions;
    }

    /**
     * 查询所有报名记录
     * @return 所有报名记录的列表
     */
    public List<Registration> findAllRegistrations() {
        List<Registration> registrations = new ArrayList<>();
        String sql = "SELECT id, user_id, comp_id, registration_date, status, team_name FROM registrations";
        logger.debug("执行查询SQL: {}", sql);

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                int userId = rs.getInt("user_id");
                int competitionId = rs.getInt("comp_id");
                Date registrationDate = rs.getDate("registration_date");
                String status = rs.getString("status");
                String teamName = rs.getString("team_name");

                // 创建报名记录对象
                Registration registration = new Registration(
                        id,
                        userId,
                        competitionId,
                        registrationDate,
                        status,
                        teamName
                );
                
                // 设置学生用户和竞赛信息
                User studentUser = userRepository.findById(userId);
                Competition competition = findCompetitionById(competitionId);
                
                registration.setStudentUser(studentUser);
                registration.setCompetition(competition);
                
                registrations.add(registration);
            }
        } catch (SQLException e) {
            logger.error("查询所有报名记录异常", e);
        }

        logger.info("返回 {} 条报名记录", registrations.size());
        return registrations;
    }

    /**
     * 根据用户ID查询报名记录
     * @param userId 用户ID
     * @return 用户的报名记录列表
     */
    public List<Registration> findRegistrationsByUserId(int userId) {
        List<Registration> registrations = new ArrayList<>();
        String sql = "SELECT id, user_id, comp_id, registration_date, status, team_name FROM registrations WHERE user_id = ?";
        logger.debug("执行查询SQL: {}, 用户ID: {}", sql, userId);

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int competitionId = rs.getInt("comp_id");
                    Date registrationDate = rs.getDate("registration_date");
                    String status = rs.getString("status");
                    String teamName = rs.getString("team_name");

                    // 创建报名记录对象
                    Registration registration = new Registration(
                            id,
                            userId,
                            competitionId,
                            registrationDate,
                            status,
                            teamName
                    );
                    
                    // 查询并设置对应的竞赛信息
                    Competition competition = findCompetitionById(competitionId);
                    registration.setCompetition(competition);
                    
                    registrations.add(registration);
                    
                    logger.debug("用户ID {} 的报名记录ID: {}, 竞赛: {}, 状态: {}", 
                            userId, id, (competition != null ? competition.getName() : "未知"), status);
                }
            }
        } catch (SQLException e) {
            logger.error("查询用户报名记录异常, 用户ID: {}", userId, e);
        }

        logger.info("用户ID {} 返回 {} 条报名记录", userId, registrations.size());
        return registrations;
    }

    /**
     * 根据ID查询竞赛信息
     * @param competitionId 竞赛ID
     * @return 竞赛对象
     */
    public Competition findCompetitionById(int competitionId) {
        String sql = "SELECT id, name, level, form_type, start_date, end_date FROM competitions WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, competitionId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String level = rs.getString("level");
                    String formType = rs.getString("form_type");
                    Date startDate = rs.getDate("start_date");
                    Date endDate = rs.getDate("end_date");

                    return new Competition(
                            id,
                            name,
                            level,
                            formType,
                            startDate,
                            endDate,
                            formType // 使用form_type作为type
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("CompetitionRepository: 查询竞赛信息异常: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 根据ID查询报名记录
     * @param registrationId 报名记录ID
     * @return 报名记录对象
     */
    public Registration findRegistrationById(int registrationId) {
        String sql = "SELECT id, user_id, comp_id, registration_date, status, team_name FROM registrations WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, registrationId);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    int userId = rs.getInt("user_id");
                    int competitionId = rs.getInt("comp_id");
                    Date registrationDate = rs.getDate("registration_date");
                    String status = rs.getString("status");
                    String teamName = rs.getString("team_name");
                    
                    // 创建报名记录对象
                    Registration registration = new Registration(
                            id,
                            userId,
                            competitionId,
                            registrationDate,
                            status,
                            teamName
                    );
                    
                    // 设置学生用户和竞赛信息
                    User studentUser = userRepository.findById(userId);
                    Competition competition = findCompetitionById(competitionId);
                    
                    registration.setStudentUser(studentUser);
                    registration.setCompetition(competition);
                    
                    return registration;
                }
            }
        } catch (SQLException e) {
            logger.error("查询报名记录异常, ID: {}", registrationId, e);
        }
        
        return null;
    }
    
    /**
     * 删除报名记录
     * @param registrationId 报名记录ID
     * @return 删除成功返回 true，否则 false
     */
    public boolean deleteRegistration(int registrationId) {
        String sql = "DELETE FROM registrations WHERE id = ?";
        logger.debug("执行删除SQL: {}", sql);
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, registrationId);
            int rowsDeleted = ps.executeUpdate();
            
            boolean success = rowsDeleted > 0;
            logger.info("删除报名记录{}", success ? "成功" : "失败");
            return success;
        } catch (SQLException e) {
            logger.error("删除报名记录异常, ID: {}", registrationId, e);
        }
        
        return false;
    }
    
    /**
     * 保存报名记录
     * @param registration 报名记录对象
     * @return 是否保存成功
     */
    public boolean saveRegistration(Registration registration) {
        String sql = "INSERT INTO registrations (user_id, comp_id, registration_date, status, team_name) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, registration.getUserId());
            ps.setInt(2, registration.getCompetitionId());
            ps.setTimestamp(3, new Timestamp(registration.getRegistrationDate().getTime()));
            ps.setString(4, registration.getStatus());
            ps.setString(5, registration.getTeamName());

            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.out.println("CompetitionRepository: 保存报名记录异常: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 保存竞赛信息
     * @param competition 竞赛对象
     * @return 是否保存成功
     */
    public boolean saveCompetition(Competition competition) {
        String sql = "INSERT INTO competitions (name, level, form_type, start_date, end_date, type) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, competition.getName());
            ps.setString(2, competition.getLevel());
            ps.setString(3, competition.getFormType());
            ps.setTimestamp(4, new Timestamp(competition.getStartDate().getTime()));
            ps.setTimestamp(5, new Timestamp(competition.getEndDate().getTime()));
            ps.setString(6, competition.getType());

            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.out.println("CompetitionRepository: 保存竞赛信息异常: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 更新竞赛信息
     * @param competition 竞赛对象
     * @return 是否更新成功
     */
    public boolean updateCompetition(Competition competition) {
        String sql = "UPDATE competitions SET name = ?, level = ?, form_type = ?, start_date = ?, end_date = ?, type = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, competition.getName());
            ps.setString(2, competition.getLevel());
            ps.setString(3, competition.getFormType());
            ps.setTimestamp(4, new Timestamp(competition.getStartDate().getTime()));
            ps.setTimestamp(5, new Timestamp(competition.getEndDate().getTime()));
            ps.setString(6, competition.getType());
            ps.setInt(7, competition.getId());

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("CompetitionRepository: 更新竞赛信息异常: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 删除竞赛信息
     * @param competitionId 竞赛ID
     * @return 是否删除成功
     */
    public boolean deleteCompetition(int competitionId) {
        String sql = "DELETE FROM competitions WHERE id = ?";
        logger.debug("执行删除SQL: {}", sql);
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, competitionId);
            int rowsDeleted = ps.executeUpdate();
            
            boolean success = rowsDeleted > 0;
            logger.info("删除竞赛{}", success ? "成功" : "失败");
            return success;
        } catch (SQLException e) {
            logger.error("删除竞赛异常, ID: {}", competitionId, e);
        }
        
        return false;
    }

    // 辅助方法：将字节数组转换为十六进制字符串
    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02X ", b));
        }
        return result.toString();
    }
}