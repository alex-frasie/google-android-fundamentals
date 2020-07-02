package ro.utcn.sd;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ro.utcn.sd.dto.*;
import ro.utcn.sd.model.Admin;
import ro.utcn.sd.model.CarPart;
import ro.utcn.sd.services.*;

import java.util.UUID;

@SpringBootTest
class SdApplicationTests {


	@Autowired
	private AdminService adminService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CarPartService carPartService;

	@Autowired
	private ProducerService producerService;

	@Autowired
	private CartService cartService;

	@Test
	void addAdmin() {
		Admin admin = new Admin();

		admin.setFirstName("Klaus");
		admin.setLastName("Iohannis");
		admin.setUsername("kiohannis");
		admin.setPassword("kiohannis");
		admin.setIdAdmin(UUID.randomUUID().toString());
		admin.setPosition("CEO");
		admin.setSalary(3500.0);

		adminService.addAdmin(admin);

	}

	@Test
	void registerCustomer(){
		RegisterDTO registerDTO = new RegisterDTO();

		registerDTO.setFirstName("Alex");
		registerDTO.setLastName("Frasie");
		registerDTO.setEmail("frasie.alexandru@gmail.com");
		registerDTO.setUsername("alexfrasie");
		registerDTO.setPassword("cocacola1");

		registerDTO.setCity("Sibiu");
		registerDTO.setCounty("Sibiu");
		registerDTO.setStreet("Otelarilor");
		registerDTO.setPostalCode("550239");
		registerDTO.setNumber(47);

		customerService.registerNewCustomer(registerDTO);
	}

	@Test
	void shouldLoginAdmin(){
		LoginDTO loginDTO = new LoginDTO();

		loginDTO.setCredential("kiohannis");
		loginDTO.setPassword("kiohannis");

		Admin admin = adminService.loginAdmin(loginDTO.getCredential(), loginDTO.getPassword());

		System.out.println(admin.getUsername());
	}

	@Test
	void addProducer(){
		ProducerDTO producerDTO = new ProducerDTO();

		producerDTO.setName("APEX");
		producerDTO.setCountry("Romania");
		producerDTO.setStartingYear(2008);

		producerService.addProducer(producerDTO);
	}

	@Test
	void seeProducers(){
		System.out.println(producerService.getAllProducers());
	}

	@Test
	void addProduct(){
		CarPartDTO carPartDTO = new CarPartDTO();

		carPartDTO.setName("Anvelope Alpin");
		carPartDTO.setDescription("Anvelope Iarna MICHELIN Alpin 5 225/60 R16 102 H XL");
		carPartDTO.setNew(true);
		carPartDTO.setPrice(590.0);
		carPartDTO.setProducerName("Michelin");
		carPartDTO.setQuantityAvailable(12);
		carPartDTO.setYear(2020);

		carPartService.addCarPart(carPartDTO);
	}

	@Test
	void addToCart(){
		CarPartCartDTO carPartCartDTO = new CarPartCartDTO();

		carPartCartDTO.setQuantity(1);
		carPartCartDTO.setName("Lichid de parbriz");

		cartService.addToCart(carPartCartDTO, "alexfrasie");
	}
}
