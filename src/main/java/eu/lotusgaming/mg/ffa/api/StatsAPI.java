package eu.lotusgaming.mg.ffa.api;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import eu.lotusgaming.mg.ffa.misc.MySQL;

public class StatsAPI {
	  public static boolean playerExists(String uuid) {
	    try {
	      PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM StatsFFA WHERE UUID=?");
	      ps.setString(1, uuid);
	      ResultSet rs = ps.executeQuery();
	      if (rs.next())
	        return (rs.getString("UUID") != null); 
	      return false;
	    } catch (SQLException e) {
	      e.printStackTrace();
	      return false;
	    } 
	  }
	  
	  public static void createPlayer(String uuid) {
	    if (!playerExists(uuid)) {
	    	try {
				PreparedStatement ps = MySQL.getConnection().prepareStatement("INSERT INTO StatsFFA (UUID, KILLS, DEATHS, WIN, LOSE) VALUES (?, '0', '0', '0', '0');");
				ps.setString(1, uuid);
				ps.executeUpdate();
	    	} catch (SQLException e) {
				e.printStackTrace();
			}
	    }
	  }
	  
	  public static Integer getKills(String uuid) {
	    Integer i = Integer.valueOf(0);
	    if (playerExists(uuid)) {
	      try {
	    	  PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM StatsFFA WHERE UUID=?");
	    	  ps.setString(1, uuid);
	    	  ResultSet rs = ps.executeQuery();
	        if (rs.next())
	          Integer.valueOf(rs.getInt("KILLS")); 
	        i = Integer.valueOf(rs.getInt("KILLS"));
	      } catch (SQLException e) {
	        e.printStackTrace();
	      } 
	    } else {
	      createPlayer(uuid);
	      getKills(uuid);
	    } 
	    return i;
	  }
	  
