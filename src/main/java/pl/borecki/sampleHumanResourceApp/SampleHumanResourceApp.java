package pl.borecki.sampleHumanResourceApp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.borecki.sampleHumanResourceApp.model.AppUser;
import pl.borecki.sampleHumanResourceApp.model.Employee;
import pl.borecki.sampleHumanResourceApp.repository.AppUserRepository;
import pl.borecki.sampleHumanResourceApp.repository.EmployeeRepository;

import java.time.LocalDate;

@SpringBootApplication
public class SampleHumanResourceApp {

    private static final Logger log = LoggerFactory.getLogger(SampleHumanResourceApp.class);

    public static void main(String[] args) {
        SpringApplication.run(SampleHumanResourceApp.class, args);
    }

    @Bean
    public CommandLineRunner loadData(EmployeeRepository employeeRepo, AppUserRepository appUserRepo) {
        return (args) -> {
            appUserRepo.save(new AppUser("Jan", passwordEncoder().encode("123"), "ROLE_USER"));
            appUserRepo.save(new AppUser("Admin", passwordEncoder().encode("123"), "ROLE_ADMIN"));
            employeeRepo.save(new Employee("Adam", "Kowalski", LocalDate.of(1999, 01, 01), "adam@2p.pl"));
            employeeRepo.save(new Employee("Robert", "Nowak", LocalDate.of(1999, 01, 02), "robert@2p.pl"));
            employeeRepo.save(new Employee("Adrian", "Kowal", LocalDate.of(1999, 01, 03), "arian@2p.pl"));
            employeeRepo.save(new Employee("Monika", "Nowakowska", LocalDate.of(1999, 01, 04), "monika@2p.pl"));
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
