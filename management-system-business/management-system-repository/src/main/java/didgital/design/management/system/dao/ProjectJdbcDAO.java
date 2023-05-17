package didgital.design.management.system.dao;

import didgital.design.management.system.JdbcConfig.JdbcConfig;
import didgital.design.management.system.common.enumerate.StatusProject;
import didgital.design.management.system.entity.Project;
import didgital.design.management.system.repository.BaseRepository;

import java.sql.*;
import java.util.*;

public class ProjectJdbcDAO implements BaseRepository<Project> {

    private final JdbcConfig jdbcConfig;

    public ProjectJdbcDAO(){
        jdbcConfig = new JdbcConfig();
    }



    @Override
    public Project create(Project project) {

        String query = "INSERT INTO Project(code, description, name, status) VALUES( ?, ?, ?, ?)";
        try (Connection connection = jdbcConfig.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, project.getCode());
            ps.setString(2, project.getDescription());
            ps.setString(3, project.getName());
            ps.setString(4, project.getStatus().getStatus());

            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                project.setId(keys.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return project;
    }

    @Override
    public Project update(Long id, Project project) {

        String query = "UPDATE Project SET code=?, description=?, name=?, status=? WHERE id=?";
        try (Connection connection = jdbcConfig.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, project.getCode());
            ps.setString(2, project.getDescription());
            ps.setString(3, project.getName());
            ps.setString(4, project.getStatus().getStatus());
            ps.setLong(5, id);

            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                project.setId(keys.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return project;
    }


    @Override
    public Optional<Project> getById(Long id) {

        String query = "SELECT * FROM Project WHERE id=?";
        try (Connection connection = jdbcConfig.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setLong(1, id);

            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next())
                return Optional.of(mapperQueryToProject(resultSet));
            else
                return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Project> getAll() {

        List<Project> projects = new ArrayList<>();
        String query = "SELECT * FROM Project LIMIT 100";
        try (Connection connection = jdbcConfig.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                projects.add(mapperQueryToProject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return projects;
    }

    @Override
    public void deleteById(Long id) {
        String query = "DELETE FROM Project WHERE id=?";
        try(Connection connection = jdbcConfig.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private Project mapperQueryToProject(ResultSet resultSet) throws SQLException {
        Project project = new Project();
        project.setId(resultSet.getLong("id"));
        project.setCode(resultSet.getString("code"));
        project.setName(resultSet.getString("name"));
        project.setDescription(resultSet.getString("description"));
        StatusProject status = Arrays.stream(StatusProject.values())
                .filter(s -> {
                    try {
                        return s.getStatus().equals(resultSet.getString("status"));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                })
                .findAny().get();
        project.setStatus(status);
        return project;
    }

}
