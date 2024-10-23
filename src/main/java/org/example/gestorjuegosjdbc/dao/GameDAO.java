package org.example.gestorjuegosjdbc.dao;

import org.example.gestorjuegosjdbc.models.Game;
import org.example.gestorjuegosjdbc.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameDAO implements DAO<Game> {

    Connection con;
    public GameDAO(Connection c) {
        con=c;
    }

    @Override
    public List<Game> findAll() {
        return List.of();
    }

    public List<Game> findByUser(User user) {
        Integer id = user.getId();
        var output = new ArrayList<Game>(0);

        try(PreparedStatement ps = con.prepareStatement("SELECT * FROM games WHERE user_id = ?")) {
            ps.setInt(1, id);
            var result = ps.executeQuery();
            while(result.next()) {
                Game g = new Game(
                        result.getInt(1),
                        result.getString(2),
                        result.getString(3),
                        result.getInt(4),
                        result.getString(5),
                        result.getInt(6)
                );
                g.setImage_url(result.getString(7));
                g.setUser(user);
                output.add(g);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return output;
    }

    @Override
    public Game findById(Integer id) {
        Game salida = null;

        try(PreparedStatement ps = con.prepareStatement("select * from games where id=?")){
            ps.setInt(1, id);
            var result = ps.executeQuery();
            if(result.next()) {
                salida = new Game(
                        result.getInt(1),
                        result.getString(2),
                        result.getString(3),
                        result.getInt(4),
                        result.getString(5),
                        result.getInt(6)
                );
                salida.setImage_url(result.getString(7));

                salida.setUser( new UserDAO(con).findById(salida.getUser_id()) );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return salida;
    }

    @Override
    public void save(Game game) {

        try( PreparedStatement pst = con.prepareStatement("insert into games(title, platform, year, description, user_id, image_url) values(?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)){
            pst.setString(1, game.getTitle());
            pst.setString(2, game.getPlatform());
            pst.setInt(3, game.getYear());
            pst.setString(4, game.getDescription());
            pst.setInt(5, game.getUser_id());
            pst.setString(6, game.getImage_url());
            int result = pst.executeUpdate();

            if(result>0){
                ResultSet keys = pst.getGeneratedKeys();
                keys.next();
                Integer game_id = keys.getInt(1);
                game.setId(game_id);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(Game game) {

    }

    @Override
    public void delete(Game game) {

    }

    public Set<String> getPlatforms(){

        var salida = new HashSet<String>();

        try( Statement st = con.createStatement()){
            ResultSet rs = st.executeQuery("select distinct games.platform from ad.games order by platform asc");
            while(rs.next()){
                salida.add(rs.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return salida;

    }


}