	  public static Integer getWins(String uuid) {
	    Integer i = Integer.valueOf(0);
	    if (playerExists(uuid)) {
	      try {
	    	PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM StatsFFA WHERE UUID=?");
	        ps.setString(1, uuid);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next())
	          Integer.valueOf(rs.getInt("WIN")); 
	        i = Integer.valueOf(rs.getInt("WIN"));
	      } catch (SQLException e) {
	        e.printStackTrace();
	      } 
	    } else {
	      createPlayer(uuid);
	      getWins(uuid);
	    } 
	    return i;
	  }
	  
	  public static Integer getDestroyedBeds(String uuid) {
	    Integer i = Integer.valueOf(0);
	    if (playerExists(uuid)) {
	      try {
	    	PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM StatsFFA  WHERE UUID=?");
	    	ps.setString(1, uuid);
	    	ResultSet rs = ps.executeQuery();
	        if (rs.next())
	          Integer.valueOf(rs.getInt("BEDS")); 
	        i = Integer.valueOf(rs.getInt("BED"));
	      } catch (SQLException e) {
	        e.printStackTrace();
	      } 
	    } else {
	      createPlayer(uuid);
	      getDestroyedBeds(uuid);
	    } 
	    return i;
	  }
	  
	  public static Integer getLoses(String uuid) {
	    Integer i = Integer.valueOf(0);
	    if (playerExists(uuid)) {
	      try {
	    	PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM StatsFFA WHERE UUID=?");
	    	ps.setString(1, uuid);
	    	ResultSet rs = ps.executeQuery();
	        if (rs.next())
	          Integer.valueOf(rs.getInt("LOSE")); 
	        i = Integer.valueOf(rs.getInt("LOSE"));
	      } catch (SQLException e) {
	        e.printStackTrace();
	      } 
	    } else {
	      createPlayer(uuid);
	      getLoses(uuid);
	    } 
	    return i;
	  }
	  
	  public static Integer getDeaths(String uuid) {
	    Integer i = Integer.valueOf(0);
	    if (playerExists(uuid)) {
	      try {
	    	PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM StatsFFA WHERE UUID=?");
	    	ps.setString(1, uuid);
	    	ResultSet rs = ps.executeQuery();
	        if (rs.next())
	          Integer.valueOf(rs.getInt("DEATHS")); 
	        i = Integer.valueOf(rs.getInt("DEATHS"));
	      } catch (SQLException e) {
	        e.printStackTrace();
	      } 
	    } else {
	      createPlayer(uuid);
	      getDeaths(uuid);
	    } 
	    return i;
	  }
	  
	  public static Integer getCoins(String uuid) {
	    Integer i = Integer.valueOf(0);
	    if (playerExists(uuid)) {
	      try {
	    	PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM StatsFFA WHERE UUID=?");
		    ps.setString(1, uuid);
		    ResultSet rs = ps.executeQuery();
	        if (rs.next())
	          Integer.valueOf(rs.getInt("COINS")); 
	        i = Integer.valueOf(rs.getInt("COINS"));
	      } catch (SQLException e) {
	        e.printStackTrace();
	      } 
	    } else {
	      createPlayer(uuid);
	      getCoins(uuid);
	    } 
	    return i;
	  }
	  
	  public static void setKills(String uuid, Integer kills) {
	    if (playerExists(uuid)) {
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE StatsFFA SET KILLS=? WHERE UUID=?");
			ps.setInt(1, kills);
		      ps.setString(2, uuid);
		      ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    } else {
	      createPlayer(uuid);
	      setKills(uuid, kills);
	    } 
	  }
	  
	  public static void setDeaths(String uuid, Integer deaths) {
	    if (playerExists(uuid)) {
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE StatsFFA SET DEATHS=? WHERE UUID=?");
			 ps.setInt(1, deaths);
			 ps.setString(2, uuid);
			 ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	     
	    } else {
	      createPlayer(uuid);
	      setDeaths(uuid, deaths);
	    } 
	  }
	  
	  public static void setWins(String uuid, Integer wins) {
	    if (playerExists(uuid)) {
	    	try {
				PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE StatsFFA SET WIN=? WHERE UUID=?");
				 ps.setInt(1, wins);
				 ps.setString(2, uuid);
				 ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    } else {
	      createPlayer(uuid);
	      setWins(uuid, wins);
	    } 
	  }
	  
	  public static void setDestroyedBeds(String uuid, Integer beds) {
	    if (playerExists(uuid)) {
	    	try {
				PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE StatsFFA SET BED=? WHERE UUID=?");
				 ps.setInt(1, beds);
				 ps.setString(2, uuid);
				 ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    } else {
	      createPlayer(uuid);
	      setDestroyedBeds(uuid, beds);
	    } 
	  }
	  
	  public static void setLose(String uuid, Integer lose) {
	    if (playerExists(uuid)) {
	    	try {
				PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE StatsFFA SET LOSE=? WHERE UUID=?");
				 ps.setInt(1, lose);
				 ps.setString(2, uuid);
				 ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    } else {
	      createPlayer(uuid);
	      setLose(uuid, lose);
	    } 
	  }
	  
	  public static void setCoins(String uuid, Integer coins) {
	    if (playerExists(uuid)) {
	    	try {
				PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE StatsFFA SET COINS=? WHERE UUID=?");
				 ps.setInt(1, coins);
				 ps.setString(2, uuid);
				 ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    } else {
	      createPlayer(uuid);
	      setCoins(uuid, coins);
	    } 
	  }
	  
	  public static void addKills(String uuid, Integer kills) {
	    if (playerExists(uuid)) {
	      setKills(uuid, Integer.valueOf(getKills(uuid).intValue() + kills.intValue()));
	    } else {
	      createPlayer(uuid);
	      addKills(uuid, kills);
	    } 
	  }
	  
	  public static void addDeaths(String uuid, Integer deaths) {
	    if (playerExists(uuid)) {
	      setDeaths(uuid, Integer.valueOf(getDeaths(uuid).intValue() + deaths.intValue()));
	    } else {
	      createPlayer(uuid);
	      addDeaths(uuid, deaths);
	    } 
	  }
	  
	  public static void addWin(String uuid, Integer wins) {
	    if (playerExists(uuid)) {
	      setWins(uuid, Integer.valueOf(getWins(uuid).intValue() + wins.intValue()));
	    } else {
	      createPlayer(uuid);
	      addWin(uuid, wins);
	    } 
	  }
	  
	  public static void addLose(String uuid, Integer lose) {
	    if (playerExists(uuid)) {
	      setLose(uuid, Integer.valueOf(getLoses(uuid).intValue() + lose.intValue()));
	    } else {
	      createPlayer(uuid);
	      addLose(uuid, lose);
	    } 
	  }
	  
	  public static void addDestroyedBed(String uuid, Integer bed) {
	    if (playerExists(uuid)) {
	      setDestroyedBeds(uuid, Integer.valueOf(getDestroyedBeds(uuid).intValue() + bed.intValue()));
	    } else {
	      createPlayer(uuid);
	      addDestroyedBed(uuid, bed);
	    } 
	  }
	  
	  public static void addCoins(String uuid, Integer coins) {
	    if (playerExists(uuid)) {
	      setCoins(uuid, Integer.valueOf(getCoins(uuid).intValue() + coins.intValue()));
	    } else {
	      createPlayer(uuid);
	      addCoins(uuid, coins);
	    } 
	  }
	  
	  public static void removeKills(String uuid, Integer kills) {
	    if (playerExists(uuid)) {
	      setKills(uuid, Integer.valueOf(getKills(uuid).intValue() - kills.intValue()));
	    } else {
	      createPlayer(uuid);
	      removeKills(uuid, kills);
	    } 
	  }
	  
	  public static void removeDeaths(String uuid, Integer deaths) {
	    if (playerExists(uuid)) {
	      setKills(uuid, Integer.valueOf(getDeaths(uuid).intValue() - deaths.intValue()));
	    } else {
	      createPlayer(uuid);
	      removeDeaths(uuid, deaths);
	    } 
	  }
	  
	  public static void removeDestroyedBeds(String uuid, Integer beds) {
	    if (playerExists(uuid)) {
	      setDestroyedBeds(uuid, Integer.valueOf(getDestroyedBeds(uuid).intValue() - beds.intValue()));
	    } else {
	      createPlayer(uuid);
	      removeDestroyedBeds(uuid, beds);
	    } 
	  }
	  
	  public static void removeCoins(String uuid, Integer coins) {
	    if (playerExists(uuid)) {
	      setCoins(uuid, Integer.valueOf(getCoins(uuid).intValue() - coins.intValue()));
	    } else {
	      createPlayer(uuid);
	      removeCoins(uuid, coins);
	    } 
	  }
	}
