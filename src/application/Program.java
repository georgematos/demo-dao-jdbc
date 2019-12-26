package application;

import java.util.Date;
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

		System.out.println("------------all sellers-------------");

		sellers = sellerDao.findAll();

		sellers.forEach(System.out::println);

		System.out.println("------------insert seller-------------");

		Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.0, new Department(2, null));
		sellerDao.insert(newSeller);
		System.out.println("Inserted. New Id: " + newSeller.getId());

		System.out.println("------------update seller-------------");

		newSeller = sellerDao.findById(1);
		newSeller.setBaseSalary(3500.0);
		sellerDao.update(newSeller);

		System.out.println("Updated Seller: " + newSeller);

		DB.closeConnection();

	}

}
