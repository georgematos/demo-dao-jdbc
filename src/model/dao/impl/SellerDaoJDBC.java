package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	private Connection conn;

	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller seller) {
		// TODO Auto-generated method stub
	}

	@Override
	public void update(Seller seller) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Seller findById(Integer id) {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT seller.*, department.Name as DepName " + "FROM seller "
					+ "INNER JOIN department " + "ON seller.DepartmentId = department.id " + "WHERE seller.id = ?;");

			st.setInt(1, id);
			rs = st.executeQuery();
			
			if (rs.next()) {
				
				Department dep = getDBDepartment(rs);
				Seller seller = getDBSeller(rs, dep);

				return seller;
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
		return null;
	}

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Department getDBDepartment(ResultSet rs) throws SQLException {
		
		Department dp = new Department();
		
		dp.setId(rs.getInt("DepartmentId"));
		dp.setName(rs.getString("DepName"));
		
		return dp;
	}
	
	private Seller getDBSeller(ResultSet rs, Department dep) throws SQLException {
		
		Seller seller = new Seller();

		seller.setId(rs.getInt("Id"));
		seller.setName(rs.getString("Name"));
		seller.setEmail(rs.getString("Email"));
		seller.setBirthDate(rs.getDate("BirthDate"));
		seller.setBaseSalary(rs.getDouble("BaseSalary"));
		seller.setDepartment(dep);

		return seller;
	}

}
