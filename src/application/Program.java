package application;

import java.util.List;

import db.DB;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
				
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		System.out.println("------------find seller by id-------------");
		
		Seller seller = sellerDao.findById(2);
		
		System.out.println(seller);
		
		System.out.println("------------sellers by department id-------------");
		
		List<Seller> sellers = sellerDao.findByDepartment(new Department(1, null));
		
		sellers.forEach(System.out::println);
		
		System.out.println("------------sellers by department id-------------");
		
		sellers = sellerDao.findAll();
		
		sellers.forEach(System.out::println);
		
		DB.closeConnection();
		
	}
	
}
