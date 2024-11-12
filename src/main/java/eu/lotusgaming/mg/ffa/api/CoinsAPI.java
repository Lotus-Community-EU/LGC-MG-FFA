package eu.lotusgaming.mg.ffa.api;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import eu.lotusgaming.mg.ffa.misc.MySQL;

public class CoinsAPI {
	  public static boolean playerExists(String uuid) {
		  boolean exist = false;
	    try {
	    	PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM Coins WHERE UUID = ?");
	    	ps.setString(1, uuid);
	      ResultSet resultSet = ps.executeQuery();
	      if(resultSet.next()) {
	    	  exist = true;
	      }else {
	    	  exist = false;
	      }
	    } catch (SQLException sQLException) {
	      sQLException.printStackTrace();
	    } 
	    return exist;
	  }
	  
	  public static void createPlayer(String uuid) {
	    if (!playerExists(uuid)) {
	    	try {
				PreparedStatement ps = MySQL.getConnection().prepareStatement("INSERT INTO Coins(UUID, Coins) VALUES (?,?)");
				ps.setString(1, uuid);
				ps.setInt(2, 0);
				ps.executeUpdate();
	    	} catch (SQLException e) {
				e.printStackTrace();
			}
	    }
	  }
	  
	  public static Integer getCoinsDB(String uuid) {
	    int Coins = 0;
	    if (playerExists(uuid)) {
	      try {
	    	  PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM Coins WHERE UUID = ?");
	    	  ps.setString(1, uuid);
	        ResultSet resultSet = ps.executeQuery();
	        if (resultSet.next()) {
	        	Coins = resultSet.getInt("Coins");
	        }
	      } catch (SQLException sQLException) {
	        sQLException.printStackTrace();
	      } 
	    }
	    return Coins;
	  }
	  
	  public static void setCoinsDB(String uuid, Integer coins) {
	    if (playerExists(uuid)) {
	    	try {
				PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE Coins SET Coins = ? WHERE UUID = ?");
				ps.setInt(1, coins);
				ps.setString(2, uuid);
				ps.executeUpdate();
	    	} catch (SQLException e) {
				e.printStackTrace();
			}
	    } else {
	      createPlayer(uuid);
	      setCoinsDB(uuid, coins);
	    } 
	  }
	  
	  public static void addCoinsDB(String uuid, Integer coins) {
	    if (playerExists(uuid)) {
	      setCoinsDB(uuid, Integer.valueOf(getCoinsDB(uuid).intValue() + coins.intValue()));
	    } else {
	      createPlayer(uuid);
	      addCoinsDB(uuid, coins);
	    } 
	  }
	  
	  public static void removeCoinsDB(String uuid, Integer coins) {
	    if (playerExists(uuid)) {
	      setCoinsDB(uuid, Integer.valueOf(getCoinsDB(uuid).intValue() - coins.intValue()));
	    } else {
	      createPlayer(uuid);
	      removeCoinsDB(uuid, coins);
	    } 
	  }
	  
}