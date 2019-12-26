package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public List<Seller> findByDepartment(Department department) {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement(
					"SELECT seller.*, department.Name DepName " + "FROM seller " + "INNER JOIN department "
							+ "ON department.Id = seller.DepartmentId " + "WHERE DepartmentId = ? " + "ORDER BY Name;");

			st.setInt(1, department.getId());
			rs = st.executeQuery();

			List<Seller> sellers = new ArrayList<Seller>();

			Map<Integer, Department> map = new HashMap<>();

			while (rs.next()) {

				Department dep = map.get(rs.getInt("DepartmentId"));

				if (dep == null) {
					dep = getDBDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}

				Seller seller = getDBSeller(rs, dep);
				sellers.add(seller);
			}

			return sellers;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	@Override
	public List<Seller> findAll() {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			ps = conn.prepareStatement("SELECT seller.*, department.Name DepName FROM seller INNER JOIN "
					+ "department ON seller.departmentId = department.Id ORDER BY Name;");

			rs = ps.executeQuery();

			List<Seller> sellers = new ArrayList<>();

			Map<Integer, Department> map = new HashMap<>();

			while (rs.next()) {

				Department dep = map.get(rs.getInt("DepartmentId"));

				if (dep == null) {
					dep = getDBDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}

				Seller seller = getDBSeller(rs, dep);
				sellers.add(seller);
			}

			return sellers;

		} catch (SQLException e) {

			throw new DbException(e.getMessage());

		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(ps);
		}

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
