//package com.example.wishlist;
//
//import com.example.wishlist.model.Wish;
//import com.example.wishlist.repository.WishRepository;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//
//import javax.sql.DataSource;
//import java.util.List;
//
//public class WishRepositoryTestRunner {
//
//    public static void main(String[] args) {
//        // --------------------------- OPSÆTNING AF DATABASEFORBINDELSE ---------------------------
//        // Her opsætter vi forbindelsen til databasen via DriverManagerDataSource
//        DataSource dataSource = new DriverManagerDataSource(
//                "jdbc:mysql://localhost:3306/wish_list",  // Dit database-navn
//                "root",                               // Dit brugernavn
//                "data2025."                            // Din adgangskode
//        );
//
//        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
//        WishRepository wishRepository = new WishRepository(jdbcTemplate);
//
//        // --------------------------- TEST CREATE ---------------------------
//        // Opretter et nyt 'Wish'-objekt og forsøger at indsætte det i databasen
//        Wish newWish = new Wish("Test Wish", "A description of the test wish", 10, 99.99);
//        boolean createSuccess = wishRepository.create(newWish);
//        System.out.println("Oprettelse af ønske var succesfuld: " + createSuccess);
//
//        // Verificer, at ønsket er blevet oprettet ved at hente alle ønsker
//        List<Wish> allWishes = wishRepository.findAll();
//        System.out.println("Alle ønsker efter oprettelse:");
//        for (Wish wish : allWishes) {
//            System.out.println(wish);
//        }
//
//        // --------------------------- TEST UPDATE ---------------------------
//        // Opdaterer det oprettede 'Wish'-objekt ved at ændre beskrivelsen
//        newWish.setDescription("Updated description of the test wish");
//        boolean updateSuccess = wishRepository.update(newWish);
//        System.out.println("Opdatering af ønske var succesfuld: " + updateSuccess);
//
//        // Verificer, at ønsket er blevet opdateret ved at hente alle ønsker igen
//        List<Wish> allWishesAfterUpdate = wishRepository.findAll();
//        System.out.println("Alle ønsker efter opdatering:");
//        for (Wish wish : allWishesAfterUpdate) {
//            System.out.println(wish);
//        }
//
//        // --------------------------- TEST DELETE ---------------------------
//        // Sletter det oprettede og opdaterede ønske via id
//        boolean deleteSuccess = wishRepository.deleteById(newWish.getId());
//        System.out.println("Sletning af ønske var succesfuld: " + deleteSuccess);
//
//        // Verificer, at ønsket er blevet slettet ved at hente alle ønsker igen
//        List<Wish> allWishesAfterDelete = wishRepository.findAll();
//        System.out.println("Alle ønsker efter sletning:");
//        for (Wish wish : allWishesAfterDelete) {
//            System.out.println(wish);
//        }
//
//        // --------------------------- TEST FIND BY ID ---------------------------
//        // Finder et eksisterende ønske ved hjælp af findById() og tester om vi får det ønskede resultat
//        int idToFind = 8030; // Vælg et ID, som du ved eksisterer
//        Wish foundWish = wishRepository.findById(idToFind);
//        if (foundWish != null) {
//            System.out.println("Fundet ønske: " + foundWish);
//        } else {
//            System.out.println("Ønske med ID " + idToFind + " blev ikke fundet.");
//        }
//    }
//}