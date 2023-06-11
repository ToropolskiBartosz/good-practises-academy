package pl.praktycznajava.module3.valueobjects.challenge1;

public class UserService {

    UserRepository userRepository;

    public boolean isBirthdayToday(String userId) {
        User user = userRepository.findBy(userId);
        return user.getDateOfBirth().isToday();
    }

    public boolean isAdult(String userId) {
        User user = userRepository.findBy(userId);
        return user.getDateOfBirth().isAdult();
    }
    
    public String getFormattedAddress(String userId) {
        User user = userRepository.findBy(userId);
        Address address = user.getAddress();
        return address.getCountry() + ", " + address.getCity() + ", " + address.getStreet() + " " + address.getPostalCode();
    }

}