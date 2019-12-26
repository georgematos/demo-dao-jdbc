package application;

import java.util.List;

import db.DB;
import model.dao.impl.DepartmentDaoJDBC;
import model.entities.Department;

public class ProgramTestDepartment {
	public static void main(String[] args) {

		System.out.println("------------find dep by id-------------");
		DepartmentDaoJDBC depDao = new DepartmentDaoJDBC(DB.getConnection());
		Department dep = depDao.findById(1);
		System.out.println(dep);

		System.out.println("------------find all dep-------------");
		List<Department> depList = depDao.findAll();
		depList.forEach(System.out::println);

		System.out.println("------------insert dep-------------");
		Department newDep = new Department(null, "Magazines");
		depDao.insert(newDep);
		System.out.println("Dep inserted: " + newDep);

		System.out.println("------------update dep-------------");
		Department depup = depDao.findById(8);
		depup.setName("Smartphones");
		depDao.update(depup);
		
		System.out.println("------------delete dep-------------");
		depDao.deleteById(8);
		
	}
}
