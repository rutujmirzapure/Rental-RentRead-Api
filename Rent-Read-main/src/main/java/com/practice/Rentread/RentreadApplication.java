package com.practice.Rentread;

import com.practice.Rentread.DataModels.Book;
import com.practice.Rentread.DataModels.RentalUser;
import com.practice.Rentread.DataModels.Role;
import com.practice.Rentread.Repository.BookRepository;
import com.practice.Rentread.Repository.RentalUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class RentreadApplication implements CommandLineRunner {

	@Autowired
	private RentalUserRepository userRepository;

	@Autowired
	private BookRepository bookRepository;

	public static void main(String[] args) {
		SpringApplication.run(RentreadApplication.class, args);
	}

	public void run(String... args){
		RentalUser adminAccount = userRepository.findByRole(Role.ADMIN);

		if(adminAccount == null){
			RentalUser rentalUser = new RentalUser();
			rentalUser.setFirstName("admin");
			rentalUser.setLastName("admin");
			rentalUser.setEmail("admin@gmail.com");
			rentalUser.setRole(Role.ADMIN);
			rentalUser.setPassword(new BCryptPasswordEncoder().encode("admin"));
			userRepository.save(rentalUser);
		}

		Book book = new Book();
		book.setAvailabilityStatus(true);
		book.setTitle("Book 1");
		book.setGenre("fantasy");
		book.setAuthor("Rutuz");
		bookRepository.save(book);
	}

}
