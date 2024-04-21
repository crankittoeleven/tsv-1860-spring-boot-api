package co.anastasiou.tsv1860springbootapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class PlayerDAO implements DataAccessObject {
    @Autowired
    DataSource dataSource;

    private static final String INSERT = "INSERT INTO players " +
            "(full_name, country, birth_year) VALUES (?, ?, ?)";
    private static final String GET = "SELECT id, full_name, country, birth_year " +
            "FROM players WHERE id=?";
    private static final String GET_ALL = "SELECT id, full_name, country, birth_year " +
            "FROM players";
    private static final String UPDATE = "UPDATE players SET full_name=?, country=?, " +
            "birth_year=? WHERE id=?";
    private static final String DELETE = "DELETE FROM players WHERE id=?";

    @Override
    public PlayerDTO getById(int id) {
        PlayerDTO player = null;

        try(PreparedStatement statement = this.dataSource.getConnection().prepareStatement(GET)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                player = new PlayerDTO();
                player.setId(rs.getInt("id"));
                player.setFullName(rs.getString("full_name"));
                player.setCountry(rs.getString("country"));
                player.setBirthYear(rs.getInt("birth_year"));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return player;
    }

    @Override
    public List<PlayerDTO> getAll() {
        List<PlayerDTO> players = new ArrayList<>();

        try(PreparedStatement statement = this.dataSource.getConnection().prepareStatement(GET_ALL)) {
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                PlayerDTO player = new PlayerDTO();
                player.setId(rs.getInt("id"));
                player.setFullName(rs.getString("full_name"));
                player.setCountry(rs.getString("country"));
                player.setBirthYear(rs.getInt("birth_year"));
                players.add(player);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return players;
    }

    @Override
    public PlayerDTO update(PlayerDTO dto) {
        PlayerDTO player = null;

        try(PreparedStatement statement = this.dataSource.getConnection().prepareStatement(UPDATE)) {
            statement.setString(2, dto.getFullName());
            statement.setString(3, dto.getCountry());
            statement.setInt(5, dto.getBirthYear());
            statement.execute();
            player = this.getById(dto.getId());
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return player;
    }

    @Override
    public void create(PlayerDTO dto) {
        try(PreparedStatement statement = this.dataSource.getConnection().prepareStatement(INSERT)) {
            statement.setString(1, dto.getFullName());
            statement.setString(2, dto.getCountry());
            statement.setInt(3, dto.getBirthYear());
            statement.execute();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try(PreparedStatement statement = this.dataSource.getConnection().prepareStatement(DELETE)) {
            statement.setInt(1, id);
            statement.execute();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
